package edu.byu.cs.roots.opg.conf;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.xerces.impl.dv.util.Base64;

public class OpgInterface {
	private static DefaultHttpClient httpclient;
	private static BasicResponseHandler responseHandler;
	static{
		httpclient = new DefaultHttpClient();
		responseHandler = new BasicResponseHandler();
	}
	
	public static void getCodes(){
		String requestUrl = "http://localhost:80/pedigree/purchaseconfirmation.php";
		String responseBody = "";
		String encodedLogin = Base64.encode(new String("username:password").getBytes());
		
		try {
			HttpGet get = new HttpGet(requestUrl);
			get.addHeader("Authority", encodedLogin);
			responseBody = httpclient.execute(get, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(responseBody);
		
	}

}
