<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %> 
    



<style>
.fila {cursor: pointer}
</style>


<script>
var objetoId = <%= request.getParameter("objetoId")%>;
var objetoTipo = <%= request.getParameter("objetoTipo")%>;
var callMeAfterFunction;

function savedata(){
	if(!$('#tipoValor').val()==='OBJETO_ID'){
		$("#objetoDiv").show();
		$('#objetoConnectedTipo').val('');
		$('#objetoConnectedId').val('');
	}
	 $.ajax({
		    type: "POST",
		    url: "DataServletJSON?oper=save&objetoId="+objetoId,
		    data: $("#formData").serialize(),
		    success: function(data) {
		    	if(data.error){
		    		 $('#dataResultado').text(data.messages);			  
		    	}else{
		    	  	fectchData();
		    	    $('#myModal').modal('hide');
			    }    
		    }
		  });
		  return false;
}
function deletedata(){
	 $.ajax({
		    type: "POST",
		    url: "DataServletJSON?oper=delete",
		    data: $("#formData").serialize(),
		    success: function(data) {
		    	fectchData();
		    	$('#myModal').modal('hide');
		    }
		  });
		  return false;
}

function fectchData(){
var url= "DataServletJSON?objetoId="+objetoId+"&objetoTipo="+objetoTipo+"&oper=getAllForObject";

$.get(url,function (data){
	$('#tbodyID').children().remove();
	$.each(data, function(i, item) {
	    var row = $('<tr id=\''+item.id+'\'></tr>').addClass('fila');
	    row.append('<td>'+ item.id+'</td>');
	    row.append('<td><span data-toggle=\'tooltip\' data-placement=\'top\' title=\''+ item.tag.descripcion +'\'>'+item.tag.nombre+'</td>');
	    row.append('<td>'+ item.value+'</td>');
        if(item.connectToId){
	        row.append('<td>'+objetoTipoToString(item)+'</td>');
        }else{
        	row.append('<td></td>');
        }  
	    
	    $('#tbodyID').append(row);
	});
	$("#tbodyID td").on("click",modifyData);
	if(callMeAfterFunction)callMeAfterFunction(data);
	
},"json");

	
}
function objetoTipoToString(item){
	return item.connectToId + "-" + item.objetoConnectedTipoString;
}


function getAllAlerts(comboid){
	var key="getAllAlerts";
    var alerts=getFromStorage(key);
	if(alerts){
		$.each(alerts, function(i, item) {
			$('#objetoConnectedId').append($('<option>', { 
		        value: item.id,
		        text : item.text 
		    }));  
		});
	}else{
		var url= "AlertServletJSON?&oper=getAll";
		$.get(url,function (data){
			setInStorage(key,data)
			$.each(data, function(i, item) {
				$('#objetoConnectedId').append($('<option>', { 
			        value: item.id,
			        text : item.text 
			    }));
			});
			
		},"json");
	}	
	if(comboid) $('#objetoConnectedId').val(comboid);	
	}
function getAllPeligros(comboid){
	var url= "PeligroServletJSON?&oper=getAll";
	$.get(url,function (data){
		$.each(data, function(i, item) {
			$('#objetoConnectedId').append($('<option>', { 
		        value: item.id,
		        text : item.nombre 
		    }));
		});
		if(comboid) $('#objetoConnectedId').val(comboid);
	},"json");		
	}
function getAllLugares(comboid){
	var url= "LugarServletJSON?&oper=getAll";
	$.get(url,function (data){
		$.each(data, function(i, item) {
			$('#objetoConnectedId').append($('<option>', { 
		        value: item.id,
		        text : item.nombre 
		    }));
		});
		if(comboid) $('#objetoConnectedId').val(comboid);
	},"json");		
	}
function getAllSitio(comboid){
	var url= "SitioServletJSON?&oper=getAll";
	$.get(url,function (data){
		$.each(data, function(i, item) {
			$('#objetoConnectedId').append($('<option>', { 
		        value: item.id,
		        text : item.nombre 
		    }));
		});
		if(comboid) $('#objetoConnectedId').val(comboid);
	},"json");		
	}
function getAllFuente(comboid){
	var url= "FuenteServletJSON?&oper=getAll";
	$.get(url,function (data){
		$.each(data, function(i, item) {
			$('#objetoConnectedId').append($('<option>', { 
		        value: item.id,
		        text : item.nombre 
		    }));
		});
		if(comboid) $('#objetoConnectedId').val(comboid);
	},"json");		
	}
function getAllAirport(comboid){
	var url= "AirportServletJSON?&oper=getAll";
	$.get(url,function (data){
		$.each(data, function(i, item) {
			$('#objetoConnectedId').append($('<option>', { 
		        value: item.id,
		        text : item.nombre 
		    }));
		});
		if(comboid) $('#objetoConnectedId').val(comboid);
	},"json");		
	}
function getAllUsuario(comboid){
	var url= "UsuarioServletJSON?&oper=getAll";
	$.get(url,function (data){
		$.each(data, function(i, item) {
			$('#objetoConnectedId').append($('<option>', { 
		        value: item.id,
		        text : item.nombre 
		    }));
		});
		if(comboid) $('#objetoConnectedId').val(comboid);
	},"json");		
	}
function getAllTags(){
	var url= "DataServletJSON?&oper=getAllTags";
	$.ajax({
	    url: url,
	    type: 'GET',
	    success: function(data){ 
	    	$.each(data, function(i, item) {
				$('#tag').append($('<option>', { 
			        value: item.id,
			        text : item.nombre 
			    }));
			});
	    },
	    error: function(data) {
	        alert(data); 
	    }
	});
		
	}


