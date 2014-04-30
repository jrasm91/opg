package edu.byu.cs.roots.opg.gui.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EtchedBorder;

public class ColorChooser extends JComponent {
	
	private static final long serialVersionUID = -8473388657390718970L;
//	private JFrame colorFrame = null;  //  @jve:decl-index=0:visual-constraint="116,52"
	private JPanel colorContentPane = null;
	private ColorWheel colorWheel = null;
	private ColorBrightnessBar colorBrightnessBar = null;
	private JPanel colorPreviewPanel = null;
	private JComboBox colorModelBox = null;
	private JSpinner colorSpinner1 = null;
//	private IntSpinnerModel spinnerModel = null;
	private JSpinner colorSpinner2 = null;
//	private IntSpinnerModel spinnerModel1 = null;
	private JSpinner colorSpinner3 = null;
//	private IntSpinnerModel spinnerModel2 = null;
	private JLabel colorLabel1 = null;
	private JLabel colorLabel2 = null;
	private JLabel colorLabel3 = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	private JLabel modelLabel = null;
	
	private JDialog dialog = null;
	private Color oldValue = null;
	private Color returnValue = null;  //  @jve:decl-index=0:


	public Color showColorDialog(Component parent, Color c){
		oldValue = returnValue = c;
		String title = "Color Chooser";
		Window window = getWindowForComponent(parent);
		if (window instanceof Frame) {
            dialog = new JDialog((Frame)window, title, true);	
        } else {
            dialog = new JDialog((Dialog)window, title, true);
        }
        dialog.setComponentOrientation(this.getComponentOrientation());
        dialog.addWindowListener(new WindowAdapter() {
    	    public void windowClosing(WindowEvent e) {
    		returnValue = oldValue;
    	    }
    	});
        Container contentPane = dialog.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(getColorContentPane(), BorderLayout.CENTER);
        
        
        
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        
        dialog.setVisible(true);
        firePropertyChange("Color Chooser is closing", dialog, null);
    	dialog.dispose();
    	dialog = null;
		
		return returnValue;
	}

	
	static Window getWindowForComponent(Component parentComponent) 
	    throws HeadlessException {
	    if (parentComponent == null)
	        return null;
	    if (parentComponent instanceof Frame || parentComponent instanceof Dialog)
	        return (Window)parentComponent;
	    return ColorChooser.getWindowForComponent(parentComponent.getParent());
	}
	
	public void setColorLabels(){
		if(getColorModelBox().getSelectedItem() == ColorUtils.colorScheme.RGB){
			colorLabel1.setText("Red");
			colorLabel2.setText("Green");
			colorLabel3.setText("Blue");
		}else if(getColorModelBox().getSelectedItem() == ColorUtils.colorScheme.HSL){
			colorLabel1.setText("Hue");
			colorLabel2.setText("Saturation");
			colorLabel3.setText("Luminosity");
		}else if(getColorModelBox().getSelectedItem() == ColorUtils.colorScheme.HSV){
			colorLabel1.setText("Hue");
			colorLabel2.setText("Saturation");
			colorLabel3.setText("Brightness");
		}
	}
	
	
	public void refreshColorSelection(){
		if(getColorModelBox().getSelectedItem() == ColorUtils.colorScheme.RGB){
			int r = (Integer) getColorSpinner1().getValue();
			int g = (Integer) getColorSpinner2().getValue();
			int b = (Integer) getColorSpinner3().getValue();
//			System.out.println("Here we are refreshing color selection with " + debugColor(r, g, b));
			float[] hsb = new float[3];
			Color.RGBtoHSB(r,g,b, hsb);
//			log.debug(r + " " + g + " " + b);
//			log.debug(hsb[0] + " " + hsb[1] + " " + hsb[2]);
			getColorWheel().setValues(hsb[0], hsb[1]);
			getColorBrightnessBar().setVal(hsb[2]);
			getColorBrightnessBar().setHueSat(hsb[0], hsb[1]);
			returnValue = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
			getColorPreviewPanel().setBackground(returnValue);
		}else if(getColorModelBox().getSelectedItem() == ColorUtils.colorScheme.HSL){
			float h = ((Integer) getColorSpinner1().getValue())/255f;
			float s = ((Integer) getColorSpinner2().getValue())/255f;
			float l = ((Integer) getColorSpinner3().getValue())/255f;
			float[] hsb = new float[3];
			ColorUtils.HSLtoHSB(h,s,l, hsb);
			getColorWheel().setValues(hsb[0], hsb[1]);
			getColorBrightnessBar().setVal(hsb[2]);
			getColorBrightnessBar().setHueSat(hsb[0], hsb[1]);
			returnValue = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
			getColorPreviewPanel().setBackground(returnValue);
		}else if(getColorModelBox().getSelectedItem() == ColorUtils.colorScheme.HSV){
			float h = ((Integer) getColorSpinner1().getValue())/255f;
			float s = ((Integer) getColorSpinner2().getValue())/255f;
			float v = ((Integer) getColorSpinner3().getValue())/255f;
			getColorWheel().setValues(h,s);
			getColorBrightnessBar().setVal(v);
			getColorBrightnessBar().setHueSat(h,s);
			returnValue = Color.getHSBColor(h,s,v);
			getColorPreviewPanel().setBackground(returnValue);
		}
		getColorWheel().repaint();
		getColorBrightnessBar().repaint();
	}
	
