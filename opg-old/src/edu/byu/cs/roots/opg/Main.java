package edu.byu.cs.roots.opg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.*;

import edu.byu.cs.roots.opg.gui.OnePageMainGui;
import edu.byu.cs.roots.opg.io.Project;

public class Main {
	
	/**
	 * 
	 */
	private static String WebErrorPre = "ERROR: ";
	private static String ParamPre = "PARAM: ";
	private enum ParamTypes {WIDTH, HEIGHT, ROOT};
	/**
	 * Webpage Parsing
	 * <Error> : <WebErrorPre>[Error Message]
	 * <Param> : <ParamPre><ParamType>"<ParamValue>"
	 */
	
	/**
	 * Launches this application
	 */
	
	public static void main(String[] args) {
		// Webpage parsing of a file
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		System.out.println("Setting up Logging system");
		//Appender app = new ConsoleAppender(new PatternLayout("(%c.java:%L) %m%n"));
		
		//BasicConfigurator.configure(app);
//		PropertyConfigurator.configure("libs/log4j.properties");
		try{
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			
		
		
		}catch (Exception ex){
			System.out.println(ex);
			System.out.println("Failed loading system dependant L&F, defaulting to Java L&F");
		}
		OnePageMainGui main = null;
		try
		{	
			main = new OnePageMainGui();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//compatibility with JWS for '-open' flag
		boolean openFlag = false;
		for (int i = 0; i < args.length; i++)
		{
		    if (openFlag)
		        main.open(args[i]);

		    if (args[i].equals("-open"))
		        openFlag = true;
		    else
		        openFlag = false;
		}
		
		
	}
	
	 public static String getSHAHashStringBase64(String str) throws NoSuchAlgorithmException
     {
		 MessageDigest digest = MessageDigest.getInstance("SHA");
		 digest.update(str.getBytes());
		 byte result[] = Base64.encodeBase64(digest.digest()); 
		 return new String(result);
     }

	 public static boolean doesPasswordMatchHash(String guess, String realHashedPassword) throws NoSuchAlgorithmException
	 {
		 String hashedGuess = getSHAHashStringBase64(guess);
		 return (hashedGuess.equals(realHashedPassword));
	 }
}
