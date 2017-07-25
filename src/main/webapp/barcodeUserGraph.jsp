<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.17/d3.min.js"></script>
<script src="js/vz/barcode/barcode.js"></script>


<div class="chart-example" id="chart"></div>
<script>
var barcode = barcodeChart();



$.ajax({
    type: "GET",
    contentType: "application/json; charset=utf-8",
    url: "SecurityServletJSON?oper=accesosPorUsuarioUltimoMes",
    dataType: "json",
    async: true,
    data: "", 
    success: function (data) {
    	drawBarcode(data);

    },
    error: function (result) {
           }
});

function drawBarcode(data){
	
	
	//Create a table element.
	var table = d3.select('#chart').selectAll('table')
	    .data([data])
	    .enter()
	    .append('table')
	    .attr('class', 'table table-condensed');
	//Append the table head and body to the table.
	var tableHead = table.append('thead'),
	    tableBody = table.append('tbody');
	//Add the table header content.
	tableHead.append('tr').selectAll('th')
	    .data(['', 'Actividad últimos 30 días'])
	    .enter()
	    .append('th')
	    .text(function(d) { return d; });
	//Add the table body rows.
	var rows = tableBody.selectAll('tr')
	    .data(data)
	    .enter()
	    .append('tr');
	//Add the stock name cell.
	var cname=rows.append('td')
	    .text(function(d) { return d.name; });
	//Add the barcode chart.
	rows.append('td').
	    datum(function(d) { return d.mentions; })
	    .call(barcode);
  
}

</script>
