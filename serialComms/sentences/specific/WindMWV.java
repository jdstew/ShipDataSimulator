/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: WindMWV.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * MWV - Wind speed and angle. 
 * Since 2.0, 'S' units added in v3.0
 *<pre>
 * Sentence:
 * $--MWV,x.x,a,x.x,a,A*hh<CR><LF>
 *         |  |  |  | |- Status, A = valid, V = invalid
 *         |  |  |  |- Wind speed units ('K', 'M', 'N', 'S')
 *         |  |  |- Wind speed
 *         |  |- Wind direction reference ('R', 'T')
 *         |- Wind direction angle (0-359 degrees)
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class WindMWV extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 29;
   final static int MIN_FIELD_COUNT = 6;
   final static int MAX_FIELD_COUNT = 6;   

   final static int [] validTalkerID = {
      SentenceTypes.TALKER_WI,
      SentenceTypes.TALKER_II
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
      return SentenceTypes.FORMATTER_MWV;
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
      return getManualSentence(); 
   }
   
   /**
    * Get sentence based upon manual data.
    *
    * @return Valid simulation-based sentence.
    */
   public String getManualSentence () {
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
         String sentence = super.getTalkerID () + "MWV";
         sentence += ',' + super.dataFactory.getWindAngle () + 
                     ',' + super.dataFactory.getWindReference () + 
                     ',' + super.dataFactory.getWindSpeed () + 
                     ',' + super.dataFactory.getWindUnits () + 
                     ',' + super.dataFactory.getWindStatus ();

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
   public String getRandomSentence() {
      int testVal = SentenceTools.getRandomDigit ();
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_1PT50) {
         String sentence = "WIMWV,045." + testVal +
                     ",R,18." + testVal +
                     ",N,A";

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
      if (fieldCount < WindMWV.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > WindMWV.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }
      
      // Test wind direction angle (0-359 degrees) 
      testString = (String) fields.get(1);
      if (testString.length () > 0) {
         SentenceTools.parseBearing (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_MISSING));
      }
      
      // Test wind direction reference ('R', 'T')
      testString = (String) fields.get(2);
      if (testString.length() == 1) {
         char mode = testString.charAt (0);
         if ((mode == 'R') || 
             (mode == 'T')) {
         }
         else {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Wind direction reference not recognzed."), mode));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Wind direction reference field has incorrect number of characters.")));
      }
      
      // Test wind speed 
      testString = (String) fields.get(3);
      double speedKnots = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if (!Double.isNaN (speedKnots)) {
         if ((speedKnots < 0.0) ||
             (speedKnots > SentenceData.MAX_WIND_SPEED)) {
                if (speedKnots != Double.NaN) {
                   errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                     new String ("Wind speed out of range.")));
                }
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                     new String ("Wind speed is either null or has invalide characters.")));
      }
      
      // Test wind speed units ('K', 'M', 'N', 'S')
      testString = (String) fields.get(4);
      if (testString.length() == 1) {
         char mode = testString.charAt (0);
         if ((mode == 'K') || 
             (mode == 'M') ||
             (mode == 'N') ||
             (mode == 'S')) {
         }
         else {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Wind speed units not recognzed."), mode));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Wind speed units field has incorrect number of characters.")));
      }
      
      // Test status, A = valid, V = invalid
      testString = (String) fields.get(5);
      char validity = SentenceTools.parseStatus (testString.toCharArray (), errorLog);
      if (validity == 'V') {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID,
            new String ("Status set to invalid.")));
      }
   }
}
/*
 * Revision History:
 * 1.3.0.1 - Changed simulted to output manual data (vice random).
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */