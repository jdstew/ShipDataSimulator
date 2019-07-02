/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SetTransmitSentencePanel.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.*;
import mdl.data.*;
import serialComms.gui.*;
import serialComms.sentences.*;
/**
 * This panel provides access to set a specific data sentence to transmit.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SetTransmitSentencePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	JComboBox formatterCombo;
   JComboBox talkerIDCombo; 
   JComboBox sentenceVersionCombo; 
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
   
   TransmitSentenceData outputData = new TransmitSentenceData();
   
   /**
    * Initializes an object of this class.
    *
    * @param inputData The type of transmitting sentence data.
    */
   public SetTransmitSentencePanel (TransmitSentenceData inputData) {
      
      JLabel thisLabel;

      sourceGroup = new ButtonGroup();
      
      simulatorButton = new JRadioButton("Simulator");
      sourceGroup.add (simulatorButton);
      
      randomButton = new JRadioButton("Random");
      randomButton.setSelected(true);
      sourceGroup.add (randomButton);
      
      manualButton = new JRadioButton("Manual");
      sourceGroup.add (manualButton);
      
      setBorder(BorderFactory.createTitledBorder("Set transmit sentence parameters"));
      
      GridBagConstraints constraints = new GridBagConstraints ();
      constraints.anchor = GridBagConstraints.WEST;
            
      setLayout (new GridBagLayout());
      
      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.insets = new Insets (2, 3, 2, 3);
      thisLabel = new JLabel("(1) Formatter:");
      add(thisLabel, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 0;
      int [] formatters = SentenceTypes.getKnownFormatters ();
      Vector<FormatterTypeItem> formatterItemList = new Vector<FormatterTypeItem>();
      
      for (int i = 0; i < formatters.length; i++) {
         if (SentenceTypes.isSupported (formatters[i])) {
            formatterItemList.add (new FormatterTypeItem(formatters[i]));
         }
      }
      formatterCombo = new JComboBox(formatterItemList);
      if (formatterCombo.getItemCount () > 4) {
         formatterCombo.setSelectedIndex (4);
      }
      add(formatterCombo, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 1;
      thisLabel = new JLabel("(2) Talker ID:");
      add(thisLabel, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 1;
      int [] talkerIDs = SentenceTypes.getKnownTalkerIDs ();
      Vector<TalkerIDTypeItem> talkerIDItemList = new Vector<TalkerIDTypeItem>();
      
      for (int i = 0; i < talkerIDs.length; i++) {
         talkerIDItemList.add (new TalkerIDTypeItem(talkerIDs[i]));
      }
      talkerIDCombo = new JComboBox(talkerIDItemList);
      if (talkerIDCombo.getItemCount () > 15) {
         talkerIDCombo.setSelectedIndex (15);
      }
      add(talkerIDCombo, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 2;
      constraints.insets = new Insets (2, 3, 2, 3);
      thisLabel = new JLabel("(3) Frequency:");
      add(thisLabel, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 2;
      freqValue = new JLabel(freqForm.format (AbstractSentence.DEFAULT_TRANSMIT_PERIOD/1000.0) +
                                  "Hz (every " + freqForm.format (1000.0/AbstractSentence.DEFAULT_TRANSMIT_PERIOD) +
                                  " seconds)");
      periodSlider = new JSlider(JSlider.HORIZONTAL, 
                                 (int) AbstractSentence.MAX_TRANSMIT_PERIOD,
                                 (int) AbstractSentence.MIN_TRANSMIT_PERIOD,
                                 (int) AbstractSentence.DEFAULT_TRANSMIT_PERIOD);
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
      constraints.gridy = 3;
      constraints.anchor = GridBagConstraints.NORTH;
      constraints.insets = new Insets (0, 0, 0, 0);
      add(freqValue, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 5;
      constraints.anchor = GridBagConstraints.WEST;
      thisLabel = new JLabel("(4) Source:");
      add(thisLabel, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 5;
      add(simulatorButton, constraints);
            
      constraints.gridx = 1;
      constraints.gridy = 6;
      add(randomButton, constraints);
            
      constraints.gridx = 1;
      constraints.gridy = 7;
      add(manualButton, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 8;
      thisLabel = new JLabel("(5) Sentence version:");
      add(thisLabel, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 8;
      int [] sentenceVersions = SentenceTypes.getKnownSentenceVersions ();
      Vector<SentenceVersionTypeItem> versionItemList = new Vector<SentenceVersionTypeItem>();
      
      for (int i = 0; i < sentenceVersions.length; i++) {
         versionItemList.add (new SentenceVersionTypeItem(sentenceVersions[i]));
      }
      sentenceVersionCombo = new JComboBox(versionItemList);
      add(sentenceVersionCombo, constraints);
      
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
      
      constraints.gridx = 2;
      constraints.gridy = 9;
      constraints.insets = new Insets (2, 3, 2, 3);
      add(buttonBox, constraints);   
      
      if (inputData != null) {
         if (inputData.sentenceFormatter > 0) {
            FormatterTypeItem thisItem;
            for (int i = 0; i < formatterCombo.getItemCount (); i++) {
               thisItem = (FormatterTypeItem) formatterCombo.getItemAt (i);
               if (thisItem.getFormatter () == inputData.sentenceFormatter) {
                  formatterCombo.setSelectedIndex (i);
                  break;
               }
            }
         }
         
         if ((inputData.transmitPeriod > AbstractSentence.MAX_TRANSMIT_PERIOD) && 
             (inputData.transmitPeriod < AbstractSentence.MIN_TRANSMIT_PERIOD)) {
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
      FormatterTypeItem selectedFormatterItem = (FormatterTypeItem) formatterCombo.getSelectedItem ();
      outputData.sentenceFormatter = selectedFormatterItem.getFormatter ();
      
      TalkerIDTypeItem selectedTalkerIDItem = (TalkerIDTypeItem) talkerIDCombo.getSelectedItem ();
      outputData.talkerID = selectedTalkerIDItem.getTalkerID ();
      
      SentenceVersionTypeItem selectedVersionItem = (SentenceVersionTypeItem) sentenceVersionCombo.getSelectedItem ();
      outputData.sentenceVersion = selectedVersionItem.getVersion ();
      
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
    * @return The specific details of the data sentence to transmit.
    */
   public TransmitSentenceData showDialog (JFrame parentFrame) {    
      dialog = new JDialog (parentFrame, "Set Transmit Sentence", true);
      dialog.getContentPane().add(this);
      dialog.getRootPane().setDefaultButton (okButton);
      dialog.pack();
      
      periodSlider.setPreferredSize (formatterCombo.getSize ());
      dialog.pack();
      
      Dimension screenSize =
         Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getPreferredSize();
      dialog.setLocation(screenSize.width/2 - (dialogSize.width/2),
                  screenSize.height/2 - (dialogSize.height/2));
      dialog.setVisible(true);
      return outputData;
   }  
   
   public class FormatterTypeItem extends Object {
      int id;
      
      public FormatterTypeItem (int formatter ) {
         id = formatter;
      }
      
      public String toString() {
         return SentenceTypes.getFormatterIDName (id);
      }
      
      public int getFormatter () {
         return id;
      }
   }
   
   public class TalkerIDTypeItem extends Object {
      int id;
      
      public TalkerIDTypeItem (int talkerID ) {
         id = talkerID;
      }
      
      public String toString() {
         return SentenceTypes.getTalkerIDName (id);
      }
      
      public int getTalkerID () {
         return id;
      }
   }
   
   
   public class SentenceVersionTypeItem extends Object {
      int id;
      
      public SentenceVersionTypeItem (int version) {
         id = version;
      }
      
      public String toString() {
         return SentenceTypes.getSentenceVersionName (id);
      }
      
      public int getVersion () {
         return id;
      }
   }
}
