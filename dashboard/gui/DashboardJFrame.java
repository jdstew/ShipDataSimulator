/* Navigation Dashboard
 * Copyright (c) 2005 Jeffrey Stewart
 *
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: DashboardJFrame.java
 * Created: 2005-05-25, 12:01:01
 */
package dashboard.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.util.*;
import dashboard.*;
import dashboard.gui.dialog.*;
/**
 * The JFrame is the window for the application.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-07-01
 */
public class DashboardJFrame extends JFrame implements MouseListener {
   

	private static final long serialVersionUID = -3302355317914805140L;

	static final int LAYER_OFFSET = 2;
   
   ImageIcon imgIcon;
   
   DashboardBaseJPanel dashboardBaseJPanel;
   DashboardGuagesJPanel dashboardGuagesJPanel;
   JLayeredPane layeredPane;
   
   Dimension frameDimension;
   double frameScale;
   
   Vector<DashboardGuagesJPanel> dashboardGuagePanels;
   
   DashboardController controller;
   
   JPopupMenu popupMenu;
   
//   JDesktopPane desktop;
   
   /** Creates a new instance of DashboardJFrame */
   public DashboardJFrame (DashboardController dashboardController,
      boolean isStandAloneApplication) {
         
      if (dashboardController != null) {
         controller = dashboardController;
      }
      
      if (isStandAloneApplication) {
         setVisible (true);
      }
      else {
         setVisible (false);
      }
           
      final Action closeWindowAction = new CloseWindowAction();
      Action displayChannelLegend = new DisplayChannelLegendAction ();
      Action setPlotRadius = new SetPlotRadiusAction ();
      Action setPersistenceAction = new SetPersistenceAction ();
      Action setPositionBufferCount = new SetPositionBufferCountAction ();
      Action setDataSampling = new SetDataSamplingAction ();
      
      popupMenu = new JPopupMenu ();
      JMenuItem nextMenuItem;
      
      nextMenuItem = new JMenuItem (displayChannelLegend);
      popupMenu.add (nextMenuItem);
      nextMenuItem = new JMenuItem (setPlotRadius);
      popupMenu.add (nextMenuItem);
      nextMenuItem = new JMenuItem (setPersistenceAction);
      popupMenu.add (nextMenuItem);
      nextMenuItem = new JMenuItem (setPositionBufferCount);
      popupMenu.add (nextMenuItem);
      nextMenuItem = new JMenuItem (setDataSampling);
      popupMenu.add (nextMenuItem);
      
      if (isStandAloneApplication) {
         JMenu comdacMenu = new JMenu("COMDAC-INS");
         comdacMenu.setMnemonic ('C');

         Action connectToNMCP = new ConnectToComdacNMAction ();
         nextMenuItem = new JMenuItem (connectToNMCP);
         comdacMenu.add (nextMenuItem);

         popupMenu.add (comdacMenu);
      }
      
      MouseListener popupListener = new PopupListener (popupMenu);
      getContentPane ().addMouseListener (popupListener);
      
      
      frameDimension = new Dimension ();
      frameScale = 0.0;
      
      Toolkit defaultKit = Toolkit.getDefaultToolkit ();
      Dimension screenSize = defaultKit.getScreenSize ();
      int screenWidth = screenSize.width;
      int screenHieght = screenSize.height;
      
      setSize ((int)(0.95*screenWidth), (int)(0.95*screenHieght));
      setResizable (true);
      setLocationRelativeTo (null);
      setExtendedState (JFrame.MAXIMIZED_BOTH);
      setTitle ("Navigation Dashboard v" + dashboard.DashboardController.VERSION);
//      setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      addWindowListener (
         new WindowAdapter () {
            public void windowClosing (WindowEvent e) {
               closeWindowAction.actionPerformed (null);
            }
         });
      
      URL imgURL = DashboardJFrame.class.getResource ("images/applicationIcon.gif");
      
      if (imgURL != null) {
         imgIcon = new ImageIcon (imgURL);
         setIconImage (imgIcon.getImage ());
      } else {
         System.err.println ("Couldn't find application icon.");
      }
      
      addComponentListener (new
      ComponentAdapter () {
         public void componentResized (ComponentEvent event) {
            rescaleDashboard ();
         }
      });
      
