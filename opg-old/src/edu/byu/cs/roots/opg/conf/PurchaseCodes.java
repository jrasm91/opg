package edu.byu.cs.roots.opg.conf;

import java.util.HashMap;

import edu.byu.cs.roots.opg.chart.ChartType;

public class PurchaseCodes {
	private final static String MultiSheet = "ahe!kglw$02,";
	private final static String Portrait = "e,h2<734th?{";
	private final static String Landscape = "*hw]gn1~9)hw";
	private final static String Working = ".9&g2e%bmq)+";

	public static boolean checkPurchaseCode(ChartType type, String MAC, HashMap<String, Void> purchases){
		if (type == ChartType.MULTISHEET){
			return purchases.containsKey(MultiSheet);
		}
		else if (type == ChartType.PORTRAIT){
			return purchases.containsKey(Portrait);
		}
		else if (type == ChartType.LANDSCAPE){
			return purchases.containsKey(Landscape);
		}
		else if (type == ChartType.WORKING){
			return purchases.containsKey(Working);
		}
		return false;
	}
	
	public static boolean isValidCode(String code){
		return (code.contentEquals(MultiSheet) || code.contentEquals(Portrait) || 
				code.contentEquals(Landscape) || code.contentEquals(Working));
	}
}
