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

/**
 * An object of this class is created by a SentenceTranceiver and passed
 * along to the SentenceManager for processing.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-26
 */
public class ReceivedSentenceServer {
   
   /** Default count of ReceivedSentence objects pool. */
   public final static int DEFAULT_SENTENCE_COUNT = 10;
   
   ReceivedSentence[] sentencePool;
   int index;
   
   public ReceivedSentenceServer () {
      
      sentencePool = new ReceivedSentence[DEFAULT_SENTENCE_COUNT];
      for (int i = 0; i < sentencePool.length; i++) {
         sentencePool[i] = new ReceivedSentence();
      }
      index = 0;
   }
   
   public ReceivedSentence getReceivedSentence () {
      
      int searchCount = 0;
      
      if (index >= sentencePool.length) {
         index = 0;
      }
      
      while (searchCount < sentencePool.length) {
         if (!sentencePool[index].getInUse ()) {
            return sentencePool[index++];
         }
         
         index++;
         if (index >= (sentencePool.length - 1)) {
            index = 0;
         }

         searchCount++;
      }
      
      System.out.println ("Warning: ReceivedSentenceServer is overloaded.");
      return new ReceivedSentence();
   }
}
