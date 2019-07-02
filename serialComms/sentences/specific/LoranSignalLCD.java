/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: LoranSignalLCD.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * LCD - LORAN-C signal data. 
 * Since v2.0
 *<pre>
 * Sentence:
 *             SNR ECD SNR ECD SNR ECD SNR ECD SNR ECD SNR ECD 
 * $--LCD,xxxx,xxx,xxx,xxx,xxx,xxx,xxx,xxx,xxx,xxx,xxx,xxx,xxx*hh<CR><LF>
 *          |   |   |   |   |   |   |   |   |   |   |   |---|- Secondary S5
 *          |   |   |   |   |   |   |   |   |   |---|- S4
 *          |   |   |   |   |   |   |   |---|- S3
 *          |   |   |   |   |   |---|- S2
 *          |   |   |   |---|- S1
 *          |   |---|- Master
 *          |- GRI, in tens of microseconds
 *
 *Note: Data is in the LORAN-C coding delay order, with null fields used when
 *      values are unavailable.  Signal-to-noise (SNR) and pulse shape (ECD)
 *      values are unsigned integers (000-999).
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class LoranSignalLCD extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 64;
   final static int MIN_FIELD_COUNT = 14;
   final static int MAX_FIELD_COUNT = 14;   

   final static int [] validTalkerID = {
      SentenceTypes.TALKER_LC
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
      return SentenceTypes.FORMATTER_LCD;
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
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
         String sentence = super.getTalkerID () + "LCD";
         sentence += ',' + super.dataFactory.getLoranGRI () + 
                     ',' + super.dataFactory.getLoranECD (0) + 
                     ',' + super.dataFactory.getLoranSNR (0) + 
                     ',' + super.dataFactory.getLoranECD (1) + 
                     ',' + super.dataFactory.getLoranSNR (1) + 
                     ',' + super.dataFactory.getLoranECD (2) + 
                     ',' + super.dataFactory.getLoranSNR (2) + 
                     ',' + super.dataFactory.getLoranECD (3) + 
                     ',' + super.dataFactory.getLoranSNR (3) + 
                     ',' + super.dataFactory.getLoranECD (4) + 
                     ',' + super.dataFactory.getLoranSNR (4) + 
                     ',' + super.dataFactory.getLoranECD (5) + 
                     ',' + super.dataFactory.getLoranSNR (5);

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
         String sentence = 
            "LCLCD,7610" +
            ",900,90" + testVal +
            ",910,91" + testVal +
            ",920,92" + testVal +
            ",930,93" + testVal +
            ",940,94" + testVal +
            ",950,95" + testVal;
         
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
      if (fieldCount < LoranSignalLCD.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > LoranSignalLCD.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }
      
      // Test GRI
      testString = (String) fields.get(1);
      if (testString.length () > 0) {
         SentenceTools.parseLoranGRI (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran GRI can not be null.")));
      };
      
      // Test master ECD
      testString = (String) fields.get(2);
      if (testString.length () > 0) {
         SentenceTools.parseLoranECDSNR (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran master ECD can not be null.")));
      }
      
      // Test master SNR
      testString = (String) fields.get(3);
      if (testString.length () > 0) {
         SentenceTools.parseLoranECDSNR (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran master SNR can not be null.")));
      }
      
      // Test signal 1 ECD
      testString = (String) fields.get(4);
      if (testString.length () > 0) {
         SentenceTools.parseLoranECDSNR (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran signal 1 ECD can not be null.")));
      }
      
      // Test signal 1 SNR
      testString = (String) fields.get(5);
      if (testString.length () > 0) {
         SentenceTools.parseLoranECDSNR (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran signal 1 SNR can not be null.")));
      }
            
      // Test signal 2 ECD
      testString = (String) fields.get(6);
      if (testString.length () > 0) {
         SentenceTools.parseLoranECDSNR (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran signal 2 ECD can not be null.")));
      }
      
      // Test signal 2 SNR
      testString = (String) fields.get(7);
      if (testString.length () > 0) {
         SentenceTools.parseLoranECDSNR (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran signal 2 SNR can not be null.")));
      }
      
      // Test signal 3 ECD
      testString = (String) fields.get(8);
      if (testString.length () > 0) {
         SentenceTools.parseLoranECDSNR (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran signal 3 ECD can not be null.")));
      }
      
      // Test signal 3 SNR
      testString = (String) fields.get(9);
      if (testString.length () > 0) {
         SentenceTools.parseLoranECDSNR (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran signal 3 SNR can not be null.")));
      }
      
      // Test signal 4 ECD
      testString = (String) fields.get(10);
      if (testString.length () > 0) {
         SentenceTools.parseLoranECDSNR (testString.toCharArray (), errorLog);
      }
      
      // Test signal 4 SNR
      testString = (String) fields.get(11);
      if (testString.length () > 0) {
         SentenceTools.parseLoranECDSNR (testString.toCharArray (), errorLog);
      }
      
      // Test signal 5 ECD
      testString = (String) fields.get(12);
      if (testString.length () > 0) {
         SentenceTools.parseLoranECDSNR (testString.toCharArray (), errorLog);
      }
      
      // Test signal 5 SNR
      testString = (String) fields.get(13);
      if (testString.length () > 0) {
         SentenceTools.parseLoranECDSNR (testString.toCharArray (), errorLog);
      }
   }
}
/*
 * Revision History:
 *
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */