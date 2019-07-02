/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentRcvdListner.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.networkInterface;

import java.util.*;
import networkComms.*;
/**
 * The interface class defines the received sentence listener objects.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public interface ReceivedMessageListener extends EventListener {
   void receiveMessage (ReceivedMessage receivedMessage);
}