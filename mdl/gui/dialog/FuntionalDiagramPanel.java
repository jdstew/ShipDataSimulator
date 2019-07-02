/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: FuntionalDiagramPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
/**
 * This panel provides a functional description of the MDL application.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class FuntionalDiagramPanel extends JPanel {  
 
	private static final long serialVersionUID = 1L;
	JButton ok;
   JDialog dialog;
   
   /**
    * Initializes this functional diagram panel.
    */
   public FuntionalDiagramPanel () {
      GridBagConstraints constraints = new GridBagConstraints ();
      constraints.fill = GridBagConstraints.BOTH;
      constraints.anchor = GridBagConstraints.CENTER;
      setLayout (new GridBagLayout());
      
      URLClassLoader urlLoader = (URLClassLoader) this.getClass().getClassLoader();
      URL fileLoc = null;     
      
      ImageIcon functionalImageIcon = null;
      fileLoc = urlLoader.getResource("gui/images/appSchema.gif"); 
      if (fileLoc != null) {
            functionalImageIcon = new ImageIcon(fileLoc);
      }
      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.insets = new Insets (4, 6, 4, 6);
      Dimension functionalDimension = new Dimension (571, 283);
      JLabel functionalLabel = new JLabel(functionalImageIcon);
      functionalLabel.setPreferredSize (functionalDimension);
      add (functionalLabel, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 1;
      JTextArea descriptionTextArea = new JTextArea(8, 25);
      descriptionTextArea.setEditable(false);
      descriptionTextArea.setLineWrap (true);
      descriptionTextArea.setWrapStyleWord (true);
      descriptionTextArea.setBackground ((Color) UIManager.get("Panel.background"));
      descriptionTextArea.setText (
         "MDL can both transmit and receive serial data.\n\n" + 

         "Tramitted data is provided from either manually entered data fields " +
         "or a ship simulator.  That data can be transmitted to multiple serial " +
         "ports and viewed as it is being transmitted.\n\n" +

         "MDL can also receive and process data from multiple serial ports. " +
         "Both the received data, and errors found in processing can be viewed.\n");
      descriptionTextArea.setPreferredSize (new Dimension(571, 80));
      add (descriptionTextArea, constraints);
            
      constraints.gridx = 0;
      constraints.gridy = 2;
      ok = new JButton("Ok");
      ok.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent event) {
               dialog.setVisible (false);
            }
         });      
      add (ok, constraints);
            
   }
   
   /**
    * Displays this panel as a dialog box.
    *
    * @param parentFrame The application frame to display the dialog box.
    */
   public void showDialog (JFrame parentFrame) {
      
      dialog = new JDialog (parentFrame, "MDL Functional Diagram", true);
      dialog.getContentPane().add(this);
      dialog.getRootPane().setDefaultButton (ok);

      Dimension screenSize =
         Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getPreferredSize();
      dialog.setSize (dialogSize);
      dialog.setLocation(screenSize.width/2 - (dialogSize.width/2),
                  screenSize.height/2 - (dialogSize.height/2));
      
      dialog.pack();
      
      dialog.setVisible(true);
   }
}
