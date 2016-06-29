// Closure to create a private scope for the charting function.
var barcodeChart = function() {

    // Definition of the chart variables.
    var width = 600,
        height = 30,
        margin = {top: 5, right: 5, bottom: 5, left: 5};
    var value = function(d) { 
    	return new Date(d); 
    	};
    var timeInterval = d3.time.month;

    // Charting function.
    function chart(selection) {
        selection.each(function(data) {
            // Bind the dataset to the svg selection.
            var div = d3.select(this),
                svg = div.selectAll('svg').data([data]);
          
            // Create the svg element on enter, and append a
            // background rectangle to it.
            svg.enter()
            .append('svg')
            .call(svgInit);
            // Select the containing group
            var g = svg.select('g.chart-content'),
            lines = g.selectAll('line');
         // Compute the first and last dates of the time interval
            var lastDate = d3.max(data, value);
            lastDate = lines.empty() ? lastDate : d3.max(lines.data(), value);
             var firstDate = timeInterval.offset(lastDate, -1);  
         // Compute the horizontal scale.
            var xScale = d3.time.scale()
                .domain([firstDate, lastDate])
                .range([0, width - margin.left - margin.right]);

          

            // Bind the data to the lines selection.
            var bars = g.selectAll('line')
                .data(data,value);
            bars.enter().append('line')
            .attr('x1',  function(d) { return xScale(value(d));})
            .attr('x2', function(d) { return xScale(value(d));})
            .attr('y1', 0)
            .attr('y2', height - margin.top - margin.bottom)
            .attr('stroke', '#000')
            .attr('stroke-opacity', 0.5);
            
            // Update the position of the bars.
            bars.transition()
            .duration(300)
            .attr('x1', function(d) { return xScale(value(d)); })
            .attr('x2', function(d) { return xScale(value(d)); });
            
            bars.exit().transition()
            .duration(300)
            .attr('stroke-opacity', 0)
            .remove();

        });
    }
    function svgInit(svg) {
        // Set the SVG size
        svg
            .attr('width', width)
            .attr('height', height);

        // Create and translate the container group
        var g = svg.append('g')
            .attr('class', 'chart-content')
            .attr('transform', 'translate(' + [margin.top, margin.left] + ')');

        // Add a background rectangle
        g.append('rect')
            .attr('width', width - margin.left - margin.right)
            .attr('height', height - margin.top - margin.bottom)
            .attr('fill', 'white');
    };
    chart.width = function(value) {
        if (!arguments.length) { return width; }
        width = value;
        // Returns the chart to allow method chaining.
        return chart;
    };
    chart.height = function(value) {
        if (!arguments.length) { return height; }
        height = value;
        // Returns the chart to allow method chaining.
        return chart;
    };
    chart.margin = function(value) {
        if (!arguments.length) { return margin; }
        margin = value;
        // Returns the chart to allow method chaining.
        return chart;
    };
    chart.value = function(accessorFunction) {
        if (!arguments.length) { return value; }
        value = accessorFunction;
        return chart;
    };
    chart.timeInterval = function(value) {
        if (!arguments.length) { return timeInterval; }
        timeInterval = value;
        return chart;
    };
    
  
    return chart;
};

