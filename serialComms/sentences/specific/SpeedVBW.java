/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SpeedVBW.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * VBW - Dual ground and water Speed. 
 * Since v1.5, stern speed fields (x4) added in v2.3
 *<pre>
 * Sentence: 
 * $--VBW,x.x,x.x,A,x.x,x.x,A,x.x,A,x.x,A*hh<CR><LF>
 *         |   |  |  |   |  |  |  |  |--|- Stern ground transverse speed, valid
 *         |   |  |  |   |  |  |--|- Stern water transverse speed, valid
 *         |   |  |  |---|--|- Ground longitudinal/transverse speed, valid
 *         |---|--|- Water longitudinal/transverse speed, valid
 *
 * Notes:
 * (1) Negative logitudinal speeds are astern
 * (2) Negative transverse speeds are to port, positive are to starboard
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class SpeedVBW extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 49;
   final static int MIN_FIELD_COUNT = 7;
   final static int MAX_FIELD_COUNT = 11; 

   final static int [] validTalkerID = {
      SentenceTypes.TALKER_EC,
      SentenceTypes.TALKER_EI,
      SentenceTypes.TALKER_LC,
      SentenceTypes.TALKER_GN,
      SentenceTypes.TALKER_GP,
      SentenceTypes.TALKER_IN,
      SentenceTypes.TALKER_SN,
      SentenceTypes.TALKER_VD,
      SentenceTypes.TALKER_VM,
      SentenceTypes.TALKER_VW
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
      return SentenceTypes.FORMATTER_VBW;
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
         String sentence = super.getTalkerID () + "VBW";
         sentence += ',' + super.dataFactory.getSimulatedSpeedWaterInKnots () +
                     ',' + super.dataFactory.getSpeedWaterTranverseInKnots () +
                     ',' + super.dataFactory.getSpeedWaterStatus () +
                     ',' + super.dataFactory.getSimulatedSpeedOverGroundInKnots () +
                     ',' + super.dataFactory.getSpeedGroundTranverseInKnots () +
                     ',' + super.dataFactory.getSpeedGroundStatus ();
                     
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence += ',' + super.dataFactory.getSpeedWaterSternInKnots () +
                        ',' + super.dataFactory.getSpeedWaterSternStatus () +
                        ',' + super.dataFactory.getSpeedGroundSternInKnots () +
                        ',' + super.dataFactory.getSpeedGroundSternStatus ();
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
         String sentence = super.getTalkerID () + "VBW";
         sentence += ',' + super.dataFactory.getSpeedWaterInKnots () +
                     ',' + super.dataFactory.getSpeedWaterTranverseInKnots () +
                     ',' + super.dataFactory.getSpeedWaterStatus () +
                     ',' + super.dataFactory.getSpeedGroundInKnots () +
                     ',' + super.dataFactory.getSpeedGroundTranverseInKnots () +
                     ',' + super.dataFactory.getSpeedGroundStatus ();
                     
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence += ',' + super.dataFactory.getSpeedWaterSternInKnots () +
                        ',' + super.dataFactory.getSpeedWaterSternStatus () +
                        ',' + super.dataFactory.getSpeedGroundSternInKnots () +
                        ',' + super.dataFactory.getSpeedGroundSternStatus ();
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
         String sentence = "VDVBW";
         sentence += ",10." + testVal +
                     ",-0." + testVal +
                     ",A,,,V";
                     
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence += ",,V,,V";
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
      if (fieldCount < SpeedVBW.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > SpeedVBW.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }
      
      // Test water longitudinal speed
      testString = (String) fields.get(1);
      double waterLongitudinalSpeed = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if ((waterLongitudinalSpeed < -SentenceData.MAX_STERN_SPEED) ||
          (waterLongitudinalSpeed > SentenceData.MAX_FORWARD_SPEED)) {
             if (waterLongitudinalSpeed != Double.NaN) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Speed (water longitudinal) out of range.")));
             }
      }
      else {
         dashboardChannel.setSpeedThroughWater ((float)waterLongitudinalSpeed);
      }
      
      // Test water transverse speed
      testString = (String) fields.get(2);
      double waterTransverseSpeed = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if ((waterTransverseSpeed < -SentenceData.MAX_TRANSVERSE_SPEED) ||
          (waterTransverseSpeed > SentenceData.MAX_TRANSVERSE_SPEED)) {
             if (waterTransverseSpeed != Double.NaN) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Speed (water transverse) out of range.")));
             }
      }
      
      // Test water speed validity
      testString = (String) fields.get(3);
      char waterSpeedValidity = SentenceTools.parseStatus (testString.toCharArray (), errorLog);
      
      
      // Test ground longitudinal speed
      testString = (String) fields.get(4);
      double groundLongitudinalSpeed = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if ((groundLongitudinalSpeed < -SentenceData.MAX_STERN_SPEED) ||
          (groundLongitudinalSpeed > SentenceData.MAX_FORWARD_SPEED)) {
             if (groundLongitudinalSpeed != Double.NaN) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Speed (ground longitudina) out of range.")));
             }
      }
      else {
         dashboardChannel.setSpeedOverGround ((float)groundLongitudinalSpeed);
      }
            
      // Test ground transverse speed
      testString = (String) fields.get(5);
      double groundTransverseSpeed = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if ((groundTransverseSpeed < -SentenceData.MAX_TRANSVERSE_SPEED) ||
          (groundTransverseSpeed > SentenceData.MAX_TRANSVERSE_SPEED)) {
             if (groundTransverseSpeed != Double.NaN) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Speed (ground transverse) out of range.")));
             }
      }
      
      // Test ground speed validity
      testString = (String) fields.get(6);
      char groundSpeedValidity = SentenceTools.parseStatus (testString.toCharArray (), errorLog);
      
      // Test to ensure either water or ground speed is available
      if ((waterSpeedValidity == 'V') && (groundSpeedValidity == 'V')) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ILLOGICAL,
            new String ("Both water and ground status bits are set to invalid.")));
      }
      
      // Test for version 1.5
      if (fieldCount == 7) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Sentence appears to be only v1.5 compliant.")));
         return;
      }
      
      // Test for version 2.3
      if (fieldCount != 11) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence is missing fields.")));
         return;
      }
      
      // Test stern water transverse speed
      testString = (String) fields.get(7);
      double waterTransverseSternSpeed = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if ((waterTransverseSternSpeed < -SentenceData.MAX_TRANSVERSE_SPEED) ||
          (waterTransverseSternSpeed > SentenceData.MAX_TRANSVERSE_SPEED)) {
             if (waterTransverseSternSpeed != Double.NaN) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Speed (stern water transverse) out of range.")));
             }
      }
      
      // Test stern water speed validity
      //testString = (String) fields.get(8);
      //char waterSternValidity = SentenceTools.parseStatus (testString.toCharArray (), errorLog);
      
      // Test stern ground transverse speed, 
      testString = (String) fields.get(9);
      double groundTransverseSternSpeed = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if ((groundTransverseSternSpeed < -SentenceData.MAX_TRANSVERSE_SPEED) ||
          (groundTransverseSternSpeed > SentenceData.MAX_TRANSVERSE_SPEED)) {
             if (groundTransverseSternSpeed != Double.NaN) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Speed (stern ground transverse) out of range.")));
             }
      }
      
      // Test stern ground speed validity
     // testString = (String) fields.get(10);
      //char groundSternValidity = SentenceTools.parseStatus (testString.toCharArray (), errorLog);    

   }
}
/*
 * Revision History:
 *
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */