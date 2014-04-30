package edu.byu.cs.roots.opg.chart.preset.templates;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.SpinnerNumberModel;


import edu.byu.cs.roots.opg.gui.tools.FloatEditor;
import edu.byu.cs.roots.opg.gui.tools.FloatSpinnerModel;


public class PresetChartOptionsStyleEditPanel extends JPanel
{
	private static final long serialVersionUID = -6585933161593824182L;
	private JLabel styleGen;
	private JLabel widthLabel;
	private JLabel heightLabel;
	private JLabel fontSizeLabel;
	private JLabel nameFontSizeLabel;
	private JLabel relativeOffsetLabel;
	private JLabel rootBackOffsetLabel;
	private JLabel borderWidthLabel;
	private JLabel cornerCurveLabel;
	private JLabel intrudeWidthLabel;
	private JLabel textPositionsLabel;
	private JLabel textMarginLabel;
	private JLabel boxLayoutLabel;
	private JLabel weddingLayoutLabel;
	private JLabel endLineArrowLabel;
	private JLabel endLineArrowHeightLabel;
	private JLabel endLineArrowFontLabel;
	private JLabel endLineArrowHeadLabel;
	private JLabel endLineArrowHeadHeightLabel;
	private JLabel textDirectionLabel;
	private JLabel weddingLocationLabel;
	private JLabel paddingAmountLabel;
	
	private JSpinner widthSpinner;
	private JSpinner heightSpinner;
	private JSpinner fontSizeSpinner;
	private JSpinner nameFontSizeSpinner;
	private JSpinner relativeOffsetSpinner;
	private JSpinner rootBackOffsetSpinner;
	private JSpinner borderWidthSpinner;
	private JSpinner cornerCurveSpinner;
	private JSpinner intrudeWidthSpinner;
	private JSpinner textPositionsSpinner;
	private JSpinner textMarginSpinner;
	private JSpinner endLineArrowSpinner;
	private JSpinner endLineArrowHeightSpinner;
	private JSpinner endLineArrowFontSpinner;
	private JSpinner endLineArrowHeadSpinner;
	private JSpinner endLineArrowHeadHeightSpinner;
	private JSpinner paddingAmountSpinner;
		
	private JCheckBox intrusionCheckBox;
	
	private JComboBox textPositionsComboBox;
	private JComboBox boxLayoutComboBox;
	private JComboBox weddingLayoutComboBox;
	private JComboBox textDirectionComboBox;
	private JComboBox weddingLocationComboBox;
	
	private JButton textPositionsButton;
	
	private JPanel textPositionPanel;
	private JPanel intrusionPanel;
	
	public PresetChartOptionsPanel parent;
	private StylingBox selectedStyle;
	private boolean isAncestor;
	private int selectedGen;
	
	private boolean maskEvents = false;
	
	
	public PresetChartOptionsStyleEditPanel(PresetChartOptionsPanel parent){
		this.parent = parent;
		initialize();
		
	}
	
