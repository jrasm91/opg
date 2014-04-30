package edu.byu.cs.roots.opg.gui.tools;

import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FloatSpinnerModel implements SpinnerModel {

	Float value = 1f;
	float maxvalue = 100;
	float minvalue = 0;
	float stepsize = 0.5f;
	
	
	CopyOnWriteArraySet<ChangeListener> listenerset = new CopyOnWriteArraySet<ChangeListener>();
	
	public void addChangeListener(ChangeListener arg0) {
		listenerset.add(arg0);
	}

	public Object getNextValue() {
//		System.out.println("getting next value from " + value);
		Float value = this.value;
		if(value > maxvalue - stepsize)
			value = null;
		else{
			value -= (float)(Math.IEEEremainder(value, stepsize));
			value += stepsize;
		}
//		System.out.println("returning " + value);
		return value;
	}

	public Object getPreviousValue(){
//		System.out.println("getting previous value from " + value);
		Float value = this.value;
		if(value < minvalue + stepsize)
			value = null;
		else{
			float tempval = (float) Math.IEEEremainder(value , stepsize);
			value -= (stepsize + tempval);
		}
//		System.out.println("returning " + value);
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
		Float val;
		if(arg0 instanceof Double){
			 Double dval = (Double) arg0;
			 val = dval.floatValue();
		}
		else{
			val = (Float) arg0;
		}
		if(val < minvalue) val = minvalue;
		else if(val > maxvalue) val = maxvalue;
		value = val;
		fireStateChanged();
	}

	private void fireStateChanged(){
		ChangeEvent e = new ChangeEvent(value);
		for(ChangeListener listener : listenerset){
			listener.stateChanged(e);
		}
	}
	
	
	

	/**
	 * @return the maxvalue
	 */
	public float getMaxvalue() {
		return maxvalue;
	}

	/**
	 * @param maxvalue the maxvalue to set
	 */
	public void setMaxvalue(float maxvalue) {
		this.maxvalue = maxvalue;
	}

	/**
	 * @return the minvalue
	 */
	public float getMinvalue() {
		return minvalue;
	}

	/**
	 * @param minvalue the minvalue to set
	 */
	public void setMinvalue(float minvalue) {
		this.minvalue = minvalue;
	}

	/**
	 * @return the stepsize
	 */
	public float getStepsize() {
		return stepsize;
	}

	/**
	 * @param stepsize the stepsize to set
	 */
	public void setStepsize(float stepsize) {
		this.stepsize = stepsize;
	}
	
}
