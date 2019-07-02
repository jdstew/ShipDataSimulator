/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentenceErrorTypes.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.analysis;

/**
 * This class contains static values and methods for errors found during
 * sentence processing.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SentenceErrorTypes {
   /** Maximum time between receipt of sentences, in milliseconds */
   public static final long SENTENCE_TIMEOUT_LENGTH = 30000;
   
   /** Sentence is valid */
   public static final int VALID_SENTENCE = 1;
   /** Sentence is suspect, but not invalid. */
   public static final int SUSPECT_SENTENCE = 0;
   /** Sentence is invalid and should not be used. */
   public static final int INVALID_SENTENCE = -1;
   /** Missing '$' character. */
   public static final int ERR_MISSING_START = 100; 
   /** Incorrect use of the '$' character. */
   public static final int ERR_INCORRECT_START = 101; 
   /** Incorrect sentence termination sequence (<CR><LF> not found). */
   public static final int ERR_INCORRECT_TERMINATION = 102;
   /** Invalid address field length. */
   public static final int ERR_ADDRESS_LENGTH_ILLEGAL = 200; 
   /** Talker ID that does not follow the protocol. */
   public static final int ERR_TALKERID_ILLEGAL = 210; 
   /** Talker ID not known. */
   public static final int ERR_TALKERID_UNKNOWN = 211; 
   /** Talker ID valid, but not processed by this application. */
   public static final int ERR_TALKERID_UNUSED = 212; 
   /** Sentence type that does not follow the protocol. */
   public static final int ERR_SENTENCE_TYPE_ILLEGAL = 220; 
   /** Sentence type unknown. */
   public static final int ERR_SENTENCE_TYPE_UNKNOWN = 221; 
   /** Sentence type valid, but not processed by this application. */
   public static final int ERR_SENTENCE_TYPE_UNUSED = 222; 
   /** Incorrect talker ID for the sentence type. */
   public static final int ERR_ID_SENTENCE_MISMATCH = 230; 
   /** Data taking more than one second to receive. */
   public static final int ERR_DATA_SLOW = 300; 
   /** Data being recieved at greater than 1Hz (@4800baud) or 50Hz (@38400baud). */
   public static final int ERR_DATA_FAST = 301; 
   /** Sentence is less than 11 characters. */
   public static final int ERR_DATA_UNDERRUN = 302; 
   /** Sentence is more than 82 characters. */
   public static final int ERR_DATA_OVERRUN = 303; 
   /** Sensor data timed out. */
   public static final int ERR_DATA_TIMEDOUT = 304; 
   /** Validity bit set to invalid. */
   public static final int ERR_DATA_INVALID = 305; 
   /** Expected data is null. */
   public static final int ERR_DATA_MISSING = 306; 
   /** Data not contained within a formatted sentence. */
   public static final int ERR_DATA_ERRONEOUS = 307; 
   /** Data not logical with other parts of sentence. */
   public static final int ERR_DATA_ILLOGICAL = 308; 
   /** Data is null may not be correct. */
   public static final int ERR_DATA_NULL = 309; 
   /** Data  may not be correct. */
   public static final int ERR_DATA_SUSPICOUS = 310; 
   /** Data fields missing. */
   public static final int ERR_FIELD_MISSING = 400; 
   /** Data field incomplete. */
   public static final int ERR_FIELD_INCOMPLETE = 401; 
   /** Erroneous data field present. */
   public static final int ERR_FIELD_INVALID = 402; 
   /** Field not contained within a formatted sentence. */
   public static final int ERR_FIELD_ERRONEOUS = 403; 
   /** Illegal use of a reserved character. */
   public static final int ERR_RESERVED_CHAR_ILLEGAL = 500; 
   /** Escaped characters value greater than hex F. */
   public static final int ERR_CODE_CHAR_ILLEGAL = 501; 
   /** Illegal use of a non-valid character. */
   public static final int ERR_CHARACTER_NOT_VALID = 502; 
   /** Checksum value is missing. */
   public static final int ERR_CHECKSUM_MISSING = 600; 
   /** Computed checksum value does not match. */
   public static final int ERR_CHECKSUM_INCORRECT = 601; 
   /** Checksum missing one or both values. */
   public static final int ERR_CHECKSUM_INCOMPLETE = 602; 
   /** Checksum characters value greater than hex F. */
   public static final int ERR_CHECKSUM_ILLEGAL_CHAR = 603; 
   /** Checksum found in an incorrect location. */
   public static final int ERR_CHECKSUM_ILLEGAL_POSITION = 604; 
   /** Incorrect hemisphere data. */
   public static final int ERR_POSITION_HEMISPHERE = 700; 
   /** Illegal characters used. */
   public static final int ERR_POSITION_ILLEGAL_CHAR = 701; 
   /** Position not given to correct precision. */
   public static final int ERR_POSITION_PRECISION = 702; 
   /** Position data out of range. */
   public static final int ERR_POSITION_OUT_OF_RANGE = 703; 
   /** Illegal characters used. */
   public static final int ERR_TIMEDATE_ILLEGAL_CHAR = 800; 
   /** Time and date values out of range. */
   public static final int ERR_TIMEDATE_OUT_OF_RANGE = 801; 
   /** Time and date is not recent. */
   public static final int ERR_TIMEDATE_NOT_RECENT = 802; 
   /** Depth values out of range. */
   public static final int ERR_DEPTH_OUT_OF_RANGE = 900; 
   /** Heading values out of range. */
   public static final int ERR_HEADING_OUT_OF_RANGE = 1000; 
   /** Speed values out of range. */
   public static final int ERR_SPEED_OUT_OF_RANGE = 1100; 
   /** Unknown error. */
   public static final int ERR_UNKNOWN_ERROR = -1;
  
   /**
    * Gets the textual description for an error code.
    *
    * @param errorCode A static error code value.
    * @return Description of an error.
    */
   public static String getMessage(int errorCode) {     
      switch (errorCode) {
         case ERR_MISSING_START: {
            return "Error: start character ('$' dollar sign) is not the first byte. ";
         }
         case ERR_INCORRECT_START: { 
            return "Error: start character ('$' dollar sign) is at an illegal position. ";
         }
         case ERR_INCORRECT_TERMINATION: { 
            return "Error: incorrect sentence termination sequence (<CR><LF> or <ETX> not found). ";
         }         
         case ERR_ADDRESS_LENGTH_ILLEGAL: { 
            return "Error: address field's length is illegal. ";
         }
         case ERR_TALKERID_ILLEGAL: { 
            return "Error: talker ID is invalid for the sentence formatter. ";
         }
         case ERR_TALKERID_UNKNOWN: { 
            return "Error: talker ID is not known. ";
         }
         case ERR_TALKERID_UNUSED: { 
            return "Problem: talker ID is not used by this application. ";
         }
         case ERR_SENTENCE_TYPE_ILLEGAL: { 
            return "Error: sentence formatter is illegal. ";
         }
         case ERR_SENTENCE_TYPE_UNKNOWN: { 
            return "Error: sentence formatter is not known. ";
         }
         case ERR_SENTENCE_TYPE_UNUSED: { 
            return "Problem: sentence type is not used by this application. ";
         }
         case ERR_ID_SENTENCE_MISMATCH: { 
            return "Warning: talker ID is incorrect for sentence formatter. ";
         }
         case ERR_DATA_SLOW: { 
            return "Warning: data receive rate is too slow. ";
         }
         case ERR_DATA_FAST: { 
            return "Warning: sentence receive frequency is too high. ";
         }
         case ERR_DATA_UNDERRUN: { 
            return "Error: sentence length is too short to comprehend. ";
         }
         case ERR_DATA_OVERRUN: { 
            return "Error: illegal sentence length received (overrun). ";
         }
         case ERR_DATA_TIMEDOUT: { 
            return "Error: sentence timed out (or being received). ";
         }
         case ERR_DATA_INVALID: { 
            return "Error: data is invalid. ";
         }
         case ERR_DATA_MISSING: { 
            return "Error: some of the data is missing. ";
         }
         case ERR_DATA_ERRONEOUS: { 
            return "Error: data is erroneous. ";
         }
         case ERR_DATA_ILLOGICAL: { 
            return "Warning: data is illogical. ";
         }
         case ERR_DATA_NULL: { 
            return "Warning: data is null. ";
         }
         case ERR_DATA_SUSPICOUS: { 
            return "Warning: data is suspicous. ";
         }
         case ERR_FIELD_MISSING: { 
            return "Error: sentence is missing a data field. ";
         }
         case ERR_FIELD_INCOMPLETE: { 
            return "Error: sentence missing field(s). ";
         }
         case ERR_FIELD_INVALID: { 
            return "Error: invalid field in sentence. "; 
         }
         case ERR_FIELD_ERRONEOUS: { 
            return "Error: field is erroneous. ";
         }
         case ERR_RESERVED_CHAR_ILLEGAL: { 
            return "Error: illegal use of reserved character. ";
         }
         case ERR_CODE_CHAR_ILLEGAL: { 
            return "Error: illegal escape character. ";
         }
         case ERR_CHARACTER_NOT_VALID: { 
            return "Error: invalid character. ";
         }
         case ERR_CHECKSUM_MISSING: { 
            return "Warning: checksum is missing. ";
         }
         case ERR_CHECKSUM_INCORRECT: { 
            return "Error: checksum does not match computed checksum. ";
         }
         case ERR_CHECKSUM_INCOMPLETE: { 
            return "Error: checksum is incomplete. ";
         }
         case ERR_CHECKSUM_ILLEGAL_CHAR: { 
            return "Error: illegal checksum character. ";
         }
         case ERR_CHECKSUM_ILLEGAL_POSITION: { 
            return "Error: checksum at incorrect location. ";
         }
         case ERR_POSITION_HEMISPHERE: { 
            return "Error: hemisphere data incorrect. ";
         }
         case ERR_POSITION_ILLEGAL_CHAR: { 
            return "Error: illegal position data character. ";
         }
         case ERR_POSITION_PRECISION: { 
            return "Warning: position precision is insufficient. ";
         }
         case ERR_POSITION_OUT_OF_RANGE: { 
            return "Error: position data is out of range.";
         }
         case ERR_TIMEDATE_ILLEGAL_CHAR: { 
            return "Error: illegal time and date character. ";
         }
         case ERR_TIMEDATE_OUT_OF_RANGE: { 
            return "Error: time and date data is out of range. ";
         }
         case ERR_TIMEDATE_NOT_RECENT: { 
            return "Warning: time and date data is not recent. ";
         }
         case ERR_DEPTH_OUT_OF_RANGE: { 
            return "Error: depth value is out of range. ";
         }
         case ERR_HEADING_OUT_OF_RANGE: { 
            return "Error: heading value is out of range. ";
         }
         case ERR_SPEED_OUT_OF_RANGE: { 
            return "Error: heading value is out of range. ";
         }
         case ERR_UNKNOWN_ERROR: { 
            return "Error: an unknown error. ";            
         }
      }
      
      return "An application error has occurred. ";      
   }
   
   /**
    * Get the validity value for a particular error code.
    *
    * @param errorCode The particular static error code value to get the validity value of.
    * @return The validity value of the error code.
    */
   public static int getValidity(int errorCode) {    
      switch (errorCode) {
         case ERR_MISSING_START: {
            return INVALID_SENTENCE;
         }
         case ERR_INCORRECT_START: { 
            return INVALID_SENTENCE;
         }
         case ERR_INCORRECT_TERMINATION: { 
            return INVALID_SENTENCE;
         }
         case ERR_ADDRESS_LENGTH_ILLEGAL: { 
            return INVALID_SENTENCE;
         }
         case ERR_TALKERID_ILLEGAL: { 
            return INVALID_SENTENCE;
         }
         case ERR_TALKERID_UNKNOWN: { 
            return INVALID_SENTENCE;
         }
         case ERR_TALKERID_UNUSED: { 
            return VALID_SENTENCE;
         }
         case ERR_SENTENCE_TYPE_ILLEGAL: { 
            return INVALID_SENTENCE;
         }
         case ERR_SENTENCE_TYPE_UNKNOWN: { 
            return INVALID_SENTENCE;
         }
         case ERR_SENTENCE_TYPE_UNUSED: { 
            return VALID_SENTENCE;
         }
         case ERR_ID_SENTENCE_MISMATCH: { 
            return SUSPECT_SENTENCE;
         }
         case ERR_DATA_SLOW: { 
            return SUSPECT_SENTENCE;
         }
         case ERR_DATA_FAST: { 
            return SUSPECT_SENTENCE;
         }
         case ERR_DATA_UNDERRUN: { 
            return INVALID_SENTENCE;
         }
         case ERR_DATA_OVERRUN: { 
            return INVALID_SENTENCE;
         }
         case ERR_DATA_TIMEDOUT: { 
            return INVALID_SENTENCE;
         }
         case ERR_DATA_INVALID: { 
            return INVALID_SENTENCE;
         }
         case ERR_DATA_MISSING: { 
            return INVALID_SENTENCE;
         }
         case ERR_DATA_ERRONEOUS: { 
            return INVALID_SENTENCE;
         }
         case ERR_DATA_ILLOGICAL: { 
            return SUSPECT_SENTENCE;
         }
         case ERR_DATA_NULL: { 
            return SUSPECT_SENTENCE;
         }
         case ERR_DATA_SUSPICOUS: { 
            return SUSPECT_SENTENCE;
         }
         case ERR_FIELD_MISSING: { 
            return INVALID_SENTENCE;
         }
         case ERR_FIELD_INCOMPLETE: { 
            return INVALID_SENTENCE;
         }
         case ERR_FIELD_INVALID: { 
            return INVALID_SENTENCE; 
         }
         case ERR_FIELD_ERRONEOUS: { 
            return INVALID_SENTENCE;
         }
         case ERR_RESERVED_CHAR_ILLEGAL: { 
            return INVALID_SENTENCE;
         }
         case ERR_CODE_CHAR_ILLEGAL: { 
            return INVALID_SENTENCE;
         }
         case ERR_CHARACTER_NOT_VALID: { 
            return INVALID_SENTENCE;
         }
         case ERR_CHECKSUM_MISSING: { 
            return SUSPECT_SENTENCE;
         }
         case ERR_CHECKSUM_INCORRECT: { 
            return INVALID_SENTENCE;
         }
         case ERR_CHECKSUM_INCOMPLETE: { 
            return INVALID_SENTENCE;
         }
         case ERR_CHECKSUM_ILLEGAL_CHAR: { 
            return INVALID_SENTENCE;
         }
         case ERR_CHECKSUM_ILLEGAL_POSITION: { 
            return INVALID_SENTENCE;
         }
         case ERR_POSITION_HEMISPHERE: { 
            return INVALID_SENTENCE;
         }
         case ERR_POSITION_ILLEGAL_CHAR: { 
            return INVALID_SENTENCE;
         }
         case ERR_POSITION_PRECISION: { 
            return SUSPECT_SENTENCE;
         }
         case ERR_POSITION_OUT_OF_RANGE: { 
            return INVALID_SENTENCE;
         }
         case ERR_TIMEDATE_ILLEGAL_CHAR: { 
            return INVALID_SENTENCE;
         }
         case ERR_TIMEDATE_OUT_OF_RANGE: { 
            return INVALID_SENTENCE;
         }
         case ERR_TIMEDATE_NOT_RECENT: { 
            return SUSPECT_SENTENCE;
         }
         case ERR_DEPTH_OUT_OF_RANGE: { 
            return INVALID_SENTENCE;
         }
         case ERR_HEADING_OUT_OF_RANGE: { 
            return INVALID_SENTENCE;
         }
         case ERR_SPEED_OUT_OF_RANGE: { 
            return INVALID_SENTENCE;
         }
         case ERR_UNKNOWN_ERROR: { 
            return INVALID_SENTENCE;            
         }
      }
      
      return 999;      
   } 
}
