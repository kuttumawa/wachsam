
<%-- <jsp:include page="cabecera.jsp"/> --%>
<style>
.fuente {
font-style: italic;
}
.pointer{
cursor:pointer;
}
</style>
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
	if(data.totalResults){
		makeTable($('#resultado'),data.data);
		makePagination($('#resultado'),data);
		$('#resultado').append($('<div/>').addClass('fuente').text(data.data.length + ' ..de.. ' + data.totalResults));
		$('#downloadButtonId').show();	
	}else{
		$('#resultado').text("Sin Resultado");
		$('#downloadButtonId').hide();	 
	}
	$('#panelResult').show();		
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
        row.addClass('pointer');
        row.click(function(){fecthSitio(d.id)});
        row.append($("<td/>").text(d.id));
        row.append($("<td/>").text(d.nombre));
        if(d.lugarObj){ row.append($("<td/>").text(d.lugarObj.nombre))}
        else{ row.append($("<td/>").text("--"))};
        row.append($("<td/>").text(d.direccion));       
        table.append(row);
    });
    return container.append(table);
}

function getAllTipoSitio(){
	var url= "SitioServletJSON?&oper=getAllTipoSitio";
	$.get(url,function (data){
		$.each(data, function(i, item) {
			$('#tipoId_formFilter').append($('<option>', { 
		        value: item.id,
		        text : item.nombre 
		    }));
		});
		
	},"json");		
	}
function getAllLugares(){
	var url= "LugarServletJSON?&oper=getAll";
	$.get(url,function (data){
		$.each(data, function(i, item) {
			$('#lugarId_formFilter').append($('<option>', { 
		        value: item.id,
		        text : item.nombre 
		    }));
		});
		
	},"json");		
	}
$(function(){
	getAllTipoSitio();
	getAllLugares();
});
function fecthSitio(id){
	var url= "SitioServletJSON?&oper=getSitio&id="+id;
	$.get(url,function (data){
		loadFields(data);		
	},"json");		
	}

function downloadResults(){
	var url= "BuscarObjectCSV?object="+objectoSistema+"&filter="+JSON.stringify($('#formFilter').serializeObject());
	window.location.href = url;
		
}

</script>
<div class="container">
<form action="" role="form" id="formFilter" class="form-horizontal">

<input type=hidden name="page" id="page" value="0"/>
<input type=hidden name="order"/>

<div  class="form-group form-group-sm">
<label class="col-sm-2 control-label"  for="id">id</label>
<div class="col-sm-4">
<input type="text" class="form-control input-sm" name="id" id="id_formFilter"/>
</div>
</div>

<div  class="form-group form-group-sm">
<label class="col-sm-2 control-label"  for="nombre">nombre</label>
<div class="col-sm-4">
<input type="text" class="form-control input-sm" name="nombre" id="nombre_formFilter"/>
</div>
</div>

<div  class="form-group form-group-sm">
<label class="col-sm-2 control-label"  for="lugarId">lugar</label>
<div class="col-sm-4">
<select class="form-control" class="form-control input-sm" name="lugarId" id="lugarId_formFilter">
		<option value=""></option>		
</select>
</div>
</div>

<div  class="form-group form-group-sm">
<label class="col-sm-2 control-label"  for="tipoId">Tipo</label>
<div class="col-sm-4">
<select class="form-control" name="tipoId" id="tipoId_formFilter">
		<option value=""></option>		
</select>
</div>
</div>

<div class="btn-group center-block" style="margin-bottom:35px">
<input class="btn btn-primary btn-sm" value="Buscar" type="button" onclick="buscar()">
</div>

</form>

<div class="panel panel-default" style="display:none" id="panelResult">
<div class="panel-body" id="resultado"></div>
</div>
<button type="button" class="btn btn-primary btn-sm" id="downloadButtonId" onclick="downloadResults()" style="display:none">
      <span class="glyphicon glyphicon-download-alt"></span>
</button>
</div>