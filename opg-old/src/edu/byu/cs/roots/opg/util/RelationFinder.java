package edu.byu.cs.roots.opg.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Individual;

public class RelationFinder 
{
	
	public ArrayList<Integer> cousins;
	private HashMap<String, RelationInfo> marriages;
	private HashMap<String, Integer> hGensAway = new HashMap<String,Integer>();
	private HashMap<String, Integer> wGensAway = new HashMap<String,Integer>();
	int indiCount;
	int maxGen;
	
	public RelationFinder(Individual indi)
	{
		hGensAway = new HashMap<String,Integer>();
		wGensAway = new HashMap<String,Integer>();
		marriages = new HashMap<String,RelationInfo>();
		indiCount = countIndividuals(indi);
		findMarriageRelations(indi);

	}
	
	public int countIndividuals(Individual indi)
	{
		maxGen = 0;
		HashMap<String,Integer> dup = new HashMap<String,Integer>();
		Queue<Individual> queue = new LinkedList<Individual>();
		dup.put(indi.id, 0);
		queue.add(indi);
		Individual cur;
		
		
		while(!queue.isEmpty())
		{
			
			cur = queue.remove();
			
			maxGen = Math.max(maxGen, dup.get(cur.id));
			if(cur.father != null && !dup.containsKey(cur.father.id))
			{
				dup.put(cur.father.id, dup.get(cur.id)+1);
				queue.add(cur.father);
			}
			if(cur.mother != null && !dup.containsKey(cur.mother.id))
			{
				dup.put(cur.mother.id, dup.get(cur.id)+1);
				queue.add(cur.mother);
			}
		}
		return dup.size();
		
	}
	
	public void findMarriageRelations(Individual indi)
	{
		Queue<Individual> queue = new LinkedList<Individual>();
		marriages.clear();
		Individual cur;
		//get individuals marriages
		for(Family fam : indi.fams)
		{
			RelationInfo rel = findSpouseRelation(fam.husband, fam.wife);
			marriages.put(fam.husband.id + fam.wife.id, rel);
		}
		queue.add(indi);
		//get ancestor marriages
		while(!queue.isEmpty())
		{
			cur = queue.remove();
			if(cur.father != null && cur.mother !=null)
			{
				String key = cur.father.id + cur.mother.id;
				if(!marriages.containsKey(key))
				{
					RelationInfo rel = findSpouseRelation(cur.father, cur.mother);
					marriages.put(key, rel);
					queue.add(cur.father);
					queue.add(cur.mother);
				}

			}
		}
	}
	
	public RelationInfo findSpouseRelation(Individual husband, Individual wife)
	{
		RelationInfo rel = new RelationInfo();

		if(husband != null && wife !=null)
		{
			hGensAway.clear();
			wGensAway.clear();
			markGensAway(husband);
			rel = findGensAway(wife);
			rel.husband = husband;
			rel.wife = wife;
		}
		return rel;
	}
	
	protected void markGensAway(Individual indi)
	{
		Queue<Individual> queue = new LinkedList<Individual>();
		
		queue.add(indi);
		hGensAway.put(indi.id,0);
		while(!queue.isEmpty())
		{
			Individual cur = queue.remove();
			
			if(cur.father != null && !hGensAway.containsKey(cur.father.id))
			{
				hGensAway.put(cur.father.id,hGensAway.get(cur.id)+1);
				queue.add(cur.father);
			}
			if(cur.mother != null && !hGensAway.containsKey(cur.mother.id))
			{
				hGensAway.put(cur.mother.id,hGensAway.get(cur.id)+1);
				queue.add(cur.mother);
			}
		}
	}
	
	protected RelationInfo findGensAway(Individual indi)
	{
		RelationInfo rel = new RelationInfo();
		Queue<Individual> queue = new LinkedList<Individual>();
		
		queue.add(indi);
		wGensAway.put(indi.id,0);
		while(!queue.isEmpty())
		{
			Individual cur = queue.remove();
			
			if(cur.father != null)
			{
				if(hGensAway.containsKey(cur.father.id))
				{
					rel.commonAnces = cur.father;
					rel.fromHusband = hGensAway.get(cur.father.id);
					rel.fromWife =  wGensAway.get(cur.id)+1;
					return rel;
				}
				else if(!wGensAway.containsKey(cur.father.id))
				{
					wGensAway.put(cur.father.id,wGensAway.get(cur.id)+1);
					queue.add(cur.father);
				}
			}
			if(cur.mother != null)
			{
				if(hGensAway.containsKey(cur.mother.id))
				{
					rel.commonAnces = cur.mother;
					rel.fromHusband = hGensAway.get(cur.mother.id);
					rel.fromWife =  wGensAway.get(cur.id)+1;
					return rel;
				}
				else if(!wGensAway.containsKey(cur.mother.id))
				{
					wGensAway.put(cur.mother.id,wGensAway.get(cur.id)+1);
					queue.add(cur.mother);
				}
			}
		}
		return rel;
	}
	
	public void printCousins()
	{
		
	}
	
	
	private class RelationInfo
	{
		public Individual commonAnces;
		public Individual husband;
		public Individual wife;
		public int fromHusband;
		public int fromWife;
		
		public RelationInfo(Individual ca, int h, int w)
		{
			commonAnces = ca;
			fromHusband =h;
			fromWife = w;
		};
		
		public RelationInfo()
		{
			commonAnces = null;
			fromHusband = -1;
			fromWife = -1;
		};
		
		public boolean hasRelation()
		{
			if(fromHusband != -1 && fromWife != -1)
				return true;
			
			return false;
		}
		
		public void print()
		{
			if(hasRelation() && commonAnces !=null)
			{
				System.out.println("Husband: " + husband.givenName + " " +husband.surname + " ( " + fromHusband + " )");
				System.out.println("Wife: " + wife.givenName + " " +wife.surname + " ( " + fromWife + " )");
				System.out.println("Common Ancestor: " + commonAnces.givenName + " " + commonAnces.surname);
				System.out.println();
			}
		}
	}
	
	public void printRelation(Individual indi)
	{
		for(Family fam : indi.fams)
		{
			RelationInfo rel = findSpouseRelation(fam.husband, fam.wife);
			rel.print();
		}
	}
	
	public void printStats()
	{
		System.out.println("Number of Marriages: " + marriages.size());
		System.out.println("Number of Individuals: " + indiCount);
		System.out.println("Max Number of Generations: " + maxGen);
		System.out.println();
		
		ArrayList<Integer> cousins = new ArrayList<Integer>();
		for(int i=0; i < 100; i++)
			cousins.add(0);
		
		for(RelationInfo ri : marriages.values())
		{
			int index = Math.min(ri.fromHusband-1, ri.fromWife-1);
			if(index > -1)
				cousins.set(index, cousins.get(index) + 1);
		}
		int total = 0;
		for(int i=0; i < cousins.size(); i++)
		{
			String suffix = "";
			switch (i)
			{
				case 1 : suffix = "st"; break;
				case 2 : suffix = "nd"; break;
				case 3 : suffix = "rd"; break;
				default : suffix = "th";
			}
			
			if(cousins.get(i) > 0)
			{
				total += cousins.get(i);
				System.out.println(i + suffix +" cousins : " + cousins.get(i));
			}
		}
		System.out.println("Total cousins : " + total);
		
		System.out.println("\nDetails:");
		for(RelationInfo ri : marriages.values())
		{
			ri.print();
		}
		
	}
	
}


