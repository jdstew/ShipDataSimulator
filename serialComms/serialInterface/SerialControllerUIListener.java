/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SerialControllerUIListener.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.serialInterface;

import java.util.*;
/**
 * This interface defines the serial controller user interface listener objects.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public interface SerialControllerUIListener extends EventListener {
   void createSerialPortView (SerialPortInfo portInfo);
}
