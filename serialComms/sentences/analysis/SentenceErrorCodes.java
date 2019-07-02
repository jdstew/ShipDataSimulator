/*
 * SentenceErrorCodes.java
 *
 * Created on January 24, 2005, 8:11 PM
 */

package serialComms.sentences.analysis;

/**
 *
 * @author  Jeff
 */
public class SentenceErrorCodes {
   /** Received sentence is more than 82 characters. */
   public static final int SENTENCE_OVERRUN = 0;
   /** Received sentence has more than one checksum token [*]. */
   public static final int TOO_MANY_CHECKSUMS = 1;
   /** Sentence start character found at incorrect location. */
   public static final int INCORRECT_START_LOCATION = 2;
   /** Incorrect location of escape character [^]. */
   public static final int INCORRECT_ESCAPE_LOCATION = 3;
   /** Non-hexidecimal digit following escape character. */
   public static final int INCORRECT_ESCAPE_CHARACTER = 4;
   /** Too many characters following checksum character. */
   public static final int TOO_MANY_CHECKSUM_CHARACTERS = 5;
   /** Incorrect location of return character <CR>. */
   public static final int INCORRECT_RETURN_LOCATION = 6;
   /** Incorrect location of line feed character <LF>. */
   public static final int INCORRECT_LINEFEED_LOCATION = 7;
   /** Incorrect address characters. */
   public static final int INCORRECT_ADDRESS_CHARACTER = 8;
   /** Incorrect address field length. */
   public static final int INCORRECT_ADDRESS_LENGTH = 9;
   /** Non-hexidecimal digits checksum character. */
   public static final int INCORRECT_CHECKSUM_CHARACTER = 10;
   /** Incorrect use of reserved character. */
   public static final int INCORRECT_RESERVED_CHARACTER = 11;
   /** Incorrect ASCII character. */
   public static final int INCORRECT_ASCII_CHARACTER = 12;
   /** Talker ID not known. */
   public static final int TALKERID_UNKNOWN = 13;
   /** Sentence type unknown. */
   public static final int SENTENCE_TYPE_UNKNOWN = 14;
   /** Query talker ID not recognized. */
   public static final int QUERY_TALKERID_UNKNOWN = 15;
   /** Missing start character [$|!]. */
   public static final int MISSING_START_CHARACTER = 16;
   /** Sentence missing data fields. */
   public static final int MISSING_DATA_FIELDS = 17;
   /** Checksum value is missing. */
   public static final int MISSING_CHECKSUM = 18;
   /** Calculated checksum does not match received checksum. */
   public static final int INVALID_CHECKSUM = 19;
   /** Sentence length is too short. */
   public static final int TOO_FEW_CHARACTERS = 20;
   /** Data rate is too slow (>1 sec). */
   public static final int SLOW_DATA_RATE = 21;
   /** Latitude data incomplete - missing degree or minute values. */
   public static final int LATITUDE_INCOMPLETE = 22;
   /** A non-number character was found in latitude. */
   public static final int LATITUDE_INCORRECT_CHARACTER = 23;
   /** Latitude degree value out of range. */
   public static final int LATITUDE_DEGREE_OUT_OF_RANGE = 24;
   /** Latitude minute value out of range. */
   public static final int LATITUDE_MINUTE_OUT_OF_RANGE = 25;
   /** A non-decimal character was found in latitude. */
   public static final int LATITUDE_NON_DECIMAL = 26;
   /** Latitude is imprecise. */
   public static final int LATITUDE_IMPRECISE = 27;
   /** Latitude hemisphere not recognzed. */
   public static final int LATITUDE_HEMISPHERE_UNRECOGNIZED = 28;
   /** Latitude hemisphere field has incorrect number of characters. */
   public static final int LATITUDE_HEMISPHERE_OVERRUN = 29;
   /** Longitude data incomplete - missing degree or minute values. */
   public static final int LONGITUDE_INCOMPLETE = 30;
   /** A non-number character was found in longitude. */
   public static final int LONGITUDE_INCORRECT_CHARACTER = 31;
   /** Longitude degree value out of range. */
   public static final int LONGITUDE_DEGREE_OUT_OF_RANGE = 32;
   /** Longitude minute value out of range. */
   public static final int LONGITUDE_MINUTE_OUT_OF_RANGE = 33;
   /** A non-decimal character was found in longitude. */
   public static final int LONGITUDE_NON_DECIMAL = 34;
   /** Longitude is imprecise. */
   public static final int LONGITUDE_IMPRECISE = 35;
   /** Longitude hemisphere not recognzed. */
   public static final int LONGITUDE_HEMISPHERE_UNRECOGNIZED = 36;
   /** Longitude hemisphere field has incorrect number of characters. */
   public static final int LONGITUDE_HEMISPHERE_OVERRUN = 37;
   /** UTC data incomplete - hour, minute, or second data. */
   public static final int UTC_INCOMPLETE = 38;
   /** A non-number character was found in the UTC. */
   public static final int UTC_INCORRECT_CHARACTER = 39;
   /** UTC hour value invalid. */
   public static final int UTC_HOUR_INCORRECT = 40;
   /** UTC minute value invalid. */
   public static final int UTC_MINUTE_INCORRECT = 41;
   /** A non-decimal character was found in UTC. */
   public static final int UTC_NON_DECIMAL = 42;
   /** Heading values out of range. */
   public static final int HEADING_OUT_OF_RANGE = 43;
   /** Check bearing/heading (value is 0.0). */
   public static final int HEADING_SUSPICOUS = 44;
   /** An illegal negative sign was found. */
   public static final int NUMBER_INCORRECT_NEGATIVE = 45;
   /** Too many decimals were found. */
   public static final int NUMBER_TOO_MANY_DECIMALS = 46;
   /** Illegal non-number character. */
   public static final int NUMBER_NON_NUMBER = 47;
   /** Status field not recognzed. */
   public static final int STATUS_NOT_RECOGNIZED = 48;
   /** Status field has incorrect number of characters. */
   public static final int STATUS_TOO_MANY_CHARACTERS = 49;
   /** Operating mode field not recognzed. */
   public static final int MODE_NOT_RECOGNIZED = 50;
   /** Operating mode field has incorrect number of characters. */
   public static final int MODE_TOO_MANY_CHARACTERS = 51;
   /** LORAN-C GRI is incorrect. */
   public static final int LORAN_GRI_INCORRECT = 52;
   /** LORAN-C TOA or TD is incorrect. */
   public static final int LORAN_TOATD_INCORRECT = 53;
   /** LORAN-C ECD or SNR is incorrect. */
   public static final int LORAN_ECDSNR_INCORRECT = 54;
   /** LORAN-C blink warning. */
   public static final int LORAN_BLINK = 55;
   /** LORAN-C cycle warning. */
   public static final int LORAN_CYCLE = 56;
   /** LORAN-C Signal-to-Noise (SNR) warning. */
   public static final int LORAN_SNR = 57;
   /** LORAN-C signal status not recognzed. */
   public static final int LORAN_STATUS = 58; 
   /** LORAN-C signal status has too many characters. */
   public static final int LORAN_STATUS_TOO_MANY_CHARACTERS = 59;
    
   
   public static final String[] errorDescriptions = {
      "Maximum sentence length exceeded (82 characters maximum).",
      "Incorrect number of checksum tokens [*].",
      "Incorrect location of start character [$|!].",
      "Incorrect location of escape character [^].",
      "Non-hexidecimal digit following escape character.",
      "Too many characters following checksum character.",
      "Incorrect location of return character <CR>.",
      "Incorrect location of line feed character <LF>.",
      "Incorrect address characters.",
      "Incorrect address field length.",
      "Non-hexidecimal digit checksum character.",
      "Incorrect use of reserved character.",
      "Incorrect ASCII character.",
      "Talker ID not known.",
      "Sentence type unknown.",
      "Query talker ID not recognized.",
      "Missing start character [$|!].",
      "Sentence missing data fields.",
      "Checksum value is missing.",
      "Calculated checksum does not match received checksum.",
      "Sentence length is too short.",
      "Data rate is too slow (>1 sec).",
      "Latitude data incomplete - missing degree or minute values.",
      "A non-number character was found in latitude.",
      "Latitude degree value out of range.",
      "Latitude minute value out of range.",
      "A non-decimal character was found in latitude.",
      "Latitude is imprecise.",
      "Latitude hemisphere not recognzed.",
      "Latitude hemisphere field has incorrect number of characters.",
      "Longitude data incomplete - missing degree or minute values.",
      "A non-number character was found in longitude.",
      "Longitude degree value out of range.",
      "Longitude minute value out of range.",
      "A non-decimal character was found in longitude.",
      "Longitude is imprecise.",
      "Longitude hemisphere not recognzed.",
      "Longitude hemisphere field has incorrect number of characters.",
      "UTC data incomplete - hour, minute, or second data.",
      "A non-number character was found in the UTC.",
      "UTC hour value invalid.",
      "UTC minute value invalid.",
      "A non-decimal character was found in UTC.",
      "Heading values out of range.",
      "Check bearing/heading (value is 0.0).",
      "An illegal negative sign was found.",
      "Too many decimals were found.",
      "Illegal non-number character.",
      "Status field not recognzed.",
      "Status field has incorrect number of characters.",
      "Operating mode field not recognzed.",
      "Operating mode field has incorrect number of characters.",
      "LORAN-C GRI is incorrect.",
      "LORAN-C TOA or TD is incorrect.",
      "LORAN-C ECD or SNR is incorrect.",
      "LORAN-C blink warning.",
      "LORAN-C cycle warning.",
      "LORAN-C Signal-to-Noise (SNR) warning.",
      "LORAN-C signal status not recognzed.",
      "LORAN-C signal status has too many characters."
   };
   
   
   
   
}
