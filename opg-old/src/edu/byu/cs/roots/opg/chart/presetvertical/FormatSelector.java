package edu.byu.cs.roots.opg.chart.presetvertical;

import java.util.TreeMap;

/**
 * TreeFormatSelector is a singleton class used to select the a TreeFormat given
 * the amount of generations to display.
 *
 */
public class FormatSelector {
	
	//single instance
	private static FormatSelector instance;
	
	private TreeMap<Integer ,BoxFormat> boxFormats;
	
	/**
	 * Get instance of TreeFormatSelector
	 */
	public static FormatSelector instance() {
		if(instance == null)
			instance = new FormatSelector();
		return instance;
	}
	
	/**
	 * Initialize the TreeFormats
	 */
	private FormatSelector() {
		createBoxFormats();
	}
	
	/**
	 * Create  box formats
	 * index key:
	 * 1st (1 or 2) numbers: the amount of generations in the tree 
	 *    * there could be earlier boxes used besides this one in the tree
	 * last 2 numbers: The generation in the tree that the box is being used to draw. If
	 *                 it is used for the rest (or all) the boxes in the tree, then the
	 *                 number will be the last box in the tree. (eg. for 3 gen box, there is
	 *                 only one box used for all of them so the box is called 303.)
	 */
	private void createBoxFormats() {
		boxFormats = new TreeMap<Integer, BoxFormat>();
		
                                          //index,mheight,width,nfontsize,bfontsize,vspace,linenums,lineWidth,rndCrners
		boxFormats.put(303,   new BoxFormat( 303 ,  150  , 422 ,   31    ,   25    ,  9   ,  5     ,  2.18   , 12.6));
		
		boxFormats.put(404,   new BoxFormat( 404 ,  92   , 293 ,   19    ,   15    ,  9   ,  5     ,  1.67   , 10.8));
		
		boxFormats.put(501,  new BoxFormat( 501  ,  82   , 280 ,   18    ,   13    ,  9   ,  5     ,  1.60   , 10.4));
		boxFormats.put(502,  new BoxFormat( 502  ,  77   , 263 ,   17    ,   12    ,  9   ,  5     ,  1.54   , 10.0));
		boxFormats.put(503,  new BoxFormat( 503  ,  59   , 243 ,   13    ,   9     ,  9   ,  5     ,  1.10   ,  9.5));
		
		boxFormats.put(601,  new BoxFormat( 601  ,  77   , 263 ,   17    ,   12    ,  9   ,  5     ,  1.50   , 10.0));
		boxFormats.put(602,  new BoxFormat( 602  ,  59   , 243 ,   13    ,   9     ,  9   ,  5     ,  1.10   ,  9.5));
		boxFormats.put(603,  new BoxFormat( 603  ,  30   , 205 ,   9     ,   7     ,  9   ,  3     ,  0.84   ,  7.9));
		boxFormats.put(604,  new BoxFormat( 604  ,  23   , 156 ,   7     ,   5     ,  4   ,  3     ,  0.70   ,  4.2));
	  	
		boxFormats.put(701,  new BoxFormat( 701  ,  152  , 585 ,   31    ,   25    ,  9   ,  5     ,  2.18   , 12.6));
		boxFormats.put(702,  new BoxFormat( 702  ,  122  , 460 ,   26    ,   20    ,  2   ,  5     ,  1.91   , 11.7));
		boxFormats.put(703,  new BoxFormat( 703  ,  92   , 330 ,   20    ,   15    ,  2   ,  5     ,  1.70   , 11.0));
		boxFormats.put(704,  new BoxFormat( 704  ,  85   , 290 ,   18    ,   14    ,  2   ,  5     ,  1.60   , 10.4));
		boxFormats.put(705,  new BoxFormat( 705  ,  50   , 245 ,   16    ,   12    ,  2   ,  3     ,  1.48   ,  9.9));
		boxFormats.put(706,  new BoxFormat( 706  ,  42	 , 215 ,   13    ,   10    ,  2   ,  3     ,  1.13   ,  9.6));
		boxFormats.put(707,  new BoxFormat( 707  ,  27   , 200 ,   12    ,    9    ,  2   ,  2     ,  1.10   ,  9.0));
		boxFormats.put(708,  new BoxFormat( 708  ,  15   , 185 ,   11    ,    9    ,  2   ,  1     ,  1.10   ,  8.6));
		
		boxFormats.put(901,  new BoxFormat( 901  ,  145  , 570 ,   30    ,   25    ,  13  ,  5     ,  2.20   , 12.5));
		boxFormats.put(902,  new BoxFormat( 902  ,  112  , 400 ,   23    ,   18    ,  12  ,  5     ,  2.00   , 11.5));
		boxFormats.put(903,  new BoxFormat( 903  ,  87   , 290 ,   18    ,   14    ,  11  ,  5     ,  1.60   , 10.4));
		boxFormats.put(904,  new BoxFormat( 904  ,  50   , 245 ,   16    ,   12    ,  10  ,  3     ,  1.40   ,  9.5));
		boxFormats.put(905,  new BoxFormat( 905  ,  45   , 230 ,   14    ,   11    ,  9   ,  3     ,  1.20   ,  9.6));
		boxFormats.put(906,  new BoxFormat( 906  ,  27   , 200 ,   12    ,    9    ,  8   ,  2     ,  1.20   ,  8.0));
		boxFormats.put(907,  new BoxFormat( 907  ,  23   , 175 ,   10    ,    8    ,  7   ,  2     ,  0.90   ,  7.4));
		boxFormats.put(908,  new BoxFormat( 908  ,  12   , 165 ,    9    ,    8    ,  6   ,  1     ,  0.85   ,  6.4));
		boxFormats.put(909,  new BoxFormat( 909  ,  11   , 155 ,    8    ,    7    ,  5   ,  1     ,  0.80   ,  5.1));
		boxFormats.put(900,  new BoxFormat( 900  ,  10   , 145 ,    7    ,    6    ,  4   ,  1     ,  0.70   ,  4.5));
		
		boxFormats.put(1010, new BoxFormat( 1010 ,  7    , 120 ,    5    ,    5    , 1    ,  1     ,  0.40   ,  3.2));
		
		boxFormats.put(1401, new BoxFormat( 1401 , 171   , 460 ,   33    ,   30    ,  4   ,  5     ,  1.50   , 10.6));
		boxFormats.put(1402, new BoxFormat( 1402 , 142   , 390 ,   28    ,   24    ,  4   ,  5     ,  1.25   , 10.6));
		boxFormats.put(1403, new BoxFormat( 1403 , 115   , 340 ,   23    ,   20    ,  4   ,  5     ,  1.25   , 10.6));
		boxFormats.put(1404, new BoxFormat( 1404 ,  92   , 300 ,   18    ,   15    ,  4   ,  5     ,  1.25   , 10.6));
		boxFormats.put(1405, new BoxFormat( 1405 ,  46   , 270 ,   15    ,   11    ,  4   ,  3     ,  1.25   , 10.6));
		boxFormats.put(1406, new BoxFormat( 1406 ,  25   , 230 ,   11    ,    9    ,  4   ,  2     ,  1.25   , 10.6));
		boxFormats.put(1407, new BoxFormat( 1407 ,  11   , 190 ,    7    ,    7    ,  4   ,  1     ,  1.25   , 10.6));
		boxFormats.put(1414, new BoxFormat( 1414 ,   7   , 114 ,    5    ,    5    ,  4   ,  1     ,  0.40   ,  2.5));
		
		
		boxFormats.put(1701, new BoxFormat( 1701 , 158   , 640 ,   48    ,   43    ,  20  ,  32    ,  1.86   , 13.8));
		boxFormats.put(1702, new BoxFormat( 1702 , 171   , 400 ,   33    ,   30    ,  10  ,  5     ,  1.50   , 10.6));
		boxFormats.put(1703, new BoxFormat( 1703 , 110   , 300 ,   22    ,   18    ,  8   ,  5     ,  1.35   ,  9.8));
		boxFormats.put(1704, new BoxFormat( 1704 ,  73   , 213 ,   16    ,   11    ,  6   ,  5     ,  1.10   ,  8.2));
		boxFormats.put(1705, new BoxFormat( 1705 ,  39   , 170 ,   12    ,    9    ,  5   ,  3     ,  0.98   ,    6));
		boxFormats.put(1706, new BoxFormat( 1706 ,  31   , 140 ,    9    ,    7    ,  4   ,  3     ,  0.85   ,    5));
		boxFormats.put(1707, new BoxFormat( 1707 ,  20   , 120 ,    8    ,    7    ,  3   ,  2     ,  0.69   ,  3.6));
		boxFormats.put(1708, new BoxFormat( 1708 ,  11   , 120 ,    8    ,    7    ,  2   ,  1     ,  0.69   ,  3.6));
		boxFormats.put(1717, new BoxFormat( 1717 ,  7    , 110 ,    5    ,    5    ,  1   ,  1     ,  0.40   ,  2.5));

		boxFormats.put(1718, new BoxFormat( 1718 ,  7    , 107 ,    5    ,    5    ,  10  ,  1     ,  0.40   ,  2.5));

		boxFormats.put(2119, new BoxFormat( 2119 ,  7    , 111 ,    5    ,    5    ,  5   ,  1     ,  0.40   ,  2.5));
		boxFormats.put(2120, new BoxFormat( 2120 ,  7    , 109 ,    5    ,    5    ,  1   ,  1     ,  0.40   ,  2.5));
		
		
		boxFormats.put(2101, new BoxFormat( 2101 , 170   , 640 ,   60    ,   43    ,  20  ,  32    ,  2.06   , 26.8));
		boxFormats.put(2102, new BoxFormat( 2102 , 193   , 460 ,   39    ,   34    ,  10  ,  5     ,  1.50   , 10.6));
		boxFormats.put(2103, new BoxFormat( 2103 , 110   , 300 ,   22    ,   18    ,  8   ,  5     ,  1.50   , 10.6));
		boxFormats.put(2104, new BoxFormat( 2104 ,  48   , 213 ,   16    ,   11    ,  6   ,  3     ,  1.10   ,  8.2));
		boxFormats.put(2105, new BoxFormat( 2105 ,  38   , 155 ,   12    ,    9    ,  5   ,  3     ,  0.98   ,    6));
		boxFormats.put(2106, new BoxFormat( 2106 ,  20   , 135 ,    9    ,    7    ,  3   ,  2     ,  0.85   ,    5));
		boxFormats.put(2107, new BoxFormat( 2107 ,  11   , 120 ,    8    ,    7    ,  2   ,  1     ,  0.69   ,  3.6));
		boxFormats.put(2121, new BoxFormat( 2121 ,  7    , 107 ,    5    ,    5    ,  1   ,  1     ,  0.40   ,  2.5));
	}
	
