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

/**
 * An object of this class is created by a SentenceTranceiver and passed
 * along to the SentenceManager for processing.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-26
 */
public class ReceivedMessageServer {
   
   /** Default count of ReceivedMessage objects pool. */
   public final static int DEFAULT_SENTENCE_COUNT = 32;
   
   ReceivedMessage [] messagePool;
   int index;
   
   public ReceivedMessageServer () {
      messagePool = new ReceivedMessage[DEFAULT_SENTENCE_COUNT];
      for (int i = 0; i < messagePool.length; i++) {
         messagePool[i] = new ReceivedMessage();
      }
      index = 0;
   }
   
   public ReceivedMessage getReceivedMessage () {
      
      int searchCount = 0;
      
      if (index >= messagePool.length) {
         index = 0;
      }
      
      while (searchCount < messagePool.length) {
         if (!messagePool[index].inUse ()) {
            return messagePool[index++];
         }
         
         index++;
         if (index >= (messagePool.length - 1)) {
            index = 0;
         }

         searchCount++;
      }
      
      System.out.println ("Warning: ReceivedMessageServer is overloaded.");
      return new ReceivedMessage();
   }
}
