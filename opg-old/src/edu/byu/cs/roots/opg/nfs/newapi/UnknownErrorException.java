package edu.byu.cs.roots.opg.nfs.newapi;

public class UnknownErrorException extends Exception{

	private static final long serialVersionUID = 1000L;

	public String getMessage() {
		return "An unknown error has occured.\n"+
				"Please contact OnePageGenealogy\n"+
				"so it can be resolved";
	}
}
