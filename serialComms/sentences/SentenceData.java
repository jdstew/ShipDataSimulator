/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentenceData.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences;

/**
 * The class contains generic data used by multiple sentence objects and methods.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SentenceData {
   /** The carriage return character. */
   public final static char CR = 0x0D; // Carriage return
   /** The line feed character. */
   public final static char LF = 0x0A; // Line feed
   
   /** The valid sentence character. */
   public final static char VALID = 'A';
   /** The invalide sentence character. */
   public final static char INVALID = 'V';

   /** Maximum rate of turn, in degrees per minute */
   public final static double MAX_ROT = 120.0;
   /** Maximum variation limit (absolute) */
   public final static double MAX_VARIATION = 10.0;
   /** Maximum deviation limit (absolute) */
   public final static double MAX_DEVIATION = 5.0;
   /** Maximum forward speed, in knots */
   public final static double MAX_FORWARD_SPEED = 50.0;
   /** Maximum stern speed, in knots (absolute value) */
   public final static double MAX_STERN_SPEED = 10.0;
   /** Maximum transverse speed, in knots (absolute value)*/
   public final static double MAX_TRANSVERSE_SPEED = 5.0;
   /** Nautical mile to kilometer conversion factor */
   public static final double NM_TO_KM_CONVERSION = 1.85;
   /** Maximum depth, in feet */
   public static final double MAX_DEPTH = 27000.0;
   /** Maximum transducer offset, in feet */
   public static final double MAX_TRANSDUCER_OFFSET = 50.0;
   /** Maximum wind speed, in knots */
   public static final double MAX_WIND_SPEED = 150.0;
   /** Maximum drift, in knots */
   public static final double MAX_DRIFT_SPEED = 8.0;
   /** Feet to meters conversion factor */
   public static final double FT_TO_M_CONVERSION = 0.305;
   /** Feet to fathoms conversion factor */
   public static final double FT_TO_F_CONVERSION = 0.167;
   
   /** WGS84 datum reference. */
   public final static String DATUM_WGS84 = "W84";
   /** WGS72 datum reference. */
   public final static String DATUM_WGS72 = "W72";
   /** SGS85 datum reference. */
   public final static String DATUM_SGS85 = "S85";
   /** PE90 datum reference. */
   public final static String DATUM_PE90 = "P90";
   /** User defined datum reference. */
   public final static String DATUM_USER_DEFINED = "999";
   // DATUM_... can also be an IHO datum code.
   
   /** List of datum references */
   public final static String [] DATUM_REFERENCE_LIST = {
      DATUM_WGS84,
      DATUM_WGS72,
      DATUM_SGS85,
      DATUM_PE90,
      DATUM_USER_DEFINED,
   };
   
   /** List of local datum references */
   public final static String [] DATUM_LOCAL_LIST = {
      DATUM_WGS84,
      DATUM_WGS72,
      DATUM_SGS85,
      DATUM_PE90,
      DATUM_USER_DEFINED,
   };
   
   /** No fix GPS quality value. */
   public final static int GPS_QUALITY_NO_FIX = 0;
   /** Standard GPS quality value. */
   public final static int GPS_QUALITY_GPS = 1;
   /** Differential GPS quality value. */
   public final static int GPS_QUALITY_DGPS = 2;
   /** Precise positioning GPS quality value. */
   public final static int GPS_QUALITY_PPS = 3;
   /** Realtime kinematic integer GPS quality value. */
   public final static int GPS_QUALITY_RTK_INT = 4;
   /** Realtime kinematic floating point  GPS quality value.*/
   public final static int GPS_QUALITY_RTK_FLOAT = 5;
   /** Estimated (DR-ed) GPS quality value. */
   public final static int GPS_QUALITY_ESTIMATED = 6;
   /** Manual GPS quality value. */
   public final static int GPS_QUALITY_MAUNUAL = 7;
   /** Simulated GPS quality value. */
   public final static int GPS_QUALITY_SIMULATED = 8;
   
   /** Autonomous positioning device mode. */
   public final static char POSITION_MODE_AUTONOMOUS = 'A';
   /** Differential positioning device mode. */
   public final static char POSITION_MODE_DIFFERENTIAL = 'D';
   /** Estimated (DR-ed) positioning device mode. */
   public final static char POSITION_MODE_ESTIMATED = 'E';
   /** Manual positioning device mode. */
   public final static char POSITION_MODE_MANUAL = 'M';
   /** Simulated positioning device mode. */
   public final static char POSITION_MODE_SIMULATOR = 'S';
   /** No fix positioning device mode. */
   public final static char POSITION_MODE_NO_FIX = 'N';

   /** List of positioning modes. */
   public final static char [] POSITION_MODE_LIST = {
      POSITION_MODE_AUTONOMOUS,
      POSITION_MODE_DIFFERENTIAL,
      POSITION_MODE_ESTIMATED,
      POSITION_MODE_MANUAL,
      POSITION_MODE_SIMULATOR,
      POSITION_MODE_NO_FIX
   };
   
}
