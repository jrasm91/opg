package opg.spacer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Dimension;
import java.awt.Point;

import opg.spacer.Node.NodeType;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jr2of6
 * 
 *         <pre>
 * 1. Build tree, 
 * 2. Space and position it
 * 3. Test for correct bound and position values
 * 
 *         <pre>
 */
public class DescendencyTest {

	private NodeType type = NodeType.Descendent;

	@BeforeClass
	public static void beforeClass(){
		Node.DEFAULT_SIZE = new Dimension(100, 50);
	}
	
	@Test
	public void zeroChildTest() {
		Node root = Node.makeRoot(type);
		Spacer.space(root);
		Spacer.position(root);
		assertEqualBounds(root.bounds, new Bounds(25, -25));
		assertEqualPosition(root.p, new Point(0, 25));
	}

	@Test
	public void oneChildTest() {
		Node root = Node.makeRoot(type);
		Node child = root.makeNode();
		Spacer.space(root);
		Spacer.position(root);
		assertEqualBounds(root.bounds, new Bounds(25, -25, 25, -25));
		assertEqualBounds(child.bounds, new Bounds(25, -25));
		assertEqualPosition(root.p, new Point(0, 25));
		assertEqualPosition(child.p, new Point(100, 25));
	}

	@Test
	public void twoChildTest() {
		Node root = Node.makeRoot(type);
		Node child1 = root.makeNode();
		Node child2 = root.makeNode();
		Spacer.space(root);
		Spacer.position(root);
		assertEqualBounds(root.bounds, new Bounds(25, -25, 50, -50));
		assertEqualBounds(child1.bounds, new Bounds(25, -25));
		assertEqualBounds(child2.bounds, new Bounds(25, -25));
		assertEqualPosition(root.p, new Point(0, 25));
		assertEqualPosition(child1.p, new Point(100, 50));
		assertEqualPosition(child2.p, new Point(100, 0));
	}

	@Test
	public void threeChildTest() {
		Node root = Node.makeRoot(type);
		Node child1 = root.makeNode();
		Node child2 = root.makeNode();
		Node child3 = root.makeNode();
		Spacer.space(root);
		Spacer.position(root);
		assertEqualBounds(child1.bounds, new Bounds(25, -25));
		assertEqualBounds(child2.bounds, new Bounds(25, -25));
		assertEqualBounds(child3.bounds, new Bounds(25, -25));
		assertEqualBounds(root.bounds, new Bounds(25, -25, 75, -75));
		
		assertEqualPosition(root.p, new Point(0, 25));
		assertEqualPosition(child1.p, new Point(100, 75));
		assertEqualPosition(child2.p, new Point(100, 25));
		assertEqualPosition(child3.p, new Point(100, -25));
	}

	@Test
	public void fourChildTest() {
		Node root = Node.makeRoot(type);
		Node child1 = root.makeNode();
		Node child2 = root.makeNode();
		Node child3 = root.makeNode();
		Node child4 = root.makeNode();
		Spacer.space(root);
		Spacer.position(root);
		assertEqualBounds(root.bounds, new Bounds(25, -25, 100, -100));
		assertEqualBounds(child1.bounds, new Bounds(25, -25));
		assertEqualBounds(child2.bounds, new Bounds(25, -25));
		assertEqualBounds(child3.bounds, new Bounds(25, -25));
		assertEqualBounds(child4.bounds, new Bounds(25, -25));
		assertEqualPosition(root.p, new Point(0, 25));
		assertEqualPosition(child1.p, new Point(100, 100));
		assertEqualPosition(child2.p, new Point(100, 50));
		assertEqualPosition(child3.p, new Point(100, 0));
		assertEqualPosition(child4.p, new Point(100, -50));
	}

	@Test
	public void unbalancedTest1() {
		Node root = Node.makeRoot(type);
		Node child1 = root.makeNode();
		Node child2 = root.makeNode();
		Node child11 = child1.makeNode();
		Spacer.space(root);
		Spacer.position(root);
		assertEqualBounds(root.bounds, new Bounds(25, -25, 50, -50, 50, 0));
		assertEqualBounds(child1.bounds, new Bounds(25, -25, 25, -25));
		assertEqualBounds(child2.bounds, new Bounds(25, -25));
		assertEqualBounds(child11.bounds, new Bounds(25, -25));
		assertEqualPosition(root.p, new Point(0, 25));
		assertEqualPosition(child1.p, new Point(100, 50));
		assertEqualPosition(child2.p, new Point(100, 0));
		assertEqualPosition(child11.p, new Point(200, 50));
	}

	private void assertEqualBounds(Bounds l1, Bounds l2) {
		int generation = 0;
		while (true) {
			if (l1.has(generation) && l2.has(generation)) {
				assertTrue(l1.getUpper(generation) == l2.getUpper(generation));
				assertTrue(l1.getLower(generation) == l2.getLower(generation));
			} else {
				if (l1.has(generation) != l2.has(generation)){
					System.out.println(l1);
					System.out.println(l2);
					fail();
				}
				break;
			}
			generation++;
		}
	}

	private void assertEqualPosition(Point p1, Point p2) {
		assertEquals(p1.x, p2.x);
		assertEquals(p1.y, p2.y);
	}
}
