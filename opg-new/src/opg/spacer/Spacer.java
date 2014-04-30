package opg.spacer;

import java.awt.Point;

import opg.spacer.Node.NodeGender;
import opg.spacer.Node.NodeType;

public class Spacer {

	public static void space(Node n) {
		n.bounds.clear();
		for (Node child : n.children) {
			space(child);
		}
		n.bounds.add(n.d.height / 2, -n.d.height / 2);
		if (n.children.size() == 1) {
			Node child = n.getFirstChild();
			n.bounds.addAll(child.bounds);
			if(n.type == NodeType.Ancestor){
				int height = child.d.height;
				if(child.gender == NodeGender.Male){
					n.bounds.shift(height/2);
				} else if(child.gender == NodeGender.Female){
					n.bounds.shift(-height/2);
				}
			}
			n.offsets.add(0);
		} else if (n.children.size() > 1) {
			// copy first child limits
			int generation = 0;
			n.offsets.add(0);
			while (true) {
				if (!n.children.get(0).bounds.has(generation)) {
					break;
				}
				int upper = n.children.get(0).bounds.getUpper(generation);
				int lower = n.children.get(0).bounds.getLower(generation);
				n.bounds.add(upper, lower);
				generation++;
			}
			// merge children trees into parent tree
			for (int i = 1; i < n.children.size(); i++) {
				combineChildTree(n, n.children.get(i));
			}
			// center parent among children
			n.bounds.shift((n.bounds.getUpper(1) - n.bounds.getLower(1)) / 2 - n.bounds.getUpper(1));
		}
		// update children offset relative to parent height
		for (int i = 0; i < n.offsets.size(); i++) {
			int previous = n.offsets.get(i);
			n.offsets.set(i, previous + n.d.height / 2);
		}
	}

	private static void combineChildTree(Node parent, Node child) {
		int minSpacing = child.d.height;
		int generation = 0;

		while (true) {
			if (!parent.bounds.has(generation + 1) || !child.bounds.has(generation)) {
				break;
			}
			int lower = parent.bounds.getLower(generation + 1);
			int upper = child.bounds.getUpper(generation);
			int spacing = -lower + upper;
			if (spacing > minSpacing) {
				minSpacing = spacing;
			}
			generation++;
		}
		parent.offsets.add(minSpacing);
		generation = 0;
		while (true) {
			if (!parent.bounds.has(generation + 1) && !child.bounds.has(generation)) {
				// tree ends at this generation, so stop
				break;
			}

			if (parent.bounds.has(generation + 1) && !child.bounds.has(generation)) {
				// previous lower is still correct
			} else if (!parent.bounds.has(generation + 1) && child.bounds.has(generation)) {
				int lower = child.bounds.getLower(generation);
				int upper = child.bounds.getUpper(generation);
				parent.bounds.add(-minSpacing + upper, -minSpacing + lower);
			} else {
				int value = -minSpacing + child.bounds.getLower(generation);
				parent.bounds.updateLower(generation + 1, value);
			}
			generation++;
		}
	}

	public static void position(Node parent) {
		if (parent.parent == null) {
			// top left corner
			parent.p = new Point(0, parent.d.height/2);
		}
		for (int i = 0; i < parent.children.size(); i++) {
			Node child = parent.children.get(i);
			int x = parent.p.x + parent.d.width;
			int y = parent.p.y - parent.d.height/2 + child.d.height/2 + parent.bounds.getUpper(1) - parent.offsets.get(i);
			child.p = new Point(x, y);
			position(parent.children.get(i));
		}
	}
}
