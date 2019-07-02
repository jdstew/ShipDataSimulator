/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: ReceivingSentence.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences;

import java.text.*;
import dashboard.*;
import serialComms.*;
import serialComms.sentences.analysis.*;
/**
 * An object of this class represents a specific sentence type being received
 * on a serial channel.
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-01
 */
public class ReceivingSentence {  
   int sentenceQuality;
   int sentenceFormatter;
   long receivedTotal;
   long receivedSuspectCount;
   long receivedBadCount;
   long timeSincePrevious;
   SentenceReceiveFrequency sentenceRcvFreq;
   AbstractSentence thisSentence;
   
   DecimalFormat freqForm = new DecimalFormat("0.00");
   
   /**
    * Create a ReceivingSentence object.
    *
    * @param formatter The formatter reference from SentenceTypes.
    */
   public ReceivingSentence (int formatter, DashboardChannel dbChnl) {
      sentenceRcvFreq = new SentenceReceiveFrequency();
      sentenceFormatter = formatter;
      thisSentence = SentenceTypes.getSentenceObject (formatter);
      
      thisSentence.setDashboardChannel (dbChnl);
   }
   
   /**
    * Create a ReceivingSentence object.
    *
    * @param receivedSentence The received sentence object.
    */   
   public void receiveSentence (ReceivedSentence receivedSentence) {
      receivedTotal++;
      sentenceRcvFreq.addSample (receivedSentence.timeOfReciept);
      
      if (sentenceRcvFreq.getLatency () > SentenceErrorTypes.SENTENCE_TIMEOUT_LENGTH) {
         receivedSentence.errorLog.addError (new SentenceError(SentenceErrorTypes.ERR_DATA_TIMEDOUT));
      }
      
      thisSentence.processReceivedSentence (receivedSentence);
      
      sentenceQuality = receivedSentence.errorLog.getQualityValue ();
      if (sentenceQuality < SentenceErrorTypes.VALID_SENTENCE) {
         if (sentenceQuality == SentenceErrorTypes.SUSPECT_SENTENCE) {
            receivedSuspectCount++;
         }
         else {
            receivedBadCount++;
         }
      }
   }
   
   /**
    * Get the specific sentence formatter.
    *
    * @return Text description of this sentence type.
    */   
   public String getFormatter() {
      return SentenceTypes.getFormatterIDName (sentenceFormatter);
   }
   
   /**
    * Get the current sentence quality.
    *
    * @return Received sentence quality, which is the worst problems discovered in processing.
    */   
   public String getSentenceQuality () {
      if (sentenceRcvFreq.getLatency () > SentenceErrorTypes.SENTENCE_TIMEOUT_LENGTH) {
         sentenceQuality = SentenceErrorTypes.INVALID_SENTENCE;
      }
      
      if (sentenceQuality == SentenceErrorTypes.VALID_SENTENCE) {
         return "valid";
      } 
      else if (sentenceQuality == SentenceErrorTypes.SUSPECT_SENTENCE) {
         return "suspect";
      }
      else if (sentenceQuality == SentenceErrorTypes.INVALID_SENTENCE) {
         return "invalid";
      }
      else {
         return "error";
      }
   }
   
   /**
    * Get the total number of sentences received for this specific sentence type.
    *
    * @return Total number of sentences received of this type.
    */   
   public String getReceiveTotal () {
      return Long.toString (receivedTotal);
   }
   
   /**
    * Get the total number of suspicious received sentences.
    *
    * @return Number of suspect sentences.
    */   
   public String getReceivedSuspectCount () {
      return Long.toString (receivedSuspectCount);
   }
   
   /**
    * Get the total number of invalid received sentences.
    *
    * @return Number of invalid sentences.
    */   
   public String getReceivedBadCount () {
      return Long.toString (receivedBadCount);
   }
   
   /**
    * Get the received sentence frequency.
    *
    * @return Frequency, in hertz.
    */   
   public String getReceiveFrequency () {
      return freqForm.format (sentenceRcvFreq.getFrequency ());
   }
   
   /**
    * Get the latency between received sentences.
    *
    * @return Latency, in seconds.
    */   
   public String getReceiveLatency () {
      return freqForm.format ((sentenceRcvFreq.getLatency () / 1000.0));
   }
}
/*
 * Revision history:
 *
 * 1.0.0.1  Changed receiveSentence() to receive ReceivedSentence object.
 */
