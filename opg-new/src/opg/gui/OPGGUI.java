package opg.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import opg.main.Const;
import opg.main.Controller;
import opg.main.View;
import opg.other.Utility;

public class OPGGUI extends JFrame {

	private static OPGGUI singleton = new OPGGUI();

	public static OPGGUI singleton() {
		return singleton;
	}

	private OPGGUI() {
		Controller.singleton();
		View.singleton();
		
		//		SplashScreen.create();
		this.setIconImage((Utility.loadImage("img/OPGlogo.jpg")));
		this.setTitle("One Page Genealogy " + Const.VERSION_ID);
		this.setContentPane(contentPanel);
		this.setJMenuBar(new OPGMenuBar());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(windowListener);
		this.pack();
		this.setSize(Const.DEFUALT_PAGE_WIDTH + Const.MARGIN_DRAWING * 2, Const.DEFAULT_PAGE_HEIGHT/2);
		this.setLocationRelativeTo(null);
		// this starts the gui in "maximized" mode
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}

	private JPanel contentPanel = new JPanel() {{
			this.setLayout(new BorderLayout());
			JScrollPane view = new JScrollPane(View.singleton());
			view.getVerticalScrollBar().setUnitIncrement(16);
			this.add(new OPGToolBar(), BorderLayout.NORTH);
			this.add(view, BorderLayout.CENTER);
		}
	};

	private WindowAdapter windowListener = new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			Controller.singleton().mainQuit();
		}
	};
}



