/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: TargetTTM.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import serialComms.*;
import serialComms.sentences.*;
/**
 * TTM - Tracked target message. 
 *<pre>
 * Sentence:
 * $--TTM,xx,x.x,x.x,a,x.x,x.x,a,x.x,x.x,a,c--c,a,a,hhmmss.ss,a*hh<CR><LF>
 *         |  |   |  |  |   |  |  |   |  |   |  | |     |     |- Acquisition type (see note 3)
 *         |  |   |  |  |   |  |  |   |  |   |  | |     |- Time of data (UTC)
 *         |  |   |  |  |   |  |  |   |  |   |  | |- Reference target (see note 2)
 *         |  |   |  |  |   |  |  |   |  |   |  |- Target status (see note 1)
 *         |  |   |  |  |   |  |  |   |  |   |- Target name
 *         |  |   |  |  |   |  |  |   |  |- Speed/distance units, 'K'/'N'/'S'
 *         |  |   |  |  |   |  |  |   |- Time to CPA, min, '-' for increasing
 *         |  |   |  |  |   |  |  |- Distance of CPA
 *         |  |   |  |  |   |--|- Target course, 'T'/'R'
 *         |  |   |  |  |- Target speed
 *         |  |   |--|- Bearing from ownship, 'T'/'R'
 *         |  |- Target distance from ownship
 *         |- Target number, 00-99
 *</pre>
 *<pre>
 *Notes:
 *(1) Target status:
 *    L - Lost, tracked target has been lost
 *    Q - Query, target in the process of acquisition
 *    T - Tracking
 *(2) Reference target - set to 'R' if target is a reference used to determine
 *ownship position or velocity, null otherwise.
 *(3) Type of acquisition:
 *    A - Automatic
 *    M - Manual
 *    R - Reported
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class TargetTTM extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 82;

   char acquisitionType; // Acquisition type (see note 3)
   long reportTime; // Time of data (UTC)
   char referenceTarget; // Reference target (see note 2)
   char targetStatus; // Target status (see note 1)
   String targetName; // Target name
   char speedDistUnits; // Speed/distance units, 'K'/'N'/'S'
   long CPATime; // Time to CPA, min, '-' for increasing
   float CPADistance; // Distance of CPA
   float targetCSE; // Target course
   char targetRef; // Target 'T'/'R'
   float targetSpeed; // Target speed
   float bearing; // Bearing from ownship
   char bearingRef; // Bearing 'T'/'R'
   float distance; // Target distance from ownship
   int targetNumber; // Target number, 00-99
   final static int [] validTalkerID = {
      SentenceTypes.TALKER_EI,
      SentenceTypes.TALKER_IN,
      SentenceTypes.TALKER_RA
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
      return SentenceTypes.FORMATTER_TTM;
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
   public String getRandomSentence () {
      int testVal = SentenceTools.getRandomDigit ();
      String testString = new String (
         "RATTM,01,5.5,030,T,8.6,180,T,0.6,7.4,N,ST9494,T,,131415,1" + testVal + 
         ",M");
      return new String('$' +  testString + '*' + SentenceTools.getChecksum(testString)
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