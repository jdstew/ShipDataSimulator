/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: DataFactory.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.data;

import java.io.*;
import java.text.*;
import java.util.*;
/**
 * An object of this class represents navigation related data specific to the
 * environment simulated or received by the input devices.
 *
 * @author Jeff Stewart
 * @version 1.3.0.0, 2005-02-17
 */
public class DataFactory implements OwnshipUpdateListener, Serializable {
   

	private static final long serialVersionUID = 1906041497609885244L;
	DecimalFormat twoDigitForm = new DecimalFormat("00");
   DecimalFormat threeDigitForm = new DecimalFormat("000");
   DecimalFormat minuteForm = new DecimalFormat("00.0000");
   DecimalFormat headingForm = new DecimalFormat("000.0");
   DecimalFormat speedForm = new DecimalFormat("#0.0");
   Calendar calendarObj = new GregorianCalendar();
   
   /** Used to indicate that the data source is from an ownship simulator. */
   public static final int SIMULATOR_DATA_SOURCE = 1;
   /** Used to indicate that the data source is from a manually entered data. */
   public static final int MANUAL_DATA_SOURCE = 2;
   /** Used to indicate that the data source is from a random sentence generator. */
   public static final int RANDOM_DATA_SOURCE = 3;
   
   /** Used to set a transmit frequency of 0.05Hz (or a period of 20 seconds). */
   public static final long TRANSMIT_FREQ_PT05HZ = 20000;
   /** Used to set a transmit frequency of 1Hz (or a period of 1 second). */
   public static final long TRANSMIT_FREQ_1HZ = 1000;
   /** Used to set a transmit frequency of 20Hz (or a period of 0.05 seconds). */
   public static final long TRANSMIT_FREQ_20HZ = 50;
   
   /** Nautical mile to kilometer conversion factor */
   public static final double NM_TO_KM_CONVERSION = 1.85;
   
   
   OwnshipUpdate ownshipUpdate;
   
   
   String latitude;
   String latitudeHemisphere;
   String longitude;
   String longitudeHemisphere;
   String positionStatusID; // Position status, 'A' for valid, 'V' for invalid
   String positionModeID; // Position mode, 'A' for autonomous, 'D' for differential 
   
   
   String depthInFeet;
   String depthInMeters;
   String depthInFathoms;
   String depthOffset; // Depth offset from tranducer, in meters, + to waterline, - to keel 
   String depthRange; // Depth range, in meters 
   
   
   String headingTrueGround; // Course over ground, degrees True
   String headingTrueWater;
   String headingMagneticGround;
   String headingMagneticWater; 
   String headingStatus; // Mode indicator
   String rateOfTurn;
   String rateOfTurnStatus;
   String variation; // Variation, in degrees Magnetic
   String variationDirection;
   String deviation; // Deviation, in degrees Magnetic
   String deviationDirection;
   
   
   String speedGroundInKnots;
   String speedGroundInKMH;
   String speedGroundTranverseInKnots;
   String speedGroundStatus;
   String speedGroundSternInKnots;
   String speedGroundSternStatus;
   String speedWaterInKnots;
   String speedWaterInKMH;
   String speedWaterTranverseInKnots;
   String speedWaterStatus;
   String speedWaterSternInKnots;
   String speedWaterSternStatus;
   String speedMode;
   String dopplerMode;
   
   
   String setTrue; // Set direction, degrees True 
   String setMagnetic; // Set direction, degrees Magnetic 
   String driftKnots; // Drift speed, Knots 
//   Vector2D setAndDrift;
   

   String GPS_qualityID; // GPS quality indicator  
   String GPS_noOfSatellites; // Number of satellites used, (w/o leading zeros)
   String GPS_HDOP; // Horizontal Dilution of Precision 
   String GPS_altitude; // Altitude, meters (may be negative)
   String GPS_geoidalSep; // Geoidal separation, meters 
   String GPS_ageOfDGPS; // Age of DGPS data, seconds (null if not used) 
   String GPSD_GPSStation; // DGPS reference station ID, (w/o leading zeros) 
   
   String loranGRI;
   String [][] loranSignals;
   
   String timeUTC;
   String dateYear;
   String dateMonth;
   String dateDay;
   String zoneHours; // Zone hours for local time. 
   String zoneMinutes; // Zone minutes for local time. 


   String datumLocalCode; // Local datum code. 
   String datumLocalSubdivisionCode; // Local datum subdivision code. 
   String latitudeOffset;  // Latitude offset, minutes. 
   String latitudeOffsetDirection; // 'N' or 'S' 
   String longitudeOffset; // Longitude offset, minutes. 
   String longitudeOffsetDirection; // 'E' or 'W' 
   String altitudeOffset; // Altitude offset, meters
   String datumReferenceCode; // Reference datum. 
   
   
   // A target's current time and date 
   Date targetTimeDate;
   
   
   String windAngle;
   String windReference;
   String windSpeed;
   String windUnits;
   String windStatus;
   

   String ossTrain; // (horizontal), in degrees.
   String ossElevation; // (vertical), in degrees.
   String ossStatusFromSCCS;
   String ossStatusFromDCU;
   
   
   String freeTextSentence;
   
   /** Instantiates a new DataFactory object. */
   public DataFactory () {
      this.resetDefaults ();
   }
   
