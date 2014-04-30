package opg.spacer;

import java.util.LinkedList;
import java.util.List;

public class Bounds {
	
	private class Bound {
		private int upper;
		private int lower;
		
		public Bound(int upper, int lower){
			this.upper = upper;
			this.lower = lower;
		}
		
		public void shift(int amount){
			this.upper += amount;
			this.lower += amount;
		}
		
		@Override
		public String toString(){
			return "U:" + upper + ",L:" + lower;
		}
	}

	private List<Bound> bounds;
	
	public Bounds(){
		bounds = new LinkedList<Bound>();
	}
	
	public Bounds(int... nums){
		this();
		for(int i = 0; i < nums.length; i+=2){
			bounds.add(new Bound(nums[i], nums[i + 1]));
		}
	}
	
	public boolean has(int generation){
		return bounds.size() > generation;
	}
	
	public int getLower(int generation){
		return bounds.get(generation).lower;
	}
	
	public int getAbsolueLower(){
		int max = bounds.get(0).lower;
		for(Bound b: bounds){
			if (b.lower < max){
				max = b.lower;
			}
		}
		return max;
	}
	
	public int getUpper(int generation){
		return bounds.get(generation).upper;
	}
	
	public int getAbsolueUpper(){
		int max = bounds.get(0).upper;
		for(Bound b: bounds){
			if (b.upper > max){
				max = b.upper;
			}
		}
		return max;
	}
	
	public void add(int upper, int lower){
		bounds.add(new Bound(upper, lower));
	}
	
	public void addAll(Bounds all){
		for(Bound b: all.bounds)
			bounds.add(new Bound(b.upper, b.lower));
	}
	
	public void clear(){
		bounds.clear();
	}
	
	public void shift(int amount){
		for(int i = 1; i < bounds.size(); i++){
			bounds.get(i).shift(amount);;
		}
	}
	
	public void update(int generation, int upper, int lower){
		bounds.set(generation, new Bound(upper, lower));
	}
	
	public void updateLower(int generation, int lower){
		bounds.set(generation, new Bound(bounds.get(generation).upper, lower));
	}
	
	public void updateHigher(int generation, int upper){
		bounds.set(generation, new Bound(upper, bounds.get(generation).lower));
	}
	
	@Override
	public String toString(){
		return "Bounds: " + bounds.toString();
	}
}
