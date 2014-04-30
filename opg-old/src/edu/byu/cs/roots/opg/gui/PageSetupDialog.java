package edu.byu.cs.roots.opg.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;

import edu.byu.cs.roots.opg.gui.tools.FloatEditor;
import edu.byu.cs.roots.opg.gui.tools.FloatSpinnerModel;
import edu.byu.cs.roots.opg.model.Page;
import edu.byu.cs.roots.opg.model.PaperWidth;

public class PageSetupDialog  extends JComponent{
	private static final long serialVersionUID = 977509998648070490L;
//	private JFrame setupFrame = null;  //  @jve:decl-index=0:visual-constraint="116,52"
//	private JPanel colorContentPane = null;
	
	private Page page;  //  @jve:decl-index=0:
	private JDialog dialog = null;
//	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="290,7"
//	private JPanel setupPane = null;
	private JPanel setupPanel = null;
	private JPanel OptionsPanel = null;
	private JLabel jLabel4 = null;
	private JPanel paperLengthPanel = null;
	private JSpinner inchesSpinner = null;
	private FloatEditor floatEditor = null;
	private JLabel inchLabel = null;
	private JLabel jLabel5 = null;
	private JPanel sliderPanel = null;
	private JSlider widthSlider = null;
	private JLabel paperSizeLabel = null;
	private JRadioButton PortraitRadioButton = null;
	private JRadioButton LandscapeRadioButton = null;
	private JButton okButton = null;
	private ButtonGroup orientationGroup = null;  //  @jve:decl-index=0:
	
	
	public void showPageDialog(Component parent, Page page){
		String title = "Page Setup";
		this.page = page;
		Window window = getWindowForComponent(parent);
		if (window instanceof Frame) {
            dialog = new JDialog((Frame)window, title, true);	
        } else {
            dialog = new JDialog((Dialog)window, title, true);
        }
        dialog.setComponentOrientation(this.getComponentOrientation());
        dialog.setSize(new Dimension(259, 174));
        dialog.setResizable(false);
        
//        dialog.addWindowListener(new WindowAdapter() {
//    	    public void windowClosing(WindowEvent e) {;
//    	    }
//    	});
        dialog.setContentPane(getSetupPanel());
//        Container contentPane = dialog.getContentPane();
//        contentPane.setLayout(new BorderLayout());
//        contentPane.add(getSetupPanel(), BorderLayout.CENTER);
        
        
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        
        dialog.setVisible(true);
        firePropertyChange("Page Setup is closing", dialog, null);
    	dialog.dispose();

	}

	
	static Window getWindowForComponent(Component parentComponent) 
	    throws HeadlessException {
	    if (parentComponent == null)
	        return null;
	    if (parentComponent instanceof Frame || parentComponent instanceof Dialog)
	        return (Window)parentComponent;
	    return PageSetupDialog.getWindowForComponent(parentComponent.getParent());
	}

	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
/*	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(259, 174));
			jFrame.setContentPane(getSetupPanel());
		}
		return jFrame;
	}*/


