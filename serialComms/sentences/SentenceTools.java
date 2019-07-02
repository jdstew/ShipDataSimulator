/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentenceTools.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences;

import java.util.*;
import java.text.*;
import serialComms.sentences.analysis.*;
/**
 * This staic class is used in processing received and trasmitted sentences.
 *
 * @author Jeff Stewart
 * @version 1.0.0.3, 2005-02-02
 */
public abstract class SentenceTools {
   /** The carriage return character. */
   public static final char CR = 13;
   /** The line feed character. */
   public static final char LF = 10;
   
   public static final char NEGATIVE_SIGN = 0x2D;
   public static final char POSITIVE_SIGN = 0x2B;

   
   static Random numGen = new Random(System.currentTimeMillis ());
   
   /** 
    * Gets a random digit (0-9) for simulating test data.
    *
    * @return A digit (0-9).
    */
   public static int getRandomDigit (){
      return numGen.nextInt (10);
   }

   /**
    * Calculate NMEA 0183/IEC 61162-1 checksum value from sub-sentence
    *
    * @param innerSentence sub-sentence (between '$' or '!' and '*' characters)
    * @return two-character checksum value (ASCII characters as a string)
    */
   public static String getChecksum (String innerSentence) {
      char [] innerChars = innerSentence.toCharArray ();
      int checksum = 0;    // checksum value (only low-order bits used, 8-bit)
      int csHighChunk = 0; // checksum AND-ed by hex F0 and right shifted 4 bits
      int csLowChunk = 0;  // checksum AND-ed by hex 0F
      String checksumString;
     
      // Exclusive or (XOR) lower 8 bits of sentence characters
      for (int i = 0; i < innerChars.length; i++) {
         checksum = (innerChars[i] & 0x00FF) ^ checksum;
      }
      
      // Convert resultant checksum value to high/low order ASCII characters
      csHighChunk = ((checksum & 0x00F0) >> 4);
      csLowChunk = (checksum & 0x000F);
      checksumString = Integer.toHexString (csHighChunk) + Integer.toHexString (csLowChunk);
      return checksumString.toUpperCase ();
   }

  /**
    * Determines if NMEA 0183/IEC 61162-1 checksum value is correct
    *
    * @param testSentence sub-sentence (between '$' or '!' and '*' characters)
    * @param checksum two-character checksum string
    * @return true if computed checksum matches provided checksum
    */
   public static boolean isChecksumCorrect (String testSentence, String checksum) {
      String testChecksum = SentenceTools.getChecksum (testSentence);
      if (testChecksum.compareTo (checksum) == 0) {
         return true; // computed checksum matches provided checksum string
      }   
      else {
         return false;
      }  
   }
  
