package edu.byu.cs.roots.opg.gui.tools;

import java.text.ParseException;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

public class IntEditor extends javax.swing.JSpinner.DefaultEditor {
	
	private static final long serialVersionUID = 7004140864166043839L;
	JSpinner spinner;
	IntSpinnerModel model = null;
	//JFormattedTextField textfield = null;
	
	public IntEditor(JSpinner spinner){
		super(spinner);
		this.spinner = spinner;
		getTextField().setEditable(true);
		
		this.setEnabled(true);
	}
	
	public SpinnerModel getModel(){
		if(model == null){
//				System.out.println(getSpinner());
			model = (IntSpinnerModel) getSpinner().getModel();
		}
		return model;
	}

	@Override
	public void commitEdit() throws ParseException {
		try {
			Integer val = Integer.parseInt(getTextField().getText());
			Integer curval = (Integer) getModel().getValue();
//				System.out.println("Comparing" + val + " "+ curval);
			if(val.compareTo(curval) != 0)
				getModel().setValue(val);
		} catch (NumberFormatException e1) {
			this.getTextField().setText( ((Integer) model.getValue()).toString());	
		}
	}

}

