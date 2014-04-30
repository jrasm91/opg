package edu.byu.cs.roots.opg.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class Encryption {

	
	public static ConfigData readConfigData(String path) throws Exception{
		byte[] key = new byte[]{8,2,1,6,4,2,3,0};
		Key encKey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(key)); 
		Cipher decCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		decCipher.init(Cipher.DECRYPT_MODE, encKey);
		
		File configFile = new File(path);
		FileInputStream inp = new FileInputStream(configFile);
		CipherInputStream cinp = new CipherInputStream(inp, decCipher);
		ObjectInputStream oinp = new ObjectInputStream(cinp);
		ConfigData retVal = (ConfigData)oinp.readObject();
		oinp.close();
		return retVal;
	}
	
	public static void writeConfigData(String path, ConfigData config) throws Exception{
		byte[] key = new byte[]{8,2,1,6,4,2,3,0};
		Key encKey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(key)); 
		Cipher encCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		encCipher.init(Cipher.ENCRYPT_MODE, encKey);
		
		File configFile = new File(path);
		FileOutputStream out = new FileOutputStream(configFile);
		CipherOutputStream cout = new CipherOutputStream(out, encCipher);
		ObjectOutputStream oout = new ObjectOutputStream(cout);
		oout.writeObject(config);
		oout.close();
	}
}
