/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: PositionGLL.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * GLL - Geographic position - latitude and longitude. 
 * Since v1.5, time a status added in v2.0, mode added in v2.3
 *<pre>
 * Sentence:
 * $--GLL,llll.ll,a,yyyyy.yy,a,hhmmss.ss,A,a*hh<CR><LF>
 *           |    |    |     |     |     | |- Mode (see note 1)
 *           |    |    |     |     |     |- Status (see note 2)
 *           |    |    |     |     |- UTC of position     
 *           |    |    |-----|- Longitude, 'E' or 'W'
 *           |----|- Latitude, 'N' or 'S'
 *            
 * Notes:
 * (1) Positioning mode (can not be null):
 *     A = Autonomous
 *     D = Differential
 *     E = Estimated (dead reckoning) mode
 *     M = Manual input
 *     S = Simulator
 *     N = Data not valid
 * (2) Mode indicator (can not be null):
 *     A (valid) when positioning mode is 'A' or 'D'
 *     V (invalid) when positioning mode is 'E', 'M', 'S', or 'N'
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class PositionGLL extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 53;
   final static int MIN_FIELD_COUNT = 5;
   final static int MAX_FIELD_COUNT = 8;   

   final static int [] validTalkerID = {
      SentenceTypes.TALKER_LC,
      SentenceTypes.TALKER_GN,
      SentenceTypes.TALKER_GP,
      SentenceTypes.TALKER_IN,
      SentenceTypes.TALKER_SN
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
      return SentenceTypes.FORMATTER_GLL;
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
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_1PT50) {
         String sentence = super.getTalkerID () + "GLL";
         sentence += ',' + super.dataFactory.getSimulatedLatitude () + 
                     ',' + super.dataFactory.getSimulatedLatitudeHemisphere () + 
                     ',' + super.dataFactory.getSimulatedLongitude () + 
                     ',' + super.dataFactory.getSimulatedLongitudeHemisphere ();

         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
            sentence += ',' + super.dataFactory.getSimulatedUTC () +
                        ',' + super.dataFactory.getPositionStatusID ();
         }

         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence +=  ',' + super.dataFactory.getPositionModeID ();
         }

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
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_1PT50) {
         String sentence = super.getTalkerID () + "GLL";
         sentence += ',' + super.dataFactory.getLatitude () + 
                     ',' + super.dataFactory.getLatitudeHemisphere () + 
                     ',' + super.dataFactory.getLongitude () + 
                     ',' + super.dataFactory.getLongitudeHemisphere ();

         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
            sentence += ',' + super.dataFactory.getSimulatedUTC () +
                        ',' + super.dataFactory.getPositionStatusID ();
         }

         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence +=  ',' + super.dataFactory.getPositionModeID ();
         }

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
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_1PT50) {
         int testVal = SentenceTools.getRandomDigit ();
         String sentence = "GNGLL";
         sentence += ",3745.500" + testVal + ",N" +
                     ",12241.600" + testVal + ",W";

         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
            sentence += ",131415.1" + testVal + ",A";
         }

         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence +=  ",D";
         }

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
      if (fieldCount < PositionGLL.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > PositionGLL.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }
      
      // Test latitude
      testString = (String) fields.get(1);
      double latitude = SentenceTools.parseLatitude (testString.toCharArray (), errorLog);
      if (latitude < 0.01) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_SUSPICOUS,
            new String ("Check latitude value.")));
      }
         
      // Test latitude hemisphere     testString = (String) fields.get(2);
      testString = (String) fields.get(2);
      SentenceTools.parseLatitudeHemisphere (testString.toCharArray (), errorLog);

      // Test longitude
      testString = (String) fields.get(3);
      double longitude = SentenceTools.parseLongitude (testString.toCharArray (), errorLog);
      if (longitude < 0.01) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_SUSPICOUS,
            new String ("Check longitude value.")));
      }
      
      // Test longitude hemisphere
      testString = (String) fields.get(4);
      SentenceTools.parseLongitudeHemisphere (testString.toCharArray (), errorLog);
      
      // Test for version 1.5
      if (fieldCount == 5) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Sentence appears to be only v1.5 compliant.")));
         return;
      }
      
      // If field count > 5, then it has to be at least 7, else error
      // Sentence version is at least 2.0 when fields 6 & 7 are present
      if ((fieldCount > 5) && (fieldCount < 7)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence is missing fields.")));
         return;
      }
      
      // Test UTC
      testString = (String) fields.get(5);
      long time = SentenceTools.parseUTC (testString.toCharArray (), errorLog);
      if (time == 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_SUSPICOUS,
            new String ("Check UTC value.")));
      }
      
      // Test Status
      testString = (String) fields.get(6);
      char status = SentenceTools.parseStatus (testString.toCharArray (), errorLog);
      
      // Test status field for validity
      if (status == 'V') {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID,
            new String ("Status set to invalid.")));
      }
      
      // Test for version 2.0
      if (fieldCount == 7) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Sentence appears to be only v2.0 compliant.")));
         return;
      }
            
      // Test Mode
      testString = (String) fields.get(7);
      char mode = SentenceTools.parseOperatingMode (testString.toCharArray (), errorLog);
      
      // Test Status against Mode
      if ((status == 'A') != ((mode == 'A') || (mode == 'D'))) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ILLOGICAL,
            new String ("Status and operating mode incongruent.")));
      }

      // Test mode field for validity
      if ((mode == 'E') ||
          (mode == 'M') ||
          (mode == 'S') ||
          (mode == 'N')) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID,
            new String ("Operating mode set to invalid.")));
      }
   }
}
/*
 * Revision History:
 *
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */