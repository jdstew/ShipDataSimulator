/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: ReceiveErrorPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.gui;

import serialComms.*;
import serialComms.serialInterface.*;
import serialComms.sentences.*;
import serialComms.sentences.analysis.*;
/**
 * An instance of this class presents a GUI for the ownship simulator.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class ReceiveErrorPanel extends DefaultTextOutputPanel implements ReceivedSentenceListener {
	private static final long serialVersionUID = 1L;

/**
    * Initializes an object of this class.
    *
    * @param sentenceManager The sentence manager which manages processing of
    * received sentences.
    */
   public ReceiveErrorPanel (SentenceManager sentenceManager) {
      sentenceManager.addReceivedSentenceListener (this);
   }
   
   /**
    * Receive, and display, errors found from processing a received sentence.
    *
    * @param receivedSentence The received sentence that has been processed.
    */
   public void receiveSentence (ReceivedSentence receivedSentence) {
      SentenceErrorLog errorLog = receivedSentence.errorLog;
      
      if (errorLog.getQualityValue () < SentenceErrorTypes.VALID_SENTENCE) {
         this.writeData ("______________________________________\n");
         this.writeData (receivedSentence.sentenceReceived + "\n");
         this.writeData (errorLog.printErrorLog () + "\n");
      }
   }
}
