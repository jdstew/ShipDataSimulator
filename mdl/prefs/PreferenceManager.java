/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: PreferenceManager.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.prefs;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.net.*;
import mdl.*;
import mdl.data.*;
/**
 * An object of this class manages the user preferences.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class PreferenceManager {
   
   /** Default file name for user preferences. 
   private static final String PREF_DEFAULT_FILENAME = "prefs.dat";*/
   
   /** File name for graphical user interface preferences. */
   private static final String GUI_PREFS_FILENAME = "guiprefs.dat";
   
   /** File name for stored manual data. */
   private static final String DATA_FACTORY_FILENAME = "mandata.dat";
   
   /** File name for stored ownship data. */
   private static final String OWNSHIP_FILENAME = "ownship.dat";
   
   
   static String applicationDirectory = null;
   static GUIPrefs guiPrefs;
   
   /**
    * Determines the directory location of the application.
    *
    * @return The directory location of the application.
    */
   public static String getApplicationDirectory () {
      if (applicationDirectory != null) {
         return applicationDirectory;
      }
      else {
         /* PreferenceManager thisObject = new PreferenceManager(); */
         /* URLClassLoader urlClassLoader = (URLClassLoader) thisObject.getClass().getClassLoader(); */
         URL urlObj = MDL.class.getResource("MDL.class");
         
         // Decode URL to remove unfortunate side effects (e.g. "%20").
         String mainClassPath;
         try {
            mainClassPath = URLDecoder.decode (urlObj.getPath (), "UTF-8");
            System.out.println ("MDL.class path = " + mainClassPath);
         }
         catch (UnsupportedEncodingException err) {
            System.out.println ("Unable to decode MDL.class path; " + err.toString ());
            applicationDirectory = null;
            return null;
         }
         
         String tempAppDir = null;
         
         // Remove URL 'file:' specific protocol, if present (for use with .jar app)
         final String FILE_PROTOCOL = "file:";
         int truncateIndex = mainClassPath.indexOf (FILE_PROTOCOL);
         if (truncateIndex == 0) {
            tempAppDir = mainClassPath.substring (FILE_PROTOCOL.length (), 
               mainClassPath.length ());
         }
         else {
            tempAppDir = mainClassPath;
         }
         
         // Determine path if application is run from a .jar file.
         final String JAR_NAME = "mdl.jar";
         truncateIndex = tempAppDir.indexOf (JAR_NAME);
         if (truncateIndex > 0) {
            tempAppDir = tempAppDir.substring (0, truncateIndex);
            System.out.println (".jar file use detected, application path = " +
               tempAppDir);
         }
         
         // Determine path if application is run from a .class file.
         final String CLASS_NAME = "MDL.class";
         truncateIndex = tempAppDir.indexOf (CLASS_NAME);
         if (truncateIndex > 0) {
            tempAppDir = tempAppDir.substring (0, truncateIndex);
            System.out.println (".class file use detected, application path = " +
               tempAppDir);
         }
         
         if (tempAppDir != null) {
            applicationDirectory = tempAppDir;
            return applicationDirectory;
         }
         else {
            applicationDirectory = null;
            return null;
         }
      }
   }
   
   /**
    * Provides an OwnshipUpdate object from storage, or a new object if not
    * found.
    *
    * @return A OwnshipUpdate from storage.
    */
   public static OwnshipUpdate loadOwnshipData() {
//      URLClassLoader urlLoader = (URLClassLoader) mdlApp.getClass().getClassLoader();
//      URL fileLoc = urlLoader.getResource("data/ownship.dat"); 
//
//      if (fileLoc != null) {
      
      OwnshipUpdate ownshipUpdate;
      
      File fileReference = new File (getApplicationDirectory() + File.separatorChar + 
                              OWNSHIP_FILENAME);
//                            "data" + File.separatorChar + OWNSHIP_FILENAME);
      if (fileReference.exists ()) {
         try {
            FileInputStream inputFile = new FileInputStream (fileReference.getCanonicalFile ());
            ObjectInputStream inputStream = new ObjectInputStream(inputFile);
            ownshipUpdate = (OwnshipUpdate) inputStream.readObject ();
            inputFile.close ();
         }
         catch (Exception exception) {
            exception.printStackTrace ();
            return new OwnshipUpdate();
         }
         
         return ownshipUpdate;
      }
      else {
         return new OwnshipUpdate();
      }
   }
   
   /**
    * Saves n OwnshipUpdate object to storage.
    *
    * @param ownshipUpdate An OwnshipUpdate to store.
    */
   public static void saveOwnshipData(OwnshipUpdate ownshipUpdate) {
      File fileReference = new File (getApplicationDirectory() + File.separatorChar + 
                              OWNSHIP_FILENAME);
//                            "data" + File.separatorChar + OWNSHIP_FILENAME);
                            
      if (ownshipUpdate != null) {
         try {
            FileOutputStream outputFile = new FileOutputStream (fileReference);
            ObjectOutputStream outputStream = new ObjectOutputStream(outputFile);
            outputStream.writeObject (ownshipUpdate);
            outputStream.close ();
            outputFile.close ();
         }
         catch (Exception exception) {
            exception.printStackTrace ();
         }
      }
   }
   
   /**
    * Provides a DataFactory object from storage, or a new object if not
    * found.
    *
    * @return A DataFactory from storage.
    */
   public static DataFactory loadManualData() {
      DataFactory dataFactory;
      
      File fileReference = new File (getApplicationDirectory() + File.separatorChar + 
                              DATA_FACTORY_FILENAME);
//                              "data" + File.separatorChar + DATA_FACTORY_FILENAME);
                              
      if (fileReference.exists ()) {
         try {
            FileInputStream inputFile = new FileInputStream (fileReference);
            ObjectInputStream inputStream = new ObjectInputStream(inputFile);
            dataFactory = (DataFactory) inputStream.readObject ();
            inputFile.close ();
         }
         catch (Exception exception) {
            exception.printStackTrace ();
            return new DataFactory();
         }
         
         return dataFactory;
      }
      else {
         return new DataFactory();
      }
   }
   
   /**
    * Saves a DataFactory object to storage.
    *
    * @param dataFactory A DataFactory to store.
    */
   public static void saveManualData(DataFactory dataFactory) {
      File fileReference = new File (getApplicationDirectory() + File.separatorChar + 
                              DATA_FACTORY_FILENAME);
//                              "data" + File.separatorChar + DATA_FACTORY_FILENAME);
                                
      if (dataFactory != null) {
         try {
            FileOutputStream outputFile = new FileOutputStream (fileReference);
            ObjectOutputStream outputStream = new ObjectOutputStream(outputFile);
            outputStream.writeObject (dataFactory);
            outputStream.close ();
            outputFile.close ();
         }
         catch (Exception exception) {
            exception.printStackTrace ();
         }
      }
   }

   /**
    * Loads the GUI properties for the application.
    */
   public static void loadGUIPrefs() {
      File fileReference = new File (getApplicationDirectory() + File.separatorChar + 
                              GUI_PREFS_FILENAME);
//                              "prefs" + File.separatorChar + GUI_PREFS_FILENAME);
      
      if (fileReference.exists ()) {
         try {
            FileInputStream inputFile = new FileInputStream (fileReference);
            ObjectInputStream inputStream = new ObjectInputStream(inputFile);
            guiPrefs = (GUIPrefs) inputStream.readObject ();
            inputFile.close ();
         }
         catch (Exception exception) {
            exception.printStackTrace ();
         }
      }
      else {
         guiPrefs = new GUIPrefs();
         
      }
      
      setGUIPrefs(guiPrefs);
   }

   /**
    * Sets the GUI properties for the application.
    *
    * @param prefs The GUI preferences for the application.
    */
   public static void setGUIPrefs(GUIPrefs prefs) {
      Color actionForeground = prefs.getActionForeground ();
      Color actionBackground = prefs.getActionBackground ();
      Font interfaceFont = prefs.getInterfaceFont ();
      Font outputFont = prefs.getOutputFont ();
      
      UIManager.put("Button.foreground", actionForeground);
      UIManager.put("Button.background", actionBackground);
      UIManager.put("Button.font", interfaceFont);
      
      UIManager.put("ComboBox.foreground", actionForeground);
      UIManager.put("ComboBox.background", actionBackground);
      UIManager.put("ComboBox.font", interfaceFont);
      
      UIManager.put("Spinner.foreground", actionForeground);
      UIManager.put("Spinner.font", outputFont);
      
      UIManager.put("Menu.font", interfaceFont);
      UIManager.put("MenuBar.font", interfaceFont);
      UIManager.put("MenuItem.font", interfaceFont);
      UIManager.put("TextField.font", outputFont);
      UIManager.put("Label.font", interfaceFont);
      UIManager.put("TabbedPane.font", interfaceFont);
      
      UIManager.put("CheckBox.foreground", actionForeground);
      UIManager.put("CheckBox.background", actionBackground);
      UIManager.put("CheckBox.font", interfaceFont);
      
      UIManager.put("Table.font", interfaceFont);
      UIManager.put("TableHeader.font", interfaceFont);
      
      UIManager.put("RadioButton.foreground", actionForeground);
      UIManager.put("RadioButton.background", actionBackground);
      UIManager.put("RadioButton.font", interfaceFont);
   }

   /**
    * Stores the GUI properties for the application.
    */
   public static void saveGUIPrefs() {
      File fileReference = new File (getApplicationDirectory() + File.separatorChar + 
                              GUI_PREFS_FILENAME);
//                              "prefs" + File.separatorChar + GUI_PREFS_FILENAME);
                     
      if (guiPrefs != null) {
         try {
            FileOutputStream outputFile = new FileOutputStream (fileReference);
            ObjectOutputStream outputStream = new ObjectOutputStream(outputFile);
            outputStream.writeObject (guiPrefs);
            outputStream.close ();
            outputFile.close ();
         }
         catch (Exception exception) {
            exception.printStackTrace ();
         }
      }
   } 
}
