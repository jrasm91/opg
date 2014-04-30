package edu.byu.cs.roots.opg.chart.cmds;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

import edu.byu.cs.roots.opg.chart.ChartConversion;

public class DrawCmdPicture extends DrawCommand implements Serializable {
	private double height, width;
	private Image picture;
	private Point coord;
	static final long serialVersionUID = 1000L;
	
	public DrawCmdPicture(double width, double height, Image picture)
	{
		this.height = height;
		this.width = width;
		this.picture = picture;
		coord = new Point(DrawCommand.curPos);
	}
	
	//access methods
	public double getHeight() { return height; }
	public double getWidth() { return width; }
	public Image getPicture() { return picture; }

	public void execute(Graphics2D g, DrawState state) {
		Image pic = picture;
		double picHeight = pic.getHeight(null);
		double picWidth = pic.getWidth(null);
		double picAspect = picWidth / picHeight;				
		double picHorizOffset = 0;
		double picVertOffset = 0;
		

		if (picAspect < 1)
			picHorizOffset = (width - ( picWidth*height/picHeight ) )/2.0; 
		else
			picVertOffset = (height - ( picHeight*width/picWidth ) )/2.0;
		
		g.drawImage(picture, (int)(state.pos.getX() + picHorizOffset), (int)(state.yExtent - (state.pos.getY() - picVertOffset)), (int)(width - picHorizOffset*2), (int)(height - picVertOffset*2), null);
	}
	@Override
	public void execute(Graphics2D g, DrawState state, int width, int height, double zoom, Point multiChartOffset) {
		Image pic = picture;
		Point curPos = ChartConversion.convertToScreenCoord(new Point((int)state.pos.x,(int)(state.yExtent - (state.pos.y + pic.getHeight(null)))), zoom, state);
		Rectangle objectBox = new Rectangle(curPos.x, curPos.y, (int)(ChartConversion.convertToScreenSize(pic.getWidth(null), zoom)), (int)(ChartConversion.convertToScreenSize(pic.getHeight(null), zoom)));

		if (viewable(getScreenArray(width, height), objectBox, multiChartOffset))
			execute(g, state);
	}

	@Override
	public Rectangle getShapeBox() {
		return new Rectangle(coord.x, coord.y, (int)width, (int)height);
	}

	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width, int height, double zoom) {
		Image pic = picture;
		double picHeight = pic.getHeight(null);
		double picWidth = pic.getWidth(null);
		double picAspect = picWidth / picHeight;				
		double picHorizOffset = 0;
		double picVertOffset = 0;
		

		if (picAspect < 1)
			picHorizOffset = (this.width - ( picWidth*height/picHeight ) )/2.0; 
		else
			picVertOffset = (this.height - ( picHeight*width/picWidth ) )/2.0;
		
		g.drawImage(picture, (int)(coord.getX() + picHorizOffset), (int)(state.yExtent - (coord.getY() - picVertOffset)), (int)(this.width - picHorizOffset*2), (int)(this.height - picVertOffset*2), null);
	}

}
