/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: ApplicationFrame.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.net.*;
import java.util.*;
import mdl.prefs.*;
import mdl.simulator.*;
import mdl.data.*;
import mdl.debug.*;
import mdl.gui.dialog.*;
import mdl.gui.manualData.*;
import serialComms.*;
import serialComms.gui.*;
import networkComms.*;
import networkComms.gui.*;
import dashboard.*;
/**
 * The ApplicatonFrame is the window for the application.  Within this frame
 * is the MainPanel, which subsequently holds the user interfaces.
 *
 * @author Jeff Stewart
 * @version 1.3.0.0, 2005-02-17
 */
public class ApplicationFrame extends JFrame {

	private static final long serialVersionUID = 8647014392419868536L;

	static final String VERSION = "1.3.0.1";
   
   OwnshipSimulatorPanel ownshipSimulatorPanel;
   AutopilotControlPanel autopilotControlPanel;
   SerialControllerPanel serialControllerPanel;
   NetworkControllerPanel networkControllerPanel;
   ManualDataPanel manualDataPanel;
   
   OwnshipController ownshipController;
   SerialController serialController;
   NetworkController networkController;
   DataFactory dataFactory;
   DashboardController dashboardController;
   
   DebugManager debugManager;
   DebugListenerPanel debugListenerPanel;
   boolean debugWindowVisible = false;
   
   URLClassLoader urlLoader = (URLClassLoader) this.getClass().getClassLoader();
   URL fileLoc;    
   
   JTabbedPane tabbedPane;
   JPanel simulatorPanel;
   
   // Create the commonly used actions
//   Action saveAction = new SaveAction();
//   Action printAction = new PrintAction();
//   Action propertiesAction = new PropertiesAction();
   Action exitAction = new ExitAction();
//   Action cutAction = new CutAction();
//   Action copyAction = new CopyAction();
//   Action pasteAction = new PasteAction();
   Action showDashboardAction = new ShowDashboardAction();
   Action startAction = new StartAction(); 
   Action pauseAction = new PauseAction();
   Action stopAction = new StopAction();
   Action setTimeDateAction = new TimeDateAction();
   Action setPositionAction = new PositionAction();
   Action setHeadingSpeedAction = new HeadingSpeedAction();
   Action setSetDriftAction = new SetDriftAction();
   Action openPortAction = new OpenPortAction();
   Action closeAllPortsAction = new CloseAllPortsAction();
   Action openServerAction = new OpenServerAction();
//   Action optionsAction = new OptionsAction();
   Action debugWindowAction = new DebugWindowAction();
   Action functionalDiagramAction = new FunctionalDiagramAction();
//   Action helpAction = new HelpAction();
   Action aboutAction = new AboutAction(); 
   
   /**
    * Creates the application frame an its menus and icon bars.
    *
    * @param ownshipCntrl The ownship controller for this application.
    * @param serialCntrl The serial port controller for this application.
    * @param dataFac The data factory for manual data for this application.
    * @param dbgMngr The debug manager for this application.
    */
   public ApplicationFrame (OwnshipController ownshipCntrl,
                            SerialController serialCntrl,
                            NetworkController netCntrl,
                            DataFactory dataFac,
                            DebugManager dbgMngr,
                            DashboardController dbCntrl) {
                               
      UIManager.put("TabbedPane.selected", new Color(0xF7F7F7));
//      UIManager.put("MenuItem.selectionBackground", Color.red);
      
      if (ownshipCntrl != null) {
         ownshipController = ownshipCntrl;
      }
                               
      if (serialCntrl != null) {
         serialController = serialCntrl;
      }
      
      if (netCntrl != null) {
         networkController = netCntrl;
      }
                             
      if (dataFac != null) {
         dataFactory = dataFac;
      }
                             
      if (dbgMngr != null) {
         debugManager = dbgMngr;
      }
      
      if (dbCntrl != null) {
         dashboardController = dbCntrl;
      }
                            

      Toolkit defaultKit = Toolkit.getDefaultToolkit ();
      Dimension screenSize = defaultKit.getScreenSize ();
      int screenWidth = screenSize.width;
      int screenHieght = screenSize.height;
      
      URL fileLoc = ApplicationFrame.class.getResource("images/mdlSmall.gif");
      if (fileLoc != null) {
         try {
            Image iconImg = ImageIO.read(fileLoc);
            setIconImage (iconImg);
         }
         catch (IOException exception) {
            exception.printStackTrace ();
         }
      } else {
         System.err.println ("Couldn't find application icon.");
      }
      
      JFrame.setDefaultLookAndFeelDecorated(true);
//      setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      addWindowListener (
         new WindowAdapter () {
            public void windowClosing (WindowEvent e) {
               exitAction.actionPerformed (null);
            }
         });

