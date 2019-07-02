/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: TransmitSentenceData.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.gui;

/**
 * An object of this class represents a transmitting sentence.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class TransmitSentenceData {
   /** Sentence formatter of the sentence being transmitted. */
   public int sentenceFormatter;
   /** Talker identification of the sentence being transmitted. */
   public int talkerID;
   /** NMEA version of the sentence being transmitted. */
   public int sentenceVersion;
   
   /** Interval, in milliseconds, between sentence transmissions. */
   public long transmitPeriod;
   
   /** Source of data used in the tranmitted sentences. */
   public int dataSource;
   
   /**
    * Used to obtain a string representation of the object's values.
    *
    * @return Text printout of this object's values.
    */   
   public String toString () {
      return "Fromatter = " + sentenceFormatter +
             "\n\rTalkerID = " + transmitPeriod + 
             "\n\rPeriod = " + talkerID + 
             "\n\rSource = " + dataSource + 
             "\n\rVersion = " + sentenceVersion;
   }
   
}
