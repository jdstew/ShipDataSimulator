/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SetTransmitListPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.text.*;
import serialComms.sentences.*;
import serialComms.gui.dialog.*;
/**
 * This panel provides a container for the transmitting sentence table.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class TransmitListPanel extends JPanel implements TransmitListListener {

	private static final long serialVersionUID = 1L;
	SentenceManager sentenceManager;
   TransmitTableModel transmitTableModel;

   JFrame parentFrame;
     
   DecimalFormat freqForm = new DecimalFormat("#.0");
   
   JLabel bandwidthLabel;
   JLabel statusMessage;
   JButton addSentenceButton;
   JButton clearTransmitList;

   /**
    * Initializes an object of this class.
    *
    * @param sentMngr The sentence manager which posseses the tranmitting 
    * sentences.
    */
   public TransmitListPanel (SentenceManager sentMngr) {
      transmitTableModel = new TransmitTableModel();
      
      sentenceManager = sentMngr;
      sentenceManager.addTransmitListListener (this);
      
      setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));
      
      Box controlBox = Box.createHorizontalBox ();
      controlBox.setBorder (BorderFactory.createEmptyBorder (4, 4, 4, 4));

      controlBox.add(new JLabel(" Available bandwidth: "));
      
      bandwidthLabel = new JLabel("?");
      controlBox.add(bandwidthLabel);
      
      controlBox.add(new JLabel(" "));
      
      addSentenceButton = new JButton ("Add Transmit Sentence");
      addSentenceButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               addSentence ();
            }
         });
      controlBox.add(addSentenceButton);
      
      controlBox.add(new JLabel(" "));
      
      clearTransmitList = new JButton ("Clear Transmit List");
      clearTransmitList.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               clearSentenceList ();
            }
         });
      controlBox.add(clearTransmitList);
      controlBox.setAlignmentX (Component.LEFT_ALIGNMENT);
      add(controlBox);
      
      statusMessage = new JLabel("Status: ");
      statusMessage.setAlignmentX (Component.LEFT_ALIGNMENT);
      add(statusMessage);
      
      JTable table = new JTable(transmitTableModel);
      table.setAutoResizeMode (JTable.AUTO_RESIZE_ALL_COLUMNS);
      
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setAlignmentX (Component.LEFT_ALIGNMENT);
      add(scrollPane);
   }
   
   /**
    * Add a new sentence to transmit.
    */
   public void addSentence () {
      TransmitSentenceData newSentence = new TransmitSentenceData();     
      SetTransmitSentencePanel thisPanel = new SetTransmitSentencePanel(newSentence);
      newSentence = thisPanel.showDialog (parentFrame);
      
      if (newSentence != null) {
         sentenceManager.addTransmitSentence(newSentence.sentenceFormatter,
                                             newSentence.talkerID,
                                             newSentence.dataSource, 
                                             newSentence.sentenceVersion,
                                             newSentence.transmitPeriod);
      }
   }
   
   /**
    * Clear the list of transmitting sentence.
    */
   public void clearSentenceList () {
      sentenceManager.stopAllTransmitSentences(); 
   }
   
   /**
    * Update the status message for the last add action taken.
    *
    * @param message Status of last add action taken.
    */
   public void updateAddMessage(String message) {
      statusMessage.setText ("Status: " + message);
      if (message.equalsIgnoreCase ("Ready to add.")) {
         statusMessage.setForeground ((Color) UIManager.get("Label.foreground"));
      }
      else {
         statusMessage.setForeground (Color.RED);
      }
   }
   
   /**
    * Update the list of transmitting sentences.
    *
    * @param updatedList Updated list of transmitting sentences.
    */
   public void updateList(Vector<TransmittingSentence> updatedList) {
      if (updatedList != null) {
         transmitTableModel.updateList (updatedList);
      }
   }
   
   /**
    * Update the amount of available bandwidth for this serial port.
    *
    * @param bandwidth The available bandwidth (in bps).
    */
   public void updateBandwidth (int bandwidth) {
      bandwidthLabel.setText ("" + bandwidth + "bps");
   }
}