      setResizable (true);
      setSize ((int)(0.95*screenWidth), (int)(0.95*screenHieght));
      setLocationRelativeTo(null);
      setExtendedState (JFrame.MAXIMIZED_BOTH);
      setTitle ("Maritime Digital Lab (MDL)");
               
      // Create the text-based menu bar
      JMenuBar textMenuBar = new JMenuBar();
      setJMenuBar(textMenuBar);
      
      JMenuItem thisMenuItem;
      
      JMenu fileMenu = new JMenu("File");
      fileMenu.setMnemonic ('F');
      
//      thisMenuItem = new JMenuItem(saveAction);
//      thisMenuItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_S, KeyEvent.CTRL_MASK));
//      fileMenu.add(thisMenuItem);
      
//      thisMenuItem = new JMenuItem(printAction);
//      thisMenuItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_P, KeyEvent.CTRL_MASK));
//      fileMenu.add(thisMenuItem);
      
//      fileMenu.add(new JMenuItem(propertiesAction));
      
      thisMenuItem = new JMenuItem(exitAction);
      thisMenuItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
      fileMenu.add(thisMenuItem); 

      textMenuBar.add(fileMenu);

//      JMenu editMenu = new JMenu("Edit");
//      editMenu.setMnemonic ('E');
      
//      thisMenuItem = new JMenuItem(cutAction);
//      thisMenuItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_X, KeyEvent.CTRL_MASK));
//      editMenu.add(thisMenuItem);
      
//      thisMenuItem = new JMenuItem(copyAction);
//      thisMenuItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_C, KeyEvent.CTRL_MASK));
//      editMenu.add(thisMenuItem);
      
//      thisMenuItem = new JMenuItem(pasteAction);
//      thisMenuItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_V, KeyEvent.CTRL_MASK));
//      editMenu.add(thisMenuItem);
      
//      textMenuBar.add(editMenu);
      
      JMenu viewMenu = new JMenu("View");
      viewMenu.setMnemonic ('V');
      
      thisMenuItem = new JMenuItem(showDashboardAction);
      viewMenu.add(thisMenuItem);
      
      textMenuBar.add(viewMenu);

      JMenu simulationMenu = new JMenu("Simulation");
      simulationMenu.setMnemonic ('S');
      
      thisMenuItem = new JMenuItem(startAction);
      thisMenuItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_F2, 0));
      simulationMenu.add(thisMenuItem);
      
      thisMenuItem = new JMenuItem(pauseAction);
      thisMenuItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_F3, 0));
      simulationMenu.add(thisMenuItem);
      
      thisMenuItem = new JMenuItem(stopAction);
      thisMenuItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_F4, 0));
      simulationMenu.add(thisMenuItem);
      
      JMenu ownshipSubMenu = new JMenu("Ownship");
      ownshipSubMenu.setMnemonic ('O');
      
      thisMenuItem = new JMenuItem(setHeadingSpeedAction);
      ownshipSubMenu.add(thisMenuItem);
       
      thisMenuItem = new JMenuItem(setPositionAction);
      ownshipSubMenu.add(thisMenuItem);
      
      JMenu environmentSubMenu = new JMenu("Environment");
      environmentSubMenu.setMnemonic ('E');
      
      thisMenuItem = new JMenuItem(setTimeDateAction);
      environmentSubMenu.add(thisMenuItem);
      
      thisMenuItem = new JMenuItem(setSetDriftAction);
      environmentSubMenu.add(thisMenuItem);
      
      simulationMenu.add(ownshipSubMenu);
      simulationMenu.add(environmentSubMenu);
      textMenuBar.add(simulationMenu);
      
      JMenu communicationsMenu = new JMenu("Communications");
      communicationsMenu.setMnemonic ('C');
      
      JMenu serialMenu = new JMenu("Serial");
      serialMenu.setMnemonic ('S');
      
      thisMenuItem = new JMenuItem(openPortAction);
      serialMenu.add(thisMenuItem);
      
      thisMenuItem = new JMenuItem(closeAllPortsAction);
      serialMenu.add(thisMenuItem);
      
      communicationsMenu.add(serialMenu);
            
      JMenu networkMenu = new JMenu("Network");
      networkMenu.setMnemonic ('N');
            
      thisMenuItem = new JMenuItem(openServerAction);
      networkMenu.add(thisMenuItem);

      communicationsMenu.add(networkMenu);
      
      textMenuBar.add(communicationsMenu);
      
      JMenu toolsMenu = new JMenu("Tools");
      toolsMenu.setMnemonic ('T');
      