	/**
	 * After our meeting with Michael(The Graphic Designer), this is what we determined we need to do
	 *  to make the chart more visually appealing:
	 *  1. DONE make the box lines get smaller with the generations (so they don't dominate)
	 *  2. make the colors not be the whole box, but rather be the boarderlines
	 *  3. have the font size flow better from the first generation to the last generation
	 *  4. have the smallest font size be 6 (maybe 5, it depends)
	 *  5. make the information be 70% black, and the names be 100% black
	 *  6. change the name fonts to be different than the info fonts (SEE EMAIL)
	 *  7. make the space boarders be the same below, above and to the left
	 *  8. DONE put more space between the name and the info (right now the same as the space between all the info)
	 *  9. fix the spacing for those that have the year dates on the same line
	 * 10. DONE push the one liner year dates so they are right fixated
	 * 11. 
	 */
	
	/**
	 * Returns a default format for the trees
	 * Moved over from BoxFormat!
	 */
	public BoxFormat getDefault() {
		return boxFormats.get(13);
	}
	
	/** 
	 * Creates arbitrary size tree format given indexes to box formats
	 * (THIS IS THE DEFAULT, JUST IN CASE THEY WANT MORE THAN 21 GENERATIONS
	 *  IN THEIR TREE. IT WILL GO TO THIS!)
	 */
	public TreeFormat createTreeFormat(int ...fIndexs ) {
		TreeFormat t = new TreeFormat();
		for(int f : fIndexs)
			t.add(boxFormats.get(f), regGenWidth(f));
		return t;
			
	}
	
