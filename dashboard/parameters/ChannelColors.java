/*
 * ChannelColors.java
 *
 * Created on June 6, 2005, 8:34 PM
 */

package dashboard.parameters;

import  java.awt.*;

/**
 *
 * @author  Stewarts
 */
public class ChannelColors implements java.util.Enumeration<Color> {
   
   float [] hueList; 
   int hueIndex;
   
   /** Creates a new instance of ChannelColors */
   public ChannelColors () {
      hueList = new float [] {   0.0f, // red
                             43.0f, // yellow
                             85.0f, // green
                            128.0f, // indigo
                            171.0f, // blue
                            213.0f, // violet
                             21.0f, // orange
                             64.0f, // lime
                            106.0f, // olive
                            149.0f, // pink
                            192.0f, // purple
                            234.0f }; // fushia
                            
      hueIndex = 0;
   }
   
   public boolean hasMoreElements () {
      if (hueIndex < hueList.length) {
         return true;
      }
      else {
         return false;
      }
   }
   
   public Color nextElement () {
      if (this.hasMoreElements ()) {
         return java.awt.Color.getHSBColor ( (hueList[hueIndex++] / 255.0f), 
            1.0f, 1.0f);
      }
      else {
         return java.awt.Color.GRAY;
      }
   }
   
    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) {
        ChannelColors channelColors = new ChannelColors();
        int index = 0;
        while (channelColors.hasMoreElements ()) {
           java.awt.Color newColor = (java.awt.Color) channelColors.nextElement ();
           System.out.println ("color==>" + newColor.getRed () + "|" +
              newColor.getGreen () + "|" + newColor.getBlue () + "[" + index++ + "]");
        }
    }
}
