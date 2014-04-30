package edu.byu.cs.roots.opg.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;

import edu.byu.cs.roots.opg.chart.ChartMarginData;
import edu.byu.cs.roots.opg.gui.tools.FloatEditor;
import edu.byu.cs.roots.opg.gui.tools.FloatSpinnerModel;


public class MarginEditorDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Width of the dialog box */
	private static final int DIALOG_WIDTH = 220;
	/** Height of the dialog box */
	private static final int DIALOG_HEIGHT = 150;
	/** Title of the dialog box */
	private static final String DIALOG_TITLE = "Margins";

	private JLabel lLabel, rLabel, tLabel, bLabel;
	private JSpinner lSpinner, rSpinner, tSpinner, bSpinner;
	private JButton okButton, cancelButton, acceptButton;
	private JPanel buttonPanel;
	ChartMarginData margins;
	OnePageMainGui parent;
	
	public MarginEditorDialog(ChartMarginData margins, OnePageMainGui parent)
	{	
		this.margins = margins;
		this.parent = parent;
		initialize();
	}
	
	
	
	private void initialize()
	{
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setLayout(new GridBagLayout());
		buttonPanel = new JPanel(new GridBagLayout());
		
		lLabel = new JLabel("Left");
		rLabel = new JLabel("Right");
		tLabel = new JLabel("Top");
		bLabel = new JLabel("Bottom");
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		add(lLabel, c);
		c.gridx = 2;
		add(getLSpinner(), c);
		
		c.gridx = 1;
		c.gridy = 1;
		add(rLabel, c);
		c.gridx = 2;
		add(getRSpinner(), c);
		
		c.gridx = 1;
		c.gridy = 2;
		add(tLabel, c);
		c.gridx = 2;
		add(getTSpinner(), c);
		
		c.gridx = 1;
		c.gridy = 3;
		add(bLabel, c);
		c.gridx = 2;
		add(getBSpinner(), c);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 4;
		add(buttonPanel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		buttonPanel.add(getOkButton(), c);
		c.gridx = 1;
		buttonPanel.add(getAcceptButton(), c);
		c.gridx = 2;
		buttonPanel.add(getCancelButton(), c);
		
		this.setSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
        this.setTitle(DIALOG_TITLE);
        this.setResizable(false);
		
		
		
	}
	
	private void close(){
		this.dispose();
	}
	
	private void updateChart(){
		margins.setLeft(new Double((Float) lSpinner.getModel().getValue()*72));
		margins.setRight(new Double((Float) rSpinner.getModel().getValue()*72));
		margins.setTop(new Double((Float) tSpinner.getModel().getValue()*72));
		margins.setBottom(new Double((Float) bSpinner.getModel().getValue()*72));
		parent.session.currentPage().getFirstOptions().setMarginsChanged(true);
		parent.refresh();
	}
	
	
	
	JSpinner getLSpinner() {
		if (lSpinner == null) {
			final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
			spinnerModel.setMaxvalue(10000);
			spinnerModel.setMinvalue(0);
			spinnerModel.setValue(margins.getLeft()/72.0);
			spinnerModel.setStepsize(.1f);
			lSpinner = new JSpinner();
			lSpinner.setModel(spinnerModel);
			lSpinner.setEditor(new FloatEditor(lSpinner));
			lSpinner.setEnabled(true);
			lSpinner.setToolTipText("The position of the texts");
			lSpinner.setPreferredSize(new Dimension(60, 20));
			lSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					
					new Double((Float) spinnerModel.getValue());
						
				}
			});
		}
		return lSpinner;
	}
	JSpinner getRSpinner() {
		if (rSpinner == null) {
			final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
			spinnerModel.setMaxvalue(10000);
			spinnerModel.setMinvalue(0);
			spinnerModel.setValue(margins.getRight()/72.0);
			spinnerModel.setStepsize(.1f);
			rSpinner = new JSpinner();
			rSpinner.setModel(spinnerModel);
			rSpinner.setEditor(new FloatEditor(rSpinner));
			rSpinner.setEnabled(true);
			rSpinner.setToolTipText("The position of the texts");
			rSpinner.setPreferredSize(new Dimension(60, 20));
			rSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					
					
						
				}
			});
		}
		return rSpinner;
	}
	JSpinner getTSpinner() {
		if (tSpinner == null) {
			final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
			spinnerModel.setMaxvalue(10000);
			spinnerModel.setMinvalue(0);
			spinnerModel.setValue(margins.getTop()/72.0);
			spinnerModel.setStepsize(.1f);
			tSpinner = new JSpinner();
			tSpinner.setModel(spinnerModel);
			tSpinner.setEditor(new FloatEditor(tSpinner));
			tSpinner.setEnabled(true);
			tSpinner.setToolTipText("The position of the texts");
			tSpinner.setPreferredSize(new Dimension(60, 20));
			tSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					
					new Double((Float) spinnerModel.getValue());
						
				}
			});
		}
		return tSpinner;
	}
	JSpinner getBSpinner() {
		if (bSpinner == null) {
			final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
			spinnerModel.setMaxvalue(10000);
			spinnerModel.setMinvalue(0);
			spinnerModel.setValue(margins.getBottom()/72.0);
			spinnerModel.setStepsize(.1f);
			bSpinner = new JSpinner();
			bSpinner.setModel(spinnerModel);
			bSpinner.setEditor(new FloatEditor(bSpinner));
			bSpinner.setEnabled(true);
			bSpinner.setToolTipText("The position of the texts");
			bSpinner.setPreferredSize(new Dimension(60, 20));
			bSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					
					new Double((Float) spinnerModel.getValue());
						
				}
			});
		}
		return bSpinner;
	}
	
	JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton("Ok");
			okButton.setEnabled(true);
			okButton.setToolTipText("Accept changes and close this window");
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					updateChart();
					close();
				}
			});
		}
		return okButton;
	}
	JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton("Cancel");
			cancelButton.setEnabled(true);
			cancelButton.setToolTipText("Close window and discard any changes");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					close();
				}
			});
		}
		return cancelButton;
	}
	JButton getAcceptButton() {
		if (acceptButton == null) {
			acceptButton = new JButton("Accept");
			acceptButton.setEnabled(true);
			acceptButton.setToolTipText("Update changes without closing the window");
			acceptButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					updateChart();
				}
			});
		}
		return acceptButton;
	}
}
