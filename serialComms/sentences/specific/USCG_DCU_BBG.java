/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: USCG_DCU_BBG.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * BBG - DCU positioner to OSS. 
 * Since v3.0
 *<pre>
 * Sentence:
 * $PBBG,x.x,x.x,c,c*hh<CR><LF>
 *        |   |  | |- OSS status (see note 1)
 *        |   |  |- SCCS status (see note 2)
 *        |   |- Elevation (vertical), in degrees.
 *        |- Train (horizontal), in degrees.
 *
 *Notes:
 *(1) This field is valid only from DCU to SCC
        On target = 'Q' (hex 51)
 *     Off target = 'P' (hex 50)
 *(2) This field is valid only from SCCS computer to DCU
              Slave enable = 'Q' (hex 51) 
 *     Slave not available = 'P' (hex 50)
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class USCG_DCU_BBG extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 26;
   final static int MIN_FIELD_COUNT = 5;
   final static int MAX_FIELD_COUNT = 5; 

   final static int [] validTalkerID = {
      SentenceTypes.TALKER_P,
      SentenceTypes.TALKER_IN
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
      return getManualSentence ();
   }
   
   /**
    * Get sentence based upon manual data.
    *
    * @return Valid simulation-based sentence.
    */
   public String getManualSentence () {
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_3PT00) {
         String sentence = super.getTalkerID () + "BBG";
         sentence += ',' + super.dataFactory.getOssTrain () + 
                     ',' + super.dataFactory.getOssElevation () + 
                     ',' + super.dataFactory.getOssStatusFromSCCS () + 
                     ',' + super.dataFactory.getOssStatusFromDCU ();

         sentence += '*' + SentenceTools.getChecksum(sentence);

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
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_3PT00) {
         String testString = new String (
            "PBBG,345." + testVal +
            ",000." + testVal +
            ",,P");
         return new String('$' +  testString + '*' + SentenceTools.getChecksum(testString)
            + SentenceTools.CR + SentenceTools.LF);
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
      if (fieldCount < USCG_DCU_BBG.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > USCG_DCU_BBG.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }
      
      // Test train bearing
      testString = (String) fields.get(1);
      if (testString.length () > 0) {
         SentenceTools.parseBearing (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_MISSING, 
            "Bit failure for OSS train (null value)."));
      }
      
      
      // Test elevation bearing
      testString = (String) fields.get(2);
      if (testString.length () > 0) {
         double elevation = SentenceTools.parseBearing (testString.toCharArray (), errorLog);
         if (Double.isNaN (elevation) || (elevation > 90.0)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               "Erroneous elevation value."));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_MISSING,
            "Bit failure for OSS elevation (null value)."));
      }
      
      // Test DCU commmand
      String dcuCmdString = (String) fields.get(3);
      if (dcuCmdString.length() == 1) {
         char status = dcuCmdString.charAt (0);
         if ((status == 'P') || (status == 'Q')) {
            // reserved for future processing.
         }
         else {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("SCCS (to DCU) command character not recognzed."), status));
         }
      }
      else {
         if (dcuCmdString.length() > 1) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("SCCS (to DCU) command character has too many characters.")));
            }
      }
      
      // Test SCCS command
      String sccsCmdString = (String) fields.get(4);
      if (sccsCmdString.length() == 1) {
         char status = sccsCmdString.charAt (0);
         if ((status == 'P') || (status == 'Q')) {
            // reserved for future processing.
         }
         else {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("DCU (to SCCS) command character not recognzed."), status));
         }
      }
      else {
         if (sccsCmdString.length() > 1) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("DCU (to SCCS) command character has too many characters.")));
         }
      }
      
      // Test logic between DCU and SCCS command
      if ((dcuCmdString.length () == 0) && (sccsCmdString.length () == 0)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ILLOGICAL,
            new String ("DCU and SCCS command characters can not both be null.")));
      }
   }
}
/*
 * Revision History:
 *
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */