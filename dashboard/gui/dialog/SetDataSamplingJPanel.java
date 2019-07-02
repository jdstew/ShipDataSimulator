/*
 * SetPersistenceJPanel.java
 *
 * Created on June 20, 2005, 8:06 PM
 */

package dashboard.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.*;
import dashboard.*;
/**
 *
 * @author  Stewarts
 */
public class SetDataSamplingJPanel extends JPanel {
  
	private static final long serialVersionUID = -2184948421537834657L;
	JFrame parentFrame;
   JDialog dialog;
   
   JButton cancelButton;
   JButton okButton;
   
   JSlider periodSlider;
   JLabel freqValue;
   
   long newSamplingPeriod; 
   
   DecimalFormat freqForm = new DecimalFormat("#.00");
   
   /** Creates a new instance of SetPersistenceJPanel */
   public SetDataSamplingJPanel (long period) {
      newSamplingPeriod = -1;
      
      setBorder(BorderFactory.createEmptyBorder (10, 10, 10, 10));
      
      okButton = new JButton ("Ok");
      okButton.setToolTipText ("Set the entered value.");
      okButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            newSamplingPeriod = (long) (100000.0 / (double)periodSlider.getValue());
            dialog.setVisible (false);
         }
      });
      
      cancelButton = new JButton ("Cancel");
      cancelButton.setToolTipText ("Cancel this step with changing the value.");
      cancelButton.addActionListener (new ActionListener () {
         public void actionPerformed (ActionEvent event) {
            newSamplingPeriod = -1;
            dialog.setVisible (false);
         }
      });
      
      setLayout (new BorderLayout ());
      
      Box valueBox = new Box (BoxLayout.Y_AXIS);
      double frequency = 1.0 / (double)period * 1000.0;
      freqValue = new JLabel(freqForm.format (frequency) +
                                  "Hz (every " + freqForm.format (1.0 / frequency) +
                                  " seconds)");
      double max = (1.0/(double)DashboardController.DATA_SAMPLING_PERIOD_MINIMUM) * 100000;
      double def = (1.0/(double)period) * 100000;
      double min = (1.0/(double)DashboardController.DATA_SAMPLING_PERIOD_MAXIMUM) * 100000;
      
      periodSlider = new JSlider(JSlider.HORIZONTAL, 
                                 (int) min,
                                 (int) max,
                                 (int) def);
      periodSlider.addChangeListener (new ChangeListener() {
            public void stateChanged (ChangeEvent event) {
               JSlider source = (JSlider) event.getSource ();              
               double frequency = (double)source.getValue() / 100.0;
               freqValue.setText (freqForm.format (frequency) +
                                  "Hz (every " + freqForm.format (1.0 / frequency) +
                                  " seconds)");
            }
         });
         
      valueBox.add (freqValue);
      valueBox.add (periodSlider);
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
      
      Dimension frameDimension = frame.getSize ();
      Insets frameInsets = frame.getInsets ();
      
      int maxWidth = (frameDimension.width - frameInsets.left
         - frameInsets.right - 10);
      
//      setSize (maxWidth, panelPreferredDimension.height);
      
      dialog = new JDialog (frame, "Set Persistence Value", true);
      dialog.getContentPane ().add (this);
      dialog.getRootPane ().setDefaultButton (okButton);
      dialog.setSize (maxWidth, 200);
//      dialog.pack ();
      dialog.setLocation (frame.getX() + 10, frame.getY() + 20);     
      dialog.setVisible (true);
      return newSamplingPeriod;
   }
   
   /**
    * Starts the application.
    *
    * @param arg read but not used.
    */
   public static void main (String [] arg){
      JFrame frame = new JFrame ();
      SetDataSamplingJPanel panel = new SetDataSamplingJPanel (100);
      frame.getContentPane().add (panel);
      frame.pack ();
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      frame.setVisible (true);
   }
}
