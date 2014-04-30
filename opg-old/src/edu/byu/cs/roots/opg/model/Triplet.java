package edu.byu.cs.roots.opg.model;

import java.io.Serializable;

public class Triplet <L extends Serializable, M extends Serializable, R extends Serializable> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final L left;
	private final M middle;
	private final R right;
	
	public Triplet(L left, M middle, R right){
		this.left = left;
		this.middle = middle;
		this.right = right;
	}
	
	public L getLeft(){return left;}
	public M getMiddle(){return middle;}
	public R getRight(){return right;}
}
