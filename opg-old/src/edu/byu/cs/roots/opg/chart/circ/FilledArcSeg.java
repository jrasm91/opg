package edu.byu.cs.roots.opg.chart.circ;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;

import edu.byu.cs.roots.opg.chart.cmds.DrawCommand;
import edu.byu.cs.roots.opg.chart.cmds.DrawState;

public class FilledArcSeg extends DrawCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5022278031240974944L;
	float radius, width;
	float startang, sweep;
	Color c, outline;
	public String[] textlines = null;
	float maxfontsize;
	float minfontsize;
	float fontsize;
	int maxlines;
	
	public FilledArcSeg(float rad, float width, float startang, float sweep, Color c) {
		super();
		this.radius = rad;
		this.width = width;
		this.startang = startang;
		this.sweep = sweep;
		this.c = c;
		outline = Color.black;
		textlines = null;
		fontsize = 12;
		maxfontsize = 12;
		minfontsize = 4;
	}

	public FilledArcSeg(float rad, float width, float startang, float sweep, Color c, Color outline) {
		super();
		this.radius = rad;
		this.width = width;
		this.startang = startang;
		this.sweep = sweep;
		this.c = c;
		textlines = null;
		fontsize = 12;
		maxfontsize = 12;
		minfontsize = 4;
	}
	
	
	public void execute(Graphics2D g, DrawState state) {
		float x = (float) state.pos.x;
		float y = (float) state.pos.y;
		
		float dia = 2*radius;
		float line = .5f;
		
		g.setColor(outline);
		BasicStroke s = new BasicStroke((float) (width),BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        g.setStroke(s);
        g.draw(new Arc2D.Double( (x-radius), (y-radius), dia,  dia, startang, sweep, Arc2D.OPEN));

		float edge = (float)((360*line)/(2*Math.PI*radius));
        
		if(width - 2*line > 0 ){
			g.setColor(c);
			s = new BasicStroke((float) width-2*line,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
	        g.setStroke(s);
	        g.draw(new Arc2D.Double( (x-radius), (y-radius), dia,  dia, startang+edge,sweep-2*edge, Arc2D.OPEN));
		}
        
        //text processing
		if (textlines != null) {
			
//			float fontsize = (float) (Math.toRadians(.8*sweep/textlines.length) * radius);
			Font f = g.getFont();
//			for(String textline:textlines){
//				float tmp = FontMaximizer.getMaxFontSize(g, f, textline, width);
//				if(tmp < fontsize) fontsize = tmp;
//			}
//
//			
//			if(fontsize > maxfontsize) fontsize = maxfontsize;
//			else if(fontsize < minfontsize) fontsize = minfontsize;
//			maxlines = (int) (Math.toRadians(.8*sweep) * radius / fontsize);
//			maxlines = (maxlines >textlines.length)? textlines.length:
//					   (maxlines == 0)? 1:maxlines;
//			
			f = f.deriveFont(fontsize);
//			f = f.deriveFont(fontsize).deriveFont(new AffineTransform());
//			double textspacing = g.getFontMetrics(f).getHeight();
			double textspacing = 1.1*f.getSize();
//			System.out.println("text height "+ textspacing);
			double textanglespace = Math.toDegrees(textspacing/radius);
//			System.out.println(textanglespace);
			g.setColor(outline);
//			
			maxlines = textlines.length;
//			System.out.println("maxlines: " + maxlines);
			for(int i = 0; i < maxlines ; i++){
//				double textangle = Math.toRadians(startang + .5 * sweep );
//				double textangleadj = ((i+1)*textanglespace - maxlines*textanglespace*.5 - .5*textspacing/(radius));
//				double rotate;
//				int offset;
//				if (textangle > Math.PI * .5 && textangle < Math.PI * 1.5){
//					textangle+=textangleadj;
//					rotate = Math.PI - textangle;
//					offset = 1;
//				}else{
//					textangle-=textangleadj;
//					rotate = 0 - textangle;
//					offset = -1;
//				}
//				AffineTransform at = AffineTransform.getRotateInstance(rotate);
				
				
				double textangle = (startang > 90 && startang < 270) 
						?  startang + (i+.75)*textanglespace + .5*sweep - (maxlines)*textanglespace*.5
						: startang + (maxlines-(i+.75))*textanglespace +.5*sweep - (maxlines)*textanglespace*.5;
				double rotate = (startang > 90 && startang < 270) ? 180-textangle:0-textangle;
//				System.out.println(textangle + " " + textlines[i]);
				rotate = Math.toRadians(rotate);
				textangle = Math.toRadians(textangle);
				AffineTransform at = AffineTransform.getRotateInstance(rotate);
				int offset = (startang > 90 && startang < 270) ? 1 : -1;
				

				g.setFont(f.deriveFont(at));
//				System.out.println(textlines[i]);
				g.drawString(textlines[i],
						(float) (x + (radius + offset * width * .48)
						* Math.cos(textangle)),
						(float) (y - (radius + offset * width * .48)
						* Math.sin(textangle)));
				
			}
			
			
			
			
			
		}        

        
	}
	
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		execute(g, state);
	}

	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width,
			int height, double zoom) {
		
	}

	@Override
	public Rectangle getShapeBox() {
		return null;
	}


}
