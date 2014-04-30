package opg.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import opg.other.Chart;
import opg.other.Utility;
import opg.spacer.Node;
import opg.spacer.Node.NodeType;
import opg.spacer.Spacer;

public class DrawableChart implements IDrawable {

	private List<IDrawable> objects;
	private int dx, dy;

	public DrawableChart(Chart chart) {
		objects = new LinkedList<IDrawable>();
		
		System.out.println("Making Tree...");
		long timeStart = System.nanoTime();
		Node root = Utility.makeGedcomTree();
		
		Spacer.space(root);
		Spacer.position(root);
		
		for (Node n : root) {
			objects.add(n.toDrawablePerson());
			objects.addAll(drawLines(n));
		}
		
		long timeEnd = System.nanoTime();
		System.out.println("Finished: " + ((timeEnd - timeStart) / 1000000000f) + " seconds");
		
		dx = 100;
		dy = -root.getAbsoluteLower();
		
		System.out.println(dx + ", " + dy);
		chart.setAvaliableHeight(root.getTotalHeight());
	}

	private List<DrawableLine> drawLines(Node n) {
		int distance = 5; // proportional to margin
		List<DrawableLine> lines = new LinkedList<DrawableLine>();
		if (n.getChildren().size() == 1) {
			Node c = n.getFirstChild();
			Point startParent = new Point(n.p.x + n.d.width - distance, n.p.y + n.d.height/2);
			Point endParent = new Point(n.p.x + n.d.width, n.p.y + n.d.height/2);
			Point startChild = new Point(c.p.x + distance, c.p.y + c.d.height/2);
			Point endChild= new Point(c.p.x, c.p.y  + c.d.height/2);
			lines.add(new DrawableLine(startParent, endParent));
			lines.add(new DrawableLine(startChild, endChild));
			if (n.type == NodeType.Ancestor) {
				lines.add(new DrawableLine(endChild, endParent));
			}
		} else if (n.getChildren().size() > 1) {
			for (Node child : n.getChildren()) {
				Point startChild = new Point(n.p.x + n.d.width, child.p.y  + child.d.height/2);
				Point endChild = new Point(n.p.x + n.d.width + distance, child.p.y + child.d.height/2);
				lines.add(new DrawableLine(startChild, endChild));
			}
			Node c = n.getFirstChild();
			Node l = n.getLastChild();
			Point startLong = new Point(n.p.x + n.d.width, c.p.y + c.d.height/2);
			Point endLong = new Point(n.p.x + n.d.width, l.p.y + l.d.height/2);
			lines.add(new DrawableLine(startLong, endLong));

			Point startParent = new Point(n.p.x + n.d.width, n.p.y + n.d.height/2);
			Point endParent = new Point(n.p.x + n.d.width - distance, n.p.y + n.d.height/2);
			lines.add(new DrawableLine(startParent, endParent));
		}
		return lines;
	}

	@Override
	public void draw(Graphics2D g) {
		for (IDrawable obj : objects) {
			obj.draw(g);
		}
	}

	public int getDx() {
		return dx;
	}	
	
	public int getDy() {
		return dy;
	}

}
