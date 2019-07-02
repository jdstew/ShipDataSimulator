/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: TransmitListListener.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.gui;

import java.util.*;
/**
 * This interface defines a tramitted sentence list listener.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public interface NetworkControllerUIListener extends EventListener {
   public void updateStatus(String status);
}
