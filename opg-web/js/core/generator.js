var Generator = {
	size: {
		height: Graphic.WIDTH + Graphic.VERT_SPACING,
		width: Graphic.HEIGHT + Graphic.HORIZ_SPACING,
		drawing_width: Graphic.HEIGHT,
		drawing_height: Graphic.WIDTH
	},
	random_range: function(min, max) {
		return function() {
			return Math.floor(Math.random() * (max - min + 1)) + min;
		}
	},
	random_gender: function(type) {
		var count = 0;
		var next = function() {
			if (type === Node.ANCE) {
				return count++ % 2 === 0 ? Node.FEMALE : Node.MALE
			} else {
				return Math.floor(Math.random() * 2) == 0 ? Node.MALE : Node.FEMALE;
			}
		}
		return next;
	},
	dummyFullAnce: function(generations) {
		return Generator.dummyTree(2, generations || 4, Node.ANCE)
	},
	dummyFullDesc: function(children, generations) {
		return Generator.dummyTree(children || 3, generations || 3, Node.DESC)
	},
	dummyRandomAnce: function(generations) {
		return Generator.dummyTree(Generator.random_range(1, 2), generations || 4, Node.ANCE)
	},
	dummyRandomDesc: function(children, generations) {
		return Generator.dummyTree(Generator.random_range(1, children || 3), generations || 4, Node.DESC)
	},
	dummyTree: function(children, generations, type) {
		var queue = [];
		var root = new Node({
			type: type,
			gender: Node.MALE,
			size: Generator.size
		});
		queue.push(root);
		while (queue.length > 0) {
			var next = queue.shift();
			if (next.id.length > generations) {
				break;
			}
			var next_gender = Generator.random_gender(type);
			var limit = typeof children === 'function' ? children() : children;
			for (var i = 0; i < limit; i++) {
				queue.push(next.makeNode({
					gender: next_gender(),
					size: Generator.size
				}));
			}
		}
		return root;
	}
}