   /** Resets the DataFactory object to DataFactoryDefaults object values. */
   public void resetDefaults () {
      latitude = DataFactoryDefaults.POSITION_LATITUDE;
      latitudeHemisphere = DataFactoryDefaults.POSITION_LAT_HEMISPHERE;
      longitude = DataFactoryDefaults.POSITION_LONGITUDE;
      longitudeHemisphere = DataFactoryDefaults.POSITION_LON_HEMISPHERE;
      positionStatusID = DataFactoryDefaults.POSITION_STATUS;
      positionModeID = DataFactoryDefaults.POSITION_MODE;
      
      depthInFeet = DataFactoryDefaults.DEPTH_FEET;
      depthInMeters = DataFactoryDefaults.DEPTH_METERS;
      depthInFathoms = DataFactoryDefaults.DEPTH_FATHOMS;
      depthOffset = DataFactoryDefaults.DEPTH_OFFSET;
      depthRange = DataFactoryDefaults.DEPTH_RANGE;

      headingTrueGround = DataFactoryDefaults.HEADING_TRUE_GROUND;
      headingTrueWater = DataFactoryDefaults.HEADING_TRUE_WATER;
      headingMagneticGround = DataFactoryDefaults.HEADING_MAGNETIC_GROUND;
      headingMagneticWater = DataFactoryDefaults.HEADING_MAGNETIC_WATER; 
      headingStatus = DataFactoryDefaults.HEADING_STATUS;
      rateOfTurn = DataFactoryDefaults.RATE_OF_TURN;
      rateOfTurnStatus = DataFactoryDefaults.RATE_OF_TURN_STATUS;
      variation = DataFactoryDefaults.MAGNETIC_VARIATION;
      variationDirection = DataFactoryDefaults.MAGNETIC_VAR_DIRECTION;
      deviation = DataFactoryDefaults.MAGNETIC_DEVIATION;
      deviationDirection = DataFactoryDefaults.MAGNETIC_DEV_DIRECTION;

      speedGroundInKnots = DataFactoryDefaults.SPEED_GROUND_KNOTS;
      speedGroundInKMH = DataFactoryDefaults.SPEED_GROUND_KMH;
      speedGroundTranverseInKnots = DataFactoryDefaults.SPEED_GROUND_TRANSVERSE_KNOTS;
      speedGroundStatus = DataFactoryDefaults.SPEED_GROUND_STATUS;
      speedGroundSternInKnots = DataFactoryDefaults.SPEED_GROUND_STERN_KNOTS;
      speedGroundSternStatus = DataFactoryDefaults.SPEED_GROUND_STERN_STATUS;
      speedWaterInKnots = DataFactoryDefaults.SPEED_WATER_KNOTS;
      speedWaterInKMH = DataFactoryDefaults.SPEED_WATER_KMH;
      speedWaterTranverseInKnots = DataFactoryDefaults.SPEED_WATER_TRANSVERSE_KNOTS;
      speedWaterStatus = DataFactoryDefaults.SPEED_WATER_STATUS;
      speedWaterSternInKnots = DataFactoryDefaults.SPEED_WATER_STERN_KNOTS;
      speedWaterSternStatus = DataFactoryDefaults.SPEED_WATER_STERN_STATUS; 
      speedMode = DataFactoryDefaults.SPEED_MODE;
      dopplerMode = DataFactoryDefaults.DOPPLER_MODE;
      
      setTrue = DataFactoryDefaults.SET_TRUE;
      setMagnetic = DataFactoryDefaults.SET_MAGNETIC;
      driftKnots = DataFactoryDefaults.DRIFT;
//      setAndDrift = new Vector2D(driftKnots, setTrue, Vector2D.IN_DEGREES);

      GPS_qualityID = DataFactoryDefaults.GPS_QUALITY_ID;
      GPS_noOfSatellites = DataFactoryDefaults.GPS_NO_OF_SATELLITES;
      GPS_HDOP = DataFactoryDefaults.GPS_HDOP;
      GPS_altitude = DataFactoryDefaults.GPS_ALTITUDE;
      GPS_geoidalSep = DataFactoryDefaults.GPS_GEO_SEP;
      GPS_ageOfDGPS = DataFactoryDefaults.DGPS_COOR_AGE;
      GPSD_GPSStation = DataFactoryDefaults.DGPS_STATION;
   
      loranGRI = DataFactoryDefaults.LORAN_GRI;
      loranSignals = DataFactoryDefaults.LORAN_SIGNALS;
      
      timeUTC = DataFactoryDefaults.UTC;
      dateYear = DataFactoryDefaults.DATE_YEAR;
      dateMonth = DataFactoryDefaults.DATE_MONTH;
      dateDay = DataFactoryDefaults.DATE_DAY;
      zoneHours = DataFactoryDefaults.ZONE_HOURS;
      zoneMinutes = DataFactoryDefaults.ZONE_MINUTES;

      datumLocalCode = DataFactoryDefaults.DATUM_LOCAL;
      datumLocalSubdivisionCode = DataFactoryDefaults.DATUM_LOCAL_SUBDIVISION;
      latitudeOffset = DataFactoryDefaults.LATITUDE_OFFSET;
      latitudeOffsetDirection = DataFactoryDefaults.LATITUDE_OFFSET_DIRECTION;
      longitudeOffset = DataFactoryDefaults.LONGITUDE_OFFSET;
      longitudeOffsetDirection = DataFactoryDefaults.LONGITUDE_OFFSET_DIRECTION;
      altitudeOffset = DataFactoryDefaults.ALTITUDE_OFFSET;
      datumReferenceCode = DataFactoryDefaults.DATUM_REFERENCE;

      targetTimeDate = new Date(System.currentTimeMillis ());
      
      windAngle = DataFactoryDefaults.WIND_ANGLE;
      windReference = DataFactoryDefaults.WIND_REFERENCE;
      windSpeed = DataFactoryDefaults.WIND_SPEED;
      windUnits = DataFactoryDefaults.WIND_UNITS;
      windStatus = DataFactoryDefaults.WIND_STATUS;
      

      ossTrain = DataFactoryDefaults.OSS_TRAIN;
      ossElevation = DataFactoryDefaults.OSS_ELEVATION;
      ossStatusFromSCCS = DataFactoryDefaults.OSS_SCCS_STATUS;
      ossStatusFromDCU = DataFactoryDefaults.OSS_DCU_STATUS;
      
      freeTextSentence = "$";
   }

   /** 
    * Get latitude.
    *
    * @return Latitude.
    */
   public String getLatitude () {
      return latitude;
   }
   
   /** 
    * Set latitude.
    *
    * @param lat Latitude.
    */
   public void setLatitude (String lat) {
      if (lat != null) {
         latitude = lat;
      }
   }
      
   /** 
    * Get latitude hemisphere.
    *
    * @return Latitude's hemisphere, 'N' for north, 'S' for South
    */
   public String getLatitudeHemisphere () {
      return latitudeHemisphere;
   }
   
   /** 
    * Set latitude hemisphere.
    *
    * @param hemisphere Latitude's hemisphere, 'N' for north, 'S' for South
    */
   public void setLatitudeHemisphere (String hemisphere) {
      if (hemisphere != null) {
         latitudeHemisphere = hemisphere;
      }
   }
      
   /** 
    * Get longitude.
    *
    * @return Longitude.
    */
   public String getLongitude () {
      return longitude;
   }
   
   /** 
    * Set longitude.
    *
    * @param lon Longitude
    */
   public void setLongitude (String lon) {
      if (lon != null) {
         longitude = lon;
      }
   }
      
   /** 
    * Get longitude hemisphere.
    *
    * @return Longitude's hemisphere, 'E' for east, 'W' for west.
    */
   public String getLongitudeHemisphere () {
      return longitudeHemisphere;
   }
   
   /** 
    * Set longitude hemisphere.
    *
    * @param hemisphere Longitude's hemisphere, 'E' for east, 'W' for west.
    */
   public void setLongitudeHemisphere (String hemisphere) {
      if (hemisphere != null) {
         longitudeHemisphere = hemisphere;
      }
   }
      
   /** 
    * Get ownship's position status.
    *
    * @return Ownship's position status, 'A' for valid, 'V' for invalid.
    */
   public String getPositionStatusID () {
      return positionStatusID;
   }
   
   /** 
    * Set ownship's position status.
    *
    * @param status Ownship's position status, 'A' for valid, 'V' for invalid.
    */
   public void setPositionStatusID (String status) {
      if (status != null) {
         positionStatusID = status;
      }
   }
   
