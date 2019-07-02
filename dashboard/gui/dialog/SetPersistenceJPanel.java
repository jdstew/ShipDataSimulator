/*
 * SetPersistenceJPanel.java
 *
 * Created on June 20, 2005, 8:06 PM
 */

package dashboard.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.text.*;
import dashboard.*;
/**
 *
 * @author  Stewarts
 */
public class SetPersistenceJPanel extends JPanel {
   
	private static final long serialVersionUID = -3554108586448970962L;
	JFrame parentFrame;
   JDialog dialog;
   
   JButton cancelButton;
   JButton okButton;
   
   JFormattedTextField valueField;
   
   long newPersistence; // in seconds
   
   /** Creates a new instance of SetPersistenceJPanel */
   public SetPersistenceJPanel (long persistence) {
      newPersistence = -1;
      
      setBorder(BorderFactory.createEmptyBorder (10, 10, 10, 10));
      
      okButton = new JButton ("Ok");
      okButton.setToolTipText ("Set the entered value.");
      okButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            try {
               valueField.commitEdit ();
            }
            catch (java.text.ParseException err) {
               System.out.println ("ParseException" + err.getMessage ());
            }  
            
            long value = ((Number)valueField.getValue()).longValue (); 
            newPersistence = value * 1000;
            dialog.setVisible (false);
         }
      });
      
      cancelButton = new JButton ("Cancel");
      cancelButton.setToolTipText ("Cancel this step with changing the value.");
      cancelButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            newPersistence = -1;
            dialog.setVisible (false);
         }
      });
      
      setLayout (new BorderLayout ());
      
      valueField = new JFormattedTextField (NumberFormat.getIntegerInstance ());
      valueField.setToolTipText ("Max value = " + (DashboardController.PERSISTENCE_MAXIMUM/1000) 
         + " seconds, \nMin value = " + (DashboardController.PERSISTENCE_MINIMUM/1000) 
         + " seconds");
      valueField.setValue (new Integer( (int)(persistence / 1000) ));
      valueField.setColumns (4);
      
      Box valueBox = new Box (BoxLayout.X_AXIS);
      valueBox.add(new JLabel("Enter persistence (seconds):"));
      valueBox.add (Box.createHorizontalStrut (10));
      valueBox.add (valueField);
      valueBox.setBorder(BorderFactory.createEmptyBorder (0, 10, 10, 0));
      add (valueBox, BorderLayout.CENTER);
      
      Box buttonBox = new Box (BoxLayout.X_AXIS);
      buttonBox.add (Box.createHorizontalGlue ());
      buttonBox.add (okButton);
      buttonBox.add (Box.createHorizontalStrut (10));
      buttonBox.add (cancelButton);
      add (buttonBox, BorderLayout.SOUTH);
      
   }
   
   /**
    * Displays a dialog box of this panel.
    *
    * @param parentFrame The application frame for this application.
    * @return The time and date to set the simulator.
    */
   public long showDialog (JFrame frame) {
      
      dialog = new JDialog (frame, "Set Persistence Value", true);
      dialog.getContentPane ().add (this);
      dialog.getRootPane ().setDefaultButton (okButton);
      dialog.pack ();
      
      Dimension screenSize =
      Toolkit.getDefaultToolkit ().getScreenSize ();
      Dimension dialogSize = dialog.getPreferredSize ();
      dialog.setLocation (screenSize.width/2 - (dialogSize.width/2),
      screenSize.height/2 - (dialogSize.height/2));
      
      dialog.setVisible (true);
      return newPersistence;
   }
   
   /**
    * Starts the application.
    *
    * @param arg read but not used.
    */
   public static void main (String [] arg){
      JFrame frame = new JFrame ();
      SetPersistenceJPanel panel = new SetPersistenceJPanel (10000);
      frame.getContentPane().add (panel);
      frame.pack ();
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      frame.setVisible (true);
   }
}