      layeredPane = this.getLayeredPane ();
      
      dashboardBaseJPanel = new DashboardBaseJPanel ();
      layeredPane.add (dashboardBaseJPanel, new Integer (1));
      
      dashboardGuagePanels = new Vector<DashboardGuagesJPanel> ();
      
      rescaleDashboard ();
      
   }
   
   public void showDashboardJFrame () {
      this.setVisible (true);
      this.requestFocus ();
   }
   
   public void hideDashboardJFrame () {
      this.setVisible (false);
   }
   
   public void addChannel (DashboardChannel newChannel) {
      int size = dashboardGuagePanels.size ();
      
      if (newChannel != null) {
         DashboardGuagesJPanel panel = newChannel.getDashboardGuagesJPanel ();
         panel.setScale (frameScale, frameDimension);
         dashboardGuagePanels.add (panel);
         layeredPane.add (panel, new Integer (size + LAYER_OFFSET));
      }
   }
   
   public void updateDisplay () {
      for (int i = 0; i < dashboardGuagePanels.size (); i++) {
         DashboardGuagesJPanel panel = (DashboardGuagesJPanel) dashboardGuagePanels.elementAt (i);
         panel.repaint ();
      }
   }
   
   private void rescaleDashboard () {
      Insets insets = this.getInsets ();
      frameDimension.width = getWidth () - (insets.left + insets.right);
      frameDimension.height = getHeight () - (insets.top + insets.bottom);
      frameScale = dashboardBaseJPanel.getNewScale (frameDimension);
      
      for (int i = 0; i < dashboardGuagePanels.size (); i++) {
         DashboardGuagesJPanel panel = (DashboardGuagesJPanel) dashboardGuagePanels.elementAt (i);
         panel.setScale (frameScale, frameDimension);
      }
   }
   
   public void mouseClicked (MouseEvent event) {
   }
   
   public void mouseEntered (MouseEvent event) {
   }
   
   public void mouseExited (MouseEvent event) {
   }
   
   public void mousePressed (MouseEvent event) {
      if (event.isPopupTrigger ()) {
         popupMenu.show (event.getComponent (),
         event.getX (), event.getY ());
         System.out.println ("attempted to show");
      }
   }
   
   public void mouseReleased (MouseEvent event) {
   }
   
   private class DisplayChannelLegendAction extends AbstractAction {
      
	private static final long serialVersionUID = 1L;

	public DisplayChannelLegendAction () {
         putValue (Action.NAME, "Display Legend");
         putValue (Action.MNEMONIC_KEY, new Integer ('L'));
         putValue (Action.SHORT_DESCRIPTION,
         "Display the name and color of each channel (i.e. set of guages).");
      }
      
      public void actionPerformed (ActionEvent event) {
         JInternalFrame legendFrame = new JInternalFrame("Legend", false, true, false);
         JPanel legendPanel = new ChannelLegendJPanel (dashboardGuagePanels);
         
         legendFrame.getContentPane ().add (legendPanel);
         legendFrame.setSize (legendPanel.getPreferredSize ());
         
         Point frameLocation = DashboardJFrame.this.getLocation ();
         Insets frameInsets = DashboardJFrame.this.getInsets ();
         legendFrame.setLocation ( (frameLocation.x + frameInsets.left + 10) ,
            (frameLocation.y + frameInsets.top ) );
         legendFrame.pack ();
         legendFrame.setVisible (true);
         
//         desktop.add(legendFrame);
         
         layeredPane.add (legendFrame, new Integer(99));
      }
   }
   
   private class SetPlotRadiusAction extends AbstractAction {
      
	private static final long serialVersionUID = 8733226639919690385L;

	public SetPlotRadiusAction () {
         putValue (Action.NAME, "Set Plot Radius");
         putValue (Action.MNEMONIC_KEY, new Integer ('R'));
         putValue (Action.SHORT_DESCRIPTION,
         "Set plot radius (i.e. distance from center to outer ring).");
      }
      
      public void actionPerformed (ActionEvent event) {
         SetPlotRadiusJPanel dialog = new SetPlotRadiusJPanel (controller.getPlotRadius ());
         double newPlotRadius = dialog.showDialog (DashboardJFrame.this);
         if (newPlotRadius > 0.0) {
            controller.setPlotRadius (newPlotRadius);
         }
      }
   }
   
   private class SetPersistenceAction extends AbstractAction {
     
	private static final long serialVersionUID = -6533094805825140131L;

	public SetPersistenceAction () {
         putValue (Action.NAME, "Set Persistence");
         putValue (Action.MNEMONIC_KEY, new Integer ('P'));
         putValue (Action.SHORT_DESCRIPTION,
         "Set data persistence (i.e. how long data stays visible).");
      }
      
      public void actionPerformed (ActionEvent event) {
         SetPersistenceJPanel dialog = new SetPersistenceJPanel (controller.getPersistence ());
         long newPersistence = dialog.showDialog (DashboardJFrame.this);
         if (newPersistence > 0) {
            controller.setPersistence (newPersistence);
         }
      }
   }
   
   private class SetPositionBufferCountAction extends AbstractAction {
     
	private static final long serialVersionUID = -1303375779602708829L;

	public SetPositionBufferCountAction () {
         putValue (Action.NAME, "Set Position Buffer");
         putValue (Action.MNEMONIC_KEY, new Integer ('B'));
         putValue (Action.SHORT_DESCRIPTION,
         "Set position buffer count (i.e. how many position plotted).");
      }
      
      public void actionPerformed (ActionEvent event) {
         SetPositionBufferCountJPanel dialog = new SetPositionBufferCountJPanel (controller.getPlotPositionBufferCount ());
         int newPositionBufferCount = dialog.showDialog (DashboardJFrame.this);
         if (newPositionBufferCount > 0) {
            controller.setPlotPositionBufferCount (newPositionBufferCount);
         }
      }
   }
   
   private class SetDataSamplingAction extends AbstractAction {
      
	private static final long serialVersionUID = 1L;

	public SetDataSamplingAction () {
         putValue (Action.NAME, "Set Sampling Rate");
         putValue (Action.MNEMONIC_KEY, new Integer ('S'));
         putValue (Action.SHORT_DESCRIPTION,
         "Set frequency (or period) of how often position data is recorded.");
      }
      
      public void actionPerformed (ActionEvent event) {
         SetDataSamplingJPanel dialog = new SetDataSamplingJPanel (controller.getDataSamplingPeriod ());
         long newSamplingPeriod = dialog.showDialog (DashboardJFrame.this);
         if (newSamplingPeriod > 0) {
            controller.setDataSamplingPeriod (newSamplingPeriod);
         }
      }
   }
     
   private class ConnectToComdacNMAction extends AbstractAction {
      
	private static final long serialVersionUID = 566013464291054756L;

	public ConnectToComdacNMAction () {
         putValue (Action.NAME, "Connect to CP");
         putValue (Action.MNEMONIC_KEY, new Integer ('N'));
         putValue (Action.SHORT_DESCRIPTION,
         "Connect to Comdac's Navigation & Maneuvering (NM)\n" +
         "ownship Computed Position (CP) broadcast.");
      }
      
      public void actionPerformed (ActionEvent event) {

      }
   }
   
   private class PopupListener extends MouseAdapter {
      JPopupMenu popup;
      
      PopupListener (JPopupMenu popupMenu) {
         popup = popupMenu;
      }
      
      public void mousePressed (MouseEvent e) {
         maybeShowPopup (e);
      }
      
      public void mouseReleased (MouseEvent e) {
         maybeShowPopup (e);
      }
      
      private void maybeShowPopup (MouseEvent e) {
         if (e.isPopupTrigger ()) {
            popup.show (e.getComponent (),
            e.getX (), e.getY ());
         }
      }
   }
   

   private class CloseWindowAction extends AbstractAction {

	private static final long serialVersionUID = 5478100232443684122L;

	public CloseWindowAction () {
//         fileLoc = urlLoader.getResource("gui/images/Blank16.gif");
         putValue(Action.NAME, "Close");
         putValue(Action.MNEMONIC_KEY, new Integer('C'));
//         if (fileLoc != null) {
//            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
//         }         
         putValue(Action.SHORT_DESCRIPTION,
            "Close this window.");
      }

      public void actionPerformed (ActionEvent event) {
         hideDashboardJFrame ();
      }    
   }
}
