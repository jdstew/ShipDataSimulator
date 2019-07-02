/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: RateOfTurnROT.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * ROT - Rate of turn. 
 * Since v2.0
 *<pre>
 * Sentence:
 * $--ROT,x.x,A*hh<CR><LF>
 *         |  |- Status (see note 1)
 *         |---- Rate of turn, degrees per minute, '-' to port
 * Notes:
 * (1) Mode indicator (can not be null):
 *     A - valid
 *     V - invalid
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class RateOfTurnROT extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 19;
   final static int MIN_FIELD_COUNT = 3;
   final static int MAX_FIELD_COUNT = 3; 

   final static int [] validTalkerID = {
      SentenceTypes.TALKER_HC,
      SentenceTypes.TALKER_HN,
      SentenceTypes.TALKER_HE
   };
   final static int talkerCount = validTalkerID.length;
      
   /**
    * Get this sentence's estimated length in bytes.
    *
    * @return Estimated sentence length in bytes.
    */
   public int getEstimatedLength () {
      return ESTIMATED_SENTENCE_LENGTH;
   }
   
   /**
    * Get the sentence formatter ID.
    *
    * @return Sentence formatter ID from SentenceTypes class.
    */
   public int getSentenceFormatter () {
      return SentenceTypes.FORMATTER_HDT;
   }
   
   /**
    * Validates an acceptable talker ID for this sentence formatter.
    * 
    * @param id Talker ID to validate.
    * @return True if valide talker ID.
    */
   public boolean isSentenceIDValid (int id) {
      for (int i = 0; i < talkerCount; i++) {
         if (id == validTalkerID[i]) {
            return true;
         }
      }
      return false;
   }
      
   /**
    * Get sentence based upon simulator data.  Manual data is inserted where
    * simulated data is unavailable.
    *
    * @return Valid simulation-based sentence.
    */
   public String getSimulatorSentence () {
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
         String sentence = super.getTalkerID () + "ROT";
         sentence += ',' + super.dataFactory.getSimulatedRateOfTurn () + 
                     ',' + super.dataFactory.getRateOfTurnStatus ();

         // Add checksum if required if not previously required
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT10) {
            sentence += '*' + SentenceTools.getChecksum(sentence);
         }

         return new String('$' + sentence + SentenceTools.CR + SentenceTools.LF);
      }
      else {
         return null;
      }
   }
   
   /**
    * Get sentence based upon manual data.
    *
    * @return Valid simulation-based sentence.
    */
   public String getManualSentence () {
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
         String sentence = super.getTalkerID () + "ROT";
         sentence += ',' + super.dataFactory.getRateOfTurn () + 
                     ',' + super.dataFactory.getRateOfTurnStatus ();

         // Add checksum if required if not previously required
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT10) {
            sentence += '*' + SentenceTools.getChecksum(sentence);
         }

         return new String('$' + sentence + SentenceTools.CR + SentenceTools.LF);
      }
      else {
         return null;
      }
   }
   
   /**
    * Get test sentence data. Note, the last digit of select values within
    * the sentence is random.
    *
    * @return Valid test sentence.
    */
   public String getRandomSentence () {
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
         int testVal = SentenceTools.getRandomDigit ();
         String sentence = "HEROT" +
                           ",0." + testVal + 
                           ",A";

         // Add checksum if required if not previously required
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT10) {
            sentence += '*' + SentenceTools.getChecksum(sentence);
         }
      
         return new String('$' + sentence + SentenceTools.CR + SentenceTools.LF);
      }
      else {
         return null;
      }
   }
      
   /**
    * Process a received sentence.
    *
    * @param receivedSentence A ReceivedSentence.
    */
   public void processReceivedSentence (ReceivedSentence receivedSentence) {
      if (receivedSentence == null) {
         return;
      }
      ArrayList<String> fields = receivedSentence.sentenceFields;
      int fieldCount = fields.size ();
      SentenceErrorLog errorLog = receivedSentence.errorLog;
      String testString;
     
      // Check for valid talkerID
      if (!this.isSentenceIDValid (receivedSentence.talkerID)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_TALKERID_ILLEGAL));
         return;
      }
      
      // Check for minimum number of fields
      if (fieldCount < RateOfTurnROT.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > RateOfTurnROT.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }
      
      testString = (String) fields.get(1);
      double rateOfTurn = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if (!Double.isNaN (rateOfTurn)) {
         if ((rateOfTurn < -SentenceData.MAX_ROT) ||
             (rateOfTurn > SentenceData.MAX_ROT)) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Rate of turn out of range.")));
         }
         else {
            dashboardChannel.setRateOfTurn ((float)rateOfTurn);
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Rate of turn value can not be null or contain non-numerals.")));
      }
      
      // Test Status
      testString = (String) fields.get(2);
      char status = SentenceTools.parseStatus (testString.toCharArray (), errorLog);
      
      // Test status field for validity
      if (status == 'V') {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID,
            new String ("Status set to invalid.")));
      }
      
   }
}
/*
 * Revision History:
 *
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 * 1.3.0.0 - Corrected error in calling incorrect variable in OwnshipUpdate
 */