	public void applyColorInput(){
		float h = getColorWheel().getHue();
		float s = getColorWheel().getSat();
		float v = getColorBrightnessBar().getVal();
//		log.debug("hue " + h + " sat " + s + " val " + v);
		if(getColorModelBox().getSelectedItem() == ColorUtils.colorScheme.RGB){
			int color = Color.HSBtoRGB(h,s,v);
			int r = (color & 0xff0000)>>16;
			int g = (color & 0x00ff00)>>8;
			int b = (color & 0x0000ff);
			getColorSpinner1().setValue(r);
			getColorSpinner2().setValue(g);
			getColorSpinner3().setValue(b);
		}else if(getColorModelBox().getSelectedItem() == ColorUtils.colorScheme.HSL){
			float[] hsl = ColorUtils.HSBtoHSL(h,s,v, null);
			getColorSpinner1().setValue((int) (hsl[0]*255));
			getColorSpinner2().setValue((int) (hsl[1]*255));
			getColorSpinner3().setValue((int) (hsl[2]*255));
		}else if(getColorModelBox().getSelectedItem() == ColorUtils.colorScheme.HSV){
			getColorSpinner1().setValue((int) (h*255));
			getColorSpinner2().setValue((int) (s*255));
			getColorSpinner3().setValue((int) (v*255));
		}
	}
	
