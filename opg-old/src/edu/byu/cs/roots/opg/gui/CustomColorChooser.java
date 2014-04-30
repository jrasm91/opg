package edu.byu.cs.roots.opg.gui;



import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.byu.cs.roots.opg.gui.tools.Swatch;

public class CustomColorChooser extends JColorChooser{
	private static final long serialVersionUID = 1L;

	public CustomColorChooser(){

		AbstractColorChooserPanel[] panels = {this.getChooserPanels()[1]};
		this.setPreviewPanel(new JPanel());
		this.setChooserPanels(panels);
	}
	
	public class ColorListener implements ChangeListener{

		OnePageMainGui gui;
		Swatch sel;
		CustomColorChooser chooser;
		public ColorListener(OnePageMainGui gui, Swatch sel, CustomColorChooser chooser){
			super();
			this.gui = gui;
			this.sel = sel;
			this.chooser = chooser;
		}
		@Override
		public void stateChanged(ChangeEvent arg0) {
			sel.setColor(chooser.getColor());
			JTable cTable = null;
			if(gui.getColorTabbedPane().getSelectedIndex() == 1) cTable = gui.getDescColorTable();
			if(gui.getColorTabbedPane().getSelectedIndex() == 0) cTable = gui.getAncesColorTable();
			int r = cTable.getSelectedRow();
			int [] rows = (r < 0? new int [] {0}:cTable.getSelectedRows());
			for(int row:rows){
				cTable.setValueAt(gui.customSwatchArray.getSelected().getColor(), row, 1);
			}
			sel.repaint();
			
		}
		
	}
}
