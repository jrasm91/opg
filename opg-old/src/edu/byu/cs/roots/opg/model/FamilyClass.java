package edu.byu.cs.roots.opg.model;
/*
This class is a holder object for family information.  All of the family classes
are stored in the family map in the parser class
*/

import java.util.ArrayList;

public class FamilyClass
{
	public String id; //identification string for the individual
	public EventClass marriage;
	public IndividualRecord husband;
	public IndividualRecord wife;
	public ArrayList<IndividualRecord> children;
	public boolean inContainer; //flag to show if a family is already in a descendency container (to avoid infiite recursion)
	
	public FamilyClass(String identity)
	{
		id = identity;
		children = new ArrayList<IndividualRecord>();
		inContainer = false;
	}
	
	//??remove this method if unused
	public int hashCode()
	{
		return 0;
	}
}