Spacer = {}

Spacer.calculateMinimumSpacing = function(parent, child) {
	var minSpacing = child.size.height;
	var generation = 0;
	while (true) {
		if (!parent.bounds.has(generation + 1) || !child.bounds.has(generation)) {
			break;
		}
		var lower = parent.bounds.get(generation + 1).lower;
		var upper = child.bounds.get(generation).upper;
		var spacing = -lower + upper;
		if (spacing > minSpacing) {
			minSpacing = spacing;
		}
		generation++;
	}
	return minSpacing;
}

Spacer.combineChildTree = function(parent, child) {
	var generation = 0;
	var minSpacing = Spacer.calculateMinimumSpacing(parent, child);
	parent.offsets.push(minSpacing);
	while (true) {
		if (!parent.bounds.has(generation + 1) && !child.bounds.has(generation)) {
			// tree ends at this generation, so stop
			break;
		}
		if (parent.bounds.has(generation + 1) && !child.bounds.has(generation)) {
			// previous lower is still correct
		} else if (!parent.bounds.has(generation + 1) && child.bounds.has(generation)) {
			var upper = child.bounds.get(generation).upper;
			var lower = child.bounds.get(generation).lower;
			parent.bounds.add(-minSpacing + upper, -minSpacing + lower);
		} else {
			var value = -minSpacing + child.bounds.get(generation).lower;
			parent.bounds.updateLower(generation + 1, value);
		}
		generation++;
	}
}

Spacer.space = function(n) {
	n.bounds.clear();
	n.offsets = []
	for (var i = 0; i < n.children.length; i++) {
		Spacer.space(n.children[i]);
	}
	n.bounds.add(n.size.height / 2, -n.size.height / 2);
	if (n.children.length == 1) {
		var child = n.children[0];
		n.bounds.addBounds(child.bounds);
		if (n.type === Node.ANCE) {
			if (child.gender === Node.MALE) {
				n.bounds.shift(child.size.height / 2);
			} else if (child.gender === Node.FEMALE) {
				n.bounds.shift(-child.size.height / 2);
			}
		}
		n.offsets.push(0);
	} else if (n.children.length > 1) {
		// copy first child limits
		n.offsets.push(0);
		n.bounds.addBounds(n.children[0].bounds);
		// merge children trees into parent tree
		for (var i = 1; i < n.children.length; i++) {
			Spacer.combineChildTree(n, n.children[i]);
		}
		// center parent among children
		n.bounds.shift((n.bounds.get(1).upper - n.bounds.get(1).lower) / 2 - n.bounds.get(1).upper);
	}
	// update children offset relative to parent height
	for (var i = 0; i < n.offsets.length; i++) {
		n.offsets[i] = n.offsets[i] + n.size.height / 2;
	}
}

Spacer.position = function(parent) {
	if (!parent.parent) {
		parent.point = {
			x: 0,
			y: parent.size.height / 2 || 0
		}
	}
	for (var i = 0; i < parent.children.length; i++) {
		var child = parent.children[i];
		child.point = {
			x : parent.point.x + parent.size.width,
			y : parent.point.y - parent.size.height / 2 + child.size.height / 2 + parent.bounds.get(1).upper - parent.offsets[i]
		};
		Spacer.position(parent.children[i]);
	}
}

Spacer.run = function(root) {
	Spacer.space(root);
	Spacer.position(root);
}