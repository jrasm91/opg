package edu.byu.cs.roots.opg.gui.tools;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class ColorCellRenderer implements TableCellRenderer {

	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		Swatch s = new Swatch((Color) arg1, 14 ,15, false);
		
		s.setSelected(arg3);
		return s;
	}

}
