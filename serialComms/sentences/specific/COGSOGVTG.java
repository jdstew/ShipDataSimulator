/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: COGSOGVTG.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;

import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * VTG - Course over ground and speed (COG/SOG). 
 * Since v1.5, mode indicator added in v2.3
 *<pre>
 * Sentence:
 * $--VTG,x.x,T,x.x,M,x.x,N,x.x,K,a*hh<CR><LF>
 *         |  |  |  |  |  |  |  | |- Mode indicator (see note 1)
 *         |  |  |  |  |  |  |--|- Speed over ground, km/hr
 *         |  |  |  |  |--|- Speed over ground, Knots
 *         |  |  |--|- Course over ground, degrees Magnetic
 *         |--|- Course over ground, degrees True
 *
 * Notes:
 * (1) Positioning system mode indicator:
 *     A = Autonomous
 *     D = Differential
 *     E = Estimated (dead reckoning) mode
 *     M = Manual input
 *     S = Simulator
 *     N = Data not valid
 * (2) The positioning system mode indicator can not be null
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class COGSOGVTG extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 43;
   final static int MIN_FIELD_COUNT = 9;
   final static int MAX_FIELD_COUNT = 10;  
   
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
      return SentenceTypes.FORMATTER_VTG;
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
         String sentence = super.getTalkerID () + "VTG";
         // Note: this data should be simulated.
         sentence += ',' + super.dataFactory.getSimulatedHeadingTrueOverGround () + ",T" +
                     ',' + super.dataFactory.getHeadingMagneticGround () + ",M" +
                     ',' + super.dataFactory.getSimulatedSpeedOverGroundInKnots () + ",N" +
                     ',' + super.dataFactory.getSimulatedSpeedOverGroundInInKPH () + ",K";
      
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence += ',' + super.dataFactory.getSpeedMode ();
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
         String sentence = super.getTalkerID () + "VTG";
         sentence += ',' + super.dataFactory.getHeadingTrueGround () + ",T" +
                     ',' + super.dataFactory.getHeadingMagneticGround () + ",M" +
                     ',' + super.dataFactory.getSpeedGroundInKnots () + ",N" +
                     ',' + super.dataFactory.getSpeedGroundInKMH () + ",K";
      
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence += ',' + super.dataFactory.getSpeedMode ();
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
         String sentence = "GPVTG";
         sentence += ",090." + testVal + ",T" +
                     ",095." + testVal + ",M" +
                     ",10." + testVal + ",N" +
                     ",12." + testVal + ",K";
      
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence += ",D";
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
      if (fieldCount < COGSOGVTG.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > COGSOGVTG.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }
      
      // Test true course over ground
      testString = (String) fields.get(1);
      double trueHdg = SentenceTools.parseBearing (testString.toCharArray (), errorLog);
      if (!Double.isNaN (trueHdg)) {
         dashboardChannel.setCourseOverGround ((float)trueHdg);
      }
      
      // Test for 'T' value
      testString = (String) fields.get(2);
      if (testString.length () == 1) {
         char character = testString.charAt (0);
         if (character != 'T') {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Units field 'T' not recognzed."), character));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Units field 'T' has incorrect number of characters.")));
      }
      
      // Test magnetic course over ground
      testString = (String) fields.get(3);
      double magHdg = SentenceTools.parseBearing (testString.toCharArray (), errorLog);
      
      // Test for 'M' value
      testString = (String) fields.get(4);
      if (testString.length () == 1) {
         char character = testString.charAt (0);
         if (character != 'M') {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Units field 'M' not recognzed."), character));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Units field 'M' has incorrect number of characters.")));
      }
      
      // Test to ensure at least one of the course values is provided
      if (Double.isNaN (trueHdg) && Double.isNaN (magHdg)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ILLOGICAL,
            new String ("Both true and magnetic heading are either blank or in error.")));    
      }

      
      // Test knots speed over ground
      testString = (String) fields.get(5);
      double speedKnots = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if ((speedKnots < -SentenceData.MAX_STERN_SPEED) ||
          (speedKnots > SentenceData.MAX_FORWARD_SPEED)) {
             if (speedKnots != Double.NaN) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Speed (knots) out of range.")));
             }
      }
      else {
         dashboardChannel.setSpeedOverGround ((float)speedKnots);
      }
      
      // Test for 'N' value
      testString = (String) fields.get(6);
      if (testString.length () == 1) {
         char character = testString.charAt (0);
         if (character != 'N') {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Units field 'N' not recognzed."), character));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Units field 'N' has incorrect number of characters.")));
      }
      
      // Test kmh speed over ground
      testString = (String) fields.get(7);
      double speedKPH = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if ((speedKPH < -(SentenceData.MAX_STERN_SPEED * SentenceData.NM_TO_KM_CONVERSION)) ||
          (speedKPH > (SentenceData.MAX_FORWARD_SPEED * SentenceData.NM_TO_KM_CONVERSION))) {
             if (speedKPH != Double.NaN) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Speed (KPH) out of range.")));
             }
      }
      
      
      // Test for 'K' value
      testString = (String) fields.get(8);
      if (testString.length () == 1) {
         char character = testString.charAt (0);
         if (character != 'K') {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Units field 'K' not recognzed."), character));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Units field 'K' has incorrect number of characters.")));
      }
      
      // Test to ensure at least one of the speed values is provided
      if (Double.isNaN (speedKnots) && Double.isNaN (speedKPH)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ILLOGICAL,
            new String ("Both knots and KPH speeds are either blank or in error.")));    
      }
      
      // Test for 2.3 compliance
      if (fieldCount == 9) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Sentence appears to be only v1.5 compliant.")));
         return;
      }
            
      // Test Mode
      testString = (String) fields.get(9);
      char mode = SentenceTools.parseOperatingMode (testString.toCharArray (), errorLog);
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
 * 1.3.0.1 - Changed simulated magnetic heading over ground to manual data.
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */