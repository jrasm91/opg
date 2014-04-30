package edu.byu.cs.roots.opg.nfs;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class StandardExecuteCallable implements Callable<String>{

	private String retVal, call;
	private DefaultHttpClient client;
	private boolean post;
	private BasicResponseHandler responseHandler;
	
	public StandardExecuteCallable(DefaultHttpClient client, String call, boolean post){
		this.client = client;
		this.call = call;
		this.post = post;
		
		responseHandler = new BasicResponseHandler();
	}
	
	@Override
	public String call() throws Exception {
		if(post){
			executeThread t = new executeThread(client, call, post);
			t.setName("DebugName");
			t.start();
			t.join();
			retVal = t.getVal();
		}
		else{
			executeThread t = new executeThread(client, call, post);
			t.setName("DebugName");
			t.start();
			t.join();
			retVal = t.getVal();
		}

		return retVal;
	}
	
	
	private class executeThread extends Thread{

		private String url, retVal;
		private DefaultHttpClient client;
		private BasicResponseHandler responseHandler;
		private boolean post;
		public executeThread(DefaultHttpClient client, String url, boolean post){
			this.client = client; this.url = url;
			responseHandler = new BasicResponseHandler();
			this.post = post;
		}
		@Override
		public void run() {
			try {
				if(post)
					retVal = client.execute(new HttpPost(url), responseHandler);
				else
					retVal = client.execute(new HttpGet(url), responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public String getVal(){
			return retVal;
		}
		
	}

	
}
