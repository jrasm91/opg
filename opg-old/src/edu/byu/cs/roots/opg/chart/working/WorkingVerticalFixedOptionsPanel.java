package edu.byu.cs.roots.opg.chart.working;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.SpecificOptionsPanel;
import edu.byu.cs.roots.opg.gui.OnePageMainGui;

public class WorkingVerticalFixedOptionsPanel extends SpecificOptionsPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7215063612079105599L;
	private JLabel jLabel = null;
	private OnePageMainGui parent = null;
	private JCheckBox genLabelsCheckBox = null;
	WorkingVerticalFixedChartOptions options;
	
//--------------------CONSTRUCTORS--------------------------------
	public WorkingVerticalFixedOptionsPanel(ChartOptions options, OnePageMainGui parent) 
	{
		super();
		this.options = (WorkingVerticalFixedChartOptions) options;
		initialize();
		this.parent = parent;
	}
	
//--------------------METHODS--------------------------------	

	private void initialize() {
        jLabel = new JLabel();
        jLabel.setText("");
        this.setPreferredSize(new Dimension(100, 100));
        this.setSize(new Dimension(201, 175));
        this.setBorder(BorderFactory.createTitledBorder(null, "Working Chart Options", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
        this.add(getGenLabelsCheckBox(), null);
        this.add(jLabel, null);
			
	}
	
	/**
	 * This method initializes genLabelsCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getGenLabelsCheckBox() {
		if (genLabelsCheckBox == null) {
			genLabelsCheckBox = new JCheckBox();
			genLabelsCheckBox.setText("Generation Labels");
			genLabelsCheckBox.setSelected(options.isDrawTitles());
			genLabelsCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (!parent.maskEvents) {
						options.setDrawTitles(genLabelsCheckBox.isSelected());
						parent.refresh();
					}
				}
			});
		}
		return genLabelsCheckBox;
	}

}
