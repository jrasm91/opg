package edu.byu.cs.roots.opg.gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import edu.byu.cs.roots.opg.chart.ChartConversion;
import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.ChartMaker;
import edu.byu.cs.roots.opg.chart.ChartOptions;
// Added By: Spencer Hoffa  10/31/2012
import edu.byu.cs.roots.opg.chart.ChartType;
import edu.byu.cs.roots.opg.chart.ShapeInfo;
import edu.byu.cs.roots.opg.chart.preset.templates.PresetChartOptions;
import edu.byu.cs.roots.opg.chart.preset.templates.PresetChartOptionsPanel;
import edu.byu.cs.roots.opg.io.AffineOnScreenChartWriter;
import edu.byu.cs.roots.opg.model.OpgCursor;
import edu.byu.cs.roots.opg.model.OpgOptions;
import edu.byu.cs.roots.opg.model.OpgPage;
import edu.byu.cs.roots.opg.model.PaperWidth;
import edu.byu.cs.roots.opg.util.NameAbbreviator;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class OpgPreviewPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -8145243590269347525L;

	private enum DRAG_TYPE
	{
		NONE, HORIZONTAL, VERTICAL, BOTH
	}
	
	public static Logger log = Logger.getLogger(OpgPreviewPanel.class);
	
	OnePageMainGui parent;
	int width, height;
	int mouseX, mouseY, mouseDownX, mouseDownY;
	int xOffset, yOffset;
	private boolean showSelectionRectangle;
	private int rulerwidth = 1;
	private int rulerlength = 36;
	double zoom;
	AffineOnScreenChartWriter writer;
	boolean dragging;
	//private BufferedImage cachedChart;
	private ArrayList<ChartDrawInfo> charts;
	
	private DRAG_TYPE resizing = DRAG_TYPE.NONE;

	private JPanel introPanel; //to be shown when no file is open
	private String introText;
	private String introButtonText = "Open GEDCOM...";
	private String NFSButtonText = "Aquire Information from new.familysearch.org";
	private String OGFButtonText = "Aquire Information from OneGreatFamily";
	private String openPafText = "Open a PAF file";
	private String openOpgText = "Open an OPG file";
	
	public OpgPreviewPanel(OnePageMainGui parent)
	{
            /*
             * Edited BY: Spencer Hoffa
             * Edited ON: 10/31/2012
             * 
             * Simplified the GUI to only have buttons for supported 
             * chart types.
             */
		if (isMac())
		{
			introText = "Welcome to OnePage Genealogy!\n" +
					"To get started creating a genealogy chart, you can either\n" +
					"open a GedCom file from your computer, or log onto\n" +
					"new.familysearch.org and upload your genealogy from there.\n" +
					"For help, click on the Help menu above.";
		}
		else
		{
			introText = "Welcome to OnePage Genealogy!\n" +
					"To get started creating a genealogy chart, you can either\n" +
					"open a GedCom file from your computer, or log onto\n" +
					"new.familysearch.org and upload your genealogy from there.\n" +
					"For help, click on the Help menu above.";
		}
		this.parent = parent;
		//setSize (new Dimension(width, height));
		mouseX = 0;
		mouseY = 0;
		zoom = 1;
		xOffset = yOffset = 0;
		dragging = false;
		showSelectionRectangle = false;
		writer = new AffineOnScreenChartWriter();
		charts = null;
		final int h = (int)parent.getSession().currentPage().getPageHeight();
		final int w = (int)parent.getSession().currentPage().getPageWidth();
		PreviewListener listener = new PreviewListener(this);
		addMouseListener(listener);
		addMouseMotionListener(listener);
		addMouseWheelListener(listener);
		addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent e) {
				height = getHeight();
				width = getWidth();
				repaint();
			}
		});
		this.setFocusable(true);
		this.addKeyListener(new keylistener());
		
		//set up introduction panel - to be shown when no file is open
		this.setBackground(Color.gray);
		introPanel = new JPanel();
		introPanel.setBackground(Color.gray);
		introPanel.setLayout(new BoxLayout(introPanel, BoxLayout.Y_AXIS));
		JTextArea introTextArea = new JTextArea(introText, 30, 10);
		introTextArea.setBackground(Color.gray);
		introTextArea.setEditable(false);
		introTextArea.setHighlighter(null);
		introTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
		introTextArea.setForeground(Color.white);
		JButton introButton = new JButton(introButtonText);
		introButton.setBackground(Color.gray);
		introButton.setOpaque(false);
		introButton.addActionListener(this);
		introButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		introButton.setPreferredSize(new Dimension(130, 30));
		introButton.setMinimumSize(new Dimension(130, 30));
		
		//----------ADDED FOR NEW FAMILY SEARCH USAGE-----------
		//String NFSButtonText = "Aquire Information from new.familysearch.org";
		JButton NFSButton = new JButton(NFSButtonText);
		NFSButton.setBackground(Color.gray);
		NFSButton.setOpaque(false);
		NFSButton.addActionListener(this);
		NFSButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		NFSButton.setPreferredSize(new Dimension(130, 30));
		NFSButton.setMinimumSize(new Dimension(130, 30));		
		//----------------END OF ADDITION-------------------
		
		//For OneGreatFamily
		JButton OGFButton = new JButton(OGFButtonText);
		OGFButton.setBackground(Color.gray);
		OGFButton.setOpaque(false);
		OGFButton.addActionListener(this);
		OGFButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		OGFButton.setPreferredSize(new Dimension(130, 30));
		OGFButton.setMinimumSize(new Dimension(130, 30));
		
		//For PAF
		JButton PafButton = new JButton(openPafText);
		PafButton.setBackground(Color.gray);
		PafButton.setOpaque(false);
		PafButton.addActionListener(this);
		PafButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		PafButton.setPreferredSize(new Dimension(130, 30));
		PafButton.setMinimumSize(new Dimension(130, 30));
		
		//For OPG
		JButton OpgButton = new JButton(openOpgText);
		OpgButton.setBackground(Color.gray);
		OpgButton.setOpaque(false);
		OpgButton.addActionListener(this);
		OpgButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		OpgButton.setPreferredSize(new Dimension(130, 30));
		OpgButton.setMinimumSize(new Dimension(130, 30));
		
                //--------------------------------------------------------------------------------
                //Added By: Spencer Hoffa for simplified GUI
                //Added On: 10/31/2012
                //--------------------------------------------------------------------------------
                //Multi Page
				//Edited By: Spencer Hoffa
				//Edited ON: 2/12/2013
				//We are only doing multipage so just
				//load from file.
                //JButton multiPage = new JButton("MultiPage"); //Original
				JButton multiPage = new JButton("<HTML><CENTER><H2>Load From File</H2></CENTER></HTML>");
				// End Edit
                multiPage.setBackground(Color.gray);
		multiPage.setOpaque(false);
                multiPage.setAlignmentX(Component.LEFT_ALIGNMENT);
                multiPage.setLocation(0, 0);
		//multiPage.setPreferredSize(new Dimension(130, 30));
		//multiPage.setMinimumSize(new Dimension(130, 30));
                multiPage.addActionListener(
                        new ActionListener()
                        {   
                            public void actionPerformed(ActionEvent e) 
                            {
                            String choice = e.getActionCommand();
                            //JOptionPane.showMessageDialog(null, "You have clicked: "+choice);
                            //openFromFile();
                            openChooseLoadType();
                            }
                        }
                        );
                //---------------------------------
                
                /*//Removed by: Spencer HOffa 2/12/2013
                //Advanced
                JButton advanced = new JButton("Advanced");
                advanced.setBackground(Color.gray);
		advanced.setOpaque(false);
                advanced.setAlignmentX(Component.LEFT_ALIGNMENT);
                advanced.setLocation(0, 0);
		advanced.setPreferredSize(new Dimension(130, 30));
		advanced.setMinimumSize(new Dimension(130, 30));
                advanced.addActionListener(
                        new ActionListener()
                        {   
                            public void actionPerformed(ActionEvent e) 
                            {
                                //String choice = e.getActionCommand();
                                //JOptionPane.showMessageDialog(null, "You have clicked: "+choice);
                                openAdvanced();
                            }
                        }
                        );
                //---------------------------------
                 
                 //*/
                //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                //      END ADDITION BY SPENCER HOFFA
                /////////////////////////////////////////////////////////////
                
                /*
                 * Edited by Spencer Hoffa
                 * Edited on: 10/31/2012
                 * Removed unnecesary components
                 * added the needed ones.
                 * 
                 * NOTE: All commented Code is the original code
                 */
                introPanel.add(multiPage);
                //introPanel.add(advanced); //Removed By: Spencer Hoffa 2/12/2013
                
		//introPanel.add(introTextArea);
		//introPanel.add(Box.createRigidArea(new Dimension(0,10)));
		//introPanel.add(PafButton);
		//introPanel.add(OpgButton);
		//introPanel.add(introButton);
                
		
		//----------ADDED FOR NEW FAMILY SEARCH USAGE-----------
		//introPanel.add(NFSButton);		
		//----------------END OF ADDITION-------------------
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                //      END EDITING BY SPENCER HOFFA
                ////////////////////////////////////////////////////////////////
                
		//introPanel.add(OGFButton);
		
		add(introPanel);
		introPanel.setVisible(false);
	}
 
	public void paint(Graphics g)
	{
		if (NameAbbreviator.frc == null)
			NameAbbreviator.frc = ((Graphics2D)g).getFontRenderContext();
		
		//make the background grey
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);
		
		super.paint(g);
		
		Graphics oldG = g.create();	
		
		OpgPage page = parent.getSession().currentPage();
		charts = parent.getSession().getCharts();
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(xOffset, yOffset);
		g2d.scale(zoom, zoom);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);	
		
