function Vector2D(x, y) {
	if (typeof y === 'undefined') {
		if (typeof x === 'undefined') {
			// default values
			this.x = 0
			this.y = 0
		} else {
			// first is Vector2D
			this.x = x.x
			this.y = x.y
		}
	} else if (typeof x === 'number') {
		// both numbers
		this.x = x
		this.y = y
	} else {
		// both Vector2D (x = to, y = from)
		this.x = y.x - x.x
		this.y = y.y - x.y
	}

	return this
}

Vector2D.ZERO = new Vector2D()
Vector2D.X_AXIS = new Vector2D(1, 0)
Vector2D.Y_AXIS = new Vector2D(0, 1)

Vector2D.distance = function(x1, y1, x2, y2) {
	var dx = x1 - x2
	var dy = y1 - y2

	return Math.sqrt(dx * dx + dy * dy)
}

Vector2D.prototype.getCopy = function() {
	return new Vector2D(this.x, this.y)
}

Vector2D.prototype.copyValues = function(x, y) {
	if (typeof y === 'undefined') {
		// x is a Vector2D
		this.x = x.x
		this.y = x.y
	} else {
		this.x = x
		this.y = y
	}

	return this
}

Vector2D.prototype.add = function(x, y) {
	if (typeof y === 'undefined') {
		this.x += x.x
		this.y += x.y
	} else {
		this.x += x
		this.y += y
	}

	return this
}

Vector2D.prototype.getAddedCopy = function(dx, dy) {
	if (typeof dy === 'undefined')
		return new Vector2D(this.x + dx.x, this.y + dx.y)
	else
		return new Vector2D(this.x + dx, this.y + dy)
}

Vector2D.prototype.subtract = function(dx, dy) {
	if (typeof dy === 'undefined') {
		this.x -= dx.x
		this.y -= dx.y
	} else {
		this.x -= dx
		this.y -= dy
	}

	return this
}

Vector2D.prototype.getSubtractedCopy = function(dx, dy) {
	if (typeof dy === 'undefined')
		return new Vector2D(this.x - dx.x, this.y - dx.y)
	else
		return new Vector2D(this.x - dx, this.y - dy)
}

Vector2D.prototype.distanceTo = function(other) {
	var dx = this.x - other.x
	var dy = this.y - other.y

	return Math.sqrt(dx * dx + dy * dy)
}

Vector2D.prototype.distanceSquaredTo = function(other) {
	var dx = this.x - other.x
	var dy = this.y - other.y

	return dx * dx + dy * dy
}

Vector2D.prototype.length = function() {
	return Math.sqrt(this.x * this.x + this.y * this.y)
}

Vector2D.prototype.length = function() {
	return this.x * this.x + this.y * this.y
}

Vector2D.prototype.invert = function() {
	this.x *= -1
	this.y *= -1

	return this
}

Vector2D.prototype.getInvertedCopy = function() {
	return new Vector2D(this.x * -1, this.y * -1)
}

Vector2D.prototype.scale = function(scalar) {
	this.x *= scalar
	this.y *= scalar

	return this
}

Vector2D.prototype.getScaledCopy = function(scalar) {
	return new Vector2D(this.x * scalar, this.y * scalar)
}

Vector2D.prototype.limit = function(length) {
	var l = this.length()
	if (l > length) {
		var scalar = length / l
		return this.scale(scalar)
	} else
		return this
}

Vector2D.prototype.getLimitedCopy = function(length) {
	var l = this.length()
	if (l > length) {
		var scalar = length / l
		return this.getScaledCopy(scalar)
	} else
		return this.getCopy()
}

Vector2D.prototype.normalize = function() {
	return this.limit(1)
}

Vector2D.prototype.getNormalized = function() {
	return this.getLimitedCopy(1)
}

Vector2D.prototype.setLength = function(length) {
	var scalar = length / this.length()
	return this.scale(scalar)
}

Vector2D.prototype.getCopyOfLength = function(length) {
	var scalar = length / this.length()
	return this.getScaledCopy(scalar)
}

Vector2D.prototype.print = function() {
	return '(' + this.x + ',' + this.y + ')'
}