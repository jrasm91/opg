package edu.byu.cs.roots.opg.nfs.newapi;

import java.awt.AWTError;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import org.apache.xerces.dom.ElementNSImpl;
import org.apache.xerces.impl.dv.util.Base64;
import org.familysearch.platform.FamilySearchPlatform;
import org.familysearch.platform.ct.ParentChildRelationship;
import org.familysearch.ws.client.identity.v2a.schema.Identity;
import org.gedcomx.atom.Feed;
import org.gedcomx.conclusion.Person;
import org.gedcomx.links.Link;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import edu.byu.cs.roots.opg.io.GedcomRecord;
import edu.byu.cs.roots.opg.io.HTTPSecureWrapper;

import edu.byu.cs.roots.opg.nfs.RelationMap;

public class NFSDownloadManager extends SwingWorker<Void,String>{
	
	private final static String devKey = "WCQY-7J1Q-GKVV-7DNM-SQ5M-9Q5H-JX3H-CMJK";
	private final static String nfsKey = "3MRF-LC1D-JJ5S-ML42-223W-7PD7-KLR8-6B83";
	private final static String usedAccessKey = devKey;
	private final static String agentName = "OnePageGenealogy";
	private String baseUrl = "https://sandbox.familysearch.org";
	private String discoveryUrl = "/.well-known/app-meta.xml";
	
	private String parentRelationshipRel = "http://familysearch.org/links/relationships/parents";
	private String childRelationshipRel = "http://familysearch.org/links/relationships/children";
	private String spouseRelationshipRel = "http://gedcomx.org/links/relationships/spouse";
	
	private String atomFeedURI = "http://www.w3.org/2005/Atom";
	
	private DefaultHttpClient httpclient; 
	private ResponseHandler<String> responseHandler;
	private ThreadSafeClientConnManager cm;
	
	
	private RelationMap relMap = null;
	private GedcomRecord gedrecord = null;
	
	private String username = "";
	private String password = "";
	private String sessionId = "";
	
	private Object keyLock;
	
	private NFSLoginFrame loginFrame = null;
	private DownloadOptionDialog downloadOptions = null;
	private DownloadProgress progress = null;
	
	public NFSDownloadManager(){
		
		relMap = new RelationMap();
		gedrecord = new GedcomRecord();
		progress = new DownloadProgress(0);
		
		keyLock = new Object();
		
		initializeHttpDevices();
	}
	
	@Override
	protected void process(List<String> updatedValue){
		StringBuilder build = new StringBuilder();
		for(String s : updatedValue)
			build.append(s+"\n");
		String messages = build.toString();
		progress.updateProgress(relMap.getAllDone().size(), relMap.getAllDone().size() + relMap.getAllNotDone().size(), messages);
	}
	
	
	
	@Override
	protected Void doInBackground(){
		Thread.currentThread().setName("NFSDownloadManagerThread");
		
		try{
			
			getUserLogin();
			authenticateNFS();
			getUserDownloadOptions();
			activateDownloadProgress();
			downloadRoot();
			
		}catch(UsernamePasswordException e){
			JOptionPane.showMessageDialog(null, 
					e.getMessage(),
					"Error!", JOptionPane.ERROR_MESSAGE);
		}catch(DownloadCancelledException e){
			System.out.println(e.getMessage());
		}catch(UnknownErrorException e){
			JOptionPane.showMessageDialog(null, 
					e.getMessage(),
					"Error!", JOptionPane.ERROR_MESSAGE);
		}catch(DiscoveryMissingException e){
			JOptionPane.showMessageDialog(null, 
					e.getMessage(),
					"Error!", JOptionPane.ERROR_MESSAGE);
		}
			
			
		

		
		
		return null;
	}
	