	/**
	 * This method initializes colorFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
/*	private JFrame getColorFrame() {
		if (colorFrame == null) {
			colorFrame.setTitle("Custom Color Control");
			colorFrame.setSize(new Dimension(333, 222));
			colorFrame.setContentPane(getColorContentPane());
		}
		return colorFrame;
	}
*/
	/**
	 * This method initializes colorContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getColorContentPane() {
		if (colorContentPane == null) {
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			gridBagConstraints62.gridx = 3;
			gridBagConstraints62.gridy = 0;
			modelLabel = new JLabel();
			modelLabel.setText("Model");
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.gridx = 4;
			gridBagConstraints61.gridy = 4;
			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
			gridBagConstraints56.gridx = 3;
			gridBagConstraints56.gridy = 4;
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.gridx = 3;
			gridBagConstraints34.gridy = 3;
			colorLabel3 = new JLabel();
			colorLabel3.setText("Color 3");
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.gridx = 3;
			gridBagConstraints32.gridy = 2;
			colorLabel2 = new JLabel();
			colorLabel2.setText("Color 2");
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 3;
			gridBagConstraints31.gridy = 1;
			colorLabel1 = new JLabel();
			colorLabel1.setText("Color 1");
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			gridBagConstraints58.gridx = 4;
			gridBagConstraints58.gridy = 3;
			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			gridBagConstraints57.gridx = 4;
			gridBagConstraints57.gridy = 2;
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.gridx = 4;
			gridBagConstraints33.gridy = 1;
			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints60.gridwidth = 1;
			gridBagConstraints60.gridx = 4;
			gridBagConstraints60.gridy = 0;
			gridBagConstraints60.weightx = 0.0D;
			gridBagConstraints60.insets = new Insets(5, 0, 0, 0);
			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
			gridBagConstraints59.fill = GridBagConstraints.BOTH;
			gridBagConstraints59.gridwidth = 4;
			gridBagConstraints59.gridx = 1;
			gridBagConstraints59.gridy = 5;
			gridBagConstraints59.insets = new Insets(5, 5, 5, 5);
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints8.gridheight = 6;
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.weighty = 1.0D;
			gridBagConstraints8.ipadx = 0;
			gridBagConstraints8.insets = new Insets(0, 5, 0, 0);
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.weightx = 1.0D;
			gridBagConstraints7.weighty = 1.0D;
			gridBagConstraints7.gridheight = 5;
			colorContentPane = new JPanel();
			colorContentPane.setSize(new Dimension(200,200));
			colorContentPane.setLayout(new GridBagLayout());
			colorContentPane.add(getColorWheel(), gridBagConstraints7);
			colorContentPane.add(getColorBrightnessBar(), gridBagConstraints8);
			colorContentPane.add(getColorPreviewPanel(), gridBagConstraints59);
			colorContentPane.add(getColorModelBox(), gridBagConstraints60);
			colorContentPane.add(getColorSpinner1(), gridBagConstraints33);
			colorContentPane.add(getColorSpinner2(), gridBagConstraints57);
			colorContentPane.add(getColorSpinner3(), gridBagConstraints58);
			colorContentPane.add(colorLabel1, gridBagConstraints31);
			colorContentPane.add(colorLabel2, gridBagConstraints32);
			colorContentPane.add(colorLabel3, gridBagConstraints34);
			colorContentPane.add(getOkButton(), gridBagConstraints56);
			colorContentPane.add(getCancelButton(), gridBagConstraints61);
			colorContentPane.add(modelLabel, gridBagConstraints62);
			setColorLabels();
			setColor(returnValue);
		}
		return colorContentPane;
	}

	/**
	 * This method initializes colorWheel	
	 * 	
	 * @return edu.byu.cs.roots.opg.gui2.tools.ColorWheel	
	 */
	private ColorWheel getColorWheel() {
		if (colorWheel == null) {
			colorWheel = new ColorWheel();
			colorWheel.setMinimumSize(new Dimension(30, 30));
			colorWheel.setDisplaySaturations(40);
			colorWheel.setBorderThickness(100);
			colorWheel.setDisplayColors(100);
			colorWheel.setPreferredSize(new Dimension(110, 110));
			colorWheel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getColorBrightnessBar().setHueSat(colorWheel.getHue(), colorWheel.getSat());
					returnValue = Color.getHSBColor(colorWheel.getHue(), colorWheel.getSat(), colorBrightnessBar.getVal());
					colorPreviewPanel.setBackground(returnValue);
					applyColorInput();
				}
			});
			
		}
		return colorWheel;
	}

	/**
	 * This method initializes colorBrightnessBar	
	 * 	
	 * @return edu.byu.cs.roots.opg.gui2.tools.ColorBrightnessBar	
	 */
	private ColorBrightnessBar getColorBrightnessBar() {
		if (colorBrightnessBar == null) {
			colorBrightnessBar = new ColorBrightnessBar();
			colorBrightnessBar.setMinimumSize(new Dimension(20, 0));
			colorBrightnessBar.setHorizontal(false);
			colorBrightnessBar.setPreferredSize(new Dimension(20, 0));
			colorBrightnessBar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					returnValue = Color.getHSBColor(colorWheel.getHue(), colorWheel.getSat(), colorBrightnessBar.getVal());
					colorPreviewPanel.setBackground(returnValue);
					applyColorInput();
				}
			});
			
		}
		return colorBrightnessBar;
	}

	/**
	 * This method initializes colorPreviewPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getColorPreviewPanel() {
		if (colorPreviewPanel == null) {
			colorPreviewPanel = new JPanel();
			colorPreviewPanel.setLayout(new GridBagLayout());
			colorPreviewPanel.setBackground(Color.white);
			colorPreviewPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			colorPreviewPanel.setMinimumSize(new Dimension(10, 20));
			colorPreviewPanel.setPreferredSize(new Dimension(10, 20));
		}
		return colorPreviewPanel;
	}

	/**
	 * This method initializes colorModelBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getColorModelBox() {
		if (colorModelBox == null) {
			colorModelBox = new JComboBox();
			colorModelBox.setPreferredSize(new Dimension(100, 25));
			colorModelBox.setMinimumSize(new Dimension(90, 25));
			colorModelBox.addItem(ColorUtils.colorScheme.RGB);
			colorModelBox.addItem(ColorUtils.colorScheme.HSV);
			colorModelBox.addItem(ColorUtils.colorScheme.HSL);
			colorModelBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					setColorLabels();
					applyColorInput();
				}
				
			});
		}
		return colorModelBox;
	}

	/**
	 * This method initializes spinnerModel	
	 * 	
	 * @return edu.byu.cs.roots.opg.gui2.tools.IntSpinnerModel	
	 */
