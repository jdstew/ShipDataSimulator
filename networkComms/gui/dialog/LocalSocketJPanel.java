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
import java.util.*;
import java.net.*;
/**
 *
 * @author Stewarts
 */
public class LocalSocketJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	static final int STEP_ONE = 1;
   static final int STEP_TWO = 2;
   static final int STEP_THREE = 3;
   
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
   JPanel stepThreeJPanel;
   
   JFormattedTextField portField;
   JFormattedTextField ipAddressField;
   
   JComboBox networkInterfacesCombo;
   JComboBox internetAddressesCombo;
   
   GridBagConstraints constraints;
   
   /** Creates a new instance of HostSocketJPanel */
   public LocalSocketJPanel (UDPServerParameters params) {
      
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
            } else if (currentStep == STEP_THREE) {
               processStepThree ();
               setupStepTwo ();
            }
         }
      });
      
      nextButton = new JButton ("Next >");
      nextButton.setToolTipText ("Go to the next step.");
      nextButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            if (currentStep == STEP_ONE) {
               processStepOne ();
               setupStepTwo ();
            } else if (currentStep == STEP_TWO) {
               processStepTwo ();
               setupStepThree ();
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
      
      finishButton = new JButton ("Done");
      finishButton.setToolTipText ("Apply the host socket settings.");
      finishButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            if (processStepThree ()) {
               dialog.setVisible (false);
            } else {
               setupStepThree ();
            }
         }
      });
      
      networkInterfacesCombo = new JComboBox ();
      internetAddressesCombo = new JComboBox ();
      
      stepNumber = new JLabel ("Step 1 of 3:  Select network interface.");
      
      setBorder (BorderFactory.createTitledBorder ("Set local host socket"));
      //      setLayout (new GridBagLayout ());
      setLayout (new BorderLayout ());
      this.setPreferredSize (new Dimension (600,175));
      
      constraints = new GridBagConstraints ();
      constraints.anchor = GridBagConstraints.WEST;
      
      add (stepNumber, BorderLayout.NORTH);
      
      
      stepOneJPanel = new StepOneJPanel ();    
      stepTwoJPanel = new StepTwoJPanel ();      
      stepThreeJPanel = new StepThreeJPanel ();
      
      Box buttonBox = new Box (BoxLayout.X_AXIS);
      buttonBox.add (Box.createHorizontalGlue ());
      buttonBox.add (backButton);
      buttonBox.add (Box.createHorizontalStrut(10));
      buttonBox.add (nextButton);
      buttonBox.add (Box.createHorizontalStrut(10));
      buttonBox.add (cancelButton);
      buttonBox.add (Box.createHorizontalStrut(10));
      buttonBox.add (finishButton);
      
      add (buttonBox, BorderLayout.SOUTH);
      setupStepOne ();
   }
   
   public void setupStepOne () { // select network interface
      backButton.setEnabled (false);
      nextButton.setEnabled (true);
      cancelButton.setEnabled (true);
      finishButton.setEnabled (false);
      stepNumber.setText ("Step 1 of 3:  Select network interface.");
      currentStep = STEP_ONE;
      
      networkInterfacesCombo.removeAllItems ();
      try {
         Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces ();
         while (networkInterfaces.hasMoreElements ()) {
            CustomNI ni = new CustomNI ((NetworkInterface) networkInterfaces.nextElement ());
            networkInterfacesCombo.addItem (ni);
         }
      } catch (SocketException e) {
         System.out.println (e);
      }
      
      add (stepOneJPanel, BorderLayout.CENTER);
      repaint ();
   }
   
   public void processStepOne () { // select network interface
      remove (stepOneJPanel);
      repaint ();
      
      CustomNI ni = (CustomNI) networkInterfacesCombo.getSelectedItem ();
      serverParameters.setNetworkInterface (ni.getNetworkInterface ());
   }
   
   public void setupStepTwo () { // select IP address
      backButton.setEnabled (true);
      nextButton.setEnabled (true);
      cancelButton.setEnabled (true);
      finishButton.setEnabled (false);
      stepNumber.setText ("Step 2 of 3:  Select local host internet address.");
      currentStep = STEP_TWO;
      
      internetAddressesCombo.removeAllItems ();
      
      NetworkInterface networkInterface = serverParameters.getNetworkInterface ();
      if (networkInterface != null) {
         Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses ();
         while (inetAddresses.hasMoreElements ()) {
            CustomIA ia = new CustomIA ((InetAddress) inetAddresses.nextElement ());
            internetAddressesCombo.addItem (ia);
         }
      } else {
         System.out.println ("no network interface!!!");
      }
      
      add (stepTwoJPanel, BorderLayout.CENTER);
      repaint ();
   }
   
   public void processStepTwo () { // select IP address
      remove (stepTwoJPanel);
      repaint ();
      
      CustomIA ia = (CustomIA) internetAddressesCombo.getSelectedItem ();
      if (ia != null) {
         serverParameters.setLocalInternetAddress (ia.getInetAddress ());
      }
   }
   
   public void setupStepThree () { // select port
      backButton.setEnabled (true);
      nextButton.setEnabled (false);
      cancelButton.setEnabled (true);
      finishButton.setEnabled (true);
      stepNumber.setText ("Step 3 of 3:  Select socket port number.");
      currentStep = STEP_THREE;
      
      add (stepThreeJPanel, BorderLayout.CENTER);
      repaint ();
   }
   
   public boolean processStepThree () { // select port
      remove (stepThreeJPanel);
      repaint ();
      
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
      serverParameters.setLocalPort (socketInt);
      return true;
   }
   
   /**
    * Displays a dialog box of this panel.
    *
    * @param parentFrame The application frame for this application.
    * @return The time and date to set the simulator.
    */
   public UDPServerParameters showDialog (JFrame frame) {
      
      dialog = new JDialog (frame, "UDP Server Setup (Part I of III)", true);
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
         Box stepOneBox;
         stepOneBox = new Box (BoxLayout.X_AXIS);
         stepOneBox.setAlignmentX (Component.LEFT_ALIGNMENT);
         stepOneBox.add (new JLabel ("Network interface: "));
         stepOneBox.add (networkInterfacesCombo);
         add (stepOneBox);
      }
   }
   
   private class StepTwoJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public StepTwoJPanel () {
//         setLayout (new GridBagLayout ());
         
//         GridBagConstraints localConstraints = new GridBagConstraints ();
//         
//         localConstraints.gridy = 0;
//         localConstraints.gridx = 1;
//         add (internetAddressesCombo, localConstraints);
         Box stepTwoBox;
         stepTwoBox = new Box (BoxLayout.X_AXIS);
         stepTwoBox.setAlignmentX (Component.LEFT_ALIGNMENT);
         stepTwoBox.add (new JLabel ("Address: "));
         stepTwoBox.add (internetAddressesCombo);
         add (stepTwoBox);
      }
   }
   
   private class StepThreeJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public StepThreeJPanel () {         
         try {
            portField = new JFormattedTextField (new MaskFormatter ("#####"));
         } catch (java.text.ParseException exc) {
            System.err.println ("formatter is bad: " + exc.getMessage ());
         }
         
         portField.setValue (new String ("06040"));
         portField.setColumns (5);
         
         Box stepThreeBox;
         stepThreeBox = new Box (BoxLayout.X_AXIS);
         stepThreeBox.setAlignmentX (Component.LEFT_ALIGNMENT);
         stepThreeBox.add (new JLabel ("Port: "));
         stepThreeBox.add (portField);
         add (stepThreeBox);
      }
   }
   
   private class CustomNI extends Object {
      NetworkInterface ni;
      
      public CustomNI (NetworkInterface networkInterface) {
         if (networkInterface != null) {
            ni = networkInterface;
         }
      }
      
      public String toString () {
         return " " + ni.getName () + " : " + ni.getDisplayName ();
      }
      
      public NetworkInterface getNetworkInterface () {
         return ni;
      }
   }
   
   private class CustomIA extends Object {
      InetAddress ia;
      
      public CustomIA (InetAddress inetAddress) {
         if (inetAddress != null) {
            ia = inetAddress;
         }
      }
      
      public String toString () {
         return " " + ia.getHostName () + " : " + ia.getHostAddress ();
      }
      
      public InetAddress getInetAddress () {
         return ia;
      }
   }
   
   /**
    * Starts the application.
    *
    * @param arg read but not used.
    */
   public static void main (String [] arg){
      JFrame frame = new JFrame ();
      LocalSocketJPanel panel = new LocalSocketJPanel (null);
      frame.getContentPane().add (panel);
      frame.pack ();
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      frame.setVisible (true);
   }
}
