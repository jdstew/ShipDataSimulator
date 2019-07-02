/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: DebugListenerConsole.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.debug;

/**
 * An object of this class displays debug reports to the default console.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class DebugListenerConsole implements DebugListener {
   
   /**
    * Displays a debug report.
    *
    * @param messageType Message type from DebugListener.
    * @param message Specific text message of the report.
    */   
   public void report (int messageType, String message) {
      if (messageType == DebugListener.THROWABLE) {
         System.out.println ("THROWABLE: " + message + "\n");
      }
      else if (messageType == DebugListener.ERROR) {
         System.out.println ("ERROR: " + message + "\n");
      }
      else if (messageType == DebugListener.EXCEPTION) {
         System.out.println ("EXCEPTION: " + message + "\n");
      }
      else if (messageType == DebugListener.IOEXCEPTIION) {
         System.out.println ("IOEXCEPTIION: " + message + "\n");
      }
      else if (messageType == DebugListener.OBJECT_INSTANTIATE) {
         System.out.println ("INSTANTIATE OBJECT: " + message + "\n");
      }
      else if (messageType == DebugListener.OBJECT_FINALIZE) {
         System.out.println ("FINALIZE OBJECT: " + message + "\n");
      }
      else if (messageType == DebugListener.GENERAL_MESSAGE) {
         System.out.println ("INFORMATION: " + message + "\n");
      }
      else if (messageType == DebugListener.CODE_FAULT) {
         System.out.println ("CODE FAULT: " + message + "\n");
      }
      else {
         System.out.println (message + "\n");
      }
   }
}
