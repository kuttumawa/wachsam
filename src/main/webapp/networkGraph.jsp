
<!DOCTYPE html>
<meta charset="utf-8">
<!-- about: http://www.coppelia.io/2014/07/an-a-to-z-of-extra-features-for-the-d3-force-layout/ -->
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.17/d3.min.js"></script>
<div style="height:30px;font-size: 0.8em;" id="infoGraph"></div>
<div id="chart"></div>
<script>
//Constants for the SVG
var width = 500,
    height = 500;

//Set up the colour scale
var colores_g = ["#3366cc", "#dc3912", "#ff9900", "#109618", "#990099", "#0099c6", "#dd4477", "#66aa00", "#b82e2e", "#316395", "#994499", "#22aa99", "#aaaa11", "#6633cc", "#e67300", "#8b0707", "#651067", "#329262", "#5574a6", "#3b3eac"];
//var color = d3.scale.category20();
var color = d3.scale.ordinal().domain([0,15]).range(colores_g);
//Set up the force layout
var force = d3.layout.force()
    .charge(-220)
    .linkDistance(200)
    .size([width, height]);

//Append a SVG to the body of the html page. Assign this SVG as an object to svg
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

var url="DataServletJSON?objetoId="+objetoId+"&objetoTipo="+objetoTipo+"&oper=getNodesAndLinks";
function refresh(){
	
d3.json(url, function(error, graph) {
	if (error) throw error;
	//Creates the graph data structure out of the json data
	force.nodes(graph.nodes)
	    .links(graph.links)
	    .start();

//Create all the line svgs but without locations yet
var link = svg.selectAll(".link")
    .data(graph.links)
    .enter().append("line")
    .attr("class", "link")
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
 



var node = svg.selectAll(".node")
.data(graph.nodes)
.enter().append("g")
.attr("class", "node").on("mouseover", function(d) {
	$('#infoGraph').text(d.text);
})
.on("mouseout", function() {
	$('#infoGraph').text('');
})
.call(force.drag);



node.append("circle")
.attr("r", 8)
.style("fill", function (d) {
return color(d.group);
});



node.append("text")
      .attr("dx", 10)
      .attr("dy", ".35em")
      .text(function(d) { return d.name })
      .style("stroke", "gray");
//Now we are giving the SVGs co-ordinates - the force layout is generating the co-ordinates which this code is using to update the attributes of the SVG elements
force.on("tick", function () {
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
});

});
}
</script>