package edu.byu.cs.roots.opg.chart;

import java.awt.Shape;

import edu.byu.cs.roots.opg.model.Individual;

public class ShapeInfo 
{
	//contains a shape
	private Shape myShape;
	//contains a reference to the individual
	private Individual myIndividual;
	private int number;
	private boolean isAncestor;
	
	public ShapeInfo(Shape chartShape, Individual chartIndi, int number, boolean isAncestor)
	{
		this.number = number;
		myShape = chartShape;
		myIndividual = chartIndi;
		this.isAncestor = isAncestor;
	}
	
	public int getNumber()
	{
		return number;
	}
	public Shape getShape() {
		return myShape;
	}
	
	public Individual getIndividual() {
		return myIndividual;
	}
	
	public boolean getIsAncestor(){
		return isAncestor;
	}

}