	/**
	 * Select tree formats based on the given amount of generations.
	 */
	public TreeFormat select(int genNum) {
		switch (genNum)
		{
		case(3):
			return(threeGenTree());
		case(4):
			return(fourGenTree());
		case(5):
			return(fiveGenTree());
		case(6):
			return(sixGenTree());
		case(7):
			return(sevenGenTree());
		case(8):
			return(eightGenTree());
		case(9):
			return(nineGenTree());
		case(10):
			return(tenGenTree());
		case(11):
			return(elevenGenTree());
		case(12):
			return(twelveGenTree());
		case(13):
			return(thirteenGenTree());
		case(14):
			return(fourteenGenTree());
		case(15):
			return(fifteenGenTree());
		case(16):
			return(sixteenGenTree());
		case(17):
			return(seventeenGenTree());
		case(18):
			return(eighteenGenTree());
		case(19):
			return(nineteenGenTree());
		case(20):
			return(twentyGenTree());
		case(21):
			return(twentyOneGenTree());
		default:
			return(twentyOneGenTree());
		}		
	}
	
	/*
	 * The next 19 methods are called by select() (the method above)
	 * It will create the TreeFormat, and then select the boxFormats
	 * for each generation. (aka, for a four generation tree, we would
	 * give the TreeFormat 4 boxFormats that it would work with).
	 * These will be included in the TreeFormat. This also returns the
	 * intrusion for each generation.
	 */
	
