<!DOCTYPE html>
<meta charset="utf-8">
<style>
.link {
  stroke: #ccc;
}

.node text {
  stroke-width: .1px;
  pointer-events: none;
  font: 10px sans-serif;
}
</style>
<div style="height:30px;font-size: 0.8em;" id="infoGraph"></div>
<div id="chart"></div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.17/d3.min.js"></script>
<script>

var width = 500,
    height = 500;

//Set up the colour scale
var colores_g = ["#3366cc", "#dc3912", "#ff9900", "#109618", "#990099", "#0099c6", "#dd4477", "#66aa00", "#b82e2e", "#316395", "#994499", "#22aa99", "#aaaa11", "#6633cc", "#e67300", "#8b0707", "#651067", "#329262", "#5574a6", "#3b3eac"];
//var color = d3.scale.category20();
var color = d3.scale.ordinal().domain([0,15]).range(colores_g);

var nodes = [],
    links = [];

var force = d3.layout.force();

var svg = d3.select("#chart").append("svg")
.attr("width", width)
.attr("height", height);

var node = svg.selectAll(".node"),
    link = svg.selectAll(".link");



function refresh(){
	var url="DataServletJSON?objetoId="+objetoId+"&objetoTipo="+objetoTipo+"&oper=getNodesAndLinks";
	d3.json(url, function(error, graph) {
		force.nodes(graph.nodes)
	    .links(graph.links)
	    .charge(-400)
	    .linkDistance(120)
	    .size([width, height])
	    .on("tick", tick);
		start();
     });
}

function start() {
      link = link.data(force.links(), function(d) { return d.source.id + "-" + d.target.id; });
	  link.enter().insert("line", ".node").attr("class", "link");
	  link.exit().remove();

	  node = node.data(force.nodes(), function(d) { return d.id;});
	  node.enter().append("circle").attr("class", function(d) { return "node " + d.id; }).attr("r", 8);
	  node.exit().remove();
}

function tick() {
	  link.attr("x1", function (d) {
	        return d.source.x;
	    })
	        .attr("y1", function (d) {
	        return d.source.y;
	    })
	        .attr("x2", function (d) {
	        return d.target.x;
	    })
	        .attr("y2", function (d) {
	        return d.target.y;
	    });
	    d3.selectAll("circle").attr("cx", function (d) {
	        return d.x;
	    })
	        .attr("cy", function (d) {
	        return d.y;
	    });
	    d3.selectAll("text").attr("x", function (d) {
	        return d.x;
	    })
	        .attr("y", function (d) {
	        return d.y;
	    });
}

</script>