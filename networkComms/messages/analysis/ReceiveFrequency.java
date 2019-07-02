/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentenceReceiveFrequency.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.messages.analysis;

/**
 * This class provides a means to calculate statistics on received sentences
 * using a moving window average.  The primary outputs of a FrequencyStatistics
 * object is the frequency of received sentences and the latency from the last
 * received sentence.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class ReceiveFrequency {
   
   /** Default window size */
   public final static int DEFAULT_WINDOW_SIZE = 10;
  
   int sampleSize;
   int intervalIndex;
   long [] timeIntervals;
   long timeLastReceived;
   long runningTotal;
   boolean fullSet;
   float meanPeriod; // = latency if now < meanPeriod;
   
   /**
    * Creates a new object of this class.
    *
    * @param windowSize How many samples to average over.
    */   
   public ReceiveFrequency (int windowSize) {
      if (windowSize != 0) {
         sampleSize = windowSize;
         timeIntervals = new long[windowSize + 1];
      }
      else {
         sampleSize = DEFAULT_WINDOW_SIZE;
         timeIntervals = new long[sampleSize + 1];
      }
      timeIntervals[0] = 0;
      intervalIndex = 1;
      timeLastReceived = 0;
      runningTotal = 0;
      fullSet = false;
      meanPeriod = Float.POSITIVE_INFINITY;
   }
   
   /** Creates a new object of this class, using the default window size. */
   public ReceiveFrequency () {
      this(DEFAULT_WINDOW_SIZE);
   }
   
   /**
    * Add a new sample.
    *
    * @param timeThisReceived The time of the sample (e.g. time of receipt)
    * @return Interval since last sample.
    */   
   public void addSample (long timeReceived) {
      long thisInterval;
      if (fullSet) {
         runningTotal -= timeIntervals[intervalIndex];
      }
      thisInterval = timeReceived - timeLastReceived;
      runningTotal += thisInterval;
      timeIntervals[intervalIndex++] = thisInterval;
      
      if (intervalIndex > sampleSize) {
         intervalIndex -= sampleSize;
         fullSet = true;
      }
      
      timeLastReceived = timeReceived;
   }
   
   /**
    * Get frequency of samples.
    *
    * @return Frequency, in hertz.
    */   
   public double getFrequency () {
      if (fullSet) {
         return ( ( 1000.0 * sampleSize ) / (double)runningTotal);
      }
      else {
         return ( ( 1000.0 * ( intervalIndex -  1 ) ) / (double)runningTotal);
      }
   }
   
   /**
    * Get latency since last sample.
    *
    * @return Latency, in milliseconds.
    */   
   public long getLatency () {
      return System.currentTimeMillis () - timeLastReceived;
   }
}
