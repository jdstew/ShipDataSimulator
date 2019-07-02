/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 *
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentenceTypes.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.messages;

import networkComms.messages.specific.*;
/**
 * This class provides a translation capability from recieved talker and
 * sentence formatter values, and the functionality to determine if those
 * sentences are supported by this application.
 *
 * @author Jeff Stewart
 * @version 1.1.0.0, 2005-02-02
 */
public class MessageTypes {
   public static final int INVALID_MESSAGE = -1;
   public static final int SUSPECT_MESSAGE = 0;
   public static final int VALID_MESSAGE = 1;
   
   
   /** Minimum message length is ? bytes. */
   public static final int MIN_SENTENCE_LENGTH = 1;
   /** Maximum message length is ? bytes. */
   public static final int MAX_SENTENCE_LENGTH = 8192;
   
   /** COMDAC-INS NM version 4.5.0.3 */
   public static final int COMDAC_NM_V4503 = 4503;
   
   /** Computed Position Message to hmi*/
   public static final int MT_CP = 200;
   /** Fix data Message to hmi */
   public static final int MT_FIX = 201;
   /** Course and Speed Source data Message to hmi */
   public static final int MT_CRS_SPD = 202;
   /** Heading Source data Message to hmi */
   public static final int MT_HEADING = 203;
   /** Set and Drift Source data Message to hmi */
   public static final int MT_SET_DRIFT = 204;
   /** Depth Source data Message to hmi */
   public static final int MT_DEPTH = 205;
   /** Wind Source data Message to hmi */
   public static final int MT_WIND = 206;
   /** Time Zone Source data Message to hmi */
   public static final int MT_TIME_ZONE = 207;
   /** GPS1 data Message to hmi */
   public static final int MT_GPS_1 = 208;
   /** GPS2 data Message to hmi */
   public static final int MT_GPS_2 = 209;
   /** LORAN1 data Message to hmi */
   public static final int MT_LORAN_1 = 210;
   /** LORAN2 data Message to hmi */
   public static final int MT_LORAN_2 = 211;
   /** LOP1 data Message to hmi */
   public static final int MT_LOP_1 = 212;
   /** LOP2 data Message to hmi */
   public static final int MT_LOP_2 = 213;
   /** INS1 data Message to hmi */
   public static final int MT_INS_1 = 214;
   /** INS2 data Message to hmi */
   public static final int MT_INS_2 = 215;
   /** Ownship data Message to hmi */
   public static final int MT_OS = 216;
   /** Turnpoint Solution data Message to hmi */
   public static final int MT_TURNPOINT = 217;
   /** Maneuver waypoint data Message to hmi */
   public static final int MT_MANEUVER_TL = 218;
   /** Active trackline data Message to hmi */
   public static final int MT_ACTIVE_TL = 219;
   /** DR trackline data Message to hmi */
   public static final int MT_DR_TL = 220;
   /** Best (computed) chart to use to hmi */
   public static final int MT_BEST_CHART = 221;
   /** Man Overboard data Message to hmi */
   public static final int MT_MAN_OVERBOARD = 222;
   /** Alert status to nssn for audible alerts */
   public static final int MT_ALERT_STATUS = 223;
   /** Class Configuration data to hmi */
   public static final int MT_CLASS_CONFIG = 224;
   /** Charted Features to hmi */
   public static final int MT_CHARTED_FEATURES = 225;
   /** OS Characteristics data to hmi */
   public static final int MT_OS_CHARACTERISTICS = 226;
   /** Comdac Capabilities data to hmi */
   public static final int MT_COMDAC_CAPABILITIES = 227;
   /** External heading control status to hmi */
   public static final int MT_EXT_HDG_CTRL_STATUS = 228;
   /** SCC heading control message back to auto pilot to ic */
   public static final int MT_SCC_OUTPUT = 229;
   /** Past Track Message  to hmi */
   public static final int MT_PAST_TRACK = 230;
   /** Alert message to hmi */
   public static final int MT_HMI_ALERT = 231;
   /** NSSN Sounding Message to ic */
   public static final int MT_RYME_NSSN = 232;
   
   /**
    * Provides a list of supported message versions.  This is a comprehensive list. Some
    * message types may not be supported by this application.
    *
    * @return an array of message versions (from the static numeric value list)
    */
   public static int [] getMessageVersionIDs () {
      int [] versions = {
         COMDAC_NM_V4503,
      };
      return versions;
   }
   /**
    * Get the sentence version descriptive name.
    *
    * @param version Sentence version reference from this class.
    * @return Text description of that version.
    */
   public static String getMessageVersionName (int version) {
      switch (version) {
         case COMDAC_NM_V4503: {
            return "COMDAC-INS NM version 4.5.0.3";
         }
      }
      return null;
   }
   
