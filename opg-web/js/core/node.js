Node.DEFAULT_SIZE = {
	height: 50,
	width: 100
};

Node.DEFAULT_POINT = {
	x: 0,
	y: 0
};

Node.MALE = "Male"
Node.FEMALE = "Female"
Node.UNKNOWN = "Unknown"
Node.DESC = "Descendency"
Node.ANCE = "Ancestry"
Node.DEFAULT_TYPE = Node.ANCE;

function Node(options) {
	this.gender = options.gender || Node.UNKNOWN;
	this.type = options.type || Node.DEFAULT_TYPE;
	this.size = options.size || Node.DEFAULT_SIZE;
	this.point = options.point || Node.DEFAULT_POINT;
	this.offsets = options.offsets || [];
	this.children = options.children || [];
	this.bounds = options.bounds || new Bounds();
	this.parent = options.parent || null;
	if (this.parent) {
		if (this.type === Node.ANCE && this.gender === Node.MALE) {
			this.parent.children.splice(0, 0, this);
		} else {
			this.parent.children.push(this);
		}
		this.id = this.parent.id + options.parent.children.length;
	} else {
		this.id = "R";
	}
}

Node.prototype.makeNode = function(options){
	var options = options || {};
	options.parent = this;
	return new Node(options);
}

Node.prototype.toList = function() {
	var nodes = [];
	var queue = []
	queue.push(this);
	while (queue.length > 0) {
		var next = queue.shift();
		nodes.push(next);
		queue.push.apply(queue, next.children || []);
	}
	return nodes;
}

// Node.prototype.getAbsoluteBounds = function(){
// 	return this.bounds.span;
// }


