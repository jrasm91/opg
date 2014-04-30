package opg.main;

import java.io.File;

import opg.gui.DrawableChart;
import opg.gui.OPGGUI;
import opg.other.Chart;
import opg.other.Utility;

//import org.apache.fop.svg.PDFDocumentGraphics2D;

public class Controller {

	private static Controller singleton = new Controller();

	public static Controller singleton(){
		return singleton;
	}

	private Chart chart;
	private DrawableChart dChart;

	private Controller(){
		chart = new Chart();
		dChart = new DrawableChart(chart);
	}

	// GETTERS
	public Chart getChart(){ return chart; }
	public DrawableChart getDrawableChart() { return dChart; }

	//SPLASH SCREEN CLICKS
	public void splashCloseClick(){ OPGGUI.singleton().setVisible(true);}
	public void splashDonate(){ OPGGUI.singleton().setVisible(true); Utility.openURL(Const.URL_DONATE); }

	// BUTTON CLICKS
	public void zoomInClick(){ View.singleton().zoomIn(); }
	public void zoomOutClick(){ View.singleton().zoomOut(); }
	public void rootSelectClick(){
		//TODO update root person 
	}
	public void openFileClick(File file){ chart = Utility.parse(file); }

	public void handClick(){ System.out.println("handClick"); }
	public void arrowClick(){ System.out.println("arrowClick"); }
	public void fitToWidthClick(){ System.out.println("fitToPageClick"); }
	public void nextClick(){ System.out.println("nextClick"); }
	public void previousClick(){ System.out.println("previousClick"); }

	// MENU CLICKS
	public void mainQuit(){} //TODO saveState
	public void saveProject(){};
	public void saveAsProject(){};
	public void saveChart(){};
	public void saveAsChart(){};
	public void print(){};
	public void exit(){};

	public void showRuler(boolean isShown){ View.singleton().setRuler(isShown); } 
	public void showGrid(boolean isShown){ View.singleton().setGrid(isShown); } 

	public void showAdvancedOptions(boolean isShown){} 

	public void saveAsPDFClick(){
//		PDFGraphics2D graphicsPDF = new PDFGraphics2D(0, 0, chart.getWidth(), chart.getHeight());
//		BufferedImage image = new BufferedImage(chart.getWidth(), chart.getHeight(), BufferedImage.TYPE_INT_RGB);
//		Graphics2D g = image.createGraphics();
//		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		g.setBackground(Color.WHITE);
//		g.clearRect(0, 0, chart.getWidth(), chart.getHeight());
//		
//		List<BufferedImage> images = new ArrayList<BufferedImage>();
//		images.add(image);
//		
//		Utility.createPDF(images, "sideways.pdf");
//		Utility.writeBytes(graphicsPDF.getBytes(), "test.pdf");
	}

}