	private TreeFormat threeGenTree() {
		TreeFormat t3 = new TreeFormat();
		t3.add(boxFormats.get(303), regGenWidth(303)/2);
		for (int i = 0; i < 3; i++)
			t3.add(boxFormats.get(303), regGenWidth(303));
		return t3;
	}

	private TreeFormat fourGenTree() {
		TreeFormat t4 = new TreeFormat();
		for (int i = 0; i <=4; i++)
			t4.add(boxFormats.get(404), regGenWidth(404));
		return t4;
	}

	private TreeFormat fiveGenTree() {
		TreeFormat t5 = new TreeFormat();
		t5.add(boxFormats.get(501), 245.0);
		t5.add(boxFormats.get(501), 245.0);
		t5.add(boxFormats.get(502), regGenWidth(502));
		t5.add(boxFormats.get(502), regGenWidth(502));
		t5.add(boxFormats.get(503), regGenWidth(503));
		t5.add(boxFormats.get(503), regGenWidth(503));
		return t5;
	}	

	private TreeFormat sixGenTree() {
		TreeFormat t6 = new TreeFormat();
		t6.add(boxFormats.get(601), 245.0);
		t6.add(boxFormats.get(601), 245.0);
		t6.add(boxFormats.get(602), 215.0);
		t6.add(boxFormats.get(602), regGenWidth(602));
		t6.add(boxFormats.get(603), regGenWidth(603));
		t6.add(boxFormats.get(603), regGenWidth(603));
		t6.add(boxFormats.get(604), regGenWidth(604));
		return t6;
	}