   /** 
    * Get ownship's position mode.
    *
    * @return Ownship's position mode, 'A' for autonomous, 'D' for differential.
    */
   public String getPositionModeID () {
      return positionModeID;
   }
   
   /** 
    * Set ownship's position mode.
    *
    * @param mode Ownship's position mode, 'A' for autonomous, 'D' for differential.
    */
   public void setPositionModeID (String mode) {
      if (mode != null) {
         positionModeID = mode;
      }
   }
   
   /** 
    * Get ownship's depth, in feet.
    *
    * @return Ownship's depth in feet.
    */
   public String getDepthInFeet () {
      return depthInFeet;
   }
   
   /** 
    * Set ownship's depth, in feet.
    *
    * @param depth Depth of ownship in feet.
    */
   public void setDepthInFeet (String depth) {
      if (depth != null) {
         depthInFeet  = depth;
      }
   }
   
   /** 
    * Get ownship's depth, in feet.
    *
    * @return Ownship's depth in feet.
    */
   public String getDepthInMeters () {
      return depthInMeters;
   }
   
   /** 
    * Set ownship's depth, in feet.
    *
    * @param depth Depth of ownship in feet.
    */
   public void setDepthInMeters (String depth) {
      if (depth != null) {
         depthInMeters  = depth;
      }
   }
   
   /** 
    * Get ownship's depth, in fathoms.
    *
    * @return Ownship's depth in fathoms.
    */
   public String getDepthInFathoms () {
      return depthInFathoms;
   }
   
   /** 
    * Set ownship's depth, in fathoms.
    *
    * @param depth Depth of ownship in fathoms.
    */
   public void setDepthInFathoms (String depth) {
      if (depth != null) {
         depthInFathoms  = depth;
      }
   }
   
   /** 
    * Get ownship's depth offset.
    *
    * @return Ownship's depth offset from tranducer, in meters, + to waterline, - to keel.
    */
   public String getDepthOffset () {
      return depthOffset ;
   }
   
   /** 
    * Set ownship's depth offset.
    *
    * @param offset Depth offset from tranducer, in meters, + to waterline, - to keel.
    */
   public void setDepthOffset (String offset) {
      if (offset != null) {
         depthOffset  = offset;
      }
   }
   
   /** 
    * Get ownship's depth range, in meters.
    *
    * @return Ownship's depth range, in meters .
    */
   public String getDepthRange () {
      return depthRange;
   }
   
   /** 
    * Set ownship's depth range, in meters.
    *
    * @param range Ownship's depth range, in meters.
    */
   public void setDepthRange (String range) {
      if (range != null) {
         depthRange = range;
      }
   }
   
   /** 
    * Get true heading referenced to ground (or COG).
    *
    * @return True heading over ground.
    */
   public String getHeadingTrueGround () {
      return headingTrueGround;
   }
   
   /** 
    * Set true heading referenced to ground (or COG).
    *
    * @param value True heading over ground.
    */
   public void setHeadingTrueGround (String value) {
      if (value != null) {
         headingTrueGround = value;
      }
   }
   
   /** 
    * Get true heading referenced to water.
    *
    * @return True heading through the water.
    */
   public String getHeadingTrueWater () {
      return headingTrueWater;
   }
   
   /** 
    * Set true heading referenced to water.
    *
    * @param value True heading through the water.
    */
   public void setHeadingTrueWater (String value) {
      if (value != null) {
         headingTrueWater = value;
      }
   }
   
   /** 
    * Get magnetic heading referenced to ground.
    *
    * @return Magnetic heading referenced to ground.
    */
   public String getHeadingMagneticGround () {
      return headingMagneticGround;
   }
   
   /** 
    * Set magnetic heading referenced to ground.
    *
    * @param value Magnetic heading referenced to ground.
    */
   public void setHeadingMagneticGround (String value) {
      if (value != null) {
         headingMagneticGround = value;
      }
   }
   
   /** 
    * Get magnetic heading referenced to water.
    *
    * @return Magnetic heading referenced to water.
    */
   public String getHeadingMagneticWater () {
      return headingMagneticWater;
   }
   
   /** 
    * Set magnetic heading referenced to water.
    *
    * @param value Magnetic heading referenced to water.
    */
   public void setHeadingMagneticWater (String value) {
      if (value != null) {
         headingMagneticWater = value;
      }
   }

   /** 
    * Get heading status.
    *
    * @return Heading status.
    */
   public String getHeadingStatus () {
      return headingStatus;
   }
   
   /** 
    * Set heading status.
    *
    * @param value Heading status.
    */
   public void setHeadingStatus (String value) {
      if (value != null) {
         headingStatus = value;
      }
   }

   /** 
    * Get rate of turn.
    *
    * @return Rate of turn.
    */
   public String getRateOfTurn () {
      return rateOfTurn;
   }
   
   /** 
    * Set rate of turn.
    *
    * @param value Rate of turn.
    */
   public void setRateOfTurn (String value) {
      if (value != null) {
         rateOfTurn = value;
      }
   }

   /** 
    * Get rate of turn status.
    *
    * @return Rate of turn status.
    */
   public String getRateOfTurnStatus () {
      return rateOfTurnStatus;
   }
   
   /** 
    * Set rate of turn status.
    *
    * @param value Rate of turn status.
    */
   public void setRateOfTurnStatus (String value) {
      if (value != null) {
         rateOfTurnStatus = value;
      }
   }

   /** 
    * Get magnetic variation.
    *
    * @return Magnetic variation.
    */
   public String getVariation () {
      return variation;
   }
   
   /** 
    * Set magnetic variation.
    *
    * @param value Magnetic variation.
    */
   public void setVariation (String value) {
      if (value != null) {
         variation = value;
      }
   }

   /** 
    * Get magnetic variation direction.
    *
    * @return Magnetic variation direction.
    */
   public String getVariationDirection () {
      return variationDirection;
   }
   
   /** 
    * Set magnetic variation direction.
    *
    * @param value Magnetic variation direction.
    */
   public void setVariationDirection (String value) {
      if (value != null) {
         variationDirection = value;
      }
   }

   /** 
    * Get magnetic compass deviation.
    *
    * @return Magnetic compass deviation.
    */
   public String getDeviation () {
      return deviation;
   }
   
   /** 
    * Set magnetic compass deviation.
    *
    * @param value Magnetic compass deviation.
    */
   public void setDeviation (String value) {
      if (value != null) {
         deviation = value;
      }
   }

   /** 
    * Get magnetic compass deviation direction.
    *
    * @return Magnetic compass deviation direction.
    */
   public String getDeviationDirection () {
      return deviationDirection;
   }
   
   /** 
    * Set magnetic compass deviation direction.
    *
    * @param value Magnetic compass deviation direction.
    */
   public void setDeviationDirection (String value) {
      if (value != null) {
         deviationDirection = value;
      }
   }
   
   /** 
    * Get ground speed, in knots.
    *
    * @return Vessel's speed.
    */
   public String getSpeedGroundInKnots () {
      return speedGroundInKnots;
   }
   
