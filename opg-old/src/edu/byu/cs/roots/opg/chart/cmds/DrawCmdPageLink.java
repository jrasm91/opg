package edu.byu.cs.roots.opg.chart.cmds;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import org.apache.fop.pdf.PDFLink;
import org.apache.fop.svg.PDFDocumentGraphics2D;
import org.apache.fop.svg.PDFFullPageDocumentGraphics2D;

import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgPage;

@SuppressWarnings("serial")
public class DrawCmdPageLink extends DrawCommand{
	private double width, height;
	Individual target;
	
	public DrawCmdPageLink(double width, double height, Individual target){
		this.width = width; this.height = height;
		offset = new Point2D.Double(0,0);
		this.target = target;
	}
	
	@Override
	public Rectangle getShapeBox() {
		return null;
	}

	@Override
	public void execute(Graphics2D g, DrawState state) {
		if(g.getClass() == PDFFullPageDocumentGraphics2D.class){
			int page = state.session.getIndexOf(state.session.getPageByRoot(target.id));
			Rectangle destinationBox;
			if(page != -1){
				OpgPage realPage = state.session.getPageByRoot(target.id);
				int innerPageIndex = realPage.getMakerList().indexOf(realPage.getMakerByRoot(target.id));
				int chartHeight = (int)state.session.getChartHeight(realPage.getMaker(innerPageIndex), realPage.getOptions(innerPageIndex));
				int chartWidth = state.xExtent;
				destinationBox = new Rectangle(0, 0, chartWidth, chartHeight);
			}else
				destinationBox = new Rectangle(0,0,0,0);
			int yReverse = 792;
			int translateY = (int)g.getTransform().getTranslateY();
			double scaleY = g.getTransform().getScaleY();
			int scaledTranslateY = (int)(translateY/scaleY);
			destinationBox.y = 792 - (scaledTranslateY + destinationBox.height);
			String destinationBoxString = destinationBox.x + " "+destinationBox.y + " "+destinationBox.width+" "+destinationBox.height;
//			g.drawRect(destinationBox.x, destinationBox.y, destinationBox.width, destinationBox.height);
			System.out.println("Box: "+destinationBox);
			Rectangle pdfLink = new Rectangle((int)(state.pos.x + offset.x),yReverse - scaledTranslateY - (int)(state.yExtent + offset.y - (state.pos.y - height/2.0)),
					((int)(width)),(int) height);
			try {
				if(page != -1)
					((PDFFullPageDocumentGraphics2D)g).addLink(pdfLink, new AffineTransform(((PDFDocumentGraphics2D)g).getTransform().createInverse()), page, destinationBoxString, PDFLink.INTERNAL);
				else
					System.out.println("Invalid link!");
			} catch (NoninvertibleTransformException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void execute(Graphics2D g, DrawState state, int width, int height,
			double zoom, Point multiChartOffset) {
		execute(g, state);		
	}

	@Override
	public void executeAbsolute(Graphics2D g, DrawState state, int width,
			int height, double zoom) {
		execute(g,state);		
	}
	
}
