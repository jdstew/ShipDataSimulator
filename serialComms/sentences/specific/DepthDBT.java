/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: DepthDBT.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * DBT - Depth. 
 * Since v1.5
 *<pre>
 * Sentence:
 * $--DBT,x.x,f,x.x,M,x.x,F*hh<CR><LF>
 *         |  |  |  |  |--|- Water depth, Fathoms
 *         |  |  |--|- Water depth, Meters
 *         |--|- Water depth, feet
 *
 * Notes:
 * (1) These values are water depth referenced from the transducer.
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class DepthDBT extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 39;
   final static int MIN_FIELD_COUNT = 7;
   final static int MAX_FIELD_COUNT = 7;   

   final static int [] validTalkerID = {
      SentenceTypes.TALKER_II,
      SentenceTypes.TALKER_SD,
      SentenceTypes.TALKER_SS,
      SentenceTypes.TALKER_YX,
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
      return SentenceTypes.FORMATTER_DBT;
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
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_1PT50) {
         String sentence = super.getTalkerID () + "DBT";
         sentence += ',' + super.dataFactory.getDepthInFeet () + ",f" +
                     ',' + super.dataFactory.getDepthInMeters () + ",M" + 
                     ',' + super.dataFactory.getDepthInFathoms () + ",F";
         
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
         String sentence = "SDDBT";
         sentence += ",600." + testVal + ",f" +
                     ",133." + testVal + ",M" + 
                     ",200." + testVal + ",F";

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
      if (fieldCount < DepthDBT.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > DepthDBT.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }
      
      // Test water depth (feet)
      testString = (String) fields.get(1);
      double depthFeet = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if ((depthFeet < 0) ||
          (depthFeet > SentenceData.MAX_DEPTH)) {
             if (depthFeet != Double.NaN) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Depth (feet) out of range.")));
             }
      }
      else {
         dashboardChannel.setDepth ((float)depthFeet);
      }
      
      
      // Test 'f' character field
      testString = (String) fields.get(2);
      if (testString.length () == 1) {
         char character = testString.charAt (0);
         if (character != 'f') {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Units field 'f' not recognzed."), character));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Units field 'M' has incorrect number of characters.")));
      }
      
      // Test water depth (meters)
      testString = (String) fields.get(3);
      double depthMeters = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if ((depthMeters < 0) ||
          (depthMeters > (SentenceData.MAX_DEPTH / SentenceData.FT_TO_M_CONVERSION))) {
             if (depthMeters != Double.NaN) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Depth (meters) out of range.")));
             }
      }
      else {
         dashboardChannel.setDepth ((float)(depthMeters / SentenceData.FT_TO_M_CONVERSION));
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
      
      // Test water depth (fathoms)
      testString = (String) fields.get(5);
      double depthFathoms = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if ((depthFathoms < 0) ||
          (depthFathoms > (SentenceData.MAX_DEPTH / SentenceData.FT_TO_M_CONVERSION))) {
             if (depthFathoms != Double.NaN) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Depth (fathoms) out of range.")));
             }
      }
      else {
         dashboardChannel.setDepth ((float)(depthFathoms / SentenceData.FT_TO_M_CONVERSION));
      }
      
      // Test 'F' character field
      testString = (String) fields.get(6);
      if (testString.length () == 1) {
         char character = testString.charAt (0);
         if (character != 'F') {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Units field 'F' not recognzed."), character));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Units field 'F' has incorrect number of characters.")));
      }
      
      // Test to make sure at least one of the water depth values is not null.
      if (Double.isNaN (depthFeet) && Double.isNaN (depthMeters) && Double.isNaN (depthFathoms)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ILLOGICAL,
            new String ("All depth fields are either blank or in error.")));    
      }
      
   }
}
/*
 * Revision History:
 *
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */