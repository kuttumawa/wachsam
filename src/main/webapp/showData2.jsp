<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %> 
    



<style>
.fila {cursor: pointer}

</style>


<script>
var objetoId;
var objetoTipo;
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
		    		 $('#dataResultado').show();
		    	}else{		    		
		    	  	fectchData();
		    	  	cleanFormData();		    	  	    	    
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
		    	cleanFormData();
		    }
		  });
		  return false;
}

function fectchData(){
var url= "DataServletJSON?objetoId="+objetoId+"&objetoTipo="+objetoTipo+"&oper=getAllForObject";

$.get(url,function (data){
	$('#datatbodyID').children().remove();
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
	    
	    $('#datatbodyID').append(row);
	});
	$("#datatbodyID td").on("click",modifyData);
	if(callMeAfterFunction)callMeAfterFunction(data);
	
},"json");

	
}
function objetoTipoToString(item){
	return item.connectToId + "-" + item.objetoConnectedTipoString;
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


function modifyData(e){
	
	var dataId =  $(e.target.closest("tr")).prop("id");
	var url="DataServletJSON?oper=getData&dataId="+dataId;
	$.getJSON(url,function(data){
        console.log(data);
        populateFormData(data);
		});	
	
}
function newData(){
	cleanFormData();
	$('#tipoValor').val('TEXTO');
		
}

function cleanFormData(){
	 $('#dataId').val('');
	 $('#tag').val('');
	 $('#value').val('');
	 $('#tipoValor').val('');
	 $('#descripcion').val('');
	 $('#objetoConnectedTipo').val('');
	 $('#objetoConnectedId').val('');
	 $('#dataResultado').text('');
	 $('#dataResultado').hide();
}
function populateFormData(data){
	 $('#dataId').val(data.id);
	 $('#tag').val(data.tag.id);
	 $('#value').val(data.value);
	 $('#tipoValor').val(data.tipoValor);
	 $('#descripcion').val(data.descripcion);
     $('#objetoConnectedId').val(data.connectToId);
	 $('#objetoConnectedTipo').val(data.objetoConnectedTipo);	
	 $('#dataResultado').text('');
	 $('#dataResultado').hide();
	 
	 tipoValorFunc();
}
function tipoValorFunc(){
	if($('#tipoValor').val()==='OBJETO_ID'){
		$("#objetoDiv").show();
	}else{
		$("#objetoDiv").hide();
	}
}
function iniData(){
	fectchData();
	getAllTags();
	$("#objetoDiv").hide();
	$("#tipoValor").on("change",tipoValorFunc);
};
</script>

 
 
      <div id="dataResultado" class="alert alert-danger" style="display:none"></div>
      <form id="formData" action="ProvisionalDataUpdaterForYou" method="post" role="form">

        <input type="hidden" name="objetoId" id="objetoId" value="" readonly/>
        <input type="hidden" name="objetoTipo" id="objetoTipo" value="" readonly/>
        <input type="hidden" name="dataId" id="dataId" readonly/>
        
        <div class="form-group">
		<label for="">Tag</label>
		<select class="form-control input-sm" name="tag" id="tag">
		   <option value=""></option>
		</select>
		</div>
        		

		<div class="form-group">
		<label for="">Descripción del Dato</label>
		<input class="form-control input-sm" type="text" id="descripcion" name="descripcion" value=""/>
		</div>


		<div class="form-group">
		<label for="">TipoValor</label>
		
		<select class="form-control input-sm" name="tipoValor" id="tipoValor">
		    <%
				for (DataValueTipo tipo : DataValueTipo.values()) {
					 out.println("<option value=\""+tipo.name()+"\">"+tipo.getDescripcion()+"</option>");
				}
			%>
		</select>
		</div>
		
         <div class="control-group" id="valorDiv">
                 <label for="">Valor</label><br>
                 <textarea class="form-control input-sm" name="value" id="value" cols="70" rows="1"></textarea>
         </div>

         <div class="controls form-inline" id="objetoDiv">

			<div class="form-group">
			<label for="">Objeto</label>
				<select class="form-control input-sm" name="objetoConnectedTipo" id="objetoConnectedTipo">
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
		    	<input class="form-control input-sm" type="text" name="objetoConnectedId" id="objetoConnectedId"/>		
			</div>
		
	
        </div>





<input type="hidden" id="oper" name="oper"/>

<div class="modal-footer" id="dataButtonsId">
        <input type="button" class="btn btn-primary" value="grabar" onclick="savedata()">
        <input type="button" class="btn btn-primary" value="delete" onclick="deletedata()">
        <input type="button" class="btn btn-primary" value="nuevo" onclick="newData()">
</div>
</form>

<table class="table table-striped small">
<tr><th>id</th><th>tag</th><th>valor</th><th>Connected to</th></tr>
<tbody id="datatbodyID">
</tbody>
</table>