	private void initialize(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		textPositionPanel = new JPanel(new GridBagLayout());
		intrusionPanel = new JPanel(new GridBagLayout());
		
		styleGen = new JLabel("Selected: None");
		widthLabel = new JLabel("Box Width: ");
		heightLabel = new JLabel("Box Height: ");
		fontSizeLabel = new JLabel("Normal Font Size: ");
		nameFontSizeLabel = new JLabel("Name Font Size: ");
		relativeOffsetLabel = new JLabel("Relative Offset: ");
		rootBackOffsetLabel = new JLabel("Root Back Offset: ");
		borderWidthLabel = new JLabel("Border Width: ");
		cornerCurveLabel = new JLabel("Rounded Corner Amount: ");
		intrudeWidthLabel = new JLabel("Intrusion Width: ");
		textPositionsLabel = new JLabel("Text Positions");
		textMarginLabel = new JLabel("Text Margins");
		boxLayoutLabel = new JLabel("Text Layout");
		weddingLayoutLabel = new JLabel("Wedding Layout");
		endLineArrowLabel = new JLabel("End Line Arrow Width");
		endLineArrowHeightLabel = new JLabel("End Line Arrow Shaft Height");
		endLineArrowFontLabel = new JLabel("End Line Arrow Font Size");
		endLineArrowHeadLabel = new JLabel("Arrow Head Length");
		endLineArrowHeadHeightLabel = new JLabel("Arrow Head Height");
		textDirectionLabel = new JLabel("Text Direction");
		weddingLocationLabel = new JLabel("Wedding Pos");
		paddingAmountLabel = new JLabel("Padding");
		
		widthSpinner = getWidthSpinner();
		heightSpinner = getHeightSpinner();
		fontSizeSpinner = getFontSizeSpinner();
		nameFontSizeSpinner = getNameFontSizeSpinner();
		relativeOffsetSpinner = getRelativeOffsetSpinner();
		rootBackOffsetSpinner = getRootBackOffsetSpinner();
		borderWidthSpinner = getBorderWidthSpinner();
		cornerCurveSpinner = getCornerCurveSpinner();
		intrudeWidthSpinner = getIntrudeWidthSpinner();
		intrusionCheckBox = getIntrusionCheckBox();
		textPositionsComboBox = getTextPositionsComboBox();
		textPositionsSpinner = getTextPositionsSpinner();
		textPositionsButton = getTextPositionsButton();
		textMarginSpinner = getTextMarginSpinner();
		boxLayoutComboBox = getBoxLayoutComboBox();
		weddingLayoutComboBox = getWeddingLayoutComboBox();
		endLineArrowSpinner = getEndLineArrowSpinner();
		endLineArrowHeightSpinner = getEndLineArrowHeightSpinner();
		endLineArrowFontSpinner = getEndLineArrowFontSpinner();
		endLineArrowHeadSpinner = getEndLineArrowHeadSpinner();
		endLineArrowHeadHeightSpinner = getEndLineArrowHeadHeightSpinner();
		textDirectionComboBox = getTextDirectionComboBox();
		weddingLocationComboBox = getWeddingLocationComboBox();
		paddingAmountSpinner = getPaddingAmountSpinner();
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		//c.fill = GridBagConstraints.HORIZONTAL;
		add(styleGen, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		add(widthLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		add(widthSpinner, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		add(heightLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		add(heightSpinner, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		add(fontSizeLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		add(fontSizeSpinner, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 4;
		add(nameFontSizeLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		add(nameFontSizeSpinner, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 5;
		add(relativeOffsetLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 5;
		add(relativeOffsetSpinner, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 6;
		add(rootBackOffsetLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 6;
		add(rootBackOffsetSpinner, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 7;
		add(borderWidthLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 7;
		add(borderWidthSpinner, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 8;
		add(cornerCurveLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 8;
		add(cornerCurveSpinner, c);

		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		intrusionPanel.add(intrudeWidthLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		intrusionPanel.add(intrusionCheckBox, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 9;
		intrusionPanel.add(intrudeWidthSpinner, c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 2;
		add(intrusionPanel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 10;
		c.gridwidth = 2;
		add(textPositionsLabel, c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		textPositionPanel.add(textPositionsComboBox, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		textPositionPanel.add(textPositionsButton, c);
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 2;
		textPositionPanel.add(textPositionsSpinner, c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 11;
		c.gridwidth = 2;
		add(textPositionPanel, c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 12;
		c.gridwidth = 1;
		add(textMarginLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 12;
		c.gridwidth = 1;
		add(textMarginSpinner, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 13;
		add(boxLayoutLabel, c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 14;
		c.gridwidth = 2;
		add(boxLayoutComboBox, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 15;
		add(weddingLayoutLabel, c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 16;
		add(weddingLayoutComboBox, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 17;
		add(endLineArrowLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 17;
		add(endLineArrowSpinner, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 18;
		add(endLineArrowFontLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 18;
		add(endLineArrowFontSpinner, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 19;
		add(endLineArrowHeadLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 19;
		add(endLineArrowHeadSpinner, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 20;
		add(textDirectionLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 20;
		add(textDirectionComboBox, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 21;
		add(weddingLocationLabel, c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 22;
		add(weddingLocationComboBox, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 23;
		add(endLineArrowHeightLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 23;
		add(endLineArrowHeightSpinner, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 24;
		add(endLineArrowHeadHeightLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 24;
		add(endLineArrowHeadHeightSpinner, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 25;
		add(paddingAmountLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 25;
		add(paddingAmountSpinner, c);

		//refreshBoxes();
	}
	
	private void refreshBoxes(){
		maskEvents = true;
		if (selectedStyle == null)
		{
			styleGen.setText("Selected: None");
			
			widthSpinner.getModel().setValue(0.0);
			widthSpinner.setEnabled(false);
			heightSpinner.getModel().setValue(0.0);
			heightSpinner.setEnabled(false);
			fontSizeSpinner.getModel().setValue(0.0);
			fontSizeSpinner.setEnabled(false);
			nameFontSizeSpinner.getModel().setValue(0.0);
			nameFontSizeSpinner.setEnabled(false);
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
			
			endLineArrowSpinner.getModel().setValue(0.0);
			endLineArrowSpinner.setEnabled(false);
			endLineArrowHeightSpinner.getModel().setValue(0.0);
			endLineArrowHeightSpinner.setEnabled(false);
			endLineArrowFontSpinner.getModel().setValue(0.0);
			endLineArrowFontSpinner.setEnabled(false);
			endLineArrowHeadSpinner.getModel().setValue(0.0);
			endLineArrowHeadSpinner.setEnabled(false);
			endLineArrowHeadHeightSpinner.getModel().setValue(0.0);
			endLineArrowHeadHeightSpinner.setEnabled(false);
			
			textDirectionComboBox.setEnabled(false);
			textDirectionComboBox.setSelectedIndex(-1);
		}
		else
		{
			
			if (isAncestor)
				styleGen.setText("Selected Ancestor Gen: " + selectedGen);
			else
				styleGen.setText("Selected Descendant Gen: " + selectedGen);
			
			if (!widthSpinner.isEnabled()){
				widthSpinner.setEnabled(true);
				heightSpinner.setEnabled(true);
				fontSizeSpinner.setEnabled(true);
				nameFontSizeSpinner.setEnabled(true);
				relativeOffsetSpinner.setEnabled(true);
				rootBackOffsetSpinner.setEnabled(true);
				borderWidthSpinner.setEnabled(true);
				cornerCurveSpinner.setEnabled(true);
				textPositionsButton.setEnabled(true);
				textPositionsSpinner.setEnabled(true);
				textPositionsComboBox.setEnabled(true);
				textMarginSpinner.setEnabled(true);
				boxLayoutComboBox.setEnabled(true);
				weddingLayoutComboBox.setEnabled(true);
				endLineArrowSpinner.setEnabled(true);
				endLineArrowHeightSpinner.setEnabled(true);
				endLineArrowFontSpinner.setEnabled(true);
				endLineArrowHeadSpinner.setEnabled(true);
				endLineArrowHeadHeightSpinner.setEnabled(true);
				textDirectionComboBox.setEnabled(true);
				weddingLocationComboBox.setEnabled(true);
				paddingAmountSpinner.setEnabled(true);
				
				intrudeWidthSpinner.setEnabled(true);
				
				intrusionCheckBox.setEnabled(true);
				
			}
			
			widthSpinner.getModel().setValue(selectedStyle.getBoxWidth());
			heightSpinner.getModel().setValue(selectedStyle.boxHeight);			
			fontSizeSpinner.getModel().setValue(selectedStyle.fontSize);			
			nameFontSizeSpinner.getModel().setValue(selectedStyle.fontNameSize);			
			relativeOffsetSpinner.getModel().setValue(selectedStyle.getRelativeOffset());
			rootBackOffsetSpinner.getModel().setValue(selectedStyle.rootBackOffset);
			borderWidthSpinner.getModel().setValue(selectedStyle.borderlineWidth);			
			cornerCurveSpinner.getModel().setValue(selectedStyle.cornerCurve);			
			intrudeWidthSpinner.getModel().setValue(selectedStyle.intrudeWidth);
			intrusionCheckBox.setSelected(selectedStyle.isIntruding);
			endLineArrowSpinner.getModel().setValue(selectedStyle.endLineArrowShaftLength);
			endLineArrowHeightSpinner.getModel().setValue(selectedStyle.endLineArrowShaftHeight);
			endLineArrowFontSpinner.getModel().setValue(selectedStyle.endLineArrowFontSize);
			endLineArrowHeadSpinner.getModel().setValue(selectedStyle.endLineArrowHeadLength);
			endLineArrowHeadHeightSpinner.getModel().setValue(selectedStyle.endLineArrowHeadHeight);
			paddingAmountSpinner.getModel().setValue(selectedStyle.paddingAmount);
			
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
		}
		maskEvents = false;
		
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
						parent.options.setStyleBoxChanged(true);
						parent.parent.refresh();
						maskEvents = false;
					}
				}
			});
		}
		return intrusionCheckBox;
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

	JSpinner getEndLineArrowSpinner() {
		if (endLineArrowSpinner == null) {
			final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
			spinnerModel.setMaxvalue(10000);
			spinnerModel.setMinvalue(0);
			spinnerModel.setValue(0f);
			endLineArrowSpinner = new JSpinner();
			endLineArrowSpinner.setModel(spinnerModel);
			endLineArrowSpinner.setEditor(new FloatEditor(endLineArrowSpinner));
			endLineArrowSpinner.setEnabled(true);
			endLineArrowSpinner.setToolTipText("The length of the end of line arrow");
			endLineArrowSpinner.setPreferredSize(new Dimension(60, 20));
			endLineArrowSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (maskEvents == false){
						maskEvents = true;
						selectedStyle.endLineArrowShaftLength = new Double((Float) spinnerModel.getValue());parent.options.setStyleBoxChanged(true);
						parent.parent.refresh();
						maskEvents = false;
					}
				}
			});
		}
		return endLineArrowSpinner;
	}
	
	JSpinner getEndLineArrowHeightSpinner() {
		if (endLineArrowHeightSpinner == null) {
			final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
			spinnerModel.setMaxvalue(10000);
			spinnerModel.setMinvalue(0);
			spinnerModel.setValue(0f);
			endLineArrowHeightSpinner = new JSpinner();
			endLineArrowHeightSpinner.setModel(spinnerModel);
			endLineArrowHeightSpinner.setEditor(new FloatEditor(endLineArrowHeightSpinner));
			endLineArrowHeightSpinner.setEnabled(true);
			endLineArrowHeightSpinner.setToolTipText("The height of the shaft of the end of line arrow");
			endLineArrowHeightSpinner.setPreferredSize(new Dimension(60, 20));
			endLineArrowHeightSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
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
		return endLineArrowHeightSpinner;
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
	
	JSpinner getEndLineArrowHeadSpinner() {
		if (endLineArrowHeadSpinner == null) {
			final FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
			spinnerModel.setMaxvalue(10000);
			spinnerModel.setMinvalue(0);
			spinnerModel.setValue(0f);
			endLineArrowHeadSpinner = new JSpinner();
			endLineArrowHeadSpinner.setModel(spinnerModel);
			endLineArrowHeadSpinner.setEditor(new FloatEditor(endLineArrowHeadSpinner));
			endLineArrowHeadSpinner.setEnabled(true);
			endLineArrowHeadSpinner.setToolTipText("The size of arrow head at the end of line arrow");
			endLineArrowHeadSpinner.setPreferredSize(new Dimension(60, 20));
			endLineArrowHeadSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
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
		return endLineArrowHeadSpinner;
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

}