   /**
    * Provides a list of known formatters.  This is a comprehensive list. Some
    * sentence types may not be supported by this application.
    *
    * @return an array of talker IDs (from the static numeric value list)
    */
   public static int [] getMessagesIDs () {
      int [] messages = {
         MT_CP,
         MT_CRS_SPD,
         MT_HEADING,
         MT_SET_DRIFT,
         MT_DEPTH,
         MT_WIND,
         MT_TIME_ZONE,
         MT_GPS_1,
         MT_GPS_2,
         MT_LORAN_1,
         MT_LORAN_2,
         MT_LOP_1,
         MT_LOP_2,
         MT_INS_1,
         MT_INS_2,
         MT_OS,
         MT_TURNPOINT,
         MT_MANEUVER_TL,
         MT_ACTIVE_TL,
         MT_DR_TL,
         MT_BEST_CHART,
         MT_MAN_OVERBOARD,
         MT_ALERT_STATUS,
         MT_CLASS_CONFIG,
         MT_CHARTED_FEATURES,
         MT_OS_CHARACTERISTICS,
         MT_COMDAC_CAPABILITIES,
         MT_EXT_HDG_CTRL_STATUS,
         MT_SCC_OUTPUT,
         MT_PAST_TRACK,
         MT_HMI_ALERT,
         MT_RYME_NSSN
      };
      return messages;
   }
   
   /**
    * Provides a string description of a sentence formatter.
    *
    * @param sentenceFormatter the numeric value of a sentence formatter.
    * @return the description of the sentence formatter
    */
   public static String getMessageName (int messageID) {
      switch (messageID) {
         case MT_CP: {
            return "Computed Position message to hmi";
         }
         case MT_FIX: {
            return "Fix data message to hmi";
         }
         case MT_CRS_SPD: {
            return "Course and Speed Source data message to hmi";
         }
         case MT_HEADING: {
            return "Heading Source data message to hmi";
         }
         case MT_SET_DRIFT: {
            return "Set and Drift Source data message to hmi";
         }
         case MT_DEPTH: {
            return "Depth Source data message to hmi";
         }
         case MT_WIND: {
            return "Wind Source data message to hmi";
         }
         case MT_TIME_ZONE: {
            return "Time Zone Source data message to hmi";
         }
         case MT_GPS_1: {
            return "GPS1 data message to hmi";
         }
         case MT_GPS_2: {
            return "GPS2 data message to hmi";
         }
         case MT_LORAN_1: {
            return "LORAN1 data message to hmi";
         }
         case MT_LORAN_2: {
            return "LORAN2 data message to hmi";
         }
         case MT_LOP_1: {
            return "LOP1 data message to hmi";
         }
         case MT_LOP_2: {
            return "LOP2 data message to hmi";
         }
         case MT_INS_1: {
            return "INS1 data message to hmi";
         }
         case MT_INS_2: {
            return "INS2 data message to hmi";
         }
         case MT_OS: {
            return "Ownship data message to hmi";
         }
         case MT_TURNPOINT: {
            return "Turnpoint Solution data message to hmi";
         }
         case MT_MANEUVER_TL: {
            return "Maneuver Waypoint data message to hmi";
         }
         case MT_ACTIVE_TL: {
            return "Active trackline data message to hmi";
         }
         case MT_DR_TL: {
            return "DR trackline data message to hmi";
         }
         case MT_BEST_CHART: {
            return "Best (Computed) Chart to Use to hmi";
         }
         case MT_MAN_OVERBOARD: {
            return "Man Overboard data message to hmi";
         }
         case MT_ALERT_STATUS: {
            return "Alert Status to NSSN for Audible Alerts";
         }
         case MT_CLASS_CONFIG: {
            return "Class Configuration data to hmi";
         }
         case MT_CHARTED_FEATURES: {
            return "Charted Features to hmi";
         }
         case MT_OS_CHARACTERISTICS: {
            return "OS Characteristics data to hmi";
         }
         case MT_COMDAC_CAPABILITIES: {
            return "Comdac Capabilities data to hmi";
         }
         case MT_EXT_HDG_CTRL_STATUS: {
            return "External Heading Control status to hmi";
         }
         case MT_SCC_OUTPUT: {
            return "SCC Heading Control message back to Auto Pilot to ic";
         }
         case MT_PAST_TRACK: {
            return "Past Track message to hmi";
         }
         case MT_HMI_ALERT: {
            return "Alert message to hmi";
         }
         case MT_RYME_NSSN: {
            return "NSSN Sounding message to ic";
         }
      }
      return "Unrecognized";
   }
   
