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
public class SetPositionBufferCountJPanel extends JPanel {
   
	private static final long serialVersionUID = -8775013469613843280L;
	JFrame parentFrame;
   JDialog dialog;
   
   JButton cancelButton;
   JButton okButton;
   
   JFormattedTextField valueField;
   
   int newPositionBufferCount; // in seconds
   
   /** Creates a new instance of SetPersistenceJPanel */
   public SetPositionBufferCountJPanel (int positionBufferCount) {
      newPositionBufferCount = -1;
      
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
            
            int value = ((Number)valueField.getValue()).intValue (); 
            newPositionBufferCount = value;
            dialog.setVisible (false);
         }
      });
      
      cancelButton = new JButton ("Cancel");
      cancelButton.setToolTipText ("Cancel this step with changing the value.");
      cancelButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            newPositionBufferCount = -1;
            dialog.setVisible (false);
         }
      });
      
      setLayout (new BorderLayout ());
      
      valueField = new JFormattedTextField (NumberFormat.getIntegerInstance ());
      valueField.setToolTipText ("Max value = " + (DashboardController.POSITION_BUFFER_MAXIMUM) 
         + " positions, \nMin value = " + (DashboardController.POSITION_BUFFER_MINIMUM) 
         + " positions");
      valueField.setValue (new Integer(positionBufferCount));
      valueField.setColumns (4);
      
      Box valueBox = new Box (BoxLayout.X_AXIS);
      valueBox.add(new JLabel("Enter number of positions:"));
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
   public int showDialog (JFrame frame) {
      
      dialog = new JDialog (frame, "Set Position Buffer Count", true);
      dialog.getContentPane ().add (this);
      dialog.getRootPane ().setDefaultButton (okButton);
      dialog.pack ();
       
      Dimension screenSize =
      Toolkit.getDefaultToolkit ().getScreenSize ();
      Dimension dialogSize = dialog.getPreferredSize ();
      dialog.setLocation (screenSize.width/2 - (dialogSize.width/2),
      screenSize.height/2 - (dialogSize.height/2));
      
      dialog.setVisible (true);
      return newPositionBufferCount;
   }
   
   /**
    * Starts the application.
    *
    * @param arg read but not used.
    */
   public static void main (String [] arg){
      JFrame frame = new JFrame ();
      SetPositionBufferCountJPanel panel = new SetPositionBufferCountJPanel (100);
      frame.getContentPane().add (panel);
      frame.pack ();
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      frame.setVisible (true);
   }
}
