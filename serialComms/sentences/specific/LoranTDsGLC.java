/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: LoranTDsGLC.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * GLC - Geographic position - LORAN-C. 
 * Since v1.5
 *<pre>
 * Sentence:
 * $--GLC,xxxx,x.x,a,x.x,a,x.x,a,x.x,a,x.x,a,x.x,a*hh<CR><LF>
 *          |   |  |  |  |  |  |  |  |  |  |  |--|- TD5
 *          |   |  |  |  |  |  |  |  |  |--|- TD4
 *          |   |  |  |  |  |  |  |--|- TD3
 *          |   |  |  |  |  |--|- TD2
 *          |   |  |  |--|- TD1
 *          |   |--|- Master TOA microseconds
 *          |- GRI, in tens of microseconds
 *
 *Notes:
 * (1) Master TOA provides for direct ranging operation.  It may be the actual
 *     range to the Master in microseconds, or be offset and track the arrival 
 *     of the Master signal.
 * (2) Time difference numbers in microseconds are in the LORAN-C coding delay
 *     order with null fields used when values are unavailable.
 * (3) The signal status ('a') in order of priority
 *     B - blink warning
 *     C - cycle warning
 *     S - SNR warning
 *     A - Valid
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class LoranTDsGLC extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 70;
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
      return SentenceTypes.FORMATTER_GLC;
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
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_1PT50) {
         String sentence = super.getTalkerID () + "GLC";
         sentence += ',' + super.dataFactory.getLoranGRI () + 
                     ',' + super.dataFactory.getLoranTOATD (0) +
                     ',' + super.dataFactory.getLoranStatus (0) +
                     ',' + super.dataFactory.getLoranTOATD (1) +
                     ',' + super.dataFactory.getLoranStatus (1) +
                     ',' + super.dataFactory.getLoranTOATD (2) +
                     ',' + super.dataFactory.getLoranStatus (2) +
                     ',' + super.dataFactory.getLoranTOATD (3) +
                     ',' + super.dataFactory.getLoranStatus (3) +
                     ',' + super.dataFactory.getLoranTOATD (4) +
                     ',' + super.dataFactory.getLoranStatus (4) +
                     ',' + super.dataFactory.getLoranTOATD (5) +
                     ',' + super.dataFactory.getLoranStatus (5);

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
         String sentence = 
            "LCGLC,4610" +
            ",9520." + testVal + ",A" +
            ",1921." + testVal + ",A" +
            ",2922." + testVal + ",A" +
            ",3923." + testVal + ",A" +
            ",4924." + testVal + ",A" +
            ",5925." + testVal + ",A";
         
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
      if (fieldCount < LoranTDsGLC.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > LoranTDsGLC.MAX_FIELD_COUNT) {
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
      }
      
      // Test master TOA
      testString = (String) fields.get(2);
      if (testString.length () > 0) {
         SentenceTools.parseLoranTOATD (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran master TOA can not be null.")));
      }
      
      // Test master signal status
      testString = (String) fields.get(3);
      if (testString.length () > 0) {
         SentenceTools.parseLoranStatus (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran master signal status can not be null.")));
      }
      
      // Test signal 1 TD
      testString = (String) fields.get(4);
      if (testString.length () > 0) {
         SentenceTools.parseLoranTOATD (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran signal 1 TD can not be null.")));
      }
      
      // Test signal 1 signal status
      testString = (String) fields.get(5);
      if (testString.length () > 0) {
         SentenceTools.parseLoranStatus (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran signal 1 status can not be null.")));
      }
      
      // Test signal 2 TD
      testString = (String) fields.get(6);
      if (testString.length () > 0) {
         SentenceTools.parseLoranTOATD (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran signal 2 TD can not be null.")));
      }
      
      // Test signal 2 signal status
      testString = (String) fields.get(7);
      if (testString.length () > 0) {
         SentenceTools.parseLoranStatus (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran signal 2 status can not be null.")));
      }
      
      
      // Test signal 3 TD
      testString = (String) fields.get(8);
      if (testString.length () > 0) {
         SentenceTools.parseLoranTOATD (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran signal 3 TD can not be null.")));
      }
      
      // Test signal 3 signal status
      testString = (String) fields.get(9);
      if (testString.length () > 0) {
         SentenceTools.parseLoranStatus (testString.toCharArray (), errorLog);
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_MISSING,
            new String ("Loran signal 3 status can not be null.")));
      }
      
      
      // Test signal 4 TD
      testString = (String) fields.get(10);
      if (testString.length () > 0) {
         SentenceTools.parseLoranTOATD (testString.toCharArray (), errorLog);
      }
      
      // Test signal 4 signal status
      testString = (String) fields.get(11);
      if (testString.length () > 0) {
         SentenceTools.parseLoranStatus (testString.toCharArray (), errorLog);
      }

      
      // Test signal 5 TD
      testString = (String) fields.get(12);
      if (testString.length () > 0) {
         SentenceTools.parseLoranTOATD (testString.toCharArray (), errorLog);
      }
      
      // Test signal 5 signal status
      testString = (String) fields.get(13);
      if (testString.length () > 0) {
         SentenceTools.parseLoranStatus (testString.toCharArray (), errorLog);
      }
   }
}
/*
 * Revision History:
 *
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */