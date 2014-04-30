package edu.byu.cs.roots.opg.io;

public class InvalidSyntaxException extends Exception{

	
	String error;
	int lineNumber;
	private static final long serialVersionUID = 4441611160813988168L;
	
	public InvalidSyntaxException(String error, int lineNumber){
		this.error = error;
		this.lineNumber = lineNumber;
	}
	
	public String toString(){
		return error+", on line number "+lineNumber;
	}
}