   /** 
    * Set ground speed, in knots.
    *
    * @param value Vessel's speed.
    */
   public void setSpeedGroundInKnots (String value) {
      if (value != null) {
         speedGroundInKnots = value;
      }
   }

   /** 
    * Get ground speed, in kilometers per hour (KMH).
    *
    * @return Vessel's speed.
    */
   public String getSpeedGroundInKMH () {
      return speedGroundInKMH;
   }
   
   /** 
    * Set ground speed, in kilometers per hour (KMH).
    *
    * @param value Vessel's speed.
    */
   public void setSpeedGroundInKMH (String value) {
      if (value != null) {
         speedGroundInKMH = value;
      }
   }

   /** 
    * Get ground traverse (sideways) speed, in knots.
    *
    * @return Vessel's speed.
    */
   public String getSpeedGroundTranverseInKnots () {
      return speedGroundTranverseInKnots;
   }
   
   /** 
    * Set ground traverse (sideways) speed, in knots..
    *
    * @param value Vessel's speed.
    */
   public void setSpeedGroundTranverseInKnots (String value) {
      if (value != null) {
         speedGroundTranverseInKnots = value;
      }
   }

   /** 
    * Get ground speed status.
    *
    * @return Status.
    */
   public String getSpeedGroundStatus () {
      return speedGroundStatus;
   }
   
   /** 
    * Set ground speed status.
    *
    * @param value Status.
    */
   public void setSpeedGroundStatus (String value) {
      if (value != null) {
         speedGroundStatus = value;
      }
   }

   /** 
    * Get ground stern speed, in knots.
    *
    * @return Speed.
    */
   public String getSpeedGroundSternInKnots () {
      return speedGroundSternInKnots;
   }
   
   /** 
    * Set ground stern speed, in knots.
    *
    * @param value Speed.
    */
   public void setSpeedGroundSternInKnots (String value) {
      if (value != null) {
         speedGroundSternInKnots = value;
      }
   }

   /** 
    * Get ground stern speed status.
    *
    * @return Status.
    */
   public String getSpeedGroundSternStatus () {
      return speedGroundSternStatus;
   }
   
   /** 
    * Set ground stern speed status.
    *
    * @param value Status.
    */
   public void setSpeedGroundSternStatus (String value) {
      if (value != null) {
         speedGroundSternStatus = value;
      }
   }

   /** 
    * Get water speed, in knots.
    *
    * @return Speed.
    */
   public String getSpeedWaterInKnots () {
      return speedWaterInKnots;
   }
   
   /** 
    * Set water speed, in knots.
    *
    * @param value Speed.
    */
   public void setSpeedWaterInKnots (String value) {
      if (value != null) {
         speedWaterInKnots = value;
      }
   }

   /** 
    * Get water speed, in kilometers per hour (KMH).
    *
    * @return Speed.
    */
   public String getSpeedWaterInKMH () {
      return speedWaterInKMH;
   }
   
   /** 
    * Set water speed, in kilometers per hour (KMH).
    *
    * @param value Speed.
    */
   public void setSpeedWaterInKMH (String value) {
      if (value != null) {
         speedWaterInKMH = value;
      }
   }

   /** 
    * Get water traverse (sideways) speed, in knots.
    *
    * @return Speed.
    */
   public String getSpeedWaterTranverseInKnots () {
      return speedWaterTranverseInKnots;
   }
   
   /** 
    * Set water traverse (sideways) speed, in knots.
    *
    * @param value Speed.
    */
   public void setSpeedWaterTranverseInKnots (String value) {
      if (value != null) {
         speedWaterTranverseInKnots = value;
      }
   }

   /** 
    * Get water speed status.
    *
    * @return Status.
    */
   public String getSpeedWaterStatus () {
      return speedWaterStatus;
   }
   
   /** 
    * Set water speed status.
    *
    * @param value Status.
    */
   public void setSpeedWaterStatus (String value) {
      if (value != null) {
         speedWaterStatus = value;
      }
   }

   /** 
    * Get water stern speed, in knots.
    *
    * @return Speed.
    */
   public String getSpeedWaterSternInKnots () {
      return speedWaterSternInKnots;
   }
   
   /** 
    * Set water stern speed, in knots.
    *
    * @param value Speed.
    */
   public void setSpeedWaterSternInKnots (String value) {
      if (value != null) {
         speedWaterSternInKnots = value;
      }
   }

   /** 
    * Get water stern speed status.
    *
    * @return Status.
    */
   public String getSpeedWaterSternStatus () {
      return speedWaterSternStatus;
   }
   
   /** 
    * Set water stern speed status.
    *
    * @param value Status.
    */
   public void setSpeedWaterSternStatus (String value) {
      if (value != null) {
         speedWaterSternStatus = value;
      }
   }

   /** 
    * Get speed mode.
    *
    * @return Mode.
    */
   public String getSpeedMode () {
      return speedMode;
   }
   
   /** 
    * Set speed mode.
    *
    * @param value Mode.
    */
   public void setSpeedMode (String value) {
      if (value != null) {
         speedMode = value;
      }
   }

   /** 
    * Get Doppler mode.
    *
    * @return Mode.
    */
   public String getDopplerMode () {
      return dopplerMode;
   }
   
   /** 
    * Set Doppler mode.
    *
    * @param value Mode.
    */
   public void setDopplerMode (String value) {
      if (value != null) {
         dopplerMode = value;
      }
   }
   
   /** 
    * Get true set.
    *
    * @return True set.
    */
   public String getSetTrue () {
      return setTrue;
   }
   
   /** 
    * Set true set.
    *
    * @param value True set.
    */
   public void setSetTrue (String value) {
      if (value != null) {
         setTrue = value;
      }
   }

   /** 
    * Get magnetic set.
    *
    * @return Magnetic set.
    */
   public String getSetMagnetic () {
      return setMagnetic;
   }
   
   /** 
    * Set magnetic set.
    *
    * @param value Magnetic set..
    */
   public void setSetMagnetic (String value) {
      if (value != null) {
         setMagnetic = value;
      }
   }

   /** 
    * Get drift, in knots.
    *
    * @return Drift.
    */
   public String getDriftKnots () {
      return driftKnots;
   }
   
   /** 
    * Set drift, in knots.
    *
    * @param value Drift.
    */
   public void setDriftKnots (String value) {
      if (value != null) {
         driftKnots = value;
      }
   }

   /**
    * Get the GPS quality ID.
    *
    * @return GPS quality indicator.
    */
   public String getGPSQualityId () {
      return GPS_qualityID;
   }
   
   /**
    * Set the GPS quality ID.
    *
    * @param id GPS quality indicator.
    */
   public void setGPSQualityId (String id) {
      if (id !=null) {
         GPS_qualityID = id;
      }
   }

