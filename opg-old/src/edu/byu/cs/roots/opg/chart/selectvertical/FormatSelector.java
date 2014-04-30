package edu.byu.cs.roots.opg.chart.selectvertical;

import java.util.ArrayList;
import java.util.List;

/**
 * TreeFormatSelector is a singleton class used to select the a TreeFormat given
 * the amount of generations to display.
 *
 */
public class FormatSelector {
	
	//single instance
	private static FormatSelector instance;
	
	private List<BoxFormat> boxFormats;
	private List<TreeFormat> treeFormats;
	
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
		createTreeFormats();
	}
	
	/**
	 * Create  box formats
	 */
	private void createBoxFormats() {
		boxFormats = new ArrayList<BoxFormat>();
		
                                    //index    mheight, width, nfontsize, bfontsize, vpad  voffset  hpad,  vspace, linenums
		boxFormats.add(new BoxFormat(30   ,  190    ,  569    ,   33   ,   39   ,   12    , 1   , 10   ,    9    ,  5   ));
		boxFormats.add(new BoxFormat(31   ,  190    ,  569    ,   33   ,   39   ,   12    , 1   , 10   ,    9    ,  5   ));
		boxFormats.add(new BoxFormat(32   ,  190    ,  569    ,   33   ,   39   ,   12    , 1   , 10   ,    9    ,  5   ));
		boxFormats.add(new BoxFormat(33   ,  190    ,  569    ,   33   ,   39   ,   12    , 1   , 10   ,    9    ,  5   ));
		
		
		
		boxFormats.add(new BoxFormat( 0   ,   144   ,   216   ,   22   ,   28   ,   12   ,  1   ,   10   ,   9   ,   5   ));
		boxFormats.add(new BoxFormat( 1   ,   126   ,   216   ,   20   ,   24   ,   10   ,  1   ,   10   ,   8   ,   5   ));
		boxFormats.add(new BoxFormat( 2   ,   108   ,   216   ,   18   ,   22   ,   8   ,   1   ,   10   ,   7   ,   5   ));
		boxFormats.add(new BoxFormat( 3   ,    90   ,   216   ,   16   ,   20   ,   8   ,   1   ,   10   ,   6   ,   4   ));
		boxFormats.add(new BoxFormat( 4   ,    72   ,   216   ,   14   ,   20   ,   8   ,   1   ,    8   ,   5   ,   4   ));
		boxFormats.add(new BoxFormat( 5   ,    54   ,   216   ,   14   ,   14   ,   7   ,   1   ,    8   ,   4   ,   3   ));
		boxFormats.add(new BoxFormat( 6   ,    36   ,   216   ,   12   ,   10   ,   6   ,   1   ,    8   ,   3   ,   2   ));
		boxFormats.add(new BoxFormat( 7   ,    18   ,   216   ,   11   ,    8   ,   4   ,   0   ,    8   ,   2   ,   1   ));
	}
	
	/**
	 * Create all TreeFormats
	 */
	private void createTreeFormats() {
		treeFormats = new ArrayList<TreeFormat>();
		
		//add tree formats here
		treeFormats.add(createTreeFormat(4,5,6,7,8,9,10,11));
	}
	
	/**
	 * Returns a default format for the trees
	 * Moved over from BoxFormat!
	 */
	public BoxFormat getDefault() {
		return boxFormats.get(11);
	}
	
	/** 
	 * Creates arbitrary size tree format given indexes to box formats;
	 */
	private TreeFormat createTreeFormat(int ...fIndexs ) {
		TreeFormat t = new TreeFormat();
		for(int f : fIndexs)
			t.add(boxFormats.get(f));
		return t;
			
	}
	
	/**
	 * Select tree format given amount of generations
	 */
	public TreeFormat select(int genNum) {
	
		//Insert amazing selecting algorithm here
		
		if(genNum == 3) { //all be the same big ones :)
			return(threeGenTree());
		}		
		else if(genNum >= treeFormats.size())
			return treeFormats.get(treeFormats.size()-1);
		return treeFormats.get(genNum);
	}
	
	private TreeFormat threeGenTree() {
		TreeFormat t3 = new TreeFormat();
		for (int i = 0; i <= 3; i++) {
			t3.add(boxFormats.get(i));
		}
			
		
		return t3;
	}

}
