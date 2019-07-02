/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 *
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: RayNav750.java
 * Created: 2005-02-10, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * RayNav750 color plotter and remote interface.
 *
 * The following 131 byte data sentence is transmitted every four seconds
 * when the status numbers are above 6.  The data is transmitted as a mix
 * of ASCII, 8-bit integers, 16-bit integers (2-byte), and special characters.
 *
 *<pre>
 *          1         2         3         4         5         6
 * 12345678901234567890123456789012345678901234567890123456789012345678
 * _Cxxxxxxxxxxxxxxxxxxxxxxxxxaxxxxxxxxxxxllllllxyyyyyyyxllllllxyyyyyyy
 * |||--||----||----||-||----|||-||-||--|||----|||-----|||----|||-----|- Dest Lon (see note 2)
 * |||--||----||----||-||----|||-||-||--|||----|||-----|||----||- Dest Lon Hemi (0=E, 1=W)
 * |||--||----||----||-||----|||-||-||--|||----|||-----|||----|- Dest Lat (see note 2)
 * |||--||----||----||-||----|||-||-||--|||----|||-----||- Dest Lat Hemi (0=N, 1=S)
 * |||--||----||----||-||----|||-||-||--|||----|||-----|- Source Lon (see note 2)
 * |||--||----||----||-||----|||-||-||--|||----||- Source Lon Hemi (0=E, 1=W)
 * |||--||----||----||-||----|||-||-||--|||----|- Source Lat (see note 2)
 * |||--||----||----||-||----|||-||-||--||-Source Lat Hemi (0=N, 1=S)
 * |||--||----||----||-||----|||-||-||--|- Route (S/D, each as 2 digits)
 * |||--||----||----||-||----|||-||-|- Speed (in knots) (see note 1) 
 * |||--||----||----||-||----|||-|- Course (DDD)
 * |||--||----||----||-||----||- Course mode ('T' or 'M')
 * |||--||----||----||-||----|- SNR (M/S1/S2, each as 2 digits)
 * |||--||----||----||-|- Stage (M/S1/S2)
 * |||--||----||----|- TD2 (see note 1) 
 * |||--||----|- TD1 (see note 1)
 * |||--|- GRI (see note 1) 
 * ||- "C"
 * |- "P"+80 (hex)
 * 
 *                                1         1         1         1
 * 67         8         9         0         1         2         3
 * 901234567890123456789012345678901234567890123456789012345678901
 * axxxxxxxxmmhhxxxassxx__xllllllxyyyyyyyxxxxxxhhmm_xxxxxx000xx00_
 * ||-||---||--||-||||||||||----|||-----||----||--|||||||||-||||||- "Z"+80 (hex)
 * ||-||---||--||-||||||||||----|||-----||----||--|||||||||-|||||- "00"
 * ||-||---||--||-||||||||||----|||-----||----||--|||||||||-|||- GRI (see note 6)
 * ||-||---||--||-||||||||||----|||-----||----||--|||||||||-|- "000"
 * ||-||---||--||-||||||||||----|||-----||----||--||||||||- Status #3
 * ||-||---||--||-||||||||||----|||-----||----||--|||||||- Course (see note 7)
 * ||-||---||--||-||||||||||----|||-----||----||--|||||- Speed (see note 7)
 * ||-||---||--||-||||||||||----|||-----||----||--|||- 12 (hex)
 * ||-||---||--||-||||||||||----|||-----||----||--||- "@"+80 (hex)
 * ||-||---||--||-||||||||||----|||-----||----||--|- hour and minute (HHMM)
 * ||-||---||--||-||||||||||----|||-----||----|- date (YYMMDD)
 * ||-||---||--||-||||||||||----|||-----|- Longitude (see note 2)
 * ||-||---||--||-||||||||||----||- Longitude Hemisphere (0=E, 1=W)
 * ||-||---||--||-||||||||||----|- Latitude (see note 2)
 * ||-||---||--||-|||||||||- Latitude Hemisphere (0=N, 1=S)
 * ||-||---||--||-||||||||- "F"+80 (hex)
 * ||-||---||--||-|||||||- "Z"+80 (hex)
 * ||-||---||--||-||||||- Status #2
 * ||-||---||--||-|||||- Status #1
 * ||-||---||--||-||||- seconds (SS)
 * ||-||---||--||-||- XTE direction ('L' or 'R')
 * ||-||---||--||-|- XTE (NM) (see note 1)
 * ||-||---||--|- Destination TTG (MMHH)
 * ||-||---|- Destination distance (NM) (see note 2)
 * ||-|- Destination bearying 
 * |- Destination mode ('T' or 'M')
 * 
 * Status byte #1
 * 0xx0x0x0
 *  || | |- 1 = no solution
 *  || |- 1 = ASF
 *  ||- 1 = ARV
 *  |- 1 = S2 blink
 * 
 * Status byte #2
 * 0xxxxxxx
 *  |||||||- 1 = M SNR
 *  ||||||- 1 = M cycle
 *  |||||- 1 = S1 SNR
 *  ||||- 1 = S1 cycle 
 *  |||- 1 = S1 blink
 *  ||- 1 = S2 SNR
 *  |- 1 = S2 cycle
 * 
 * Status byte #3 - is the logic OR-ing of M/S1/S2 status bits
 * 000xxx00
 *    |||- 1 = cycle
 *    ||- 1 = SNR
 *    |- 1 = blink
 * 
 * Notes:
 *    (1) Value to 1/10th, decimal removed
 *    (2) Value to 1/100th, decimal removed
 *    (3) MSB of bytes 1, 90, 91, and 131 is '1', all other is '0'
 *    (4) Bytes 3 thru 116 are BCD numbers
 *    (5) Bytes 118 thru 130 are binary numbers
 *    (6) 2-byte binary value of GRI using least significant seven bits in
 *        byte #128 and most significant bits in byte #127
 *    (7) 2-byte binary value of course and speed to 1/10th of unit with
 *        decimal point, least significant 7 bits in second byte and next 7 
 *        bits in the first byte.
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.2.0.0, 2005-02-10
 */