//      thisMenuItem = new JMenuItem(optionsAction);
//      toolsMenu.add(thisMenuItem);
      
      thisMenuItem = new JMenuItem(debugWindowAction);
      toolsMenu.add(thisMenuItem);
     
      textMenuBar.add(toolsMenu);
      
      JMenu helpMenu = new JMenu("Help");
      helpMenu.setMnemonic ('H');
      
//      thisMenuItem = new JMenuItem(helpAction);
//      thisMenuItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_F1, 0));
////      helpMenu.add(thisMenuItem);
      
      helpMenu.add(new JMenuItem(functionalDiagramAction));
      
      helpMenu.add(new JMenuItem(aboutAction));
      
      textMenuBar.add(helpMenu);
      
      // Add tool bar below the text menu bar
      JToolBar iconToolBar = new JToolBar();
//      iconToolBar.add(saveAction);
//      iconToolBar.addSeparator();
//      iconToolBar.add(printAction);
//      iconToolBar.addSeparator ();
//      iconToolBar.add(cutAction);
//      iconToolBar.add(copyAction);
//      iconToolBar.add(pasteAction);
//      iconToolBar.addSeparator ();
//      iconToolBar.add(optionsAction);
//      iconToolBar.addSeparator ();
//      iconToolBar.add(helpAction);
//      iconToolBar.addSeparator ();
      iconToolBar.add(startAction);
      iconToolBar.add(pauseAction);
      iconToolBar.add(stopAction);
           
      Container contentPane = getContentPane();
      contentPane.add(iconToolBar, BorderLayout.NORTH);

      ownshipSimulatorPanel = new OwnshipSimulatorPanel(ownshipController);
      ownshipSimulatorPanel.setAlignmentY (JComponent.TOP_ALIGNMENT);
      autopilotControlPanel = new AutopilotControlPanel(ownshipController);
      autopilotControlPanel.setAlignmentY (JComponent.TOP_ALIGNMENT);
      serialControllerPanel = new SerialControllerPanel(serialController);
      networkControllerPanel = new NetworkControllerPanel(this, networkController);
      manualDataPanel = new ManualDataPanel(dataFactory);
      
      serialController.addSerialControllerListener (serialControllerPanel);
      networkController.addNetworkControllerUIListener (networkControllerPanel);
      ownshipController.addOwnshipUpdateListener (ownshipSimulatorPanel);
      eventPauseAction ();
      
//      simulatorPanel = new JPanel();
      Box simulatorBox = new Box(BoxLayout.X_AXIS);
      simulatorBox.add(ownshipSimulatorPanel);
      simulatorBox.add(autopilotControlPanel);

      tabbedPane = new JTabbedPane();
      
      tabbedPane.addTab("Simulated Data", null, new JScrollPane (simulatorBox),
                  "Ownship simulator with autopilot controls");
      tabbedPane.addTab("Manual Data", null, new JScrollPane (manualDataPanel),
                  "Manual data entry fields for output data");
      //tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
      tabbedPane.addTab("Serial Comms", null, serialControllerPanel,
                  "Serial communications data control panel");
      tabbedPane.addTab("Network Comms", null, networkControllerPanel,
                  "Network communications data control panel");

      contentPane.add(tabbedPane);
      
      debugListenerPanel = new DebugListenerPanel ();
      debugManager.addDebugListener (debugListenerPanel);
      
