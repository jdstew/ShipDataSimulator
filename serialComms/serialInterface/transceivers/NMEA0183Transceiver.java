/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: NMEA0183Transceiver.java
 * Created: 2005-02-01, 12:01:01
 */
package serialComms.serialInterface.transceivers;

import java.util.*;
import javax.comm.*;
import serialComms.*;
import serialComms.serialInterface.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * This class both receieves and transmits serial data, and performs 
 * common preprocessing of sentence data.
 *
 * @author Jeff Stewart
 * @version 1.1.0.0, 2005-02-01
 */
public class NMEA0183Transceiver extends AbstractTransceiver {
   public final static long SLOW_DATA_THRESHOLD = 1000; // in milliseconds
   
   public final static char DOLLAR = 0x24;
   public final static char EXCLAMATION = 0x21;
   
   public final static char QUERY = 0x51;
   public final static char PROPRIETARY = 0x50;
   
   public final static char COMMA = 0x2C;
   public final static char CIRCUMFLEX = 0x5E;
   public final static char ASTERISK = 0x2A;
  
   public final static char CARRIAGE_RETURN = 0x0D;
   public final static char LINE_FEED = 0x0A;
   
   public final static char BACK_SLANT = 0x5C; 
   public final static char TILDE = 0x7E;
   public final static char DELETE = 0x7F;
   
   static final char [] UNUSED_RESERVED_CHAR = {
      EXCLAMATION,
      BACK_SLANT,
      TILDE,
      DELETE };
   static int unusedCharCount = UNUSED_RESERVED_CHAR.length;
       
   SerialChannelIO serialChannelIO;
   
   ReceivedSentenceServer receivedSentenceServer;
   ReceivedSentence receivedSentence;
   SentenceErrorLog errorLog;
   
   int startPosition;
   int codePosition;
   int codeCharacter;
   int checksumPosition;
   int lineFeed;
   int carriageReturn;
   int sentenceLength;
   int dataFieldCount;
   
   CharacterBuffer sentRcvdRaw;
   CharacterBuffer processedField;
   ArrayList<String> sentenceFields;
   
   boolean proprietary;
   boolean querySentence;
   
   int talkerID;
   int queryTalkerID;
   int formatter;
   int calculatedChecksum;
   int receivedChecksum;
   
   long startTime;
   long stopTime;
   
   /**
    * Instantiates a SentenceTransceiver object.
    *
    * @param serialPort The serial port object.
    * @param dbgMgr The debug manager object for report errors to.
    */   
   public NMEA0183Transceiver (SerialPort serialPort) {
      super(serialPort);
      
      receivedSentenceServer = new ReceivedSentenceServer ();
      sentRcvdRaw = new CharacterBuffer ();
      processedField = new CharacterBuffer ();
      sentenceFields = new ArrayList<String>();
      
      this.resetReceiver();
   }

   /*
    * Resets the received data, generally called after each sentence is received.
    */
   void resetReceiver () {
      receivedSentence = receivedSentenceServer.getReceivedSentence ();
      errorLog = receivedSentence.errorLog;
      
      startPosition = -1;
      codePosition = -1;
      codeCharacter = 0;
      checksumPosition = -3;
      lineFeed = -1;
      carriageReturn = -1;
      sentenceLength = 0;
      dataFieldCount = 0;
      
      sentRcvdRaw.clear ();
      processedField.clear ();
      sentenceFields.clear ();
      
      proprietary = false;
      querySentence = false;
      
      talkerID = 0;
      queryTalkerID = 0;
      formatter = 0;
      calculatedChecksum = 0;
      receivedChecksum = -1;
      
      startTime = System.currentTimeMillis ();
      stopTime = 0;
   }

