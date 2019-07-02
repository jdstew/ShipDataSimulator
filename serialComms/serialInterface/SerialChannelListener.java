/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SerialChannelListener.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.serialInterface;

import java.util.*;
/**
 * This interface defines serial channel listener objects.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public interface SerialChannelListener extends EventListener {
   void receiveCharacter(char c);
}