/*
 * DefaultMessage.java
 *
 * Created on July 3, 2005, 9:23 AM
 */
package networkComms.messages.specific;

import networkComms.*;
import networkComms.messages.*;
import java.nio.*;
import java.util.*;
import java.text.*;
/**
 * Computed Position Message
 * Used by Message Type: MT_CP
 * Sent by: nm
 * Sent to: hmi, tt
 * This message sends the computed position.
 *<pre>
 * [byte index (approximate)]
 * [  0]int type                   Message Type ID = 200
 * [  4]int numOfBytes             number of bytes in message
 *
 * [  8]double latitude            Ownship Computed Position Latitude based on sensor offset
 * [ 16]double longitude           Ownship Computed Position Longitude based on sensor offset
 * [ 24]double conningLatitude     Conning Position Latitude
 * [ 32]double conningLongitude    Conning Position Longitude
 * [ 40]double fixAouLatitude
 * [ 48]double fixAouLongitude
 * [ 56]double fixAouRadius
 * [ 64]double dInitialRadius      initial value to set fix expansion to
 * [ 72]double dExpansionRate      rate at which fix expansion grows
 * [ 80]double semiMajorValue      Semi Major Value
 * [ 88]double semiMinorValue      Semi Minor Value
 * [ 96]double brgOsCpToFixLatLon  Bearing of Ownship Computed Position To Fix Expansion Lat / Lon
 * [104]double rngOsCpToFixLatLon  Range of Ownship Computed Position To Fix Expansion Lat / Lon
 *
 * [112]float trueHeading          Gyro Compass Heading including correction
 * [116]float course               Ownship Course
 * [120]float speed                Ownship Speed
 * [124]float posUncertainty       Position Uncertainty
 * [128]float magneticVariation    Magnetic Variation
 * [132]float deltamagVar          Annual Change Chanage in Mag Var
 *
 * [136]int time                   Ownship Computed Position Time
 * [140]int crsSpdReference        Course and Speed reference
 * [144]int iExpansionState        whether fix expansion is on or off
 * [148]int posOsCpSrc             Source Used in Computed Position (GPS RTS .. )
 *
 * [152]byte (C~char) velocitySourceString     [MAX_FIELD_SIZE] = 127
 * [279]byte (C~char) padChar[7]               64 bit alignment
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class ComputedPosition extends AbstractMessage {
   final static int ESTIMATED_MESSAGE_LENGTH = 286; // in bytes
   ByteBuffer bufferOut;
   ByteBuffer bufferIn;
   StringBuffer decodedText;
   
   DecimalFormat twoDigitForm = new DecimalFormat ("00");
   DecimalFormat threeDigitForm = new DecimalFormat ("000");
   DecimalFormat minuteForm = new DecimalFormat ("00.0000");
   DecimalFormat headingForm = new DecimalFormat ("000.0");
   DecimalFormat speedForm = new DecimalFormat ("#0.0");
   
   public ComputedPosition () {
      bufferOut = ByteBuffer.allocate (ESTIMATED_MESSAGE_LENGTH + 16);
      decodedText = new StringBuffer (512);
   }
   
   public int getEstimatedLength () {
      return ESTIMATED_MESSAGE_LENGTH;
   }
   
   public int getMessageID () {
      return 0; // MessageTypes.?;
   }
   
   public byte[] getSimulatorMessage () {
      bufferOut.clear ();
      bufferOut.putInt (200); // type
      bufferOut.putInt (ESTIMATED_MESSAGE_LENGTH); // numOfBytes
      bufferOut.putDouble (dataFactory.getSimulatedLatitudeValue ()); // latitude
      bufferOut.putDouble (dataFactory.getSimulatedLongitudeValue ()); // longitude
      bufferOut.putDouble (0.0); // conningLatitude
      bufferOut.putDouble (0.0); // conningLongitude
      bufferOut.putDouble (0.0); // fixAouLatitude
      bufferOut.putDouble (0.0); // fixAouLongitude
      bufferOut.putDouble (0.0); // fixAouRadius
      bufferOut.putDouble (0.0); // dInitialRadius
      bufferOut.putDouble (0.0); // dExpansionRate
      bufferOut.putDouble (0.0); // semiMajorValue
      bufferOut.putDouble (0.0); // semiMinorValue
      bufferOut.putDouble (0.0); // brgOsCpToFixLatLon
      bufferOut.putDouble (0.0); // rngOsCpToFixLatLon
      bufferOut.putFloat ((float)dataFactory.getSimulatedCTWValue ()); // trueHeading
      bufferOut.putFloat ((float)dataFactory.getSimulatedCOGValue ()); // course
      bufferOut.putFloat ((float)dataFactory.getSimulatedSOGKnotsValue ()); // speed
      bufferOut.putFloat (0.0f); // posUncertainty
      bufferOut.putFloat (0.0f); // magneticVariation
      bufferOut.putFloat (0.0f); // deltamagVar
      
      GregorianCalendar timeObj = new GregorianCalendar (Locale.US);
      timeObj.setTime (dataFactory.getSimulatedTimeDateValue ());
      int time = timeObj.get (Calendar.HOUR_OF_DAY) * 3600 +
      timeObj.get (Calendar.MINUTE) * 60 +
      timeObj.get (Calendar.SECOND);
      
      bufferOut.putInt (time); // time
      bufferOut.putInt (0); // crsSpdReference
      bufferOut.putInt (0); // iExpansionState
      bufferOut.putInt (1); // posOsCpSrc
      
      // velocitySourceString
      for (int i = 0; i < 127; i++) {
         bufferOut.put ((byte)0);
      }
      
      // padChar
      for (int i = 0; i < 7; i++) {
         bufferOut.put ((byte)0);
      }
      
      return bufferOut.array ();
   }
   
   public byte[] getManualMessage () {
      bufferOut.clear ();
      bufferOut.putInt (200); // type
      bufferOut.putInt (ESTIMATED_MESSAGE_LENGTH); // numOfBytes
      bufferOut.putDouble (MessageTools.parseLatitude (
      dataFactory.getLatitude ().toCharArray ())); // latitude
      bufferOut.putDouble (MessageTools.parseLongitude (
      dataFactory.getLongitude ().toCharArray ())); // longitude
      bufferOut.putDouble (0.0); // conningLatitude
      bufferOut.putDouble (0.0); // conningLongitude
      bufferOut.putDouble (0.0); // fixAouLatitude
      bufferOut.putDouble (0.0); // fixAouLongitude
      bufferOut.putDouble (0.0); // fixAouRadius
      bufferOut.putDouble (0.0); // dInitialRadius
      bufferOut.putDouble (0.0); // dExpansionRate
      bufferOut.putDouble (0.0); // semiMajorValue
      bufferOut.putDouble (0.0); // semiMinorValue
      bufferOut.putDouble (0.0); // brgOsCpToFixLatLon
      bufferOut.putDouble (0.0); // rngOsCpToFixLatLon
      bufferOut.putFloat ((float)MessageTools.parseBearing (
      dataFactory.getHeadingTrueWater ().toCharArray ())); // trueHeading
      bufferOut.putFloat ((float)MessageTools.parseBearing (
      dataFactory.getHeadingTrueGround ().toCharArray ())); // course
      bufferOut.putFloat ((float)MessageTools.parseNumber (
      dataFactory.getSpeedGroundInKnots ().toCharArray ())); // speed
      bufferOut.putFloat (0.0f); // posUncertainty
      bufferOut.putFloat (0.0f); // magneticVariation
      bufferOut.putFloat (0.0f); // deltamagVar
      
      long parsedTime = MessageTools.parseUTC (dataFactory.getTimeUTC ().toCharArray ());
      if (parsedTime < 0) {
         parsedTime = 1000;
      }
      
      bufferOut.putInt ( (int)(parsedTime / 1000) ); // time
      bufferOut.putInt (0); // crsSpdReference
      bufferOut.putInt (0); // iExpansionState
      bufferOut.putInt (1); // posOsCpSrc
      
      // velocitySourceString
      for (int i = 0; i < 127; i++) {
         bufferOut.put ((byte)0);
      }
      
      // padChar
      for (int i = 0; i < 7; i++) {
         bufferOut.put ((byte)0);
      }
      
      return bufferOut.array ();
   }
   
   public byte[] getRandomMessage () {
      bufferOut.clear ();
      bufferOut.putInt (200); // type
      bufferOut.putInt (ESTIMATED_MESSAGE_LENGTH); // numOfBytes
      bufferOut.putDouble (45.0); // latitude
      bufferOut.putDouble (-110.0); // longitude
      bufferOut.putDouble (0.0); // conningLatitude
      bufferOut.putDouble (0.0); // conningLongitude
      bufferOut.putDouble (0.0); // fixAouLatitude
      bufferOut.putDouble (0.0); // fixAouLongitude
      bufferOut.putDouble (0.0); // fixAouRadius
      bufferOut.putDouble (0.0); // dInitialRadius
      bufferOut.putDouble (0.0); // dExpansionRate
      bufferOut.putDouble (0.0); // semiMajorValue
      bufferOut.putDouble (0.0); // semiMinorValue
      bufferOut.putDouble (0.0); // brgOsCpToFixLatLon
      bufferOut.putDouble (0.0); // rngOsCpToFixLatLon
      bufferOut.putFloat (129.0f); // trueHeading
      bufferOut.putFloat (132.0f); // course
      bufferOut.putFloat (12.0f); // speed
      bufferOut.putFloat (0.0f); // posUncertainty
      bufferOut.putFloat (0.0f); // magneticVariation
      bufferOut.putFloat (0.0f); // deltamagVar
      
      GregorianCalendar timeObj = new GregorianCalendar (Locale.US);
      timeObj.setTimeInMillis (System.currentTimeMillis ());
      int time = timeObj.get (Calendar.HOUR_OF_DAY) * 3600 +
      timeObj.get (Calendar.MINUTE) * 60 +
      timeObj.get (Calendar.SECOND);
      
      bufferOut.putInt (time); // time
      bufferOut.putInt (0); // crsSpdReference
      bufferOut.putInt (0); // iExpansionState
      bufferOut.putInt (1); // posOsCpSrc
      
      // velocitySourceString
      for (int i = 0; i < 127; i++) {
         bufferOut.put ((byte)0);
      }
      
      // padChar
      for (int i = 0; i < 7; i++) {
         bufferOut.put ((byte)0);
      }
      return bufferOut.array ();
   }
   
   public void processReceivedMessage (ReceivedMessage receivedMessage) {
      // int intVal;
      // float floatVal;
      // double doubleVal;
      decodedText.setLength (0);
      
      bufferIn = receivedMessage.getByteBuffer ();
      
      // assumes that message type was already read.
      decodedText.append ("\nMessage type: Computed Position");
      
      // assumes that message length was already read.
      decodedText.append ("\nNumber of bytes: " + receivedMessage.getMessageSize () +
      " (" + bufferIn.limit () + " actual)"); // numOfBytes
      
      if (bufferIn.limit () < ESTIMATED_MESSAGE_LENGTH) {
         decodedText.append ("\n");
         bufferIn.rewind ();
         if (bufferIn.limit () > 0) {
            for (int i = 0; i < bufferIn.limit (); i++) {
               int thisByte = (int)bufferIn.get () & 0x00FF;
               if (thisByte < 16) {
                  decodedText.append ('0');
               }
               decodedText.append (Integer.toHexString (thisByte) + ".");
            }
         }
         receivedMessage.addDecodedText (decodedText.toString ());
         return;
      }
      
      double latitude = bufferIn.getDouble (); // latitude
      
      decodedText.append ("\nLatitude:   " +
      twoDigitForm.format ((int)Math.abs (latitude)) + '-' +
      minuteForm.format (Math.abs (latitude % 1.0) * 60.0));
      
      if (latitude >= 0) {
         decodedText.append ('N');
      }
      else {
         decodedText.append ('S');
      }
      
      
      double longitude = bufferIn.getDouble (); // longitude
      
      decodedText.append ("\nLongitude: " +
      threeDigitForm.format ((int)Math.abs (longitude)) + '-' +
      minuteForm.format (Math.abs (longitude % 1.0) * 60.0));
      
      if (longitude >= 0) {
         decodedText.append ('E');
      }
      else {
         decodedText.append ('W');
      }
      
      decodedText.append ("\nConning latitude:  " + bufferIn.getDouble ()); // conningLatitude
      decodedText.append ("\nConning longitude: " + bufferIn.getDouble ()); // conningLongitude
      decodedText.append ("\nFix AOU latitude:  " + bufferIn.getDouble ()); // fixAouLatitude
      decodedText.append ("\nFix AOU longitude: " + bufferIn.getDouble ()); // fixAouLongitude
      decodedText.append ("\nFix AOU radius:    " + bufferIn.getDouble ()); // fixAouRadius
      decodedText.append ("\nInitial radius: " + bufferIn.getDouble ()); // dInitialRadius
      decodedText.append ("\nExpansion rate: " + bufferIn.getDouble ()); // dExpansionRate
      decodedText.append ("\nSemi-axis major value: " + bufferIn.getDouble ()); // semiMajorValue
      decodedText.append ("\nSemi-axis minor value: " + bufferIn.getDouble ()); // semiMinorValue
      decodedText.append ("\nBearing to fix expansion: " + bufferIn.getDouble ()); // brgOsCpToFixLatLon
      decodedText.append ("\nRange to fix expansion:   " + bufferIn.getDouble ()); // rngOsCpToFixLatLon
      
      float ctwValue = bufferIn.getFloat ();
      decodedText.append ("\nTrue heading: " + ctwValue); // trueHeading
      
      float cogValue = bufferIn.getFloat ();
      decodedText.append ("\nCourse over ground: " + cogValue); // course
      
      float sogValue = bufferIn.getFloat ();
      decodedText.append ("\nSpeed over ground:  " + sogValue); // speed
      decodedText.append ("\nPosition uncertainty: " + bufferIn.getFloat ()); // posUncertainty
      decodedText.append ("\nMagnetic variation: " + bufferIn.getFloat ()); // magneticVariation
      decodedText.append ("\nVariation rate: " + bufferIn.getFloat ()); // deltamagVar
      
      int timeRemainder = bufferIn.getInt (); // time
      int hours = timeRemainder / 3600;
      timeRemainder = timeRemainder % 3600;
      int minutes = timeRemainder / 60;
      int seconds = timeRemainder % 60;
      
      decodedText.append ("\nTime " +
      twoDigitForm.format (hours) + ":" +
      twoDigitForm.format (minutes) + ":" +
      twoDigitForm.format (seconds));
      decodedText.append ("\nCourse & speed reference: " + bufferIn.getInt ()); // crsSpdReference
      decodedText.append ("\nFix expansion state: " + bufferIn.getInt ()); // iExpansionState
      decodedText.append ("\nPosition source: " + bufferIn.getInt ()); // posOsCpSrc
      
      // velocitySourceString
      decodedText.append ("\n");
      for (int i = 0; i < 127; i++) {
         decodedText.append ((char)bufferIn.get ());
      }
      
      receivedMessage.addDecodedText (decodedText.toString ());
      
      dashboardChannel.setPlotPosition (latitude, longitude);
      dashboardChannel.setCourseThroughWater (ctwValue);
      dashboardChannel.setCourseOverGround (cogValue);
      dashboardChannel.setSpeedOverGround (sogValue);
   }
   
   /**
    * Starts the application.
    *
    * @param arg read but not used.
    */
   public static void main (String [] arg){
      ComputedPosition cp = new ComputedPosition ();
      ReceivedMessage rm = new ReceivedMessage ();
      byte [] testMessage = cp.getRandomMessage ();
      rm.setMessage (testMessage, testMessage.length);
      cp.processReceivedMessage (rm);
      System.out.println (rm.getDecodedText ());
   }
}
