/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: DepthDPT.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * DPT - Depth. 
 * Since v2.0, maximum range scale added in v2.3
 *<pre>
 * Sentence:
 * $--DPT,x.x,x.x,x.x*hh<CR><LF>
 *         |   |   |- Maximum range scale in use, meters
 *         |   |- Offset from transducer, meters
 *         |- Water depth relative to the transducer, meters
 *
 * Notes:
 * (1) Positive offset equals the distance from the waterline to the transducer.
 *     This results in depth of water.
 * (2) Negative offset equals the distance from the transducer to the keel.
 *     This results in depth beneath the keel.
 * (3) Unit of measurement is ALWAYS in meters for this sentence.
 * (4) For IEC applications, the depth offset is always to the keel
 *
 * Example:
 * $SDDPT,17.0,-0.2,100.0*39<CR><LF>
 * 17.0 meters = sounding from transducer
 *  0.2 meters = distance between transducer and keel
 * -----------
 * 16.8 meters = depth beneath the keel
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class DepthDPT extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 28;
   final static int MIN_FIELD_COUNT = 3;
   final static int MAX_FIELD_COUNT = 4;   

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
      return SentenceTypes.FORMATTER_DPT;
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
         String sentence = super.getTalkerID () + "DPT";
         sentence += ',' + super.dataFactory.getDepthInMeters () +
                     ',' + super.dataFactory.getDepthOffset ();
         
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence += ',' + super.dataFactory.getDepthRange ();
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
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
         int testVal = SentenceTools.getRandomDigit ();     
         String sentence = "SDDPT";
         sentence += ",133." + testVal + 
                     ",3.2";
         
         if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
            sentence += ",200";
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
      if (fieldCount < DepthDPT.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > DepthDPT.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }
      
      // Test water depth (feet)
      testString = (String) fields.get(1);
      double depthMeters = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if (Double.isNaN (depthMeters)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Depth (meters) can not be null.")));
      }
      if ((depthMeters < 0) ||
          (depthMeters > (SentenceData.MAX_DEPTH / SentenceData.FT_TO_M_CONVERSION))) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Depth (meters) out of range.")));
      }
      else {
         dashboardChannel.setDepth ((float)(depthMeters / SentenceData.FT_TO_M_CONVERSION));
      }
      
      // Test water depth (feet)
      testString = (String) fields.get(2);
      double transducerOffset = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if (Double.isNaN (transducerOffset)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Transducer offset can not be null.")));
      }
      if ((transducerOffset < -(SentenceData.MAX_TRANSDUCER_OFFSET * SentenceData.FT_TO_M_CONVERSION)) ||
          (transducerOffset >  (SentenceData.MAX_TRANSDUCER_OFFSET * SentenceData.FT_TO_M_CONVERSION))) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Transducer offset out of range.")));
      }
      
      // Test for version 2.3
      if (fieldCount == 3) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Sentence appears to be only v2.0 compliant.")));
         return;
      }
      
      // Test water depth (feet)
      testString = (String) fields.get(3);
      double depthRange = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if (Double.isNaN (depthRange)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Depth range scale can not be null.")));
      }
      if ((depthRange < 0) ||
          (depthRange > (SentenceData.MAX_DEPTH * SentenceData.FT_TO_M_CONVERSION))) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Maximum depth range scale out of range.")));
      }
      
      // Test depth against range scale.
      if (depthMeters > depthRange) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ILLOGICAL,
            new String ("Check depth, it should not be greater than range scale.")));    
      }
   }
}
/*
 * Revision History:
 *
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */