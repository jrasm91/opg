package edu.byu.cs.roots.opg.chart.circ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;

import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.SpecificOptionsPanel;
import edu.byu.cs.roots.opg.gui.OnePageMainGui;
import edu.byu.cs.roots.opg.gui.tools.FloatEditor;
import edu.byu.cs.roots.opg.gui.tools.FloatSpinnerModel;

public class CircularOptionsPanel extends SpecificOptionsPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 239394139097901251L;
	private JPanel otherOptionsPanel = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private OnePageMainGui parent = null;
	private CircularOptions options = null;
//	private ImageIcon imageIcon = null;  //  @jve:decl-index=0:visual-constraint="246,63"
	private JLabel jLabel = null;
	private JLabel WLabel = null;
	private JLabel SLabel = null;
	private JLabel RLabel = null;
	private JSpinner WSpinner = null;
	private JSpinner SSpinner = null;
	private JSpinner RSpinner = null;
	private JPanel spacerPanel = null;
	private JLabel inchesLabel1 = null;
	private JLabel inchesLabel2 = null;
	private JLabel inchesLabel3 = null;
	private JCheckBox jCheckBox = null;
	private JLabel emptyboxLabel = null;
	/**
	 * This method initializes 
	 * 
	 */
	public CircularOptionsPanel(ChartOptions options, OnePageMainGui parent) {
		super();
		this.parent = parent;
		this.options = (CircularOptions) options;
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(438, 1013));
        this.add(getOtherOptionsPanel(), BorderLayout.CENTER);
			
	}

	/**
	 * This method initializes otherOptionsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getOtherOptionsPanel() {
		if (otherOptionsPanel == null) {
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.gridx = 0;
			gridBagConstraints22.gridy = 5;
			emptyboxLabel = new JLabel();
			emptyboxLabel.setText("Include Empty Boxes");
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 1;
			gridBagConstraints12.gridy = 5;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 2;
			gridBagConstraints21.gridy = 3;
			inchesLabel3 = new JLabel();
			inchesLabel3.setText("inches");
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.gridy = 2;
			inchesLabel2 = new JLabel();
			inchesLabel2.setText("inches");
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.weightx = 1.0D;
			gridBagConstraints8.gridy = 1;
			inchesLabel1 = new JLabel();
			inchesLabel1.setText("inches");
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.weighty = 3.0D;
			gridBagConstraints7.gridy = 4;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 3;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 2;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.gridy = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.weighty = 1.0D;
			gridBagConstraints3.gridy = 3;
			RLabel = new JLabel();
			RLabel.setText("Radius of Root (R)");
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.weighty = 1.0D;
			gridBagConstraints2.gridy = 2;
			SLabel = new JLabel();
			SLabel.setText("Box Spacing (S)");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.weighty = 1.0D;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.gridy = 1;
			WLabel = new JLabel();
			WLabel.setText("Box Width (W)");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridwidth = 3;
			gridBagConstraints.weighty = 1.0D;
			gridBagConstraints.gridy = 4;
			otherOptionsPanel = new JPanel();
			otherOptionsPanel.setLayout(new GridBagLayout());
			otherOptionsPanel.setBorder(BorderFactory.createTitledBorder(null, "Circular Chart Options", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			otherOptionsPanel.setSize(new Dimension(406, 613));
			otherOptionsPanel.add(getJLabel(), gridBagConstraints);
			otherOptionsPanel.add(WLabel, gridBagConstraints1);
			otherOptionsPanel.add(SLabel, gridBagConstraints2);
			otherOptionsPanel.add(RLabel, gridBagConstraints3);
			otherOptionsPanel.add(getWSpinner(), gridBagConstraints4);
			otherOptionsPanel.add(getSSpinner(), gridBagConstraints5);
			otherOptionsPanel.add(getRSpinner(), gridBagConstraints6);
			otherOptionsPanel.add(getSpacerPanel(), gridBagConstraints7);
			otherOptionsPanel.add(inchesLabel1, gridBagConstraints8);
			otherOptionsPanel.add(inchesLabel2, gridBagConstraints11);
			otherOptionsPanel.add(inchesLabel3, gridBagConstraints21);
			otherOptionsPanel.add(getJCheckBox(), gridBagConstraints12);
			otherOptionsPanel.add(emptyboxLabel, gridBagConstraints22);
		}
		return otherOptionsPanel;
	}

	/**
	 * This method initializes jLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new JLabel();
			jLabel.setText("");
			jLabel.setIcon(new ImageIcon(getClass().getResource("/edu/byu/cs/roots/opg/image/diagram.png")));
		}
		return jLabel;
	}

	/**
	 * This method initializes WSpinner	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getWSpinner() {
		if (WSpinner == null) {
			FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
			spinnerModel.setMaxvalue(10);
			spinnerModel.setMinvalue(0);
			spinnerModel.setStepsize(.1f);
			spinnerModel.setValue(options.boxWidth/72);
			WSpinner = new JSpinner();
			WSpinner.setModel(spinnerModel);
			WSpinner.setEditor(new FloatEditor(WSpinner));
			WSpinner.setEnabled(true);
			WSpinner.setToolTipText("The current width of the chart boxes");
			WSpinner.setPreferredSize(new Dimension(60, 20));
			WSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (!parent.maskEvents) {
						options.setBoxWidth((Float) WSpinner.getValue() * 72);
						parent.refresh();
					}					
				}
			});
		}
		return WSpinner;
	}

	/**
	 * This method initializes SSpinner	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getSSpinner() {
		if (SSpinner == null) {
			FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
			spinnerModel.setMaxvalue(10);
			spinnerModel.setMinvalue(0);
			spinnerModel.setStepsize(.1f);
			spinnerModel.setValue(options.boxSpacing/72);
			SSpinner = new JSpinner();
			SSpinner.setModel(spinnerModel);
			SSpinner.setEditor(new FloatEditor(SSpinner));
			SSpinner.setEnabled(true);
			SSpinner.setToolTipText("The current spacing of the chart boxes");
			SSpinner.setPreferredSize(new Dimension(60, 20));
			SSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (!parent.maskEvents) {
						options.setBoxSpacing((Float) SSpinner.getValue() * 72);
						parent.refresh();
					}					
				}
			});
		}
		return SSpinner;
	}

	/**
	 * This method initializes RSpinner	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getRSpinner() {
		if (RSpinner == null) {
			FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
			spinnerModel.setMaxvalue(10);
			spinnerModel.setMinvalue(0);
			spinnerModel.setStepsize(.1f);
			spinnerModel.setValue(options.rootRadius/72);
			RSpinner = new JSpinner();
			RSpinner.setModel(spinnerModel);
			RSpinner.setEditor(new FloatEditor(RSpinner));
			RSpinner.setEnabled(true);
			RSpinner.setToolTipText("The current radius of the root circle");
			RSpinner.setPreferredSize(new Dimension(60, 20));
			RSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (!parent.maskEvents) {
						options.setRootRadius((Float) RSpinner.getValue() * 72);
						parent.refresh();
					}					
				}
			});
		}
		return RSpinner;
	}

	/**
	 * This method initializes spacerPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSpacerPanel() {
		if (spacerPanel == null) {
			spacerPanel = new JPanel();
			spacerPanel.setLayout(new GridBagLayout());
		}
		return spacerPanel;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					options.setIncludeEmpty(jCheckBox.isSelected());
					parent.refresh();
				}
			});
		}
		return jCheckBox;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
