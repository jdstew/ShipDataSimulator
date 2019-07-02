/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: TransmitDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.gui;

import serialComms.sentences.*;
import serialComms.serialInterface.*;
/**
 * An instance of this class presents a GUI for the ownship simulator.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class TransmitDataPanel extends DefaultTextOutputPanel 
   implements SentTransListener {

	private static final long serialVersionUID = 1L;

/**
    * Initializes an object of this class.
    *
    * @param sentenceManager The sentence manager for the transmit list.
    */
   public TransmitDataPanel (SentenceManager sentenceManager) {
      sentenceManager.addSentTransListener (this);
   }

   /**
    * Write, to display area, a trasmitted sentence.
    *
    * @param sentence The transmitted sentence.
    */
   public void transmitSentence (String sentence) {
      this.writeData (sentence);
   }
}
