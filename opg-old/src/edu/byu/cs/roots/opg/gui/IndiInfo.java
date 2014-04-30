package edu.byu.cs.roots.opg.gui;

import java.awt.Dimension;

import javax.swing.JDialog;

import edu.byu.cs.roots.opg.model.Individual;
import javax.swing.*;

public class IndiInfo extends JDialog
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Width of the dialog box */
	private static final int DIALOG_WIDTH = 800;
	/** Height of the dialog box */
	private static final int DIALOG_HEIGHT = 600;
	/** Title of the dialog box */
	private static final String DIALOG_TITLE = "Individual Information";

	private Individual indi;
	
	//private JLabel name;
	private JTextArea outp;
	
	public IndiInfo(Individual indi)
	{
		this.indi = indi;
		
		initialize();
	}
	
	
	
	private void initialize()
	{
		this.setSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
        this.setTitle(DIALOG_TITLE);
        this.setResizable(false);
		outp = new JTextArea();
		add(outp);
		outp.setText(indi.toStringVerbose());
		
	}
}
