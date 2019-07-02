/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentenceManager.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.messages;

import java.util.*;
import mdl.data.*;
import dashboard.*;
import networkComms.*;
import networkComms.gui.*;
import networkComms.networkInterface.*;
//import networkComms.networkInterface.transceivers.*;
/**
 * An object of this class manages all received and transmitted messages
 * on a unique serial port.
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-01
 */
public class MessageManager extends TimerTask implements ReceivedMessageListener, TransmittedMessageListener {
   /** Used to set a transmit frequency of 0.03Hz (or a period of 30 seconds). */
   public static final long MIN_TRANSMIT_PERIOD = 30000;
   /** Used to set a transmit frequency of 1Hz (or a period of 1 second). */
   public static final long DEFAULT_TRANSMIT_PERIOD = 1000;
   /** Used to set a transmit frequency of 50Hz (or a period of 0.02 seconds). */
   public static final long MAX_TRANSMIT_PERIOD = 20;
   
   /** The default received message list update rate, in milliseconds. */
   public static final long UPDATE_RECEIVED_DATA_PERIOD = 2700;
   // private static final boolean DEBUG_MODE = true;
   
   Timer receivedDataUpdateTimer = new Timer();
   
   private Vector<ReceivedMessageListener> receivedMessageListeners;
   private Vector<TransmittedMessageListener> transmittedMessageListeners;
   private Vector<Object> transmitListListeners = new Vector<Object>();
   private Vector<Object> receiveListListeners = new Vector<Object>();
   
   HashMap<Integer, ReceivingMessage> receivingMessagesMap;
   HashMap<Integer, TransmittingMessage> transmittingMessagesMap;
   
//   UDPServer server;
   
   DataFactory dataFactory;
   DashboardChannel dashboardChannel;
   
   int bandwidthBPS;
   
   ReceivingMessage receivingMessage;
   
   /**
    * Initialize a SentenceManager object.
    *
    * @param serialPort The serial port to communicate on.
    * @param factory The data factory to obtain data from.
    * @param dbgMgr The debug manager to make reports to.
    */   
   public MessageManager (DataFactory factory, DashboardChannel dbChnl) {
      
      bandwidthBPS = 100000000;

      receivedMessageListeners = new Vector<ReceivedMessageListener>();
      transmittedMessageListeners = new Vector<TransmittedMessageListener>();

      receivingMessagesMap = new HashMap<Integer, ReceivingMessage>();
      transmittingMessagesMap = new HashMap<Integer, TransmittingMessage>();
      
      this.resetReceivingMessages ();
      this.resetTransmittingMessages ();
      
//      if (udpServer != null) {
//         server = udpServer;
//      }
//      else {
//         server = new UDPServer ();
//         System.out.println ("UDPServer object not passed to MessageManager object. ");
//      }
      
      if (factory != null) {
         dataFactory = factory;
      }
      else {
         dataFactory = new DataFactory();
         System.out.println ("DataFactory object not passed to MessageManager object. ");
      }
      
      if (dbChnl != null) {
         dashboardChannel = dbChnl;
      }
      
      receivedDataUpdateTimer.schedule (this, 0, UPDATE_RECEIVED_DATA_PERIOD);
   }
   
   /**
    * Reset (clear) the list of received messages.
    */
   public void resetReceivingMessages () {
      receivingMessagesMap.clear ();
   }
      
   /** 
    * Reset (clear) the list of tranmitting messages. 
    */
   public void resetTransmittingMessages () {
      transmittingMessagesMap.clear ();
   }
   
   /**
    * Add a received sentnece listener.
    *
    * @param listener The listener to add.
    */   
   public synchronized void addReceivedMessageListener (ReceivedMessageListener listener) {
      receivedMessageListeners.addElement(listener);
   }
   
   /**
    * Remove a received sentnece listener.
    *
    * @param listener The listener to remove.
    */   
   public synchronized void removeReceivedMessageListener (ReceivedMessageListener listener) {
      receivedMessageListeners.remove(listener);
   }
   
