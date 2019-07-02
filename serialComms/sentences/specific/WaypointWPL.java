/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: WaypointWPL.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import serialComms.*;
import serialComms.sentences.*;
/**
 * WPL - Waypoint location. 
 *<pre>
 * Sentence:
 * $--WPL,llll.ll,a,yyyyy.yy,a,c--c*hh<CR><LF>
 *           |    |    |     |   |- Waypoint identfier   
 *           |    |    |-----|- Longitude, 'E' or 'W'
 *           |----|- Latitude, 'N' or 'S'
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class WaypointWPL extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 82;

   float latitude;     // Latitude
   char latDirection;  // 'N' or 'S'
   float longitude;    // Longitude
   char longDirection; // 'E' or 'W'
   char [] identifier;
   final static int [] validTalkerID = {
      SentenceTypes.TALKER_EC,
      SentenceTypes.TALKER_EI,
      SentenceTypes.TALKER_LC,
      SentenceTypes.TALKER_GN,
      SentenceTypes.TALKER_GP,
      SentenceTypes.TALKER_IN,
      SentenceTypes.TALKER_SN
   };
   final static int talkerCount = validTalkerID.length;
      
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
      return SentenceTypes.FORMATTER_WPL;
   }
   
   /**
    * Validates an acceptable talker ID for this sentence formatter.
    * 
    * @param id Talker ID to validate.
    * @return True if valide talker ID.
    */
   public boolean isSentenceIDValid (int id) {
      for (int i = 0; i < talkerCount; i++) {
         if (id == validTalkerID[i]) {
            return true;
         }
      }
      return false;
   }
      
   /**
    * Get sentence based upon simulator data.  Manual data is inserted where
    * simulated data is unavailable.
    *
    * @return Valid simulation-based sentence.
    */
   public String getSimulatorSentence () {
      return getRandomSentence(); // Temporary until processing is completed.
   }
   
   /**
    * Get sentence based upon manual data.
    *
    * @return Valid simulation-based sentence.
    */
   public String getManualSentence () {
      return getRandomSentence(); // Temporary until processing is completed.
   }
   
   /**
    * Get test sentence data. Note, the last digit of select values within
    * the sentence is random.
    *
    * @return Valid test sentence.
    */
   public String getRandomSentence() {
      int testVal = SentenceTools.getRandomDigit ();
      String testString = new String (
         "INWPL" +
         ",3745.500" + testVal + ",N" +
         ",12241.600" + testVal + ",W" +
         ",Buoy SF");
      return new String('$' + testString + '*' + SentenceTools.getChecksum(testString)
         + SentenceTools.CR + SentenceTools.LF);
   }
      
   /**
    * Process a received sentence.
    *
    * @param receivedSentence A ReceivedSentence.
    */
   public void processReceivedSentence (ReceivedSentence receivedSentence) {
      
   }
}
/*
 * Revision History:
 *
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */