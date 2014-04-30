package edu.byu.cs.roots.opg.nfs.newapi;

public class DiscoveryMissingException extends Exception{

private static final long serialVersionUID = 1000L;

	
	public String getMessage() {
		return "There has been an error retrieving\n" +
				"the Discovery resource from New FamilySearch.\n" +
				"Please contact OnePageGenealogy\n" +
				"to have this resolved.";
	}
}
