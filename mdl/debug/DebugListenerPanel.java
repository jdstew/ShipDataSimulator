/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: DebugListenerPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.debug;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * An object of this class is a JPanel to display debug manager reports.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class DebugListenerPanel extends JPanel implements DebugListener {

	private static final long serialVersionUID = -5925549872944271663L;

	JTextArea textArea;
   
   JButton clearTextButton;
   JButton gotoEndOfTextButton;

   /**
    * Creates and displays the debug listener panel.
    */
   public DebugListenerPanel () {
      setBorder(BorderFactory.createTitledBorder("Debug Panel"));
      setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));
      
      Box buttonBox = new Box(BoxLayout.X_AXIS);
      buttonBox.setBorder (BorderFactory.createEmptyBorder (4, 4, 4, 4));
      buttonBox.setAlignmentX (JComponent.LEFT_ALIGNMENT);
      gotoEndOfTextButton = new JButton ("Goto end of text");
      gotoEndOfTextButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               textArea.setCaretPosition(textArea.getDocument().getLength());
            }
         });
      buttonBox.add(gotoEndOfTextButton);
      
      buttonBox.add(new JLabel(" "));
      
      clearTextButton = new JButton ("Clear text");
      clearTextButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               textArea.setText ("");
            }
         });
      buttonBox.add(clearTextButton);
      buttonBox.add(Box.createHorizontalGlue ());
      add(buttonBox);
      
      textArea = new JTextArea();
      textArea.setColumns (85);
      textArea.setRows(6);
      textArea.setEditable(false);
      Font font = new Font ("Monospaced", Font.PLAIN, 12);
      textArea.setFont (font);
      textArea.setTabSize (3);
      textArea.setEditable (false);
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
    * Receive and display a debug report.
    *
    * @param messageType The message type from DebugListener
    * @param message The specific text message to report.
    */   
   public void report (int messageType, String message) {
      if (messageType == DebugListener.THROWABLE) {
         textArea.append ("THROWABLE: " + message + "\n\n");
      }
      else if (messageType == DebugListener.ERROR) {
         textArea.append ("ERROR: " + message + "\n\n");
      }
      else if (messageType == DebugListener.EXCEPTION) {
         textArea.append ("EXCEPTION: " + message + "\n\n");
      }
      else if (messageType == DebugListener.IOEXCEPTIION) {
         textArea.append ("IOEXCEPTIION: " + message + "\n\n");
      }
      else if (messageType == DebugListener.OBJECT_INSTANTIATE) {
         textArea.append ("INSTANTIATE OBJECT: " + message + "\n\n");
      }
      else if (messageType == DebugListener.OBJECT_FINALIZE) {
         textArea.append ("FINALIZE OBJECT: " + message + "\n\n");
      }
      else if (messageType == DebugListener.GENERAL_MESSAGE) {
         textArea.append ("INFORMATION: " + message + "\n\n");
      }
      else if (messageType == DebugListener.CODE_FAULT) {
         textArea.append ("CODE FAULT: " + message + "\n\n");
      }
      else {
         textArea.append (message + "\n\n");
      }
   }
}
