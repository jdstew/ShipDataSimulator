/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: DefaultTextOutputPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * An instance of this class creates a display window for outputting data.
 * This class is extended for displaying tranmitted data, received data, and
 * received data processing errors.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class DefaultTextOutputPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int MAX_DISPLAY_LINES = 128;
   public static final float LINE_TRIM_PERCENT = 0.33f;
   
   JTextArea textArea;
   
   JButton clearTextButton;
   JButton gotoEndOfTextButton;

   /**
    * Creates a default text output panel.
    */
   public DefaultTextOutputPanel () {
      
      setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));
      
      Box buttonBox = new Box(BoxLayout.X_AXIS);
      buttonBox.setAlignmentX (JComponent.LEFT_ALIGNMENT);
     
         clearTextButton = new JButton ("Clear text");
         clearTextButton.setToolTipText ("Delete the text contained in the output window.");
         clearTextButton.addActionListener (new
            ActionListener () {
               public void actionPerformed (ActionEvent event) {
                  textArea.setText ("");
               }
            });
            
      buttonBox.add(clearTextButton);
      buttonBox.add (Box.createHorizontalGlue ());
      add(buttonBox);
      
      textArea = new JTextArea();
      textArea.setColumns (85);
      textArea.setRows(15);
      textArea.setEditable(false);
      Font font = new Font ("Monospaced", Font.PLAIN, 12);
      textArea.setFont (font);
      textArea.setTabSize (3);
      textArea.setLineWrap (true);
      textArea.setWrapStyleWord (true);
      
      JScrollPane scrollPane = 
          new JScrollPane(textArea,
                          JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                          JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

      scrollPane.setAlignmentX (JComponent.LEFT_ALIGNMENT);
      add(scrollPane);
   }
   
   /**
    * Displays a new 'chunk' of data to the output text display area.
    *
    * @param data Data to append to the display.
    */
   public void writeData (String data) {
      if (textArea.getLineCount () > MAX_DISPLAY_LINES) {
         textArea.replaceRange (null, 0, 
            (int)(textArea.getText ().length () * LINE_TRIM_PERCENT));
      }
      textArea.append (data);
      textArea.setCaretPosition(textArea.getDocument().getLength());
   }
   
   public void writeData (byte [] buffer) {
      if (buffer.length < 128) {
         String data = new String (buffer);
         this.writeData(data);
      }
      else {
         this.writeData("  [Error: data length out of bounds.]   \n");
      }
   }
}