   /**
    * Called to receive and process the collection of serial data.
    *
    * @param c The received character.
    */   
   public void receiveCharacter (char c) {
      // Increment sentence character count
      sentenceLength++;
      
      // Build unprocessed sentence, 0x1F is last control character
      if ((c > 0x1F) && (c < DELETE)){
         sentRcvdRaw.append (c);
      }
      else if (c == CARRIAGE_RETURN) {
         sentRcvdRaw.append ("<CR>");
      }
      else if (c == LINE_FEED) {
         sentRcvdRaw.append ("<LF>");
      }
      else if (c == DELETE) {
         sentRcvdRaw.append ("<DEL>");
      }
      else { // Unknown character
         sentRcvdRaw.append ("<?>");
      }

      // Catch sentences that are too long.
      if (sentenceLength == SentenceTypes.MAX_SENTENCE_LENGTH) {
         processedField.append (c);
         sentenceFields.add (processedField.toString ());
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_OVERRUN)); //SENTENCE_OVERRUN
         stopTime = System.currentTimeMillis ();
         this.transmitToParser();
         return;
      }

      // Trigger on checksum delimiter, ASTERISK = '*'
      if (c == ASTERISK) {
         if (checksumPosition < 0) {
            checksumPosition = sentenceLength;
            receivedChecksum = 0;
         }
         else {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_CHARACTER_NOT_VALID,
               "Too many checksum tokens found. ")); //TOO_MANY_CHECKSUMS
         }
         sentenceFields.add (processedField.toString ());
         processedField.clear ();
         return;
      }
      
      // Dynamically calculate checksum, beginning after the start character '$'
      if ((sentenceLength > 1) && (startPosition == 1) && (checksumPosition < 0)) {
         calculatedChecksum ^= (c & 0x00FF);
      }
      
      // Trigger on data field delimiter, COMMA = ','
      if (c == COMMA) { 
         dataFieldCount++;
         sentenceFields.add (processedField.toString ());
         processedField.clear ();
         return;
      }  
      
      // Trigger on address field delimiter, DOLLAR = '$'
      if (c == DOLLAR) {
         if ((sentenceLength == 1) && (startPosition < 1)) {
            startPosition = 1;
            startTime = System.currentTimeMillis ();
         }
         else { // Start character found at an incorrect position
            errorLog.addError (new SentenceError(
               SentenceErrorTypes.ERR_INCORRECT_START, 
               "Found at position " + sentenceLength + ".")); //INCORRECT_START_LOCATION
         }
         processedField.append (c);
         return;
      }
      
      // Trigger on code delimited character, CIRCUMFLEX = '^'.
      if (c == CIRCUMFLEX) {
         if (codePosition > 0) {
            errorLog.addError (new SentenceError(
               SentenceErrorTypes.ERR_CODE_CHAR_ILLEGAL, 
               "Illegal location of '^' escape character.")); //INCORRECT_ESCAPE_LOCATION
         }
         else {
            codePosition = sentenceLength;
            return;
         }
      }
      
      // Process coded characters
      if (codePosition > 0) {
         //    |--  within 0 and 9 --|  OR  |--  within A and F  --|
         if (((c > 0x2F) && (c < 0x3A)) || ((c > 0x40) && (c < 0x47))) {
            if (sentenceLength == (codePosition + 1)) {
               codeCharacter = (Character.getNumericValue (c));
               return;
            }
            if (sentenceLength == (codePosition + 2)) {
               codeCharacter <<= 4;
               codeCharacter ^= (Character.getNumericValue (c));
               c = (char) codeCharacter;
               codePosition = -1;
               processedField.append (c);
               return;
            }
         }
         else {
            errorLog.addError (new SentenceError(
               SentenceErrorTypes.ERR_CODE_CHAR_ILLEGAL, 
               "Code delimited characters can only be hexidecimal digits.",
               c)); // INCORRECT_ESCAPE_CHARACTER
            codePosition = -1;
            processedField.append (c);
            return;
         }
      }
      
      // Trigger the begining of the end of sentence sequence, <CR> = CARRIAGE_RETURN
      if (c == CARRIAGE_RETURN) {
         // Ensure <CR> directly follows checksum if present
         if (checksumPosition > 0) {
            if (sentenceLength != (checksumPosition + 3)) {
               errorLog.addError (new SentenceError(
                  SentenceErrorTypes.ERR_CHARACTER_NOT_VALID, 
                  "Too many characters following checksum character.")); //TOO_MANY_CHECKSUM_CHARACTERS
            }
         }
         else {
            sentenceFields.add (processedField.toString ());
            processedField.clear ();
         }
         
         if (carriageReturn < 0) {
             carriageReturn = sentenceLength;
         }
         else {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_CHARACTER_NOT_VALID,
               "Illegal use of <CR> character.")); //INCORRECT_RETURN_LOCATION
            carriageReturn = sentenceLength;
         }
         return;
      }
      
      // Catch standard end of sentence, <LF> = 0x0A
      if (c == LINE_FEED) {
         if (carriageReturn == (sentenceLength - 1)) {
            stopTime = System.currentTimeMillis ();
            this.transmitToParser();
            return;
         }
         else { // Error: <LF> not directly following <CR>
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_INCORRECT_TERMINATION));
            processedField.append ("<LF>"); //INCORRECT_LINEFEED_LOCATION
            return;
         }
      }
      
      // Address field comes before the first data field
      if (dataFieldCount < 1) {
         
         // Compile address field only if correct start character received
         if (startPosition == 1) {
            
            // Ensure address characters are valid
            if (!(Character.isUpperCase (c) | Character.isDigit (c))){
               errorLog.addError (new SentenceError(
                  SentenceErrorTypes.ERR_CHARACTER_NOT_VALID, 
                  "Address field can only be uppercase characters or digits.",
                  c)); //INCORRECT_ADDRESS_CHARACTER
               processedField.append (c);
               return;
            }

            // Determine if proprietary sentence, 0x50 = 'P'
            if ((sentenceLength == 2) && (c == PROPRIETARY)) {
               proprietary = true;
               talkerID = PROPRIETARY;
            }

            // Determine formatter for proprietary sentences
            if ((sentenceLength > 2) && (proprietary)) {
                  formatter <<= 8;
                  formatter |= c;
            }

            // Determine talkerID
            if ((sentenceLength < 4) && (!proprietary)) {
                  talkerID <<= 8;
                  talkerID |= c;
            }

            // Determine formatter for non-proprietary sentences
            if (((sentenceLength > 3) && (sentenceLength < 7)) &&
               (!proprietary)) {
               // Check for query sentence, QUERY = 'Q'
               if ((sentenceLength == 6) && (c == QUERY)) {
                  queryTalkerID = formatter;
                  formatter = c;
                  querySentence = true;
               }
               else {
                  formatter <<= 8;
                  formatter |= c;
               }
            }
            
            // Check for address sentences of incorrect length
            if ((sentenceLength > 6) && (!proprietary)) {
               errorLog.addError (new SentenceError(
                  SentenceErrorTypes.ERR_ADDRESS_LENGTH_ILLEGAL)); //INCORRECT_ADDRESS_LENGTH
            }
         }
         processedField.append (c);
         return;
      }
      
      // Calculate checksum
      if (checksumPosition > 0) {
         // Dynamically calculate received checksum
         if (sentenceLength > checksumPosition) {
            if ((sentenceLength - checksumPosition) < 3) {
               // Ensure checksum characters are valid
               //   |--  within 0 and 9  --|  OR  |--  within A and F  --|
               if (((c > 0x2F) && (c < 0x3A)) || ((c > 0x40) && (c < 0x47))) {
                  receivedChecksum <<= 4;
                  receivedChecksum ^= (Character.getNumericValue (c));
                  return;
               }
               else {
                  errorLog.addError (new SentenceError(
                     SentenceErrorTypes.ERR_CHARACTER_NOT_VALID, 
                     "Checksum characters can only be hexidecimal digits.",
                     c)); //INCORRECT_CHECKSUM_CHARACTER
                  processedField.append (c);
                  return;
               }
            }
         }
      }
           
      // Process data characters
      if ((c > 0x1F) && (c < 0x80)) {
         for (int i = 0; i < unusedCharCount; i++) {
            if (c == UNUSED_RESERVED_CHAR[i]) {
               errorLog.addError (new SentenceError(
                  SentenceErrorTypes.ERR_RESERVED_CHAR_ILLEGAL, 
                  null,
                  c)); //INCORRECT_RESERVED_CHARACTER
            }
         }
         processedField.append (c);
         return;
      }
      else {
         errorLog.addError (new SentenceError(
            SentenceErrorTypes.ERR_CHARACTER_NOT_VALID, 
            "Character value not in range.",
            c)); //INCORRECT_ASCII_CHARACTER
         processedField.append ("<?>");
         return;
      }
   }

   void transmitToParser () {
      if (SentenceTypes.getTalkerIDName (talkerID) == null) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_TALKERID_UNKNOWN)); //TALKERID_UNKNOWN
      }
      if (SentenceTypes.getFormatterIDName (formatter) == null) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_SENTENCE_TYPE_UNKNOWN)); //SENTENCE_TYPE_UNKNOWN
      }
      // If query sentence, then check requestor talkerID, 0x51 = 'Q'
      if (querySentence) {
         if (SentenceTypes.getTalkerIDName (queryTalkerID) == null) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_TALKERID_UNKNOWN,
               "Query talker ID not recognized.")); //QUERY_TALKERID_UNKNOWN
         }
      }
      if (startPosition < 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_MISSING_START)); //MISSING_START_CHARACTER
      }
      if (dataFieldCount < 1) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE)); //MISSING_DATA_FIELDS
      }
      if (receivedChecksum < 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_CHECKSUM_MISSING)); //MISSING_CHECKSUM
      }
      else {
         if (receivedChecksum != calculatedChecksum) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_CHECKSUM_INCORRECT,
               ("Received = "  + Integer.toHexString (receivedChecksum) + 
                  ", calculated = " + Integer.toHexString (calculatedChecksum) + "."))); //INVALID_CHECKSUM
         }
      }
      if (sentenceLength < SentenceTypes.MIN_SENTENCE_LENGTH) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_UNDERRUN)); //TOO_FEW_CHARACTERS
      }
      if ((stopTime - startTime) > SLOW_DATA_THRESHOLD) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_SLOW)); //SLOW_DATA_RATE
      }
      
      receivedSentence.setInUse (true);
      receivedSentence.talkerID = talkerID;
      receivedSentence.queryTalkerID = queryTalkerID;
      receivedSentence.formatter = formatter;
      receivedSentence.fieldCount = dataFieldCount + 1;
      receivedSentence.calculatedChecksum = calculatedChecksum;
      receivedSentence.receivedChecksum = receivedChecksum;
      receivedSentence.timeToReceive = stopTime - startTime;
      receivedSentence.sentenceReceived = sentRcvdRaw.toString ();
      receivedSentence.sentenceFields.addAll(0,  sentenceFields); // = (ArrayList<String>) sentenceFields.clone (); modified 2012-08-08
      receivedSentence.errorLog = (SentenceErrorLog) errorLog.clone ();
      receivedSentence.timeOfReciept = System.currentTimeMillis ();

      if (super.receivedSentenceListener != null) {
         super.receivedSentenceListener.receiveSentence (receivedSentence);
      }
      this.resetReceiver();
   }
}
/* 
 * 
 */