   /** 
    * Parses a latitude from a character array.
    *<pre>
    * A valid latitude is defined as:
    *    characters: lll1[.[11ll]]
    *      position: 0123 4 5678
    *</pre>
    *
    *<pre>
    * Rules:
    *   1. Positions 0-3 are required, including leading zeros
    *   2. Position 4 is optional
    *   3. If positions of 5 or higher are used, then the decimal (position 4)
    *      is required.
    *   4. Only digits, and one decimal (in position 4), are legal
    *</pre>
    * @param latitude a character array representing a text latitude
    * @param errorLog records errors received during processing
    * @return value of latitude (+90.0 to -90.0)
    */
   public static double parseLatitude (char [] latitude, SentenceErrorLog errorLog) {
      double latitudeValue;
      double valueDivisor = 600.0;
      
      if (latitude.length < 4) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            "Latitude data incomplete - missing degree or minute values. ")); //LATITUDE_INCOMPLETE
         return Double.NaN;
      }
      else {
         for (int i = 0; i < 4; i++) {
            if ((latitude[i] < 0x30) | (latitude[i] > 0x39)) {
               errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_ILLEGAL_CHAR,
                  "A non-number character was found in latitude. ", 
                  latitude[i])); //LATITUDE_INCORRECT_CHARACTER
               return Double.NaN;
            }
         }
         
         // Latitude degrees must be less than 90
         if ((latitude[0] > 0x38) & (latitude[0] < 0x3A)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_OUT_OF_RANGE,
               "Latitude degree value out of range. ")); //LATITUDE_DEGREE_OUT_OF_RANGE
            return Double.NaN; 
         }
         
         // Latitude minutes must be less than 60
         if ((latitude[2] > 0x35) & (latitude[2] < 0x3A)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_OUT_OF_RANGE,
               "Latitude minute value out of range. ")); //LATITUDE_MINUTE_OUT_OF_RANGE
            return Double.NaN; 
         }
         
         latitudeValue = 
            Character.getNumericValue (latitude[0]) * 10.0 +
            Character.getNumericValue (latitude[1]) +
            Character.getNumericValue (latitude[2]) / 6.0 +
            Character.getNumericValue (latitude[3]) / 60.0;
         
         if (latitude.length < 5) {
            return latitudeValue;
         }
         
         // 5th character may only be a decimal
         if (latitude[4] != 0x2E) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_ILLEGAL_CHAR,
               "A non-decimal character was found in latitude. ",
               latitude[4])); //LATITUDE_NON_DECIMAL
            return Double.NaN;                  
         }
         else {
            if (latitude.length < 9) {
               errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_PRECISION,
                  "Latitude is imprecise.")); //LATITUDE_IMPRECISE
            }
            for (int i = 5; i < latitude.length; i++) {
               if ((latitude[i] < 0x30) | (latitude[i] > 0x39)) {
                  errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_ILLEGAL_CHAR,
                     "A non-number character was found in latitude.",
                     latitude[i])); //LATITUDE_INCORRECT_CHARACTER
                  return Double.NaN;
               }
               else {
                  latitudeValue += Character.getNumericValue (latitude[i]) /
                     valueDivisor;
                  valueDivisor = valueDivisor * 10.0;
               }
            }
            
            return latitudeValue;
         }
      }
   }
   
   /**
    * Parse latitude hemisphere character.
    *
    * @param latHemisphere value to parse
    * @param errorLog  records errors received during processing
    * @return parsed value, if successful
    */   
   public static char parseLatitudeHemisphere (char [] latHemisphere, SentenceErrorLog errorLog) {
      if (latHemisphere.length == 1) {
         char hemisphere = latHemisphere[0];
         if ((hemisphere == 'N') || (hemisphere == 'S')) {
            return hemisphere;
         }
         else {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_HEMISPHERE,
               "Latitude hemisphere not recognzed.", hemisphere)); //LATITUDE_HEMISPHERE_UNRECOGNIZED
            return 0;
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_HEMISPHERE,
            "Latitude hemisphere field has incorrect number of characters.")); //LATITUDE_HEMISPHERE_OVERRUN
         return 0;
      }
   }
   
   /**
    * Converts a latitude value into a text reprsentation of the latitude.
    *
    * @param latitude value of latitude (+90.0 to -90.0).
    * @return text representation of the latitude.
    */ 
   public static String getLatitudeString (double latitude) {
      DecimalFormat twoDigitDegreeForm = new DecimalFormat("00");
      DecimalFormat fourDigitMinuteForm = new DecimalFormat("00.0000");
      return 
         twoDigitDegreeForm.format ((int)Math.abs(latitude)) +
         fourDigitMinuteForm.format (Math.abs(latitude % 1.0) * 60.0);  
   }
   
   /** 
    * Parses a longitude from a character array.
    *<pre>
    * A valid longitude is defined as:
    *    characters: yyyyy[.[yyyy]]
    *      position: 01234 5 6789
    *</pre>
    *
    *<pre>                        
    * Rules:
    *   1. Positions 1-4 are required, including leading zeros
    *   2. Position 5 is optional
    *   3. If positions of 6 or higher are used, then the decimal (position 5)
    *      is required.
    *   4. Only digits, and one decimal (in position 5), are legal
    *</pre>
    * @param longitude a character array representing a text longitude
    * @param errorLog records errors received during processing
    * @return value of longitude (+180.0 to -180.0)
    */
   public static double parseLongitude (char [] longitude, SentenceErrorLog errorLog) {
      double longitudeValue;
      double valueDivisor = 600.0;
      
      if (longitude.length < 5) {        
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            "Longitude data incomplete - missing degree or minute values."));//LONGITUDE_INCOMPLETE
         return Double.NaN;
      }
      else{
         for (int i = 0; i < 5; i++) {
            if ((longitude[i] < 0x30) | (longitude[i] > 0x39)) {
               errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_ILLEGAL_CHAR,
                  "A non-number character was found in longitude.",
                  longitude[i]));//LONGITUDE_INCORRECT_CHARACTER
               return Double.NaN;
            }
         }
         
         // Longitude degree must be less than 180
         if (longitude[0] > 0x30) {
            if ((longitude[0] > 0x31) | (longitude[1] > 0x37)) {
               errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_OUT_OF_RANGE,
                  "Longitude degree value out of range."));//LONGITUDE_DEGREE_OUT_OF_RANGE
               return Double.NaN;
            }
         }
         
         // Longitude minute must be less than 60
         if ((longitude[3] > 0x35) & (longitude[3] > 0x35)) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_OUT_OF_RANGE,
               "Longitude minute value out of range."));//LONGITUDE_MINUTE_OUT_OF_RANGE
            return Double.NaN; 
         }
         
         longitudeValue = 
            Character.getNumericValue (longitude[0]) * 100.0 +
            Character.getNumericValue (longitude[1]) * 10.0 +
            Character.getNumericValue (longitude[2]) +
            Character.getNumericValue (longitude[3]) / 6.0 +
            Character.getNumericValue (longitude[4]) / 60.0;
         
         if (longitude.length < 6) {
            return longitudeValue;
         }
         
         // 6th character may only be a decimal
         if (longitude[5] != 0x2E) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_ILLEGAL_CHAR,
               "A non-decimal character was found in longitude.",
               longitude[5]));//LONGITUDE_NON_DECIMAL
            return Double.NaN;                  
         }
         else {
            if (longitude.length < 10) {
               errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_PRECISION,
                  "Longitude is imprecise."));//LONGITUDE_IMPRECISE
            }
            for (int i = 6; i < longitude.length; i++) {
               if ((longitude[i] < 0x30) | (longitude[i] > 0x39)) {
                  errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_ILLEGAL_CHAR,
                     "A non-number character was found in longitude.",
                     longitude[i]));//LONGITUDE_INCORRECT_CHARACTER
                  return Double.NaN;
               }
               else {
                  longitudeValue += Character.getNumericValue (longitude[i]) /
                     valueDivisor;
                  valueDivisor = valueDivisor * 10.0;
               }
            }
            return longitudeValue;
         }
      }
   }
   
   /**
    * Parse latitude hemisphere character.
    *
    * @param longHemisphere value to parse
    * @param errorLog records errors received during processing
    * @return parsed value, if successful
    */   
   public static char parseLongitudeHemisphere (char [] longHemisphere, SentenceErrorLog errorLog) {
      if (longHemisphere.length == 1) {
         char hemisphere = longHemisphere[0];
         if ((hemisphere == 'E') || (hemisphere == 'W')) {
            return hemisphere;
         }
         else {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_HEMISPHERE,
               "Latitude hemisphere not recognzed.", hemisphere));//LONGITUDE_HEMISPHERE_UNRECOGNIZED
            return 0;
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_POSITION_HEMISPHERE,
            "Longitude hemisphere field has incorrect number of characters."));//LONGITUDE_HEMISPHERE_OVERRUN
         return 0;
      }
   }
   
   /**
    * Converts a longitude value into a text reprsentation of the longitude.
    *
    * @param longitude value of longitude (+180.0 to -180.0)
    * @return text representation of the longitude
    */
   public static String getLongitudeString (double longitude) {
      DecimalFormat threeDigitDegreeForm = new DecimalFormat("000");
      DecimalFormat fourDigitMinuteForm = new DecimalFormat("00.0000");
      return 
         threeDigitDegreeForm.format ((int)Math.abs(longitude)) +
         fourDigitMinuteForm.format (Math.abs(longitude % 1.0) * 60.0);  
   }

   /**
    * Parses a UTC value from a character array.
    *<pre>
    * A valid UTC is defined as:
    *    characters: hhmmss[.[sss]]
    *      position: 012345 6 789
    *</pre>
    *<pre>                          
    * Rules:
    *   1. Positions 5 are required, including leading zeros
    *   2. Position 6 is optional
    *   3. If positions of 7 or higher are used, then the decimal (position 6)
    *      is required.
    *   4. Only digits, and one decimal (in position 6), are legal
    *</pre>
    * @param timeUTC a character array representing the UTC text
    * @param errorLog records errors received during processing
    * @return the time and date value in milliseconds
    */ 
   public static long parseUTC (char [] timeUTC, SentenceErrorLog errorLog) {
      int hours;
      int minutes;
      int seconds;
      int milliseconds = 0;
      int milliDivisor = 1000;
      
      if (timeUTC.length < 6) {        
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            "UTC data incomplete - hour, minute, or second data.")); //UTC_INCOMPLETE
         return -1;
      }
      else{
         // Validate numerical values for first 6 digits
         for (int i = 0; i < 6; i++) {
            if ((timeUTC[i] < 0x30) | (timeUTC[i] > 0x39)) {
               errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_TIMEDATE_ILLEGAL_CHAR,
                  "A non-number character was found in the UTC.",
                  timeUTC[i])); //UTC_INCORRECT_CHARACTER
               return -1;
            }
         }
         
         // Time hour value must be valid
         if (timeUTC[0] > 0x31) { // ~hours greater than 19
            if (timeUTC[0] < 0x33) { // ~hours less than 30 
               if (timeUTC[1] > 0x33) { // ~hours more than 23
                  errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_TIMEDATE_OUT_OF_RANGE,
                     "Hour value invalid.")); //UTC_HOUR_INCORRECT
                  return -1; 
               }
            }
            else {
               errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_TIMEDATE_OUT_OF_RANGE,
                  "Hour value invalid.")); //UTC_HOUR_INCORRECT
               return -1; 
            }
         }
         
         // Time minute value must be valid
         if (timeUTC[2] > 0x35) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_TIMEDATE_OUT_OF_RANGE,
               "Minute value invalid.",
               timeUTC[2])); //UTC_MINUTE_INCORRECT
            return -1;   
         }
         
         // Time second value must be valid
         if (timeUTC[4] > 0x35) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_TIMEDATE_OUT_OF_RANGE,
               "Second value invalid.",
               timeUTC[4])); //UTC_MINUTE_INCORRECT
            return -1;   
         }
         
         hours = Character.getNumericValue (timeUTC[0]) * 10 + 
            Character.getNumericValue (timeUTC[1]);
         minutes = Character.getNumericValue (timeUTC[2]) * 10 + 
            Character.getNumericValue (timeUTC[3]);
         seconds = Character.getNumericValue (timeUTC[4]) * 10 + 
            Character.getNumericValue (timeUTC[5]);
         
         GregorianCalendar timeValue = new GregorianCalendar ();
         timeValue.set (Calendar.HOUR_OF_DAY, hours);
         timeValue.set (Calendar.MINUTE, minutes);
         timeValue.set (Calendar.SECOND, seconds);
         
         if (timeUTC.length < 7) {
            return timeValue.getTimeInMillis ();
         }
         
         // 7th character may only be a decimal
         if (timeUTC[6] != 0x2E) {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_TIMEDATE_ILLEGAL_CHAR,
               "A non-decimal character was found in time.",
               timeUTC[6])); //UTC_NON_DECIMAL
            return -1;                  
         }
         else {
            for (int i = 7; i < timeUTC.length; i++) {
               if ((timeUTC[i] < 0x30) | (timeUTC[i] > 0x39)) {
                  errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_TIMEDATE_ILLEGAL_CHAR,
                     "A non-number character was found in time.",
                     timeUTC[i])); //UTC_INCORRECT_CHARACTER
                  return -1;
               }
               else {
                  milliseconds += timeUTC[i] * milliDivisor;
                  milliDivisor /= 10;
               }
            }
         }

         timeValue.set (Calendar.MILLISECOND, milliseconds);
         return timeValue.getTimeInMillis ();
      }
   }
   
   /**
    * Converts a UTC time value into a text representation
    * 
    * @param timeDate the value of time to convert
    * @param zoneHours the time zone value in hours to convert from UTC
    * @param zoneMinutes the time zone value in minutes to convert from UTC
    * @return the text representation of the time value    *
    */
   public static String getUTCString (Date timeDate, int zoneHours, int zoneMinutes) {
      DecimalFormat twoDigitForm = new DecimalFormat("00");
      DecimalFormat threeDigitForm = new DecimalFormat("000");
      Calendar calendarObj = new GregorianCalendar();
      calendarObj.setTime (timeDate);
      calendarObj.add (Calendar.HOUR_OF_DAY, -zoneHours);
      calendarObj.add (Calendar.MINUTE, -zoneMinutes);
      return 
         twoDigitForm.format (calendarObj.get(Calendar.HOUR_OF_DAY)) + 
         twoDigitForm.format (calendarObj.get(Calendar.MINUTE)) + 
         twoDigitForm.format (calendarObj.get(Calendar.SECOND)) + "." +
         threeDigitForm.format (calendarObj.get(Calendar.MILLISECOND));
      
   }
   
   /**
    * Parse bearing data.
    *
    * @param number character array representing a bearing
    * @param errorLog records errors received during processing
    * @return actual bearing value, Double.NaN if unsuccessful
    */   
   public static double parseBearing (char [] number, SentenceErrorLog errorLog) {
      // Test for null length field
      if (number.length == 0) {
         return Double.NaN;
      }
      
      double bearing = parseNumber (number, errorLog);
      
      // Test bearing for 0.0 < value < 360.0
      if ((bearing < 0.0) || (bearing >= 360.0)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_HEADING_OUT_OF_RANGE)); //HEADING_OUT_OF_RANGE
         return Double.NaN;
      }
      
      // Test for 0.0 suspect value
      if (bearing < 0.001) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_SUSPICOUS,
            "Check bearing/heading (value is 0.0)."));//HEADING_SUSPICOUS
      }
      
      return bearing;
   }
   
   /** 
    * Parses a number from a character array.
    *<pre>
    * A valid number is defined as:
    *    characters: [-][0][#][.[#][0]]
    *      position:  0  1  2  3 4  5
    *</pre>
    *
    *<pre>                
    * Rules:
    *   1. A negative symbol is valid (at position 0)
    *   2. Leading and trailing zeros are valid (in positions 1 and 5)
    *   3. Digit in position is optional (for values less than 1)
    *   4. The decimal place is optional (at position 3)
    *   5. Fractions are optional
    *</pre>
    * @param number the character array representing the number
    * @param errorLog records errors received during processing
    * @return the value of the parsed number text 
    */ 
   public static double parseNumber (char [] number, SentenceErrorLog errorLog) {
      boolean decimalFound = false;
      if (number.length == 0) {
         return Double.NaN;
      }
      
      for (int i = 0; i < number.length; i++) {
         // Check for non-numeric characters
         if ((number[i] < 0x30) || (number[i] > 0x39)) {
            // Check for negative sign in wrong position
            if ( (number[i] == NEGATIVE_SIGN) || (number[i] == POSITIVE_SIGN) ){
               if (i > 0) {
                  errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_CHARACTER_NOT_VALID,
                     "An illegal negative or positive sign was found.",
                     number[i]));//NUMBER_INCORRECT_NEGATIVE
                  return Double.NaN; 
               }
               else {
                  continue;
               }
            }
            // Check for multiple decimals
            if (number[i] == 0x2E) {
               if (decimalFound) {
                  errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_CHARACTER_NOT_VALID,
                     "Too many decimals were found.",
                     number[i])); //NUMBER_TOO_MANY_DECIMALS
                  return Double.NaN;  
               }
               else { 
                  decimalFound = true;
                  continue;
               }
            }
            
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_CHARACTER_NOT_VALID,
               "Illegal non-number character.",
               number[i])); //NUMBER_NON_NUMBER
            return Double.NaN; 
         }
      }
      return Double.parseDouble (new String(number));
   }
   
 /*
   private static String getNumberString (double number) {
      DecimalFormat numberForm = new DecimalFormat("0.0###");
      return numberForm.format (number);
   }
  */
   
   /**
    * Parse the validity character
    *
    * @param statusField status value
    * @param errorLog records errors received during processing
    * @return validity character, if successfully parsed.
    */   
   public static char parseStatus (char [] statusField, SentenceErrorLog errorLog) {
      if (statusField.length == 1) {
         char status = statusField[0];
         if ((status == 'A') || (status == 'V')) {
            return status;
         }
         else {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               "Status field not recognzed.", status)); //STATUS_NOT_RECOGNIZED
            return 0;
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            "Status field has incorrect number of characters.")); //STATUS_TOO_MANY_CHARACTERS
         return 0;
      }
   }
      
   /**
    * Parse a positioning device operating mode.
    *
    * @param modeField operating mode
    * @param errorLog records errors received during processing
    * @return operaing mode character, if successfully parsed
    */   
   public static char parseOperatingMode (char [] modeField, SentenceErrorLog errorLog) {
      if (modeField.length == 1) {
         char mode = modeField[0];
         if ((mode == 'A') || 
             (mode == 'D') ||
             (mode == 'E') ||
             (mode == 'M') ||
             (mode == 'S') ||
             (mode == 'N')) {
            return mode;
         }
         else {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               "Operating mode field not recognzed.", mode)); //MODE_NOT_RECOGNIZED
            return 0;
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            "Operating mode field has incorrect number of characters.")); //MODE_TOO_MANY_CHARACTERS
         return 0;
      }
   }
   
   /**
    * Parse LORAN-C group repetition rate
    *
    * @param number value to parse
    * @param errorLog records errors received during processing
    * @return value of parsed input
    */   
   public static double parseLoranGRI (char [] number, SentenceErrorLog errorLog) {
      // Test for null length field
      if (number.length == 0) {
         return Double.NaN;
      }
      else if (number.length != 4) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID,
               "Invalid number of characters for GRI.")); //LORAN_GRI_INCORRECT
         return 0;
      }
      
      double loranGRI = parseNumber (number, errorLog);
      
      // Test bearing for 0.0 < value < 360.0
      if ((loranGRI < 7000.0) || (loranGRI > 10000.0)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID,
               "GRI is out of range.")); //LORAN_GRI_INCORRECT
         return loranGRI;
      }
      
      return loranGRI;
   }
   
   /**
    * Parse a LORAN-C time of arrival (Master) or time differnce value of
    * received signals.
    *
    * @param number value to parse
    * @param errorLog records errors received during processing
    * @return value of parsed input
    */   
   public static double parseLoranTOATD (char [] number, SentenceErrorLog errorLog) {
      // Test for null length field
      if (number.length == 0) {
         return Double.NaN;
      }
      
      double loranTD = parseNumber (number, errorLog);
      
      // Test bearing for 0.0 < value < 360.0
      if ((loranTD < 0.0) || (loranTD > 99999.9)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID,
               "TD is out of range.")); //LORAN_TOATD_INCORRECT
         return loranTD;
      }
      
      return loranTD;
   }
   
   
   /**
    * Parse the signal envelope and signal-to-noise ration values.
    *
    * @param number value to parse
    * @param errorLog records errors received during processing
    * @return value of parsed input
    */   
   public static double parseLoranECDSNR (char [] number, SentenceErrorLog errorLog) {
      // Test for null length field
      if (number.length == 0) {
         return Double.NaN;
      }
      else if (number.length != 3) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID,
               "Invalid number of characters for ECD or SNR."));//LORAN_ECDSNR_INCORRECT
         return 0;
      }
      
      double loranQuality = parseNumber (number, errorLog);
      
      // Test bearing for 0.0 < value < 360.0
      if ((loranQuality < 0.0) || (loranQuality > 999.9)) {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID,
               "ECD or SNR is out of range."));//LORAN_ECDSNR_INCORRECT
         return loranQuality;
      }
      
      return loranQuality;
   }
      
   /**
    * Parse the loran status character. 
    *
    * @param modeField the value to parse
    * @param errorLog records errors received during processing
    * @return the mode character parsed, if successful
    */   
   public static char parseLoranStatus (char [] modeField, SentenceErrorLog errorLog) {
      if (modeField.length == 1) {
         char mode = modeField[0];
         if (mode == 'B') {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_INVALID,
               "Blink warning present."));//LORAN_BLINK
            return mode;
         }
         else if (mode == 'C') {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               "Cycle warning present."));//LORAN_CYCLE
            return mode;
         }
         else if (mode == 'S') {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               "Signal-to-Noise (SNR) warning present.")); //LORAN_SNR
            return mode;
         }
         else if (mode == 'A') {
            return mode;
         }
         else {
            errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
               "Signal status not recognzed.", mode)); //LORAN_STATUS
            return '?';
         }
      }
      else {
         errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_FIELD_INVALID,
            "Signal status field has incorrect number of characters.")); //LORAN_STATUS_TOO_MANY_CHARACTERS
         return '?';
      }
   }
 /*
   /
    * Converts a character array to a string.
    *
    * @param charArray Character array.
    * @return String, of received characters.
    /
   private String charArrayToString (char [] charArray){
      if (charArray == null){
         return "";
      }
      else {
         return new String(charArray);
      }
   }
  */
  
  /*
   /
    * Converts a string into a character array.
    *
    * @param str String object.
    * @return A character array.
    /
   private char [] stringToCharArray (String str){
      if (str == null) {
         return null;
      }
      else {
         int length = str.length ();
         char [] charArray = new char [length];
         for (int i = 0; i < length; i++) {
            charArray[i] = str.charAt (i);
         }
         return charArray;
      }
   }
   */
}
/*
 * Version history:
 *
 * 1.0.0.3 - Allowed positive sign (+) character in parseNumber() method.
 */

