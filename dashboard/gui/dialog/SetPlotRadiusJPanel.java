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
public class SetPlotRadiusJPanel extends JPanel {
   
	private static final long serialVersionUID = -2258272561978537779L;

	static final double DEGREE_TO_METERS = 111120.0;
   
   JFrame parentFrame;
   JDialog dialog;
   
   JButton cancelButton;
   JButton okButton;
   
   JFormattedTextField valueField;
   DecimalFormat decimalForm = new DecimalFormat("#0.0");
   
   double newPlotRadius; // in seconds
   
   /** Creates a new instance of SetPersistenceJPanel */
   public SetPlotRadiusJPanel (double plotRadius) {
      newPlotRadius = -1.0;
      
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
            
            double value = ((Number)valueField.getValue()).doubleValue (); 
            newPlotRadius = value / DEGREE_TO_METERS;
            dialog.setVisible (false);
         }
      });
      
      cancelButton = new JButton ("Cancel");
      cancelButton.setToolTipText ("Cancel this step with changing the value.");
      cancelButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            newPlotRadius = -1.0;
            dialog.setVisible (false);
         }
      });
      
      setLayout (new BorderLayout ());
      
      valueField = new JFormattedTextField (NumberFormat.getNumberInstance ());
      valueField.setToolTipText ("Max value = " + 
         decimalForm.format (DashboardController.POSITION_RADIUS_MAXIMUM * DEGREE_TO_METERS) 
         + " meters, \nMin value = " + 
         decimalForm.format (DashboardController.POSITION_RADIUS_MINIMUM * DEGREE_TO_METERS) 
         + " meters");
      valueField.setValue (new Double(plotRadius * DEGREE_TO_METERS));
      valueField.setColumns (6);
      
      Box valueBox = new Box (BoxLayout.X_AXIS);
      valueBox.add(new JLabel("Enter plot radius (meters):"));
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
   public double showDialog (JFrame frame) {
      
      dialog = new JDialog (frame, "Set Position Plot Radius", true);
      dialog.getContentPane ().add (this);
      dialog.getRootPane ().setDefaultButton (okButton);
      dialog.pack ();
       
      Dimension screenSize =
      Toolkit.getDefaultToolkit ().getScreenSize ();
      Dimension dialogSize = dialog.getPreferredSize ();
      dialog.setLocation (screenSize.width/2 - (dialogSize.width/2),
      screenSize.height/2 - (dialogSize.height/2));
      
      dialog.setVisible (true);
      return newPlotRadius;
   }
   
   /**
    * Starts the application.
    *
    * @param arg read but not used.
    */
   public static void main (String [] arg){
      JFrame frame = new JFrame ();
      SetPlotRadiusJPanel panel = new SetPlotRadiusJPanel (100.0);
      frame.getContentPane().add (panel);
      frame.pack ();
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      frame.setVisible (true);
   }
}
