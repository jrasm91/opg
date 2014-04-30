package edu.byu.cs.roots.opg.nfs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class RelationMap implements Serializable {

	private static final long serialVersionUID = 1000L;
	
	private HashMap <String, Integer> locationInTree;
	private HashMap <String, Boolean> parsedYet;
	private HashMap <String, Void> rootDescendant;
	private int oldestGen;
	private int youngestGen;

	public RelationMap() {
		locationInTree = new HashMap<String, Integer>();
		parsedYet = new HashMap<String, Boolean>();
		rootDescendant = new HashMap<String, Void>();
		oldestGen = 0;
		youngestGen = 0;
	}

	public synchronized void add(String Id, int gen, boolean done) {
		if (!contains(Id)){
			if (gen < youngestGen)
				youngestGen = gen;
			if (gen > oldestGen)
				oldestGen = gen;
			locationInTree.put(Id, gen);
			parsedYet.put(Id, done);
		}
	}

	public boolean isCompleted(String Id) {
		return parsedYet.get(Id);
	}

	public synchronized int getGeneration(String Id) {
		return locationInTree.get(Id);
	}

	public synchronized boolean contains(String Id) {
		//if it is in ParsedYet, then it must be in locationInTree as well
		 return parsedYet.containsKey(Id);
	}

	public synchronized void nowCompleted(String Id) {
		parsedYet.remove(Id);
		parsedYet.put(Id, true);
	}

	public int getAmtNotDone() {
		int count = 0;
		Collection<String> allIDs = parsedYet.keySet();
		for (String id: allIDs) {
			if (!parsedYet.get(id))
				count++;
		}
		return count;
	}

	public String getLowestGen() {
		String lowestID = "abcdefg";
		int lowest = 10000;
		Collection<String> allIDs= locationInTree.keySet();
		for (String id: allIDs) {
			int gen = locationInTree.get(id);
			if (gen < lowest && !parsedYet.get(id)) {
				lowest = gen;
				lowestID = id;
			}
		}
		return lowestID;
	}

	public ArrayList<String> getSmToLgNotDone() {
		ArrayList<String> smToLg = new ArrayList<String>();
		ArrayList<String> notDone = getAllNotDone();
		int counter = youngestGen;
		while (counter <= oldestGen) {
			for (String ID: notDone) {
				if (locationInTree.get(ID) == counter)
					smToLg.add(ID);
			}
			counter++;
		}		
		return smToLg;
	}
	
	public ArrayList<String> getGenerationList(int i) {
		ArrayList<String> genI = new ArrayList<String>();
		Collection<String> allIDs = locationInTree.keySet();
		for (String id: allIDs) {
			if (locationInTree.get(id).equals(i))
				genI.add(id);
		}
		
		return genI;
	}

	public int getOldestGeneration() {
		return oldestGen;
	}

	public int getYoungestGeneration() {
		return youngestGen;
	}
	
	public int getTotalPeople(){
		return parsedYet.size();
	}

	public ArrayList<String> getAllNotDone() {
		ArrayList<String> notDoneIDs = new ArrayList<String>();
		Collection<String> allIDs = parsedYet.keySet();
		for (String id: allIDs) {
			if (!parsedYet.get(id))
				notDoneIDs.add(id);
		}
		return notDoneIDs;
	}

	public ArrayList<String> getAllDone() {
		ArrayList<String> doneIDs = new ArrayList<String>();
		Collection<String> allIDs = parsedYet.keySet();
		for (String id: allIDs) {
			if (parsedYet.get(id))
				doneIDs.add(id);
		}
		return doneIDs;
	}

	public boolean isEmpty() {
		return locationInTree.isEmpty();
	}
	
	public synchronized void addRootDescendant(String add){
		rootDescendant.put(add, null);
	}
	
	public boolean isRootDescendant(String add){
		return rootDescendant.containsKey(add);
	}
}










