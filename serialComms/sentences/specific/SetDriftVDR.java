/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SetDriftVDR.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * VDR - Set and drift. 
 * Since v1.5
 *<pre>
 * Sentence:
 * $--VDR,x.x,T,x.x,M,x.x,N*hh<CR><LF>
 *         |  |  |  |  |--|- Drift speed, Knots
 *         |  |  |--|- Set direction, degrees Magnetic
 *         |--|- Set direction, degrees True
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class SetDriftVDR extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 35;
   final static int MIN_FIELD_COUNT = 7;
   final static int MAX_FIELD_COUNT = 7; 

   final static int [] validTalkerID = {
      SentenceTypes.TALKER_EC,
      SentenceTypes.TALKER_EI,
      SentenceTypes.TALKER_HC,
      SentenceTypes.TALKER_HE,
      SentenceTypes.TALKER_HN,
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
      return SentenceTypes.FORMATTER_VDR;
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
         String sentence = super.getTalkerID () + "VDR";
         sentence += ',' + super.dataFactory.getSimulatedSetTrue () + ",T" +
                     ',' + super.dataFactory.getSetMagnetic () + ",M" +
                     ',' + super.dataFactory.getSimulatedDriftInKnots () + ",N";

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
         String sentence = super.getTalkerID () + "VDR";
         sentence += ',' + super.dataFactory.getSetTrue () + ",T" +
                     ',' + super.dataFactory.getSetMagnetic () + ",M" +
                     ',' + super.dataFactory.getDriftKnots () + ",N";

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
         String sentence = "VDVDR";
         sentence += ",180." + testVal + ",T" +
                     ",185." + testVal + ",M" +
                     ",0." + testVal + ",N";

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
      if (fieldCount < SetDriftVDR.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > SetDriftVDR.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }

      
      // Test set direction, degrees True
      testString = (String) fields.get(1);
      double trueSet = SentenceTools.parseBearing (testString.toCharArray (), errorLog);
      
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
      
      // Test set direction, degrees Magnetic
      testString = (String) fields.get(3);
      double magSet = SentenceTools.parseBearing (testString.toCharArray (), errorLog);
      
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
          
      // Test to make sure at least true or magnetic set are entered.
      if (Double.isNaN (trueSet) && Double.isNaN (magSet)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ILLOGICAL,
            new String ("Both true and magnetic set are either blank or in error.")));    
      }
      
      // Test drift speed, Knots
      testString = (String) fields.get(5);
      double drift = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if (!Double.isNaN (drift)) {
         if ((drift < 0.0) ||
             (drift > SentenceData.MAX_DRIFT_SPEED)) {
                if (drift != Double.NaN) {
                   errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                     new String ("Drift speed out of range.")));
                }
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                     new String ("Drift speed is either null or has invalide characters.")));
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
      
   }
}
/*
 * Revision History:
 * 1.3.0.1 - Changed simulated magnetic set to manual data.
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */