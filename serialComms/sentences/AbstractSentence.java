/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: AbstractSentence.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences;

import mdl.data.*;
import serialComms.*;
import dashboard.*;
/**
 * An object of this class frames the basic sentence structure used to implement
 * specific sentence types.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public abstract class AbstractSentence {
   
   /** Used to set a transmit frequency of 0.05Hz (or a period of 20 seconds). */
   public static final long MIN_TRANSMIT_PERIOD = 20000;
   /** Used to set a transmit frequency of 1Hz (or a period of 1 second). */
   public static final long DEFAULT_TRANSMIT_PERIOD = 1000;
   /** Used to set a transmit frequency of 20Hz (or a period of 0.05 seconds). */
   public static final long MAX_TRANSMIT_PERIOD = 50;
   
   protected DataFactory dataFactory;
   protected DashboardChannel dashboardChannel;
   protected int talkerID;
   protected String talkerIDMnemonic;
   protected int sentenceVersion;
  
   public abstract int getEstimatedLength();
   public abstract int getSentenceFormatter();
   public abstract boolean isSentenceIDValid (int id);
   public abstract String getSimulatorSentence();
   public abstract String getManualSentence();
   public abstract String getRandomSentence();
   public abstract void processReceivedSentence(ReceivedSentence receivedSentence);
   
   /**
    * Set the DataFactory object for the specific sentence.
    *
    * @param factory The store of data used to build transmitted sentences.
    */   
   public void setDataFactory (DataFactory factory) {
      if (factory != null) {
         dataFactory = factory;
      }
      else {
         System.out.println ("DataFactory not provided for AbstractSentence.");
      }
   }
   
   /**
    * Set the DashboardChannel object for the specific sentence.
    *
    * @param dbChnl The ...
    */   
   public void setDashboardChannel (DashboardChannel dbChnl) {
      if (dbChnl != null) {
         dashboardChannel = dbChnl;
      }
      else {
         System.out.println ("DashboardChannel not provided for AbstractSentence.");
      }
   }
   
   /**
    * Set talker identifier.
    *
    * @param id Talker ID referenced in SentenceTypes.
    */   
   public void setTalkerID (int id) {
      talkerID = id;

      if (SentenceTypes.getTalkerIDName (talkerID) == null) {
         talkerID = SentenceTypes.TALKER_IN;
      }
      
      if (talkerID > 0x00FF) {
         talkerIDMnemonic = "" + ((char)((talkerID & 0xFF00) >>> 8)) +
                                 ((char) (talkerID & 0x00FF));
      }
      else {
         talkerIDMnemonic = "" + ((char) (talkerID & 0x00FF));
      }
   }
   
   /**
    * Get sentence talker identifier.
    *
    * @return Talker ID referenced in SentenceTypes.
    */   
   public String getTalkerID () {
      return talkerIDMnemonic;      
   }
   
   /**
    * Set sentence version (NMEA version)
    *
    * @param version NMEA version reference contained in SentenceTypes.
    */   
   public void setSentenceVersion (int version) {
      if (SentenceTypes.getSentenceVersionName (version) != null) {
         sentenceVersion = version;
      }
      else {
         sentenceVersion = SentenceTypes.NMEA_DEFAULT_VERSION;
      }
   }
}
