package edu.byu.cs.roots.opg.gui.tools;

import java.util.HashSet;

import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class IntSpinnerModel implements SpinnerModel {

	Integer value = 255;
	int maxvalue = 255;
	int minvalue = 0;
	int stepsize = 1;
	
	HashSet<ChangeListener> listenerset = new HashSet<ChangeListener>();
	
	public void addChangeListener(ChangeListener arg0) {
		listenerset.add(arg0);
	}

	public Object getNextValue() {
		Integer value = this.value;
		if(value > maxvalue - stepsize)
			value = null;
		else{
			value -= value % stepsize;
			value += stepsize;
		}
//		System.out.println("getting next value " + value);
		return value;
	}

	public Object getPreviousValue(){
		Integer value = this.value;
		
		if(value < minvalue + stepsize)
			value = null;
		else{
			int tempval = value % stepsize;
			if(tempval == 0)value -=stepsize;
			else value -= tempval;
		}
//		System.out.println("getting previous value " + value );
		return value;
	}

	public Object getValue() {
		return value;
	}

	public void removeChangeListener(ChangeListener arg0) {
		listenerset.remove(arg0);
	}

	public void setValue(Object arg0) {
//		System.out.println("setting value to " + arg0);
		Integer val = (Integer) arg0;
		if(value == val) return;
		if(val < minvalue) val = minvalue;
		else if(val > maxvalue) val = maxvalue;
		value = val;
		headsUp();
		
	}

	private void headsUp(){
		ChangeEvent e = new ChangeEvent(value);
		for(ChangeListener listener : listenerset){
			listener.stateChanged(e);
		}
	}

	/**
	 * @return the maxvalue
	 */
	public int getMaxvalue() {
		return maxvalue;
	}

	/**
	 * @param maxvalue the maxvalue to set
	 */
	public void setMaxvalue(int maxvalue) {
		this.maxvalue = maxvalue;
	}

	/**
	 * @return the minvalue
	 */
	public int getMinvalue() {
		return minvalue;
	}

	/**
	 * @param minvalue the minvalue to set
	 */
	public void setMinvalue(int minvalue) {
		this.minvalue = minvalue;
	}

	/**
	 * @return the stepsize
	 */
	public int getStepsize() {
		return stepsize;
	}

	/**
	 * @param stepsize the stepsize to set
	 */
	public void setStepsize(int stepsize) {
		this.stepsize = stepsize;
	}
	
}
