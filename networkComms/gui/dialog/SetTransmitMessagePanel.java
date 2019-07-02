/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SetTransmitSentencePanel.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.*;
import mdl.data.*;
import networkComms.gui.*;
import networkComms.messages.*;
/**
 * This panel provides access to set a specific data message to transmit.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SetTransmitMessagePanel extends JPanel {
 
	private static final long serialVersionUID = 1L;
	JComboBox messageIDCombo;
   JSlider periodSlider;
   JLabel freqValue;
   JRadioButton simulatorButton;
   JRadioButton randomButton;
   JRadioButton manualButton;
   ButtonGroup sourceGroup;
   
   DecimalFormat freqForm = new DecimalFormat("#.00");

   JButton okButton;
   JButton cancelButton;
   JDialog dialog;
   
   TransmitMessageData outputData = new TransmitMessageData();
   
   /**
    * Initializes an object of this class.
    *
    * @param inputData The type of transmitting message data.
    */
   public SetTransmitMessagePanel (TransmitMessageData inputData) {
      
      JLabel thisLabel;

      sourceGroup = new ButtonGroup();
      
      simulatorButton = new JRadioButton("Simulator");
      sourceGroup.add (simulatorButton);
      
      randomButton = new JRadioButton("Random");
      randomButton.setSelected(true);
      sourceGroup.add (randomButton);
      
      manualButton = new JRadioButton("Manual");
      sourceGroup.add (manualButton);
      
      setBorder(BorderFactory.createTitledBorder("Set transmit message parameters"));
      
      GridBagConstraints constraints = new GridBagConstraints ();
      constraints.anchor = GridBagConstraints.WEST;
            
      setLayout (new GridBagLayout());
      
      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.insets = new Insets (2, 3, 2, 3);
      thisLabel = new JLabel("(1) Message:");
      add(thisLabel, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 0;
      int [] messageIDs = MessageTypes.getMessagesIDs ();
      Vector<MessageTypeItem> messageIDList = new Vector<MessageTypeItem>();
      
      for (int i = 0; i < messageIDs.length; i++) {
         if (MessageTypes.isSupported (messageIDs[i])) {
            messageIDList.add (new MessageTypeItem(messageIDs[i]));
         }
      }
      messageIDCombo = new JComboBox(messageIDList);
//      if (messageIDCombo.getItemCount () > 4) {
//         messageIDCombo.setSelectedIndex (4);
//      }
      add(messageIDCombo, constraints);
           
      constraints.gridx = 0;
      constraints.gridy = 1;
      constraints.insets = new Insets (2, 3, 2, 3);
      thisLabel = new JLabel("(2) Frequency:");
      add(thisLabel, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 1;
      freqValue = new JLabel(freqForm.format (MessageManager.DEFAULT_TRANSMIT_PERIOD/1000.0) +
                                  "Hz (every " + freqForm.format (1000.0/MessageManager.DEFAULT_TRANSMIT_PERIOD) +
                                  " seconds)");
      periodSlider = new JSlider(JSlider.HORIZONTAL, 
                                 (int) MessageManager.MAX_TRANSMIT_PERIOD,
                                 (int) MessageManager.MIN_TRANSMIT_PERIOD,
                                 (int) MessageManager.DEFAULT_TRANSMIT_PERIOD);
      Dimension screenSize =
         Toolkit.getDefaultToolkit().getScreenSize();
      periodSlider.setSize (screenSize.width, 20);
      periodSlider.addChangeListener (new ChangeListener() {
            public void stateChanged (ChangeEvent event) {
               JSlider source = (JSlider) event.getSource ();
               freqValue.setText (freqForm.format (source.getValue()/1000.0) +
                                  "Hz (every " + freqForm.format (1000.0/source.getValue()) +
                                  " seconds)");
            }
         });
      add(periodSlider, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 2;
      constraints.anchor = GridBagConstraints.NORTH;
      constraints.insets = new Insets (2, 3, 2, 3);
      add(freqValue, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 3;
      constraints.anchor = GridBagConstraints.WEST;
      thisLabel = new JLabel("(3) Source:");
      add(thisLabel, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 3;
      add(simulatorButton, constraints);
            
      constraints.gridx = 1;
      constraints.gridy = 4;
      add(randomButton, constraints);
            
      constraints.gridx = 1;
      constraints.gridy = 5;
      add(manualButton, constraints);

            
      okButton = new JButton ("Ok");
      okButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               setTransmitSentenceData();
               dialog.setVisible (false);
            }
         });
      
      cancelButton = new JButton ("Cancel");
      cancelButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               outputData = null;
               dialog.setVisible (false);
            }
         });
         
      Box buttonBox = new Box(BoxLayout.X_AXIS);
      buttonBox.add(okButton);
      buttonBox.add (new JLabel(" "));
      buttonBox.add(cancelButton);
      
      constraints.gridx = 1;
      constraints.gridy = 6;
      constraints.insets = new Insets (2, 3, 2, 3);
      add(buttonBox, constraints);   
      
      if (inputData != null) {
         if (inputData.messageID > 0) {
            MessageTypeItem thisItem;
            for (int i = 0; i < messageIDCombo.getItemCount (); i++) {
               thisItem = (MessageTypeItem) messageIDCombo.getItemAt (i);
               if (thisItem.getMessageID () == inputData.messageID) {
                  messageIDCombo.setSelectedIndex (i);
                  break;
               }
            }
         }
         
         if ((inputData.transmitPeriod > MessageManager.MAX_TRANSMIT_PERIOD) && 
             (inputData.transmitPeriod < MessageManager.MIN_TRANSMIT_PERIOD)) {
            periodSlider.setValue ((int) inputData.transmitPeriod);
         } 
         
         if (inputData.dataSource == DataFactory.SIMULATOR_DATA_SOURCE) {
            simulatorButton.setSelected(true);
         }
         else if (inputData.dataSource == DataFactory.MANUAL_DATA_SOURCE){
            manualButton.setSelected(true);
         }
         else {
            randomButton.setSelected (true);
         }
      }
   }
   
   void setTransmitSentenceData () {
      MessageTypeItem selectedMessageItem = (MessageTypeItem) messageIDCombo.getSelectedItem ();
      outputData.messageID = selectedMessageItem.getMessageID ();
      
      outputData.transmitPeriod = 1000000/periodSlider.getValue ();
      if (manualButton.isSelected ()) {
         outputData.dataSource = DataFactory.MANUAL_DATA_SOURCE;
      }
      else if (randomButton.isSelected ()){
         outputData.dataSource = DataFactory.RANDOM_DATA_SOURCE;
      }
      else {
         outputData.dataSource = DataFactory.SIMULATOR_DATA_SOURCE;
      }     
   }

   /**
    * Displays a dialog box of this panel.
    *
    * @param parentFrame The application frame for this application.
    * @return The specific details of the data message to transmit.
    */
   public TransmitMessageData showDialog (JFrame parentFrame) {    
      dialog = new JDialog (parentFrame, "Set Transmit Sentence", true);
      dialog.getContentPane().add(this);
      dialog.getRootPane().setDefaultButton (okButton);
//      dialog.pack();
      
//      periodSlider.setPreferredSize (messageIDCombo.getSize ());
//      dialog.pack();
      
      Dimension screenSize =
         Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getPreferredSize();
      dialog.setLocation(screenSize.width/2 - (dialogSize.width/2),
                  screenSize.height/2 - (dialogSize.height/2));
      dialog.pack();
      dialog.setVisible(true);
      return outputData;
   }  
   
   public class MessageTypeItem extends Object {
      int id;
      
      public MessageTypeItem (int msgID ) {
         id = msgID;
      }
      
      public String toString() {
         return MessageTypes.getMessageName (id);
      }
      
      public int getMessageID () {
         return id;
      }
   }
   
   /**
    * Starts the application.
    *
    * @param arg read but not used.
    */
   public static void main (String [] arg){
      JFrame frame = new JFrame ();
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      SetTransmitMessagePanel panel = new SetTransmitMessagePanel (null);
      panel.showDialog (frame);
   }
}