   /**
    * Add a transmitted sentnece listener.
    *
    * @param listener The listener to add.
    */   
   public synchronized void addTransmittedMessageListener (TransmittedMessageListener listener) {
      transmittedMessageListeners.addElement(listener);
   }
   
   /**
    * Remove a transmitted sentnece listener.
    *
    * @param listener The listener to remove.
    */   
   public synchronized void removeTransmittedMessageListener (TransmittedMessageListener listener) {
      transmittedMessageListeners.remove(listener);
   }
   
   /**
    * Add a transmit list listener.
    *
    * @param listener The listener to add.
    */   
   public synchronized void addTransmitListListener (Object listener) {
      transmitListListeners.addElement(listener);
   }
   
   /**
    * Remove a transmit list listener.
    *
    * @param listener The listener to remove.
    */   
   public synchronized void removeTransmitListListener (Object listener) {
      transmitListListeners.remove(listener);
   }
   
   /**
    * Add a receive list listener.
    *
    * @param listener The listener to add.
    */   
   public synchronized void addReceiveListListener (Object listener) {
      receiveListListeners.addElement(listener);
   }
   
   /**
    * Remove a receive list listener.
    *
    * @param listener The listener to remove.
    */   
   public synchronized void removeReceiveListListener (Object listener) {
      receiveListListeners.remove(listener);
   }
   
   /**
    * Close the serial port that this SentenceManager is bound to.
    */
   public void closeMessageManager () {
      try {
         this.resetReceivingMessages ();
         this.stopAllTransmittingMessages ();
         this.resetTransmittingMessages ();
         this.cancel ();
         this.finalize ();
      }
      catch (Throwable throwable){ 
         System.out.println ("Finalizing SentenceManager attempted, but failed. " +
            throwable.toString ());
      }
   }
   
   /**
    * Start the refresh cycle for updating received message statistics.
    */
   public void run () {
      // Update receive data listeners
      for (int i = 0; i < receiveListListeners.size(); i++) {
         ReceiveListListener listener = (ReceiveListListener) receiveListListeners.elementAt (i);
         listener.updateList (new Vector<ReceivingMessage>(receivingMessagesMap.values ()));
      }
   }
  
   /**
    * Receive a message from a SentenceTransceiver.
    *
    * @param receivedSentence The received message object.
    */   
   public void receiveMessage (ReceivedMessage receivedMessage) {
      Integer keyValue = new Integer(receivedMessage.getMessageID ());
      if (receivingMessagesMap.containsKey (keyValue)) {
         receivingMessage = (ReceivingMessage) receivingMessagesMap.get (keyValue);
         receivingMessage.receiveMessage (receivedMessage);
      }
      else {
         receivingMessage = new ReceivingMessage (receivedMessage.getMessageID (), dashboardChannel);
         receivingMessage.receiveMessage (receivedMessage);
         receivingMessagesMap.put (keyValue, receivingMessage);
      }
      
      for (int i = 0; i < receivedMessageListeners.size (); i++) {
         ReceivedMessageListener listener =
            (ReceivedMessageListener) receivedMessageListeners.elementAt (i);
         listener.receiveMessage (receivedMessage);
      }
      
      receivedMessage.clearMesssage ();
   }
   
