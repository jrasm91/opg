var Gender = {
	Male: {
		gender: "Male"
	},
	Female: {
		gender: "Female"
	}
}

var makeRoot = function() {
	return new Node({
		id: "R",
		type: "Descendency",
		size: {
			height: 50,
			width: 100
		}
	});
};

function Point(x, y) {
	return {
		x: x,
		y: y
	}
};

function zeroChildTest() {
	var root = makeRoot();
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
};

function oneChildTest() {
	var root = makeRoot();
	var child = root.makeNode();
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25, 25, -25]));
	testBounds(child.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
	testPoint(child.point, new Point(100, 25));
};

function twoChildTest() {
	var root = makeRoot();
	var child1 = root.makeNode();
	var child2 = root.makeNode();
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25, 50, -50]));
	testBounds(child1.bounds, new Bounds([25, -25]));
	testBounds(child2.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
	testPoint(child1.point, new Point(100, 50));
	testPoint(child2.point, new Point(100, 0));
};

function threeChildTest() {
	var root = makeRoot();
	var child1 = root.makeNode();
	var child2 = root.makeNode();
	var child3 = root.makeNode();
	Spacer.space(root);
	Spacer.position(root);
	testBounds(child1.bounds, new Bounds([25, -25]));
	testBounds(child2.bounds, new Bounds([25, -25]));
	testBounds(child3.bounds, new Bounds([25, -25]));
	testBounds(root.bounds, new Bounds([25, -25, 75, -75]));

	testPoint(root.point, new Point(0, 25));
	testPoint(child1.point, new Point(100, 75));
	testPoint(child2.point, new Point(100, 25));
	testPoint(child3.point, new Point(100, -25));
};

function fourChildTest() {
	var root = makeRoot();
	var child1 = root.makeNode();
	var child2 = root.makeNode();
	var child3 = root.makeNode();
	var child4 = root.makeNode();
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25, 100, -100]));
	testBounds(child1.bounds, new Bounds([25, -25]));
	testBounds(child2.bounds, new Bounds([25, -25]));
	testBounds(child3.bounds, new Bounds([25, -25]));
	testBounds(child4.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
	testPoint(child1.point, new Point(100, 100));
	testPoint(child2.point, new Point(100, 50));
	testPoint(child3.point, new Point(100, 0));
	testPoint(child4.point, new Point(100, -50));
};

function unbalancedTest1() {
	var root = makeRoot();
	var child1 = root.makeNode();
	var child2 = root.makeNode();
	var child11 = child1.makeNode();
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25, 50, -50, 50, 0]));
	testBounds(child1.bounds, new Bounds([25, -25, 25, -25]));
	testBounds(child2.bounds, new Bounds([25, -25]));
	testBounds(child11.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
	testPoint(child1.point, new Point(100, 50));
	testPoint(child2.point, new Point(100, 0));
	testPoint(child11.point, new Point(200, 50));
};

function test(actual, expected) {
	if (actual !== expected) {
		throw new Error(">>> FAILED: " + actual + ' != ' + expected);
	} else {
		// console.log(">PASSED");
	}
};

function testBounds(l1, l2) {
	generation = 0;
	while (true) {
		if (l1.has(generation) && l2.has(generation)) {
			var bound1 = l1.get(generation);
			var bound2 = l2.get(generation);
			test(bound1.upper, bound2.upper);
			test(bound1.lower, bound2.lower);
		} else {
			test(l1.has(generation), l2.has(generation));
			break;
		}
		generation++;
	}
};

function testPoint(p1, p2) {
	test(p1.x, p2.x);
	test(p1.y, p2.y);
};

function run() {
	console.log('DESC: Starting Tests...');
	zeroChildTest();
	oneChildTest();
	twoChildTest();
	threeChildTest();
	fourChildTest();
	unbalancedTest1();
	console.log('DESC: Finished, All Tests Passed!');
};

run();