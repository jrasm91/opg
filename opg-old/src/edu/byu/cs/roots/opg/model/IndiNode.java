package edu.byu.cs.roots.opg.model;

public class IndiNode implements Comparable<IndiNode>
{
        public String ID;
        public String label;
        
        public int compareTo(IndiNode other)
        {
        	int labelTest = label.compareToIgnoreCase(other.label );
        	if (labelTest == 0)
        		return ID.compareTo(other.ID);
        	return  labelTest;
        }
        public boolean equals(Object o)
        {
        	IndiNode other = (IndiNode)o;
        	return (ID == other.ID && label == other.label);
        }
}