/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: OwnshipUpdateListener.java
 * Created: 2005-01-01, 12:01:01
 */
package dashboard.testSimulator;

import java.util.*;
/**
 * This interface defines an ownship update listener.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public interface OwnshipUpdateListener extends EventListener {
   void updateOwnship (OwnshipUpdate ownshipUpdate);
}
