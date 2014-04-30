var DEFAULT_BOUND = {
	upper: 0,
	lower: 0
};

function Bounds(nums) {
	this.limits = [];
	for (var i = 0; nums && i < nums.length; i += 2) {
		this.add(nums[i], nums[i + 1]);
	}
}

Bounds.prototype.has = function(index) {
	return index < this.limits.length;
}

Bounds.prototype.get = function(generation) {
	return this.limits[generation];
}

Bounds.prototype.add = function(upper, lower) {
	this.limits.push({
		upper: upper,
		lower: lower
	});
}

Bounds.prototype.addBounds = function(bounds) {
	for(var i = 0; i < bounds.limits.length; i++){
		this.limits.push({
			upper: bounds.get(i).upper,
			lower: bounds.get(i).lower
		});
	}
}

Bounds.prototype.clear = function() {
	this.limits = [];
}

Bounds.prototype.shift = function(amount) {
	for (var i = 1; i < this.limits.length; i++) {
		this.limits[i] = {
			upper: this.limits[i].upper + amount,
			lower: this.limits[i].lower + amount
		};
	}
}

Bounds.prototype.update = function(generation, upper, lower) {
	this.limits[generation].upper = upper;
	this.limits[generation].lower = lower;
}

Bounds.prototype.updateLower = function(generation, lower) {
	this.limits[generation].lower = lower;
}

Bounds.prototype.updateUpper = function(generation, upper) {
	this.limits[generation].upper = upper;
}

Bounds.prototype.span = function() {
	var span = this.limits[0] || DEFAULT_BOUND;
	for (var i in this.limits) {
		if (this.limits[i].lower < span.lower) {
			span.lower = this.limits[i].lower;
		}
		if (this.limits[i].upper > span.upper) {
			span.upper = this.limits[i].upper;
		}
	}
	return span;
}

Bounds.prototype.getTotalHeight = function(){
	var span = this.span();
	return span.upper - span.lower;
}