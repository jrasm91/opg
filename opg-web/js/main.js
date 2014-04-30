var display = {
	width: 960,
	height: 500,
	makeGraphic: function(node) {
		return new Graphic(node);
	},
	initialize: function(data) {
		Spacer.space(data[0]);
		Spacer.position(data[0]);

		display.svgContainer = d3.select("body").append("svg")
			.attr("width", 200)
			.attr("height", 200);

		display.svgGroup = display.svgContainer.append('g')
			.attr('transform', 'translate(' + display.width / 2 + ', ' + (display.height / 2) + ')');

		for (var i = 0; i < data.length; i++) {
			display.makeGraphic(data[i]);
		}
	}
};

// var data = Generator.dummyFullDesc().toList();
// var data = Generator.dummyFullAnce(5).toList();
// var data = Generator.dummyRandomDesc(3, 5).toList();
var data = Generator.dummyRandomAnce().toList();
// console.log(data);
// console.log(data[0]);

display.initialize(data);