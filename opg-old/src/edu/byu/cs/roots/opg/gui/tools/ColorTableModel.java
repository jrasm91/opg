package edu.byu.cs.roots.opg.gui.tools;

import javax.swing.table.AbstractTableModel;

public class ColorTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 5205061253148881336L;
	private String[] columnNames;
    private Object[][] data;


    public ColorTableModel(String[] columnNames, Object[][] data) {
		super();
		this.columnNames = columnNames;
		this.data = data;
	}

	public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return (data != null) ? data.length : 0;
    }

    public String getColumnName(int col) {
        return (columnNames != null) ? columnNames[col] : "";
    }

    public Object getValueAt(int row, int col) {
        return (data == null) ? null : 
          (row >= data.length) ? null : 
          (col >= data[row].length) ? null : data[row][col];
    }

    public Class<?> getColumnClass(int c) {
        Object o = getValueAt(0, c);
        return (o != null) ? o.getClass() : null;
    }

    public boolean isCellEditable(int row, int col) {
        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public void setData(Object[][] data){
    	this.data = data;
    }

	/**
	 * @return the columnNames
	 */
	public String[] getColumnNames() {
		return columnNames;
	}
    
}