function loadObjetoConnectedSelect(comboid){
	$('#objetoConnectedId').children().remove();
	$('#objetoConnectedId').append($('<option>', { 
        value: '',
        text : '' 
    }));
	var objeto =$('#objetoConnectedTipo').val();
	if(objeto == 0) getAllAlerts();
	else if(objeto == 1) getAllPeligros(comboid);
	else if(objeto == 2) getAllLugares(comboid);
	else if(objeto == 3) getAllFactores(comboid);
	else if(objeto == 4) getAllSitio(comboid);
	else if(objeto == 5) getAllFuente(comboid);
	else if(objeto == 6) getAllAirport(comboid);
	else if(objeto == 7) getAllUsuario(comboid);
}
function modifyData(e){
	$('#myModal').modal('show');
	console.log($(e.target.closest("tr")).prop("id"));
	var dataId = $(e.target.closest("tr")).prop("id");
	var url="DataServletJSON?oper=getData&dataId="+dataId;
	$.getJSON(url,function(data){
        console.log(data);
        populateForm(data);
		});	
	
}
function newData(){
	cleanForm();
	$('#tipoValor').val('TEXTO');
	$('#myModal').modal('show');		
}
function paintGraph(){
	$('#modalGraph').modal('show');
	refresh();		
}
function cleanForm(){
	 $('#dataId').val('');
	 $('#tag').val('');
	 $('#value').val('');
	 $('#tipoValor').val('');
	 $('#descripcion').val('');
	 $('#objetoConnectedTipo').val('');
	 $('#objetoConnectedId').val('');	
}
function populateForm(data){
	 $('#dataId').val(data.id);
	 $('#tag').val(data.tag.id);
	 $('#value').val(data.value);
	 $('#tipoValor').val(data.tipoValor);
	 $('#descripcion').val(data.descripcion);
     $('#objetoConnectedId').val(data.connectToId);
	 $('#objetoConnectedTipo').val(data.objetoConnectedTipo);	
	 tipoValorFunc();
}
function tipoValorFunc(){
	if($('#tipoValor').val()==='OBJETO_ID'){
		$("#objetoDiv").show();
	}else{
		$("#valorDiv").show();
		$("#objetoDiv").hide();
	}
}
$(function(){
	fectchData();
	getAllTags();
	$("#objetoDiv").hide();
	$("#tipoValor").on("change",tipoValorFunc);
});
</script>

<h3>Data<span style="align:right" class=""></span></h3>     

<table class="table table-striped small">
<tr><th>id</th><th>tag</th><th>valor</th><th>Connected to</th></tr>
<tbody id="tbodyID">
</tbody>
</table>

<button type="button" class="btn btn-primary btn-sm" onclick="newData()">
      <span class="glyphicon glyphicon-plus-sign"></span>
</button>
<button type="button" class="btn btn-primary btn-sm" onclick="paintGraph()">
      <span class="glyphicon glyphicon-link"></span> 
</button>


<!-- Modal -->
<div id="modalGraph" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Red de Conexiones</h4>
      </div>
      <div class="modal-body">
            <jsp:include page="networkGraph.jsp"/>
      </div>
      <div class="modal-footer">
       
      </div>
  </div>
</div>
</div>


<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Nuevo Dato</h4>
      </div>
      <div class="modal-body">
      <div id="dataResultado"></div>
      <form id="formData" action="ProvisionalDataUpdaterForYou" method="post" role="form">
        <input type="hidden" name="objetoId" value="<%= request.getParameter("objetoId")%>" readonly/>
        <input type="hidden" name="objetoTipo" value="<%= request.getParameter("objetoTipo")%>" readonly/>
        <input type="hidden" name="dataId" id="dataId" readonly/>
        
        <div class="form-group">
		<label for="">Tag</label>
		<select class="form-control" name="tag" id="tag">
		   <option value=""></option>
		</select>
		</div>
        		

		<div class="form-group">
		<label for="">Descripción del Dato</label>
		<input class="form-control" type="text" id="descripcion" name="descripcion" value=""/>
		</div>


		<div class="form-group">
		<label for="">TipoValor</label>
		
		<select class="form-control" name="tipoValor" id="tipoValor">
		    <%
				for (DataValueTipo tipo : DataValueTipo.values()) {
					 out.println("<option value=\""+tipo.name()+"\">"+tipo.getDescripcion()+"</option>");
				}
			%>
		</select>
		</div>
		
         <div class="control-group" id="valorDiv">
                 <label for="">Valor</label><br>
                 <textarea class="form-control" name="value" id="value" cols="70" rows="1"></textarea>
         </div>

         <div class="controls form-inline" id="objetoDiv">

			<div class="form-group">
			<label for="">Objeto</label>
				<select class="form-control" name="objetoConnectedTipo" id="objetoConnectedTipo">
					<option value="0">Alert</option>
					<option value="1">Peligro</option>
					<option value="2">Lugar</option>
					<option value="3">Factor</option>
					<option value="4">Sitio</option>
					<option value="5">Fuente</option>
					<option value="6">Airport</option>
					<option value="7">Usuario</option>
					<option value="8">Riesgo</option>
					<option value="9">Recurso</option>
				</select>
			</div>
	
			<div class="form-group">
		    	<label for="">Objeto Id</label>		
		    	<input class="form-control" type="text" name="objetoConnectedId" id="objetoConnectedId"/>		
			</div>
		
	
        </div>





<input type="hidden" id="oper" name="oper"/>

</form>
</div>
      <div class="modal-footer">
        <input type="submit" class="btn btn-primary" value="grabar" onclick="savedata()">
        <input type="button" class="btn btn-primary" value="delete" onclick="deletedata()">
      </div>
</div>

</div>
</div>