   /**
    * Add a message to transmit, referenced by message formatter. Note that
    * adding a message that is currently transmitting will replace, not
    * duplicate the transmissions.
    *
    * @param msgID The message formatter, see SentenceTypes
    * @param src The source of the data to transmit, see DataFactory
    * @param per The period, in milliseconds, of the transmittions.
    */   
   public void addTransmitMessage (int msgID, 
                                   int src,
                                   long per) {
                                      
      int messageID = msgID;
      int source;
      long period;
      
      int bandwidthDemand;
      
      TransmittingMessage newTransmittingMessage;
      TransmittingMessage oldTransmittingMessage;
      
      String operationStatus;
      
      if ((src != DataFactory.MANUAL_DATA_SOURCE) ||
          (src != DataFactory.RANDOM_DATA_SOURCE) ||
          (src != DataFactory.SIMULATOR_DATA_SOURCE)) {
             source = src;
      }
      else {
         source = DataFactory.RANDOM_DATA_SOURCE;
      }
      
      if ((per > DataFactory.TRANSMIT_FREQ_PT05HZ) ||
          (per < DataFactory.TRANSMIT_FREQ_20HZ)) {
          period = DataFactory.TRANSMIT_FREQ_1HZ;
      }
      else {
         period = per;
      }
      
      newTransmittingMessage = new TransmittingMessage (messageID, source, period, dataFactory);
      try {
         newTransmittingMessage.addTransmittedMessageListener(this);
      } 
      catch (TooManyListenersException error) {
         System.out.println ("Serial channel listener already set in TransmittingMessage. " +
            error.toString());
      }
      
      bandwidthDemand = newTransmittingMessage.getBandwidthBPS ();
      
      if (bandwidthDemand < bandwidthBPS) {
         
         bandwidthBPS -= bandwidthDemand;
         
         Integer keyValue = new Integer(messageID);
         if (transmittingMessagesMap.containsKey (keyValue)) {
            oldTransmittingMessage = (TransmittingMessage) transmittingMessagesMap.remove (keyValue);
            bandwidthBPS += oldTransmittingMessage.getBandwidthBPS ();
            oldTransmittingMessage.stop();
            operationStatus = "New message replaced existing sentece.";
         }
         else {
            operationStatus = "Ready to add.";
         }
         transmittingMessagesMap.put (keyValue, newTransmittingMessage);
      }
      else {
         newTransmittingMessage.stop ();
         operationStatus = "Insufficient bandwidth available to add requested message.";
      }
      
      for (int i = 0; i < transmitListListeners.size(); i++) {
        TransmitListListener listener = (TransmitListListener) transmitListListeners.elementAt (i);
        listener.updateList (new Vector<TransmittingMessage>(transmittingMessagesMap.values ()));
        listener.updateBandwidth (bandwidthBPS);
        listener.updateAddMessage (operationStatus);
      } 
   }

   /**
    * Remove and stop an individual message type from transmitting.
    *
    * @param formatter The message to stop, referenced by message formatter.
    */   
   public void removeTransmitMessage (int messageID) {
      Integer keyValue = new Integer(messageID);
      if (transmittingMessagesMap.containsKey (keyValue)) {
         transmittingMessagesMap.remove (keyValue);
      }                              
   }
   
   /**
    * Stop all transmitting messages.
    */
   public void stopAllTransmittingMessages () {
      TransmittingMessage removedTransmittingMessage;
      Vector<TransmittingMessage> transmitList = new Vector<TransmittingMessage>(transmittingMessagesMap.values ());
      for (int i = 0; i < transmitList.size (); i++) {
         removedTransmittingMessage =  (TransmittingMessage) transmitList.elementAt (i);
         bandwidthBPS += removedTransmittingMessage.getBandwidthBPS ();
         removedTransmittingMessage.stop();
      }
      transmittingMessagesMap = new HashMap<Integer, TransmittingMessage>();

      for (int i = 0; i < transmitListListeners.size(); i++) {
        TransmitListListener listener = (TransmitListListener) transmitListListeners.elementAt (i);
        listener.updateList (new Vector<TransmittingMessage>(transmittingMessagesMap.values ()));
        listener.updateBandwidth (bandwidthBPS);
        listener.updateAddMessage (new String ("Transmit list cleared."));
      } 
   }
   
   /**
    * Transmit a message.
    *
    * @param message The complete message to transmit to the serial channel.
    */   
   public synchronized void transmitMessage (byte [] message) {
     for (int i = 0; i < transmittedMessageListeners.size(); i++) {
        TransmittedMessageListener listener = (TransmittedMessageListener) transmittedMessageListeners.elementAt (i);
        listener.transmitMessage (message);
     } 
   }
}
/*
 * Revision history:
 *
 */
