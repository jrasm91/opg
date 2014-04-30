package opg.main;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import opg.gui.DrawableChart;
import opg.other.Chart;

public class View extends JPanel {

	private static View singleton = new View();

	public static View singleton() {
		return singleton;
	}

	private float scale;
	private boolean showRuler;
	private boolean showGrid;
	private Point downPoint;

	private View() {
		this.setBackground(Const.COLOR_PAGE_BACKGROUND);
		this.scale = 1;
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.downPoint = null;
	}

	public void setRuler(boolean isShown) {
		showRuler = isShown;
		this.repaint();
	}

	public void setGrid(boolean isShown) {
		showGrid = isShown;
		this.repaint();
	}

	public void zoomIn() {
		scale *= 2;
		if (scale > Const.ZOOM_IN_MAX)
			scale = Const.ZOOM_IN_MAX;

		this.repaint();
		this.revalidate();
	}

	public void zoomOut() {
		scale /= 2;
		if (scale < Const.ZOOM_OUT_MAX)
			scale = Const.ZOOM_OUT_MAX;

		this.repaint();
		this.revalidate();
	}

	private MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mouseDragged(MouseEvent event) {
			downPoint = event.getPoint();
			View.this.repaint();
			View.this.revalidate();
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			downPoint = null;
			View.this.repaint();
			View.this.revalidate();
		}
	};

	@Override
	public Dimension getPreferredSize() {
		Chart chart = Controller.singleton().getChart();
		if (chart == null) {
			int w = (int) (scale * Const.DRAWING_SIZE.width);
			int h = (int) (scale * Const.DRAWING_SIZE.height);
			return new Dimension(w, h);
		} else {
			int w = (int) (scale * (chart.getWidth() + Const.MARGIN_DRAWING * 2));
			int h = (int) (scale * (chart.getHeight() + Const.MARGIN_DRAWING * 2));
			return new Dimension(w, h);
		}
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D g = (Graphics2D) graphics;
		Chart chart = Controller.singleton().getChart();

		AffineTransform saveAt = g.getTransform();
		
		// transformations
		g.scale(scale, scale);
		double extraSideSpace = (getWidth() - (chart.getWidth() * scale)) / scale;
		double xTranslate = extraSideSpace / 2;
		double yTranslate = Const.MARGIN_DRAWING;
		g.translate(xTranslate, yTranslate);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// draw chart with new size
//		if (downPoint != null) {
//			int newHeight = (int) Math.abs(downPoint.y / scale - yTranslate);
//			if (newHeight > Const.DEFAULT_PAGE_HEIGHT)
//				chart.setHeight(newHeight);
//			int newWidth = (int) Math.abs(downPoint.x / scale - xTranslate);
//			if (newWidth > Const.DEFUALT_PAGE_WIDTH)
//				chart.setWidth(newWidth);
//		}

		// draw page outline
//		Color oldColor = g.getColor();
//		g.setStroke(new BasicStroke(Const.STROKE_SIZE / scale));
//		g.setColor(Const.COLOR_PAGE_OUTLINE);
//		g.draw((new Rectangle(0, 0, chart.getWidth(), chart.getHeight())));

		// draw page inline (margins)
//		g.setColor(Const.COLOR_PAGE_INLINE);
//		g.drawRect(chart.getMarginLeft(), chart.getMarginTop(), chart.getAvaliableWidth(), chart.getAvaliableHeight());
//		g.setStroke(new BasicStroke(Const.STROKE_SIZE));
//		g.setColor(oldColor);

//		 draw pedigree
		DrawableChart dc = Controller.singleton().getDrawableChart();
		g.translate(dc.getDx(), dc.getDy());
		dc.draw(g);

		//draw grid
		if (showGrid) {
			g.setColor(Const.COLOR_GRID);
			g.setStroke(new BasicStroke(Const.STROKE_SIZE / 2 / scale));
			for (int i = 0; i <= chart.getWidth(); i += Const.RULER_MAX_INTERVAL/4)
				g.drawLine(i, 0, i, chart.getHeight());
			for (int j = 0; j < chart.getHeight(); j += Const.RULER_MAX_INTERVAL/4)
				g.drawLine(0, j, chart.getWidth(), j);
		}

		// draw ruler
		if (showRuler) {
			int offset = -Const.RULER_OFFSET;
			g.setColor(Const.COLOR_RULER);
			g.setStroke(new BasicStroke(Const.STROKE_SIZE / 2 / scale));
			drawRuler(g, offset, 0, offset, chart.getHeight());
			drawRuler(g, 0, offset, chart.getWidth(), offset);
		}

		g.setTransform(saveAt);
	}

	// /////////////////////////////////////////////////////

	private void drawRuler(Graphics2D g, int x1, int y1, int x2, int y2) {
		Font oldFont = g.getFont();
		g.setFont(oldFont.deriveFont(16.0f));
		g.drawLine(x1, y1, x2, y2);
		for (int i = y1; i <= y2; i += Const.RULER_MIN_INTERVAL) {
			int tickLength = getTickLength(i);
			g.drawLine(x1, i, x1 + tickLength, i);
			if (tickLength == Const.RULER_MAX_TICK_LENGTH) {
				FontMetrics metrics = g.getFontMetrics();
				String label = (i - y1) / Const.RULER_MAX_INTERVAL + "";
				g.drawString(label, x1 - metrics.stringWidth(label) - 5, i + metrics.getHeight() / 3);
			}
		}
		for (int i = x1; i <= x2; i += Const.RULER_MIN_INTERVAL) {
			int tickLength = getTickLength(i);
			g.drawLine(i, y1, i, y1 + tickLength);
			if (tickLength == Const.RULER_MAX_TICK_LENGTH) {
				FontMetrics metrics = g.getFontMetrics();
				String label = (i - y1) / Const.RULER_MAX_INTERVAL + "";
				g.drawString(label, i + metrics.getHeight() / 3, y1 - metrics.stringWidth(label) - 5);
			}
		}
		g.setFont(oldFont);
	}

	private int getTickLength(int i) {
		int length = Const.RULER_MAX_TICK_LENGTH;
		int interval = Const.RULER_MAX_INTERVAL;
		while (i % interval != 0) {
			interval /= 2;
			length /= 2;
			if (interval == 0)
				throw new IllegalArgumentException("Bad Tick Mark: " + i);
		}
		return length;
	}
}
