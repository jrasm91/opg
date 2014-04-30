package edu.byu.cs.roots.opg.model;
/*
This holds all of the personal information for each person in the gedcom
It also contains methods to print and compute boxes for the actual spacing
Each person has their own instance of this class.  People that appear in the
tree more than once have an instance for each time they are in the tree.
*/

import java.util.ArrayList;

public class IndividualRecord
{
	//consants
	static final float boxdx = 144;
	static final float boxSingleParentOffset = 3;
	static final float boxverticalSpace = 3;
	static final float boxy = 24;
	static final float boxdelta = 3;
	static final float boxxspace = 20;
	static final float fontsize2 = 10;
	static final float boxhorizontalspace = 35;

	//member variables
	//new algorithm variables
	float dy0, dy1, dydx0, dydx1, x, y; //dy0 = y center of indi to top; dy1 = center of indi to bottom
	//dydx0 = slope up, dydx0 = bottom slope
	float dx; //horizontal width of bounding box of subtree rooted at this node
	float fy, my; //vertical distance from the center of this box to the center of mother's and father's boxes
	float r, g, b; //color of box
	public String givenName;
	public String surName;
	public String middleName; //string of all the middle names
	public String nameSuffix;
	public EventClass birth;
	public EventClass death;
	public EventClass marriage;
	public String famc;//id of the family of which this individual is a child
	public ArrayList<String> fams;//list of ids for all of the marriage families
	public ArrayList<IndividualRecord> children;//list of references to all the children
	public int gender; //0 = male, 1 = female

	//for the descendents
	public float center; //this will be the center left of the indibox
	//add in the print stuff here


	public IndividualRecord mother; //reference to the mother
	public IndividualRecord father; //reference to the father
	public String id; //id of this person from the gedcom
	public float separationvalue; // vertical seperation between individuals
	public float distbetweengen; // horizontal distance between gens
	public float linetofatherx, linetofathery;	//coordinates of where to start line to father
	public float linetomotherx, linetomothery;
	public float linefromchildx, linefromchildy;
	public boolean vertlinetofather, vertlinetomother, vertlinefromchild; //flag to say whether the drawing should come out vertically or not
	public float photoxoffset; //this is the xoffset to use if there is a picture stored for this indi
	public float fontsize; // this is the font size of the name
	public int linesofinfo; // this is how many pieces of information we have. 
	//boolean hasphoto; // this is whether or not the individual has photo information
	public float indiyheight;//height of this individual box (calculated inGenClass)
	public float xsepvalue;//separationvalue between the current generation and the next generation
	public boolean intree;//flag of whether or not it is in the tree
	public boolean drawn;//flag of whether or not the indi has been drawn
	public float c1, c2, c3, c; // these are used in determining the bounding box
	
	//////////////////////////
	//variables used in the new algorithm
	public double fOffsetTight; //the vertical offset from the center of this individual to its father using the tight fit algorithm
	public double mOffsetTight; //the vertical offset from the center of this individual to its mother using the tight fit algorithm
	public double fOffsetTrapBox; //the vertical offset from the center of this individual to its father using the trapezoidal/bounding box fit algorithm
	public double mOffsetTrapBox; //the vertical offset from the center of this individual to its mother using the trapezoidal/bounding box fit algorithm
	public double upperSubTreeHeight; //the height of to the top of the bounding box of this individual's sub tree measured from its center
	public double lowerSubTreeHeight; //the height of to the bottom of the bounding box of this individual's sub tree measured from its center
	public double upperHeight;//the actual current upper height of this individual's sub tree (used for measuement)  
	public double lowerHeight;//the actual current lower height of this individual's sub tree (used for measuement)
	public double treeWidth; //the width of the entire tree, including this individual
	public double boxHeight; //the height of this individual's box
	public double boxWidth; //the width of this individual's box
	public double upperBoundingSlope;  //the slope of the line from the upper left corner of this individual's box that includes all of the sub tree (cannot be negative)
	public double lowerBoundingSlope; //the slope of the line from the lower left corner of this individual's box that includes all of the sub tree (cannot be positive)
	public ArrayList<Double> upperBounds; //list of upper bounding vertical offsets from this individual's center for each generation in the sub tree (0 is this individual's offset)
	public ArrayList<Double> lowerBounds; //list of lower bounding vertical offsets from this individual's center for each generation in the sub tree (0 is this individual's offset)
	public int maxGensOfTree; //this the maximum number of generations in this individuals tree
	public boolean inTree = false;
	
	public String photoPath; //absolute path of photo of this person.
	public boolean hasPhoto = false; // this is whether or not the individual has photo information
	
	
	
    
	//Constructors
	//----------------------------------------------------------
	public IndividualRecord(String tmpid)
	{
		id = tmpid;
		fams = new ArrayList<String>();
		birth = null;
		death = null;
		marriage = null;
		middleName = "";
		mother = null;
		father = null;
		fontsize = 12;
		//printf(tmpid.c_str());
		photoxoffset = 25;	//this is how far from the far left corner of the bounding box the name will start.  This is how much space the photo gets
		
		fontsize = 16;
		linesofinfo = 0;
		xsepvalue = 0;
		x =0;
		y = 0;
		intree = false;
		drawn = false;
		c = 0;
		c2 = 0;
		c3 = 0;	
		center = 0;
		gender = 0;
		fams.clear();
		fams.ensureCapacity(0);
	}

	//----------------------------------------------------------

	public IndividualRecord(IndividualRecord indi1)
	{
		System.out.println("starting to copy indi1\n");
		//things that stay the same between copies
		givenName = indi1.givenName;
		surName = indi1.surName;
		middleName = indi1.middleName;
		id = indi1.id;
		fams = indi1.fams;
		famc = indi1.famc;
		birth = indi1.birth;
		death = indi1.death;
		marriage = indi1.marriage;
		mother = indi1.mother;
		father = indi1.father;
		photoxoffset = indi1.photoxoffset;
		indiyheight = indi1.indiyheight;
		linesofinfo = indi1.linesofinfo;
		xsepvalue = indi1.xsepvalue;
		x = 0;
		y = 250;
		intree = false;
		drawn = false;
		c = 0;
		c1 = 0;
		c2 = 0;
		c3 = 0;
		System.out.println("done copying Inid1\n");
	}

	//member functions
	//----------------------------------------------------------

	public String toString()
	{
		String indiv;
		indiv = "\nIndividual Record " + id + ":\n" ;

		indiv = indiv + givenName;
		if (!middleName.equals("")) 
		{
			indiv = indiv + "." + middleName;
		}
		indiv = indiv + "."+ surName;
		if (birth != null)
			indiv = indiv + "\nB: " + birth.toString();
		if (death != null)
			indiv = indiv + "\nD: " + death.toString();
		indiv = indiv + "\n";
		if (marriage != null) 
		{
			indiv = indiv + "\nM: " + marriage.toString();
		}
		return (indiv);
	}

	//----------------------------------------------------------
	public void resetFlags()
	{
		//recurse on parents
		if (father != null && (father.drawn || father.inTree))
			father.resetFlags();
		if (mother != null && (mother.drawn || mother.inTree))
			mother.resetFlags();
		
		drawn = false;
		inTree = false;
	}
	//----------------------------------------------------------
	
}