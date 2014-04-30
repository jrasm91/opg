package opg.spacer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Dimension;
import java.awt.Point;

import opg.spacer.Node.NodeGender;
import opg.spacer.Node.NodeType;

import org.junit.BeforeClass;
import org.junit.Test;

public class AncestorTest {

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

	private NodeType type = NodeType.Ancestor;

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
	public void oneChildMaleTest() {
		Node root = Node.makeRoot(type);
		Node child = root.makeNode(NodeGender.Male, null);
		Spacer.space(root);
		Spacer.position(root);
		assertEqualBounds(root.bounds, new Bounds(25, -25, 50, 0));
		assertEqualBounds(child.bounds, new Bounds(25, -25));
		assertEqualPosition(root.p, new Point(0, 25));
		assertEqualPosition(child.p, new Point(100, 50));
	}
	
	@Test
	public void oneChildFemaleTest() {
		Node root = Node.makeRoot(type);
		Node child = root.makeNode(NodeGender.Female, null);
		Spacer.space(root);
		Spacer.position(root);
		assertEqualBounds(root.bounds, new Bounds(25, -25, 0, -50));
		assertEqualBounds(child.bounds, new Bounds(25, -25));
		assertEqualPosition(root.p, new Point(0, 25));
		assertEqualPosition(child.p, new Point(100, 0));
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
	public void unbalancedTest1() {
		Node root = Node.makeRoot(type);
		Node child1 = root.makeNode(NodeGender.Male, null);
		Node child2 = root.makeNode(NodeGender.Female, null);
		Node child11 = child1.makeNode(NodeGender.Male, null);
		Spacer.space(root);
		Spacer.position(root);
		assertEqualBounds(root.bounds, new Bounds(25, -25, 50, -50, 75, 25));
		assertEqualBounds(child1.bounds, new Bounds(25, -25, 50, 0));
		assertEqualBounds(child2.bounds, new Bounds(25, -25));
		assertEqualBounds(child11.bounds, new Bounds(25, -25));
		assertEqualPosition(root.p, new Point(0, 25));
		assertEqualPosition(child1.p, new Point(100, 50));
		assertEqualPosition(child2.p, new Point(100, 0));
		assertEqualPosition(child11.p, new Point(200, 75));
	}
	
	@Test
	public void unbalancedTest2() {
		Node root = Node.makeRoot(type);
		Node child1 = root.makeNode(NodeGender.Male, null);
		Node child2 = root.makeNode(NodeGender.Female, null);
		Node child11 = child1.makeNode(NodeGender.Female, null);
		Spacer.space(root);
		Spacer.position(root);
		assertEqualBounds(root.bounds, new Bounds(25, -25, 50, -50, 25, -25));
		assertEqualBounds(child1.bounds, new Bounds(25, -25, 0, -50));
		assertEqualBounds(child2.bounds, new Bounds(25, -25));
		assertEqualBounds(child11.bounds, new Bounds(25, -25));
		assertEqualPosition(root.p, new Point(0, 25));
		assertEqualPosition(child1.p, new Point(100, 50));
		assertEqualPosition(child2.p, new Point(100, 0));
		assertEqualPosition(child11.p, new Point(200, 25));
	}
	
	@Test
	public void unbalancedTest3() {
		Node root = Node.makeRoot(type);
		Node child1 = root.makeNode(NodeGender.Male, null);
		Node child2 = root.makeNode(NodeGender.Female, null);
		Node child21 = child2.makeNode(NodeGender.Male, null);
		Spacer.space(root);
		Spacer.position(root);
		assertEqualBounds(root.bounds, new Bounds(25, -25, 50, -50, 25, -25));
		assertEqualBounds(child1.bounds, new Bounds(25, -25));
		assertEqualBounds(child2.bounds, new Bounds(25, -25, 50, 0));
		assertEqualBounds(child21.bounds, new Bounds(25, -25));
		assertEqualPosition(root.p, new Point(0, 25));
		assertEqualPosition(child1.p, new Point(100, 50));
		assertEqualPosition(child2.p, new Point(100, 0));
		assertEqualPosition(child21.p, new Point(200, 25));
	}
	
	@Test
	public void unbalancedTest4() {
		Node root = Node.makeRoot(type);
		Node child1 = root.makeNode(NodeGender.Male, null);
		Node child2 = root.makeNode(NodeGender.Female, null);
		Node child21 = child2.makeNode(NodeGender.Female, null);
		Spacer.space(root);
		Spacer.position(root);
		assertEqualBounds(root.bounds, new Bounds(25, -25, 50, -50, -25, -75));
		assertEqualBounds(child1.bounds, new Bounds(25, -25));
		assertEqualBounds(child2.bounds, new Bounds(25, -25, 0, -50));
		assertEqualBounds(child21.bounds, new Bounds(25, -25));
		assertEqualPosition(root.p, new Point(0, 25));
		assertEqualPosition(child1.p, new Point(100, 50));
		assertEqualPosition(child2.p, new Point(100, 0));
		assertEqualPosition(child21.p, new Point(200, -25));
	}
	
	@Test
	public void longFemaleTest1() {
		Node root = Node.makeRoot(type);
		Node child1 = root.makeNode(NodeGender.Female, null);
		Node child11 = child1.makeNode(NodeGender.Female, null);
		Node child111 = child11.makeNode(NodeGender.Female, null);
		Spacer.space(root);
		Spacer.position(root);
		assertEqualBounds(root.bounds, new Bounds(25, -25, 0, -50, -25, -75, -50, -100));
		assertEqualBounds(child1.bounds, new Bounds(25, -25, 0, -50, -25, -75));
		assertEqualBounds(child11.bounds, new Bounds(25, -25, 0, -50));
		assertEqualBounds(child111.bounds, new Bounds(25, -25));
		assertEqualPosition(root.p, new Point(0, 25));
		assertEqualPosition(child1.p, new Point(100, -0));
		assertEqualPosition(child11.p, new Point(200, -25));
		assertEqualPosition(child111.p, new Point(300, -50));
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
