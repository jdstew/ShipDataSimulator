/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: RayNav750Transceiver.java
 * Created: 2005-02-10, 12:01:01
 */
package serialComms.serialInterface.transceivers;

import java.util.*;
import java.text.*;
import javax.comm.*;
import serialComms.*;
import serialComms.serialInterface.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * This class both receieves and transmits serial data from and to
 * a RayNav750 collor plotter and remote.
 *
 * @author Jeff Stewart
 * @version 1.2.0.0, 2005-02-10
 */
public class RayNav750Transceiver extends AbstractTransceiver {
   public final static int ABSOLUTE_SENTENCE_LENGTH = 131;
 
   public final static char START_CHARACTER = 0x0050; // 'P'
   public final static char END_CHARACTER = 0x005A; // 'Z'
   
   public final static char SPECIAL_CHAR_MASK = 0x007F;
   public final static char CHAR_VAL_TO_ASCII = 0x0030;
          
   SerialChannelIO serialChannelIO;
   
   ReceivedSentenceServer receivedSentenceServer;
   ReceivedSentence receivedSentence;
   SentenceErrorLog errorLog;
     
   CharacterBuffer sentRcvdRaw;
   CharacterBuffer processedField;
   ArrayList<String> sentenceFields;
   
   int sentenceLength;
   boolean receivingSentence;
   
   long startTime;
   long stopTime;
   
   DecimalFormat shortForm = new DecimalFormat("0.00");
   
   /**
    * Instantiates a SentenceTransceiver object.
    *
    * @param serialPort The serial port object.
    * @param dbgMgr The debug manager object for report errors to.
    */   
   public RayNav750Transceiver (SerialPort serialPort) {
      super(serialPort);
      
      receivedSentenceServer = new ReceivedSentenceServer ();
      sentRcvdRaw = new CharacterBuffer (256);
      processedField = new CharacterBuffer (256);
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
          
      sentenceLength = 0;
      receivingSentence = false;
     
      startTime = System.currentTimeMillis ();
      stopTime = 0;
   }

   /**
    * Called to receive and process the collection of serial data.
    *
    * @param c The received character.
    */   
   public void receiveCharacter (char c) {
      sentenceLength++;
      
      // Build unprocessed sentence
      char charVal = 0;
      if (c < 10) {
         charVal = (char) (c + CHAR_VAL_TO_ASCII);
         sentRcvdRaw.append (charVal);
      }
      else if (c > 0x0080) {
         charVal = (char) (c & SPECIAL_CHAR_MASK);
         sentRcvdRaw.append (charVal);
      }
      else if ((c > 0x001F) && (c < 0x0080)){
         sentRcvdRaw.append (c);
      }
      else {
         sentRcvdRaw.append ("[" + Integer.toHexString ((int) c) + "_hex]");
      }
      
      // Catch sentences that are too long.
      if (sentenceLength > ABSOLUTE_SENTENCE_LENGTH) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_OVERRUN)); //SENTENCE_OVERRUN
         processedField.append (charVal);
         sentenceFields.add (processedField.toString ());
         stopTime = System.currentTimeMillis ();
         transmitToParser();
         return;
      }
      
      // Catch data received outside start and stop characters
      if ( (!receivingSentence) && (charVal != START_CHARACTER) ) {
         errorLog.addError (new SentenceError(
               SentenceErrorTypes.ERR_INCORRECT_START, 
               "Missing sentence start character (P)."));
         processedField.append (charVal);
         sentenceFields.add (processedField.toString ());
         stopTime = System.currentTimeMillis ();
         transmitToParser();
         return;
      }
      
      // Flag start of sentence
      if (charVal == START_CHARACTER) {
         receivingSentence = true;
      }
      
      // Catch correct end of sentence
      if ( (charVal == END_CHARACTER) && (sentenceLength == ABSOLUTE_SENTENCE_LENGTH) ) {
         processedField.append (charVal);
         sentenceFields.add (processedField.toString ());
         stopTime = System.currentTimeMillis ();
         transmitToParser();
         return;
      }
      
      processedField.append (c);
   }

   
   void transmitToParser () {     
      receivedSentence.setInUse (true);
      receivedSentence.talkerID = 0;
      receivedSentence.queryTalkerID = 0;
      receivedSentence.formatter = SentenceTypes.FORMATTER_RAYNAV750;
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
