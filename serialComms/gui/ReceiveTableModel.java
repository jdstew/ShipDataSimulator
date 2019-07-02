/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: ReceiveTableModel.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.gui;

import javax.swing.table.*;
import java.util.*;
import serialComms.sentences.*;
/**
 * An object of this class defines a customized Table Model for displaying
 * a list of received sentences.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class ReceiveTableModel extends AbstractTableModel {
   
	private static final long serialVersionUID = 1L;
	static final String[] columnNames = {"Formatter",
                                        "Quality",
                                        "Freq (Hz)",
                                        "Late (sec)",
                                        "Total",
                                        "Bad",
                                        "Suspect"};
   static final int columnCount = columnNames.length;
   
   Vector<ReceivingSentence> receiveList;

   public ReceiveTableModel () {
      receiveList = new Vector<ReceivingSentence>();
   }
   
   public void updateList(Vector<ReceivingSentence> updatedList) {
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
      ReceivingSentence item = (ReceivingSentence) receiveList.get (rowIndex);
      if (columnIndex == 0) {
         return item.getFormatter ();
      }
      else if (columnIndex == 1) {
         return item.getSentenceQuality ();
      }
      else if (columnIndex == 2) {
         return item.getReceiveFrequency ();
      }
      else if (columnIndex == 3) {
         return item.getReceiveLatency ();
      }
      else if (columnIndex == 4) {
         return item.getReceiveTotal ();
      }
      else if (columnIndex == 5) {
         return item.getReceivedBadCount ();
      }
      else if (columnIndex == 6) {
         return item.getReceivedSuspectCount ();
      }
      else {
         return new String ("?");
      }
   }
}