   /**
    * Get GPS number of satellites.
    *
    * @return Number of satellites used, (w/o leading zeros).
    */
   public String getGPSNoOfSatellites () {
      return GPS_noOfSatellites;
   }
   
   /**
    * Set GPS number of satellites.
    *
    * @param satellites Number of satellites used, (w/o leading zeros).
    */
   public void setGPSNoOfSatellites (String satellites) {
      if (satellites !=null) {
         GPS_noOfSatellites = satellites;
      }
   }
   
   /**
    * Get GPS HDOP, horizontal dillution of precision.
    * Note: HDOP * 4 ~= horizontal accuracy, in meters.
    *
    * @return Horizontal Dilution of Precision, HDOP
    */
   public String getGPSHDOP () {
      return GPS_HDOP;
   }
   
   /**
    * Set GPS HDOP, horizontal dillution of precision.
    * Note: HDOP * 4 ~= horizontal accuracy, in meters.
    *
    * @param hdop Horizontal Dilution of Precision, HDOP
    */
   public void setGPSHDOP (String hdop) {
      if (hdop !=null) {
         GPS_HDOP = hdop;
      }
   }

   /**
    * Get GPS altitude.
    *
    * @return GPS altitude, in meters.
    */
   public String getGPSAltitude () {
      return GPS_altitude;
   }
   
   /**
    * Set GPS altitude.
    *
    * @param altitude GPS altitude, in meters.
    */
   public void setGPSAltitude (String altitude) {
      if (altitude !=null) {
         GPS_altitude = altitude;
      }
   }

   /**
    * Get GPS geodal separation.
    *
    * @return Geoidal separation, in meters.
    */
   public String getGPSGeoidalSeparation () {
      return GPS_geoidalSep;
   }
    
   /**
    * Set GPS geodal separation.
    *
    * @param separation Geoidal separation, in meters.
    */
   public void setGPSGeoidalSeparation (String separation) {
      if (separation !=null) {
         GPS_geoidalSep = separation;
      }
   }

   /**
    * Get age of GPS differential corrections.
    *
    * @return Age of DGPS data, in seconds (null if not used).
    */
   public String getGPSAgeOfDGPS () {
      return GPS_ageOfDGPS;
   }
   
   /**
    * Set age of GPS differential corrections.
    *
    * @param age Age of DGPS data, in seconds (null if not used).
    */
   public void setGPSAgeOfDGPS (String age) {
      if (age !=null) {
         GPS_ageOfDGPS = age;
      }
   }

   /**
    * Get GPS differential station.
    *
    * @return DGPS reference station ID, (w/o leading zeros).
    */
   public String getGPSDifferentialStation () {
      return GPSD_GPSStation;
   }
      
   /**
    * Set GPS differential station.
    *
    * @param station DGPS reference station ID, (w/o leading zeros).
    */
   public void setGPSDifferentialStation (String station) {
      if (station !=null) {
         GPSD_GPSStation = station;
      }
   }
   
   /**
    * Get the LORAN-C GRI in microseconds.
    *
    * @return GRI.
    */
   public String getLoranGRI () {
      return loranGRI;
   }
   
   /**
    * Set the LORAN-C GRI in microseconds.
    *
    * @param text GRI.
    */
   public void setLoranGRI (String text) {
      if ((text != null) && (text.length () < 10)) {
         loranGRI = text;
      }
   }

   /**
    * Get the LORAN-C TD (or TOA for master)
    *
    * @param station Signal (0 = master, 1-5 for legs)
    * @return TD (or TOA for master).
    */
   public String getLoranTOATD (int station) {
      if (station < 6) {
         return loranSignals[station][0];
      }
      else {
         return "";
      }
   }
   
   /**
    * Set the LORAN-C TD (or TOA for master)
    *
    * @param station Signal (0 = master, 1-5 for legs)
    * @param text TD (or TOA for master).
    */
   public void setLoranTOATD (int station, String text) {
      if ((text != null) && (text.length () < 10)) {
         if (station < 6) {
            loranSignals[station][0] = text;
         }
      }
   }
   
   /**
    * Get the LORAN-C signal status.
    *
    * @return Status.
    * @param station Master or station value (0-5).
    */
   public String getLoranStatus (int station) {
      if (station < 6) {
         return loranSignals[station][1];
      }
      else {
         return "";
      }
   }
   
   /**
    * Set the LORAN-C signal status.
    *
    * @param station Master or station value  (0-5).
    * @param text Status.
    */
   public void setLoranStatus (int station, String text) {
      if ((text != null) && (text.length () < 10)) {
         if (station < 6) {
            loranSignals[station][1] = text;
         }
      }
   }
   
   /**
    * Get the LORAN-C ECD.
    * 
    * @param station Signal (0 = master, 1-5 for legs)
    * @return ECD.
    */
   public String getLoranECD (int station) {
      if (station < 6) {
         return loranSignals[station][2];
      }
      else {
         return "";
      }
   }
   
   /**
    * Set the LORAN-C ECD.
    *
    * @param station Signal (0 = master, 1-5 for legs)
    * @param text ECD.
    */
   public void setLoranECD (int station, String text) {
      if ((text != null) && (text.length () < 10)) {
         if (station < 6) {
            loranSignals[station][2] = text;
         }
      }
   }
   
   /**
    * Get the LORAN-C SNR.
    *
    * @param station Signal (0 = master, 1-5 for legs)
    * @return SNR.
    */
   public String getLoranSNR (int station) {
      if (station < 6) {
         return loranSignals[station][3];
      }
      else {
         return "";
      }
   }
   
   /**
    * Set the LORAN-C SNR.
    *
    * @param station Signal (0 = master, 1-5 for legs)
    * @param text SNR.
    */
   public void setLoranSNR (int station, String text) {
      if ((text != null) && (text.length () < 10)) {
         if (station < 6) {
            loranSignals[station][3] = text;
         }
      }
   }

   /**
    * Get the time, in UTC.
    *
    * @return Time.
    */
   public String getTimeUTC () {
      return timeUTC;
   }
   
   /**
    * Set the time, in UTC.
    *
    * @param utc Time.
    */
   public void setTimeUTC (String utc) {
      if (utc !=null) {
         timeUTC = utc;
      }
   }

   /**
    * Get the date's year.
    *
    * @return year value.
    */
   public String getDateYear () {
      return dateYear;
   }
   
   /**
    * Set the date's year.
    *
    * @param year Year value.
    */
   public void setDateYear (String year) {
      if (year !=null) {
         dateYear = year;
      }
   }

   /**
    * Get the date's month.
    *
    * @return month value.
    */
   public String getDateMonth () {
      return dateMonth;
   }
   
   /**
    * Set the date's month.
    *
    * @param month Month value.
    */
   public void setDateMonth (String month) {
      if (month !=null) {
         dateMonth = month;
      }
   }

   /**
    * Get the date's day.
    *
    * @return day value.
    */
   public String getDateDay () {
      return dateDay;
   }
   
