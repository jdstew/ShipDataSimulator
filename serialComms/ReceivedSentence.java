/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentRcvdEventObject.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms;

import java.util.*;
import serialComms.sentences.analysis.*;
/**
 * An object of this class is created by a SentenceTranceiver and passed
 * along to the SentenceManager for processing.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-26
 */
public class ReceivedSentence {
   
   /** Sentence 'in-use' status. */
   private boolean currentlyInUse;
   
   
   /** Sentence talker id from SentenceTypes class. */
   public int talkerID;
   
   /** Source talker id of a query sentence. */
   public int queryTalkerID;
   
   /** Sentence formatter. */
   public int formatter;
   
   
   /** The checksum calculated from the received sentence. */
   public int calculatedChecksum;
   
   /** The checksum value provided by the received sentence. */
   public int receivedChecksum;
   
   
   /** The time when the sentence was received (last character). */
   public long timeOfReciept;
   
   /** The time from first to last character received (in milliseconds). */
   public long timeToReceive;

   
   /** The number of fields provided by this sentence. */
   public int fieldCount;
   
   
   /** The sentence received (annotated with non-printing characters). */
   public String sentenceReceived;
   
   /** An ArrayList of Strings containing the sentence field data. */
   public ArrayList<String> sentenceFields;

   
   /** An ErrorLog of errors found in receipt of the sentence (non-formatter specific). */
   public SentenceErrorLog errorLog;

   /** 
    * Instantiates a ReceivedSentence object.
    */
   public ReceivedSentence () {
      sentenceFields = new ArrayList<String>();
      errorLog = new SentenceErrorLog();
      setInUse(false);
   }
   
   /**
    * Gets the 'in-use' status of this object.
    *
    * @return 'True' for in-use.
    */
   public boolean getInUse () {
      return currentlyInUse;
   }
   
   /**
    * Sets the 'in-use' status of this object.
    *
    * @param inUse 'True' for in-use, 'false' resets object an makes it available
    * for use.
    */
   public void setInUse (boolean inUse) {
      if (inUse) {
         currentlyInUse = true;
      }
      else {
         currentlyInUse = false;
         talkerID = 0;
         queryTalkerID = 0;
         formatter = 0;
         calculatedChecksum = 0;
         receivedChecksum = 0;
         timeOfReciept = 0;
         timeToReceive = 0;
         fieldCount = 0;
         sentenceReceived = null;
         sentenceFields.clear ();
         errorLog.clear ();
      }
   }
}
