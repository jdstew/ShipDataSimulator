/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: RoutesRTE.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import serialComms.*;
import serialComms.sentences.*;
/**
 * RTE - Routes. 
 *<pre>
 * Sentence:
 * $--RTE,x,x,a,c--c,c--c,...,c--c*hh<CR><LF>
 *        | | |  |     |    |    |- Waypoint identifier 'n' (see note 1)
 *        | | |  |     |    |- Additional waypoint identifiers
 *        | | |  |     |- Waypoint identifier
 *        | | |  |- Route identifier
 *        | | |- Message mode (see note 2)
 *        | |- Message number (see note 3)
 *        |- Total number of messages being transmitted (see note 3)
 *
 *Notes:
 *(1) A variable number of waypoint identifiers, up to 'n', may be included
 *    within the limits of allowed sentence length.  As there is no specified
 *    number of waypoints, null fields are not required for waypoint identifier
 *    fields.
 *(2) Modes:
 *    'c' - complete route, all waypoints
 *    'w' - working route, the first waypoint is the starting location.
 *(3) A single route may require the transmission of multiple messages all 
 *    containing identical field formats. The first field specifies the total 
 *    number of messages, minimum value = 1. The second field identifies the 
 *    order of this message (message number), minimum value = 1. For efficiency,
 *    it is recommended that null fields be used in the additional sentences 
 *    when the data is unchanged from the first sentence.
 *</pre>
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class RoutesRTE extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 82;

   String [] waypointIDs; // Waypoint identifiers
   String routeID; // Route identifier
   char msgMode; // Message mode (see note 2)
   int msgNumber; // Message number (see note 3)
   int msgCount; // Total number of messages being transmitted (see note 3)
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
      return SentenceTypes.FORMATTER_RTE;
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
      //$--RTE,x,x,a,c--c,c--c,...,c--c*hh<CR><LF>
      String testString = new String (
         "INRTE,1,1,c" +
         ",R" + testVal +
         ",W1,W2,W3,W4,W5,W6");
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