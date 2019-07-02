/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: OwnshipSimulationListener.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.simulator;

import java.util.*;
import mdl.data.*;
/**
 * This interface defines the ownship simulation update listener.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public interface OwnshipSimulationListener extends EventListener {
   public void updateOwnship (OwnshipUpdate ownshipUpdate);
}
