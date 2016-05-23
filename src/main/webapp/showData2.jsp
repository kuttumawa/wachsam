<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %> 
    






<script>
var objetoId = <%= request.getParameter("objetoId")%>
var objetoTipo = <%= request.getParameter("objetoTipo")%>

function savedata(){
	 $("#oper").val("save");
	 $.ajax({
		    type: "POST",
		    url: "DataServletJSON",
		    data: $("#formData").serialize(),
		    success: function(data) {
		    	alert(data);
		    }
		  });
		  return false;
}

function fectchData(){
var url= "DataServletJSON?objetoId="+objetoId+"&objetoTipo="+objetoTipo+"&oper=getAllForObject";
//var url="http://localhost:8080/wachsam/DataServletJSON?objetoId=1376424326&objetoTipo=0&oper=getAllForObject";
$.get(url,function (data){
	console.log(data);
	$.each(data, function(i, item) {
	    alert(item);
	    var row = $('<tr></tr>').addClass('bar');
	    row.append('<td>'+ item.id+'</td>');
	    row.append('<td><span data-toggle=\'tooltip\' data-placement=\'top\' title=\''+ item.tag.descripcion +'\'>'+item.tag.nombre+'</td>');
	    row.append('<td>'+ item.value+'</td>');
        if(item.connectToId){
	      row.append('<td>'+ item.connectToId+'-'+item.objetoConnectedTipo+'</td>');
        }else{
        	row.append('<td></td>');
        }  
	   
	    $('#tbodyID').append(row);
	});
	
},"json");

	
}

function getAllAlerts(){
	var url= "AlertServletJSON?&oper=getAll";
	$.get(url,function (data){
		console.log(data);
		$.each(data, function(i, item) {
			$('#objetoConnectedSelect').append($('<option>', { 
		        value: item.id,
		        text : item.nombre 
		    }));
		});
		
	},"json");		
	}
function getAllPeligros(){
	var url= "PeligroServletJSON?&oper=getAll";
	$.get(url,function (data){
		console.log(data);
		$.each(data, function(i, item) {
			$('#objetoConnectedSelect').append($('<option>', { 
		        value: item.id,
		        text : item.nombre 
		    }));
		});
		
	},"json");		
	}

function getAllTags(){
	var url= "DataServletJSON?&oper=getAllTags";
	$.ajax({
	    url: url,
	    type: 'GET',
	    success: function(data){ 
	    	$.each(data, function(i, item) {
				$('#tagSelect').append($('<option>', { 
			        value: item.id,
			        text : item.nombre 
			    }));
			});
	    },
	    error: function(data) {
	        alert(data); //or whatever
	    }
	});
		
	}


function loadSelect(){
	getAllAlerts();
}

fectchData();
loadSelect();
getAllTags();
</script>

<h3>Data</h3>     

<table class="table table-striped small">
<tr><th>id</th><th>tag</th><th>valor</th><th>Connected to</th></tr>
<tbody id="tbodyID">
</tbody>
</table>

<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Nuevo</button>

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
 <form id="formData" action="ProvisionalDataUpdaterForYou" method="post" role="form">
        <input type="objetoId" name="objetoId" value="<%= request.getParameter("objetoId")%>"/>
        <input type="objetoTipo" name="objetoTipo" value="<%= request.getParameter("objetoTipo")%>"/>
        
        <div class="form-group">
		<label for="">Tag</label>
		<select class="form-control" name="tag" id="tagSelect">
		   <option value=""></option>
		</select>
		</div>
        
		<div class="form-group">
		<label for="">Valor</label>
		<input class="form-control" type="text" id="value" name="value" value=""/>
		</div>

		<div class="form-group">
		<label for="">Descripción del Dato</label>
		<input class="form-control" type="text" id="descripcion" name="descripcion" value=""/>
		</div>


		<div class="form-group">
		<label for="">TipoValor</label>
		
		<select class="form-control" name="tipoValor" id="tipo">
		    <option value="VACIO" >VACÍO</option>
			<option value="NUMERICO" >NUMÉRICO</option>
			<option value="TEXTO" >TEXTO</option>
			<option value="DATE" >DATE</option>
		</select>
		</div>


		<div class="form-group">
		<label for="">Objeto to Connect</label>
		
			<select class="form-control" name="objetoConnectedTipo" id="objetoConnectedTipo">
				<option value="0">Alert</option>
				<option value="1">Peligro</option>
				<option value="2">Lugar</option>
				<option value="3">Factor</option>
				<option value="4">Sitio</option>
				<option value="5">Fuente</option>
				<option value="6">Airport</option>
				<option value="7">Usuario</option>
			</select>
		</div>

		<div class="form-group">
		<label for="">ObjetoConnected to</label>
		
		<select class="form-control" name="objetoConnectedId" id="objetoConnectedSelect">
		<option value=""></option>
		
		</select>
		</div>






<input type="hidden" id="oper" name="oper"/>

</form>
</div>
      <div class="modal-footer">
        <input type="submit" class="btn btn-primary" value="grabar" onclick="savedata()">
        <input type="button" class="btn btn-primary" value="delete" onclick="deleteOper()">
        <input type="button" class="btn btn-primary" value="limpiar" onclick="clearFields()">
      </div>
</div>

</div>
</div>