	private void getUserLogin() throws DownloadCancelledException{
		//create dialog and retrieve username and password, and load those in
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				loginFrame = new NFSLoginFrame(keyLock);
				loginFrame.pack();
				loginFrame.setVisible(true);
			}
		});
			
		lock();
		if(loginFrame.getCancelled())
			throw new DownloadCancelledException();
			
		username = loginFrame.getUsername();
		password = loginFrame.getPassword();
		System.out.println("UN: " + username + " PW: " + password);
			
		
	}
	
	private void authenticateNFS() throws UsernamePasswordException, UnknownErrorException, DiscoveryMissingException{
		HttpGet get = new HttpGet(baseUrl+discoveryUrl);
		get.addHeader("GET", "/.well-known/app-meta.xml");
		get.addHeader("Accept", "application/x-gedcom-conclusion-v1+xml");
		
		try {
			String responseBody = (httpclient.execute(get, responseHandler));
			System.out.println(responseBody);
			Feed extract = (Feed) JAXBContext.newInstance(Feed.class).createUnmarshaller().unmarshal(new StringReader(responseBody));
			//Gets the link from the discovery to the Identity\Login resource
			System.out.println(extract.toString());
			String loginExtension="";
			for(Link link : extract.getLinks()){
				if(link.getRel().contentEquals("fs-identity-v2")){
					loginExtension = link.getHref();
					break;
				}
			}
			if(loginExtension.contentEquals(""))
				throw new DiscoveryMissingException();
			String requestUrl = loginExtension;
			get = new HttpGet(requestUrl+"?key="+usedAccessKey);
			
			get.addHeader("User-Agent", agentName);
			get.addHeader("Accept", "application/x-gedcom-conclusion-v1+xml");
			String encodedLogin = Base64.encode(new String(username+":"+password).getBytes());
			get.addHeader("Authorization","Basic "+encodedLogin);
			responseBody = (httpclient.execute(get, responseHandler));
		
			Identity identity = (Identity) JAXBContext.newInstance(Identity.class).createUnmarshaller().unmarshal(new StringReader(responseBody));
			sessionId = identity.getSession().getId();
	
		} catch (HttpResponseException e) {
			//TODO find a better way of giving a more accurate error message
			if(e.getStatusCode() == 401)
				throw new UsernamePasswordException();
		} catch(ClientProtocolException e){
			throw new UnknownErrorException();
		} catch (IOException e) {
			throw new UnknownErrorException();
		} catch (JAXBException e) {
			throw new UnknownErrorException();
		}
	}
	
	private void getUserDownloadOptions() throws DownloadCancelledException{
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				downloadOptions = new DownloadOptionDialog(keyLock);
				Dimension screenSize;		
				try {
					Toolkit tk = Toolkit.getDefaultToolkit();
					screenSize = tk.getScreenSize();
				} catch (AWTError awe) {
					screenSize = new Dimension(640, 480);
				}
				
				//Dialog dlgPreOrder = new PreOrderDialog(session, getJFrame());
				int frameX = screenSize.width / 2 - downloadOptions.getWidth() / 2;
				int frameY = screenSize.height / 2 - downloadOptions.getHeight() / 2;
				downloadOptions.setBounds(frameX, frameY,downloadOptions.getWidth(), downloadOptions.getHeight());
				downloadOptions.setVisible(true);
			}
		});
		
		lock();
		if(!downloadOptions.getDownload())
			throw new DownloadCancelledException();
	}

	private void activateDownloadProgress(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				progress = new DownloadProgress(0);
				Dimension screenSize;		
				try {
					Toolkit tk = Toolkit.getDefaultToolkit();
					screenSize = tk.getScreenSize();
				} catch (AWTError awe) {
					screenSize = new Dimension(640, 480);
				}
				
				//Dialog dlgPreOrder = new PreOrderDialog(session, getJFrame());
				int frameX = screenSize.width / 2 - progress.getWidth() / 2;
				int frameY = screenSize.height / 2 - progress.getHeight() / 2;
				progress.setBounds(frameX, frameY, progress.getWidth(), progress.getHeight());
				progress.setVisible(true);
				progress.startTimer();
			}
			
		});
	}
	
	private void downloadRoot() throws UnknownErrorException, DiscoveryMissingException{
		HttpGet get = new HttpGet(baseUrl+discoveryUrl);
		get.addHeader("GET", "/.well-known/app-meta.xml");
		get.addHeader("Accept", "application/x-gedcom-conclusion-v1+xml");
		get.addHeader("Authorization", "Bearer " + sessionId);
		
		String responseBody;
		try {
			responseBody = (httpclient.execute(get, responseHandler));
			Feed extract = (Feed) JAXBContext.newInstance(Feed.class).createUnmarshaller().unmarshal(new StringReader(responseBody));
			String requestLink = "";
			for(Link link : extract.getLinks()){
				if(link.getRel().contentEquals("current-user-person")){
					requestLink=link.getHref();
					break;
				}
			}
			if(requestLink.contentEquals(""))
				throw new DiscoveryMissingException();
			get = new HttpGet(requestLink);
			get.addHeader("Accept", "application/x-fs-v1+xml");
			get.addHeader("Authorization", "Bearer " + sessionId);
			
			String rootString = responseBody = (httpclient.execute(get,responseHandler));
			
			System.out.println(responseBody);
			
			
			FamilySearchPlatform root = (FamilySearchPlatform) JAXBContext.newInstance(FamilySearchPlatform.class).createUnmarshaller().unmarshal(new StringReader(responseBody));
			Person rootPerson = root.getPersons().get(0);
			
			String ancestryCode = "";
			for(Link link : rootPerson.getLinkExtensions()){
				if(link.getRel().contentEquals("ancestry"))
				{
					ancestryCode = link.getHref();
					break;
				}
			}
			get = new HttpGet(ancestryCode);
			get.addHeader("Accept", "application/x-fs-v1+xml");
			get.addHeader("Authorization", "Bearer " + sessionId);
			
			String ancestryString = responseBody = (httpclient.execute(get,responseHandler));
			System.out.println(ancestryString);
			FamilySearchPlatform ancestry = (FamilySearchPlatform) JAXBContext.newInstance(FamilySearchPlatform.class).createUnmarshaller().unmarshal(new StringReader(ancestryString));
			ancestry.toString();
			
		} catch (HttpResponseException e) {
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public void initializeHttpDevices(){
		HttpParams params = new BasicHttpParams();
	    SchemeRegistry registry = new SchemeRegistry();
	    cm = new ThreadSafeClientConnManager(params, registry);
	    
		httpclient = new DefaultHttpClient(cm, params);	
		httpclient = HTTPSecureWrapper.wrapClient(httpclient);
		
		
		httpclient.getCredentialsProvider().clear();
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(baseUrl, 80), 
				new UsernamePasswordCredentials(username, password));
		
		
		responseHandler = new BasicResponseHandler();
	}
	
	public void lock(){
		synchronized(keyLock){
			try {
				System.out.println("LOCKING ON: " +Thread.currentThread().getName());
				keyLock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
