/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SimpleNMEA0180.java
 * Created: 2005-02-02, 12:01:01
 */
package serialComms.sentences.specific;

import java.util.*;
import serialComms.*;
import serialComms.sentences.*;
/**
 * NMEA 0180 - Simple autopilot interface.
 * Note: this 'sentence' is actually one byte long, has no start or end
 * character.
 *<pre>
 * Sentence (by bits):
 *  7 6 5 4 3 2 1 0 
 *  | | |---------|- Cross track error (XTE) (see note 1)
 *  | |- Validity bit, '1' is valid
 *  |- Always '0'
 *
 * Notes:
 * (1) Bits form an unsigned 6-bit number representing the 'scale' in
 *     0.01NM increments of left or right of track. The following values apply:
 *
 *     0.32NM (00 0000) - maximum right of track
 *     0.00NM (10 0000) - on track
 *     0.31NM (11 1111) - maximum left of track
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.1.0.0, 2005-02-02
 */
public class SimpleNMEA0180 extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 1;
   
   Random random = new Random(System.currentTimeMillis ());
      
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
      return SentenceTypes.FORMATTER_NMEA0180;
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
      return String.valueOf ((char) (random.nextInt (0xFF) & 0x7F));
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
   
   public static void main (String [] arg){  
      SimpleNMEA0180 simple = new SimpleNMEA0180();
      for (int i = 0; i < 100; i++) {
         System.out.println (simple.getRandomSentence ());
      }
   }
}