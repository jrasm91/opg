package edu.byu.cs.roots.opg.model;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public enum OpgCursor {
	
	Z_IN("/edu/byu/cs/roots/opg/image/z_in.png", new Point(10, 10), "Zoom In"),
	Z_OUT("/edu/byu/cs/roots/opg/image/z_out.png", new Point(10, 10), "Zoom Out"),
	MOVE("/edu/byu/cs/roots/opg/image/handc.png", new Point(10, 10),"Hand"),
	MOVE_GRAB("/edu/byu/cs/roots/opg/image/handcc.png", new Point(10, 10),"Hand grab"),
	ARROWTEXT("/edu/byu/cs/roots/opg/image/arrowtextc.png", new Point(10, 0), "Arrow-Text"),
	TEXT("/edu/byu/cs/roots/opg/image/textc.png", new Point(2, 5), "Text"),
	ARROW("/edu/byu/cs/roots/opg/image/arrowc.png", new Point(0, 0),"Arrow"),
	PRUNE("/edu/byu/cs/roots/opg/image/prune.png", new Point(5,5), "Prune");
	
	
	
	private Cursor cursor;
	
	
	private OpgCursor(String path, Point hotspot, String name){
//		ClassLoader loader = ClassLoader.getSystemClassLoader();
		//InputStream in = loader.getResourceAsStream(path);
		
		URL fontUrl = this.getClass().getResource(path);
		//System.out.println("FontUrl: " + fontUrl);
		Image image = null;
		try {
			InputStream in = fontUrl.openStream();
			image = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		
		Cursor cursor = Cursor.getDefaultCursor();
		if (image != null)
		{
			cursor = Toolkit.getDefaultToolkit().createCustomCursor(image, hotspot, name);
			System.out.println("Loading cursor "+ name);
		}
		else
		{
			System.out.println("Failed to load "+ name);
		}
		this.cursor = cursor;
	}
	
	public Cursor getCursor(){
		return cursor;
	}
	
	
}
