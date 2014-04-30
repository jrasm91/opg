package edu.byu.cs.roots.opg.exc;

public class FailedToSaveException extends Exception {

	private static final long serialVersionUID = 3837403298502270846L;

	public FailedToSaveException(String text){
		super(text);
	}
}
