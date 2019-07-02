/*
 * NetworkOperationStatus.java
 *
 * Created on July 10, 2005, 8:37 PM
 */

package networkComms.networkInterface;

/**
 *
 * @author  Stewarts
 */
public class NetworkOperationStatus {
   
   boolean successState;
   String stateMessage;
   
   /** Creates a new instance of NetworkOperationStatus */
   public NetworkOperationStatus (boolean success, String message) {
      successState = success;
      stateMessage = message;
   }
   
   public boolean wasSuccessful () {
      return successState;
   }
   
   public String getMessage () {
      if (stateMessage != null) {
         return stateMessage;
      }
      else {
         return "";
      }
   }
}
