/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: ReceiveDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.gui;

import serialComms.*;
import serialComms.serialInterface.*;
import serialComms.sentences.*;

/**
 * An instance of this class presents a GUI for the ownship simulator.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class ReceiveDataPanel extends DefaultTextOutputPanel 
   implements ReceivedSentenceListener {
      
	private static final long serialVersionUID = 1L;

/** 
    * Initializes an object of this class.
    *
    * @param sentenceManager The sentence manager for this GUI panel.
    */
   public ReceiveDataPanel (SentenceManager sentenceManager) {
      sentenceManager.addReceivedSentenceListener (this);
   }
   
   /**
    * Receive, to display, data received by a serial port.
    *
    * @param receivedSentence The received sentence data.
    */
   public void receiveSentence (ReceivedSentence receivedSentence) {
      this.writeData (receivedSentence.sentenceReceived + "\n");
   }
}
