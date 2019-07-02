/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: NMEA0182Transceiver.java
 * Created: 2005-02-02, 12:01:01
 */
package serialComms.serialInterface.transceivers;

import java.util.*;
import java.text.*;
import javax.comm.*;
import serialComms.*;
import serialComms.serialInterface.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
//import data.*;
/**
 * This class both receieves and transmits serial data, and performs 
 * common preprocessing of sentence data for NMEA 0180 (simple) and
 * NMEA 0182 (complex) data.
 *
 * @author Jeff Stewart
 * @version 1.1.0.0, 2005-02-02
 */
public class NMEA0182Transceiver extends AbstractTransceiver {
   public final static int MAX_SENTENCE_LENGTH = 82;
   public final static char NULL = 0x00;
   public final static char ETX = 0x03;
   public final static char DOLLAR = 0x24;
   public final static char CARRIAGE_RETURN = 0x0D;
   public final static char LINE_FEED = 0x0A;
     
    
   SerialChannelIO serialChannelIO;
   
   ReceivedSentenceServer receivedSentenceServer;
   ReceivedSentence receivedSentence;
   SentenceErrorLog errorLog;
     
   CharacterBuffer sentRcvdRaw;
   CharacterBuffer processedField;
   ArrayList<String> sentenceFields;
   
   int formatter;
   
   boolean receivingComplexData; // implies start characte received
   int complexDataLength;
   
   long startTime;
   long stopTime;
   
   DecimalFormat shortForm = new DecimalFormat("0.00");
   
   /**
    * Instantiates a SentenceTransceiver object.
    *
    * @param serialPort The serial port object.
    * @param dbgMgr The debug manager object for report errors to.
    */   
   public NMEA0182Transceiver (SerialPort serialPort) {
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
           
      sentRcvdRaw.clear ();
      processedField.clear ();
      sentenceFields.clear ();
      
      formatter = 0;
      
      receivingComplexData = false; // put in reset();
      complexDataLength = 0;
     
      startTime = System.currentTimeMillis ();
      stopTime = 0;
   }

   /**
    * Called to receive and process the collection of serial data.
    *
    * @param c The received character.
    */   
   public void receiveCharacter (char c) {
      if (c >= 0x80) {
         receiveNMEA0182Character  ((char) ((int)c & 0x7F));
      }
      else {
         receiveNMEA0180Character (c);
      }
   }
   
   /**
    * Called to receive and process NMEA 0180 characters.
    *
    * @param c The received character.
    */   
   void receiveNMEA0180Character (char c) {     
      int charVal = (int) c;
      
      // check for simple data within complex data
      if (receivingComplexData) {
         errorLog.addError (new SentenceError(
               SentenceErrorTypes.ERR_INCORRECT_TERMINATION, 
               "Simple data detected within complex data."));
         formatter = SentenceTypes.FORMATTER_NMEA0182;
         sentenceFields.add (processedField.toString ());
         stopTime = System.currentTimeMillis ();
         transmitToParser();
      }
           
      // convert hex character value to binary text
      for (int i = 0x80; i > 0; i /= 2) {
         if ((charVal & i) > 0) {
            sentRcvdRaw.append ("1 ");
         }
         else {
            sentRcvdRaw.append ("0 ");
         }
      }
      sentRcvdRaw.append ("[binary] ");
      
      // compute cross track error (xte) value if valid bit is set
      boolean valid = ((charVal & 0x40) > 0);
      if (valid) {
         float xte = ((charVal & 0x3F) - 32) * 0.010000f;
         if (xte < 0) {
            sentRcvdRaw.append ("Right of track by " + 
               shortForm.format (Math.abs (xte)) + "NM");
         }
         else {
            sentRcvdRaw.append ("Left of track by " + 
               shortForm.format (xte) + "NM");
         }
      }
      else {
         sentRcvdRaw.append("Invalid!");
      }
      
      // package data for transmission
      sentenceFields.add (Character.toString (c));
      formatter = SentenceTypes.FORMATTER_NMEA0180;
      stopTime = System.currentTimeMillis ();
      transmitToParser ();
   }
   
