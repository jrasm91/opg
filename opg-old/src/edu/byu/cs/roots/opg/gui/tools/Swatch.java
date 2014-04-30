package edu.byu.cs.roots.opg.gui.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

public class Swatch extends JComponent {
	private static final long serialVersionUID = -6786198842264584070L;
	Color color;
	boolean selected;
	boolean selectable;
	int height;
	int width;
	int border;
	
	
	CopyOnWriteArraySet<ActionListener> listenerset = new CopyOnWriteArraySet<ActionListener>();
	
	public Swatch(Color c){
		color = c;
		height = 14;
		width = 19;
		border = 2;
		selectable = false;
		selected = false;
		this.addMouseListener(new SwatchListener(this));
	}
	
	public Swatch(Color c, boolean selectable){
		color = c;
		height = 14;
		width = 19;
		border = 2;
		selected = false;
		this.addMouseListener(new SwatchListener(this));
		this.selectable = selectable;
	}
	
	public Swatch(Color c, int height, int width){
		color = c;
		this.height = height;
		this.width = width;
		border = 2;
		selectable = false;
		selected = false;
		this.addMouseListener(new SwatchListener(this));
	}
	
	public Swatch(Color c, int height, int width, boolean selectable){
		color = c;
		this.height = height;
		this.width = width;
		border = 2;
		this.selectable = selectable;
		selected = false;
		this.addMouseListener(new SwatchListener(this));
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(color);
		g.fillRect(border, border, width-2*border, height-2*border);
		if(selected){
			g.setColor(Color.black);
			g.drawRect(0, 0, width-1, height-1);
			g.drawRect(0,0, width-2, height-2);
		}
	}
	
	
	void fireActionPerformed(int id){
		ActionEvent e = new ActionEvent(this, id, "selected");
		for(ActionListener listener : listenerset){
			listener.actionPerformed(e);
		}
	}
	
	public void addActionListener(ActionListener arg0) {
		listenerset.add(arg0);
	}
	
	public void removeChangeListener(ChangeListener arg0) {
		listenerset.remove(arg0);
	}
	
	
	
	private class SwatchListener implements MouseListener
	{
		 @SuppressWarnings("unused")
		JComponent parent;
		 
		SwatchListener(JComponent parent){
			this.parent = parent;
		}

		public void mouseClicked (MouseEvent event){
			if (event.getClickCount() == 2){
				fireActionPerformed(2);
			}else{
				fireActionPerformed(1);
			}
		}

		public void mouseEntered(MouseEvent arg0) {}

		public void mouseExited(MouseEvent arg0) {}

		public void mousePressed(MouseEvent arg0) {}

		public void mouseReleased(MouseEvent arg0) {}

	}


	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}


	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.firePropertyChange("color", this.color, color);
		this.color = color;
	}


	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}


	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		if(!selectable) return;
		this.firePropertyChange("selected", this.selected, selected);
		this.selected = selected;
	}

	/**
	 * @return the selectable
	 */
	public boolean isSelectable() {
		return selectable;
	}

	/**
	 * @param selectable the selectable to set
	 */
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
		if(!selectable) selected = false;
	}
}
