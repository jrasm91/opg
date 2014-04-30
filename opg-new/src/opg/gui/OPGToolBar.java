package opg.gui;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileFilter;

import opg.main.Const;
import opg.main.Controller;
import opg.other.Utility;

public class OPGToolBar extends JPanel {

	private JTextField currentPage;
	private JLabel pages;
	private List<JComponent> buttons;

	public OPGToolBar() {

		JToggleButton previous = makeToggleButton(Const.PATH_IMG_PREVIOUS, "Previous", previousAction);
		JToggleButton next = makeToggleButton(Const.PATH_IMG_NEXT, "Next", nextAction);

		currentPage = new JTextField(4);
		currentPage.setText("1");

		pages = new JLabel("(1 of 1)");

		JToggleButton arrow = makeToggleButton(Const.PATH_IMG_ARROW, "Select", arrowAction);
		JToggleButton hand = makeToggleButton(Const.PATH_IMG_HAND, "Drag", handAction);
		JButton zoomIn = makeButton(Const.PATH_IMG_ZOOMIN, "Zoom In", zoomInAction);
		JButton zoomOut = makeButton(Const.PATH_IMG_ZOOMOUT, "Zoom Out", zoomOutAction);
		JButton ftwin = makeButton(Const.PATH_IMG_FTWIN, "Fit to Page", fitToWidthAction);
		JButton open = makeButton(Const.PATH_IMG_OPEN, "Open", openAction);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(arrow);
		buttonGroup.add(hand);
		hand.setSelected(true);

		JButton root = new JButton(rootAction);
		JButton test = new JButton(switchAction);

		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(previous);
		this.add(next);
		this.add(makeSeparator());
		this.add(currentPage);
		this.add(pages);
		this.add(makeSeparator());
		this.add(arrow);
		this.add(hand);
		this.add(makeSeparator());
		this.add(zoomIn);
		this.add(zoomOut);
		this.add(ftwin);
		this.add(makeSeparator());
		this.add(open);
		this.add(makeSeparator());
		this.add(root);
		this.add(test);

		buttons = new ArrayList<JComponent>();
		buttons.add(root);
		buttons.add(ftwin);
		buttons.add(zoomIn);
		buttons.add(zoomOut);

		// setEnabled(false);
	}

	public static JToggleButton makeToggleButton(String path, String help, ActionListener action) {
		JToggleButton button = new JToggleButton();
		Image img = Utility.loadImage(path);
		Image newimg = img.getScaledInstance(Const.ICON_SIZE.width, Const.ICON_SIZE.height, Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(newimg));
		button.setToolTipText(help);
		button.addActionListener(action);
		button.setMinimumSize(Const.ICON_SIZE);
		return button;
	}

	public static JButton makeButton(String path, String help, ActionListener action) {
		JButton button = new JButton();
		Image img = Utility.loadImage(path);
		Image newimg = img.getScaledInstance(Const.ICON_SIZE.width, Const.ICON_SIZE.height, Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(newimg));
		button.setToolTipText(help);
		button.addActionListener(action);
		button.setMinimumSize(Const.ICON_SIZE);
		return button;
	}

	private JLabel makeSeparator() {
		return new JLabel(" | ");
	}

	private ActionListener arrowAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Controller.singleton().arrowClick();
		}
	};
	private ActionListener handAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Controller.singleton().handClick();
		}
	};
	private ActionListener zoomInAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Controller.singleton().zoomInClick();
		}
	};
	private ActionListener zoomOutAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Controller.singleton().zoomOutClick();
		}
	};
	private ActionListener fitToWidthAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Controller.singleton().fitToWidthClick();
		}
	};
	private ActionListener previousAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Controller.singleton().previousClick();
		}
	};
	private ActionListener nextAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Controller.singleton().nextClick();
		}
	};
	private Action rootAction = new AbstractAction("Set Tree Beginning") {
		public void actionPerformed(ActionEvent e) {
			//TODO does nothing
		}
	};
	private Action switchAction = new AbstractAction("Compact") {
		public void actionPerformed(ActionEvent e) {
			//TODO does nothing
		}
	};
	private ActionListener openAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			// Controller.singleton().openFileClick(new
			// File("data/hinckley.ged"));

			File file = null;
			JFileChooser fileChooser = new JFileChooser(".");
			fileChooser.setFileFilter(OPGFileFilter.GED);
			fileChooser.setDialogTitle("Choose a file...");
			int option = fileChooser.showOpenDialog(OPGToolBar.this);
			if (option == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				Controller.singleton().openFileClick(file);
			} else {
				System.out.println("file open cancelled");
			}
		}
	};

	public void setEnabled(boolean enabled) {
		for (JComponent b : buttons)
			b.setEnabled(enabled);
	}

	private static class OPGFileFilter extends FileFilter implements FilenameFilter {
		private static String[] extged = { "ged" };
		public static OPGFileFilter GED = new OPGFileFilter("GEDCOM File (.ged)", extged);
		// private static String [] extopg = {"opg"};
		// public static final OPGFileFilter OPG = new
		// OPGFileFilter("OnePage Project File (.opg)", extopg);
		// private static String [] extopgged = {"opg", "ged"};
		// public static OPGFileFilter OPGGED = new
		// OPGFileFilter("OPG Compatible Files (.opg, .ged)", extopgged);
		// private static String [] extpafzip = {"paf", "zip"};
		// public static OPGFileFilter PAFZIP = new
		// OPGFileFilter("Personal Ancestral File 5 databases (.paf, .zip)",
		// extpafzip);
		// private static String[] extAll = { "opg", "ged", "paf", "zip" };
		// public static OPGFileFilter ALL = new OPGFileFilter(
		// "Supported File Types (.opg, .ged, .pag, .zip)", extAll);

		private String description;
		private String[] extensions;

		private OPGFileFilter(String description, String[] extensions) {
			this.description = description;
			this.extensions = extensions;
		}

		@Override
		public boolean accept(File f) {
			if (f.isDirectory())
				return true; // allows for folder browsing...
			String extension = null;
			int index = f.getName().lastIndexOf('.');
			if (index > 0 && index < f.getName().length() - 1)
				extension = f.getName().substring(index + 1);
			if (extension != null)
				for (int i = 0; i < extensions.length; i++)
					if (extension.compareToIgnoreCase((extensions[i])) == 0)
						return true;
			return false;
		}

		@Override
		public String getDescription() {
			return description;
		}

		@Override
		public boolean accept(File dir, String name) {
			try {
				return accept(new File(dir.getCanonicalPath() + name));
			} catch (IOException e) {
				return false;
			}
		}
	}
}
