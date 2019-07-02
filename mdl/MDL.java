/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: MDL.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl;

import javax.swing.*;
import java.util.*;
import mdl.data.*;
import mdl.debug.*;
import mdl.gui.*;
import mdl.gui.dialog.*;
import mdl.prefs.*;
import serialComms.*;
import serialComms.serialInterface.*;
import serialComms.gui.dialog.*;
import networkComms.*;
import dashboard.*;
import mdl.simulator.*;
import mdl.simulator.ships.*;
/**
 * This class is initiates the Maritime Digital Lab (MDL) application.
 *
 * It uses the following objects:
 *
 * @see simulator.OwnshipController
 * @see gui.ApplicationFrame
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2004-06-01
 */
public class MDL {
   static final int SPLASH_DISPLAY_TIME = 4000; // in milliseconds
   
   DebugManager debugManager;
   
   SerialController serialController;
   NetworkController networkController;
   OwnshipController ownshipController;
   PositionData position; 
   HdgSpdData hdgSpd;
   DataFactory dataFactory;
   DashboardController dashboardController;

   boolean startSimulator;
   boolean openPort;
   
   public MDL () {
      //PreferenceManager.getApplicationDirectory ();
      //PreferenceManager.loadGUIPrefs ();
      
      OwnshipUpdate ownshipUpdate = PreferenceManager.loadOwnshipData ();
      
      debugManager = new DebugManager();
      
      ownshipController = new OwnshipController (new DefaultShip(), debugManager);
      ownshipController.setTimeDate (new Date(System.currentTimeMillis ()));
      
      position = new PositionData (ownshipUpdate.latitude, ownshipUpdate.longitude);
      ownshipController.setPosition (position);
      
      hdgSpd = new HdgSpdData (ownshipUpdate.headingActual, ownshipUpdate.speedActual);
      ownshipController.setHeadingSpeed (hdgSpd);
      
      dataFactory = PreferenceManager.loadManualData ();
      
      ownshipController.addOwnshipUpdateListener (dataFactory);
      
      dashboardController = new DashboardController (!DashboardController.RUN_AS_STANDALONE_APPLICATION);
      
      serialController = new SerialController (dataFactory, dashboardController);
      networkController = new NetworkController (dataFactory, dashboardController);
      startSimulator = false;
      openPort = false;
      
   }
   
   /**
    *
    */
   int displayStartUpPanel (JFrame frame) {
      StartUpPanel startUpPanel = new StartUpPanel();
      return startUpPanel.showDialog (frame);
   }
   
   /**
    *
    */
   void startSimulation (JFrame frame) {
      int shipType;
      SetShipTypePanel setShipTypePanel = new SetShipTypePanel();
   
      shipType = setShipTypePanel.showDialog (frame);
      if ((shipType == SetShipTypePanel.CANCEL_ACTION) ||
          (shipType == SetShipTypePanel.BACK_ACTION)) {
          return;
      }
      else {
	 AbstractShip simulatedShip = (AbstractShip) ShipTypes.getShipObject (shipType);
         ownshipController = new OwnshipController (simulatedShip, debugManager);
         ownshipController.addOwnshipUpdateListener (dataFactory);
      }

      Date ownshipTime = new Date();
      SetTimeDatePanel setTimeDatePanel = new SetTimeDatePanel(ownshipTime);
      ownshipTime = setTimeDatePanel.showDialog (frame);
      if (ownshipTime != null) {
         ownshipController.setTimeDate (ownshipTime);
      }
      else {
         return;
      }
      
      PositionData ownshipPosition = position;
      SetPositionPanel setPositionPanel = new SetPositionPanel(ownshipPosition);
      ownshipPosition = setPositionPanel.showDialog (frame);
      if (ownshipPosition != null) {
         ownshipController.setPosition (ownshipPosition);
      }
      else {
         return;
      }

      HdgSpdData ownshipHdgSpd = hdgSpd;
      SetHeadingSpeedPanel hdgSpdPanel = new SetHeadingSpeedPanel(ownshipHdgSpd);
      ownshipHdgSpd = hdgSpdPanel.showDialog (frame);
      if (ownshipHdgSpd != null) {
         ownshipController.setHeadingSpeed (ownshipHdgSpd);
         startSimulator = true;
      }
      else {
         return;
      }

      SerialPortInfo serialPortInfo;
      OpenSerialPortPanel portPanel = new OpenSerialPortPanel(
         serialController.getSerialPortNames ());
      serialPortInfo = portPanel.showDialog (frame);
      if (serialPortInfo != null) {
         //Now try to open the port!
      }
      else {
         return;
      }
   }
   
   /**
    *
    */
   void startSerialPort () {
      openPort = true;      
   }
   
   /** 
    * Starts the MDL application. 
    *
    * @param arg read but not used.
    */
   public static void main (String [] arg){         
      MDL thisApplication = new MDL();
      JFrame startUpFrame = new JFrame("Start MDL");
      
      int startUpMode = 0;
      startUpMode = thisApplication.displayStartUpPanel(startUpFrame);
      if (startUpMode == StartUpPanel.START_SIMULATOR) {
         thisApplication.startSimulation (startUpFrame);
      }
      else if (startUpMode == StartUpPanel.START_SERIAL_PORT) {
         thisApplication.startSerialPort ();
      }
      else if (startUpMode == StartUpPanel.START_APPLICATION) {
         // Do nothing
      }
      else {
         //"Unable to determine start mode."
      }
      startUpFrame.dispose ();
      
//      if (thisApplication.ownshipController == null) {
//         thisApplication.ownshipController = new OwnshipController (new DefaultShip(), 
//            thisApplication.debugManager);
//         thisApplication.ownshipController.addOwnshipUpdateListener (thisApplication.dataFactory);
//      }
      
      ApplicationFrame applicationFrame = 
         new ApplicationFrame(thisApplication.ownshipController, 
                              thisApplication.serialController,
                              thisApplication.networkController, 
                              thisApplication.dataFactory,
                              thisApplication.debugManager,
                              thisApplication.dashboardController);
      if (thisApplication.startSimulator) {
         applicationFrame.eventStartAction();
      }
      if (thisApplication.openPort) {
         applicationFrame.eventOpenPort();
      }
   }
}