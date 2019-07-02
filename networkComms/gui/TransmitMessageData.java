/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: TransmitSentenceData.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.gui;

/**
 * An object of this class represents a transmitting message.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class TransmitMessageData {
   /** Sentence formatter of the message being transmitted. */
   public int messageID;
   
   /** Interval, in milliseconds, between message transmissions. */
   public long transmitPeriod;
   
   /** Source of data used in the tranmitted messages. */
   public int dataSource;
   
   /**
    * Used to obtain a string representation of the object's values.
    *
    * @return Text printout of this object's values.
    */   
   public String toString () {
      return "Formatter = " + messageID +
             "\n\rTalkerID = " + transmitPeriod + 
             "\n\rSource = " + dataSource;
   }
   
}
