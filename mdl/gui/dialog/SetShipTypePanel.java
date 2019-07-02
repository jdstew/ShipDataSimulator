/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SetShipTypePanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.dialog;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import mdl.simulator.ships.*;
/**
 * This panel provides GUI access to setting the simulated ship type.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SetShipTypePanel extends JPanel {
 
	private static final long serialVersionUID = 1L;
	/** Reference to cancel the dialog box. */
   public static final int CANCEL_ACTION = 0;
   /** Reference to move back to the setup panel. */
   public static final int BACK_ACTION = -1;
   
   int shipType;
   
   JComboBox shipCombo;
   
   JButton okButton;
   JButton cancelButton;
   JButton backButton;

   JDialog dialog;
   
   /**
    * Initializes an object of this class.
    */
   public SetShipTypePanel () {
      
      this.setBorder(BorderFactory.createTitledBorder("Select ship type to simulate."));
      
      int [] shipTypes = ShipTypes.getShipTypes ();
      Vector<ShipTypeItem> itemList = new Vector<ShipTypeItem>();
      
      for (int i = 0; i < shipTypes.length; i++) {
         itemList.add (new ShipTypeItem(shipTypes[i]));
      }
      shipCombo = new JComboBox(itemList);
      
      backButton = new JButton("< Back");
      backButton.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent event) {
               shipType = BACK_ACTION;
               dialog.setVisible (false);
            }
         });
         
      okButton = new JButton("  Ok  ");
      okButton.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent event) {
               ShipTypeItem selected = (ShipTypeItem) shipCombo.getSelectedItem ();
               shipType = selected.getShipType ();
               dialog.setVisible (false);
            }
         });

      cancelButton = new JButton("Cancel");
      cancelButton.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent event) {
               shipType = CANCEL_ACTION;
               dialog.setVisible (false);
            }
         });
         
      Box buttonBox = new Box(BoxLayout.X_AXIS);
      buttonBox.add(backButton);
      buttonBox.add(new JLabel("  "));
      buttonBox.add(okButton);
      buttonBox.add(new JLabel("  "));
      buttonBox.add(cancelButton);

       
      Box panelBox = new Box(BoxLayout.Y_AXIS);
      panelBox.add(shipCombo);
      panelBox.add(new JLabel(" "));
      panelBox.add(buttonBox);
      
      this.add(panelBox);
   }

   /**
    * Displays a dialog box of this panel.
    *
    * @param parentFrame The application frame for this application.
    * @return The reference to the type of ship to simulate
    */
   public int showDialog (JFrame parentFrame) {
      shipType = 0;
      
      dialog = new JDialog (parentFrame, "Setup step 1 of 5", true);
      dialog.getContentPane().add(this);
      dialog.getRootPane().setDefaultButton (okButton);
      dialog.pack();

      Dimension screenSize =
         Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getPreferredSize();
      dialog.setLocation(screenSize.width/2 - (dialogSize.width/2),
                  screenSize.height/2 - (dialogSize.height/2));
 
      dialog.setVisible(true);
      return shipType;
   }
   
   public class ShipTypeItem extends Object {
      int ship;
      
      public ShipTypeItem (int shipType ) {
         ship = shipType;
      }
      
      public String toString() {
         return ShipTypes.getShipName (ship);
      }
      
      public int getShipType () {
         return ship;
      }
   }
}
