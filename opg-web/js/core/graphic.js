function Graphic(node) {
	this.node = node;
	var s = utils.getWindowScalar();
	this.graphic = display.svgGroup.append('g')
		.data([this])
		.attr('class', 'graphic')
		.attr('id', this.node.id)
		.style('font-size', (s * 100) + '%')

	this.graphic.append('rect')
		.style('fill', 'none')
		.style('stroke', 'none')

	this.graphic.append('rect')
		.attr('class', 'graphic ' + this.node.gender.substring(0, 1))
		.attr('width', this.node.size.drawing_height)
		.attr('height', this.node.size.drawing_width)
		.attr('rx', Graphic.CORNER_RADIUS)
		.attr('ry', Graphic.CORNER_RADIUS)
		.attr('x', this.node.point.y)
		.attr('y', this.node.point.x)
	// .on('mouseover', Graphic.focus)
	.on('click', utils.getD3CancelFunction())
}

Graphic.setConstants = function() {
	var s = utils.getWindowScalar()

	Graphic.HALF_WIDTH = 125 * s
	Graphic.HALF_HEIGHT = 25 * s

	Graphic.HALF_HORIZ_SPACING = 15 * s
	Graphic.HALF_VERT_SPACING = 15 * s

	Graphic.WIDTH = Graphic.HALF_WIDTH * 2
	Graphic.HEIGHT = Graphic.HALF_HEIGHT * 2

	Graphic.HORIZ_SPACING = Graphic.HALF_HORIZ_SPACING * 2
	Graphic.VERT_SPACING = Graphic.HALF_VERT_SPACING * 2
	Graphic.DESCENDANCY_VERT_SPACING = 10 * s

	Graphic.HALF_HORIZ_OFFSET = Graphic.HALF_WIDTH + Graphic.HALF_HORIZ_SPACING
	Graphic.HALF_VERT_OFFSET = Graphic.HALF_HEIGHT + Graphic.HALF_VERT_SPACING
	Graphic.ARC_SIZE = 6 * s
	Graphic.CORNER_RADIUS = 5 * s
	Graphic.DESCENDANCY_ARC_SIZE = Graphic.ARC_SIZE * 2.5

	Graphic.HORIZ_OFFSET = Graphic.WIDTH + Graphic.HORIZ_SPACING
	Graphic.VERT_OFFSET = Graphic.HEIGHT + Graphic.VERT_SPACING
	Graphic.DESCENDANCY_VERT_OFFSET = Graphic.HEIGHT + Graphic.DESCENDANCY_VERT_SPACING

	Graphic.NOB_SIZE = 8 * s
	Graphic.DESCENDANCY_NOB_OFFSET = 20 * s

	Graphic.SHOW_DESCENDANCY_BUTTON_WIDTH = 140 * s
	Graphic.SHOW_DESCENDANCY_BUTTON_HEIGHT = 21 * s
}

Graphic.setConstants();