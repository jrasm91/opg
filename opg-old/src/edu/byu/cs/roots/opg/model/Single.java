package edu.byu.cs.roots.opg.model;

import java.io.Serializable;

public class Single <I extends Serializable> implements Serializable{

	private static final long serialVersionUID = 1L;
	private I item;
	
	public Single(I item){
		this.item = item;
	}
	
	public I getItem(){
		return item;
	}
	
	public void setItem(I item){
		this.item = item;
	}
	
	public String toString(){
		return item.toString();
	}
}
