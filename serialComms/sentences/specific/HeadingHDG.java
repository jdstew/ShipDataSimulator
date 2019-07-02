/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: HeadingHDG.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * HDG - Magnetic heading (sensed), deviation, and variation. 
 * Since v2.0
 * <pre>
 * Sentence:
 * $--HDG,x.x,x.x,a,x.x,a*hh<CR><LF>
 *         |   |  |  |--|- Magnetic variation, degrees E/W
 *         |   |--|- Magnetic deviation, degrees E/W
 *         |- Magnetic sensor heading, degrees
 *
 * Notes:
 * (1) Magnetic heading = sensor + East-deviation - West-deviation.
 * (2) True heading = magnetic heading + East-variation - West-variation
 * (3) Null values in deviation and variation means unknown.
 * </pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class HeadingHDG extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 29;
   final static int MIN_FIELD_COUNT = 5;
   final static int MAX_FIELD_COUNT = 8;

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
      return SentenceTypes.FORMATTER_HDG;
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
      return getManualSentence (); // Temporary until processing is completed.
   }
   
   /**
    * Get sentence based upon manual data.
    *
    * @return Valid simulation-based sentence.
    */
   public String getManualSentence () {
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
         String sentence = super.getTalkerID () + "HDG";
         sentence += ',' + super.dataFactory.getHeadingMagneticWater () + 
                     ',' + super.dataFactory.getDeviation () + 
                     ',' + super.dataFactory.getDeviationDirection () + 
                     ',' + super.dataFactory.getVariation () +
                     ',' + super.dataFactory.getVariationDirection ();

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
         String sentence = "HCHDG";
         sentence += ",095." + testVal + 
                     ",0.5,W,4.5,W";

         // Add checksum if required if not previously required
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT10) {
            sentence += '*' + SentenceTools.getChecksum(sentence);
         }

         return new String('$' + sentence + SentenceTools.CR + SentenceTools.LF);
      }
      else {
         return null;
      }   }
     
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
      if (fieldCount < HeadingHDG.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > HeadingHDG.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }
      
      // Test magnetic heading
      testString = (String) fields.get(1);
      double magHdg = SentenceTools.parseBearing (testString.toCharArray (), errorLog);
      if (Double.isNaN (magHdg)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Magnetic heading can not be null or contain non-numerals.")));
      }
      
      // Test deviation value
      testString = (String) fields.get(2);
      double deviation = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if (!Double.isNaN (deviation)) {
         if ((deviation < -SentenceData.MAX_DEVIATION) ||
             (deviation > SentenceData.MAX_DEVIATION)) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Deviation out of range.")));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Deviation can not be null or contain non-numerals.")));
      }
      
      // Test deviation direction
      testString = (String) fields.get(3);
      if (testString.length () == 1) {
         char character = testString.charAt (0);
         if (!((character == 'E') || (character == 'W'))) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Deviation field 'E' or 'W' not recognzed."), character));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Deviation field has incorrect number of characters.")));
      }
      
      // Test variation value
      testString = (String) fields.get(4);
      double variation = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if (!Double.isNaN (variation)) {
         if ((variation < -SentenceData.MAX_VARIATION) ||
             (variation > SentenceData.MAX_VARIATION)) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Variation out of range.")));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Variation can not be null or contain non-numerals.")));
      }
      
      // Test variation direction
      testString = (String) fields.get(5);
      if (testString.length () == 1) {
         char character = testString.charAt (0);
         if (!((character == 'E') || (character == 'W'))) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Deviation field 'E' or 'W' not recognzed."), character));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Deviation field has incorrect number of characters.")));
      }
   }
}
/*
 * Revision History:
 *
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */