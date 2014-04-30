package opg.other;

import opg.main.Const;

public class Chart {

	private int width;
	private int height;
	private int marginLeft;
	private int marginRight;
	private int marginTop;
	private int marginBottom;
	
	public Chart(){
		this(Const.DEFUALT_PAGE_WIDTH * 10, Const.DEFAULT_PAGE_HEIGHT * 10);
	}

	public Chart(int width, int height) {
		this.width = width;
		this.height = height;
		this.marginLeft = Const.MARGIN_PAGE;
		this.marginRight = Const.MARGIN_PAGE;
		this.marginTop = Const.MARGIN_PAGE;
		this.marginBottom = Const.MARGIN_PAGE;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getAvaliableHeight(){
		return this.height - (marginBottom + marginTop);
	}

	public void setAvaliableHeight(int height){
		this.height = height + (marginTop + marginBottom);
	}
	
	public int getAvaliableWidth(){
		return this.width - (marginLeft + marginRight);
	}
	
	public void setAvaliableWidth(int width){
		this.width = width + (marginLeft + marginRight);
	}

	public int getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(int marginLeft) {
		this.marginLeft = marginLeft;
	}

	public int getMarginRight() {
		return marginRight;
	}

	public void setMarginRight(int marginRight) {
		this.marginRight = marginRight;
	}

	public int getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(int marginTop) {
		this.marginTop = marginTop;
	}

	public int getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(int marginBottom) {
		this.marginBottom = marginBottom;
	}

}




