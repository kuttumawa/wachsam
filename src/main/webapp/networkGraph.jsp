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
var force = d3.layout.force().
charge(-400)
.linkDistance(120)
.size([width, height])
.on("tick", tick);
var svg = d3.select("#chart").append("svg")
.attr("width", width)
.attr("height", height);
svg.append("defs").selectAll("marker")
.data(["suit", "licensing", "resolved"])
.enter().append("marker")
.attr("id", function(d) { return d; })
.attr("viewBox", "0 -5 10 10")
.attr("refX", 25)
.attr("refY", 0)
.attr("markerWidth", 6)
.attr("markerHeight", 6)
.attr("orient", "auto")
.append("path")
.attr("d", "M0,-5L10,0L0,5 L10,0 L0, -5")
.style("stroke", "#4679BD")
.style("opacity", "0.6");
var node = svg.selectAll(".node"),
    link = svg.selectAll(".link");
function refresh(){
	var url="DataServletJSON?objetoId="+objetoId+"&objetoTipo="+objetoTipo+"&oper=getNodesAndLinks";
	d3.json(url, function(error, graph) {
		force.nodes(graph.nodes)
	    .links(graph.links).start();
	    
	    console.log(graph.nodes);
	    console.log(graph.links);
		start();
     });
}
function start() {
     
	   node = node.data(force.nodes(), function(d) { return d.id;});
	   node.exit().remove();
	   var group=node.enter().append("g")
			  .attr("class", "node").on("mouseover", function(d) {
			  	$('#infoGraph').text(d.text);
			  })
			  .on("mouseout", function() {
			  	$('#infoGraph').text('');
			  })
			  .call(force.drag);
		
		group.append("circle")
			  .attr("r", 8)
			  .style("fill", function (d) {
			  return color(d.group);
			  });
		group.append("text")
		.attr("dx", 10)
		.attr("dy", ".35em")
		.text(function(d) { return d.name; })
		.style("stroke", "gray");
		
		link = link.data(force.links(), function(d) { return d.source.id + "-" + d.target.id; });
		link.enter().insert("line", ".node").attr("class", "link")
		.style("marker-end",  "url(#suit)")
		.style("stroke", function (d) {
		     return color(d.value);
		})
		.style("stroke-dasharray",function(d){return "3,3"})
		.on("mouseover", function(d) {	   
		    $('#infoGraph').text('Tag: '+d.text);
		}).on("mouseout", function() {
			$('#infoGraph').text('');
		}); 
		link.exit().remove();
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
	    d3.selectAll(".node") .attr("transform", function(d){return "translate("+d.x+","+d.y+")";});
	  
	   
}
</script>