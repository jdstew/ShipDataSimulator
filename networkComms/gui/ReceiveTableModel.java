/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: ReceiveTableModel.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.gui;

import javax.swing.table.*;
import java.util.*;
import networkComms.messages.*;
/**
 * An object of this class defines a customized Table Model for displaying
 * a list of received sentences.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class ReceiveTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	static final String[] columnNames = {"Message Type",
                                        "Freq (Hz)",
                                        "Latency (s)",
                                        "Total"};
   static final int columnCount = columnNames.length;
   
   Vector<ReceivingMessage> receiveList;

   public ReceiveTableModel () {
      receiveList = new Vector<ReceivingMessage>();
   }
   
   public void updateList(Vector<ReceivingMessage> updatedList) {
      if (updatedList != null) {
         receiveList = updatedList;
      }
      this.fireTableDataChanged ();
   }

   public int getColumnCount () {
      return columnCount;
   }
   
   public int getRowCount () {
      return receiveList.size ();
   }
   
   public String getColumnName(int col) {
     return columnNames[col];
   }

   public Object getValueAt (int rowIndex, int columnIndex) {
      ReceivingMessage item = (ReceivingMessage) receiveList.get (rowIndex);
      if (columnIndex == 0) {
         return item.getMessageName ();
      }
      else if (columnIndex == 1) {
         return item.getReceiveFrequency ();
      }
      else if (columnIndex == 2) {
         return item.getReceiveLatency ();
      }
      else if (columnIndex == 3) {
         return item.getReceiveTotal ();
      }
      else {
         return new String ("?");
      }
   }
}
