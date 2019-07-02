/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: GUIPrefs.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.prefs;

import java.awt.*;
import java.io.*;
/**
 * An object of this class stores the user interface preferences
 * for font and colors.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class GUIPrefs implements Serializable {

	private static final long serialVersionUID = 1L;
	/** Default foreground (i.e. text) color for user controllable features. */
   static final Color ACTION_FOREGROUND_DEFAULT = new Color(0, 0, 192);
   /** Default background color for user controllable features. */
   static final Color ACTION_BACKGROUND_DEFAULT = Color.WHITE;
   /** Default user interface font. */
   static final Font FONT_INTERFACE_DEFAULT = new Font("Dialog", Font.PLAIN, 12);
   /** Default output text font. */
   static final Font FONT_OUTPUT_DEFAULT = new Font("Monospaced", Font.PLAIN, 12);
   /** Default font type. */
   static final String FONT_TYPE_DEFAULT = "SansSerif";
   /** Default font style. */
   static final int FONT_STYLE_DEFAULT = Font.PLAIN;
   /** Defualt font size. */
   static final int FONT_SIZE_DEFAULT = 12;

   Color actionForeground;
   Color actionBackground;
   Font interfaceFont;
   Font outputFont;
   
   /** 
    * Creates a new instance of GUIPrefs 
    */
   public GUIPrefs () {
      resetPreferences();
   }
   
   /**
    * Resets the GUI preferences to the default values.
    */
   public void resetPreferences() {
      actionForeground = ACTION_FOREGROUND_DEFAULT;
      actionBackground = ACTION_BACKGROUND_DEFAULT;
      interfaceFont = FONT_INTERFACE_DEFAULT;
      /* Font outputFont = FONT_OUTPUT_DEFAULT; */
   }
   
   /**
    * Sets the foreground (ie text) color for user controllable features.
    *
    * @param color The color for the features.
    */
   public void setActionForeground (Color color) {
      if (color != null) {
         actionForeground = color;
      }
   }
   
   /**
    * Gets the foreground (ie text) color for user controllable features.
    *
    * @return The color for the features.
    */
   public Color getActionForeground () {
      return actionForeground;
   }
   
   /**
    * Sets background color for user controllable features.
    *
    * @param color The color for the features.
    */
   public void setActionBackground (Color color) {
      if (color != null) {
         actionBackground = color;
      }
   }
   
   /**
    * Gets background color for user controllable features.
    *
    * @return The color for the features.
    */
   public Color getActionBackground () {
      return actionBackground;
   }
   
   /**
    * Sets the font for the graphical user interface (GUI).
    *
    * @param font The font to set for the GUI.
    */
   public void setIntefaceFont (Font font) {
      if (font != null) {
         interfaceFont = font;
      }
   }
   
   /**
    * Gets the font for the graphical user interface (GUI).
    *
    * @return The font for the GUI.
    */
   public Font getInterfaceFont () {
      return interfaceFont;
   }
  
   /**
    * Sets the font for the text output areas within the GUI.
    *
    * @param font The text output area font.
    */
   public void setOutputFont (Font font) {
      if (font != null) {
         outputFont = font;
      }
   }
   
   /**
    * Gets the font for the text output areas within the GUI.
    *
    * @return The text output area font.
    */
   public Font getOutputFont () {
      return outputFont;
   }
   
}
