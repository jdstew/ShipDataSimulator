/*
 * DefaultMessage.java
 *
 * Created on July 3, 2005, 9:23 AM
 */
package networkComms.messages.specific;

import java.nio.*;
import networkComms.*;
import networkComms.messages.*;
/**
 *
 * @author  Stewarts
 */
public class DefaultMessage extends AbstractMessage {
   final static int ESTIMATED_SENTENCE_LENGTH = 8;
   final static int DEFAULT_MESSAGE_ID = 299;
   final static byte [] DEFAULT_MESSAGE = { 0x00, 0x00, 0x01, 0x2B, 0x00, 0x00, 0x00, 0x00 };
   final static int MIN_FIELD_COUNT = 0;
   final static int MAX_FIELD_COUNT = 0;
   StringBuffer decodedText;
   ByteBuffer bufferOut;
   int increment;
   
   public DefaultMessage () {
      decodedText = new StringBuffer (8192);
      bufferOut = ByteBuffer.allocate (DEFAULT_MESSAGE.length);
      increment = 1;
   }
   
   public int getEstimatedLength () {
      return ESTIMATED_SENTENCE_LENGTH;
   }
   
   public int getMessageID () {
      return DEFAULT_MESSAGE_ID; // MessageTypes.?;
   }
   
   public byte[] getSimulatorMessage () {
      bufferOut.clear ();
      bufferOut.put (DEFAULT_MESSAGE);
      return bufferOut.array ();
   }
   
   public byte[] getManualMessage () {
      bufferOut.clear ();
      bufferOut.put (DEFAULT_MESSAGE);
      return bufferOut.array ();   
   }
   
   public byte[] getRandomMessage () {
      bufferOut.clear ();
      bufferOut.putInt (299);
      bufferOut.putInt (0);
      return bufferOut.array ();   
   }
   
   public void processReceivedMessage (ReceivedMessage receivedMessage) {
      ByteBuffer bufferIn = receivedMessage.getByteBuffer ();
      
      int messageID = receivedMessage.getMessageID ();
      
      decodedText.setLength (0);
      decodedText.append ("___Message ");
      
      if (MessageTypes.isSupported (messageID)) {
         decodedText.append ("name: " + MessageTypes.getMessageName (messageID));
      }
      else {
         decodedText.append ("ID: " + messageID + "[unknown type]");
      }
      
      decodedText.append (", length: " +
      bufferIn.limit () + ", hex data...\n");
      
      bufferIn.rewind ();
      if (bufferIn.limit () > 0) {
         for (int i = 0; i < bufferIn.limit (); i++) {
            int thisByte = (int)bufferIn.get () & 0x00FF;
            if (thisByte < 16) {
               decodedText.append ('0');
            }
            decodedText.append (Integer.toHexString (thisByte) + ".");
         }
      }
      
      decodedText.append ("\n___End of message\n");
      
      receivedMessage.addDecodedText (decodedText.toString ());
      receivedMessage.addProcessingText ("Unrecognized message received.");
   }
}
