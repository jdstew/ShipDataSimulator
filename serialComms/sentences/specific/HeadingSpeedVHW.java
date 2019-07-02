/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: HeadingSpeedVHW.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * VHW - Water speed and heading. 
 * Since v1.5
 *<pre>
 * Sentence:
 * $--VHW,x.x,T,x.x,M,x.x,N,x.x,K*hh<CR><LF>
 *         |  |  |  |  |  |  |--|- Speed, km/hr
 *         |  |  |  |  |--|- Speed, Knots
 *         |  |  |--|- Heading, degrees Magnetic
 *         |--|- Heading, degrees True
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.3, 2005-01-31
 */
public class HeadingSpeedVHW extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 41;
   final static int MIN_FIELD_COUNT = 9;
   final static int MAX_FIELD_COUNT = MIN_FIELD_COUNT; 

   final static int [] validTalkerID = {
      SentenceTypes.TALKER_EC,
      SentenceTypes.TALKER_EI,
      SentenceTypes.TALKER_HC,
      SentenceTypes.TALKER_HE,
      SentenceTypes.TALKER_HN,
      SentenceTypes.TALKER_II,
      SentenceTypes.TALKER_LC,
      SentenceTypes.TALKER_GN,
      SentenceTypes.TALKER_GP,
      SentenceTypes.TALKER_IN,
      SentenceTypes.TALKER_SN,
      SentenceTypes.TALKER_VD,
      SentenceTypes.TALKER_VM,
      SentenceTypes.TALKER_VR,
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
      return SentenceTypes.FORMATTER_VHW;
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
         String sentence = super.getTalkerID () + "VHW";
         sentence += ',' + super.dataFactory.getSimulatedHeadingTrueWater () + ",T" +
                     ',' + super.dataFactory.getHeadingMagneticWater () + ",M" +
                     ',' + super.dataFactory.getSimulatedSpeedWaterInKnots () + ",N" +
                     ',' + super.dataFactory.getSimulatedSpeedWaterInKPH () + ",K";

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
         String sentence = super.getTalkerID () + "VHW";
         sentence += ',' + super.dataFactory.getHeadingTrueWater () + ",T" +
                     ',' + super.dataFactory.getHeadingMagneticWater () + ",M" +
                     ',' + super.dataFactory.getSpeedWaterInKnots () + ",N" +
                     ',' + super.dataFactory.getSpeedWaterInKMH () + ",K";

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
         String sentence = "INVHW";
         sentence += ",090." + testVal + ",T" +
                     ",095." + testVal + ",M" +
                     ",10." + testVal + ",N" +
                     ",12." + testVal + ",K";

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
      if (fieldCount < HeadingSpeedVHW.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > HeadingSpeedVHW.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }
      
      // Test true water heading value field
      testString = (String) fields.get(1);
      double trueHdg = SentenceTools.parseBearing (testString.toCharArray (), errorLog);
      if (!Double.isNaN (trueHdg)) {
         dashboardChannel.setCourseThroughWater ((float)trueHdg);
      }
      
      // Test 'T' character field
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
      
      // Test magnetic water heading value field
      testString = (String) fields.get(3);
      double magHdg = SentenceTools.parseBearing (testString.toCharArray (), errorLog);
      
      // Test to make sure at least true or magnetic heading are entered.
      if (Double.isNaN (trueHdg) && Double.isNaN (magHdg)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ILLOGICAL,
            new String ("Both true and magnetic heading are either blank or in error.")));    
      }
      
      // Test 'M' character field
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
      
      
      // Test true water speed value field
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
         dashboardChannel.setSpeedThroughWater ((float)speedKnots);
      }
      
      // Test 'N' character field
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
      
      
      // Test true water heading value field
      testString = (String) fields.get(7);
      double speedKPH = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if ((speedKPH < -(SentenceData.MAX_STERN_SPEED * SentenceData.NM_TO_KM_CONVERSION)) ||
          (speedKPH > (SentenceData.MAX_FORWARD_SPEED * SentenceData.NM_TO_KM_CONVERSION))) {
             if (speedKPH != Double.NaN) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Speed (KPH) out of range.")));
             }
      }
      
      // Test to make sure at least true or magnetic speed are entered.
      if (Double.isNaN (speedKnots) && Double.isNaN (speedKPH)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ILLOGICAL,
            new String ("Both knots and KPH speeds are either blank or in error.")));    
      }
      
      // Test 'K' character field
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
   }
}
/*
 * Revision History:
 * 1.3.0.1 - Changed simulated magnetic water heading to manual data.
 * 1.0.0.3 - Added 'II' talker ID to valid talker ID array.
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */