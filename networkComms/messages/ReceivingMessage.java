/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: ReceivingSentence.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.messages;

import java.text.*;
import dashboard.*;
import networkComms.*;
import networkComms.messages.analysis.*;
/**
 * An object of this class represents a specific message type being received
 * on a serial channel.
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-01
 */
public class ReceivingMessage {
   public static final long MESSAGE_MAXIMUM_LATENCY = 3000;

   int messageID;
   long receivedTotal;
   long timeSincePrevious;
   ReceiveFrequency messageRcvFreq;
   AbstractMessage thisMessage;
   
   DecimalFormat freqForm = new DecimalFormat("0.00");
   
   /**
    * Create a ReceivingSentence object.
    *
    * @param formatter The formatter reference from SentenceTypes.
    */
   public ReceivingMessage (int msgID, DashboardChannel dbChnl) {
      messageRcvFreq = new ReceiveFrequency ();
      messageID = msgID;
      thisMessage = MessageTypes.getMessageObject (messageID);
      
      thisMessage.setDashboardChannel (dbChnl);
   }
   
   /**
    * Create a ReceivingSentence object.
    *
    * @param receivedSentence The received message object.
    */   
   public void receiveMessage (ReceivedMessage receivedMessage) {
      receivedTotal++;
      messageRcvFreq.addSample (receivedMessage.getTimeOfReceipt ());
      
      if (messageRcvFreq.getLatency () > MESSAGE_MAXIMUM_LATENCY) {
         receivedMessage.addProcessingText ("The " + getMessageName () +
            " message has stopped or become slow.");
      }
      
      thisMessage.processReceivedMessage (receivedMessage);
   }
   
   /**
    * Get the specific message formatter.
    *
    * @return Text description of this message type.
    */   
   public String getMessageName () {
      return MessageTypes.getMessageName (messageID);
   }
     
   /**
    * Get the total number of messages received for this specific message type.
    *
    * @return Total number of messages received of this type.
    */   
   public String getReceiveTotal () {
      return Long.toString (receivedTotal);
   }
   
   /**
    * Get the received message frequency.
    *
    * @return Frequency, in hertz.
    */   
   public String getReceiveFrequency () {
      return freqForm.format (messageRcvFreq.getFrequency ());
   }
   
   /**
    * Get the latency between received messages.
    *
    * @return Latency, in seconds.
    */   
   public String getReceiveLatency () {
      return freqForm.format ((messageRcvFreq.getLatency () / 1000.0));
   }
}
/*
 * Revision history:
 *
 * 1.0.0.1  Changed receiveSentence() to receive ReceivedSentence object.
 */
