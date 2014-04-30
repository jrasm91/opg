package edu.byu.cs.roots.opg.chart.presetvertical;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdMoveTo;
import edu.byu.cs.roots.opg.chart.cmds.DrawCmdRelLineTo;
import edu.byu.cs.roots.opg.model.Individual;

public class AncesTree {

	public AncesBox root = null;
	public ArrayList<Generation> generations;
	//all duplicate ancestors on the current chart are stored in here by id
	private HashMap<String, Integer> duplicateMap;
	private int duplicateCounter;
	private TreeFormat format;

	public AncesTree(Individual rootIndi)
	{
		duplicateMap = new HashMap<String, Integer>();
		duplicateCounter  = 1;
		root = new AncesBox(rootIndi);
		generations = new ArrayList<Generation>();
		Generation gen = new Generation(0);
		gen.add(root);
		buildTree(gen);
	}
	
	public AncesTree(Individual father, Individual mother)
	{
		duplicateMap = new HashMap<String, Integer>();
		duplicateCounter  = 1;
		root = createFakeChildBox(father, mother);
		generations = new ArrayList<Generation>();
		Generation gen = new Generation(-1);
		gen.add(root);
		buildTree(gen);
	}
	
	private AncesBox createFakeChildBox(Individual father, Individual mother) {
		Individual fake = new Individual();
		fake.father = father;
		fake.mother = mother;
		AncesBox fakeChild = new FakeAncesBox(fake);
		return fakeChild;
	}
	
	public void DrawTree(ChartMargins chart, VerticalChartOptions options, double x, double y)
	{
		//root.drawSubTreeConnectors(chart, options, x, y);
		root.drawSubTree(chart, options, x, y);
		if(root instanceof FakeAncesBox || (options.getDescGens() == 0 && options.isIncludeSpouses())) {
			//System.out.println(root.father);
			drawSpouseConnector(chart,
								x + root.father.getWidth()/3.0,
								y + root.fatherVOffset - root.father.getHeight()/2,
								-root.fatherVOffset + root.motherVOffset + root.mother.getHeight());
		}

	}
	
	private void drawSpouseConnector(ChartMargins chart, double x, double y, double offset) {
		chart.addDrawCommand(new DrawCmdMoveTo(chart.xOffset(x), chart.yOffset(y)));
		chart.addDrawCommand(new DrawCmdRelLineTo(0.0,offset,root.father.getLineWidth(), Color.BLACK));
	}
	
	private void setDuplicateNumbers()
	{
		for(Generation g : generations)
		{
			for(Box b : g.getBoxes()){
				if(duplicateMap.containsKey(b.indi.id))
					b.setDuplicateIndex(duplicateMap.get(b.indi.id));
			}
		}
	}
	
	public void setFormat(TreeFormat treeFormat) {
		int i = (root != null && root instanceof FakeAncesBox)? 1 : 0;
		if (i == 1) {
			generations.get(0).setFormat(treeFormat.get(0));
			generations.get(0).setWidth(treeFormat.getIntrusion(0));
		}
		int k = 0;
		for(; i < generations.size(); i++) {
			generations.get(i).setFormat(treeFormat.get(k));
			generations.get(i).setWidth(treeFormat.getIntrusion(k++));
		}
	}
	
	private void buildTree(Generation g) 
	{
		Generation gen = g;
		
		while(!gen.isEmpty()) {
			generations.add(gen);
			gen = createNextGeneration(gen);		
		}
		
		setDuplicateNumbers();
			
	}
	
	private Generation createNextGeneration(Generation gen) {
		Generation nextGen = new Generation(gen.getGenNum()+1);
		for(Box b : gen.getBoxes())
		{
			AncesBox ab = (AncesBox)b;
			if(ab.expand) {
				ab.setFather(addIndividualToGen(ab.indi.father,nextGen));
				ab.setMother(addIndividualToGen(ab.indi.mother,nextGen));
			}
		}
		return nextGen;
	}
	
	/**
	 * Adds Individual to the specified generation.
	 * If individual is null or is a parent of a duplicate it is not added
	 * The duplicate map is populated (0 = non-dup, non-zero = dup)
	 * @param indi individual to add to the generation
	 * @param gen generation to add individual to
	 * @return Box of the individual added, null if not added
	 */
	private AncesBox addIndividualToGen(Individual indi, Generation gen) {
		AncesBox newBox = null;
		if(indi != null) {
			if(!duplicateMap.containsKey(indi.id)) {
				newBox = new AncesBox(indi);
				duplicateMap.put(indi.id,0);
				gen.add(newBox);
			}
			else {
				if(duplicateMap.get(indi.id) == 0){
					newBox = new AncesBox(indi);
					newBox.expand = false;
					gen.add(newBox);
					duplicateMap.put(indi.id,duplicateCounter);
					duplicateCounter++;
				}
			}
		}
		return newBox;
	}
	
	public int getGenerationSize(int gen)
	{
		return generations.get(gen).getCount();
	}
	
	public Generation getGeneration(int gen)
	{
		return generations.get(gen);
	}
	
	public int getGenerationCount()
	{
		return generations.size();
	}
	
	public double getHeight()
	{
		return root.upperSubTreeOffset - root.lowerSubTreeOffset;
	}
	
	public double getWidth(int maxGen)
	{
		double result = 0;
		for(int i=0; i <= maxGen ; i++) 
			result += generations.get(i).getFormat().getWidth();
		return result;
	}
	
	public double getRootOffset()
	{
		return root.upperSubTreeOffset + root.lowerSubTreeOffset;
	}

	public void setTreeFormat(TreeFormat format) {
		this.format = format;
	}

	public TreeFormat getTreeFormat() {
		return format;
	}
	
	

}
