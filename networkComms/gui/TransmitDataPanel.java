/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: TransmitDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.gui;

import networkComms.messages.*;
import networkComms.networkInterface.*;
/**
 * An instance of this class presents a GUI for the ownship simulator.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class TransmitDataPanel extends DefaultTextOutputPanel implements TransmittedMessageListener {

	private static final long serialVersionUID = 1L;
	StringBuffer hexMessage;
   
   /**
    * Initializes an object of this class.
    *
    * @param messageManager The sentence manager for the transmit list.
    */
   public TransmitDataPanel (MessageManager messageManager) {
      messageManager.addTransmittedMessageListener (this);
      hexMessage = new StringBuffer (8192);
   }

   /**
    * Write, to display area, a trasmitted sentence.
    *
    * @param sentence The transmitted sentence.
    */
   public void transmitMessage (byte [] message) {
      this.writeData ("(" + message.length + ") bytes: ");
      
      hexMessage.setLength (0);
      
      for (int i = 0; i < message.length; i++) {
         int thisByte = (int)message[i] & 0x00FF;
            if (thisByte < 16) {
               hexMessage.append ('0');
            }
         hexMessage.append (Integer.toHexString (thisByte) + ".");
      }
      
      this.writeData (hexMessage.toString () + "\n");
   }
}
