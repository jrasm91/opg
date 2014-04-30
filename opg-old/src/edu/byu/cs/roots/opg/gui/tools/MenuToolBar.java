package edu.byu.cs.roots.opg.gui.tools;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JToolBar;
import javax.swing.JToggleButton;


public class MenuToolBar extends JToolBar {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3613150486272685288L;
	@Override
	public Component add(Component comp){
		super.add(comp);
		try{
			ToolBarMenu menu = (ToolBarMenu) comp;
			menu.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent e) {
//					 ToolBarMenu orig = (ToolBarMenu) e.getComponent();
					 
//					if(orig.menuopen == true){
//						
//					}
			        ShowPopup(e);
			    }
			    private void ShowPopup(MouseEvent e) {
			        ToolBarMenu orig = (ToolBarMenu) e.getComponent();
			        orig.openMenu(getOrientation());
			    }
				public void mouseClicked(MouseEvent arg0) {}
				public void mouseEntered(MouseEvent arg0) {
					if(isSelected()){
						deselectAll();
						ShowPopup(arg0);
					}
				}
				public void mouseExited(MouseEvent arg0) {
				}
				public void mouseReleased(MouseEvent arg0) {}
			});
		}
		catch(ClassCastException cce){}
		return comp;
	}
	
	public boolean isSelected(){
		JToggleButton button = null;
		for(Component comp : this.getComponents()){
			try{
				button = (JToggleButton) comp;
			}
			catch(ClassCastException cce){
				continue;
			}
			if(button.isSelected()) return true;
		}
		return false;
	}
	public void deselectAll(){
		JToggleButton button = null;
		for(Component comp : this.getComponents()){
			try{
				button = (JToggleButton) comp;
			}
			catch(ClassCastException cce){
				continue;
			}
			button.setSelected(false);
		}
	}
	
	
	
}