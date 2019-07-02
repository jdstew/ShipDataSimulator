/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentRcvdEventObject.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms;

import java.nio.*;
//import networkComms.messages.analysis.*;
/**
 * An object of this class is created by a SentenceTranceiver and passed
 * along to the SentenceManager for processing.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-26
 */
public class ReceivedMessage {
   public static final int DECODED_MESSAGE_TEXT_LENGTH = 1024;
   public static final int PROCESSING_TEXT_LEGTH = 1024;
   public static final int DEFAULT_BUFFER_SIZE = 8192;
   
   /** Sentence 'in-use' status. */
   private boolean inUse;
   
   /** Sentence message id from MessageTypes class. */
   int messageID;
   
   int messageSize;
   
   /** The time when the sentence was received (last character). */
   long timeOfReciept;
   
   StringBuffer decodedMessage;
   StringBuffer processingText;
   ByteBuffer byteBuffer;
   
   /** 
    * Instantiates a ReceivedSentence object.
    */
   public ReceivedMessage () {
      decodedMessage = new StringBuffer (DECODED_MESSAGE_TEXT_LENGTH);
      processingText = new StringBuffer (PROCESSING_TEXT_LEGTH);
      byte [] byteArray = new byte[DEFAULT_BUFFER_SIZE];
      byteBuffer = ByteBuffer.wrap (byteArray);
   }

   public void setMessage (byte[] message, int length) {
      inUse = true;
      
      byteBuffer.put(message, 0, length);
      byteBuffer.limit (byteBuffer.position ());
      byteBuffer.rewind ();
      
      if (length >= 8) {
         messageID = byteBuffer.getInt ();
         messageSize = byteBuffer.getInt ();
      }
      else {
         messageID = -1;
         messageSize = length;
         processingText.append ("Error: message received with too few bytes in header.\n");
      }
      
      timeOfReciept = System.currentTimeMillis ();
   }
   
   public int getMessageID () {
      return messageID;
   }
   
   public int getMessageSize () {
      return messageSize;
   }
   
   public void addDecodedText (String text) {
      decodedMessage.append (text + "\n");
   }
   
   public String getDecodedText () {
      return decodedMessage.toString ();
   }
   
   public void addProcessingText (String text) {
      processingText.append (text + "\n");
   }
   
   public String getProcessingText () {
      return processingText.toString ();
   }
   
   public ByteBuffer getByteBuffer () {
      return byteBuffer;
   }
   
   public void clearMesssage () {
      inUse = false;
      decodedMessage.setLength (0);
      processingText.setLength (0);      
      byteBuffer.clear ();
   }
   
   public boolean inUse () {
      return inUse;
   }

   public long getTimeOfReceipt () {
      return timeOfReciept;
   }
}
