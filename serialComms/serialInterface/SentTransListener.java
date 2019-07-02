/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentTransListener.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.serialInterface;

import java.util.*;
/**
 * The interface class defines the transmitted sentence listener objects.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public interface SentTransListener extends EventListener {
   void transmitSentence (String sentence);
}