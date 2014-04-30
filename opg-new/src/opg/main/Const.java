package opg.main;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

public class Const {

	// strings
	public static final String VERSION_ID = "Version 3.489";
	public static final String URL_HELP = "http://roots.cs.byu.edu/pedigree/help/beta";
	public static final String URL_DONATE = "https://secure3.convio.net/ldsp/site/Donation2?idb=51449929&df_id=5301&5301.donation=form1";
	public static final String TITLE_CHOOSE_ROOT = "Choose Root Dialog";

	// paths
	public static final String PATH_IMG = "img" + File.separator;
	public static final String PATH_IMG_SPLASH = PATH_IMG + "splash.jpg";
	public static final String PATH_IMG_ARROW = PATH_IMG + "arrow.png";
	public static final String PATH_IMG_HAND = PATH_IMG + "hand.png";
	public static final String PATH_IMG_ZOOMIN = PATH_IMG + "icon_zoom_in.png";
	public static final String PATH_IMG_ZOOMOUT = PATH_IMG + "icon_zoom_out.png";
	public static final String PATH_IMG_FTWIN = PATH_IMG + "ftwin.png";
	public static final String PATH_IMG_NEXT = PATH_IMG + "right_arrow.jpg";
	public static final String PATH_IMG_PREVIOUS = PATH_IMG + "left_arrow.jpg";
	public static final String PATH_IMG_OPEN = PATH_IMG + "file_open.jpg";

	// colors
	public static final Color COLOR_PAGE_OUTLINE = Color.BLACK;
	public static final Color COLOR_PAGE_INLINE = Color.BLUE;
	public static final Color COLOR_PAGE_BACKGROUND = Color.WHITE;
	public static final Color COLOR_RULER = Color.BLACK;
	public static final Color COLOR_GRID = Color.BLACK;
	
//	public static final Dimension BOX_SIZE = new Dimension(150, 5);
	public static final int MINIMUM_BOX_HEIGHT = 50;
	public static final int MINUMUM_BOX_WIDTH = MINIMUM_BOX_HEIGHT * 3;
	public static final float DEFAULT_FONT_SIZE = 8.0f;
	
	// errors
	public static final String ERR_NO_FILE_EXT = "File Must Have Extension";
	public static final String ERR_BAD_EXT = "Cannot open file extension: ";

	// sizes
	public static final int SPLASH_PARTIAL_HEIGHT = (int) (480 * 0.92f);
	public static final int DEFUALT_PAGE_WIDTH = 612;
	public static final int DEFAULT_PAGE_HEIGHT = (int) (DEFUALT_PAGE_WIDTH * 11 / 8.5);

	// margins/spacing
	public static final int MARGIN_PAGE = 32;
	public static final int MARGIN_DRAWING = 100;
	public static final int MARGIN_BOX_SPACING = 10;
	
	public static final float STROKE_SIZE = 2;
	public static final int RULER_INTERVAL = 9;
	public static final int RULER_MIN_INTERVAL = RULER_INTERVAL * 1;
	public static final int RULER_MAX_INTERVAL = RULER_INTERVAL * 8;
	public static final int RULER_MAX_TICK_LENGTH = 16;
	public static final int RULER_OFFSET = RULER_MAX_TICK_LENGTH * 2;

	public static final Dimension SPLASH_DIM = new Dimension(640, 480);
	public static final Dimension DEFAULT_PAGE_SIZE = new Dimension(DEFUALT_PAGE_WIDTH, DEFAULT_PAGE_HEIGHT);
	public static final Dimension DRAWING_SIZE = new Dimension(DEFUALT_PAGE_WIDTH + MARGIN_DRAWING * 2, DEFAULT_PAGE_HEIGHT + MARGIN_DRAWING * 2);
	public static final Dimension CHOOSE_ROOT_SIZE = new Dimension(100, 100);
	public static final Dimension ICON_SIZE = new Dimension(20, 20);

	
	// zoom ranges
	public static final float ZOOM_IN_MAX = 8;
	public static final float ZOOM_OUT_MAX = .05f;

	public static final int MAX_GENERATIONS = 5;
	
	static {
		//	1 Point = 1/72 of an inch?
		System.out.println(DEFUALT_PAGE_WIDTH + " points is + " + DEFUALT_PAGE_WIDTH/72.0 + " inches");
		System.out.println(DEFAULT_PAGE_HEIGHT + " points is + " + DEFAULT_PAGE_HEIGHT/72.0 + " inches");
	}
}
