/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: DataFactoryDefaults.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.data;

import serialComms.sentences.*;
/**
 * This class is defines the default values for the DataFactory class.
 *
 * @author Jeff Stewart
 * @version 1.3.0.0, 2005-02-06
 */
final public class DataFactoryDefaults {
   /** Default depth, in feet */   
   public final static String DEPTH_FEET = "48.8";
   /** Default depth, in meters */   
   public final static String DEPTH_METERS = "14.2";
   /** Default depth, in fathoms */   
   public final static String DEPTH_FATHOMS = "8.1";
   /** Default depth offset is -0.8 meters. */
   public final static String DEPTH_OFFSET = "-0.8";
   /** Default depth range is 50.0 meters. */
   public final static String DEPTH_RANGE = "50.0";
   
   
   /** Default latitude value */   
   public final static String POSITION_LATITUDE = "3745.5001";
   /** Default latitude hemisphere */   
   public final static String POSITION_LAT_HEMISPHERE = "N";
   /** Default longitude value */   
   public final static String POSITION_LONGITUDE = "12241.0003";
   /** Default longitude hemisphere */   
   public final static String POSITION_LON_HEMISPHERE = "W";
   /** Default position status is valid ('A'). */
   public final static String POSITION_STATUS = Character.toString (SentenceData.VALID);
   /** Default position mode is differential GPS. */
   public final static String POSITION_MODE = 
      Character.toString (SentenceData.POSITION_MODE_DIFFERENTIAL);

   
   /** Default true heading over the ground. */
   public final static String HEADING_TRUE_GROUND = "270";
   /** Default true heading through the water. */
   public final static String HEADING_TRUE_WATER = "268";
   /** Default magnetic heading over the ground. */
   public final static String HEADING_MAGNETIC_GROUND = "263";
   /** Default magnetic heading through the water. */
   public final static String HEADING_MAGNETIC_WATER = "261";
   /** Default heading validity status. */
   public final static String HEADING_STATUS = Character.toString (SentenceData.VALID);
   /** Default rate of turn, in degrees per minute. */
   public final static String RATE_OF_TURN = "-0.8";
   /** Default rate of turn validity status. */
   public final static String RATE_OF_TURN_STATUS = Character.toString (SentenceData.VALID);
   
   
   /** Default magnetic variation is +3.0 degrees. */
   public final static String MAGNETIC_VARIATION = "3.0";
   /** Default magnetic variation is +3.0 degrees for the simulator. */
   public final static double MAGNETIC_VARIATION_SIMULATED = 3.0;
   /** Default magnetic variation direction. */
   public final static String MAGNETIC_VAR_DIRECTION = "W";
   /** Default magnetic deviation is -0.5 degrees. */
   public final static String MAGNETIC_DEVIATION = "0.5";
   /** Default magnetic deviation direction. */
   public final static String MAGNETIC_DEV_DIRECTION = "E";
   
   
   /** Default fore/aft speed over ground, in knots. */
   public final static String SPEED_GROUND_KNOTS = "8.7";
   /** Default fore/aft speed over ground, in KM per hour. */
   public final static String SPEED_GROUND_KMH = "9.2";
   /** Default transvers speed over ground, in knots. */
   public final static String SPEED_GROUND_TRANSVERSE_KNOTS = "0.1";
   /** Default speed over ground validity status. */
   public final static String SPEED_GROUND_STATUS = Character.toString (SentenceData.VALID);
   /** Default stern speed over ground, in knots. */
   public final static String SPEED_GROUND_STERN_KNOTS = "0.4";
   /** Default stern speed over ground validity status. */
   public final static String SPEED_GROUND_STERN_STATUS = Character.toString (SentenceData.VALID);
   /** Default fore/aft speed through the water, in knots. */
   public final static String SPEED_WATER_KNOTS = "8.5";
   /** Default fore/aft speed through the water, in KM per hour. */
   public final static String SPEED_WATER_KMH = "8.9";
   /** Default transverse speed through the water, in knots. */
   public final static String SPEED_WATER_TRANSVERSE_KNOTS = "0.1";
   /** Default speed through the water validity status. */
   public final static String SPEED_WATER_STATUS = Character.toString (SentenceData.VALID);
   /** Default stern speed through the water, in knots. */
   public final static String SPEED_WATER_STERN_KNOTS = "0.4";
   /** Default stern speed through the water validity status. */
   public final static String SPEED_WATER_STERN_STATUS = Character.toString (SentenceData.VALID);
   /** Default speed data mode. */
   public final static String SPEED_MODE = 
      Character.toString (SentenceData.POSITION_MODE_DIFFERENTIAL);
   /** Default doppler mode is water mass mode ('W'). */
   public final static String DOPPLER_MODE = "W";
   
   
   /** Default set direction is 225.0 degrees true. */
   public final static String SET_TRUE = "225.0";
   /** Default set direction, degrees magnetic. */
   public final static String SET_MAGNETIC = "219.0";
   /** Default drift speed is 1.5 knots. */
   public final static String DRIFT = "1.5";
   
   
   /** Default GPS quality id is differential (DGPS). */
   public final static String GPS_QUALITY_ID = 
      Integer.toString (SentenceData.GPS_QUALITY_DGPS);
   /** Default number of satellites in use is 7. */
   public final static String GPS_NO_OF_SATELLITES = "07";
   /** Default horizontal dillution of precision (HDOP) is 2.3 (~9.2 meters). */
   public final static String GPS_HDOP = "2.3";
   /** Default GPS altitude is 6.8 meters. */
   public final static String GPS_ALTITUDE = "6.8";
   /** Default GPS geodial separation is 5.6 meters. */
   public final static String GPS_GEO_SEP = "5.6";
   /** Default age of differential corrections is 3 seconds. */
   public final static String DGPS_COOR_AGE = "3";
   /** Default differential beacon station is 320 (???) */
   public final static String DGPS_STATION = "0320";
   
   
   /** Default LORAN-C GRI */
   public final static String LORAN_GRI = "9960";
   /** Default signal status */
   public final static String LORAN_STATUS = "";
   /** Default signal data (TOA/TD, ECD, SNR) */
   public final static String [][] LORAN_SIGNALS = {
                                       {"00350.6", "A", "890", "780"},
                                       {"15978.6", "A", "891", "781"},
                                       {"41203.7", "A", "892", "782"},
                                       {"33333.3", "A", "893", "783"},
                                       {"44444.4", "A", "894", "784"},
                                       {"55555.5", "A", "895", "785"}
   };
   
