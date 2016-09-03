
<jsp:include page="cabecera.jsp"/>
<script>
var objectoSistema="Sitio";
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
function buscar(page){
	$('#page').val(page || 0);
	 $.ajax({
		    type:"POST",
		    url: "BuscarObject",
		    data:"object="+objectoSistema+"&filter="+JSON.stringify($('#formFilter').serializeObject()),
		    success: function(data) {
		    	paintData(data);
		    }
		  });
		  return false;
}
function paintData(data){
	$('#resultado').children().remove();
	makeTable($('#resultado'),data.data);
	makePagination($('#resultado'),data);	
}
function makePagination(container,data){
	var list = $("<ul/>").addClass('pagination pagination-sm');
	var INTERVAL=5;
	var desde=Math.max(0,data.currentpage-INTERVAL-Math.max(0,data.currentpage+INTERVAL-data.numpages));
    var hasta=Math.min(data.numpages,data.currentpage+INTERVAL);
	for(i = desde;i<= hasta;i++){
		 var li = $("<li/>");
		 if(data.currentpage==i) li.addClass('active');
		 var link=$("<a/>");		
		 li.append(link);
		 list.append(li);
		 if(i==hasta && hasta<data.numpages) link.attr('href','javascript:buscar('+i+')').text('..');
		 else{
			  link.attr('href','javascript:buscar('+i+')').text(i);			
		 }
	 };	
	 return container.append(list);	 
}
function makeTable(container, data) {
    var table = $("<table/>").addClass('table table-condensed  table-striped small');
    var theader=$("<tr/>");
    theader.append($("<th/>").text("id"));
    theader.append($("<th/>").text("nombre"));
    theader.append($("<th/>").text("lugar"));
    theader.append($("<th/>").text("dirección"));
    
    table.append(theader);
    $.each(data, function(rowIndex, d) {
        var row = $("<tr/>");
        row.append($("<td/>").text(d.id));
        row.append($("<td/>").text(d.nombre));
        if(d.lugarObj){ row.append($("<td/>").text(d.lugarObj.nombre))}
        else{ row.append($("<td/>").text("--"))};
        row.append($("<td/>").text(d.direccion));       
        table.append(row);
    });
    return container.append(table);
}

</script>
<div class="container">
<form action="" role="form" id="formFilter">

<input type=hidden name="page" id="page" value="0"/>
<input type=hidden name="order"/>

<div  class="form-group">
<label for="id">id</label>
<input type="text" class="form-control" name="id" id="id"/>
</div>
<div  class="form-group">
<label for="nombre">nombre</label>
<input type="text" class="form-control" name="nombre" id="nombre"/>
</div>
<div  class="form-group">
<label for="lugarId">lugarId</label>
<input type="text" class="form-control" name="lugarId" id="lugarId"/>
</div>
<div  class="form-group">
<label for="tipoId">tipoId</label>
<input type="text" class="form-control" name="tipoId" id="tipoId"/>
</div>
<div class="btn-group center-block" style="margin-bottom:20px">
<input class="btn btn-primary btn-sm" value="Buscar" type="button" onclick="buscar()">
</div>
</form>

<div class="panel panel-default">
<div class="panel-body" id="resultado"></div>
</div>

</div>