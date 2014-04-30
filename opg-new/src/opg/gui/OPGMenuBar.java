package opg.gui;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle.Control;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import opg.main.Controller;

public class OPGMenuBar extends JMenuBar {

	public OPGMenuBar() {
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new JMenuItem(saveAsPdfAction));
		fileMenu.add(new JSeparator());
		fileMenu.add(new JMenuItem(projectSaveAction));
		fileMenu.add(new JMenuItem(projectSaveAsAction));
		fileMenu.add(new JSeparator());
		fileMenu.add(new JMenuItem(chartSaveAction));
		fileMenu.add(new JMenuItem(chartSaveAsAction));
		fileMenu.add(new JSeparator());
		fileMenu.add(new JMenuItem(printAction));
		fileMenu.add(new JSeparator());
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu insertMenu = new JMenu("Insert");
		insertMenu.add(new JMenuItem(insertPictureAction));
		
		JMenu pageMenu = new JMenu("Page");
		pageMenu.add(new JMenuItem(marginAction));
		pageMenu.add(new JMenuItem(advancedOptionsAction));
		pageMenu.add(new JMenuItem(rulerAction));
		pageMenu.add(new JMenuItem(gridAction));
		
		JMenu familySearchMenu = new JMenu("FamilySearch");
		familySearchMenu.add( new JMenuItem(familySearchDownloadAction));
		familySearchMenu.add( new JMenuItem(familySearchUpdateAction));

		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new JMenuItem(helpAction));
		helpMenu.add(new JMenuItem(donateAction));
		helpMenu.add(new JSeparator());
		helpMenu.add(new JMenuItem(aboutAction));
		
		this.add(fileMenu);
		this.add(pageMenu);
		this.add(insertMenu);
		this.add(familySearchMenu);
		this.add(helpMenu);

//		rulerAction.actionPerformed(null);
//		gridAction.actionPerformed(null);
	}

	private Action projectSaveAction = new AbstractAction("Project Save") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Project Save Action");
		}
	};
	
	private Action projectSaveAsAction = new AbstractAction("Project Save As") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Project Save As Action");
		}
	};
	
	private Action chartSaveAction = new AbstractAction("Chart Save") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Chart Save Action");
		}
	};
	
	private Action chartSaveAsAction = new AbstractAction("Chart Save As") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Chart Save As Action");
		}
	};
	
	private Action saveAsPdfAction= new AbstractAction("Save As PDF") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Save As PDF Action");
			Controller.singleton().saveAsPDFClick();
		}
	};
	
	private Action printAction = new AbstractAction("Print") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Print Action");
		}
	};
	
	private Action exitAction = new AbstractAction("Exit") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Exit Action");
		}
	};
	
	private Action marginAction = new AbstractAction("Edit Margins") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Margin Action");
		}
	};
	
	private Action insertPictureAction = new AbstractAction("Insert Picture") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Picture Action");
		}
	};
	
	private Action advancedOptionsAction = new AbstractAction("Show Advanced Options") {
		public void actionPerformed(ActionEvent arg0) {
			String hide = "Hide Advanced Options";
			String show = "Show Advanced Options";
			String name = (String)this.getValue(Action.NAME);
			this.putValue(Action.NAME, name == show? hide: show);
			Controller.singleton().showAdvancedOptions(name.equals(show));
		}
	};
	
	private Action rulerAction = new AbstractAction("Show Ruler") {
		public void actionPerformed(ActionEvent arg0) {
			String hide = "Hide Ruler";
			String show = "Show Ruler";
			String name = (String)this.getValue(Action.NAME);
			this.putValue(Action.NAME, name == show? hide: show);
			Controller.singleton().showRuler(name.equals(show));
		}
	};
	
	private Action gridAction = new AbstractAction("Show Grid") {
		public void actionPerformed(ActionEvent arg0) {
			String hide = "Hide Grid";
			String show = "Show Grid";
			String name = (String)this.getValue(Action.NAME);
			this.putValue(Action.NAME, name == show? hide: show);
			Controller.singleton().showGrid(name.equals(show));
		}
	};
	
	private Action helpAction = new AbstractAction("Online Help") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Help Action");
		}
	};
	
	private Action donateAction = new AbstractAction("Donate") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Donate Action");
		}
	};
	
	private Action aboutAction = new AbstractAction("About") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("About Action");
		}
	};
	
	private Action familySearchDownloadAction = new AbstractAction("Download") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Download FS Action");
		}
	};
	
	private Action familySearchUpdateAction = new AbstractAction("Update") {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Update FS Action");
		}
	};
}