/*	private IntSpinnerModel getSpinnerModel() {
		if (spinnerModel == null) {
			spinnerModel = new IntSpinnerModel();
			spinnerModel.setValue(255);
			spinnerModel.setMinvalue(0);
			spinnerModel.setMaxvalue(255);
		}
		return spinnerModel;
	}
*/
	/**
	 * This method initializes colorSpinner1	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getColorSpinner1() {
		if (colorSpinner1 == null) {
			IntSpinnerModel spinnerModel = new IntSpinnerModel();
			spinnerModel.setMaxvalue(255);
			spinnerModel.setMinvalue(0);
			spinnerModel.setValue(255);
			colorSpinner1 = new JSpinner(spinnerModel);
			colorSpinner1.setEditor(new IntEditor(colorSpinner1));
			colorSpinner1.setPreferredSize(new Dimension(45, 20));
			colorSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					refreshColorSelection();
				}
			});
		}
		return colorSpinner1;
	}

	/**
	 * This method initializes spinnerModel1	
	 * 	
	 * @return edu.byu.cs.roots.opg.gui2.tools.IntSpinnerModel	
	 */
/*	private IntSpinnerModel getSpinnerModel1() {
		if (spinnerModel1 == null) {
			spinnerModel1 = new IntSpinnerModel();
			spinnerModel1.setValue(255);
			spinnerModel1.setMinvalue(0);
			spinnerModel1.setMaxvalue(255);
		}
		return spinnerModel1;
	}*/

	/**
	 * This method initializes colorSpinner2	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getColorSpinner2() {
		if (colorSpinner2 == null) {
			IntSpinnerModel spinnerModel = new IntSpinnerModel();
			spinnerModel.setMaxvalue(255);
			spinnerModel.setMinvalue(0);
			spinnerModel.setValue(255);
			colorSpinner2 = new JSpinner(spinnerModel);
			colorSpinner2.setEditor(new IntEditor(colorSpinner2));
			colorSpinner2.setPreferredSize(new Dimension(45, 20));
			colorSpinner2.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					refreshColorSelection();
				}
			});
		}
		return colorSpinner2;
	}

	/**
	 * This method initializes spinnerModel2	
	 * 	
	 * @return edu.byu.cs.roots.opg.gui2.tools.IntSpinnerModel	
	 */
/*	private IntSpinnerModel getSpinnerModel2() {
		if (spinnerModel2 == null) {
			spinnerModel2 = new IntSpinnerModel();
			spinnerModel2.setValue(255);
			spinnerModel2.setMinvalue(0);
			spinnerModel2.setMaxvalue(255);
		}
		return spinnerModel2;
	}*/

	/**
	 * This method initializes colorSpinner3	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getColorSpinner3() {
		if (colorSpinner3 == null) {
			IntSpinnerModel spinnerModel = new IntSpinnerModel();
			spinnerModel.setMaxvalue(255);
			spinnerModel.setMinvalue(0);
			spinnerModel.setValue(255);
			colorSpinner3 = new JSpinner(spinnerModel);
			colorSpinner3.setEditor(new IntEditor(colorSpinner3));
			colorSpinner3.setPreferredSize(new Dimension(45, 20));
			colorSpinner3.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					refreshColorSelection();
				}
			});
		}
		return colorSpinner3;
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
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dialog.setVisible(false);
				}
			});
		}
		return okButton;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					returnValue = oldValue;
					dialog.setVisible(false);
				}
			});
		}
		return cancelButton;
	}
	
	public void setColor(Color c){
		returnValue = c;
		colorPreviewPanel.setBackground(returnValue);
		
		colorSpinner1.setValue(c.getRed());
		colorSpinner2.setValue(c.getGreen());
		colorSpinner3.setValue(c.getBlue());
		refreshColorSelection();
	}
//	private String debugColor(Color c){
//		int r = c.getRed();
//		int g = c.getGreen();
//		int b = c.getBlue();
//		return " RGB(" + r + ", " + g + ", " + b + ")";
//	}
//	private String debugColor(int r, int g, int b){
//		return " RGB(" + r + ", " + g + ", " + b + ")";
//	}
	
	
}
