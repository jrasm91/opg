package edu.byu.cs.roots.opg.chart;

import javax.swing.JPanel;

import edu.byu.cs.roots.opg.gui.OnePageMainGui;

/**
 * This class is a generic base class which should be extended to create an options panel
 * for each specific chart type.
 * This class is meant to have it's constructor overridden
 *  
 * @author Travix
 *
 */
public class SpecificOptionsPanel extends JPanel {

	private static final long serialVersionUID = 2350083580936491922L;
	public ChartOptions options;
	public OnePageMainGui parent;
	
	protected SpecificOptionsPanel(){
		super();
		initialize();
	}
	
	/**
	 * This method initializes 
	 * 
	 */
	public SpecificOptionsPanel(ChartOptions options) {
		super();
		this.options = options;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
			
	}

}
