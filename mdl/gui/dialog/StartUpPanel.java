/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: StartUpPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import mdl.gui.*;
/**
 * This panel is used to setup the application for the user.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class StartUpPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	/** Reference to start application without any setup. */
   public static final int START_APPLICATION = 0;
   /** Reference to start application using the simulator. */
   public static final int START_SIMULATOR = 1;
   /** Reference to start application using a serial port. */
   public static final int START_SERIAL_PORT = 2;
   
   int startUpMode = 0;
   
   JButton startSimulator;
   JButton openSerialPort;
   JButton startApplication;
   
   JDialog dialog;
   
   /** 
    * Initializes an object of this class.
    */
   public StartUpPanel () {
      setBorder(BorderFactory.createTitledBorder("Select setup process."));
      GridBagConstraints constraints = new GridBagConstraints ();
      constraints.fill = GridBagConstraints.BOTH;
      constraints.anchor = GridBagConstraints.CENTER;
      setLayout (new GridBagLayout());
    
      URL fileLoc = ApplicationFrame.class.getResource("images/mdl.gif");
      ImageIcon mdlImageIcon = null;
      if (fileLoc != null) {
            mdlImageIcon = new ImageIcon(fileLoc);
      }
      
      Dimension mdlDimension = new Dimension (mdlImageIcon.getIconWidth (),
         mdlImageIcon.getIconHeight ());
      JLabel appImageLabel = new JLabel(mdlImageIcon);
      appImageLabel.setPreferredSize (mdlDimension);
      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.gridwidth = 2;
      constraints.insets = new Insets (4, 6, 4, 6);
      add(appImageLabel, constraints);
            
      constraints.gridx = 0;
      constraints.gridy = 1;
      constraints.gridwidth = 1;
      fileLoc = ApplicationFrame.class.getResource("images/simulator.gif");  
      startSimulator = new JButton("Start ship simulation",
         new ImageIcon(fileLoc));
      startSimulator.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent event) {
               startUpMode = StartUpPanel.START_SIMULATOR;
               dialog.setVisible (false);
            }
         });      
      add (startSimulator, constraints);

      constraints.gridx = 1;
      constraints.gridy = 1;
      JTextArea simulatorTextArea = new JTextArea(4, 25);
      simulatorTextArea.setEditable(false);
      simulatorTextArea.setLineWrap (true);
      simulatorTextArea.setWrapStyleWord (true);
      simulatorTextArea.setBackground ((Color) UIManager.get("Panel.background"));
      simulatorTextArea.setText (
         "The ship simulator can be used to generate dynamic test data " +
         "sentences, which can be transmitted to a navigation system via " +
         "serial communication ports.");
      simulatorTextArea.setPreferredSize (new Dimension (320, 60));
      add (simulatorTextArea, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 2;
      fileLoc = ApplicationFrame.class.getResource("images/rcvAndTest.gif");  
      openSerialPort = new JButton("Open a Serial Port",
         new ImageIcon(fileLoc));
      openSerialPort.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent event) {
               startUpMode = StartUpPanel.START_SERIAL_PORT;
               dialog.setVisible (false);
            }
         });
       add (openSerialPort, constraints);

      constraints.gridx = 1;
      constraints.gridy = 2;
      JTextArea openPortTextArea = new JTextArea(4, 25);
      openPortTextArea.setEditable(false);
      openPortTextArea.setLineWrap (true);
      openPortTextArea.setWrapStyleWord (true);
      openPortTextArea.setBackground ((Color) UIManager.get("Panel.background"));
      openPortTextArea.setText (
         "Serial communications ports can be opened to view and process " +
         "received data, and transmit either simulated dynamic data or manually " +
         "entered static data.");
      openPortTextArea.setPreferredSize (new Dimension (320, 60));
      add (openPortTextArea, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 3;
      startApplication = new JButton("Start Neither");
      startApplication.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent event) {
               startUpMode = StartUpPanel.START_APPLICATION;
               dialog.setVisible (false);
            }
         });
      add (startApplication, constraints);
   }

   /**
    * Displays a dialog box of this panel.
    *
    * @param parentFrame The application frame for this application.
    * @return The code to indicate the path of desired initialization steps.
    */
   public int showDialog (JFrame parentFrame) {
      startUpMode = 0;
      
      dialog = new JDialog (parentFrame, "Start MDL", true);
      dialog.getContentPane().add(this);
      dialog.getRootPane().setDefaultButton (startSimulator);

      Dimension screenSize =
         Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getPreferredSize();
      dialog.setSize (dialogSize);
      dialog.setLocation(screenSize.width/2 - (dialogSize.width/2),
                  screenSize.height/2 - (dialogSize.height/2));
      
      dialog.pack();
      
      dialog.setVisible(true);
      return startUpMode;
   }
   
    
//   public static void main (String [] arg){
//      JFrame frame = new JFrame();
//      StartUpPanel startUpPanel = new StartUpPanel();
//      Container contentPane = frame.getContentPane ();
//      contentPane.add (startUpPanel);
////      frame.setSize (startUpPanel.getPreferredSize ().width, 
////         startUpPanel.getPreferredSize ().height);
//      frame.pack();
//      frame.show ();
//      
////      startUpPanel.showDialog (testFrame);
////      testFrame.dispose ();
//   }
}