public class RayNav750 extends AbstractSentence {
   final static int MIN_FIELD_COUNT = 1;
   final static int MAX_FIELD_COUNT = 1;   
   
   public static final char PLOTTER_CHARACTER = 0x00D0; // "P"+80 (hex)
   public static final char REMOTE_CHARACTER = 0x00C6; // "F"+80
   public static final char BINARY_CHARACTER = 0x00C0; // "@"+80 (hex)
   public static final char UNKNOWN_CHARACTER = 0x000C; // decimal 12
   public static final char END_CHARACTER = 0x00DA; // "Z"+80
   
   public final static char ASCII_TO_CHAR_VAL = 0x0030;
   
   final static int ESTIMATED_SENTENCE_LENGTH = 131;
   
   Random random = new Random (System.currentTimeMillis ());
   
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
      return SentenceTypes.FORMATTER_RAYNAV750;
   }
   
   /**
    * Validates an acceptable talker ID for this sentence formatter.
    *
    * @param id Talker ID to validate.
    * @return True if valide talker ID.
    */
   public boolean isSentenceIDValid (int id) {
      return true;
   }
   
   /** 
    * This method strips characters and decimals, and packages the result
    * in a set length for use in the RayNav750 sentence.  Input data that is
    * too long gets truncated, and input data that is too short gets padded
    * with zeros.
    */
   String stripNonNumbers (String dataField, int dataFieldLength) {
      CharacterBuffer charBuffer = new CharacterBuffer(dataFieldLength);
      
      for (int i = 0; i < dataField.length (); i++) {
         char testChar = dataField.charAt (i);
         if ( (testChar > 0x002F) && (testChar < 0x003A) ) {
            charBuffer.append (testChar);
            if (charBuffer.length () == dataFieldLength) {
               break;
            }
         }
      }
      
      if (charBuffer.length () < dataFieldLength) {
         for (int i = 0; i <= (dataFieldLength - charBuffer.length ()); i++) {
            charBuffer.append ('0');
         }
      }
      
      return charBuffer.toString ();
   }
   
   /**
    * Get sentence based upon simulator data.  Manual data is inserted where
    * simulated data is unavailable.
    *
    * @return Valid simulation-based sentence.
    */
   public String getSimulatorSentence () {
      char status1 = 0x0008;  // ASF bit on.
      char status2 = 0x0000;
      char status3 = 0x0000;
      
      String mStatus =  dataFactory.getLoranStatus (0);
      if (mStatus.equalsIgnoreCase ("C")) {
         status2 |= 0x0002; // 0000 0010
         status3 |= 0x0004; // 0000 0100
      }
      else if (mStatus.equalsIgnoreCase ("S")) {
         status2 |= 0x0001; // 0000 0001
         status3 |= 0x0008; // 0000 1000
      }
      
      String s1Status =  dataFactory.getLoranStatus (1);
      if (s1Status.equalsIgnoreCase ("B")) {
         status2 |= 0x0010; // 0000 0001
         status3 |= 0x0010; // 0001 0000
      }
      else if (s1Status.equalsIgnoreCase ("C")) {
         status2 |= 0x0008; // 0000 0010
         status3 |= 0x0004; // 0000 0100
      }
      else if (s1Status.equalsIgnoreCase ("S")) {
         status2 |= 0x0004; // 0000 0001
         status3 |= 0x0008; // 0000 1000
      }
      
      String s2Status =  dataFactory.getLoranStatus (2);
      if (s2Status.equalsIgnoreCase ("B")) {
         status1 |= 0x0040; // 0000 0001
         status3 |= 0x0010; // 0001 0000
      }
      else if (s2Status.equalsIgnoreCase ("C")) {
         status2 |= 0x0040; // 0000 0010
         status3 |= 0x0004; // 0000 0100
      }
      else if (s2Status.equalsIgnoreCase ("S")) {
         status2 |= 0x0020; // 0000 0001
         status3 |= 0x0008; // 0000 1000
      }
      
      String seconds = "00";
      if (dataFactory.getSimulatedUTC ().length () >= 6) {
         seconds = stripNonNumbers(
            dataFactory.getSimulatedUTC ().substring (4, 5), 2);
      }
      
      char latHemisphere = '0';
      if (dataFactory.getSimulatedLatitudeHemisphere ().equalsIgnoreCase ("S")) {
         latHemisphere = '1';
      }

      char lonHemisphere = '0';
      if (dataFactory.getSimulatedLongitudeHemisphere ().equalsIgnoreCase ("W")) {
         lonHemisphere = '1';
      }
      
      String shortYear = "00";
      if (dataFactory.getSimulatedYear ().length () >= 4) {
         shortYear = stripNonNumbers(
            dataFactory.getSimulatedYear ().substring (2, 3), 2);
      }
      
      String binarySpeed;
      try {
         float speedAsFloat = Float.parseFloat (dataFactory.getSimulatedSpeedOverGroundInKnots ());
         int speedAsInt = (int) (speedAsFloat * 10);
         int secondByte = speedAsInt & 0x007F;
         int firstByte = (speedAsInt>>>7) & 0x007F;
         binarySpeed = "" + (char) firstByte + (char) secondByte;
      }
      catch (NumberFormatException error) {
         binarySpeed = "00";
      }
      
      String binaryCourse;
      try {
         float courseAsFloat = Float.parseFloat (dataFactory.getSimulatedHeadingTrueOverGround ());
         int courseAsInt = (int) (courseAsFloat * 10);
         int secondByte = courseAsInt & 0x007F;
         int firstByte = (courseAsInt>>>7) & 0x007F;
         binaryCourse = "" + (char) firstByte + (char) secondByte;
      }
      catch (NumberFormatException error) {
         binaryCourse = "00";
      }
      
      String binaryGRI;
      try {
         int griAsInt = Integer.parseInt (dataFactory.getLoranGRI ());
         int secondByte = griAsInt & 0x007F;
         int firstByte = (griAsInt>>>7) & 0x007F;
         binaryGRI = "" + (char) firstByte + (char) secondByte;
      }
      catch (NumberFormatException error) {
         binaryGRI = "0000";
      }
      
      String sentence = "" + PLOTTER_CHARACTER + 'C' +
         stripNonNumbers(dataFactory.getLoranGRI (), 4) + // GRI
         stripNonNumbers(dataFactory.getLoranTOATD (1), 6) + // S1
         stripNonNumbers(dataFactory.getLoranTOATD (2), 6) + // S2
         "666" + // Stage
         stripNonNumbers(dataFactory.getLoranSNR (0), 2) + // M SNR
         stripNonNumbers(dataFactory.getLoranSNR (1), 2) + // S1 SNR
         stripNonNumbers(dataFactory.getLoranSNR (2), 2) + // S2 SNR
         "T" +  stripNonNumbers(dataFactory.getSimulatedHeadingTrueOverGround (), 3) + // T or M and Course
         stripNonNumbers(dataFactory.getSimulatedSpeedOverGroundInKnots (), 3) + // Speed
         "0000" + // Route source and destination
         "0000000" + "00000000" + // Source Lat/Lon
         "0000000" + "00000000" + // Destination Lat/Lon
         "T" + "000" + "00000" + // Destination bearing and distance
         "0000" + // Destination TTG
         "000" + "R" + // XTE and direction
         seconds + // Seconds (from Time, see below)
         status1 + status2 + // Status 1 and status 2 bytes
         END_CHARACTER + REMOTE_CHARACTER +
         latHemisphere + 
         stripNonNumbers(dataFactory.getSimulatedLatitude (), 6) + // Latitude
         lonHemisphere + 
         stripNonNumbers(dataFactory.getSimulatedLongitude (), 7) + // Longitude
         shortYear + // Date, year
         stripNonNumbers(dataFactory.getSimulatedMonth (), 2) + // Date, month
         stripNonNumbers(dataFactory.getSimulatedDay (), 2) + // Date, day
         stripNonNumbers(dataFactory.getSimulatedUTC (), 4) + // Time
         BINARY_CHARACTER + UNKNOWN_CHARACTER + 
         binarySpeed + // Speed
         binaryCourse + // Course
         status3 + // Status 3
         "000" +
         binaryGRI + // GRI, binary
         "00" + END_CHARACTER;
      
      return convertASCIItoNumeric (sentence);
   }
   
   /**
    * Get sentence based upon manual data.
    *
    * @return Valid simulation-based sentence.
    */
   public String getManualSentence () {
      char status1 = 0x0008;  // ASF bit on.
      char status2 = 0x0000;
      char status3 = 0x0000;
      
      String mStatus =  dataFactory.getLoranStatus (0);
      if (mStatus.equalsIgnoreCase ("C")) {
         status2 |= 0x0002; // 0000 0010
         status3 |= 0x0004; // 0000 0100
      }
      else if (mStatus.equalsIgnoreCase ("S")) {
         status2 |= 0x0001; // 0000 0001
         status3 |= 0x0008; // 0000 1000
      }
      
      String s1Status =  dataFactory.getLoranStatus (1);
      if (s1Status.equalsIgnoreCase ("B")) {
         status2 |= 0x0010; // 0000 0001
         status3 |= 0x0010; // 0001 0000
      }
      else if (s1Status.equalsIgnoreCase ("C")) {
         status2 |= 0x0008; // 0000 0010
         status3 |= 0x0004; // 0000 0100
      }
      else if (s1Status.equalsIgnoreCase ("S")) {
         status2 |= 0x0004; // 0000 0001
         status3 |= 0x0008; // 0000 1000
      }
      
      String s2Status =  dataFactory.getLoranStatus (2);
      if (s2Status.equalsIgnoreCase ("B")) {
         status1 |= 0x0040; // 0000 0001
         status3 |= 0x0010; // 0001 0000
      }
      else if (s2Status.equalsIgnoreCase ("C")) {
         status2 |= 0x0040; // 0000 0010
         status3 |= 0x0004; // 0000 0100
      }
      else if (s2Status.equalsIgnoreCase ("S")) {
         status2 |= 0x0020; // 0000 0001
         status3 |= 0x0008; // 0000 1000
      }
      
      String seconds = "00";
      if (dataFactory.getTimeUTC ().length () >= 6) {
         seconds = stripNonNumbers(
            dataFactory.getTimeUTC ().substring (4, 5), 2);
      }
      
      char latHemisphere = '0';
      if (dataFactory.getLatitudeHemisphere ().equalsIgnoreCase ("S")) {
         latHemisphere = '1';
      }

      char lonHemisphere = '0';
      if (dataFactory.getLongitudeHemisphere ().equalsIgnoreCase ("W")) {
         lonHemisphere = '1';
      }
      
      String shortYear = "00";
      if (dataFactory.getDateYear ().length () >= 4) {
         shortYear = stripNonNumbers(
            dataFactory.getDateYear ().substring (2, 3), 2);
      }
      
      String binarySpeed;
      try {
         float speedAsFloat = Float.parseFloat (dataFactory.getSpeedGroundInKnots ());
         int speedAsInt = (int) (speedAsFloat * 10);
         int secondByte = speedAsInt & 0x007F;
         int firstByte = (speedAsInt>>>7) & 0x007F;
         binarySpeed = "" + (char) firstByte + (char) secondByte;
      }
      catch (NumberFormatException error) {
         binarySpeed = "00";
      }
      
      String binaryCourse;
      try {
         float courseAsFloat = Float.parseFloat (dataFactory.getHeadingTrueGround ());
         int courseAsInt = (int) (courseAsFloat * 10);
         int secondByte = courseAsInt & 0x007F;
         int firstByte = (courseAsInt>>>7) & 0x007F;
         binaryCourse = "" + (char) firstByte + (char) secondByte;
      }
      catch (NumberFormatException error) {
         binaryCourse = "00";
      }
      
      String binaryGRI;
      try {
         int griAsInt = Integer.parseInt (dataFactory.getLoranGRI ());
         int secondByte = griAsInt & 0x007F;
         int firstByte = (griAsInt>>>7) & 0x007F;
         binaryGRI = "" + (char) firstByte + (char) secondByte;
      }
      catch (NumberFormatException error) {
         binaryGRI = "0000";
      }
      
      String sentence = "" + PLOTTER_CHARACTER + 'C' +
         stripNonNumbers(dataFactory.getLoranGRI (), 4) + // GRI
         stripNonNumbers(dataFactory.getLoranTOATD (1), 6) + // S1
         stripNonNumbers(dataFactory.getLoranTOATD (2), 6) + // S2
         "666" + // Stage
         stripNonNumbers(dataFactory.getLoranSNR (0), 2) + // M SNR
         stripNonNumbers(dataFactory.getLoranSNR (1), 2) + // S1 SNR
         stripNonNumbers(dataFactory.getLoranSNR (2), 2) + // S2 SNR
         "T" +  stripNonNumbers(dataFactory.getHeadingTrueGround (), 3) + // T or M and Course
         stripNonNumbers(dataFactory.getSpeedGroundInKnots (), 3) + // Speed
         "0000" + // Route source and destination
         "0000000" + "00000000" + // Source Lat/Lon
         "0000000" + "00000000" + // Destination Lat/Lon
         "T" + "000" + "00000" + // Destination bearing and distance
         "0000" + // Destination TTG
         "000" + "R" + // XTE and direction
         seconds + // Seconds (from Time, see below)
         status1 + status2 + // Status 1 and status 2 bytes
         END_CHARACTER + REMOTE_CHARACTER +
         latHemisphere + 
         stripNonNumbers(dataFactory.getLatitude (), 6) + // Latitude
         lonHemisphere + 
         stripNonNumbers(dataFactory.getLongitude (), 7) + // Longitude
         shortYear + // Date, year
         stripNonNumbers(dataFactory.getDateMonth (), 2) + // Date, month
         stripNonNumbers(dataFactory.getDateDay (), 2) + // Date, day
         stripNonNumbers(dataFactory.getTimeUTC (), 4) + // Time
         BINARY_CHARACTER + UNKNOWN_CHARACTER + 
         binarySpeed + // Speed
         binaryCourse + // Course
         status3 + // Status 3
         "000" +
         binaryGRI + // GRI, binary
         "00" + END_CHARACTER;
      
      return convertASCIItoNumeric (sentence);
   }
   
   /**
    * Get test sentence data. Note, the last digit of select values within
    * the sentence is random.
    *
    * @return Valid test sentence.
    */
   public String getRandomSentence () {
      String sentence = "" + PLOTTER_CHARACTER + 'C' +
         "9960" + // GRI
         "15978" + random.nextInt (9) + // S1
         "41206" + random.nextInt (9) + // S2
         "666" + // Stage
         "5" + random.nextInt (9) + // M SNR
         "5" + random.nextInt (9) + // S1 SNR
         "5" + random.nextInt (9) + // S2 SNR
         "T" + "256" + // Course
         "103" + // Speed
         "0000" + // Route source and destination
         "0000000" + "00000000" + // Source Lat/Lon
         "0000000" + "00000000" + // Destination Lat/Lon
         "T" + "000" + "00000" + // Destination bearing and distance
         "0000" + // Destination TTG
         "000" + "R" + // XTE and direction
         "0" + random.nextInt (9) + // Seconds
         random.nextInt (9) + // Status 1
         random.nextInt (9) + // Status 2
         END_CHARACTER + REMOTE_CHARACTER +
         "036526" + random.nextInt (9) + // North latitude
         "1076211" + random.nextInt (9) + // West longitude
         "050210" + "1200" + // Date and time
         BINARY_CHARACTER + UNKNOWN_CHARACTER + 
         "10" + // speed, binary
         "34" + // course, binary
         random.nextInt (9) + // Status 3
         "000" +
         "58" + // GRI, binary
         "00" + END_CHARACTER;
         
      return convertASCIItoNumeric (sentence);
   }

   /**
    * Converts ASCII numbers to numeric character values.
    * For example '1' (hex 31) is converted to hex 01.
    *
    * @return RayNav750 sentence with number values converted
    */
   String convertASCIItoNumeric (String inputString) {
      char charVal;
      CharacterBuffer characterBuffer = new CharacterBuffer (131);
      for (int i = 0; i < inputString.length (); i++) {
         charVal = inputString.charAt (i);
         if ( (charVal > 0x002F) && (charVal < 0x003A) ) {
            charVal = (char) (charVal - ASCII_TO_CHAR_VAL);
         }
         characterBuffer.append (charVal);
      }
      return characterBuffer.toString ();
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
      // Check for minimum number of fields
      if (fieldCount < RayNav750.MIN_FIELD_COUNT) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INCOMPLETE));
         return;
      }
      
      // Terminate processing in invalid length of sentence
      String sentence = (String) fields.get(0);
      if (sentence.length () != ESTIMATED_SENTENCE_LENGTH) {
         return;
      }
      
      int status1 = (int) sentence.charAt (87);
      int status2 = (int) sentence.charAt (88);
      int status3 = (int) sentence.charAt (122);
      
      // Check for no solution
      if ((status1 & 0x0002) > 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID, 
            "No position solution available."));
      }
      
      // Check for cummulative blink error
      if ((status3 & 0x0010) > 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID, 
            "Overall blink error"));
      }
      
      // Check for S1 blink error
      if ((status2 & 0x0010) > 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID, 
            "S1 blink error"));
      }
      
      // Check for S2 blink error
      if ((status1 & 0x0040) > 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID, 
            "S2 blink error"));
      }
      
      // Check for cumulative cycle error
      if ((status3 & 0x0004) > 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID, 
            "Overall cycle error"));
      }
      
      // Check for Master cycle error
      if ((status2 & 0x0002) > 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID, 
            "Master cycle error"));
      }
      
      // Check for S1 cycle error
      if ((status2 & 0x0008) > 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID, 
            "S1 cycle error"));
      }
      
      // Check for S2 cycle error
      if ((status2 & 0x0040) > 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID, 
            "S2 cycle error"));
      }
      
      // Check for cumulative SNR error
      if ((status3 & 0x0008) > 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID, 
            "Overall SNR error"));
      }
      
      // Check for Master SNR error
      if ((status2 & 0x0001) > 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID, 
            "Master SNR error"));
      }
      
      // Check for S1 SNR error
      if ((status2 & 0x0004) > 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID, 
            "S1 SNR error"));
      }
      
      // Check for S2 SNR error
      if ((status2 & 0x0020) > 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID, 
            "S2 SNR error"));
      }
      
      // Warn if ASF is not used
      if ((status1 & 0x0008) == 0) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_PRECISION, 
            "ASF correction factors are not being used."));
      }
      
      // Construct and check latitude
      
      // Construct and check longitude
      
      // Construct and check date
      
      // Construct and check time
      
      // Cross check GRI array against binary value
      
      // Cross check course array against binary value
      
      // Cross check speed array against binary value
      
   }
   
//   /**
//    * @param args the command line arguments
//    */
//   public static void main (String[] args) {
//      // TODO code application logic here
//      
//      RayNav750 raynav = new RayNav750();
//      
//      String testString = "x3940.95";
//      System.out.println(testString);
//      System.out.println(raynav.stripNonNumbers(testString, 5));
//      
//      testString = "94.5";
//      System.out.println(testString);
//      System.out.println(raynav.stripNonNumbers(testString, 5));
//   }
}