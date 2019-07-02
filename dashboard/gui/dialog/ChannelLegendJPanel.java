/*
 * SetPersistenceJPanel.java
 *
 * Created on June 20, 2005, 8:06 PM
 */

package dashboard.gui.dialog;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import dashboard.gui.*;
/**
 *
 * @author  Stewarts
 */
public class ChannelLegendJPanel extends JPanel {
   
	private static final long serialVersionUID = 6668681861830366419L;

/** Creates a new instance of SetPersistenceJPanel */
   public ChannelLegendJPanel (Vector<DashboardGuagesJPanel> dashboardGuagePanels) {
      
      setOpaque (false);
      setLayout (new BorderLayout ());
      
      if (dashboardGuagePanels != null) {
         JLabel label;
         Box legendBox = new Box (BoxLayout.Y_AXIS);
         for (int i = 0; i < dashboardGuagePanels.size (); i++) {
            DashboardGuagesJPanel panel = (DashboardGuagesJPanel) dashboardGuagePanels.elementAt (i);
            label = new JLabel(panel.getChannelName ());
            label.setOpaque (true);
            label.setBackground (panel.getColor ());
            legendBox.add (label);
         }
         add (legendBox, BorderLayout.CENTER);
      }     
   }
}
