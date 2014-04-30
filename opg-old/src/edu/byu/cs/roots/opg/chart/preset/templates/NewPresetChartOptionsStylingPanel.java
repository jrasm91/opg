package edu.byu.cs.roots.opg.chart.preset.templates;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.NumberEditor;

import edu.byu.cs.roots.opg.gui.tools.FloatEditor;
import edu.byu.cs.roots.opg.gui.tools.FloatSpinnerModel;

public class NewPresetChartOptionsStylingPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	public PresetChartOptionsPanel parent;
	private StylingBox selectedStyle;
	private boolean isAncestor;
	private int selectedGen;
	private boolean maskEvents = false;
	private BoxDimensionPanel boxOps = null;
	private static String boxString = "BOXPANELSTRING";
	private FillerTextPanel textOps = null;
	private static String textString = "TEXTPANELSTRING";
	private ArrowDimensionPanel arrowOps = null;
	private static String arrowString = "ARROWPANELSTRING";
	
	private JLabel styleGen = null;
	private JComboBox editPanelChoice = null;
	private JPanel panelSwap;
	
	public NewPresetChartOptionsStylingPanel(PresetChartOptionsPanel parent){
		this.parent = parent;
		initialize();
	}
	
	public void initialize(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		this.add(styleGen = new JLabel("Selected: None"),c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		this.add(getEditPanelChoice(), c);
		
		boxOps = new BoxDimensionPanel();
		textOps = new FillerTextPanel();
		arrowOps = new ArrowDimensionPanel();
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		panelSwap = new JPanel();
		panelSwap.setLayout(new CardLayout());
		panelSwap.add(boxOps, boxString);
		panelSwap.add(textOps, textString);
		panelSwap.add(arrowOps, arrowString);
		CardLayout cl = (CardLayout)panelSwap.getLayout();
		cl.show(panelSwap, boxString);
		this.add(panelSwap, c);
	}
	
	private void refreshBoxes(){
		maskEvents = true;
		if(selectedStyle == null){
			styleGen.setText("Selected: None");
		}else{
			if (isAncestor)
				styleGen.setText("Selected Ancestor Gen: " + selectedGen);
			else
				styleGen.setText("Selected Descendant Gen: " + selectedGen);
		}
		boxOps.refreshBoxes();
		textOps.refreshBoxes();
		arrowOps.refreshBoxes();
		maskEvents = false;
	}
	
	private JComboBox getEditPanelChoice(){
		if (editPanelChoice == null) {
			editPanelChoice = new JComboBox();
			editPanelChoice.setToolTipText("The different Chart Style Panels");
			editPanelChoice.addItem("Box Properties");
			editPanelChoice.addItem("Text Properties");
			editPanelChoice.addItem("Arrow Properties");
			editPanelChoice.setSelectedIndex(0);

			editPanelChoice.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					updatePanel(editPanelChoice.getSelectedIndex());
				}
			});
			
		}
		return editPanelChoice;
	}
	
	public void updatePanel(int i){		
		CardLayout cl = (CardLayout)panelSwap.getLayout();
		
		switch(i){
		case 0:
			cl.show(panelSwap, boxString);
			break;
		case 1:
			cl.show(panelSwap, textString);
			break;
		case 2:
			cl.show(panelSwap, arrowString);
			break;
		}
	}
	
	
	public void setStyle(int gen, boolean isAncestor, StylingBoxScheme boxStyles){
		if (boxStyles != null){
			selectedStyle = isAncestor?boxStyles.getAncesStyle(gen):boxStyles.getDescStyle(gen);
			this.isAncestor = isAncestor;
			selectedGen = gen;
		}
		else
			selectedStyle = null;
		refreshBoxes();
	}
	
	private interface StyleOptionsPanelBase {
		
		public void refreshBoxes();
	}
	
	private class BoxDimensionPanel extends JPanel implements StyleOptionsPanelBase {
		private JSpinner widthSpinner = null;
		private JSpinner heightSpinner = null;
		private JSpinner relativeOffsetSpinner = null;
		private JSpinner rootBackOffsetSpinner = null;
		private JSpinner borderWidthSpinner = null;
		private JSpinner cornerCurveSpinner = null;
		private JSpinner intrudeWidthSpinner = null;
		private JSpinner paddingAmountSpinner = null;
		
		private JCheckBox intrusionCheckBox = null;
		
		public BoxDimensionPanel(){
			this.setLayout(new GridBagLayout());
			initialize();
			
		}
		
		private void initialize(){
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			add(new JLabel("Box Width: "), c);
			c.gridx = 1;
			add(getWidthSpinner(), c);
			
			c.gridx = 0;
			c.gridy++;
			add(new JLabel("Box Height: "), c);
			c.gridx = 1;
			add(getHeightSpinner(), c);
			
			c.gridx = 0;
			c.gridy++;
			add(new JLabel("Box Padding: "), c);
			c.gridx = 1;
			add(getPaddingAmountSpinner(), c);
			
			c.gridx = 0;
			c.gridy++;
			c.gridwidth = 2;
			add(new JLabel("Intrusion:"), c);
			c.gridwidth = 1;
			c.gridy++;
			add(getIntrusionCheckBox(), c);
			c.gridx = 1;
			add(getIntrudeWidthSpinner(), c);
			
			c.gridx = 0;
			c.gridy++;
			add(new JLabel("Border Width: "), c);
			c.gridx = 1;
			add(getBorderWidthSpinner(), c);
			
			c.gridx = 0;
			c.gridy++;
			add(new JLabel("Corner Curve: "), c);
			c.gridx = 1;
			add(getCornerCurveSpinner(), c);
			
			c.gridx = 0;
			c.gridy++;
			add(new JLabel("Ancestor Offset: "), c);
			c.gridx = 1;
			add(getRelativeOffsetSpinner(), c);
			
			c.gridx = 0;
			c.gridy++;
			add(new JLabel("Root Back Offset: "), c);
			c.gridx = 1;
			add(getRootBackOffsetSpinner(), c);
			
		}
		
		JSpinner getWidthSpinner() {
			if (widthSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				widthSpinner = new JSpinner();
				widthSpinner.setModel(spinnerModel);
				widthSpinner.setEditor(new FloatEditor(widthSpinner));
				widthSpinner.setEnabled(true);
				widthSpinner.setToolTipText("The width of the box");
				widthSpinner.setPreferredSize(new Dimension(60, 20));
				widthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.setPermWidth(new Double((Float) spinnerModel.getValue()));
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return widthSpinner;
		}
		
		JSpinner getHeightSpinner() {
			if (heightSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				heightSpinner = new JSpinner();
				heightSpinner.setModel(spinnerModel);
				heightSpinner.setEditor(new FloatEditor(heightSpinner));
				heightSpinner.setEnabled(true);
				heightSpinner.setToolTipText("The height of the box");
				heightSpinner.setPreferredSize(new Dimension(60, 20));
				heightSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.boxHeight = new Double((Float) spinnerModel.getValue());
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return heightSpinner;
		}
		
		JSpinner getRelativeOffsetSpinner() {
			if (relativeOffsetSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				relativeOffsetSpinner = new JSpinner();
				relativeOffsetSpinner.setModel(spinnerModel);
				relativeOffsetSpinner.setEditor(new FloatEditor(relativeOffsetSpinner));
				relativeOffsetSpinner.setEnabled(true);
				relativeOffsetSpinner.setToolTipText("The offset of the next box");
				relativeOffsetSpinner.setPreferredSize(new Dimension(60, 20));
				relativeOffsetSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.setPermOffset(new Double((Float) spinnerModel.getValue()));
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return relativeOffsetSpinner;
		}
		
		JSpinner getRootBackOffsetSpinner() {
			if (rootBackOffsetSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				rootBackOffsetSpinner = new JSpinner();
				rootBackOffsetSpinner.setModel(spinnerModel);
				rootBackOffsetSpinner.setEditor(new FloatEditor(rootBackOffsetSpinner));
				rootBackOffsetSpinner.setEnabled(true);
				rootBackOffsetSpinner.setToolTipText("The offset of the previous box, when this is a root box");
				rootBackOffsetSpinner.setPreferredSize(new Dimension(60, 20));
				rootBackOffsetSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.rootBackOffset = new Double((Float) spinnerModel.getValue());
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return rootBackOffsetSpinner;
		}
		
		JSpinner getBorderWidthSpinner() {
			if (borderWidthSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				spinnerModel.setStepsize(.1f);
				borderWidthSpinner = new JSpinner();
				borderWidthSpinner.setModel(spinnerModel);
				borderWidthSpinner.setEditor(new FloatEditor(borderWidthSpinner));
				borderWidthSpinner.setEnabled(true);
				borderWidthSpinner.setToolTipText("The width of the borderline");
				borderWidthSpinner.setPreferredSize(new Dimension(60, 20));
				borderWidthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.borderlineWidth = new Double((Float) spinnerModel.getValue());
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return borderWidthSpinner;
		}
		
		JSpinner getCornerCurveSpinner() {
			if (cornerCurveSpinner == null) {
				final SpinnerNumberModel spinnerModel = new SpinnerNumberModel();
				spinnerModel.setMaximum(10000);
				spinnerModel.setMinimum(0);
				spinnerModel.setValue(0);
				cornerCurveSpinner = new JSpinner();
				cornerCurveSpinner.setModel(spinnerModel);
				cornerCurveSpinner.setEditor(new NumberEditor(cornerCurveSpinner));
				cornerCurveSpinner.setEnabled(true);
				cornerCurveSpinner.setToolTipText("The curve amount of the corner");
				cornerCurveSpinner.setPreferredSize(new Dimension(60, 20));
				cornerCurveSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.cornerCurve = (Integer)(spinnerModel.getValue());
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return cornerCurveSpinner;
		}
		
		JSpinner getIntrudeWidthSpinner() {
			if (intrudeWidthSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				intrudeWidthSpinner = new JSpinner();
				intrudeWidthSpinner.setModel(spinnerModel);
				intrudeWidthSpinner.setEditor(new FloatEditor(intrudeWidthSpinner));
				intrudeWidthSpinner.setEnabled(true);
				intrudeWidthSpinner.setToolTipText("The box width if intruding");
				intrudeWidthSpinner.setPreferredSize(new Dimension(60, 20));
				intrudeWidthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.intrudeWidth = new Double((Float) spinnerModel.getValue());
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return intrudeWidthSpinner;
		}
		
		private JCheckBox getIntrusionCheckBox() {
			if (intrusionCheckBox == null) {
				intrusionCheckBox = new JCheckBox();
				intrusionCheckBox.setText("Intrusion");
				intrusionCheckBox.setSelected(true);
				intrusionCheckBox.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent e) {
						if (!maskEvents) {
							maskEvents = true;
							selectedStyle.isIntruding = intrusionCheckBox.isSelected();
							getIntrudeWidthSpinner().setEnabled(selectedStyle.isIntruding);
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return intrusionCheckBox;
		}
		
		JSpinner getPaddingAmountSpinner() {
			if (paddingAmountSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				paddingAmountSpinner = new JSpinner();
				paddingAmountSpinner.setModel(spinnerModel);
				paddingAmountSpinner.setEditor(new FloatEditor(paddingAmountSpinner));
				paddingAmountSpinner.setEnabled(true);
				paddingAmountSpinner.setToolTipText("The mandatory white space needed between this box");
				paddingAmountSpinner.setPreferredSize(new Dimension(60, 20));
				paddingAmountSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.paddingAmount = new Double((Float) spinnerModel.getValue());
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return paddingAmountSpinner;
		}

		@Override
		public void refreshBoxes() {
			if(selectedStyle == null){
				widthSpinner.getModel().setValue(0.0);
				widthSpinner.setEnabled(false);
				heightSpinner.getModel().setValue(0.0);
				heightSpinner.setEnabled(false);
				relativeOffsetSpinner.getModel().setValue(0.0);
				relativeOffsetSpinner.setEnabled(false);
				rootBackOffsetSpinner.getModel().setValue(0.0);
				rootBackOffsetSpinner.setEnabled(false);
				borderWidthSpinner.getModel().setValue(0.0);
				borderWidthSpinner.setEnabled(false);
				cornerCurveSpinner.getModel().setValue(0);
				cornerCurveSpinner.setEnabled(false);
				intrudeWidthSpinner.getModel().setValue(0.0);
				intrudeWidthSpinner.setEnabled(false);
				intrusionCheckBox.setSelected(false);
				intrusionCheckBox.setEnabled(false);
				paddingAmountSpinner.getModel().setValue(0.0);
				paddingAmountSpinner.setEnabled(false);
			}else{
				widthSpinner.setEnabled(true);
				heightSpinner.setEnabled(true);
				relativeOffsetSpinner.setEnabled(true);
				rootBackOffsetSpinner.setEnabled(true);
				borderWidthSpinner.setEnabled(true);
				cornerCurveSpinner.setEnabled(true);
				paddingAmountSpinner.setEnabled(true);
				
				intrudeWidthSpinner.setEnabled(selectedStyle.isIntruding);
				
				intrusionCheckBox.setEnabled(true);
				
				widthSpinner.getModel().setValue(selectedStyle.getBoxWidth());
				heightSpinner.getModel().setValue(selectedStyle.boxHeight);	
				relativeOffsetSpinner.getModel().setValue(selectedStyle.getRelativeOffset());
				rootBackOffsetSpinner.getModel().setValue(selectedStyle.rootBackOffset);
				borderWidthSpinner.getModel().setValue(selectedStyle.borderlineWidth);			
				cornerCurveSpinner.getModel().setValue(selectedStyle.cornerCurve);			
				intrudeWidthSpinner.getModel().setValue(selectedStyle.intrudeWidth);
				intrusionCheckBox.setSelected(selectedStyle.isIntruding);
				paddingAmountSpinner.getModel().setValue(selectedStyle.paddingAmount);
			}
			
		}
	}
	
	
	
	private class FillerTextPanel extends JPanel implements StyleOptionsPanelBase {
		private JSpinner fontSizeSpinner = null;
		private JSpinner nameFontSizeSpinner = null;
		private JSpinner textPositionsSpinner = null;
		private JSpinner textMarginSpinner = null;
		
		private JComboBox textPositionsComboBox = null;
		private JComboBox boxLayoutComboBox = null;
		private JComboBox weddingLayoutComboBox = null;
		private JComboBox textDirectionComboBox = null;
		private JComboBox weddingLocationComboBox = null;
		
		private JButton textPositionsButton = null;
		
		
		
		public FillerTextPanel(){
			this.setLayout(new GridBagLayout());
			
			initialize();
		}
		
		private void initialize(){
			GridBagConstraints c = new GridBagConstraints();
			c.gridwidth = 1;
			c.gridy = 0;
			c.gridx = 0;
			add(new JLabel("Name Font Size: "), c);
			c.gridx = 1;
			add(getNameFontSizeSpinner(), c);
			
			c.gridy++;
			c.gridx = 0;
			add(new JLabel("Normal Font Size: "), c);
			c.gridx = 1;
			add(getFontSizeSpinner(), c);
			
			c.gridy++;
			c.gridx = 0;
			add(new JLabel("Text Margins: "), c);
			c.gridx = 1;
			add(getTextMarginSpinner(), c);
			
			c.gridy++;
			c.gridx = 0;
			add(new JLabel("Text Direction: "), c);
			c.gridx = 1;
			add(getTextDirectionComboBox(), c);
			
			c.gridwidth = 2;
			c.gridy++;
			c.gridx = 0;
			add(new JLabel("Text Style"), c);
			
			c.gridwidth = 2;
			c.gridy++;
			c.gridx = 0;
			add(getBoxLayoutComboBox(), c);
			
			c.gridwidth = 2;
			c.gridy++;
			c.gridx = 0;
			add(new JLabel("Text Positions"), c);
			
			c.gridwidth = 2;
			c.gridy++;
			c.gridx = 0;
			JPanel positions = new JPanel();
			add(positions, c);
			positions.setLayout(new GridBagLayout());
			GridBagConstraints tempC = new GridBagConstraints();
			tempC.gridwidth = 1;
			tempC.gridx = 0;
			tempC.gridy = 0;
			positions.add(getTextPositionsSpinner(), tempC);
			tempC.gridx++;
			positions.add(getTextPositionsComboBox(), tempC);
			tempC.gridx++;
			positions.add(getTextPositionsButton(), tempC);
			
			c.gridwidth = 2;
			c.gridy++;
			c.gridx = 0;
			add(new JLabel("Wedding Layout: "), c);
			c.gridy++;
			c.gridx = 0;
			add(getWeddingLayoutComboBox(), c);
			

			c.gridwidth = 2;
			c.gridy++;
			c.gridx = 0;
			add(new JLabel("Wedding Location: "), c);
			c.gridy++;
			c.gridx = 0;
			add(getWeddingLocationComboBox(), c);
			
			
		}
		
		JSpinner getFontSizeSpinner() {
			if (fontSizeSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				fontSizeSpinner = new JSpinner();
				fontSizeSpinner.setModel(spinnerModel);
				fontSizeSpinner.setEditor(new FloatEditor(fontSizeSpinner));
				fontSizeSpinner.setEnabled(true);
				fontSizeSpinner.setToolTipText("The size of the normal font");
				fontSizeSpinner.setPreferredSize(new Dimension(60, 20));
				fontSizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.fontSize = new Double((Float) spinnerModel.getValue());
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return fontSizeSpinner;
		}
		
		JSpinner getNameFontSizeSpinner() {
			if (nameFontSizeSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				nameFontSizeSpinner = new JSpinner();
				nameFontSizeSpinner.setModel(spinnerModel);
				nameFontSizeSpinner.setEditor(new FloatEditor(nameFontSizeSpinner));
				nameFontSizeSpinner.setEnabled(true);
				nameFontSizeSpinner.setToolTipText("The size of the name font");
				nameFontSizeSpinner.setPreferredSize(new Dimension(60, 20));
				nameFontSizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.fontNameSize = new Double((Float) spinnerModel.getValue());
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return nameFontSizeSpinner;
		}
		
		private JComboBox getTextPositionsComboBox() {
			if (textPositionsComboBox == null) {
				textPositionsComboBox = new JComboBox();
				textPositionsComboBox.setToolTipText("The list of text positions");

				textPositionsComboBox.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if(!maskEvents){
							maskEvents = true;
							textPositionsSpinner.getModel().setValue(
									(Double)selectedStyle.textPositions.get(
											textPositionsComboBox.getSelectedIndex()));
							maskEvents = false;
							
						}
					}
				});
				
			}
			return textPositionsComboBox;
		}
		
		JSpinner getTextPositionsSpinner() {
			if (textPositionsSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				textPositionsSpinner = new JSpinner();
				textPositionsSpinner.setModel(spinnerModel);
				textPositionsSpinner.setEditor(new FloatEditor(textPositionsSpinner));
				textPositionsSpinner.setEnabled(true);
				textPositionsSpinner.setToolTipText("The position of the texts");
				textPositionsSpinner.setPreferredSize(new Dimension(60, 20));
				textPositionsSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.textPositions.set(textPositionsComboBox.getSelectedIndex(), new Double((Float) spinnerModel.getValue()));
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return textPositionsSpinner;
		}
		
		JButton getTextPositionsButton() {
			if (textPositionsButton == null) {
				textPositionsButton = new JButton("Add Line");
				textPositionsButton.setEnabled(true);
				textPositionsButton.setToolTipText("Add a new position data type");
				textPositionsButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if(!maskEvents){
							selectedStyle.textPositions.add(0.0);
							textPositionsComboBox.addItem(new String("Line " + selectedStyle.textPositions.size() + 1));
							refreshBoxes();
						}
					}
				});
			}
			return textPositionsButton;
		}
		
		JSpinner getTextMarginSpinner() {
			if (textMarginSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				textMarginSpinner = new JSpinner();
				textMarginSpinner.setModel(spinnerModel);
				textMarginSpinner.setEditor(new FloatEditor(textMarginSpinner));
				textMarginSpinner.setEnabled(true);
				textMarginSpinner.setToolTipText("The horizontal margin of text in their boxes.");
				textMarginSpinner.setPreferredSize(new Dimension(60, 20));
				textMarginSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.textMargin = new Double((Float) spinnerModel.getValue());
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return textMarginSpinner;
		}
		
		private JComboBox getBoxLayoutComboBox() {
			if (boxLayoutComboBox == null) {
				boxLayoutComboBox = new JComboBox();
				boxLayoutComboBox.setToolTipText("The layout of the text");
				for (edu.byu.cs.roots.opg.chart.preset.templates.BoxLayoutParent layout : Layouts.textboxlayouts.list){
					boxLayoutComboBox.addItem(layout);
				}
				boxLayoutComboBox.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if(!maskEvents){
							maskEvents = true;
							if (parent.options == null)
								System.out.println("BLARHG!");
							selectedStyle.layout = (edu.byu.cs.roots.opg.chart.preset.templates.BoxLayoutParent)boxLayoutComboBox.getSelectedItem();
							
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
							
						}
					}
				});
				
			}
			return boxLayoutComboBox;
		}
		
		private JComboBox getWeddingLayoutComboBox() {
			if (weddingLayoutComboBox == null) {
				weddingLayoutComboBox = new JComboBox();
				weddingLayoutComboBox.setToolTipText("The layout of the wedding text");
				for (edu.byu.cs.roots.opg.chart.preset.templates.BoxLayoutParent layout : Layouts.textboxlayouts.weddinglist){
					weddingLayoutComboBox.addItem(layout);
				}
				weddingLayoutComboBox.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if(!maskEvents){
							maskEvents = true;
							
							selectedStyle.weddingLayout = (edu.byu.cs.roots.opg.chart.preset.templates.BoxLayoutParent)weddingLayoutComboBox.getSelectedItem();
							
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
							
						}
					}
				});
				
			}
			return weddingLayoutComboBox;
		}
		
		private JComboBox getTextDirectionComboBox() {
			if (textDirectionComboBox == null) {
				textDirectionComboBox = new JComboBox();
				textDirectionComboBox.setToolTipText("The direction of the text");
				textDirectionComboBox.addItem(StylingBox.TextDirection.NORMAL);
				textDirectionComboBox.addItem(StylingBox.TextDirection.NINETY);
				textDirectionComboBox.addItem(StylingBox.TextDirection.ONE_EIGHTY);
				textDirectionComboBox.addItem(StylingBox.TextDirection.TWO_SEVENTY);
				
				textDirectionComboBox.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if(!maskEvents){
							maskEvents = true;
							selectedStyle.direction = (StylingBox.TextDirection)textDirectionComboBox.getSelectedItem();
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
							
						}
					}
				});
				
			}
			return textDirectionComboBox;
		}
		
		private JComboBox getWeddingLocationComboBox() {
			if (weddingLocationComboBox == null) {
				weddingLocationComboBox = new JComboBox();
				weddingLocationComboBox.setToolTipText("Where to position the wedding information");
				weddingLocationComboBox.addItem(StylingBox.WeddingPositions.BOTH_POSTFIX);
				weddingLocationComboBox.addItem(StylingBox.WeddingPositions.DIRECT_DESCENDANT_POSTFIX);
				weddingLocationComboBox.addItem(StylingBox.WeddingPositions.HUSBAND_POSTFIX);
				weddingLocationComboBox.addItem(StylingBox.WeddingPositions.WIFE_POSTFIX);
				
				weddingLocationComboBox.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if(!maskEvents){
							maskEvents = true;
							selectedStyle.weddingDisplayType = (StylingBox.WeddingPositions)weddingLocationComboBox.getSelectedItem();
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
							
						}
					}
				});
				
			}
			return weddingLocationComboBox;
		}

		@Override
		public void refreshBoxes() {
			if(selectedStyle == null){
				fontSizeSpinner.getModel().setValue(0.0);
				fontSizeSpinner.setEnabled(false);
				nameFontSizeSpinner.getModel().setValue(0.0);
				nameFontSizeSpinner.setEnabled(false);
				
				
				textPositionsComboBox.setEnabled(false);
				textPositionsComboBox.setSelectedItem(null);
				textPositionsSpinner.setEnabled(false);
				textPositionsSpinner.setValue(0.0);
				textPositionsButton.setEnabled(false);
				textMarginSpinner.setEnabled(false);
				textMarginSpinner.setValue(0.0);
				
				boxLayoutComboBox.setEnabled(false);
				boxLayoutComboBox.setSelectedIndex(-1);
				
				weddingLayoutComboBox.setEnabled(false);
				weddingLayoutComboBox.setSelectedIndex(-1);
				weddingLocationComboBox.setEnabled(false);
				weddingLocationComboBox.setSelectedIndex(-1);
				
				textDirectionComboBox.setEnabled(false);
				textDirectionComboBox.setSelectedIndex(-1);
			}else{
				maskEvents = true;
				fontSizeSpinner.setEnabled(true);
				nameFontSizeSpinner.setEnabled(true);
				
				textPositionsButton.setEnabled(true);
				textPositionsSpinner.setEnabled(true);
				textPositionsComboBox.setEnabled(true);
				textMarginSpinner.setEnabled(true);
				boxLayoutComboBox.setEnabled(true);
				weddingLayoutComboBox.setEnabled(true);
				textDirectionComboBox.setEnabled(true);
				weddingLocationComboBox.setEnabled(true);
				
				fontSizeSpinner.getModel().setValue(selectedStyle.fontSize);			
				nameFontSizeSpinner.getModel().setValue(selectedStyle.fontNameSize);			
				
				
				
				
				textPositionsComboBox.removeAllItems();
				
				for (int i = 0; i < selectedStyle.textPositions.size(); i++){
					textPositionsComboBox.addItem(new String("Line " + (i+1)));
				}
				textPositionsComboBox.setSelectedIndex(0);
				textPositionsSpinner.getModel().setValue(selectedStyle.textPositions.get(textPositionsComboBox.getSelectedIndex()));
				textMarginSpinner.getModel().setValue(selectedStyle.textMargin);
				
				boxLayoutComboBox.setSelectedItem(selectedStyle.layout);
				weddingLayoutComboBox.setSelectedItem(selectedStyle.weddingLayout);
				textDirectionComboBox.setSelectedItem(selectedStyle.direction);
				weddingLocationComboBox.setSelectedItem(selectedStyle.weddingDisplayType);
				maskEvents = false;
			}
			
		}
	}
	
	
	private class ArrowDimensionPanel extends JPanel implements StyleOptionsPanelBase {
		private JSpinner endLineArrowShaftLengthSpinner = null;
		private JSpinner endLineArrowShaftHeightSpinner = null;
		private JSpinner endLineArrowHeadLengthSpinner = null;
		private JSpinner endLineArrowHeadHeightSpinner = null;
		
		private JSpinner endLineArrowFontSpinner = null;
		
		public ArrowDimensionPanel(){
			this.setLayout(new GridBagLayout());
			initialize();
		}
		
		private void initialize(){
			GridBagConstraints c = new GridBagConstraints();
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
			add(new JLabel("Arrow Shaft Length: "),c);
			c.gridx = 1;
			add(getEndLineArrowShaftLengthSpinner(), c);
			
			c.gridx = 0;
			c.gridy++;
			add(new JLabel("Arrow Shaft Height: "), c);
			c.gridx = 1;
			add(getEndLineArrowShaftHeightSpinner(), c);
			
			c.gridx = 0;
			c.gridy++;
			add(new JLabel("Arrow Head Length: "), c);
			c.gridx = 1;
			add(getEndLineArrowHeadLengthSpinner(), c);
			
			c.gridx = 0;
			c.gridy++;
			add(new JLabel("Arrow Head Height: "), c);
			c.gridx = 1;
			add(getEndLineArrowHeadHeightSpinner(), c);
			
			c.gridx = 0;
			c.gridy++;
			add(new JLabel("Arrow Head Font Size: "), c);
			c.gridx = 1;
			add(getEndLineArrowFontSpinner(), c);
			
			
		}
		
		JSpinner getEndLineArrowShaftLengthSpinner() {
			if (endLineArrowShaftLengthSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				endLineArrowShaftLengthSpinner = new JSpinner();
				endLineArrowShaftLengthSpinner.setModel(spinnerModel);
				endLineArrowShaftLengthSpinner.setEditor(new FloatEditor(endLineArrowShaftLengthSpinner));
				endLineArrowShaftLengthSpinner.setEnabled(true);
				endLineArrowShaftLengthSpinner.setToolTipText("The length of the end of line arrow");
				endLineArrowShaftLengthSpinner.setPreferredSize(new Dimension(60, 20));
				endLineArrowShaftLengthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.endLineArrowShaftLength = new Double((Float) spinnerModel.getValue());
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return endLineArrowShaftLengthSpinner;
		}
		
		JSpinner getEndLineArrowShaftHeightSpinner() {
			if (endLineArrowShaftHeightSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				endLineArrowShaftHeightSpinner = new JSpinner();
				endLineArrowShaftHeightSpinner.setModel(spinnerModel);
				endLineArrowShaftHeightSpinner.setEditor(new FloatEditor(endLineArrowShaftHeightSpinner));
				endLineArrowShaftHeightSpinner.setEnabled(true);
				endLineArrowShaftHeightSpinner.setToolTipText("The height of the shaft of the end of line arrow");
				endLineArrowShaftHeightSpinner.setPreferredSize(new Dimension(60, 20));
				endLineArrowShaftHeightSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.endLineArrowShaftHeight = new Double((Float) spinnerModel.getValue());
							parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return endLineArrowShaftHeightSpinner;
		}
		
		JSpinner getEndLineArrowFontSpinner() {
			if (endLineArrowFontSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				endLineArrowFontSpinner = new JSpinner();
				endLineArrowFontSpinner.setModel(spinnerModel);
				endLineArrowFontSpinner.setEditor(new FloatEditor(endLineArrowFontSpinner));
				endLineArrowFontSpinner.setEnabled(true);
				endLineArrowFontSpinner.setToolTipText("The size of the font of the end of line arrow");
				endLineArrowFontSpinner.setPreferredSize(new Dimension(60, 20));
				endLineArrowFontSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.endLineArrowFontSize = new Double((Float) spinnerModel.getValue());parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return endLineArrowFontSpinner;
		}
		
		JSpinner getEndLineArrowHeadLengthSpinner() {
			if (endLineArrowHeadLengthSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				endLineArrowHeadLengthSpinner = new JSpinner();
				endLineArrowHeadLengthSpinner.setModel(spinnerModel);
				endLineArrowHeadLengthSpinner.setEditor(new FloatEditor(endLineArrowHeadLengthSpinner));
				endLineArrowHeadLengthSpinner.setEnabled(true);
				endLineArrowHeadLengthSpinner.setToolTipText("The size of arrow head at the end of line arrow");
				endLineArrowHeadLengthSpinner.setPreferredSize(new Dimension(60, 20));
				endLineArrowHeadLengthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.endLineArrowHeadLength = new Double((Float) spinnerModel.getValue());parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return endLineArrowHeadLengthSpinner;
		}
		
		JSpinner getEndLineArrowHeadHeightSpinner() {
			if (endLineArrowHeadHeightSpinner == null) {
				final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
				spinnerModel.setMaxvalue(10000);
				spinnerModel.setMinvalue(0);
				spinnerModel.setValue(0f);
				endLineArrowHeadHeightSpinner = new JSpinner();
				endLineArrowHeadHeightSpinner.setModel(spinnerModel);
				endLineArrowHeadHeightSpinner.setEditor(new FloatEditor(endLineArrowHeadHeightSpinner));
				endLineArrowHeadHeightSpinner.setEnabled(true);
				endLineArrowHeadHeightSpinner.setToolTipText("The height of the arrow head at the end of line arrow");
				endLineArrowHeadHeightSpinner.setPreferredSize(new Dimension(60, 20));
				endLineArrowHeadHeightSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent e) {
						if (maskEvents == false){
							maskEvents = true;
							selectedStyle.endLineArrowHeadHeight = new Double((Float) spinnerModel.getValue());parent.options.setStyleBoxChanged(true);
							parent.parent.refresh();
							maskEvents = false;
						}
					}
				});
			}
			return endLineArrowHeadHeightSpinner;
		}

		@Override
		public void refreshBoxes() {
			if(selectedStyle == null){
				endLineArrowShaftLengthSpinner.getModel().setValue(0.0);
				endLineArrowShaftLengthSpinner.setEnabled(false);
				endLineArrowShaftHeightSpinner.getModel().setValue(0.0);
				endLineArrowShaftHeightSpinner.setEnabled(false);
				endLineArrowFontSpinner.getModel().setValue(0.0);
				endLineArrowFontSpinner.setEnabled(false);
				endLineArrowHeadLengthSpinner.getModel().setValue(0.0);
				endLineArrowHeadLengthSpinner.setEnabled(false);
				endLineArrowHeadHeightSpinner.getModel().setValue(0.0);
				endLineArrowHeadHeightSpinner.setEnabled(false);
			}else{
				endLineArrowShaftLengthSpinner.setEnabled(true);
				endLineArrowShaftHeightSpinner.setEnabled(true);
				endLineArrowFontSpinner.setEnabled(true);
				endLineArrowHeadLengthSpinner.setEnabled(true);
				endLineArrowHeadHeightSpinner.setEnabled(true);
				
				endLineArrowShaftLengthSpinner.getModel().setValue(selectedStyle.endLineArrowShaftLength);
				endLineArrowShaftHeightSpinner.getModel().setValue(selectedStyle.endLineArrowShaftHeight);
				endLineArrowFontSpinner.getModel().setValue(selectedStyle.endLineArrowFontSize);
				endLineArrowHeadLengthSpinner.getModel().setValue(selectedStyle.endLineArrowHeadLength);
				endLineArrowHeadHeightSpinner.getModel().setValue(selectedStyle.endLineArrowHeadHeight);
			}
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	

}
