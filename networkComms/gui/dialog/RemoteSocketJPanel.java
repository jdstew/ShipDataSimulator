/*
 * HostSocketJPanel.java
 *
 * Created on April 26, 2005, 9:29 PM
 */

package networkComms.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.text.*;
import java.net.*;
/**
 *
 * @author Stewarts
 */
public class RemoteSocketJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	static final int STEP_ONE = 1;
   static final int STEP_TWO = 2;
   
   int currentStep;
   UDPServerParameters serverParameters;
   
   JButton backButton;
   JButton nextButton;
   JButton cancelButton;
   JButton finishButton;
   JLabel stepNumber;
   JDialog dialog;
   
   JFrame parentFrame;
   JPanel stepOneJPanel;
   JPanel stepTwoJPanel;
   
   JFormattedTextField portField;
   JFormattedTextField udpIPAddressField;
   JFormattedTextField multicastIPAddressField;
   
   GridBagConstraints constraints;
   
   /** Creates a new instance of HostSocketJPanel */
   public RemoteSocketJPanel (UDPServerParameters params) {
      
      currentStep = STEP_ONE;
      
      if (params != null) {
         serverParameters = params;
      }
      else {
         serverParameters = new UDPServerParameters ();
      }
      
      backButton = new JButton ("< Back");
      backButton.setToolTipText ("Go back to the previous step.");
      backButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            if (currentStep == STEP_TWO) {
               processStepTwo ();
               setupStepOne ();
            }
         }
      });
      
      nextButton = new JButton ("Next >");
      nextButton.setToolTipText ("Go to the next step.");
      nextButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            if (currentStep == STEP_ONE) {
               if (processStepOne ()) {
                  setupStepTwo ();
               }
               else {
                  setupStepOne ();
               }
            }
         }
      });
      
      cancelButton = new JButton ("Cancel");
      cancelButton.setToolTipText ("Cancel setting the host socket.");
      cancelButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            serverParameters = null;
            dialog.setVisible (false);
         }
      });
      
      finishButton = new JButton ("Finsh");
      finishButton.setToolTipText ("Apply the host socket settings.");
      finishButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            if (processStepTwo ()) {
               dialog.setVisible (false);
            } else {
               setupStepTwo ();
            }
         }
      });
      
      stepNumber = new JLabel ("Step 1 of 2:  Select remote host internet address.");
      
      setBorder (BorderFactory.createTitledBorder ("Set remote host receiving socket"));
      //      setLayout (new GridBagLayout ());
      setLayout (new BorderLayout ());
      this.setPreferredSize (new Dimension (600,175));
      
      constraints = new GridBagConstraints ();
      constraints.anchor = GridBagConstraints.WEST;
      
      add (stepNumber, BorderLayout.NORTH);
      
      
      stepOneJPanel = new StepOneJPanel ();
      stepTwoJPanel = new StepTwoJPanel ();
      
      Box buttonBox = new Box (BoxLayout.X_AXIS);
      buttonBox.add (Box.createHorizontalGlue ());
      buttonBox.add (backButton);
      buttonBox.add (Box.createHorizontalStrut (10));
      buttonBox.add (nextButton);
      buttonBox.add (Box.createHorizontalStrut (10));
      buttonBox.add (cancelButton);
      buttonBox.add (Box.createHorizontalStrut (10));
      buttonBox.add (finishButton);
      
      add (buttonBox, BorderLayout.SOUTH);
      setupStepOne ();
   }
   
   public void setupStepOne () { // select IP address
      backButton.setEnabled (false);
      nextButton.setEnabled (true);
      cancelButton.setEnabled (true);
      finishButton.setEnabled (false);
      stepNumber.setText ("Step 1 of 2:  Select remote host internet address.");
      currentStep = STEP_ONE;
      
      add (stepOneJPanel, BorderLayout.CENTER);
      repaint ();
   }
   
   public boolean processStepOne () { // select IP address
      remove (stepOneJPanel);
      repaint ();
      
      try {
         udpIPAddressField.commitEdit ();
      }
      catch (ParseException e) {
         
      }
      
      String ipText = (String) udpIPAddressField.getValue ();
      InetAddress internetAddress;
      try {
         internetAddress = InetAddress.getByName (ipText);
      } catch (UnknownHostException e) {
         String msgString = new String (
         "The address typed in is unrecognizeable.\n" );
         
         JOptionPane.showMessageDialog (parentFrame, msgString,
         "Invalid address", JOptionPane.ERROR_MESSAGE);
         return false;
      }
      
      serverParameters.setRemoteInternetAddress (internetAddress);
      return true;
   }
   
   public void setupStepTwo () { // select port
      backButton.setEnabled (true);
      nextButton.setEnabled (false);
      cancelButton.setEnabled (true);
      finishButton.setEnabled (true);
      stepNumber.setText ("Step 2 of 2:  Select socket port number.");
      currentStep = STEP_TWO;
      
      add (stepTwoJPanel, BorderLayout.CENTER);
      repaint ();
   }
   
   public boolean processStepTwo () { // select port
      remove (stepTwoJPanel);
      repaint ();
      
      try {
         portField.commitEdit ();
      }
      catch (ParseException e) {
         
      }
      
      
      String socketText = (String) portField.getValue ();
      int socketInt = Integer.parseInt (socketText);
      
      if ( (socketInt < 0) || (socketInt > 65536)) {
         String msgString = new String (
         "The port you entered is invalid.\n\n " +
         "Valid port range is \n from 0 to 65536.\n" );
         
         JOptionPane.showMessageDialog (parentFrame, msgString,
         "Invalid Port Number", JOptionPane.ERROR_MESSAGE);
         return false;
      }
      serverParameters.setRemotePort (socketInt);
      return true;
   }
   
   /**
    * Displays a dialog box of this panel.
    *
    * @param parentFrame The application frame for this application.
    * @return The time and date to set the simulator.
    */
   public UDPServerParameters showDialog (JFrame frame) {
      parentFrame = frame;
      
      dialog = new JDialog (frame, "UDP Server Setup (Part II of II)", true);
      dialog.getContentPane ().add (this);
      dialog.getRootPane ().setDefaultButton (finishButton);
      dialog.pack ();
      
      Dimension screenSize =
      Toolkit.getDefaultToolkit ().getScreenSize ();
      Dimension dialogSize = dialog.getPreferredSize ();
      dialog.setLocation (screenSize.width/2 - (dialogSize.width/2),
      screenSize.height/2 - (dialogSize.height/2));
      
      dialog.setVisible (true);
      return serverParameters;
   }
   
   private class StepOneJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public StepOneJPanel () {
//         setLayout (new GridBagLayout ());
         
//         GridBagConstraints localConstraints = new GridBagConstraints ();
         
         try {
            udpIPAddressField = new JFormattedTextField (new MaskFormatter ("###.###.###.###"));
         } catch (java.text.ParseException exc) {
            System.err.println ("formatter is bad: " + exc.getMessage ());
         }
         
         StringBuffer ipAddressText = new StringBuffer ();
         byte [] ipAddressBytes = serverParameters.getRemoteInternetAddress ().getAddress ();
         DecimalFormat byteForm = new DecimalFormat ("000");
         for (int i = 0; i < ipAddressBytes.length; i++) {
            if (i > 0) {
               ipAddressText.append (".");
            }
            long thisByteValue = (long)ipAddressBytes[i];
            if (thisByteValue < 0) {
               thisByteValue += 256;
            }
            ipAddressText.append (byteForm.format ( thisByteValue ));
         }
         
         System.out.println ("ipAddressText=" + ipAddressText);
         udpIPAddressField.setValue (ipAddressText.toString ());
         
         
         udpIPAddressField.setColumns (12);
//         localConstraints.gridy = 0;
//         localConstraints.gridx = 1;
//         add (udpIPAddressField, localConstraints);
         
         Box stepOneBox;
         stepOneBox = new Box (BoxLayout.X_AXIS);
         stepOneBox.setAlignmentX (Component.LEFT_ALIGNMENT);
         stepOneBox.add (new JLabel ("Address: "));
         stepOneBox.add (udpIPAddressField);
         add (stepOneBox);
      }
   }
   
   private class StepTwoJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public StepTwoJPanel () {
         try {
            portField = new JFormattedTextField (new MaskFormatter ("#####"));
         } catch (java.text.ParseException exc) {
            System.err.println ("formatter is bad: " + exc.getMessage ());
         }
         
         int portNumber = serverParameters.getRemotePort ();
         System.out.println ("portNumbe=" + portNumber);
         if ( (portNumber > 0) && (portNumber < 65536) ) {
            DecimalFormat portForm = new DecimalFormat ("00000");
            portField.setValue (portForm.format (portNumber));
         }
         else {
            portField.setValue (new String ("06041"));
         }
         portField.setColumns (5);
         
         Box stepThreeBox;
         stepThreeBox = new Box (BoxLayout.X_AXIS);
         stepThreeBox.setAlignmentX (Component.LEFT_ALIGNMENT);
         stepThreeBox.add (new JLabel ("Port: "));
         stepThreeBox.add (portField);
         add (stepThreeBox);
      }
   }
   
   /**
    * Starts the application.
    *
    * @param arg read but not used.
    */
   public static void main (String [] arg){
      JFrame frame = new JFrame ();
      RemoteSocketJPanel panel = new RemoteSocketJPanel (null);
      frame.getContentPane ().add (panel);
      frame.pack ();
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      frame.setVisible (true);
   }
}
