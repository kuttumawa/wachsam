<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
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
var objectoSistema="Alert";
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
function downloadResults(){
	var url= "BuscarObjectCSV?object="+objectoSistema+"&filter="+JSON.stringify($('#formFilter').serializeObject());
	window.location.href = url;
		
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
    theader.append($("<th/>").text("peligro"));
    theader.append($("<th/>").text("lugar"));
    theader.append($("<th/>").text("tipo"));
    theader.append($("<th/>").text("fecha"));
    theader.append($("<th/>").text("texto"));   
    table.append(theader);
    $.each(data, function(rowIndex, d) {
        var row = $("<tr/>");
        row.addClass('pointer');
        row.click(function(){fecthAlert(d.id)});
        row.append($("<td/>").text(d.id));
        row.append($("<td/>").text(d.nombre));
        row.append($("<td/>").text(d.peligro.nombre));
        if(d.lugarObj){ row.append($("<td/>").text(d.lugarObj.nombre))}
        else{ row.append($("<td/>").text("--"))};
        row.append($("<td/>").text(d.tipo)); 
        row.append($("<td/>").text(d.fechaPubFormatted)); 
        row.append($("<td/>").text(d.texto));         
        table.append(row);
    });
    return container.append(table);
}

function getAllpeligros(){
	var url= "PeligroServletJSON?&oper=getAll";
	$.get(url,function (data){
		$.each(data, function(i, item) {
			$('#peligroId_formFilter').append($('<option>', { 
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
	getAllpeligros();
	getAllLugares();
});
function fecthAlert(id){
	var url= "AlertServletJSON?&oper=getAlert&id="+id;
	$.get(url,function (data){
		loadFields(data);		
	},"json");		
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
<label class="col-sm-2 control-label"  for="nombre">texto</label>
<div class="col-sm-4">
<input type="text" class="form-control input-sm" name="texto" id="texto_formFilter"/>
</div>
</div>

<div  class="form-group form-group-sm">
<label class="col-sm-2 control-label"  for="lugarId">peligro</label>
<div class="col-sm-4">
<select class="form-control" class="form-control input-sm" name="peligroId" id="peligroId_formFilter">
		<option value=""></option>		
</select>
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


<%
class Tipo{
	public String id;
	public String texto;
	public Tipo(String id,String texto){
		this.id=id;
		this.texto=texto;
	}
}	
	List<Tipo> tipos = new ArrayList<Tipo>();
	tipos.add(new Tipo("justInfo","Just Information you may ignore"));
	tipos.add(new Tipo("informativa","Level 1 Watch, Practice Usual Precautions"));
	tipos.add(new Tipo("normal","Level 2 Alert, Practice Enhanced Precautions"));
	tipos.add(new Tipo("severa","Level 3 Warning, Avoid Nonessential Travel"));
	
%>


<div  class="form-group form-group-sm">
<label class="col-sm-2 control-label"  for="tipoId">Tipo</label>
<div class="col-sm-4">
<select class="form-control" name="tipoId" id="tipoId_formFilter">
<option value=""></option>
<%
  for(Tipo tipo_i:tipos){
      out.println("<option value=\""+tipo_i.id+"\">"+tipo_i.texto+"</option>");
  }
%>
</select> 
</div>
</div>

<div  class="form-group form-group-sm">
<label class="col-sm-2 control-label"  for="tipoId">Fecha Desde</label>
<div class="col-sm-4">
<input class="form-control input-sm" type="date" id="fechaPub" name="fechaPubDesde" value=""/>
</div>
</div>

<div  class="form-group form-group-sm">
<label class="col-sm-2 control-label"  for="caducidad"></label>
<div class="checkbox col-sm-4">
<label><input type="checkbox" name="caducidad" value="" checked>Todas</label>
<label><input type="checkbox" name="caducidad" value="nocaducadas">No caducadas</label>
<label><input type="checkbox" name="caducidad" value="caducadas">Caducadas</label>
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