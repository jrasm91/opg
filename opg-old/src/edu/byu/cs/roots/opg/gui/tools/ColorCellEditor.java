package edu.byu.cs.roots.opg.gui.tools;

import java.awt.Component;
import java.util.EventObject;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

public class ColorCellEditor implements TableCellEditor {

	CopyOnWriteArraySet<CellEditorListener> editorlistenerset = new CopyOnWriteArraySet<CellEditorListener>();
	
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
//		return arg0.getCellRenderer(1, 1).getTableCellRendererComponent(arg0, arg1, arg2,false, arg3, arg4);
		return null;
	}

	public void addCellEditorListener(CellEditorListener arg0) {
		editorlistenerset.add(arg0);

	}

	public void cancelCellEditing() {

	}

	public Object getCellEditorValue() {
		return null;
	}

	public boolean isCellEditable(EventObject arg0) {
		return true;
	}

	public void removeCellEditorListener(CellEditorListener arg0) {
		editorlistenerset.remove(arg0);

	}

	public boolean shouldSelectCell(EventObject arg0) {
		return false;
	}

	public boolean stopCellEditing() {
		return false;
	}

	void fireEditingCanceled(){
		ChangeEvent e = new ChangeEvent(this);
		for(CellEditorListener listener : editorlistenerset){
			listener.editingCanceled(e);
		}
	}
	
	void fireEditingStopped(){
		ChangeEvent e = new ChangeEvent(this);
		for(CellEditorListener listener : editorlistenerset){
			listener.editingStopped(e);
		}
	}
	
}
