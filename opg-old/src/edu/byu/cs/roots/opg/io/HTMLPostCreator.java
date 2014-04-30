
package edu.byu.cs.roots.opg.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JList;

public class HTMLPostCreator {

	byte[] SessionID = null;
	static String User_Agent = "OnePageGeneology";
	static String Connection = "close";
	static String Content_Type = "multipart/form-data";
	static String Protocol = "http";
	static String Host = "roots.cs.byu.edu";
	static int Port = 80;
	static String Page = "/pedigree/opgsubmit.php"; 
	
	List<String> files = null;
	
	Random rand = new Random();
	
	public HTMLPostCreator()
	{
		SessionID = new byte[64];
		new Random().nextBytes(SessionID);
		files = new LinkedList<String>();
	}
	
	public void AddFile(String file)
	{
		files.add(file);
	}
	
	public static boolean hasInternetConnection()
	{
		try {
			URLConnection conn = new URL(Protocol,Host,Port, Page).openConnection();
			conn.setRequestProperty("User-Agent", User_Agent);
			conn.setDoOutput(true);
//			OutputStream toWeb = conn.getOutputStream();
			return true;
		}catch (IOException e)
		{
			return false;
		}
	}
	
	public String Send()
	{
		String response = "Test";
		List<String> filesNotUploaded = new LinkedList<String>();
		byte[] boundary = generateBoundary();
		// Need Content Type, Date Application, Length
		try {
			URLConnection conn = new URL(Protocol,Host,Port, Page).openConnection();
			conn.setRequestProperty("User-Agent", User_Agent);
			
			/*  POST Structure
			 * 
		Content-type: multipart/form-data, boundary=AaB03x

        --AaB03x
        Content-disposition: attachment; filename="file1.txt"
        Content-Type: text/plain

        ... contents of file1.txt ...
        --AaB03x
        Content-disposition: attachment; filename="file2.gif"
        Content-type: image/gif
        Content-Transfer-Encoding: binary

          ...contents of file2.gif...
        --AaB03x--
        
			 */
			
			// Create the cluster for files. All in one major boundary
			conn.setRequestProperty("Content-type", Content_Type + ", boundary=" + new String(boundary, "UTF-8"));
			conn.setDoOutput(true);
			OutputStream toWeb = conn.getOutputStream();
			toWeb.write(("\n--" + new String(boundary, "UTF-8")).getBytes());
			for (int fileIndex = 0; fileIndex < files.size(); fileIndex++)
				toWeb.write(createFileBlock(files.get(fileIndex), boundary));
			toWeb.write("--\n".getBytes());
			toWeb.close();
			byte[] input = new byte[conn.getInputStream().available()];
			conn.getInputStream().read(input);
			response = new String(input);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Get session ID
		String SessionID = response.substring(response.indexOf(':') + 2, response.indexOf('\n')).trim();
		// Verify that all the files were uploaded.
		String uploadedFiles = response.substring(response.indexOf("List of files submitted:"));
		uploadedFiles = uploadedFiles.substring(uploadedFiles.indexOf('\n'));
		
		for (int fileIndex = 0; fileIndex < files.size(); fileIndex++)
		{
			String curFile = files.get(fileIndex);
			curFile = curFile.substring((curFile.lastIndexOf('\\') > -1 ? curFile.lastIndexOf('\\') : curFile.lastIndexOf('/')) + 1);
			if (!uploadedFiles.contains("\n" + curFile + "\n"))
			{
				filesNotUploaded.add(files.get(fileIndex));
			}
		}
		
		if (filesNotUploaded.size() > 0)
		{
			JFrame msgBox =  new JFrame("Files Not Uploaded");
			JList lstNotUploaded = new JList(filesNotUploaded.toArray());
			msgBox.add(lstNotUploaded);
		}
		
		// Return the session ID byte string
		return SessionID;
 	}
	
	private byte[] createFileBlock(String Filename, byte[] boundary)
	{
		byte[] retValue = null;
		 
		try
		{
			String tempFileName = Filename;
			if (tempFileName.indexOf("\\") > -1)
				tempFileName = Filename.substring(Filename.lastIndexOf("\\") + 1);
			else if (tempFileName.indexOf("/") > -1)
				tempFileName = Filename.substring(Filename.lastIndexOf("/") + 1);
			//  
			String header = "\ncontent-disposition: attachment; name=\"" + tempFileName +"\"; filename=\"" + tempFileName + "\"\nContent-Type: application/opg\nContent-Transfer-Encoding: binary\n\n";
			//File fileToSend = new File(Filename);
			FileInputStream fileToSend = new FileInputStream(Filename);
			byte[] file = new byte[fileToSend.available()];
			fileToSend.read(file);
			String footer = "\n--" + new String(boundary, "UTF-8");
			retValue = new byte[header.getBytes().length + file.length + footer.getBytes().length];
			System.arraycopy(header.getBytes(), 0, retValue, 0, header.getBytes().length);
			System.arraycopy(file, 0, retValue, header.getBytes().length, file.length);
			System.arraycopy(footer.getBytes(), 0, retValue, header.getBytes().length + file.length, footer.getBytes().length);
			//String block = header + new String(file, "UTF-8") + footer;
			//retValue = block.getBytes();
		}
		catch (UnsupportedEncodingException e)
		{
			retValue = new byte[0];
		}
		catch (FileNotFoundException e)
		{
			retValue = new byte[0];
		}
		catch (IOException e)
		{
			retValue = new byte[0];
		}
		return retValue;
	}
	
	private byte[] generateBoundary()
	{
		byte[] retValue = new byte[rand.nextInt(32) + 32];	// Make the boundary between 32 and 64 bytes long
		for (int boundIndex = 0; boundIndex < retValue.length; boundIndex++)
		{
			int nextChar = rand.nextInt(62);
			retValue[boundIndex] = (byte)(nextChar < 26 ? 'A' + nextChar : (nextChar < 52 ? 'a' + (nextChar - 26) : (nextChar - 52) + '0'));
		}
		return retValue;
	}
}