   /**
    * Set the date's day.
    *
    * @param day Day value.
    */
   public void setDateDay (String day) {
      if (day !=null) {
         dateDay = day;
      }
   }
   

   /**
    * Get local zone hours.
    *
    * @return hours for the local time zone.
    */
   public String getZoneHours () {
      return zoneHours;
   }
   
   /**
    * Set local zone hours.
    *
    * @param hours Hours for the local time zone.
    */
   public void setZoneHours (String hours) {
      if (hours !=null) {
      zoneHours = hours;
      }
   }
   
   /**
    * Get local zone minutes.
    *
    * @return minutes for the local time zone.
    */
   public String getZoneMinutes () {
      return zoneMinutes;
   }
   
   /**
    * Set local zone minutes.
    *
    * @param minutes Minutes for the local time zone.
    */
   public void setZoneMinutes (String minutes) {
      if (minutes !=null) {
         zoneMinutes = minutes;
      }
   }

   /**
    * Get local datum code.
    *
    * @return Local datum code.
    */
   public String getDatumLocalCode () {
      return datumLocalCode;
   }
   
   /**
    * Set local datum code.
    *
    * @param code Local datum code.
    */
   public void setDatumLocalCode (String code) {
      if (code != null) {
         datumLocalCode = code;
      }
   }
   
   /**
    * Get local datum subdivision code.
    *
    * @return Local datum subdivision code.
    */
   public String getDatumLocalSubdivisionCode () {
      return datumLocalSubdivisionCode;
   }
   
   /**
    * Set local datum subdivision code.
    *
    * @param code Local datum subdivision code.
    */
   public void setDatumLocalSubdivisionCode (String code) {
      if (code != null) {
         datumLocalSubdivisionCode = code;
      }
   }

   /**
    * Get reference datum code.
    *
    * @return Reference datum code.
    */
   public String getDatumReferenceCode () {
      return datumReferenceCode;
   }
   
   /**
    * Set reference datum code.
    *
    * @param code Reference datum code.
    */
   public void setDatumReferenceCode (String code) {
      if (code != null) {
         datumReferenceCode = code;
      }
   }

   /**
    * Get datum latitude offset.
    *
    * @return Latitude offset, in minutes.
    */
   public String getLatitudeOffset () {
      return latitudeOffset;
   }  
   
   /**
    * Set datum latitude offset.
    *
    * @param offset Latitude offset, in minutes.
    */
   public void setLatitudeOffset (String offset) {
      if (offset != null) {
         latitudeOffset = offset;
      }
   }
   
   /**
    * Get datum latitude offset direction.
    *
    * @return Datum latitude offset direction.
    */
   public String getLatitudeOffsetDirection () {
      return latitudeOffsetDirection;
   }
   
   /**
    * Set datum latitude offset direction.
    *
    * @param code Datum latitude offset direction, 'N' or 'S'.
    */
   public void setLatitudeOffsetDirection (String code) {
      if (code != null) {
         latitudeOffsetDirection = code;
      }
   }
   
   /**
    * Get datum longitude offset.
    *
    * @return Longitude offset, in minutes.
    */
   public String getLongitudeOffset () {
      return longitudeOffset;
   }
   
   /**
    * Set datum longitude offset.
    *
    * @param offset Longitude offset, in minutes.
    */
   public void setLongitudeOffset (String offset) {
      if (offset != null) {
         longitudeOffset = offset;
      }
   }
   
   /**
    * Get datum longitude offset direction.
    *
    * @return Datum longitude offset direction.
    */
   public String getLongitudeOffsetDirection () {
      return longitudeOffsetDirection;
   }
   
   /**
    * Set datum longitude offset direction.
    *
    * @param code Datum longitude offset direction, 'E' or 'W'.
    */
   public void setLongitudeOffsetDirection (String code) {
      if (code != null) {
         longitudeOffsetDirection = code;
      }
   }
   
   /**
    * Get datum altitude offset.
    *
    * @return Altitude offset, in meters.
    */
   public String getAltitudeOffset () {
      return altitudeOffset;
   }
   
   /**
    * Set datum altitude offset.
    *
    * @param offset Offset, in meters.
    */
   public void setAltitudeOffset (String offset) {
      if (offset != null) {
         altitudeOffset = offset;
      }
   }

   /**
    * Get target date and time.
    *
    * @return Target date and time.
    */
   public Date getTargetDateTime () {
      return (Date) targetTimeDate.clone ();
   }
   
   /**
    * Set target date and time.
    *
    * @param timeDate Target date and time.
    */
   public void setTargetDateTime (Date timeDate) {
      targetTimeDate = timeDate;
   }

   /**
    * Get free text sentence data.
    *
    * @return Free text sentence data.
    */
   public String getFreeTextSentence () {
      return freeTextSentence;
   }
   
   /**
    * Set free text sentence data.
    *
    * @param text Free text sentence data.
    */
   public void setFreeTextSentence (String text) {
      if (text.length () < 128) {
         freeTextSentence = text;
      }
   }
     
   /**
    * Get wind angle.
    *
    * @return Wind angle.
    */
   public String getWindAngle () {
      return windAngle;
   }
   
   /**
    * Set wind angle.
    *
    * @param text Wind angle.
    */
   public void setWindAngle (String text) {
      if (text.length () < 128) {
         windAngle = text;
      }
   }
   /**
    * Get wind reference ('R' - relative of 'T' - true).
    *
    * @return Wind reference.
    */
   public String getWindReference () {
      return windReference;
   }
   
   /**
    * Set wind reference ('R' - relative of 'T' - true).
    *
    * @param text Wind reference.
    */
   public void setWindReference (String text) {
      if (text.length () < 128) {
         windReference = text;
      }
   }
   /**
    * Get wind speed.
    *
    * @return Wind speed.
    */
   public String getWindSpeed () {
      return windSpeed;
   }
   
   /**
    * Set wind speed.
    *
    * @param text Wind speed.
    */
   public void setWindSpeed (String text) {
      if (text.length () < 128) {
         windSpeed = text;
      }
   }
   /**
    * Get wind units ('N' - knots, 'M' - m/s, 'K' - km/h, 'S' - MPH).
    *
    * @return Wind units.
    */
   public String getWindUnits () {
      return windUnits;
   }
   
   /**
    * Set wind units ('N' - knots, 'M' - m/s, 'K' - km/h, 'S' - MPH).
    *
    * @param text Wind units.
    */
   public void setWindUnits (String text) {
      if (text.length () < 128) {
         windUnits = text;
      }
   }
   
   /**
    * Get wind status ('A' - valid, 'V' - invalid).
    *
    * @return Wind status.
    */
   public String getWindStatus () {
      return windStatus;
   }
   
   /**
    * Set wind status ('A' - valid, 'V' - invalid).
    *
    * @param text Wind status.
    */
   public void setWindStatus (String text) {
      if (text.length () < 128) {
         windStatus = text;
      }
   }
      
