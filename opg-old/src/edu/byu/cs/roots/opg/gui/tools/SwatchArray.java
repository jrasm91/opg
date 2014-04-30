package edu.byu.cs.roots.opg.gui.tools;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.border.SoftBevelBorder;


public class SwatchArray extends javax.swing.JPanel{

	private static final long serialVersionUID = -3061887987335615900L;
	public int swatchHeight;
	public int swatchWidth;
	public int spacing;
	private ActionListener slistener;
	
	private Swatch selected;
	CopyOnWriteArraySet<ActionListener> listenerset = new CopyOnWriteArraySet<ActionListener>();
	private ArrayList<Swatch> swatches = new ArrayList<Swatch>();
	
	
	public SwatchArray(){
		swatchHeight = 15;
		swatchWidth = 20;
		spacing = 3;
		slistener = new SwatchSelectionListener();
	}
	
	private void selectSwatch(Swatch s){
		if(selected != null) selected.setSelected(false);
		selected = s;
		selected.setSelected(true);
	}
	
	@Override
	public void add(Component component, Object constraints){
		if(component instanceof Swatch ){
			addSwatch((Swatch) component);
		}
		super.add(component, constraints);
	}
	
	
	private void addSwatch(Swatch swatch){
		swatches.add(swatch);
		swatch.height = swatchHeight;
		swatch.width = swatchWidth;
		swatch.addActionListener(slistener);
		swatch.setPreferredSize(new Dimension(swatchWidth, swatchHeight));
		swatch.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		this.validate();
	}
	
	void fireActionPerformed(ActionEvent e){
		for(ActionListener listener : listenerset){
			listener.actionPerformed(e);
		}
	}
	
	public void addActionListener(ActionListener arg0) {
		listenerset.add(arg0);
	}
	
	public void removeActionListener(ActionListener arg0) {
		listenerset.remove(arg0);
	}
	
	
//	@Override
//	public void paint(Graphics g){
//		super.paint(g);
////		int height = getHeight();
////		int width = getWidth();
////		g.drawRect(spacing, spacing, width-2*spacing, height-2*spacing);
////		Iterator<Swatch> itr = swatches.iterator();
////		Swatch temp = null;
////		for(int y = 0;y<height-swatchHeight;y+= swatchHeight + spacing){
////			for(int x = 0;x<width - swatchWidth;x+= swatchWidth + spacing){
////				if(itr.hasNext()) temp = itr.next();
////				else break;
////				g.setColor(temp.color);
////				g.fillRect(x, y, swatchWidth, swatchHeight);	
////				g.setColor(Colo.black);
////				g.drawRect(x, y, swatchWidth, swatchHeight);		
////			}
////			if(!itr.hasNext()) break;
////		}
//
//	}
	
	private void deselectAll(){
		for(Swatch s : swatches){
			s.setSelected(false);
		}
	}
	
	
	private class SwatchSelectionListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			deselectAll();
			Swatch source = (Swatch) arg0.getSource();			
			selectSwatch(source);
			fireActionPerformed(arg0);
			repaint();
		}
	}
	
	public Swatch getSelected(){
		return selected;
	}

	/**
	 * @return the swatchHeight
	 */
	public int getSwatchHeight() {
		return swatchHeight;
	}

	/**
	 * @param swatchHeight the swatchHeight to set
	 */
	public void setSwatchHeight(int swatchHeight) {
		this.swatchHeight = swatchHeight;
		for(Swatch s: swatches){
			s.height = swatchHeight;
			repaint();
		}
	}

	/**
	 * @return the swatchWidth
	 */
	public int getSwatchWidth() {
		return swatchWidth;
	}

	/**
	 * @param swatchWidth the swatchWidth to set
	 */
	public void setSwatchWidth(int swatchWidth) {
		this.swatchWidth = swatchWidth;
		for(Swatch s: swatches){
			s.width = swatchWidth;
			repaint();
		}
	}

	/**
	 * @return the swatches
	 */
	public ArrayList<Swatch> getSwatches() {
		return swatches;
	}
	
	
	
	
	
	
}
