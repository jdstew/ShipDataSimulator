/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: OpenSerialPortPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

import serialComms.serialInterface.*;
/**
 * This panel provides a GUI to open a serial port.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class OpenSerialPortPanel extends JPanel {
   
	private static final long serialVersionUID = 1L;

	JComboBox portNameCombo;
   
   Object [][] interfaceTypes = SerialInfo.DIGITAL_INTERFACES;
   Object [][] baudRates = SerialInfo.BUADRATE;
   Object [][] dataBits = SerialInfo.DATABITS;
   Object [][] parity = SerialInfo.PARITY;
   Object [][] stopBits = SerialInfo.STOPBITS;
   Object [][] flowControl = SerialInfo.FLOWCONTROL;
   
   JComboBox interfaceTypeCombo;
   
   ArrayList<JRadioButton> baudRateButtons;
   ArrayList<JRadioButton> dataBitsButtons;
   ArrayList<JRadioButton> parityButtons;
   ArrayList<JRadioButton> stopBitsButtons;
   ArrayList<JRadioButton> flowControlButtons;
   
   ButtonGroup baudRateGroup = new ButtonGroup();
   ButtonGroup dataBitsGroup = new ButtonGroup();
   ButtonGroup parityGroup = new ButtonGroup();
   ButtonGroup stopBitsGroup = new ButtonGroup();
   ButtonGroup flowControlGroup = new ButtonGroup();
   
   JRadioButton thisButton;
   JLabel thisLabel;
   
   JButton okButton;
   JButton cancelButton;
   JDialog dialog;
   
   SerialPortInfo portParams;
   
   /**
    * Initializes an object of this class.
    *
    * @param portNameArray The list of serial ports available.
    */
   public OpenSerialPortPanel (String[] portNameArray) {
      Integer testValue;

      setBorder(BorderFactory.createTitledBorder("Select serial port parameters"));
      
      GridBagConstraints constraints = new GridBagConstraints ();
      constraints.anchor = GridBagConstraints.NORTHWEST;
             
      setLayout (new GridBagLayout());
      
      constraints.gridx = 0;
      constraints.gridy = 2;
      constraints.insets = new Insets (2, 3, 2, 3);
      thisLabel = new JLabel("Port");
      thisLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      add(thisLabel, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 3;
      constraints.insets = new Insets (0, 0, 0, 0);
      portNameCombo = new JComboBox (portNameArray);
      portNameCombo.setToolTipText ("Serial port pull-down selector.");
      add(portNameCombo, constraints);
      
      dataBitsGroup = new ButtonGroup();
      parityGroup = new ButtonGroup();
      stopBitsGroup = new ButtonGroup();
      flowControlGroup = new ButtonGroup();
   
      constraints.gridx = 1;
      constraints.gridy = 2;
      constraints.insets = new Insets (2, 3, 2, 3);
      thisLabel = new JLabel("Baud(bps)");
      thisLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      add(thisLabel, constraints);

      Box baudRateBox = new Box(BoxLayout.Y_AXIS);
      baudRateButtons = new ArrayList<JRadioButton> (baudRates.length);
      for (int i = 0; i < baudRates.length; i++) {
         thisButton = new JRadioButton ((String) baudRates[i][0]);
         thisButton.setToolTipText ("Transmit and receive at " + (String) baudRates[i][0] + " bits per second.");
         baudRateGroup.add(thisButton);
         baudRateButtons.add (thisButton);
         baudRateBox.add(thisButton);
         testValue = (Integer) baudRates[i][1];
         if (testValue.intValue() == SerialInfo.BAUDRATE_DEFAULT) {
            thisButton.setSelected (true);
         }
      }
      constraints.gridx = 1;
      constraints.gridy = 3;
      constraints.insets = new Insets (0, 0, 0, 0);
      add(baudRateBox, constraints);
   
      constraints.gridx = 2;
      constraints.gridy = 2;
      constraints.insets = new Insets (2, 3, 2, 3);
      thisLabel = new JLabel("Data bits");
      thisLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      add(thisLabel, constraints);

      Box dataBitsBox = new Box(BoxLayout.Y_AXIS);
      dataBitsButtons = new ArrayList<JRadioButton> (dataBits.length);
      for (int i = 0; i < dataBits.length; i++) {
         thisButton = new JRadioButton ((String) dataBits[i][0]);
         thisButton.setToolTipText ((String) dataBits[i][0] + " data bits per block.");
         dataBitsGroup.add(thisButton);
         dataBitsButtons.add (thisButton);
         dataBitsBox.add(thisButton);
         testValue = (Integer) dataBits[i][1];
         if (testValue.intValue() == SerialInfo.DATABITS_DEFAULT) {
            thisButton.setSelected (true);
         }
      }
      constraints.gridx = 2;
      constraints.gridy = 3;
      constraints.insets = new Insets (0, 0, 0, 0);
      add(dataBitsBox, constraints);
   
      constraints.gridx = 3;
      constraints.gridy = 2;
      constraints.insets = new Insets (2, 3, 2, 3);
      thisLabel = new JLabel("Parity");
      thisLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      add(thisLabel, constraints);

      Box parityBox = new Box(BoxLayout.Y_AXIS);
      parityButtons = new ArrayList<JRadioButton> (parity.length);
      for (int i = 0; i < parity.length; i++) {
         thisButton = new JRadioButton ((String) parity[i][0]);
         thisButton.setToolTipText ((String) parity[i][0] + " parity.");
         parityGroup.add(thisButton);
         parityButtons.add (thisButton);
         parityBox.add(thisButton);
         testValue = (Integer) parity[i][1];
         if (testValue.intValue() == SerialInfo.PARITY_DEFAULT) {
            thisButton.setSelected (true);
         }
      }
      constraints.gridx = 3;
      constraints.gridy = 3;
      constraints.insets = new Insets (0, 0, 0, 0);
      add(parityBox, constraints);
   
   
      constraints.gridx = 4;
      constraints.gridy = 2;
      constraints.insets = new Insets (2, 3, 2, 3);
      thisLabel = new JLabel("Stop bits");
      thisLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      add(thisLabel, constraints);

      Box stopBitsBox = new Box(BoxLayout.Y_AXIS);
      stopBitsButtons = new ArrayList<JRadioButton> (stopBits.length);
      for (int i = 0; i < stopBits.length; i++) {
         thisButton = new JRadioButton ((String) stopBits[i][0]);
         thisButton.setToolTipText ((String) stopBits[i][0] + " stop bits per block.");
         stopBitsGroup.add(thisButton);
         stopBitsButtons.add (thisButton);
         stopBitsBox.add(thisButton);
         testValue = (Integer) stopBits[i][1];
         if (testValue.intValue() == SerialInfo.STOPBITS_DEFAULT) {
            thisButton.setSelected (true);
         }
      }
      constraints.gridx = 4;
      constraints.gridy = 3;
      constraints.insets = new Insets (0, 0, 0, 0);
      add(stopBitsBox, constraints);
   
   
      constraints.gridx = 5;
      constraints.gridy = 2;
      constraints.insets = new Insets (2, 3, 2, 3);
      thisLabel = new JLabel("Flow control");
      thisLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      add(thisLabel, constraints);

      Box flowControlBox = new Box(BoxLayout.Y_AXIS);
      flowControlButtons = new ArrayList<JRadioButton> (flowControl.length);
      for (int i = 0; i < flowControl.length; i++) {
         thisButton = new JRadioButton ((String) flowControl[i][0]);
         thisButton.setToolTipText ((String) flowControl[i][0] + " flow control.");
         flowControlGroup.add(thisButton);
         flowControlButtons.add (thisButton);
         flowControlBox.add(thisButton);
         testValue = (Integer) flowControl[i][1];
         if (testValue.intValue() == SerialInfo.FLOWCONTROL_DEFAULT) {
            thisButton.setSelected (true);
         }
      }
      constraints.gridx = 5;
      constraints.gridy = 3;
      constraints.insets = new Insets (0, 0, 0, 0);
      add(flowControlBox, constraints);
   
      
      okButton = new JButton ("Ok");
      okButton.setToolTipText ("Open serial port with selected parameters.");
      okButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               getSerialPortParams ();
               dialog.setVisible (false);
            }
         });
      constraints.gridx = 2;
      constraints.gridy = 4;
      constraints.insets = new Insets (0, 0, 0, 0);
      add(okButton, constraints);
      
      cancelButton = new JButton ("Cancel");
      cancelButton.setToolTipText ("Cancel decsion to open a serial port.");
      cancelButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               portParams = null;
               dialog.setVisible (false);
            }
         });
      constraints.gridx = 3;
      constraints.gridy = 4;
      constraints.insets = new Insets (0, 0, 0, 0);
      add(cancelButton, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.gridwidth = 5;
      constraints.insets = new Insets (2, 3, 2, 3);
      thisLabel = new JLabel("Digital interface type");
      thisLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      add(thisLabel, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 1;
      Integer intefaceTypeID;
      int interfaceTypeIDValue;
      Vector<DigitalInterfaceTypeItem> intefaceItemList = new Vector<DigitalInterfaceTypeItem>();
      for (int i = 0; i < interfaceTypes.length; i++) {
         intefaceTypeID = (Integer) interfaceTypes[i][1];
         interfaceTypeIDValue = intefaceTypeID.intValue ();
         intefaceItemList.add (new DigitalInterfaceTypeItem(interfaceTypeIDValue));
      }
      interfaceTypeCombo = new JComboBox(intefaceItemList);
      interfaceTypeCombo.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               DigitalInterfaceTypeItem selectedInterface = 
                  (DigitalInterfaceTypeItem) interfaceTypeCombo.getSelectedItem ();
               if (selectedInterface.getInterface () == SerialInfo.NMEA_0183) {
                  JRadioButton button;
                  button = (JRadioButton) baudRateButtons.get (4);
                  button.setSelected (true);
                  button = (JRadioButton) dataBitsButtons.get (3);
                  button.setSelected (true);
                  button = (JRadioButton) parityButtons.get (0);
                  button.setSelected (true);
                  button = (JRadioButton) stopBitsButtons.get (0);
                  button.setSelected (true);
               }
               else if (selectedInterface.getInterface () == SerialInfo.NMEA_0183_HS) {
                  JRadioButton button;
                  button = (JRadioButton) baudRateButtons.get (7);
                  button.setSelected (true);
                  button = (JRadioButton) dataBitsButtons.get (3);
                  button.setSelected (true);
                  button = (JRadioButton) parityButtons.get (0);
                  button.setSelected (true);
                  button = (JRadioButton) stopBitsButtons.get (0);
                  button.setSelected (true);
               }
               else if (selectedInterface.getInterface () == SerialInfo.NMEA_0182) {
                  JRadioButton button;
                  button = (JRadioButton) baudRateButtons.get (2);
                  button.setSelected (true);
                  button = (JRadioButton) dataBitsButtons.get (3);
                  button.setSelected (true);
                  button = (JRadioButton) parityButtons.get (1);
                  button.setSelected (true);
                  button = (JRadioButton) stopBitsButtons.get (2);
                  button.setSelected (true);
               }
               else if (selectedInterface.getInterface () == SerialInfo.RAYNAV750) {
                  JRadioButton button;
                  button = (JRadioButton) baudRateButtons.get (2);
                  button.setSelected (true);
                  button = (JRadioButton) dataBitsButtons.get (3);
                  button.setSelected (true);
                  button = (JRadioButton) parityButtons.get (0);
                  button.setSelected (true);
                  button = (JRadioButton) stopBitsButtons.get (2);
                  button.setSelected (true);
               }
            }
         });
      add(interfaceTypeCombo, constraints);
      
   }
   
   void getSerialPortParams () {
      /* String portName; */
      int ic = SerialInfo.INTERFACE_DEFAULT;
      Integer bd = new Integer(SerialInfo.BAUDRATE_DEFAULT);
      Integer db = new Integer(SerialInfo.DATABITS_DEFAULT);
      Integer par = new Integer(SerialInfo.PARITY_DEFAULT);
      Integer sb = new Integer(SerialInfo.STOPBITS_DEFAULT);
      Integer fc = new Integer(SerialInfo.FLOWCONTROL_DEFAULT);
      
      DigitalInterfaceTypeItem selectedInterface = 
                  (DigitalInterfaceTypeItem) interfaceTypeCombo.getSelectedItem ();
      ic = selectedInterface.getInterface ();
      
      int index;
      boolean buttonFound;
     
      index = 0;
      buttonFound = false;
      while ((!buttonFound) && (index < baudRates.length)) {
         thisButton = (JRadioButton) baudRateButtons.get (index);
         if (thisButton.isSelected ()) {
            buttonFound = true;
            bd = (Integer) baudRates[index][1];
         }
         index++;
      }
     
      index = 0;
      buttonFound = false;
      while ((!buttonFound) && (index < dataBits.length)) {
         thisButton = (JRadioButton) dataBitsButtons.get (index);
         if (thisButton.isSelected ()) {
            buttonFound = true;
            db = (Integer) dataBits[index][1];
         }
         index++;
      }
           
      index = 0;
      buttonFound = false;
      while ((!buttonFound) && (index < parity.length)) {
         thisButton = (JRadioButton) parityButtons.get (index);
         if (thisButton.isSelected ()) {
            buttonFound = true;
            par = (Integer) parity[index][1];
         }
         index++;
      }
           
      index = 0;
      buttonFound = false;
      while ((!buttonFound) && (index < stopBits.length)) {
         thisButton = (JRadioButton) stopBitsButtons.get (index);
         if (thisButton.isSelected ()) {
            buttonFound = true;
            sb = (Integer) stopBits[index][1];
         }
         index++;
      }
           
      index = 0;
      buttonFound = false;
      while ((!buttonFound) && (index < flowControl.length)) {
         thisButton = (JRadioButton) flowControlButtons.get (index);
         if (thisButton.isSelected ()) {
            buttonFound = true;
            fc = (Integer) flowControl[index][1];
         }
         index++;
      }
     
      portParams = new SerialPortInfo((String) portNameCombo.getSelectedItem ());
      portParams.setSerialPortParameters (
         ic, 
         bd.intValue (),
         db.intValue (),
         par.intValue (),
         sb.intValue (), 
         fc.intValue ());
   }

   /**
    * Displays a dialog box of this panel.
    *
    * @param parentFrame The application frame for this application.
    * @return The serial port to open, and its initial parameters.
    */
   public SerialPortInfo showDialog (JFrame parentFrame) {
      
      dialog = new JDialog (parentFrame, "Set Serial Port Parameters", true);
      dialog.getContentPane().add(this);
      dialog.getRootPane().setDefaultButton (okButton);
      dialog.pack();

      Dimension screenSize =
         Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getPreferredSize();
      dialog.setLocation(screenSize.width/2 - (dialogSize.width/2),
                  screenSize.height/2 - (dialogSize.height/2));
 
      dialog.setVisible(true);

      return portParams;
   }
   
   public class DigitalInterfaceTypeItem extends Object {
      int id;
      
      public DigitalInterfaceTypeItem (int digitalInterface) {
         id = digitalInterface;
      }
      
      public String toString() {
         return SerialInfo.getDigitalInterface (id);
      }
      
      public int getInterface () {
         return id;
      }
   }
}