	private TreeFormat sevenGenTree() {
		TreeFormat t7 = new TreeFormat();
		t7.add(boxFormats.get(701), 2*regGenWidth(701)/3);
		t7.add(boxFormats.get(702), 2*regGenWidth(702)/3);
		t7.add(boxFormats.get(703), regGenWidth(703)+5);
		t7.add(boxFormats.get(704), regGenWidth(704)+5);
		t7.add(boxFormats.get(705), regGenWidth(705)+5);
		t7.add(boxFormats.get(706), regGenWidth(706)+5);
		t7.add(boxFormats.get(707), regGenWidth(707)+5);
		t7.add(boxFormats.get(708), regGenWidth(708)+5);
		return t7;
	}
	
	private TreeFormat eightGenTree() {
		TreeFormat t8 = new TreeFormat();
		t8.add(boxFormats.get(701), regGenWidth(701)/2);
		t8.add(boxFormats.get(702), 2*regGenWidth(702)/3);
		t8.add(boxFormats.get(703), regGenWidth(703));
		t8.add(boxFormats.get(704), regGenWidth(704));
		t8.add(boxFormats.get(705), regGenWidth(705));
		t8.add(boxFormats.get(706), regGenWidth(706));
		t8.add(boxFormats.get(707), regGenWidth(707));
		t8.add(boxFormats.get(708), regGenWidth(708));
		t8.add(boxFormats.get(708), regGenWidth(708));
		return t8;
	}
	
	private TreeFormat nineGenTree() {
		TreeFormat t9 = new TreeFormat();
		t9.add(boxFormats.get(901), 2*regGenWidth(901)/3);
		t9.add(boxFormats.get(902), 2*regGenWidth(902)/3);
		t9.add(boxFormats.get(903), regGenWidth(903));
		t9.add(boxFormats.get(904), regGenWidth(904));
		t9.add(boxFormats.get(905), regGenWidth(905));
		t9.add(boxFormats.get(906), regGenWidth(906));
		t9.add(boxFormats.get(907), regGenWidth(907));
		t9.add(boxFormats.get(908), regGenWidth(908));
		t9.add(boxFormats.get(909), regGenWidth(909));
		t9.add(boxFormats.get(900), regGenWidth(900));
		return t9;
	}
	
	private TreeFormat tenGenTree() {
		TreeFormat t10 = new TreeFormat();
		t10.add(boxFormats.get(901), regGenWidth(901)/2);
		t10.add(boxFormats.get(902), regGenWidth(902)/2);
		t10.add(boxFormats.get(903), regGenWidth(903));
		t10.add(boxFormats.get(904), regGenWidth(904));
		t10.add(boxFormats.get(905), regGenWidth(905));
		t10.add(boxFormats.get(906), regGenWidth(906));
		t10.add(boxFormats.get(907), regGenWidth(907));
		t10.add(boxFormats.get(908), regGenWidth(908));
		t10.add(boxFormats.get(909), regGenWidth(909));
		t10.add(boxFormats.get(900), regGenWidth(900));
		t10.add(boxFormats.get(1010), regGenWidth(1010));
		return t10;
	}
	
	private TreeFormat elevenGenTree() {
		TreeFormat t11 = new TreeFormat();
		t11.add(boxFormats.get(901), regGenWidth(901)/2);
		t11.add(boxFormats.get(902), regGenWidth(902)/2);
		t11.add(boxFormats.get(903), 2*regGenWidth(903)/3);
		t11.add(boxFormats.get(904), regGenWidth(904));
		t11.add(boxFormats.get(905), regGenWidth(905));
		t11.add(boxFormats.get(906), regGenWidth(906));
		t11.add(boxFormats.get(907), regGenWidth(907));
		t11.add(boxFormats.get(908), regGenWidth(908));
		t11.add(boxFormats.get(909), regGenWidth(909));
		t11.add(boxFormats.get(900), regGenWidth(900));
		t11.add(boxFormats.get(1010), regGenWidth(1010));
		return t11;
	}
	