   /**
    * Determines whether a particular sentence formatte is supported by this
    * application.
    *
    * @param sentenceFormatter the static class value of a particular sentence formatter
    * @return An extended AbstractSentence object.
    */
   public static AbstractMessage getMessageObject (int messageID) {
      switch (messageID) {
         case MT_CP: {
            return new ComputedPosition ();
         }
         case MT_FIX: {
            return new DefaultMessage ();
         }
         case MT_CRS_SPD: {
            return new DefaultMessage ();
         }
         case MT_HEADING: {
            return new DefaultMessage ();
         }
         case MT_SET_DRIFT: {
            return new DefaultMessage ();
         }
         case MT_DEPTH: {
            return new DefaultMessage ();
         }
         case MT_WIND: {
            return new DefaultMessage ();
         }
         case MT_TIME_ZONE: {
            return new DefaultMessage ();
         }
         case MT_GPS_1: {
            return new DefaultMessage ();
         }
         case MT_GPS_2: {
            return new DefaultMessage ();
         }
         case MT_LORAN_1: {
            return new DefaultMessage ();
         }
         case MT_LORAN_2: {
            return new DefaultMessage ();
         }
         case MT_LOP_1: {
            return new DefaultMessage ();
         }
         case MT_LOP_2: {
            return new DefaultMessage ();
         }
         case MT_INS_1: {
            return new DefaultMessage ();
         }
         case MT_INS_2: {
            return new DefaultMessage ();
         }
         case MT_OS: {
            return new DefaultMessage ();
         }
         case MT_TURNPOINT: {
            return new DefaultMessage ();
         }
         case MT_MANEUVER_TL: {
            return new DefaultMessage ();
         }
         case MT_ACTIVE_TL: {
            return new DefaultMessage ();
         }
         case MT_DR_TL: {
            return new DefaultMessage ();
         }
         case MT_BEST_CHART: {
            return new DefaultMessage ();
         }
         case MT_MAN_OVERBOARD: {
            return new DefaultMessage ();
         }
         case MT_ALERT_STATUS: {
            return new DefaultMessage ();
         }
         case MT_CHARTED_FEATURES: {
            return new DefaultMessage ();
         }
         case MT_OS_CHARACTERISTICS: {
            return new DefaultMessage ();
         }
         case MT_COMDAC_CAPABILITIES: {
            return new DefaultMessage ();
         }
         case MT_EXT_HDG_CTRL_STATUS: {
            return new DefaultMessage ();
         }
         case MT_SCC_OUTPUT: {
            return new DefaultMessage ();
         }
         case MT_HMI_ALERT: {
            return new DefaultMessage ();
         }
         case MT_RYME_NSSN: {
            return new DefaultMessage ();
         }
         default: {
            return new DefaultMessage ();
         }
      }
   }
   
   /**
    * Determines whether a particular message  is supported by this
    * application.
    *
    * @param messageID the static class value of a particular sentence formatter
    * @return An extended AbstractMessage object.
    */
   public static boolean isSupported (int messageID) {
      switch (messageID) {
         case MT_CP: {
            return true;
         }
         case MT_FIX: {
            return true;
         }
         case MT_CRS_SPD: {
            return true;
         }
         case MT_HEADING: {
            return true;
         }
         case MT_SET_DRIFT: {
            return true;
         }
         case MT_DEPTH: {
            return true;
         }
         case MT_WIND: {
            return true;
         }
         case MT_TIME_ZONE: {
            return true;
         }
         case MT_GPS_1: {
            return true;
         }
         case MT_GPS_2: {
            return true;
         }
         case MT_LORAN_1: {
            return true;
         }
         case MT_LORAN_2: {
            return true;
         }
         case MT_LOP_1: {
            return true;
         }
         case MT_LOP_2: {
            return true;
         }
         case MT_INS_1: {
            return true;
         }
         case MT_INS_2: {
            return true;
         }
         case MT_OS: {
            return true;
         }
         case MT_TURNPOINT: {
            return true;
         }
         case MT_MANEUVER_TL: {
            return true;
         }
         case MT_ACTIVE_TL: {
            return true;
         }
         case MT_DR_TL: {
            return true;
         }
         case MT_BEST_CHART: {
            return true;
         }
         case MT_MAN_OVERBOARD: {
            return true;
         }
         case MT_ALERT_STATUS: {
            return true;
         }
         case MT_CHARTED_FEATURES: {
            return true;
         }
         case MT_OS_CHARACTERISTICS: {
            return true;
         }
         case MT_COMDAC_CAPABILITIES: {
            return true;
         }
         case MT_EXT_HDG_CTRL_STATUS: {
            return true;
         }
         case MT_SCC_OUTPUT: {
            return true;
         }
         case MT_HMI_ALERT: {
            return true;
         }
         case MT_RYME_NSSN: {
            return true;
         }
         default: {
            return false;
         }
      }
   }
}
