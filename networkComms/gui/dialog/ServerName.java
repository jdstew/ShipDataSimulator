/*
 * HostSocketJPanel.java
 *
 * Created on April 26, 2005, 9:29 PM
 */

package networkComms.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author Stewarts
 */
public class ServerName extends JPanel {

	private static final long serialVersionUID = 1L;

	UDPServerParameters serverParameters;
   
   JButton backButton;
   JButton nextButton;
   JButton cancelButton;
   JButton finishButton;
   JLabel stepNumber;
   JDialog dialog;
   
   JTextField serverNameTextField;
   
   GridBagConstraints constraints;
   
   /** Creates a new instance of HostSocketJPanel */
   public ServerName (UDPServerParameters params) {
      
      if (params != null) {
         serverParameters = params;
      }
      else {
         serverParameters = new UDPServerParameters ();
      }
      
      backButton = new JButton ("< Back");
      backButton.setEnabled (false);
      backButton.setToolTipText ("Go back to the previous step.");
//      backButton.addActionListener (new ActionListener () {
//         public void actionPerformed (ActionEvent event) {
//         }
//      });
      
      nextButton = new JButton ("Next >");
      nextButton.setEnabled (false);
      nextButton.setToolTipText ("Go to the next step.");
//      nextButton.addActionListener (new ActionListener () {
//         public void actionPerformed (ActionEvent event) {
//         }
//      });
      
      cancelButton = new JButton ("Cancel");
      cancelButton.setEnabled (true);
      cancelButton.setToolTipText ("Cancel setting the host socket.");
      cancelButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            serverParameters = null;
            dialog.setVisible (false);
         }
      });
      
      finishButton = new JButton ("Done");
      finishButton.setEnabled (true);
      finishButton.setToolTipText ("Apply the host socket settings.");
      finishButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            if (serverNameTextField.getText ().length () > 0) {
               serverParameters.setConnectionName (serverNameTextField.getText ());
               dialog.setVisible (false);
            }
            else {
               serverNameTextField.setText ("enter server name here");
            }
         }
      });

      
      stepNumber = new JLabel ("Step 1 of 1:  Enter server Name.");
      
      setBorder (BorderFactory.createTitledBorder ("Set server name"));
      //      setLayout (new GridBagLayout ());
      setLayout (new BorderLayout ());
//      this.setPreferredSize (new Dimension (600,175));
      
      constraints = new GridBagConstraints ();
      constraints.anchor = GridBagConstraints.WEST;
      
      add (stepNumber, BorderLayout.NORTH);
      
      serverNameTextField = new JTextField ();
      serverNameTextField.setColumns (15);
      if (serverParameters.getConnectionName () != null) {
         serverNameTextField.setText (serverParameters.getConnectionName ());
      }
      else {
         serverNameTextField.setText ("UDP Server");
      }

      Box dataBoxOutside = new Box (BoxLayout.Y_AXIS);
      dataBoxOutside.add(Box.createVerticalGlue ());
         Box dataBoxInside = new Box (BoxLayout.X_AXIS);
         dataBoxInside.add(new JLabel("Server name: "));
         dataBoxInside.add(serverNameTextField);
      dataBoxOutside.add(dataBoxInside);
      dataBoxOutside.add(Box.createVerticalGlue ());
      add (dataBoxOutside, BorderLayout.CENTER);
      
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
   }
   
   /**
    * Displays a dialog box of this panel.
    *
    * @param parentFrame The application frame for this application.
    * @return The time and date to set the simulator.
    */
   public UDPServerParameters showDialog (JFrame frame) {
      
      dialog = new JDialog (frame, "UDP Server Setup (Part III of III)", true);
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
     
   /**
    * Starts the application.
    *
    * @param arg read but not used.
    */
   public static void main (String [] arg){
      JFrame frame = new JFrame ();
      ServerName panel = new ServerName (null);
      UDPServerParameters params = panel.showDialog (frame);
      System.out.println (params.getConnectionName ());
//      frame.getContentPane().add (panel);
//      frame.pack ();
//      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
//      frame.setVisible (true);
   }
}