	private TreeFormat twelveGenTree() {
		TreeFormat t12 = new TreeFormat();
		t12.add(boxFormats.get(901), regGenWidth(901)/4);
		t12.add(boxFormats.get(902), regGenWidth(902)/2);
		t12.add(boxFormats.get(903), 3*regGenWidth(903)/4);
		t12.add(boxFormats.get(904), regGenWidth(904));
		t12.add(boxFormats.get(905), regGenWidth(905));
		t12.add(boxFormats.get(906), regGenWidth(906));
		t12.add(boxFormats.get(907), regGenWidth(907));
		t12.add(boxFormats.get(908), regGenWidth(908));
		t12.add(boxFormats.get(909), regGenWidth(909));
		t12.add(boxFormats.get(900), regGenWidth(900));
		t12.add(boxFormats.get(1010), regGenWidth(1010));
		return t12;
	}
	
	private TreeFormat thirteenGenTree() {
		TreeFormat t12 = new TreeFormat();
		t12.add(boxFormats.get(901), regGenWidth(901)/4);
		t12.add(boxFormats.get(902), 3*regGenWidth(902)/8);
		t12.add(boxFormats.get(903), regGenWidth(903)/2);
		t12.add(boxFormats.get(904), regGenWidth(904));
		t12.add(boxFormats.get(905), regGenWidth(905));
		t12.add(boxFormats.get(906), regGenWidth(906));
		t12.add(boxFormats.get(907), regGenWidth(907));
		t12.add(boxFormats.get(908), regGenWidth(908));
		t12.add(boxFormats.get(909), regGenWidth(909));
		t12.add(boxFormats.get(900), regGenWidth(900));
		t12.add(boxFormats.get(1010), regGenWidth(1010));
		return t12;
	}
	
	private TreeFormat fourteenGenTree() {
		TreeFormat t14 = new TreeFormat();
		t14.add(boxFormats.get(1401), regGenWidth(1401)/5);
		t14.add(boxFormats.get(1402), regGenWidth(1402)/3);
		t14.add(boxFormats.get(1403), regGenWidth(1403)/3);
		t14.add(boxFormats.get(1404), regGenWidth(1404));
		t14.add(boxFormats.get(1405), regGenWidth(1405));
		t14.add(boxFormats.get(1406), regGenWidth(1406));
		t14.add(boxFormats.get(1407), regGenWidth(1407));
		t14.add(boxFormats.get(1414), regGenWidth(1414));
		return t14;
	}
	
	private TreeFormat fifteenGenTree() {
		TreeFormat t17 = new TreeFormat();
		t17.add(boxFormats.get(1701), regGenWidth(1701)/4);
		t17.add(boxFormats.get(1702), regGenWidth(1702)/2);
		t17.add(boxFormats.get(1703), 3*regGenWidth(1703)/4);
		t17.add(boxFormats.get(1704), regGenWidth(1704));
		t17.add(boxFormats.get(1705), regGenWidth(1705));
		t17.add(boxFormats.get(1706), regGenWidth(1706));
		t17.add(boxFormats.get(1707), regGenWidth(1707));
		t17.add(boxFormats.get(1708), regGenWidth(1708));
		t17.add(boxFormats.get(1717), regGenWidth(1717));
		return t17;
	}
	
	private TreeFormat sixteenGenTree() {
		TreeFormat t17 = new TreeFormat();
		t17.add(boxFormats.get(1701), regGenWidth(1701)/5);
		t17.add(boxFormats.get(1702), regGenWidth(1702)/3);
		t17.add(boxFormats.get(1703), 3*regGenWidth(1703)/4);
		t17.add(boxFormats.get(1704), regGenWidth(1704));
		t17.add(boxFormats.get(1705), regGenWidth(1705));
		t17.add(boxFormats.get(1706), regGenWidth(1706));
		t17.add(boxFormats.get(1707), regGenWidth(1707));
		t17.add(boxFormats.get(1708), regGenWidth(1708));
		t17.add(boxFormats.get(1717), regGenWidth(1717));
		return t17;
	}
	
