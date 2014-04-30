package edu.byu.cs.roots.opg.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

//this class has an iterator for all of the values stored in the hashmap
public class IterableHashMap<K,V> extends HashMap<K,V> implements Iterable<V>
{
	private static final long serialVersionUID = 1315504904806506906L;
	LinkedList<V> list = new LinkedList<V>();
		
	public Iterator<V> iterator()
	{
		return list.iterator();
	}
	
	@Override
	public V put(K key, V value)
	{
		list.add(value);
		return super.put(key, value);
	}
}
