/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: ReceiveDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.gui;

import networkComms.*;
import networkComms.networkInterface.*;
import networkComms.messages.*;
/**
 * An instance of this class presents a GUI for the ownship simulator.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class ReceiveDataPanel extends DefaultTextOutputPanel 
   implements ReceivedMessageListener {
      
	private static final long serialVersionUID = 1L;

/** 
    * Initializes an object of this class.
    *
    * @param messageManager The sentence manager for this GUI panel.
    */
   public ReceiveDataPanel (MessageManager messageManager) {
      messageManager.addReceivedMessageListener (this);
   }
   
   /**
    * Receive, to display, data received by a serial port.
    *
    * @param receivedSentence The received sentence data.
    */
   public void receiveMessage (ReceivedMessage receivedMessage) {
      this.writeData (receivedMessage.getDecodedText ());
   }
}
