/*
 * ConnectionParameters.java
 *
 * Created on April 23, 2005, 5:53 PM
 */
package networkComms.gui.dialog;

import java.net.*;
/**
 *
 * @author Stewarts
 */
public class UDPServerParameters {
   
   public static final String DEFAULT_SERVER_NAME = "COMDAC-INS NM";
   public static final String DEFAULT_LOCAL_INETADDRESS = "211.000.000.102";
   public static final int DEFAULT_LOCAL_PORT = 6040;
   public static final String DEFAULT_REMOTE_INETADDRESS = "211.000.000.101";
   public static final int DEFAULT_REMOTE_PORT = 6040;
   
   
   protected String connectionName;
   
   protected NetworkInterface networkInterface;
   
   protected InetAddress localInternetAddress;
   protected int localPort;
   
   protected InetAddress remoteInternetAddress;
   protected int remotePort;
   
   public UDPServerParameters () {
      connectionName = DEFAULT_SERVER_NAME;
      try {
         remoteInternetAddress = InetAddress.getByName (DEFAULT_REMOTE_INETADDRESS);
      }
      catch (UnknownHostException error) {
         System.out.println ("UDPServerParamenters calls error..." + error.toString ());
      }
      remotePort = DEFAULT_REMOTE_PORT;
   }
   
   public void setConnectionName (String name) {
      if (name != null) {
         connectionName = name;
      }
   }
   
   public String getConnectionName () {
      return connectionName;
   }
   
   public void setNetworkInterface (NetworkInterface ni) {
      if (ni != null) {
         networkInterface = ni;
      }
   }
   
   public NetworkInterface getNetworkInterface () {
      return networkInterface;
   }
   
   public void setLocalInternetAddress (InetAddress localIA) {
      if (localIA != null) {
         localInternetAddress = localIA;
      }
   }
   
   public InetAddress getLocalInternetAddress () {
      return localInternetAddress;
   }
   
   public void setLocalPort (int port) {
      if ( (port > 0) && (port <= 65536) ) {
         localPort = port;
      }
   }
   
   public int getLocalPort () {
      return localPort;
   }
   
   public void setRemoteInternetAddress (InetAddress remoteIA) {
      if (remoteIA != null) {
         remoteInternetAddress = remoteIA;
      }
   }
   
   public InetAddress getRemoteInternetAddress () {
      return remoteInternetAddress;
   }
   
   public void setRemotePort (int port) {
      if ( (port > 0) && (port <= 65536) ) {
         remotePort = port;
      }
   }
   
   public int getRemotePort () {
      return remotePort;
   }
}
