/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentRcvdEventObject.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.debug;

import java.util.*;
/**
 * This interface defines reports made to a debug listener.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public interface DebugListener extends EventListener {
   /** Throwable exception type. */
   public static final int THROWABLE = 0;
   /** Error exception type. */
   public static final int ERROR = 1;
   /** Exception type. */
   public static final int EXCEPTION = 2;
   /** Input/Output exception type. */
   public static final int IOEXCEPTIION = 3;
   /** Object finalization type. */
   public static final int OBJECT_FINALIZE = 4;
   /** Object instantiation type. */
   public static final int OBJECT_INSTANTIATE = 5;
   /** General message type. */
   public static final int GENERAL_MESSAGE = 6;
   /** Code fault type. */
   public static final int CODE_FAULT = 7;
   
   /**
    * Report a message to a debug listener.
    *
    * @param messageType See above message types.
    * @param message Specific message to be displayed or logged.
    */   
   void report (int messageType, String message);
}
