/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: TransmitTableModel.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.gui;

import javax.swing.table.*;
import java.util.*;
import serialComms.sentences.*;
/**
 * An object of this class defines a customized Table Model for displaying
 * a list of transmitting sentences.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class TransmitTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	static final String[] columnNames = {"Formatter",
                                        "Frequency",
                                        "Source"};
   static final int columnCount = columnNames.length;
   
   Vector<TransmittingSentence> transmitList;

   public TransmitTableModel () {
      transmitList = new Vector<TransmittingSentence>();
   }
    
   public void updateList(Vector<TransmittingSentence> updatedList) {
      if (updatedList != null) {
         transmitList = updatedList;
      }
      this.fireTableDataChanged ();
   }

   public int getColumnCount () {
      return columnCount;
   }
      
   public int getRowCount () {
      return transmitList.size ();
   }
   
   public String getColumnName(int col) {
     return columnNames[col];
   }

   public Object getValueAt (int rowIndex, int columnIndex) {
      TransmittingSentence item = (TransmittingSentence) transmitList.get (rowIndex);
      if (columnIndex == 0) {
         return item.getFormatter ();
      }
      else if (columnIndex == 1) {
         return item.getFrequency();
      }
      else if (columnIndex == 2) {
         return item.getSource ();
      }
      else {
         return new String ("?");
      }
   }
}
