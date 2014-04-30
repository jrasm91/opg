package edu.byu.cs.roots.opg.conf;

import java.awt.Color;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.byu.cs.roots.opg.chart.ChartType;

/**
 * This class is designed to encapsulate all the information which
 * should persist from excecution to execution
 * @author Travix
 *
 */
public class ConfigData implements Serializable{
	public ArrayList<Color> usercolors;
	private HashMap<String, Void> purchasedCharts;
	public String directory, computerId;
	public boolean showRuler;
	public boolean advancedOptions;
	public static final double versionNumber = 0.1;
	
	static final long serialVersionUID = 1000L;
	
	public ConfigData() {
		usercolors = null;
		directory  = null;
		showRuler = false;
		advancedOptions = false;
		purchasedCharts = null;
		computerId = getMACAdress();
		purchasedCharts = new HashMap<String, Void>();
	}
	
	private String getMACAdress(){
		InetAddress ip;
		String retVal = null;
		try {
	 
			ip = InetAddress.getLocalHost();
	 
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
	 
			byte[] mac = network.getHardwareAddress();
	 	 
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
			}
			retVal = sb.toString();
	 
		} catch (UnknownHostException e) {
	 
			e.printStackTrace();
	 
		} catch (SocketException e){
	 
			e.printStackTrace();
	 
		}
		
		return retVal;
	}
	
	public boolean checkId(){
		String curId = getMACAdress();
		System.out.println("Current: " + curId + " Saved: " + computerId);
		if(curId.contentEquals("")){
			System.out.println("No MAC adress, allowing use of config file");
			return true;
		}
		return curId.contentEquals(computerId);
	}
	
	public boolean isPurchased(ChartType type){
		if(purchasedCharts == null)
			purchasedCharts = new HashMap<String, Void>();
		return PurchaseCodes.checkPurchaseCode(type, computerId, purchasedCharts);
	}
	
	public void resetPurchases(){
		if(purchasedCharts == null)
			purchasedCharts = new HashMap<String, Void>();
		else
			purchasedCharts.clear();
	}
	
	public void addPurchase(String code){
		if (PurchaseCodes.isValidCode(code) && !purchasedCharts.containsKey(code))
			purchasedCharts.put(code, null);
	}
	
}
