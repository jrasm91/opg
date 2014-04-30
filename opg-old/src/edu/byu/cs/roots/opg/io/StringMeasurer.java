package edu.byu.cs.roots.opg.io;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class StringMeasurer extends JPanel{
	
	private static final long serialVersionUID = -7555111689710259742L;

	public StringMeasurer(){
		
	}
	
	public int getMaxFontSize(String string, Graphics2D g, Font font, int heightOfBox, int widthOfBox){
		Font testFont = font;
		int largestSoFar = -1;
		while(true){
			FontRenderContext frc = g.getFontRenderContext();
			TextLayout layout = new TextLayout(string, testFont, frc);
			Rectangle2D minRect = layout.getBounds();
			if(minRect.getWidth() <= widthOfBox && minRect.getHeight() <= heightOfBox){
				largestSoFar = testFont.getSize();
				testFont = new Font(testFont.getFamily(),testFont.getStyle(), testFont.getSize()+1);
			}
			else if(minRect.getWidth() > widthOfBox || minRect.getHeight() > heightOfBox){
				break;
			}
		}
		g.drawString(string,50,50);
		repaint();
		return largestSoFar;
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.black);
		Point2D loc = new Point(30,30);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawRect(20, 20, 100, 57);
		Font font = new Font("Helvetica", Font.BOLD, 12);
//		if(font == null)
//			System.exit(0);
		FontRenderContext frc = g2.getFontRenderContext();
		TextLayout layout = new TextLayout("this is a test String", font, frc);
		layout.draw(g2, (float)loc.getX(), (float)loc.getY());
		Rectangle2D bounds = layout.getBounds();
		bounds.setRect(bounds.getX()+loc.getX(),
                bounds.getY()+loc.getY(),
                bounds.getWidth(),
                bounds.getHeight());
		g2.draw(bounds);
	}
	
	public static void main(String[] args){
		StringMeasurer frame = new StringMeasurer();
		JFrame f = new JFrame();
		f.getContentPane().add(frame);
		f.setSize(500,500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		int res = frame.getMaxFontSize("hello there how are you?", (Graphics2D)frame.getGraphics(), new Font("Helvetica",Font.BOLD,5), 75, 200);
		System.err.println("hello: "+res);

	}
}