/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: DebugManager.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.debug;

import java.util.*;
/**
 * An object of this type manages communications from sources to listeners.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class DebugManager {   
   private Vector<DebugListener> debugListeners = new Vector<DebugListener>();

   /**
    * Sets the output of the debug manager to the default output console.
    */
   public void setOutputToConsole () {
      this.addDebugListener (new DebugListenerConsole());
   }
     
   /**
    * Adds a debug listener, which should generally be a 
    * DebugListenerPanel object.
    *
    * @param listener The listener of debug reports.
    */   
   public synchronized void addDebugListener (DebugListener listener) {
      debugListeners.addElement(listener);
   }
   
   /**
    * Removes a debug listener, which should generally be a 
    * DebugListenerPanel object.
    *
    * @param listener The listener of debug reports.
    */   
   public synchronized void removeDebugListener (DebugListener listener) {
      debugListeners.remove(listener);
   }
   
   /**
    * Relays the received debug report to the listeners.
    *
    * @param messageType Message type from DebugListener.
    * @param message Specific message to report.
    */   
   public void report (int messageType, String message) {
      for (int i = 0; i < debugListeners.size(); i++) {
        DebugListener listener = (DebugListener) debugListeners.elementAt (i);
        listener.report (messageType, message);
      } 
   }
}
