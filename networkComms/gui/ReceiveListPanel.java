/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: ReceiveListPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.text.*;
import networkComms.messages.*;
/**
 * This panel provides a container for a network port receive list table.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class ReceiveListPanel extends JPanel implements ReceiveListListener {

	private static final long serialVersionUID = 1L;
	MessageManager messageManager;
   ReceiveTableModel receiveTableModel;

   JFrame parentFrame;
     
   DecimalFormat freqForm = new DecimalFormat("#.0");
     
   JButton clearListButton;
   
   /**
    * Initializes an object of this class.
    *
    * @param sentMngr The sentence manager for the received sentence list.
    */
   public ReceiveListPanel (MessageManager sentMngr) {
      receiveTableModel = new ReceiveTableModel();
      
      messageManager = sentMngr;
      messageManager.addReceiveListListener (this);
      
      setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));
      
      Box controlBox = Box.createHorizontalBox ();
      controlBox.setBorder (BorderFactory.createEmptyBorder (4, 4, 4, 4));
      
      clearListButton = new JButton ("Clear Receive List");
      clearListButton.setToolTipText ("Remove current list and reset receive statistics.");
      clearListButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               messageManager.resetReceivingMessages ();
            }
         });
      controlBox.add(clearListButton);
      controlBox.setAlignmentX (Component.LEFT_ALIGNMENT);
      add(controlBox);
      
      JTable table = new JTable(receiveTableModel);
      table.setAutoResizeMode (JTable.AUTO_RESIZE_ALL_COLUMNS);
      
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setAlignmentX (Component.LEFT_ALIGNMENT);
      add(scrollPane);
   } 

   /**
    * Update the information in the table of received sentences.
    *
    * @param updatedList List of sentences types received.
    */
   public void updateList(Vector<ReceivingMessage> updatedList) {
      if (updatedList != null) {
         receiveTableModel.updateList (updatedList);
      }
   }
}
