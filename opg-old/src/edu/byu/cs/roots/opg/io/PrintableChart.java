package edu.byu.cs.roots.opg.io;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import edu.byu.cs.roots.opg.model.OpgPage;
import edu.byu.cs.roots.opg.model.OpgSession;

public class PrintableChart implements Printable
{
	OpgPage page;
	ChartWriter writer;
	OpgSession session;
	
	public PrintableChart(OpgPage page, ChartWriter writer, OpgSession session) {
		this.page = page;
		this.writer = writer;
		this.session = session;
	}

	public int print(Graphics g, PageFormat pageFormat, int PageNumber)
	{

		Graphics2D g2d = (Graphics2D) g;
		//Translate the origin to 0,0 for the top left corner
		 g2d.translate (pageFormat.getImageableX () , pageFormat.getImageableY ());
		 double scaleRatio = Math.min(pageFormat.getImageableWidth() / page.getPageWidth(), pageFormat.getImageableHeight() / page.getPageHeight());
		 g2d.scale(scaleRatio, scaleRatio);
		 writer.createChart(page ,g2d, (int)pageFormat.getImageableWidth(), (int)pageFormat.getImageableHeight(), 0, 0, 0, g, false, session);	
		return (PAGE_EXISTS);
	}
}