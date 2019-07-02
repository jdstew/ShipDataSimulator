/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: PositionGGA.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * GGA - Global Positioning System (GPS) fix data. 
 * Since v1.5, differential station and age of corrections added in v2.0
 *<pre>
 * Sentence:
 *                         DGPS reference station ID, 0000-1023 -|
 *                                Age of DGPS data, seconds -|   |
 *                           Geoidal separation, meters -|   |   |
 *                               Altitude, meters -|--|  |   |   |
 *    HDOP (Horizontal Dilution of Precision) -|   |  |  |   |   |
 * $--GGA,hhmmss.ss,llll.ll,a,yyyyy.yy,a,x,xx,x.x,x.x,M,x.x,x.x,xxxx*hh<CR><LF>
 *            |        |    |    |     | |  |- Number of satellites used, 00-12
 *            |        |    |    |     | |- GPS quality indicator (see note 1)
 *            |        |    |    |-----|- Longitude, 'E' or 'W'
 *            |        |----|- Latitude, 'N' or 'S'
 *            |- UTC of position
 *
 * Notes:
 * (1) GGA only applies to GPS and DGPS receivers
 * (2) Altitude is from mean-sea-level
 * (3) GPS Quality Indicator (can not be null):
 *     0 = Fix not available or invalid
 *     1 = GPS SPS mode, fix valid
 *     2 = Differential GPS, SPS mode, fix valid
 *     3 = GPS PPS mode, fix valid
 *     4 = Real Time Kinematic (RTK) (fixed integers)
 *     5 = Float RTK (floating integers)
 *     6 = Estimated (dead reckoning) mode
 *     7 = Manual input mode
 *     8 = Simulator mode
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class PositionGGA extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 82;
   final static int MIN_FIELD_COUNT = 13;
   final static int MAX_FIELD_COUNT = 15;   

   final static int [] validTalkerID = {
      SentenceTypes.TALKER_GP
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
      return SentenceTypes.FORMATTER_GGA;
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
         String sentence = super.getTalkerID () + "GGA";
         sentence += ',' + super.dataFactory.getSimulatedUTC () +
                     ',' + super.dataFactory.getSimulatedLatitude () +
                     ',' + super.dataFactory.getSimulatedLatitudeHemisphere () +
                     ',' + super.dataFactory.getSimulatedLongitude () +
                     ',' + super.dataFactory.getSimulatedLongitudeHemisphere () +
                     ',' + super.dataFactory.getGPSQualityId () + 
                     ',' + super.dataFactory.getGPSNoOfSatellites () +
                     ',' + super.dataFactory.getGPSHDOP () +
                     ',' + super.dataFactory.getGPSAltitude () + ",M" +
                     ',' + super.dataFactory.getGPSGeoidalSeparation () + ",M";
         
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
            sentence += ',' + super.dataFactory.getGPSAgeOfDGPS () + 
                        ',' + super.dataFactory.getGPSDifferentialStation ();
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
         String sentence = super.getTalkerID () + "GGA";
         sentence += ',' + super.dataFactory.getTimeUTC () +
                     ',' + super.dataFactory.getLatitude () +
                     ',' + super.dataFactory.getLatitudeHemisphere () +
                     ',' + super.dataFactory.getLongitude () +
                     ',' + super.dataFactory.getLongitudeHemisphere () +
                     ',' + super.dataFactory.getGPSQualityId () + 
                     ',' + super.dataFactory.getGPSNoOfSatellites () +
                     ',' + super.dataFactory.getGPSHDOP () +
                     ',' + super.dataFactory.getGPSAltitude () + ",M" +
                     ',' + super.dataFactory.getGPSGeoidalSeparation () + ",M";
         
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
            sentence += ',' + super.dataFactory.getGPSAgeOfDGPS () + 
                        ',' + super.dataFactory.getGPSDifferentialStation ();
         }

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
    * Get test sentence data. Note, the last digit of select values within
    * the sentence is random.
    *
    * @return Valid test sentence.
    */
   public String getRandomSentence () {
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_1PT50) {
         int testVal = SentenceTools.getRandomDigit ();
         String sentence = "GPGGA";
         sentence += ",131415" +
                     ",3745.500" + testVal + ",N" +
                     ",12241.600" + testVal + ",W" +
                     ",2,04,1.2,9.6,M,2.5,M";
         
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
            sentence += ",23,0293";
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
      if (fieldCount < PositionGGA.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > PositionGGA.MAX_FIELD_COUNT) {
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
      
      // Test latitude
      testString = (String) fields.get(2);
      double latitude = SentenceTools.parseLatitude (testString.toCharArray (), errorLog);
      if (latitude < 0.01) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_SUSPICOUS,
            new String ("Check latitude value.")));
      }
         
      // Test latitude hemisphere     testString = (String) fields.get(2);
      testString = (String) fields.get(3);
      char latHemi = SentenceTools.parseLatitudeHemisphere (testString.toCharArray (), errorLog);
      if (latHemi == 'S') {
         latitude = -latitude;
      }

      // Test longitude
      testString = (String) fields.get(4);
      double longitude = SentenceTools.parseLongitude (testString.toCharArray (), errorLog);
      if (longitude < 0.01) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_SUSPICOUS,
            new String ("Check longitude value.")));
      }
      
      // Test longitude hemisphere
      testString = (String) fields.get(5);
      char lonHemi = SentenceTools.parseLongitudeHemisphere (testString.toCharArray (), errorLog);
      if (lonHemi == 'W') {
         longitude = -longitude;
      }
      
      dashboardChannel.setPlotPosition (latitude, longitude);

      // Test GPS quality indicator (see note 1)
      boolean differentialQuality = false;
      testString = (String) fields.get(6);
      if (testString.length () == 1) {
         char character = testString.charAt (0);
         if ((character < 0x30) || (character > 0x38)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Quality indicator value not recognized."), character));
         }
         // Test value of quality indicator
         else if ((character < 0x31) || (character > 0x34)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID,
               new String ("Quality indicator invalid."), character));
         }
         else if (character == 0x32) {
            differentialQuality = true;
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Quality indicator field has incorrect number of characters.")));
      }

      // Test number of satellites used, 00-12
      testString = (String) fields.get(7);
      if (testString.length () == 2) {
         char character = testString.charAt (0);
         if ((character < 0x30) || (character > 0x39)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Number of satellites value not recognized."), character));
         }
         character = testString.charAt (1);
         if ((character < 0x30) || (character > 0x39)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Number of satellites value not recognized."), character));
         }
         double numberOfSatellites = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
         if ((numberOfSatellites < 2) || (numberOfSatellites > 12)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Number of satellites value not valid."), character));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Number of satellites field has incorrect number of characters.")));
      }
      
      // Test HDOP (Horizontal Dilution of Precision)
      testString = (String) fields.get(8);
      if (testString.length () > 0) {
         double hdop = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
         if ((hdop < 0.0) || (hdop > 100.0)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ERRONEOUS,
               new String ("HDOP value out of bounds.")));
         }
         else {
            if ((hdop < 0.5) || (hdop > 20.0)) {
               errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_SUSPICOUS,
                  new String ("Check HDOP value.")));
            }
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ERRONEOUS,
               new String ("HDOP value can not be null.")));
      }
      
      // Test Altitude, meters
      testString = (String) fields.get(9);
      if (testString.length () > 0) {
         double antennaAlt = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
         if ((antennaAlt < 0.0) || (antennaAlt > 50.0)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_SUSPICOUS,
               new String ("Check antenna altitude value.")));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ERRONEOUS,
               new String ("Antenna altitude value can not be null.")));
      }
      
      // Test units of altitude
      testString = (String) fields.get(10);
      if (testString.length () == 1) {
         char character = testString.charAt (0);
         if (character != 'M') {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Units field 'M' not recognized."), character));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Units field 'M' has incorrect number of characters.")));
      }
      
      // Test geoidal separation, meters
      testString = (String) fields.get(11);
      if (testString.length () > 0) {
         double geoidalSep = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
         if ((geoidalSep < -10.0) || (geoidalSep > 10.0)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_SUSPICOUS,
               new String ("Check geoidal separation value.")));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ERRONEOUS,
               new String ("Geoidal separation value can not be null.")));
      }
      
      // Test units of geoidal separation
      testString = (String) fields.get(12);
      if (testString.length () == 1) {
         char character = testString.charAt (0);
         if (character != 'M') {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Units field 'M' not recognized."), character));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Units field 'M' has incorrect number of characters.")));
      }
      
      // Test for version 2.0
      if (fieldCount == 13) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Sentence appears to be only v1.5 compliant.")));
         return;
      }
      else if (fieldCount < 15) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Sentence is missing fields.")));
         return;
      }
      
      // Test age of DGPS data, seconds
      testString = (String) fields.get(13);
      double diffCorrectionLatency = 0.0;
      if (testString.length () > 0) {
         diffCorrectionLatency = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
         if ((diffCorrectionLatency < 0.0) || (diffCorrectionLatency > 60.0)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_SUSPICOUS,
               new String ("Check age of differential correction value.")));
         }
      }
      else {
         if (differentialQuality) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ILLOGICAL,
                  new String ("Differential age can not be null for differential GPS positions.")));
         }
      }
      
      // Test DGPS reference station ID, 0000-1023
      testString = (String) fields.get(14);
      // Don't test if latency is either null or invalid
      if (!Double.isNaN (diffCorrectionLatency)) {
         if (testString.length () == 4) {
            char character;
            for (int i = 0; i < testString.length (); i++) {
               character = testString.charAt (i);
               if ((character < 0x30) || (character > 0x39)) {
                  errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                     new String ("Differential reference station value not recognized."), character));
               }
            }
         }
         else {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Differential reference station field has incorrect number of characters.")));
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