	private TreeFormat seventeenGenTree() {
		TreeFormat t17 = new TreeFormat();
		t17.add(boxFormats.get(1701), regGenWidth(1701)/5);
		t17.add(boxFormats.get(1702), regGenWidth(1702)/3);
		t17.add(boxFormats.get(1703), regGenWidth(1703)/2);
		t17.add(boxFormats.get(1704), 4*regGenWidth(1704)/5);
		t17.add(boxFormats.get(1705), regGenWidth(1705));
		t17.add(boxFormats.get(1706), regGenWidth(1706));
		t17.add(boxFormats.get(1707), regGenWidth(1707));
		t17.add(boxFormats.get(1708), regGenWidth(1708));
		t17.add(boxFormats.get(1717), regGenWidth(1717));
		return t17;
	}
	
	private TreeFormat eighteenGenTree() {
		TreeFormat t18 = new TreeFormat();
		t18.add(boxFormats.get(1701), regGenWidth(1701)/5);
		t18.add(boxFormats.get(1702), regGenWidth(1702)/4);
		t18.add(boxFormats.get(1703), regGenWidth(1703)/3);
		t18.add(boxFormats.get(1704), 4*regGenWidth(1704)/5);
		t18.add(boxFormats.get(1705), regGenWidth(1705));
		t18.add(boxFormats.get(1706), regGenWidth(1706));
		t18.add(boxFormats.get(1707), regGenWidth(1707));
		t18.add(boxFormats.get(1708), regGenWidth(1708));
		t18.add(boxFormats.get(1717), regGenWidth(1718));
		return t18;
	}
	
	private TreeFormat nineteenGenTree() {
		TreeFormat t19 = new TreeFormat();
		t19.add(boxFormats.get(2101), regGenWidth(2101)/3);
		t19.add(boxFormats.get(2102), regGenWidth(2102)/2+6);
		t19.add(boxFormats.get(2103), 2*regGenWidth(2103)/3);
		t19.add(boxFormats.get(2104), 3*regGenWidth(2104)/5);
		t19.add(boxFormats.get(2105), regGenWidth(2105));
		t19.add(boxFormats.get(2106), regGenWidth(2106));
		t19.add(boxFormats.get(2107), regGenWidth(2107));
		t19.add(boxFormats.get(2119), regGenWidth(2119));
		return t19;
	}
	
	private TreeFormat twentyGenTree() {
		TreeFormat t20 = new TreeFormat();
		t20.add(boxFormats.get(2101), regGenWidth(2101)/5);
		t20.add(boxFormats.get(2102), regGenWidth(2102)/2);
		t20.add(boxFormats.get(2103), 2*regGenWidth(2103)/3);
		t20.add(boxFormats.get(2104), 3*regGenWidth(2104)/5);
		t20.add(boxFormats.get(2105), regGenWidth(2105));
		t20.add(boxFormats.get(2106), regGenWidth(2106));
		t20.add(boxFormats.get(2107), regGenWidth(2107));
		t20.add(boxFormats.get(2120), regGenWidth(2120));
		return t20;
	}
	
	private TreeFormat twentyOneGenTree() {
		TreeFormat t21 = new TreeFormat();
		t21.add(boxFormats.get(2101), regGenWidth(2101)/5);
		t21.add(boxFormats.get(2102), regGenWidth(2102)/3);
		t21.add(boxFormats.get(2103), 2*regGenWidth(2103)/3);
		t21.add(boxFormats.get(2104), 3*regGenWidth(2104)/5);
		t21.add(boxFormats.get(2105), regGenWidth(2105));
		t21.add(boxFormats.get(2106), regGenWidth(2106));
		t21.add(boxFormats.get(2107), regGenWidth(2107));
		t21.add(boxFormats.get(2121), regGenWidth(2121));
		return t21;
	}
	
	/**
	 * Calculates the generation width for a box that doesn't have intrusion.
	 * The regular size is 11/10 the box size.  The added extra is for the 
	 * line protruding from the box that is connected to the individual's ancestors
	 * lines.
	 * @param index
	 * @return
	 */
	private double regGenWidth(int index) {
		return boxFormats.get(index).width*11/10;
	}
}
