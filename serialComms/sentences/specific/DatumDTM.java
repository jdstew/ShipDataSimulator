/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: DatumDTM.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * DTM - Datum reference. 
 * Since v2.01 (but format not determined until v2.2)
 *<pre>
 * Sentence:
 * $--DTM,ccc,a,x.x,a,x.x,a,x.x,ccc*hh<CR><LF>
 *         |  |  |  |  |  |  |   |- Reference datum (see note 2)
 *         |  |  |  |  |  |  |- Altitude offset, meters
 *         |  |  |  |  |--|- Longitude offset, minutes, 'E' or 'W'
 *         |  |  |--|- Latitude offset, minutes, 'N' or 'S'
 *         |  |- Local datum subdivision code (see note 3)
 *         |- Local datum code (see note 4)
 *
 * Notes:
 * (1) The offset is applied from the reference to the local datum position(P).
 *     P_local = P_ref + P_datum
 * (2) Valid reference datums (ref = value):
 *     WGS84 = W84
 *     WGS72 = W72
 *     SGS85 = S85
 *      PE90 = P90
 * (3) From IHO S-60, appendices B and C, or null if unknown.
 * (4) Valid local datum codes (ref = value):
 *              WGS84 = W84
 *              WGS72 = W72
 *              SGS85 = S85
 *               PE90 = P90
 *       User defined = 999
 *     IHO datum code = [varies, from IHO S-60, appendices B and C]
 *            Unknown = null
 * (5) Latitude and longitude values are always positive.  Altitude offsets may
 *     be positive or negative.
 * (6) IEC 61162-1 and NMEA 0183 contain specific requirements for frequency
 *     of transmitting this sentence.
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class DatumDTM extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 40;
   final static int MIN_FIELD_COUNT = 9;
   final static int MAX_FIELD_COUNT = 9;

   final static int [] validTalkerID = {
      SentenceTypes.TALKER_EC,
      SentenceTypes.TALKER_EI,
      SentenceTypes.TALKER_LC,
      SentenceTypes.TALKER_GN,
      SentenceTypes.TALKER_GP,
      SentenceTypes.TALKER_IN,
      SentenceTypes.TALKER_SN
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
      return SentenceTypes.FORMATTER_DTM;
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
      return getManualSentence(); // Temporary until processing is completed.
   }
   
   /**
    * Get sentence based upon manual data.
    *
    * @return Valid simulation-based sentence.
    */
   public String getManualSentence () {
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT20) {
         String sentence = super.getTalkerID () + "DTM";
         sentence += ',' + super.dataFactory.getDatumLocalCode () +
                     ',' + super.dataFactory.getDatumLocalSubdivisionCode () +
                     ',' + super.dataFactory.getLatitudeOffset () +
                     ',' + super.dataFactory.getLatitudeOffsetDirection () +
                     ',' + super.dataFactory.getLongitudeOffset () +
                     ',' + super.dataFactory.getLongitudeOffsetDirection () +
                     ',' + super.dataFactory.getAltitudeOffset () +
                     ',' + super.dataFactory.getDatumReferenceCode ();


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
   public String getRandomSentence () {
      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT20) {
         int testVal = SentenceTools.getRandomDigit ();
         String sentence = "INDTM";
         sentence += ",W84" +
                     ',' +
                     ",0." + testVal +
                     ",N" +
                     ",0." + testVal +
                     ",W" +
                     ",0.0" +
                     ",W84";

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
      if (fieldCount < DatumDTM.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Check for maximum number of fields
      if (fieldCount > DatumDTM.MAX_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Sentence has too many fields.")));
         return;
      }
      
      // Test local datum code
      String localDatum = (String) fields.get(1);
      if ((localDatum.length () != 0) &&
          (localDatum.length () != 3)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Local datum field has incorrect number of characters.")));
      }
      else {
         if (localDatum.length () != 3) {
            String [] datums = SentenceData.DATUM_REFERENCE_LIST;
            boolean datumMatch = false;
            for (int i = 0; i < datums.length; i++) {
               if (localDatum.equalsIgnoreCase (datums[i])) {
                  datumMatch = true;
               }
            }

            if (!datumMatch) {
               errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_ILLOGICAL,
                  new String ("Reference datum is not recognized.")));
            }
         }
      }
      
      // Test local datum subdivision code
      testString = (String) fields.get(2);
      if (((testString.length () != 1) || (localDatum.length () != 3)) &&
           (testString.length () != 0)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Local datum subdivision code has incorrect number of characters.")));
      }
      
      // Test latitude offset, minutes
      testString = (String) fields.get(3);
      double latitudeOffset = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if (!Double.isNaN (latitudeOffset)) {
         if ((latitudeOffset < 0.0) ||
             (latitudeOffset > 60.0)) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Latitude offset out of range.")));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Latitude offset can not be null or contain non-numerals.")));
      }
            
      // Test latitude offset, 'N' or 'S'
      testString = (String) fields.get(4);
      SentenceTools.parseLatitudeHemisphere (testString.toCharArray (), errorLog);
      
      // Test longitude offset, minutes
      testString = (String) fields.get(5);
      double longitudeOffset = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if (!Double.isNaN (longitudeOffset)) {
         if ((longitudeOffset < 0.0) ||
             (longitudeOffset > 60.0)) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Longitude offset out of range.")));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Longitude offset can not be null or contain non-numerals.")));
      }
      
      // Test longitude offset, 'E' or 'W'
      testString = (String) fields.get(6);
      SentenceTools.parseLongitudeHemisphere (testString.toCharArray (), errorLog);
      
      // Test altitude offset, meters
      testString = (String) fields.get(7);
      double altitudeOffset = SentenceTools.parseNumber (testString.toCharArray (), errorLog);
      if (!Double.isNaN (altitudeOffset)) {
         if ((altitudeOffset < -50.0) ||
             (altitudeOffset > 50.0)) {
                errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
                  new String ("Altitude offset out of range.")));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Altitude offset can not be null or contain non-numerals.")));
      }
      
      // Test reference datum
      testString = (String) fields.get(8);
      if (testString.length () == 3) {
         String [] datums = SentenceData.DATUM_REFERENCE_LIST;
         boolean datumMatch = false;
         for (int i = 0; i < datums.length; i++) {
            if (testString.equalsIgnoreCase (datums[i])) {
               datumMatch = true;
            }
         }
         
         if (!datumMatch) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               new String ("Reference datum is not recognized.")));
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            new String ("Reference datum has incorrect number of characters.")));
      }
   }
}
/*
 * Revision History:
 *
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */