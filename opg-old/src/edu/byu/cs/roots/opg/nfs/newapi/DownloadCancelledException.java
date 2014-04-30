package edu.byu.cs.roots.opg.nfs.newapi;

public class DownloadCancelledException extends Exception{
	
	private static final long serialVersionUID = 1000L;

	
	public String getMessage() {
		return "The download was cancelled";
	}
}
