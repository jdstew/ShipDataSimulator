/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: GUIException.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui;

import java.io.*;
/**
 * An object of this class is used to display application staus notes and faults.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class GUIException extends IOException {

	private static final long serialVersionUID = 19410259524410320L;
	int changeCode = 0;
   String changeValue;
   
   /** 
    * Creates a new instance of GUIException 
    */
   public GUIException () { }
   
   /** 
    * Creates a new instance of GUIException 
    *
    * @param errorMessage The error message to display.
    */
   public GUIException (String errorMessage) {
      super(errorMessage);
   }
   
   /** 
    * Creates a new instance of GUIException 
    *
    * @param code The type of error message.
    * @param errorMessage The error message to display.
    */
   public GUIException (int code, String errorMessage) {
      changeCode = code;
      changeValue = new String(errorMessage);
   }
   
   /**
    * Get the output message of the exception.
    *
    * @return The GUI exception message.
    */
   public String outputException(){
      return (changeValue + ", code: " + changeCode);
   }
}
