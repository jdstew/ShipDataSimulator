/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 *
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: ComplexNMEA0182.java
 * Created: 2005-02-02, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
/**
 * NMEA 0182 - Complex LORAN-C autopilot interface.
 *
 *<pre>
 * Sentence (by bytes):
 *
 *          1         2         3
 * 1234567890123456789012345678901234567
 * $MPaxx.xaaxxxxxDxx.xx"axxxDxx.xx"abne
 * $MPN00.2LT25012D34.56'N123D45.67"WF03
 *
 * Byte        Data
 *   1          $
 *   2          M                  | device
 *   3          P                  | address
 *
 *   4          K = kilometres     | cross track
 *              N = nautical miles | error
 *              U = microseconds   | units
 *
 *   5 - 8      0 - 9 or .          cross track error value
 *   9          L or R              cross track error position
 *
 *  10          T or M              True or Magnetic bearing
 *  11 - 13     0 - 9               bearing to next waypoint
 *
 *  14 - 23     12D34'56"N or       present latitude
 *              12D34.56'N
 *  24 - 34     123D45'56"W or      present longitude
 *              123D45.67"W
 *
 *  35          non-ASCII status byte (Note: STATUS_MASK USED)
 *              bit 0 = 1 for manual cycle lock
 *                  1 = 1     low SNR
 *                  2 = 1     cycle jump
 *                  3 = 1     blink
 *                  4 = 1     arrival alarm
 *                  5 = 1     discontinuity of TDs
 *                  6 = 1 always
 *   36         "NUL" character (hex 80)(reserved status byte)
 *   37         "ETX" character (hex 83)
 *   Any unavailable data is filled with "NUL" bytes.
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.1.0.0, 2005-02-02
 */
public class ComplexNMEA0182 extends AbstractSentence {
   public final static char NULL = 0x0000;
   public final static char ETX = 0x0003;
   
   public final static int STATUS_MASK = 0x00EE;
   
   final static int ESTIMATED_SENTENCE_LENGTH = 37;
   
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
      return SentenceTypes.FORMATTER_NMEA0182;
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
    * Get sentence based upon simulator data.  Manual data is inserted where
    * simulated data is unavailable.
    *
    * @return Valid simulation-based sentence.
    */
   public String getSimulatorSentence () {
      return getManualSentence ();
   }
   
   /**
    * Get sentence based upon manual data.
    *
    * @return Valid simulation-based sentence.
    */
   public String getManualSentence () {
      return getRandomSentence ();
   }
   
   /**
    * Get test sentence data. Note, the last digit of select values within
    * the sentence is random.
    *
    * @return Valid test sentence.
    */
   public String getRandomSentence () {
      String sentence = "$MPN00.2LT25012D34.56\"N123D45.67\"W" + 
         ((char) (random.nextInt (0xFF) & STATUS_MASK)) + NULL + ETX;
      return convertToComplex (sentence);
   }
   
   /**
    * Converts an ASCII sentence into a complex NMEA 0182 sentence.
    *
    * @return Complex NMEA 0182 sentence.
    */
   String convertToComplex (String inputString) {
      CharacterBuffer characterBuffer = new CharacterBuffer ();
      for (int i = 0; i < inputString.length (); i++) {
         characterBuffer.append ((char) ((int) inputString.charAt (i) | 0x0080));
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
      
   }
}