//		writer.createChart(page ,g2d, /*(int)(page.getPageWidth()),(int)page.getPageWidth()*/getWidth(), getHeight(), zoom, xOffset, yOffset, oldG, parent.session.config.showRuler, parent.getSession());
		updateZoomBox();
		//if( parent.session.config.showRuler && parent.getSession().getChart() != null) drawRuler(g);
		if( showSelectionRectangle ) drawScaledRect(g2d, mouseDownX, mouseDownY, mouseX, mouseY);
		
		/**
		 * Added By: Spencer Hoffa
		 * Added ON: 2/12/2013
		 * 
		 * This will check to see if the chart was just loaded if it was 
		 * then maximize.
		 */
		if (firstLoaded)
		{
			fitWidth();
			firstLoaded = false;
		}
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		// End Addition By: Spencer Hoffa
		////////////////////////////////////////////////////
	}
	
	private void drawRect(Graphics2D g, int x, int y, int x1, int y1){
		int minx = (x < x1) ? x : x1;
		int miny = (y < y1) ? y : y1;
		int maxx = (x > x1) ? x : x1;
		int maxy = (y > y1) ? y : y1;
		g.drawRect(minx, miny, maxx-minx, maxy - miny);		
	}
	
	private void drawScaledRect(Graphics2D g, int x, int y, int x1, int y1){
		try{
			float dashwidth = 2;
			Point p = new Point(x, y);
			Point p1 = new Point(x1, y1);
			Point2D pp = null;
			pp = g.getTransform().inverseTransform(p, pp);
			Point2D pp1 = null;
			pp1 = g.getTransform().inverseTransform(p1, pp1);
			float[] dasharray = {(float)(dashwidth / zoom)};
			g.setColor(Color.BLACK);
			g.setStroke(new BasicStroke((float)(dashwidth / zoom), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dasharray, 1.0f ));
			drawRect(g, (int) pp.getX(), (int) pp.getY(), (int) pp1.getX(), (int) pp1.getY());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
        /*
         * Added By: Spencer Hoffa
         * Added On: 11/13/2012
         * 
         * Added a zoom function that will zoom in on the 
         * center of the screen.  This is for the simplified
         * GUI functionality.
         * 
         * I used the original zoom function and modified it for 
         * the center of the screen instead of where the mouse is.
         */
        public void zoomCenter(double newZoom){
		if(parent.getSession().currentPage() == null ) return;
//		calculate the position of the mouse on the page 
                
                double centerX = this.getWidth()/2;
                double centerY = this.getHeight()/2;
                
		double xTemp = (centerX - xOffset) / zoom;
		double yTemp = (centerY - yOffset) / zoom;

		//This equation is designed to make sure that the point you click on to zoom 
		//does not move on the screen. Or in other words you mouse stays pointing on
		//a given part of the chart while the zoom changes
		xOffset = (int)centerX - (int) (xTemp*newZoom);
		yOffset = (int)centerY - (int) (yTemp*newZoom);
		//zoom = newZoom; //why have two
		zoom = newZoom;
	}
        
	public void zoom(double newZoom){
		if(parent.getSession().currentPage() == null ) return;
//		calculate the position of the mouse on the page 
                
		double xTemp = ((double)mouseX- xOffset) / zoom;
		double yTemp = ((double)mouseY - yOffset) / zoom;

		//This equation is designed to make sure that the point you click on to zoom 
		//does not move on the screen. Or in other words you mouse stays pointing on
		//a given part of the chart while the zoom changes
		xOffset = mouseX - (int) (xTemp*newZoom);
		yOffset = mouseY - (int) (yTemp*newZoom);
		//zoom = newZoom; //why have two
		zoom = newZoom;
	}
	
	public Point toChartXY(float x, float y){
		double xTemp = ((double)mouseX - xOffset) / zoom;
		double yTemp = ((double)mouseY - yOffset) / zoom;
		return new Point((int) xTemp, (int) yTemp);
	}
	
	public Point toPanelXY(float x, float y){
		double xTemp = xOffset + zoom * x;
		double yTemp = yOffset + zoom * y;
		return new Point((int) xTemp, (int) yTemp);
	}
	
	public void fitWidth(){
//		OpgPage page = parent.getSession().currentPage();
//		if(page == null) return;
//		int xmax, ymax;
//		int rheight = 72*rulerwidth*3 + 72*rulerlength;
//		int rwidth = rheight;
//		int xoff, yoff;
		//first time	
		//set scaling factor based on size of visible graphics drawing area
//		if(parent.session.config.showRuler){
//			xoff = 30;//0;//72*rulerwidth*2;
//			yoff = 30;//xoff;
//			xmax = (int) ((rheight > page.getPageWidth()) ? page.getPageWidth()+30/*rheight*/ : page.getPageWidth());
//			ymax = (int) ((rwidth > page.getPageHeight()) ? page.getPageHeight()+30/*rwidth*/ : page.getPageHeight());
//		}else{
//			xoff = yoff = 0;
//			xmax = (int) page.getPageWidth();
//			ymax = (int) page.getPageHeight();
//		}
//		setViewArea(xoff, yoff, xmax, ymax);
		
	}
	
	private void setViewArea(int xoff, int yoff, int viewX, int viewY){
		if ( ((double)viewX / (double)viewY) > ((double)width / (double)height) )
		{
			zoom = (double)width / (double)viewX;
			//set the x and y offsets
			xOffset = (int) (xoff * zoom);
			yOffset = (int) (yoff * zoom) + (height - (int)(viewY * zoom) ) / 2;
		}
		else
		{
			zoom = (double)height / (double)viewY;
			//set the x and y offsets
			yOffset = (int) (yoff * zoom);
			xOffset = (int) (xoff * zoom) + (width - (int)(viewX * zoom ) ) / 2;
		}
		repaint();
	}


	public void drawRuler(Graphics g){
		int rwidth = 72*rulerwidth;
		int rlength = 72*rulerlength;
		int roffset = 25;
		int zeroOffset = 25;
		int vtextoffset = 50;
		int htextoffset = 63;
		int i;
		Color c = new Color(217, 180,94);
		//Color c = Color.LIGHT_GRAY;
		((Graphics2D) g).setStroke(new BasicStroke(1));
		g.setColor(c);
		g.fillRect(0-zeroOffset, 0-roffset-rwidth, rlength+2*zeroOffset, rwidth );
		g.fillRect(0-roffset-rwidth, 0-zeroOffset, rwidth, rlength+2*zeroOffset);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Times New Roman",0, 22 ));
		for(i = 0;i<rulerlength;i++){
			g.drawString(String.valueOf(i), 72*i, 0-roffset-vtextoffset);
			g.drawLine(72*i, 0-roffset-40, 72*i, 0-roffset);
			g.drawLine(72*i + 9, 0-roffset-10, 72*i + 9, 0-roffset);
			g.drawLine(72*i + 18, 0-roffset-20, 72*i + 18, 0-roffset);
			g.drawLine(72*i + 27, 0-roffset-10, 72*i + 27, 0-roffset);
			g.drawLine(72*i + 36, 0-roffset-30, 72*i + 36, 0-roffset);
			g.drawLine(72*i + 45, 0-roffset-10, 72*i + 45, 0-roffset);
			g.drawLine(72*i + 54, 0-roffset-20, 72*i + 54, 0-roffset);
			g.drawLine(72*i + 63, 0-roffset-10, 72*i + 63, 0-roffset);
		}
		g.drawString(String.valueOf(i), 72*i, 0-roffset-vtextoffset);
		g.drawLine(72*i, 0-roffset-40, 72*i, 0-roffset);
		
		for(i = 0;i<rulerlength;i++){
			//if(i%12 == 0) g.drawString(String.valueOf(i/12) + " ft", 0-roffset-htextoffset, 72*i);
			g.drawString(String.valueOf(i), 0-roffset-htextoffset, 72*i);
			g.drawLine(0-roffset-40, 72*i, 0-roffset, 72*i);
			g.drawLine(0-roffset-10, 72*i + 9, 0-roffset, 72*i + 9);
			g.drawLine(0-roffset-20, 72*i + 18, 0-roffset, 72*i + 18);
			g.drawLine(0-roffset-10, 72*i + 27, 0-roffset, 72*i + 27);
			g.drawLine(0-roffset-30, 72*i + 36, 0-roffset, 72*i + 36);
			g.drawLine(0-roffset-10, 72*i + 45, 0-roffset, 72*i + 45);
			g.drawLine(0-roffset-20, 72*i + 54, 0-roffset, 72*i + 54);
			g.drawLine(0-roffset-10, 72*i + 63, 0-roffset, 72*i + 63);
		}
		g.drawString(String.valueOf(i), 0-roffset-htextoffset, 72*i);
		g.drawLine(0-roffset-40, 72*i, 0-roffset, 72*i);
		
		
		
		
		
		
		
	}
	
	public void updateZoomBox(){
//		parent.getZoomField().setText(NumberFormat.getPercentInstance().format(writer.getZoomFactor()));
		parent.getZoomField().setText(NumberFormat.getPercentInstance().format(zoom));
	}
	
	public void setZoom(double zoom){
		mouseX = (int) (parent.getSession().currentPage().getPageWidth()*.5);//(getWidth()*.5);
		mouseY = (int) (parent.getSession().currentPage().getPageWidth()*.5);//(getHeight()*.5);
		zoom(zoom);
		repaint();
	}
	
	 private class PreviewListener implements MouseListener, MouseMotionListener, MouseWheelListener
	{
		 OpgPreviewPanel parent;
		 int origOffsetx;
		 int origOffsety;
		 
		 PreviewListener(OpgPreviewPanel parent)
		 {
			 this.parent = parent;
		 }
		 
		//--------------------------------------------------------------
		//  Captures the initial position at which the mouse button is
		//  pressed.
		//--------------------------------------------------------------
		public void mousePressed (MouseEvent event)
		{
			requestFocusInWindow();

			Point point = event.getPoint();
			mouseDownX = (int)point.getX();
			mouseDownY = (int)point.getY();
			origOffsetx = xOffset;
			origOffsety = yOffset;
		}
	
		public void mouseDragged (MouseEvent event)
		{
			requestFocusInWindow();

			mouseX = event.getX();
			mouseY = event.getY();
			
			if (resizing == DRAG_TYPE.NONE)
			{
				if(parent.parent.session.cursor == OpgCursor.MOVE){
					dragging = true;
					xOffset = origOffsetx + (mouseX - mouseDownX);
					yOffset = origOffsety + (mouseY - mouseDownY);
					parent.setCursor(OpgCursor.MOVE_GRAB.getCursor());
					
				}
				else if(parent.parent.session.cursor == OpgCursor.Z_IN){
					dragging = true;
					showSelectionRectangle = true;
					
				}
				else if(parent.parent.session.cursor == OpgCursor.ARROWTEXT){
					dragging = true;
					showSelectionRectangle = true;
					
				}
	//			if(selected != null){
	//				Point p = toPanelXY(selected.x, selected.y);
	//				selected.setBounds(p.x, p.y, (int) (selected.sizex * zoom),(int) (selected.sizey * zoom));
	//			}
			}
			else
			{
				ChartOptions ops = parent.parent.session.getBaseOptions();
				OpgPage curPage = parent.parent.session.currentPage();
				boolean horizontal = ops.isLandscape();
				Point mouseOnChart = ChartConversion.convertMousePixelToChartPoint(event.getPoint(), zoom, yOffset, xOffset);
				PaperWidth newWidth = horizontal ? PaperWidth.findClosestFit(mouseOnChart.y) : PaperWidth.findClosestFit(mouseOnChart.x);
				double newLength = horizontal ? (mouseOnChart.x < curPage.getPageMinimumHeight() ? curPage.getPageMinimumHeight() : (mouseOnChart.x)) : 
					(mouseOnChart.y < curPage.getPageMinimumHeight() ? curPage.getPageMinimumHeight() : (mouseOnChart.y));

				if (resizing == DRAG_TYPE.BOTH ||
					resizing == DRAG_TYPE.HORIZONTAL)
				{
					parent.parent.updatePaperWidth(newWidth);
					
				}
				if (resizing == DRAG_TYPE.BOTH ||
						resizing == DRAG_TYPE.VERTICAL)
				{
					parent.parent.maskEvents = true;
					parent.parent.updatePaperLength(newLength);
					parent.parent.maskEvents = false;
					
				}
			}
			repaint();
		}
		
		//zoom in/out and select items when the mouse is clicked
		public void mouseClicked (MouseEvent event)
		{
			requestFocusInWindow();

//			if (dragging == true)
//				return;
			dragging = false;
			OpgCursor cur = parent.parent.session.cursor;
			Point point = event.getPoint();
			mouseX = (int)point.getX();
			mouseY = (int)point.getY();
			
			log.debug("mouse clicked at " + (((double)mouseDownX - xOffset) / zoom) + "," + (((double)mouseDownY - yOffset) / zoom));
			
					
			
			int button = event.getButton();
			if(cur == OpgCursor.Z_IN){
				zoom(zoom*1.2);
			}else
			if(cur == OpgCursor.Z_OUT){
				zoom(zoom*.8);
			}else
			if(cur == OpgCursor.MOVE){
				if (button == MouseEvent.BUTTON1)
					zoom(zoom*1.2);
				else if (button == MouseEvent.BUTTON3)
					zoom(zoom*.8);
			}else
			if(cur == OpgCursor.ARROW)
			{
				
				
				ShapeInfo box = parent.parent.session.getIndiIntersect((((double)mouseDownX - xOffset) / zoom), (((double)mouseDownY - yOffset) / zoom), 
						parent.parent.session.getOptions().getAncesGens(), parent.parent.session.getOptions().getDescGens(), parent.parent.session);

				if (box != null)
				{
					parent.parent.setComboBoxSelection(box.getIndividual());
                                        
                                        //if (parent.parent.isSimplifiedMode())
                                        {
                                            parent.parent.setSelectedIndividual(box.getIndividual());
                                            parent.parent.setMoveButton();
                                        }

					if(PresetChartOptions.class.isInstance(parent.parent.session.getOptions())){
						ChartMaker maker = parent.parent.session.getMaker();
						PresetChartOptionsPanel opsPanel = (PresetChartOptionsPanel) parent.parent.getAdvancedOptionsPanel().getComponent(0);
						opsPanel.getStyleEdit().setStyle(box.getNumber(), box.getIsAncestor(), maker.getBoxStyles());
					}

					
					
					
				}
			}
			
			repaint();
		}

		public void mouseReleased (MouseEvent event)
		{
			requestFocusInWindow();

			if(dragging && parent.parent.session.cursor == OpgCursor.Z_IN) {
				showSelectionRectangle = false;
				int minzoombox = (int) (10/zoom);
				int x = (int) (((double)mouseDownX - xOffset) / zoom);
				int y = (int) (((double)mouseDownY - yOffset) / zoom);
				int x1 = (int) (((double)mouseX - xOffset) / zoom);
				int y1 = (int) (((double)mouseY - yOffset) / zoom);
				int minx = (x < x1) ? x : x1;
				int miny = (y < y1) ? y : y1;
				int maxx = (x > x1) ? x : x1;
				int maxy = (y > y1) ? y : y1;
				if(maxx-minx > minzoombox && maxy-miny > minzoombox){
					setViewArea(-minx, -miny, maxx - minx, maxy - miny);
				}
			}else if(dragging && parent.parent.session.cursor == OpgCursor.ARROWTEXT) {
					showSelectionRectangle = false;
					parent.parent.session.cursor = OpgCursor.ARROW;
			}
			dragging = false;
			parent.setCursor(parent.parent.session.cursor.getCursor());
//			if(selected != null){
//				Point p = toPanelXY(selected.x, selected.y);
//				selected.setBounds(p.x, p.y, (int) (selected.sizex * zoom),(int) (selected.sizey * zoom));
//			}
			repaint();
		}
		public void mouseEntered (MouseEvent event) {
			parent.setCursor(parent.parent.session.cursor.getCursor());
		}
		public void mouseExited (MouseEvent event) {
			parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		public void mouseMoved (MouseEvent event) 
		{
			ChartOptions ops = parent.parent.session.currentPage().getFirstOptions();
			double width = ops.isLandscape() ? parent.parent.session.currentPage().getPageHeight() : ops.getPaperWidth().width;
			double height = ops.isLandscape() ? ops.getPaperWidth().width : parent.parent.session.currentPage().getPageHeight();
			double mouseError = (2 / zoom);
			if(charts == null || charts.size() == 0 || charts.get(0) == null) 
			{
				parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				return;
			}
		
			Point mouseToPaper = ChartConversion.convertMousePixelToChartPoint(event.getPoint(), zoom, yOffset, xOffset);
			// If the mouse goes over the edge of the paper select
			resizing = DRAG_TYPE.NONE;
			if (Math.abs(mouseToPaper.x - width) < mouseError &&
					Math.abs(mouseToPaper.y - height) < mouseError)
			{
				if(ops.getPaperHeightChoice() && ops.getPaperWidthChoice()){
					parent.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
					resizing = DRAG_TYPE.BOTH;
				}				
			}
			else if (Math.abs(mouseToPaper.x - width) < mouseError &&
					mouseToPaper.y <= height + mouseError &&
					mouseToPaper.y >= 0 - mouseError)
			{
				if(ops.getPaperWidthChoice()){
					parent.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
					resizing = ops.isLandscape() ? DRAG_TYPE.VERTICAL : DRAG_TYPE.HORIZONTAL;
				}
			}
			else if (Math.abs(mouseToPaper.y - height) < mouseError && 
					mouseToPaper.x <= width + mouseError &&
					mouseToPaper.x >= 0 - mouseError)
			{
				if(ops.getPaperHeightChoice()){
					parent.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
					resizing = ops.isLandscape() ? DRAG_TYPE.HORIZONTAL : DRAG_TYPE.VERTICAL;
				}
			}
			else
			{
				parent.setCursor(parent.parent.session.cursor.getCursor());
				resizing = DRAG_TYPE.NONE;
			}
		}
		
		public void mouseWheelMoved(MouseWheelEvent event) {
			int notches = event.getWheelRotation();
			OpgCursor cur = parent.parent.session.cursor;
			Point point = event.getPoint();
			mouseX = (int)point.getX();
			mouseY = (int)point.getY();
			if(cur == OpgCursor.MOVE){
				zoom(zoom * ( 1 + -1 * notches * .1));
				repaint();
			}			
		}
	}
	
	 private class keylistener implements KeyListener{
		 
		public void keyPressed(KeyEvent arg0) {

		}

		public void keyReleased(KeyEvent arg0) 
		{
		}

		public void keyTyped(KeyEvent arg0) {}
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(introButtonText))
			parent.importGedcom();
		else if (e.getActionCommand().equals(openPafText))
		{
			parent.openPaf();
		}
		else if (e.getActionCommand().equals(openOpgText))
			parent.open();
		else if (e.getActionCommand().equals(NFSButtonText))
			parent.getFamilySearch();

	}
	
	public void setIntroVisible(boolean newVal) {
		introPanel.setVisible(newVal);
	}
	
	/**
	 * @return true if the OS is Mac OS X
	 */
	private boolean isMac() {
		return System.getProperty("mrj.version") != null;
	}

	/**
	 * Added By: Spencer Hoffa
	 * Added On: 2/12/2013
	 * 
	 * Added two new functions.  One to load from a file
	 * and another to load from new family search. Since 
	 * we are only doing multisheet this seems to be the
	 * appropriate way to do it.
	 * 
	 * Also added a function to make this chart maximize 
	 * when first loaded.
	 */
	private void openFromFile()
	{
		parent.openSupportedFileTypes();
		parent.getSession().changeType(ChartType.MULTISHEET);
		parent.setSimplifiedGUI(true);
	}
	
	private void openFromNewFamilySearch()
	{
		parent.getFamilySearch();
		parent.getSession().changeType(ChartType.MULTISHEET);
		parent.setSimplifiedGUI(true);
	}
	
	private boolean firstLoaded = true;
	
	/**
	 * This method tells this class that the chart was
	 * just loaded.
	 */
	public void firstLoaded()
	{
		firstLoaded = true;
	}
	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	// 		End addition by: Spencer HOffa
	/////////////////////////////////////////////////////////////////
	
	
	
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	//
	//			Everything below here is obsolete
	//			I'm keeping it just in case we want
	//			to allow other types of charts.
	//
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////
	
        /**
         * Added By Spencer Hoffa
         * Added on: 10/31/2012
         * Last Edited: 12/29/2012
         *                  -Removed call to OpenChooseLoadType
         *                  -added a direct call to create a modal dialog box
         */
		/*
        public void openMultipage()
        {
            //openChooseLoadType();
            ChooseLoadType newLoadType = new ChooseLoadType(parent.getCurrentWindow());
            //parent.openSupportedFileTypes();
            
            parent.getSession().changeType(ChartType.MULTISHEET);
            //parent.setOptionsVisibility(false);
            parent.setSimplifiedGUI(true);
        }
        //*/
        /**
         * Added By: Spencer Hoffa
         * Added On: 12/8/2012
         * 
         * This method allows to choose between opening from a file
         * or opening from New Family Search
         * 
         * Last Edited: Spencer Hoffa 12/29/2012
         *                  -We no longer need this section so I removed it.
         */
        /*private JFrame chooseLoadType = null;
        private JButton cancelChooseLoadType = null;
        private JButton chooseLoadTypeFile = null;
        private JButton chooseLoadTypeFamilySearch = null;
        private JPanel chooseLoadTypePanel = null;*/
        
        /*public void disposeChooseLoadType()
        {
            chooseLoadType.dispose();
            cancelChooseLoadType = null;
            chooseLoadTypeFile = null;
            chooseLoadTypeFamilySearch = null;
            chooseLoadTypePanel = null;
        }*/
        
        /*
         * Added and edited by: Spencer Hoffa
         * Last Edit: Spencer Hoffa (12/29/2012)
         *              -Changed to private
         *              -Removed it completely because we no longer need it
         */
        //*
        private void openChooseLoadType()
        {
            ChooseLoadType newLoadType = new ChooseLoadType(parent.getCurrentWindow());
            
            /*
            // Setup cancel button
            cancelChooseLoadType = new JButton("Cancel");
            cancelChooseLoadType.addActionListener(
                    new ActionListener()
                    {

                        @Override
                        public void actionPerformed(ActionEvent e) 
                        {
                            disposeChooseLoadType();
                        }
                    }
                    );
            
            //setup Load from file button
            chooseLoadTypeFile = new JButton("<HTML><CENTER><H2>Load From File</H2></CENTER></HTML>");
            //chooseLoadTypeFile.setSize(new Dimension(200,200));
            chooseLoadTypeFile.addActionListener(
                    new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            chooseLoadType.setVisible(false);
                            parent.openSupportedFileTypes();
                            disposeChooseLoadType();
                        }       
                    }
                    );
            
            //setup load from New Family Search
            chooseLoadTypeFamilySearch = new JButton("<HTML><CENTER><H2>Load From New<BR>Family Search</H2></CENTER></HTML");
            //chooseLoadTypeFamilySearch.setSize(new Dimension(200,200));
            chooseLoadTypeFamilySearch.addActionListener(
                    new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            chooseLoadType.setVisible(false);
                            parent.getFamilySearch();
                            disposeChooseLoadType();
                        }       
                    }
                    );
            
            //Setup the panel
            chooseLoadTypePanel = new JPanel();
            chooseLoadTypePanel.setLayout(new BoxLayout(chooseLoadTypePanel, BoxLayout.Y_AXIS));
            
            JPanel loadTypeTop = new JPanel();
            loadTypeTop.setLayout(new GridLayout(0,2));
            //loadTypeTop.setSize(400, 200);
            
            JPanel loadTypeBottom = new JPanel();
            loadTypeBottom.setLayout(new BoxLayout(loadTypeBottom, BoxLayout.X_AXIS));
            
            loadTypeTop.add(chooseLoadTypeFile);
            loadTypeTop.add(chooseLoadTypeFamilySearch);
            chooseLoadTypePanel.add(loadTypeTop);
            loadTypeBottom.add(Box.createHorizontalGlue());
            loadTypeBottom.add(cancelChooseLoadType);
            loadTypeBottom.add(Box.createRigidArea(new Dimension(10, 0)));
            chooseLoadTypePanel.add(loadTypeBottom);
            
            chooseLoadType = new JFrame();
            chooseLoadType.setMinimumSize(new Dimension(400, 250));
            
            //Center this frame onto the screen
            Point position = OpgPreviewPanel.this.getLocationOnScreen();
            position.x += (OpgPreviewPanel.this.getWidth() / 2) - (chooseLoadType.getWidth()/2);
            position.y += (OpgPreviewPanel.this.getHeight() / 2) - (chooseLoadType.getHeight()/2);
            
            chooseLoadType.setLocation(position);
            chooseLoadType.add(chooseLoadTypePanel);
            chooseLoadType.setVisible(true);
            //*/
        }
        //*/
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        // End Addition by Spencer Hoffa
        ////////////////////////////////////////////////////////////////
        
        /**
         * Added By: Spencer Hoffa
         * Added on: 10/31/2012
         */
	/*
        private void openAdvanced()
        {
            //openChooseLoadType();
            ChooseLoadType newLoadType = new ChooseLoadType(parent.getCurrentWindow());
            
            //parent.openSupportedFileTypes();
            
            //parent.getSession().changeType(ChartType.MULTISHEET);
            //parent.setOptionsVisibility(true);
            parent.setSimplifiedGUI(false);
        }
    //*/
        
        /**
         * Added By: Spencer Hoffa
         * Added On: 12/29/2012
         * 
         * This class is added to facilitate the opening
         * of a Choose load type dialog.
         * 
         * Last Edited: 12/29/2012
         *                  -Removed call to OpenChooseLoadType
         *                  -added a direct call to create a modal dialog box
         */
	//*
        private class ChooseLoadType extends JDialog
        {
            
            private JButton cancelChooseLoadType = null;
            private JButton chooseLoadTypeFile = null;
            private JButton chooseLoadTypeFamilySearch = null;
            private JPanel chooseLoadTypePanel = null;
            
            ChooseLoadType(Frame parent)
            {
                super (parent, "Choose Pedigree Source");
                
                //--------- Set up Frame -----------------
                cancelChooseLoadType = new JButton("Cancel");
                cancelChooseLoadType.addActionListener(
                        new ActionListener()
                        {

                            @Override
                            public void actionPerformed(ActionEvent e) 
                            {
                                ChooseLoadType.this.dispose();
                            }
                        }
                        );

                //setup Load from file button
                chooseLoadTypeFile = new JButton("<HTML><CENTER><H2>Load From File</H2></CENTER></HTML>");
                //chooseLoadTypeFile.setSize(new Dimension(200,200));
                chooseLoadTypeFile.addActionListener(
                        new ActionListener()
                        {
                            @Override
                            public void actionPerformed(ActionEvent e)
                            {
                                //chooseLoadType.setVisible(false);
                                //ChooseLoadType.this.setVisible(false);
                                //OpgPreviewPanel.this.parent.openSupportedFileTypes();
                                ChooseLoadType.this.dispose();
                            	openFromFile();
                            }       
                        }
                        );

                //setup load from New Family Search
                chooseLoadTypeFamilySearch = new JButton("<HTML><CENTER><H2>Load From New<BR>Family Search</H2></CENTER></HTML");
                //chooseLoadTypeFamilySearch.setSize(new Dimension(200,200));
                chooseLoadTypeFamilySearch.addActionListener(
                        new ActionListener()
                        {
                            @Override
                            public void actionPerformed(ActionEvent e)
                            {
                                //chooseLoadType.setVisible(false);
                                //ChooseLoadType.this.setVisible(false);
                                //OpgPreviewPanel.this.parent.getFamilySearch();
                                ChooseLoadType.this.dispose();
                            	openFromNewFamilySearch();
                                
                            }       
                        }
                        );

                //Setup the panel
                chooseLoadTypePanel = new JPanel();
                chooseLoadTypePanel.setLayout(new BoxLayout(chooseLoadTypePanel, BoxLayout.Y_AXIS));

                JPanel loadTypeTop = new JPanel();
                loadTypeTop.setLayout(new GridLayout(0,2));
                //loadTypeTop.setSize(400, 200);

                JPanel loadTypeBottom = new JPanel();
                loadTypeBottom.setLayout(new BoxLayout(loadTypeBottom, BoxLayout.X_AXIS));

                loadTypeTop.add(chooseLoadTypeFile);
                loadTypeTop.add(chooseLoadTypeFamilySearch);
                chooseLoadTypePanel.add(loadTypeTop);
                loadTypeBottom.add(Box.createHorizontalGlue());
                loadTypeBottom.add(cancelChooseLoadType);
                loadTypeBottom.add(Box.createRigidArea(new Dimension(10, 0)));
                chooseLoadTypePanel.add(loadTypeBottom);

                JPanel chooseLoadType = new JPanel();
                chooseLoadType.setMinimumSize(new Dimension(400, 250));

                //Center this frame onto the screen
                Point position = OpgPreviewPanel.this.getLocationOnScreen();
                position.x += (OpgPreviewPanel.this.getWidth() / 2) - (chooseLoadType.getWidth()/2);
                position.y += (OpgPreviewPanel.this.getHeight() / 2) - (chooseLoadType.getHeight()/2);

                chooseLoadType.setLocation(position);
                chooseLoadType.add(chooseLoadTypePanel);
                
                //addthe choose LoadType panel to the 
                //dialog box
                getContentPane().add(chooseLoadType);
                
                //chooseLoadType.setVisible(true);
                //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                //      Done Setting up Frame
                ///////////////////////////////////////////
                
                
                
                
                setModal(true);
                pack();
                setLocationRelativeTo(parent);
                
                ChooseLoadType.this.addWindowListener(
                        new java.awt.event.WindowAdapter()
                        {
                            public void windowClosing(WindowEvent e) 
                            {
                                //currChooseRootDialog = null;
                                ChooseLoadType.this.dispose();
                            }
                        }
                        );
                
                setVisible(true);
            }
        }
    //*/
	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        // End Addition by Spencer Hoffa
        ////////////////////////////////////////////////////////////////
}
