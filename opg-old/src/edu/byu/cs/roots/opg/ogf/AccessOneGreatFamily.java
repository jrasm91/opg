package edu.byu.cs.roots.opg.ogf;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class AccessOneGreatFamily {

	private String username;
	private String password;
	private final static String devKey = "WCQY-7J1Q-GKVV-7DNM-SQ5M-9Q5H-JX3H-CMJK";
	private final static String devPass = "temp";
	private final static String urlBase = "http://wsdev.onegreatfamily.com";
	private String PersonID;
	private DefaultHttpClient httpclient; 
	private ResponseHandler<String> responseHandler;
	
	public AccessOneGreatFamily(){
		httpclient = new DefaultHttpClient();
		responseHandler = new BasicResponseHandler();
		username = "";
		password = "";
	}
	
	public void devLogin() throws ClientProtocolException, IOException{
		String responseBody = "";
		String requestUrl = urlBase+"/v11.02/Developer.svc/Login?developerId="+devKey+"&password="+devPass;
		System.out.println(requestUrl);
		responseBody = httpclient.execute(new HttpGet("http://wsdev.onegreatfamily.com/v11.02/Individual.svc/Read?SessionId=ll3f3mdpag0yv2myypky2ocx&IndiOgfn=123456789"), responseHandler);
		System.out.println("PRINTING"+responseBody);
	}
}