	/**
	 * This method initializes setupPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSetupPanel() {
		if (setupPanel == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridwidth = 1;
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.weighty = 1.0D;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.gridy = 0;
			setupPanel = new JPanel();
			setupPanel.setLayout(new GridBagLayout());
			setupPanel.add(getOptionsPanel(), gridBagConstraints2);
		}
		return setupPanel;
	}


	/**
	 * This method initializes OptionsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getOptionsPanel() {
		if (OptionsPanel == null) {
			getOrientationGroup();
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 4;
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.gridwidth = 2;
			gridBagConstraints53.gridy = 3;
			gridBagConstraints53.gridx = 2;
			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
			gridBagConstraints52.gridwidth = 2;
			gridBagConstraints52.gridy = 3;
			gridBagConstraints52.weighty = 1.0D;
			gridBagConstraints52.gridx = 0;
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints51.gridx = 1;
			gridBagConstraints51.gridy = 2;
			gridBagConstraints51.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints51.gridwidth = 3;
			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.anchor = GridBagConstraints.WEST;
			gridBagConstraints50.gridy = 2;
			gridBagConstraints50.weighty = 1.0D;
			gridBagConstraints50.gridx = 0;
			jLabel5 = new JLabel();
			jLabel5.setText("Width");
			GridBagConstraints gridBagConstraints49 = new GridBagConstraints();
			gridBagConstraints49.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints49.gridx = 2;
			gridBagConstraints49.gridy = 1;
			gridBagConstraints49.gridwidth = 2;
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			gridBagConstraints48.anchor = GridBagConstraints.WEST;
			gridBagConstraints48.gridy = 1;
			gridBagConstraints48.weighty = 1.0D;
			gridBagConstraints48.gridx = 0;
			jLabel4 = new JLabel();
			jLabel4.setText("Height");
			OptionsPanel = new JPanel();
			OptionsPanel.setLayout(new GridBagLayout());
			OptionsPanel.setMinimumSize(new Dimension(150, 150));
			OptionsPanel.setPreferredSize(new Dimension(170, 170));
			OptionsPanel.setBorder(BorderFactory.createTitledBorder(null, "Page Layout", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			OptionsPanel.add(jLabel4, gridBagConstraints48);
			OptionsPanel.add(getPaperLengthPanel(), gridBagConstraints49);
			OptionsPanel.add(jLabel5, gridBagConstraints50);
			OptionsPanel.add(getSliderPanel(), gridBagConstraints51);
			OptionsPanel.add(getPortraitRadioButton(), gridBagConstraints52);
			OptionsPanel.add(getLandscapeRadioButton(), gridBagConstraints53);
			OptionsPanel.add(getOkButton(), gridBagConstraints3);
		}
		return OptionsPanel;
	}

	private ButtonGroup getOrientationGroup() {
		if(orientationGroup == null){
			orientationGroup = new ButtonGroup();
			orientationGroup.add(getPortraitRadioButton());
			orientationGroup.add(getLandscapeRadioButton());
		}
		return orientationGroup;
	}

	/**
	 * This method initializes paperLengthPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPaperLengthPanel() {
		if (paperLengthPanel == null) {
			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
			gridBagConstraints44.gridx = 1;
			gridBagConstraints44.weightx = 1.0D;
			gridBagConstraints44.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints44.gridy = 0;
			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
			gridBagConstraints45.gridx = 0;
			gridBagConstraints45.ipadx = 0;
			gridBagConstraints45.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints45.gridy = 0;
			inchLabel = new JLabel();
			inchLabel.setText("inches");
			paperLengthPanel = new JPanel();
			paperLengthPanel.setLayout(new GridBagLayout());
			paperLengthPanel.add(getInchesSpinner(), gridBagConstraints45);
			paperLengthPanel.add(inchLabel, gridBagConstraints44);
		}
		return paperLengthPanel;
	}


	/**
	 * This method initializes inchesSpinner	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getInchesSpinner() {
		if (inchesSpinner == null) {
			inchesSpinner = new JSpinner();
			inchesSpinner.setEnabled(true);
			inchesSpinner.setToolTipText("The current size of the paper");
			inchesSpinner.setModel(new FloatSpinnerModel());
			inchesSpinner.setEditor(getFloatEditor());
			inchesSpinner.setPreferredSize(new Dimension(50, 20));
			inchesSpinner.setValue(page.getPaperLength());
			inchesSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					double val = (Float) (inchesSpinner.getValue());
					page.setPaperLength(val*72);
				}
			});
		}
		return inchesSpinner;
	}


	/**
	 * This method initializes floatEditor	
	 * 	
	 * @return edu.byu.cs.roots.opg.gui2.tools.FloatEditor	
	 */
	private FloatEditor getFloatEditor() {
		if (floatEditor == null) {
			floatEditor = new FloatEditor(getInchesSpinner());
		}
		return floatEditor;
	}


	/**
	 * This method initializes sliderPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSliderPanel() {
		if (sliderPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			paperSizeLabel = new JLabel();
			paperSizeLabel.setPreferredSize(new Dimension(25, 16));
			paperSizeLabel.setText(PaperWidth.values()[0].displayName);
			paperSizeLabel.setToolTipText("The current size of the paper");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridx = 0;
			sliderPanel = new JPanel();
			sliderPanel.setLayout(new GridBagLayout());
			sliderPanel.setMaximumSize(new Dimension(105, 25));
			sliderPanel.setPreferredSize(new Dimension(105, 25));
			sliderPanel.add(getWidthSlider(), gridBagConstraints);
			sliderPanel.add(paperSizeLabel, gridBagConstraints1);
		}
		return sliderPanel;
	}


	/**
	 * This method initializes widthSlider	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getWidthSlider() {
		if (widthSlider == null) {
			widthSlider = new JSlider();
			widthSlider.setPaintTicks(true);
			widthSlider.setMaximum(PaperWidth.values().length-1);
			widthSlider.setMinimum(0);
			widthSlider.setMajorTickSpacing(1);
			widthSlider.setMinorTickSpacing(1);
			widthSlider.setSnapToTicks(true);
			widthSlider.setPreferredSize(new Dimension(110, 25));
			widthSlider.setToolTipText("Use this slider to determine which paper size you want to use");
			widthSlider.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					PaperWidth width = PaperWidth.values()[widthSlider.getValue()];
					page.setPaperWidth(width);
					paperSizeLabel.setText(width.displayName);
				}
			});
			PaperWidth selected = page.getPaperWidth();
			PaperWidth[] widths = PaperWidth.values();
			int length = widths.length;
			for(int i = 0;i<length;i++){
				if(widths[i] == selected ){
					widthSlider.setValue(i);
					break;
				}
			}
		}
		return widthSlider;
	}


	/**
	 * This method initializes PortraitRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getPortraitRadioButton() {
		if (PortraitRadioButton == null) {
			PortraitRadioButton = new JRadioButton();
			PortraitRadioButton.setToolTipText("Click here to change the orientation so that the height is fully adjustable");
			PortraitRadioButton.setText("Portrait");
			PortraitRadioButton.setSelected(true);
		}
		return PortraitRadioButton;
	}


	/**
	 * This method initializes LandscapeRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getLandscapeRadioButton() {
		if (LandscapeRadioButton == null) {
			LandscapeRadioButton = new JRadioButton();
			LandscapeRadioButton.setToolTipText("Click here to change the orientation of the paper so that the width is fully adjustable");
			LandscapeRadioButton.setText("Landscape");
		}
		return LandscapeRadioButton;
	}


	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("OK");
		}
		return okButton;
	}

}
