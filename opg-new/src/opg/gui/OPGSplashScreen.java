package opg.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import opg.main.Const;
import opg.main.Controller;
import opg.other.Utility;

public class OPGSplashScreen extends JFrame {
	// how far "Donate" and "Close" buttons are located from edges
	private final int BUTTON_MARGIN = 25; 
	
	public static void create(){
		new OPGSplashScreen();
	}
	
	private OPGSplashScreen() {
		super();
		this.setMinimumSize(Const.SPLASH_DIM);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.add(new SplashScreenPanel());
		this.setUndecorated(true);
		this.pack();
		this.setVisible(true);
	}

	private class SplashScreenPanel extends JPanel {
		private static final long serialVersionUID = -1892451647888632710L;
		Image splashImage = null;

		public SplashScreenPanel() {
			splashImage = Utility.loadImage(Const.PATH_IMG_SPLASH);
			
			// donate button
			JButton donate = new JButton("Donate");
			donate.setOpaque(false);
			donate.addActionListener(donateAction);

			// close button
			JButton close = new JButton("Close");
			close.setOpaque(false);
			close.addActionListener(closeAction);

			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			this.add(Box.createRigidArea(new Dimension(0, Const.SPLASH_PARTIAL_HEIGHT)));

			// needs separate panel in order to appear at bottom of splash screen
			JPanel splashAlignPanel = new JPanel();
			splashAlignPanel.setOpaque(false);
			splashAlignPanel.setLayout(new BoxLayout(splashAlignPanel,
					BoxLayout.LINE_AXIS));
			splashAlignPanel.add(Box.createRigidArea(new Dimension(BUTTON_MARGIN, 0)));
			splashAlignPanel.add(close);
			splashAlignPanel.add(Box.createHorizontalGlue());
			splashAlignPanel.add(donate);
			splashAlignPanel.add(Box.createRigidArea(new Dimension(BUTTON_MARGIN, 0)));
			
			this.add(splashAlignPanel);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(splashImage, 0, 0, null);
		}
		
		private ActionListener closeAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OPGSplashScreen.this.dispose();
				Controller.singleton().splashCloseClick();
			}
		};
		private ActionListener donateAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OPGSplashScreen.this.dispose();
				Controller.singleton().splashDonate();
			}
		};
	}
}
