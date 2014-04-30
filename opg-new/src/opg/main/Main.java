package opg.main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import opg.gui.OPGGUI;

public class Main {

	public static void main(String[] args) {
		System.out.println("Starting GUI");
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception ex) {
					System.out.println(ex);
					System.out.println("Failed loading system dependant Look and Fell");
				}
				OPGGUI.singleton();
			}
		});
	}
}