   /**
    * Get OSS train angle (000.0 to 359.0 degrees).
    *
    * @return OSS train angle.
    */
   public String getOssTrain () {
      return ossTrain;
   }
   
   /**
    * Set OSS train angle (000.0 to 359.0 degrees).
    *
    * @param text OSS train angle.
    */
   public void setOssTrain (String text) {
      if (text.length () < 128) {
         ossTrain = text;
      }
   }
    
   /**
    * Get OSS elevation angle (000.0 to 090.0 degrees).
    *
    * @return OSS elevation angle.
    */
   public String getOssElevation () {
      return ossElevation;
   }
   
   /**
    * Set OSS elevation angle (000.0 to 090.0 degrees).
    *
    * @param text OSS elevation angle.
    */
   public void setOssElevation (String text) {
      if (text.length () < 128) {
         ossElevation = text;
      }
   }
    
   /**
    * Get OSS status from SCCS computer to DCU.
    *
    * @return OSS status.
    */
   public String getOssStatusFromSCCS () {
      return ossStatusFromSCCS;
   }
   
   /**
    * Set OSS status from SCCS computer to DCU.
    *
    * @param text OSS status.
    */
   public void setOssStatusFromSCCS (String text) {
      if (text.length () < 128) {
         ossStatusFromSCCS = text;
      }
   }
    
   /**
    * Get OSS status from DCU computer to SCCS.
    *
    * @return OSS status.
    */
   public String getOssStatusFromDCU () {
      return ossStatusFromDCU;
   }
   
   /**
    * Set OSS status from DCU computer to SCCS.
    *
    * @param text OSS status.
    */
   public void setOssStatusFromDCU (String text) {
      if (text.length () < 128) {
         ossStatusFromDCU = text;
      }
   }
    
   
//   
//   /**
//    * Get ownship course over ground (COG).
//    *
//    * @return Ownship COG.
//    */
//   public double getOwnshipCOG () {
//      if (ownshipUpdate != null) {
//         cogSog.setRadius (ownshipUpdate.speedActual);
//         cogSog.setDegrees (ownshipUpdate.headingActual);
//         cogSog.add (setAndDrift);
//         return cogSog.getDegrees ();
//      }
//      else {
//         return 0.0;
//      }
//   }
//   
//   /**
//    * Get ownship speed over ground (SOG).
//    *
//    * @return Ownship SOG.
//    */
//   public double getOwnshipSOG () {
//      if (ownshipUpdate != null) {
//         cogSog.setRadius (ownshipUpdate.speedActual);
//         cogSog.setDegrees (ownshipUpdate.headingActual);
//         cogSog.add (setAndDrift);
//         return cogSog.getRadius ();
//      }
//      else {
//         return 0.0;
//      }
//   }
//   
   /**
    * Set ownship data object.
    *
    * @param ownship Ownship update object.
    */
   public void updateOwnship (OwnshipUpdate ownship) {
      if (ownship != null) {
         ownshipUpdate = ownship;
      }
   }
   //=============== O = W = N = S = H = I = P === D = A = T= A ================
   /** 
    * Get ownship's current time, in UTC.
    *
    * @return Ownship's current time, in UTC.
    */
   public String getSimulatedUTC () {
      if (ownshipUpdate != null) {
         calendarObj.setTime (ownshipUpdate.timeDate);
         return (twoDigitForm.format (calendarObj.get(Calendar.HOUR_OF_DAY)) +
                 twoDigitForm.format (calendarObj.get(Calendar.MINUTE)) +
                 twoDigitForm.format (calendarObj.get(Calendar.SECOND)) + '.' +
                 threeDigitForm.format (calendarObj.get(Calendar.MILLISECOND)));
      }
      else {
         return "??????.????";
      }
   }
   
   /** 
    * Get ownship's current date year.
    *
    * @return Ownship's current date year.
    */
   public String getSimulatedYear () {
      if (ownshipUpdate != null) {
         calendarObj.setTime (ownshipUpdate.timeDate);
         return "" + calendarObj.get(Calendar.YEAR);
      }
      else {
         return "????";
      }
   }
   
   /** 
    * Get ownship's current date month.
    *
    * @return Ownship's current date month.
    */
   public String getSimulatedMonth () {
      if (ownshipUpdate != null) {
         calendarObj.setTime (ownshipUpdate.timeDate);
         return twoDigitForm.format (calendarObj.get(Calendar.MONTH) + 1);
      }
      else {
         return "????";
      }
   }
      
   /** 
    * Get ownship's current date day.
    *
    * @return Ownship's current date day.
    */
   public String getSimulatedDay () {
      if (ownshipUpdate != null) {
         calendarObj.setTime (ownshipUpdate.timeDate);
         return twoDigitForm.format (calendarObj.get(Calendar.DAY_OF_MONTH));
      }
      else {
         return "????";
      }
   }
      
   /** 
    * Get ownship's current date day.
    *
    * @return Ownship's current date day.
    */
   public Date getSimulatedTimeDateValue () {
      if (ownshipUpdate != null) {
         return ownshipUpdate.timeDate;
      }
      else {
         return null;
      }
   }
   
   /** 
    * Get ownship's latitude.
    *
    * @return Ownship's latitude (+90 to -90 degrees), negative (-) is South, positive (+) is North.
    */
   public String getSimulatedLatitude () {
      if (ownshipUpdate != null) {
         return (twoDigitForm.format ((int)Math.abs(ownshipUpdate.latitude)) +
                 minuteForm.format (Math.abs(ownshipUpdate.latitude % 1.0) * 60.0));
      }
      else {
         return "????.????";
      }
   }
   
   /** 
    * Get ownship's latitude.
    *
    * @return Ownship's latitude (+90 to -90 degrees), negative (-) is South, positive (+) is North.
    */
   public String getSimulatedLatitudeHemisphere () {
      if (ownshipUpdate != null) {
         if (ownshipUpdate.latitude > 0.0) {
            return "N";
         }
         else {
            return "S";
         }
      }
      else {
         return "?";
      }
   }
   
   /** 
    * Get ownship's latitude.
    *
    * @return Ownship's latitude (+90 to -90 degrees), negative (-) is South, positive (+) is North.
    */
   public double getSimulatedLatitudeValue () {
      if (ownshipUpdate != null) {
         return ownshipUpdate.latitude;
      }
      else {
         return 0.0;
      }
   }
   
   /** 
    * Get ownship's longitude.
    *
    * @return Ownship's longitude (+180 to -180 degrees),  negative (-) is West, positive (+) is East.
    */
   public String getSimulatedLongitude () {
      if (ownshipUpdate != null) {
         return (threeDigitForm.format ((int)Math.abs(ownshipUpdate.longitude)) +
                 minuteForm.format (Math.abs(ownshipUpdate.longitude % 1.0) * 60.0));
      }
      else {
         return "?????.????";
      }
   }
   
