package edu.byu.cs.roots.opg.fonts;

import java.awt.Font;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

public enum OpgFont implements Serializable{
	
	GENTIUM("Serif (Gentium)", "/edu/byu/cs/roots/opg/fonts/GenR102.TTF", "/edu/byu/cs/roots/opg/fonts/GenBasB.ttf"),
	VERA("Serif (Vera)", "/edu/byu/cs/roots/opg/fonts/VeraSe.ttf", "/edu/byu/cs/roots/opg/fonts/VeraSeBd.ttf"),
	VERA_SANS("Sans Serif (Vera)", "/edu/byu/cs/roots/opg/fonts/Vera.ttf", "/edu/byu/cs/roots/opg/fonts/VeraBd.ttf"),
	VERA_MONO("Monospaced (Vera)", "/edu/byu/cs/roots/opg/fonts/VeraMono.ttf", "/edu/byu/cs/roots/opg/fonts/VeraMoBd.ttf"),
	GARA("Garamond", "/edu/byu/cs/roots/opg/fonts/Gara.ttf", "/edu/byu/cs/roots/opg/fonts/GaraBD.ttf"),
	COUR("Courier New", "/edu/byu/cs/roots/opg/fonts/cour.ttf", "/edu/byu/cs/roots/opg/fonts/courbd.ttf");
	//EZRA("Hebrew (Ezra)", "/edu/byu/cs/roots/opg/fonts/Ezra.ttf");
	
	public Font font,boldFont;
	public String displayName;
	
	static final long serialVersionUID = 1000L;
	
	public String toString(){
		return displayName;
	}
	
	public Font getFont(){
		return Font.getFont(font.getFontName());
	}
	
	public Font getFont(int style, float size){
		return font.deriveFont(style, size);
		
	}
	
	public Font getBoldFont(float size){
		return font.deriveFont(Font.BOLD,size);
	}
	
	public static Font getDefaultSerifFont(int style, float size){
		return GENTIUM.font.deriveFont(style, size);
	}
	
	public static Font getDefaultSansSerifFont(int style, float size){
		return VERA_SANS.font.deriveFont(style, size);
	}
		
	private OpgFont(String displayName, String path, String boldPath){
		
		this.displayName = displayName;
//		ClassLoader loader = this.getClass().getClassLoader();
		URL fontUrl = this.getClass().getResource(path);
		URL boldfontUrl = this.getClass().getResource(boldPath);
		try {
			InputStream in = fontUrl.openStream();
			//InputStream in = loader.getResourceAsStream(path);
			font = Font.createFont(Font.TRUETYPE_FONT, in);
			in = boldfontUrl.openStream();
			//boldFont = new Font("Arial",Font.BOLD,1);
			boldFont = Font.createFont(Font.TRUETYPE_FONT, in);
		} catch (Exception e) { 
			e.printStackTrace();
			font = new Font("Lucida Sans", Font.PLAIN, 12);
		} 
	}
}
