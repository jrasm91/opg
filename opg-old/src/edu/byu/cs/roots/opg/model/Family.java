package edu.byu.cs.roots.opg.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Family implements Serializable
{

	private static final long serialVersionUID = 1000L;
	public String id; //identification string for the family
	public Event marriage = null;
	public String husbandId = null;
	public String wifeId = null;
	public Individual husband = null;
	public Individual wife = null;
	public transient ArrayList<Individual> children = new ArrayList<Individual>();
	public ArrayList<String> childrenXRefIds = new ArrayList<String>();
	
	public boolean divorce = false;
	
	//LDS ordinance data
	public boolean sealing = false;
	public boolean sealingComplete = false;
	
	public boolean isInTree = false;
	
	//Constructors
	public Family(String id)
	{
		this.id = id;
	}
	
	public Family()
	{
		this.id = null;
		this.children = new ArrayList<Individual>();
		this.childrenXRefIds = new ArrayList<String>();
	}
	
	@Override
	public int hashCode()
	{
		return id.hashCode();
	}
	
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append("FAM_ID: "+id+"\n");		
		b.append("HUSB_ID: "+husbandId+"\n");
		b.append(husband+"\n");
		b.append("WIFE_ID: "+wifeId+"\n");
		b.append(wife+"\n");
		b.append("CHIL_IDS: "+childrenXRefIds+"\n");
		b.append(children+"\n");		
		
		return b.toString();
	}
	
	public void resetFlags()
	{
		isInTree = false;
	}
	
}