   /** 
    * Get ownship's longitude.
    *
    * @return Ownship's longitude (+180 to -180 degrees),  negative (-) is West, positive (+) is East.
    */
   public String getSimulatedLongitudeHemisphere () {
      if (ownshipUpdate != null) {
         if (ownshipUpdate.longitude > 0.0) {
            return "E";
         }
         else {
            return "W";
         }
      }
      else {
         return "?";
      }
   }
   
   /** 
    * Get ownship's longitude.
    *
    * @return Ownship's longitude (+180 to -180 degrees),  negative (-) is West, positive (+) is East.
    */
   public double getSimulatedLongitudeValue () {
      if (ownshipUpdate != null) {
         return ownshipUpdate.longitude;
      }
      else {
         return 0.0;
      }
   }
   
   /** 
    * Get ownship's heading.
    *
    * @return Ownship's heading, in degrees True, range: {0.0, 360.0]
    */
   public String getSimulatedHeadingTrueWater () {
      if (ownshipUpdate != null) {
         return headingForm.format(ownshipUpdate.headingActual);
      }
      else {
         return "???.?";
      }
   }
   
   /** 
    * Get ownship's heading.
    *
    * @return Ownship's heading, in degrees True, range: {0.0, 360.0]
    */
   public double getSimulatedCTWValue () {
      if (ownshipUpdate != null) {
         return ownshipUpdate.headingActual;
      }
      else {
         return 0.0;
      }
   }
   
   /** 
    * Get ownship's heading, in magnetic.
    *
    * @return Ownship's heading, in degrees magnetic, range: {0.0, 360.0]
    */
   public String getSimulatedHeadingMagneticWater () {
      if (ownshipUpdate != null) {
         double heading = ownshipUpdate.headingActual + 
            DataFactoryDefaults.MAGNETIC_VARIATION_SIMULATED;
         if (heading > 360.0) {
            heading -= 360.0;
         }
         return headingForm.format(heading);
      }
      else {
         return "???.?";
      }
   }
   
   /** 
    * Get ownship's heading over ground, in true.
    *
    * @return Ownship's heading, in degrees True, range: {0.0, 360.0]
    */
   public String getSimulatedHeadingTrueOverGround () {
      if (ownshipUpdate != null) {
         return headingForm.format(ownshipUpdate.headingOverGround);
      }
      else {
         return "???.?";
      }
   }
   
   
   /** 
    * Get ownship's heading over ground, in true.
    *
    * @return Ownship's heading, in degrees True, range: {0.0, 360.0]
    */
   public double getSimulatedCOGValue () {
      if (ownshipUpdate != null) {
         return ownshipUpdate.headingOverGround;
      }
      else {
         return 0.0;
      }
   }
   
   /** 
    * Get ownship's heading over ground, in magnetic.
    *
    * @return Ownship's heading, in degrees magnetic, range: {0.0, 360.0]
    */
   public String getSimulatedHeadingMagneticOverGround () {
      if (ownshipUpdate != null) {
         double heading = ownshipUpdate.headingOverGround + 
            DataFactoryDefaults.MAGNETIC_VARIATION_SIMULATED;
         if (heading > 360.0) {
            heading -= 360.0;
         }
         return headingForm.format(heading);
      }
      else {
         return "???.?";
      }
   }
   
   /** 
    * Get ownship's rate of turn.
    *
    * @return Ownship's rate of turn, in degrees per minute.
    */
   public String getSimulatedRateOfTurn () {
      if (ownshipUpdate != null) {
         return speedForm.format(ownshipUpdate.headingVelocity);
      }
      else {
         return "+?.?";
      }
   }
   
   /**
    * Get ownship's speed, in knots.
    *
    * @return Ownship's speed, in knots.
    */
   public String getSimulatedSpeedWaterInKnots () {
      if (ownshipUpdate != null) {
         return speedForm.format(ownshipUpdate.speedActual);
      }
      else {
         return "??.?";
      }
   }
   
   /**
    * Get ownship's speed, in knots.
    *
    * @return Ownship's speed, in knots.
    */
   public double getSimulatedSTWValue () {
      if (ownshipUpdate != null) {
         return ownshipUpdate.speedActual;
      }
      else {
         return 0.0;
      }
   }
   
   /**
    * Get ownship's speed, in KPH.
    *
    * @return Ownship's heading, in KPH.
    */
   public String getSimulatedSpeedWaterInKPH () {
      if (ownshipUpdate != null) {
         return speedForm.format(ownshipUpdate.speedActual * DataFactory.NM_TO_KM_CONVERSION);
      }
      else {
         return "??.?";
      }
   }
   
   
   /**
    * Get ownship's speed over ground, in knots.
    *
    * @return Ownship's speed over ground, in knots.
    */
   public String getSimulatedSpeedOverGroundInKnots () {
      if (ownshipUpdate != null) {
         return speedForm.format(ownshipUpdate.speedOverGround);
      }
      else {
         return "??.?";
      }
   }
   
   /**
    * Get ownship's speed over ground, in knots.
    *
    * @return Ownship's speed over ground, in knots.
    */
   public double getSimulatedSOGKnotsValue () {
      if (ownshipUpdate != null) {
         return ownshipUpdate.speedOverGround;
      }
      else {
         return 0.0;
      }
   }
   
   /**
    * Get ownship's speed over ground, in KPH.
    *
    * @return Ownship's speed over ground, in KPH.
    */
   public String getSimulatedSpeedOverGroundInInKPH () {
      if (ownshipUpdate != null) {
         return speedForm.format(ownshipUpdate.speedOverGround * DataFactory.NM_TO_KM_CONVERSION);
      }
      else {
         return "??.?";
      }
   }
   
   
   /** 
    * Get ownship's simulated set, in true.
    *
    * @return Ownship's simulated set, in degrees True, range: {0.0, 360.0]
    */
   public String getSimulatedSetTrue () {
      if (ownshipUpdate != null) {
         return headingForm.format(ownshipUpdate.set);
      }
      else {
         return "???.?";
      }
   }
   
   /** 
    * Get ownship's simulated set, in magnetic.
    *
    * @return Ownship's simulated set, in degrees True, range: {0.0, 360.0]
    */
   public String getSimulatedSetMagnetic () {
      if (ownshipUpdate != null) {
         double set = ownshipUpdate.set + 
            DataFactoryDefaults.MAGNETIC_VARIATION_SIMULATED;
         if (set > 360.0) {
            set -= 360.0;
         }
         return headingForm.format(set);
      }
      else {
         return "???.?";
      }
   }
   
   /**
    * Get ownship's simulated drift, in knots.
    *
    * @return Ownship's simulated drift, in knots.
    */
   public String getSimulatedDriftInKnots () {
      if (ownshipUpdate != null) {
         return speedForm.format(ownshipUpdate.drift);
      }
      else {
         return "??.?";
      }
   }
}
/* 
 * Version history
 * 1.3.0.1 - corrected simulated month 'off-by-one' error
 * 1.3.0.0 - added getter/setter methods for simulated COG/SOG, set and drift
 */
