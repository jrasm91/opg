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
		type: "Ancestry",
		gender: "Male",
		size: {
			height: 50,
			width: 100
		}
	});
}

function Point(x, y){
	return {
		x: x,
		y: y
	}
}

function zeroChildTest() {
	var root = makeRoot();
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
};

function oneChildMaleTest() {
	var root = makeRoot();
	var child = root.makeNode(Gender.Male);
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25, 50, 0]));
	testBounds(child.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
	testPoint(child.point, new Point(100, 50));
};

function oneChildFemaleTest() {
	var root = makeRoot();
	var child = root.makeNode(Gender.Female);
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25, 0, -50]));
	testBounds(child.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
	testPoint(child.point, new Point(100, 0));
};

function twoChildTest() {
	var root = makeRoot();
	var child1 = root.makeNode(Gender.Male);
	var child2 = root.makeNode(Gender.Female);
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25, 50, -50]));
	testBounds(child1.bounds, new Bounds([25, -25]));
	testBounds(child2.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
	testPoint(child1.point, new Point(100, 50));
	testPoint(child2.point, new Point(100, 0));
};

function unbalancedTest1() {
	var root = makeRoot();
	var child1 = root.makeNode(Gender.Male);
	var child2 = root.makeNode(Gender.Female);
	var child11 = child1.makeNode(Gender.Male);
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25, 50, -50, 75, 25]));
	testBounds(child1.bounds, new Bounds([25, -25, 50, 0]));
	testBounds(child2.bounds, new Bounds([25, -25]));
	testBounds(child11.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
	testPoint(child1.point, new Point(100, 50));
	testPoint(child2.point, new Point(100, 0));
	testPoint(child11.point, new Point(200, 75));
}

function unbalancedTest2() {
	var root = makeRoot();
	var child1 = root.makeNode(Gender.Male);
	var child2 = root.makeNode(Gender.Female);
	var child11 = child1.makeNode(Gender.Female);
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25, 50, -50, 25, -25]));
	testBounds(child1.bounds, new Bounds([25, -25, 0, -50]));
	testBounds(child2.bounds, new Bounds([25, -25]));
	testBounds(child11.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
	testPoint(child1.point, new Point(100, 50));
	testPoint(child2.point, new Point(100, 0));
	testPoint(child11.point, new Point(200, 25));
}

function unbalancedTest3() {
	var root = makeRoot();
	var child1 = root.makeNode(Gender.Male, null);
	var child2 = root.makeNode(Gender.Female, null);
	var child21 = child2.makeNode(Gender.Male, null);
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25, 50, -50, 25, -25]));
	testBounds(child1.bounds, new Bounds([25, -25]));
	testBounds(child2.bounds, new Bounds([25, -25, 50, 0]));
	testBounds(child21.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
	testPoint(child1.point, new Point(100, 50));
	testPoint(child2.point, new Point(100, 0));
	testPoint(child21.point, new Point(200, 25));
}

function unbalancedTest4() {
	root = makeRoot();
	child1 = root.makeNode(Gender.Male, null);
	child2 = root.makeNode(Gender.Female, null);
	child21 = child2.makeNode(Gender.Female, null);
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25, 50, -50, -25, -75]));
	testBounds(child1.bounds, new Bounds([25, -25]));
	testBounds(child2.bounds, new Bounds([25, -25, 0, -50]));
	testBounds(child21.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
	testPoint(child1.point, new Point(100, 50));
	testPoint(child2.point, new Point(100, 0));
	testPoint(child21.point, new Point(200, -25));
}

function longFemaleTest1() {
	root = makeRoot();
	child1 = root.makeNode(Gender.Female, null);
	child11 = child1.makeNode(Gender.Female, null);
	child111 = child11.makeNode(Gender.Female, null);
	Spacer.space(root);
	Spacer.position(root);
	testBounds(root.bounds, new Bounds([25, -25, 0, -50, -25, -75, -50, -100]));
	testBounds(child1.bounds, new Bounds([25, -25, 0, -50, -25, -75]));
	testBounds(child11.bounds, new Bounds([25, -25, 0, -50]));
	testBounds(child111.bounds, new Bounds([25, -25]));
	testPoint(root.point, new Point(0, 25));
	testPoint(child1.point, new Point(100, -0));
	testPoint(child11.point, new Point(200, -25));
	testPoint(child111.point, new Point(300, -50));
};

function test(actual, expected) {
	if (actual !== expected) {
		throw new Error(">>> FAILED: " + actual + ' != ' + expected);
	} else {
		// console.log(">PASSED");
	}
}

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
}

function testPoint(p1, p2) {
	test(p1.x, p2.x);
	test(p1.y, p2.y);
}

function run() {
	console.log('ANCE: Starting Tests...');
	zeroChildTest();
	oneChildMaleTest();
	oneChildFemaleTest();
	twoChildTest();
	unbalancedTest1();
	unbalancedTest2();
	unbalancedTest3();
	unbalancedTest4();
	longFemaleTest1();
	console.log('ANCE: Finished, All Tests Passed!');
}

run();