   /** Default UTC time value. */
   public final static String UTC = "120034";
   /** Default day of the month value. */
   public final static String DATE_DAY = "04";
   /** Default month of the year value. */
   public final static String DATE_MONTH = "07";
   /** Default year value. */
   public final static String DATE_YEAR = "2005";
   /** Default zone hours is +5 hours. */
   public final static String ZONE_HOURS = "08";
   /** Default zone minutes is zero (0) minutes (ie a whole hour). */
   public final static String ZONE_MINUTES = "00";
   
   
   /** Default datum is WGS-84. */
   public final static String DATUM_LOCAL = SentenceData.DATUM_WGS84;
   /** Default datum local subdivision code value. */
   public final static String DATUM_LOCAL_SUBDIVISION = "";
   /** Default latitude offset is 0.0 minutes. */
   public final static String LATITUDE_OFFSET = "0.0";
   /** Default latitude offset direction is north ('N'). */
   public final static String LATITUDE_OFFSET_DIRECTION = "N";
   /** Default longitude offset is 0.0 minutes. */
   public final static String LONGITUDE_OFFSET = "0.0";
   /** Default longitude offset direction is west ('W'). */
   public final static String LONGITUDE_OFFSET_DIRECTION = "W";
   /** Default altitude offset is zero (0.0) meters. */
   public final static String ALTITUDE_OFFSET = "0.0";
   /** Default datum reference value. */
   public final static String DATUM_REFERENCE = SentenceData.DATUM_WGS84;
   
   
   /** Default wind angle */
   public final static String WIND_ANGLE = "037";
   /** Default wind reference */
   public final static String WIND_REFERENCE = "R";
   /** Default wind speed */
   public final static String WIND_SPEED = "23.0";
   /** Default wind speed units */
   public final static String WIND_UNITS = "K";
   /** Default wind status */
   public final static String WIND_STATUS = "A";
   
   
   /** Default OSS train angle */
   public final static String OSS_TRAIN = "030.5";
   /** Default OSS elevation angle */
   public final static String OSS_ELEVATION = "001.6";
   /** Default OSS status from SCCS to DCU */
   public final static String OSS_SCCS_STATUS = "Q";
   /** Default OSS status from DCU to SCCS */
   public final static String OSS_DCU_STATUS = "";
   
   String ossStatusFromDCU;
   String ossStatusFromSCCS;
   String ossElevation; // (vertical), in degrees.
   String ossTrain; // (horizontal), in degrees.
}
/*
 * Version history
 * 1.3.0.0 - added double value for magnetic variation for simulated heading.
 */
