package edu.byu.cs.roots.opg.model;

import java.io.Serializable;

public class Pair <L extends Serializable, R extends Serializable> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private L left;
	private R right;
	
	public Pair(L left, R right){
		this.left = left;
		this.right = right;
	}
	
	public L getLeft(){return left;}
	public R getRight(){return right;}
	
	public void setLeft(L left){this.left = left;}
	public void setRight(R right){this.right = right;}
}
