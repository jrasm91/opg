package edu.byu.cs.roots.opg.gui.tools;

import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class ToolBarMenu extends JToggleButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5245578679219105536L;
	JPopupMenu menu = null;
	
	public void setMenu(JPopupMenu menu){
		this.menu = menu;
		menu.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
			public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {
				closeMenu();
			}
			public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {}
			public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
				closeMenu();
			}
		});
	}
	
	public void openMenu(int orientation){
		setSelected(true);
		if(orientation == JToolBar.HORIZONTAL){
			menu.show(this,0, getLocation().y + getHeight() );
		}
		else{
			menu.show(this, getLocation().x + getWidth(),0 );
		}
	}
	
	public void closeMenu(){
		setSelected(false);
	}
}