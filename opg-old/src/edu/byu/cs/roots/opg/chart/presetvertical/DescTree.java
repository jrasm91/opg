package edu.byu.cs.roots.opg.chart.presetvertical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Individual;

public class DescTree {

	public DescBox root = null;
	public ArrayList<Generation> generations;
	// all duplicate ancestors on the current chart are stored in here by id
	private HashMap<String, Integer> duplicateMap;
	private int duplicateCounter;
	private TreeFormat format;

	public DescTree(Individual rootIndi) {
		duplicateMap = new HashMap<String, Integer>();
		duplicateCounter = 1;
		generations = new ArrayList<Generation>();
		root = new DescBox(rootIndi);
		Generation gen = new Generation(0);
		gen.add(root);
		buildTree(gen);
	}

	public void drawTree(ChartMargins chart, VerticalChartOptions options,
			double x, double y) {
		root.drawSubTree(chart, options, x, y);
	}

	private void setDuplicateNumbers() {
		for (Generation g : generations) {
			for (Box b : g.getBoxes()) {
				if (duplicateMap.containsKey(b.indi.id))
					b.setDuplicateIndex(duplicateMap.get(b.indi.id));
			}
		}
	}

	public void setFormat(TreeFormat treeFormat) {
		for (int i = 0; i < generations.size(); i++)
			generations.get(i).setFormat(treeFormat.get(i));// BoxFormat.getDefault());
	}

	private void buildTree(Generation g) {
		Generation gen = g;
		generations.add(gen);
		while (!gen.isEmpty()) {
			gen = createNextGeneration(gen);
			generations.add(gen);
		}
		setDuplicateNumbers();
	}

	private Generation createNextGeneration(Generation gen) {
		Generation nextGen = new Generation(gen.getGenNum() - 1);
		for (Box b : gen.getBoxes()) {
			DescBox db = (DescBox) b;
			addChildrenToGen(db, nextGen);
		}
		return nextGen;
	}

	private List<Individual> getChildren(Family fam) {
		List<Individual> fams = new ArrayList<Individual>();
		if (fam != null && !fam.children.isEmpty()) {
			for (Individual i : fam.children)
				fams.add(i);
		}
		return fams;
	}

	private void addChildrenToGen(DescBox b, Generation gen) {
		for (Family f : b.indi.fams) {
			for (Individual i : getChildren(f)) {
				addIndividualToGen(i, b, gen);
			}
		}
	}

	// private void addFamiliesToGen(Individual ind, Generation gen) {
	// if( ind.fams != null && !ind.fams.isEmpty()) {
	// for(Family fam : ind.fams)
	// addFamilyToGen(fam,gen);
	// }
	// }

	/**
	 * Adds Individual to the specified generation. If individual is null or is
	 * a parent of a duplicate it is not added The duplicate map is populated (0
	 * = non-dup, non-zero = dup)
	 * 
	 * @param indi
	 *            individual to add to the generation
	 * @param gen
	 *            generation to add individual to
	 * @return Box of the individual added, null if not added
	 */
	private DescBox addIndividualToGen(Individual indi, DescBox parent,
			Generation gen) {
		DescBox newBox = null;
		if (indi != null) {
			if (!duplicateMap.containsKey(indi.id)) {
				duplicateMap.put(indi.id, 0);
				addFamilies(indi, parent, gen);
			} else {
				if (duplicateMap.get(indi.id) == 0) {
					addFamilies(indi, parent, gen);
					duplicateMap.put(indi.id, duplicateCounter);
					duplicateCounter++;
				}
			}
		}
		return newBox;
	}

	private void addFamilies(Individual ind, DescBox parent, Generation gen) {
		if (!ind.fams.isEmpty()) {
			for (Family f : ind.fams) {
				DescBox childMarriage = new DescBox(ind, f);
				parent.addChild(childMarriage);
				gen.add(childMarriage);
			}
		} else {
			DescBox childMarriage = new DescBox(ind);
			parent.addChild(childMarriage);
			gen.add(childMarriage);
		}
	}

	// private DescBox addFamilyToGen(Family fam, Generation gen) {
	// DescBox newBox = null;
	// if(fam != null) {
	// if(!duplicateMap.containsKey(fam.id)) {
	// newBox = new DescBox(fam);
	// duplicateMap.put(fam.id,0);
	// gen.add(newBox);
	// }
	// else {
	// if(duplicateMap.get(fam.id) == 0){
	// newBox = new DescBox(fam);
	// gen.add(newBox);
	// duplicateMap.put(fam.id,duplicateCounter);
	// duplicateCounter++;
	// }
	// }
	// }
	// return newBox;
	// }

	public int getGenerationSize(int gen) {
		return generations.get(gen).getCount();
	}

	public int getGenerationCount() {
		return generations.size();
	}
	
	public Generation getGeneration(int gen)
	{
		return generations.get(gen);
	}

	public double getHeight() {
		return root.upperSubTreeOffset - root.lowerSubTreeOffset;
	}

	public double getRootOffset() {
		return root.upperSubTreeOffset + root.lowerSubTreeOffset;
	}

	public void setTreeFormat(TreeFormat format) {
		this.format = format;
	}

	public TreeFormat getTreeFormat() {
		return format;
	}
	
	public void printAllGenerationWidths() {
		System.out.println("Generation amt: " + generations.size());
		for(int i = 0; i < generations.size(); i++) {
			System.out.print("generation: " + i);
			System.out.println("\twidth: " + generations.get(i).getWidth());
		}
	}

	public double getWidth(int maxGen) {
		double result = 0;
		int limit = Math.min(maxGen, generations.size());
		for (int i = 0; i <= limit; i++) {
			BoxFormat f = generations.get(i).getFormat();
			if(f != null)
				result += f.getWidth();
		}
		return result;
	}

}