//      pack();
      setVisible(true);
   }

   /* note: the following code is never called in the current code version
   / 
    * Sets the ownship controller for the GUI, which allows control
    * of the simulated vessel and simulator.
    *
    * @param obj Reference to the ownship controller.
    /
   private void setOwnshipController (OwnshipController obj) {
      if (obj != null) {
         ownshipController = obj;
         ownshipController.addOwnshipUpdateListener (ownshipSimulatorPanel);
         
         ownshipSimulatorPanel.setOwnshipController(ownshipController);
         autopilotControlPanel.setOwnshipController(ownshipController);
         eventPauseAction ();
      }
   }
   */
   
   /**
    * Returns a reference to the ownship simulator interface.
    *
    * @return Ownship simulator GUI.
    */
   public OwnshipSimulatorPanel getOwnshipSimulatorPanel () {
      return ownshipSimulatorPanel;
   }
   
   /**
    * Returns a reference to the autopilot control interface.
    *
    * @return Autopilot control GUI.
    */
   public AutopilotControlPanel getAutopilotControlPanel () {
      return autopilotControlPanel;
   }
   
   /**
    * Start the simulator.
    */
   public void eventStartAction () {
      startAction.setEnabled(false);
      pauseAction.setEnabled(true);
      stopAction.setEnabled(true);
      if (ownshipController != null) {
            ownshipController.start ();
      }
   }

   /**
    * Pause the simulator.
    */
   public void eventPauseAction () {
      startAction.setEnabled(true);
      pauseAction.setEnabled(false);
      stopAction.setEnabled(true);
      if (ownshipController != null) {
         ownshipController.pause();
      }  
   }
   
   /**
    * Stop the simulator.
    */
   public void eventStopAction () {
      startAction.setEnabled(true);
      pauseAction.setEnabled(false);
      stopAction.setEnabled(false);
      if (ownshipController != null) {
         ownshipController.stop ();
      }
   }
   
   /** 
    * Open a serial port.
    */
   public void eventOpenPort () {
      openPortAction.actionPerformed (null);
   }

   /* note: the following code is never called in the current code version
   private class SaveAction extends AbstractAction {

      public SaveAction () {
         fileLoc = urlLoader.getResource("gui/images/Save16.gif");
         putValue(Action.NAME, "Save");
         putValue(Action.MNEMONIC_KEY, new Integer('S'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }
         putValue(Action.SHORT_DESCRIPTION,
            "Save current settings to a file.");
         setEnabled(false);
      }

      public void actionPerformed (ActionEvent event) {
      }    
   }
*/
   
   /* note: the following code is never called in the current code version
   private class PrintAction extends AbstractAction {

      public PrintAction () {
         fileLoc = urlLoader.getResource("gui/images/Print16.gif");
         putValue(Action.NAME, "Print");
         putValue(Action.MNEMONIC_KEY, new Integer('P'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }         putValue(Action.SHORT_DESCRIPTION,
            "Print currently viewed data to printer or file.");
         setEnabled(false);
      }

      public void actionPerformed (ActionEvent event) {
      }    
   }
   
   private class PropertiesAction extends AbstractAction {

      public PropertiesAction () {
         fileLoc = urlLoader.getResource("gui/images/Properties16.gif");
         putValue(Action.NAME, "Properties...");
         putValue(Action.MNEMONIC_KEY, new Integer('i'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }         putValue(Action.SHORT_DESCRIPTION,
            "View and edit application properties.");
         setEnabled(false);
      }

      public void actionPerformed (ActionEvent event) {
      }    
   }
*/
   private class ExitAction extends AbstractAction {

	private static final long serialVersionUID = 6854695489761829008L;

	public ExitAction () {
         fileLoc = urlLoader.getResource("gui/images/Blank16.gif");
         putValue(Action.NAME, "Exit");
         putValue(Action.MNEMONIC_KEY, new Integer('x'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }         putValue(Action.SHORT_DESCRIPTION,
            "Exit this application.");
      }

      public void actionPerformed (ActionEvent event) {
         serialControllerPanel.closeAllSerialPorts ();
         PreferenceManager.saveOwnshipData (ownshipController.getOwnshipUpdate ());
         PreferenceManager.saveManualData (dataFactory);
         PreferenceManager.saveGUIPrefs ();
         System.exit(0);
      }    
   }
   /* note: the following code is never called in the current code version
   private class CutAction extends AbstractAction {

      public CutAction () {
         fileLoc = urlLoader.getResource("gui/images/Cut16.gif");
         putValue(Action.NAME, "Cut");
         putValue(Action.MNEMONIC_KEY, new Integer('t'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }         putValue(Action.SHORT_DESCRIPTION,
            "Cut selected text to clipboard.");
         setEnabled(false);
      }

      public void actionPerformed (ActionEvent event) {
      }    
   }
*/
   
   /* note: the following code is never called in the current code version
   private class CopyAction extends AbstractAction {

      public CopyAction () {
         fileLoc = urlLoader.getResource("gui/images/Copy16.gif");
         putValue(Action.NAME, "Copy");
         putValue(Action.MNEMONIC_KEY, new Integer('C'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }         putValue(Action.SHORT_DESCRIPTION,
            "Copy selected text to clipboard.");
         setEnabled(false);
      }

      public void actionPerformed (ActionEvent event) {
      }    
   }
*/
   
   /* note: the following code is never called in the current code version
   private class PasteAction extends AbstractAction {

      public PasteAction () {
         fileLoc = urlLoader.getResource("gui/images/Paste16.gif");
         putValue(Action.NAME, "Paste");
         putValue(Action.MNEMONIC_KEY, new Integer('P'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }         putValue(Action.SHORT_DESCRIPTION,
            "Copy clipboard data to cursor location.");
         setEnabled(false);
      }

      public void actionPerformed (ActionEvent event) {
      }    
   }
   */
   
   private class ShowDashboardAction extends AbstractAction {

	private static final long serialVersionUID = 6402783928928986700L;

	public ShowDashboardAction () {
//         fileLoc = urlLoader.getResource("gui/images/Paste16.gif");
         putValue(Action.NAME, "Show Dashboard");
         putValue(Action.MNEMONIC_KEY, new Integer('S'));
//         if (fileLoc != null) {
//            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
//         }         
         putValue(Action.SHORT_DESCRIPTION,
            "Show the dashboard and set it on top of other windows.");
         setEnabled(true);
      }

      public void actionPerformed (ActionEvent event) {
         dashboardController.showDashboardJFrame ();
      }    
   }

   private class StartAction extends AbstractAction {

	private static final long serialVersionUID = -6489115301356235745L;

	public StartAction () {
         fileLoc = urlLoader.getResource("gui/images/Play16.gif");
         putValue(Action.NAME, "Start");
         putValue(Action.MNEMONIC_KEY, new Integer('S'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }         putValue(Action.SHORT_DESCRIPTION,
            "Start ownship simulation.");
         setEnabled(true);
      }

      public void actionPerformed (ActionEvent event) {
         eventStartAction();
      }
      
   }
   
   private class PauseAction extends AbstractAction {

	private static final long serialVersionUID = -5300848173271463867L;

	public PauseAction () {
         fileLoc = urlLoader.getResource("gui/images/Pause16.gif");
         putValue(Action.NAME, "Pause");
         putValue(Action.MNEMONIC_KEY, new Integer('u'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }         putValue(Action.SHORT_DESCRIPTION,
            "Pause ownship; however, keep time ticking.");
         setEnabled(false);
      }

      public void actionPerformed (ActionEvent event) {
         eventPauseAction();
      }    
   }
   
   private class StopAction extends AbstractAction {

	private static final long serialVersionUID = 5224403275383497857L;

	public StopAction () {
         fileLoc = urlLoader.getResource("gui/images/Stop16.gif");
         putValue(Action.NAME, "Stop");
         putValue(Action.MNEMONIC_KEY, new Integer('p'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }         putValue(Action.SHORT_DESCRIPTION,
            "Stop ownship simulation.");
         setEnabled(false);
      }

      public void actionPerformed (ActionEvent event) {
         eventStopAction();
      }    
   }
   
   private class TimeDateAction extends AbstractAction {

	private static final long serialVersionUID = -1606295875834720748L;

	public TimeDateAction () {
         putValue(Action.NAME, "Set Time and Date");
         putValue(Action.MNEMONIC_KEY, new Integer('T'));
         putValue(Action.SHORT_DESCRIPTION,
            "Set ownship simulation time and date.");
      }

      public void actionPerformed (ActionEvent event) {
         OwnshipUpdate ownshipUpdate = ownshipController.getOwnshipUpdate ();
         Date ownshipTime = ownshipUpdate.timeDate;
         SetTimeDatePanel setTimeDatePanel = new SetTimeDatePanel(ownshipTime);
         
         ownshipTime = setTimeDatePanel.showDialog (ApplicationFrame.this);
         
         if (ownshipTime != null) {
            ownshipController.setTimeDate (ownshipTime);
         }
      }    
   }
   
   private class PositionAction extends AbstractAction {

	private static final long serialVersionUID = 2891609539418759399L;

	public PositionAction () {
         putValue(Action.NAME, "Set Position");
         putValue(Action.MNEMONIC_KEY, new Integer('P'));
         putValue(Action.SHORT_DESCRIPTION,
            "Set ownship simulation position.");
      }

      public void actionPerformed (ActionEvent event) {      
            OwnshipUpdate ownshipUpdate = ownshipController.getOwnshipUpdate ();
            PositionData ownshipPosition = new PositionData(
               ownshipUpdate.latitude, ownshipUpdate.longitude);
            
            SetPositionPanel setPositionPanel = new SetPositionPanel(ownshipPosition);
            ownshipPosition = setPositionPanel.showDialog (ApplicationFrame.this);
            if (ownshipPosition != null) {
               ownshipController.setPosition (ownshipPosition);
            }
      }    
   }
   
   private class HeadingSpeedAction extends AbstractAction {

	private static final long serialVersionUID = 3316644989700778260L;

	public HeadingSpeedAction () {
         putValue(Action.NAME, "Set Heading and Speed");
         putValue(Action.MNEMONIC_KEY, new Integer('H'));
         putValue(Action.SHORT_DESCRIPTION,
            "Set ownship simulation heading and speed.");
      }

      public void actionPerformed (ActionEvent event) {    
            OwnshipUpdate ownshipUpdate = ownshipController.getOwnshipUpdate ();
            HdgSpdData ownshipHdgSpd = new HdgSpdData(
               ownshipUpdate.headingActual,
               ownshipUpdate.speedActual);
            
            SetHeadingSpeedPanel hdgSpdPanel = new SetHeadingSpeedPanel(ownshipHdgSpd);
            ownshipHdgSpd = hdgSpdPanel.showDialog (ApplicationFrame.this);
            if (ownshipHdgSpd != null) {
               ownshipController.setHeadingSpeed (ownshipHdgSpd);
            }
      }    
   }
   
   private class SetDriftAction extends AbstractAction {

	private static final long serialVersionUID = -139509496550068836L;

	public SetDriftAction () {
         putValue(Action.NAME, "Set Set and Drift");
         putValue(Action.MNEMONIC_KEY, new Integer('S'));
         putValue(Action.SHORT_DESCRIPTION,
            "Set ownship simulation set and drift.");
      }

      public void actionPerformed (ActionEvent event) {    
            OwnshipUpdate ownshipUpdate = ownshipController.getOwnshipUpdate ();
            SetDriftData ownshipSetDrift = new SetDriftData(
               ownshipUpdate.set,
               ownshipUpdate.drift);
            
            SetSetDriftPanel setDriftPanel = new SetSetDriftPanel(ownshipSetDrift);
            ownshipSetDrift = setDriftPanel.showDialog (ApplicationFrame.this);
            if (ownshipSetDrift != null) {
               ownshipController.setSetDrift (ownshipSetDrift);
            }
      }    
   }
   
   private class OpenPortAction extends AbstractAction {

	private static final long serialVersionUID = 4679018447426896231L;

	public OpenPortAction () {
         putValue(Action.NAME, "Open Port");
         putValue(Action.MNEMONIC_KEY, new Integer('O'));
         putValue(Action.SHORT_DESCRIPTION,
            "Open a serial communications port.");
      }

      public void actionPerformed (ActionEvent event) {    
         int i = tabbedPane.indexOfTab ("Serial Comms");
         tabbedPane.setSelectedIndex (i);
         
         serialControllerPanel.openSerialPort ();
      }    
   }
   
   private class CloseAllPortsAction extends AbstractAction {

	private static final long serialVersionUID = -7937261224192647268L;

	public CloseAllPortsAction () {
         putValue(Action.NAME, "Close All Ports");
         putValue(Action.MNEMONIC_KEY, new Integer('C'));
         putValue(Action.SHORT_DESCRIPTION,
            "Close all serial ports opened by this MDL application.");
      }

      public void actionPerformed (ActionEvent event) {    
         int i = tabbedPane.indexOfTab ("Serial Comms");
         tabbedPane.setSelectedIndex (i);
         
         serialControllerPanel.closeAllSerialPorts ();
      }    
   }
   
   private class OpenServerAction extends AbstractAction {

	private static final long serialVersionUID = 2324506449739244153L;

	public OpenServerAction () {
         putValue(Action.NAME, "Open Server");
         putValue(Action.MNEMONIC_KEY, new Integer('O'));
         putValue(Action.SHORT_DESCRIPTION,
            "Open a network server port.");
      }

      public void actionPerformed (ActionEvent event) {    
         int i = tabbedPane.indexOfTab ("Network Comms");
         tabbedPane.setSelectedIndex (i);
         
         networkControllerPanel.createNewServer ();
      }    
   }
   
   private class DebugWindowAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public DebugWindowAction () {
         putValue(Action.NAME, "Show Debug Window");
         putValue(Action.MNEMONIC_KEY, new Integer('S'));
         putValue(Action.SHORT_DESCRIPTION,
            "Show and hide the debug window.");
         setEnabled(true);
      }

      public void actionPerformed (ActionEvent event) {
         if (debugWindowVisible) {
            Container thisContentPane = getContentPane();
            thisContentPane.remove (debugListenerPanel);
            thisContentPane.validate ();
            debugWindowVisible = false;
            putValue(Action.NAME, "Show Debug Window");
         }
         else {
            Container thisContentPane = getContentPane();
            thisContentPane.add(debugListenerPanel, BorderLayout.SOUTH);
            thisContentPane.validate ();
            debugWindowVisible = true;
            putValue(Action.NAME, "Hide Debug Window");
         }
      }    
   }
   
   /* note: the following code is never called in the current code version
   private class OptionsAction extends AbstractAction {

      public OptionsAction () {
         fileLoc = urlLoader.getResource("gui/images/Preferences16.gif");
         putValue(Action.NAME, "Options");
         putValue(Action.MNEMONIC_KEY, new Integer('O'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }         putValue(Action.SHORT_DESCRIPTION,
            "Set current options.");
         setEnabled(false);
      }

      public void actionPerformed (ActionEvent event) {
      }    
   }
*/
   
   /* note: the following code is never called in the current code version
   private class HelpAction extends AbstractAction {

      public HelpAction () {
         fileLoc = urlLoader.getResource("gui/images/Help16.gif");
         putValue(Action.NAME, "Help");
         putValue(Action.MNEMONIC_KEY, new Integer('H'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }         putValue(Action.SHORT_DESCRIPTION,
            "Help for operating this application.");
         setEnabled(false);
      }

      public void actionPerformed (ActionEvent event) {
         
      }    
   }
*/
   
   private class FunctionalDiagramAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public FunctionalDiagramAction () {
         fileLoc = urlLoader.getResource("gui/images/Blank16.gif"); 
         putValue(Action.NAME, "Functional Diagram");
         putValue(Action.MNEMONIC_KEY, new Integer('F'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }         putValue(Action.SHORT_DESCRIPTION,
            "MDL Functional Diagram Description.");
      }

      public void actionPerformed (ActionEvent event) {
         FuntionalDiagramPanel funtionalDiagramPanel = new FuntionalDiagramPanel();
         funtionalDiagramPanel.showDialog (ApplicationFrame.this);
      }    
   }
   
   private class AboutAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public AboutAction () {
         fileLoc = urlLoader.getResource("gui/images/Blank16.gif"); 
         putValue(Action.NAME, "About");
         putValue(Action.MNEMONIC_KEY, new Integer('A'));
         if (fileLoc != null) {
            putValue(Action.SMALL_ICON, new ImageIcon(fileLoc));
         }         putValue(Action.SHORT_DESCRIPTION,
            "About this application.");
      }

      public void actionPerformed (ActionEvent event) {
         String msgString = new String (
            "Maritime Digital Laboratory (MDL)\n" +
            "Copyright (c) 2005 Jeffrey Stewart\n\n" +
            "Licensed under the Open Software License version 2.1\n" +
            "(Re: www.opensource.org)\n\n" +
            "Version: " + VERSION );
            
         JOptionPane.showMessageDialog (ApplicationFrame.this, msgString, 
            "About MDL", JOptionPane.INFORMATION_MESSAGE);
         
      }    
   }
}
/* 
 * Version history
 * 1.3.0.0 - Added ability to set set and drift menu capability.  Removed 
 * unused, or disabled, menu items and buttons.
 */