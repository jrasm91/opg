package edu.byu.cs.roots.opg.nfs;

public class UsernamePasswordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getMessage() {
		return "The username and/or password\n" +
		       "you entered was incorrect.\n" +
		       "Please try again!";
	}

}
