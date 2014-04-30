package edu.byu.cs.roots.opg.gui.tools;

import java.text.ParseException;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

public class FloatEditor extends javax.swing.JSpinner.DefaultEditor {
	private static final long serialVersionUID = 341477045272980456L;
	JSpinner spinner;
	FloatSpinnerModel model = null;
	//JFormattedTextField textfield = null;
	
	public FloatEditor(JSpinner spinner){
		super(spinner);
		this.spinner = spinner;
		getTextField().setEditable(true);
		
		
	}
	
	public SpinnerModel getModel(){
		if(model == null){
//			System.out.println(getSpinner());
			model = (FloatSpinnerModel) getSpinner().getModel();
		}
		return model;
	}

	@Override
	public void commitEdit() throws ParseException {
		try {
			Float val = Float.parseFloat(getTextField().getText());
			Float curval = (Float) getModel().getValue();
//			System.out.println("Comparing" + val + " "+ curval);
			if(val.compareTo(curval) != 0)
				getModel().setValue(val);
		} catch (NumberFormatException e1) {
			this.getTextField().setText( ((Float) model.getValue()).toString());	
		}
	}

}
