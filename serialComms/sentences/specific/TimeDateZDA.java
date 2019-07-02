/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: TimeDateZDA.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import java.text.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * ZDA - Time and date. 
 * Since v1.5, local zone minutes added in version 2.3
 *<pre>
 * Sentence:
 * $--ZDA,hhmmss.ss,xx,xx,xxxx,xx,xx*hh<CR><LF>
 *            |     |  |   |   |  |- Local zone minutes, 00 to 59
 *            |     |  |   |   |- Local zone hours, -13 to +13
 *            |     |  |   |- Year
 *            |     |  |- Month, 01 to 12
 *            |     |- Day, 01 to 31
 *            |- UTC
 *
 * Notes:
 * (1) UTC = local-time + zone-sign|zone-hours + zone-minutes|
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class TimeDateZDA extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 38;
   final static int MIN_FIELD_COUNT = 6;
   final static int MAX_FIELD_COUNT = 7;   

   final static int [] validTalkerID = {
      SentenceTypes.TALKER_EC,
      SentenceTypes.TALKER_EI,
      SentenceTypes.TALKER_LC,
      SentenceTypes.TALKER_GN,
      SentenceTypes.TALKER_GP,
      SentenceTypes.TALKER_II,
      SentenceTypes.TALKER_IN,
      SentenceTypes.TALKER_SN,
      SentenceTypes.TALKER_ZA,
      SentenceTypes.TALKER_ZC,
      SentenceTypes.TALKER_ZQ,
      SentenceTypes.TALKER_ZV
   };
   final static int talkerCount = validTalkerID.length;
   
   DecimalFormat twoDigitForm = new DecimalFormat("00");
   DecimalFormat fourDigitForm = new DecimalFormat("00");
      
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
      return SentenceTypes.FORMATTER_ZDA;
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
      String sentence = super.getTalkerID () + "ZDA";
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_1PT50) {
         sentence += ',' + super.dataFactory.getSimulatedUTC () +
                     ',' + super.dataFactory.getSimulatedDay () +
                     ',' + super.dataFactory.getSimulatedMonth () +
                     ',' + super.dataFactory.getSimulatedYear () +
                     ',' + super.dataFactory.getZoneHours ();

         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence += ',' + super.dataFactory.getZoneMinutes ();
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
         String sentence = super.getTalkerID () + "ZDA";
         sentence += ',' + super.dataFactory.getTimeUTC () +
                     ',' + super.dataFactory.getDateDay () +
                     ',' + super.dataFactory.getDateMonth () +
                     ',' + super.dataFactory.getDateYear () +
                     ',' + super.dataFactory.getZoneHours ();
      
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence += ',' + super.dataFactory.getZoneMinutes ();
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
   public String getRandomSentence() {
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_1PT50) {
         int testVal = SentenceTools.getRandomDigit ();
         String sentence = "GPZDA";
         sentence += ",131415.1" + testVal +
                     ",0" + testVal +
                     ",08,2004,05";

         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence += ",01";
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
    * @param receivedSentence A receivedSentence.
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
      if (fieldCount < TimeDateZDA.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > TimeDateZDA.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }
      
      // Test UTC
      testString = (String) fields.get(1);
      long time = SentenceTools.parseUTC (testString.toCharArray (), errorLog);
      if (time == 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_SUSPICOUS,
            new String ("Check UTC value.")));
      }
      
      // Test day value
      testString = (String) fields.get(2);
      if (testString.length () == 2) {
         double day = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
         if ((day < 1.0) || (day > 31.0)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ERRONEOUS,
               new String ("Illegal day value.")));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Day value has incorrect number of characters.")));
      }
      
      // Test month value
      testString = (String) fields.get(3);
      if (testString.length () == 2) {
         double month = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
         if ((month < 1.0) || (month > 12)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ERRONEOUS,
               new String ("Illegal month value.")));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Month value has incorrect number of characters.")));
      }
      
      // Test year value
      testString = (String) fields.get(4);
      if (testString.length () == 4) {
         double year = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
         if ((year < 2004.0) || (year > 2020.0)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ERRONEOUS,
               new String ("Illegal year value.")));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Year value has incorrect number of characters.")));
      }
      
      // Test local zone hours value
      testString = (String) fields.get(5);
      double zoneHour = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if (Double.isNaN (zoneHour)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Zone minutes can not be null or contain non-numerals.")));
      }
      else {
         if ((zoneHour < 0.0) || (zoneHour > 59.0)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ERRONEOUS,
               new String ("Illegal zone minutes value.")));
         }
      }

      // Test for version 1.5
      if (fieldCount == 6) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Sentence appears to be only v1.5 compliant.")));
         return;
      }
      
      // Test local zone minutes value
      testString = (String) fields.get(6);
      double zoneMinute = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if (Double.isNaN (zoneMinute)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Zone minutes can not be null or contain non-numerals.")));
      }
      else {
         if ((zoneMinute < 0.0) || (zoneMinute > 59.0)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ERRONEOUS,
               new String ("Illegal zone minutes value.")));
         }
      }
   }
}
/*
 * Revision History:
 *
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */