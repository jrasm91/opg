package opg.spacer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import opg.gui.DrawablePerson;

public class Node implements Iterable<Node> {

	static Dimension DEFAULT_SIZE = new Dimension(150, 50);

	public Node parent;
	public List<Node> children;
	public Bounds bounds;
	public Point p;
	public Dimension d;
	/**
	 * Distances in between children nodes
	 */
	public List<Integer> offsets;
	public String id;

	public enum NodeGender {
		Female, Male, Undefined
	};

	public NodeGender gender;

	public enum NodeType {
		Ancestor, Descendent
	};

	public NodeType type;

	private Node(Node parent, NodeType type, NodeGender gender, Dimension d) {
		if(gender == null){
			gender = NodeGender.Undefined;
		}
		if(d == null){
			d = DEFAULT_SIZE;
		}
		if (this.id == null) {
			this.id = "R";
		}
		
		this.parent = parent;
		this.d = d;
		this.gender = gender;
		this.type = type;
		this.offsets = new LinkedList<Integer>();
		this.children = new LinkedList<Node>();
		if (this.parent != null) {
			this.parent.children.add(this);
			this.id = this.parent.id + this.children.size();
		} else {
			this.id = "R";
		}
		this.bounds = new Bounds();
		this.p = null;
	}

	public static Node makeRoot(NodeType type, NodeGender gender, Dimension d) {
		return new Node(null, type, gender, d);
	}

	public static Node makeRoot(NodeType type) {
		return makeRoot(type, null, null);
	}

	public Node makeNode(NodeGender gender, Dimension d) {
		return new Node(this, this.type, gender, d);
	}

	public Node makeNode() {
		return makeNode(null, null);
	}

	public int getTotalHeight() {
		int low = bounds.getAbsolueLower();
		int high = bounds.getAbsolueUpper();
		return high - low;
	}

	public int getAbsoluteLower() {
		return bounds.getAbsolueLower();
	}

	public int getAbsoluteUpper() {
		return bounds.getAbsolueUpper();
	}

	public DrawablePerson toDrawablePerson() {
		Color c = null;
		switch (gender) {
		case Female:
			c = new Color(255, 179, 224);
			break;
		case Male:
			c = new Color(157, 242, 217);
			break;
		case Undefined:
			c = Color.WHITE;
			break;
		}
		return new DrawablePerson(p, d, null, id, c);
	}

	@Override
	public Iterator<Node> iterator() {
		LinkedList<Node> c = new LinkedList<Node>();
		Queue<Node> q = new LinkedList<Node>();
		q.add(this);
		while (!q.isEmpty()) {
			Node next = q.poll();
			c.add(next);
			for (Node child : next.children) {
				q.add(child);
			}
		}
		return c.iterator();
	}

	public List<Node> getChildren() {
		return children;
	}

	public Node getFirstChild() {
		return children.get(0);
	}

	public Node getLastChild() {
		return children.get(children.size() - 1);
	}
	
	public Node rotate(){
//		this.d = new Dimension(d.height, d.width);
		return this;
	}

}