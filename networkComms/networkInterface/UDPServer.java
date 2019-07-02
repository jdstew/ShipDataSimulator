/*
 * udpServer.java
 *
 * Created on July 5, 2005, 8:47 PM
 */

package networkComms.networkInterface;

import java.net.*;
import java.io.*;
import networkComms.*;
import networkComms.messages.*;
/**
 *
 * @author  Stewarts
 */
public class UDPServer extends Thread implements TransmittedMessageListener {
   
   public static final int DEFAULT_BUFFER_SIZE = 8192;
   
   protected DatagramSocket datagramSocket;
   
   protected InetAddress localInternetAddress;
   protected int localSocket;
   
   protected InetAddress remoteInternetAddress;
   protected int remoteSocket;
   
   private boolean isReceiving;
   private boolean isTransmitting;
   
   byte[] receiveByteArray;
   DatagramPacket receiveDatagramPacket;
   
   ReceivedMessageServer messageServer;
   ReceivedMessage receivedMessage;
   
   ReceivedMessageListener receivedMessageListener;
   
   MessageManager messageManager;
   
   public UDPServer () {
      receiveByteArray = new byte[DEFAULT_BUFFER_SIZE];
      receiveDatagramPacket = new DatagramPacket (receiveByteArray, receiveByteArray.length);
      
      isReceiving = false;
      isTransmitting = false;
      messageServer = new ReceivedMessageServer ();
      this.start ();
   }
   
   public void setReceivedMessageListener (ReceivedMessageListener listener) {
      if (listener != null) {
         receivedMessageListener = listener;
      }
   }
   
   public NetworkOperationStatus setLocalSocket (InetAddress ia, int so) {
      if ( (ia != null) && (so > 0) ) {
         localInternetAddress = ia;
         localSocket = so;
         
         try {
            datagramSocket = new DatagramSocket (localSocket, localInternetAddress);
            datagramSocket.setReuseAddress (true);
            isReceiving = true;
         } catch (SocketException e) {
            return new NetworkOperationStatus (false, e.toString ());
         }
      }
      
      return new NetworkOperationStatus (true, "Local socket (IP + port) set.");
   }
   
   public NetworkOperationStatus setRemoteSocket (InetAddress ia, int so) {
      if ( (ia != null) && (so > 0) ) {
         remoteInternetAddress = ia;
         remoteSocket = so;
         return new NetworkOperationStatus (true, "Remote socket (IP + port) set.");
      }
      else if (ia == null) {
         return new NetworkOperationStatus (false, "Remote destination internet " +
         "address erroneously set to null.");
      }
      else if (so <= 0) {
         return new NetworkOperationStatus (false, "Remote destination socket " +
         "erroneously set to zero.");
      }
      else {
         return new NetworkOperationStatus (false, "Remote destination internet address and socket " +
         "erroneously set to zero.");
      }
   }
   
   public void transmitMessage (byte[] message) {
      // NetworkOperationStatus status = sendDatagramPacket (message);
   }
   
   public NetworkOperationStatus sendDatagramPacket (byte[] buffer) {
      if (isTransmitting) {
         if ( (remoteInternetAddress != null) && (remoteSocket != 0)) {
            DatagramPacket datagramPacket = new DatagramPacket (buffer, buffer.length,
            remoteInternetAddress, remoteSocket);
            try {
               datagramSocket.send (datagramPacket);
            } catch (IOException e) {
               return new NetworkOperationStatus (false, e.toString ());
            }
            return null;
         }
         else {
            System.out.println ("Either remote destination internet " +
            "address or socket is set to an invalid value.");
            return new NetworkOperationStatus (false, "Either remote destination internet " +
            "address or socket is set to an invalid value.");
         }
      }
      else {
         System.out.println ("isTransmitting = false");
         return null;
      }
   }
   
   public void run () { // run...the receive part of this server
      while (true) {
         if (isReceiving) {
            try {
               datagramSocket.receive (receiveDatagramPacket);
            } catch (IOException e) {
               System.out.println (e.toString ());
            }
            receivedMessage = messageServer.getReceivedMessage ();
            receivedMessage.setMessage (receiveDatagramPacket.getData (),
               receiveDatagramPacket.getLength ());
            receivedMessageListener.receiveMessage (receivedMessage);
         }
         
         try {
            Thread.sleep (20); // ...assumes a maximum receive rate of 50Hz.
         }
         catch (InterruptedException e) {
            System.out.println (e.toString ());
         }
      }
   }
   
   public void setReceiving (boolean receive) {
      isReceiving = receive;
   }
   
   public void setTransmitting (boolean transmit) {
      isTransmitting = transmit;
   }
   
   public void closeUDPServer () {
      isReceiving = false; // to block calls to read receive() method
      messageManager.closeMessageManager ();
      datagramSocket.close ();
      try {
         this.finalize ();
      }
      catch (Throwable throwable){
         System.out.println ("Finalizing UDP Server attempted, but failed. " +
         throwable.toString ());
      }
   }
   
   public void setMessageManager (MessageManager msgMngr) {
      if (msgMngr != null) {
         messageManager = msgMngr;
         messageManager.addTransmittedMessageListener (this);
         receivedMessageListener = messageManager;
      }
   }
   
   public MessageManager getMessageManager () {
      return messageManager;
   }
   
   public String getLocalIA () {
      return localInternetAddress.getHostAddress ();
   }
   
   public int getLocalPort () {
      return localSocket;
   }
   
   public String getRemoteIA () {
      return remoteInternetAddress.getHostAddress ();
   }
   
   public int getRemotePort () {
      return remoteSocket;
   }
}
