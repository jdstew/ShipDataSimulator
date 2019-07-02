/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 *
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: AbstractSentence.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.messages;

import mdl.data.*;
import networkComms.*;
import dashboard.*;
/**
 * An object of this class frames the basic sentence structure used to implement
 * specific sentence types.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public abstract class AbstractMessage {
   
   protected DataFactory dataFactory;
   protected DashboardChannel dashboardChannel;
   protected int messageID;
   protected String messageName;
   protected int messageVersionID;
   protected String messageVersionName;
   
   public abstract int getEstimatedLength ();
   public abstract int getMessageID ();
   public abstract byte [] getSimulatorMessage ();
   public abstract byte [] getManualMessage ();
   public abstract byte [] getRandomMessage ();
   public abstract void processReceivedMessage (ReceivedMessage receivedMessage);
   
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
   public void setMessageID (int id) {
      messageID = id;
      messageName = MessageTypes.getMessageName (messageID);
   }
   
   /**
    * Get sentence talker identifier.
    *
    * @return Talker ID referenced in SentenceTypes.
    */
   public String getMessageName () {
      return messageName;
   }
   
   /**
    * Set sentence version (NMEA version)
    *
    * @param version NMEA version reference contained in SentenceTypes.
    */
   public void setMessageVersionID (int version) {
      messageVersionID = version;
      messageVersionName = MessageTypes.getMessageVersionName (messageVersionID);
   }
   
   /**
    * Get sentence talker identifier.
    *
    * @return Talker ID referenced in SentenceTypes.
    */
   public String getMessageVersionName () {
      return messageVersionName;
   }
}