   /**
    * Called to receive and process NMEA 0182 characters.
    *
    * @param c The received character.
    */   
   public void receiveNMEA0182Character (char c) {
      // Increment sentence character count
      complexDataLength++;
      
      // Build unprocessed sentence, 0x1F is last control character
      if ((c > 0x1F) && (c < 0x80)){
         sentRcvdRaw.append (c);
      }
      else if (c == ETX) {
         sentRcvdRaw.append ("<ETX>");
      }
      else if (c == NULL) {
         sentRcvdRaw.append ("<null>");
      }
      else { // Unknown character
         sentRcvdRaw.append ("<?>");
         errorLog.addError (new SentenceError(
            SentenceErrorTypes.ERR_CHARACTER_NOT_VALID, 
            "Character value not in range.",
            c)); //INCORRECT_ASCII_CHARACTER
      }
      
      // Catch sentences that are too long.
      if (complexDataLength > MAX_SENTENCE_LENGTH) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_OVERRUN)); //SENTENCE_OVERRUN
         formatter = SentenceTypes.FORMATTER_NMEA0182;
         processedField.append (c);
         sentenceFields.add (processedField.toString ());
         stopTime = System.currentTimeMillis ();
         transmitToParser();
         return;
      }

      // check for complex character before start character
      // the first complex character must be a start character
      if ( (!receivingComplexData) && (c != DOLLAR) ) {
         errorLog.addError (new SentenceError(
               SentenceErrorTypes.ERR_INCORRECT_START, 
               "Missing sentence start character ($)."));
         formatter = SentenceTypes.FORMATTER_NMEA0182;
         processedField.append (c);
         sentenceFields.add (processedField.toString ());
         stopTime = System.currentTimeMillis ();
         transmitToParser();
         return;
      }
      
      // check for complex character preceeding a start character
      if (c == DOLLAR) {
         if (receivingComplexData) {
            errorLog.addError (new SentenceError(
                  SentenceErrorTypes.ERR_INCORRECT_START, 
                  "Found at position " + complexDataLength + "."));
            formatter = SentenceTypes.FORMATTER_NMEA0182;
            processedField.append (c);
            sentenceFields.add (processedField.toString ());
            stopTime = System.currentTimeMillis ();
            transmitToParser();
            return;
         }
         else {
            receivingComplexData = true;
            processedField.append (c);
            return;
         }
      }
      
      
      // transmit on end character <etx>
      if (c == ETX) {
         formatter = SentenceTypes.FORMATTER_NMEA0182;
         processedField.append (c);
         sentenceFields.add (processedField.toString ());
         stopTime = System.currentTimeMillis ();
         transmitToParser();
         return;
      }
      
      processedField.append (c);
   }
   
   CharacterBuffer appendASCII (char c, CharacterBuffer charBuff) {
      if ((c > 0x1F) && (c < 0x80)) {
         charBuff.append ((char) 0x0027);
         charBuff.append(c);
         charBuff.append ((char) 0x0027);
         charBuff.append (' ');
      }
      else {
         charBuff.append("<?> ");
      }
      return charBuff;
   }
   
   CharacterBuffer appendHex (char c, CharacterBuffer charBuff) {
      charBuff.append ('{');
      charBuff.append(Integer.toHexString ((int) c));
      charBuff.append ('}');
      charBuff.append (' ');
      return charBuff;
   }
   
   CharacterBuffer appendBinary (char c, CharacterBuffer charBuff) {
      
      charBuff.append ("[");
      
      // convert hex character value to binary text
      int charVal = (int) c;
      for (int i = 0x80; i > 0; i /= 2) {
         if ((charVal & i) > 0) {
            charBuff.append ("1 ");
         }
         else {
            charBuff.append ("0 ");
         }
      }
      charBuff.append ("] ");

      return charBuff;
   }
   
   void transmitToParser () {
      if (SentenceTypes.getFormatterIDName (formatter) == null) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_SENTENCE_TYPE_UNKNOWN)); //SENTENCE_TYPE_UNKNOWN
      }
      
      receivedSentence.setInUse (true);
      receivedSentence.talkerID = 0;
      receivedSentence.queryTalkerID = 0;
      receivedSentence.formatter = formatter;
      receivedSentence.fieldCount = 1;
      receivedSentence.calculatedChecksum = 0;
      receivedSentence.receivedChecksum = 0;
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
