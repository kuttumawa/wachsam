<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
    <%@ page import="es.io.wachsam.services.*"  %>
    <%@ page import="org.springframework.web.context.WebApplicationContext"  %> 
    <%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"  %> 
<html> 


<jsp:include page="cabecera.jsp"/>
<head>
<style>
.fila {cursor: pointer}
#modal1Id .modal-dialog  {width:75%;}
</style>

<script>
function modifyData(e){
	cleanForm();
	$('#error').hide();
	$('#modal1Id').modal('show');
	$('#modalFooterId').show();
	  
	var riesgoId = $(e).children().first().text();
	var url="RiesgoServletJSON?oper=getRiesgo&riesgoId="+riesgoId;
	$.getJSON(url,function(data){
        populateForm(data);
		});	
	 
	
}


function cleanForm(){
	 
	 $('#riesgoId').val('');
	 $('#lugarId').val('');
	 $('#nivelProbabilidadId').val('');
	 $('#caducidad').val('');
	 $('#fechaActivacion').val('');
	 $('#mesActivacion').val('');	 
	 $('#texto').val('');
	 $('#text').val('');
	 $('#formulaDisipacion').val('');
	 $("#desactivado").prop('checked', false);
	 $('#diaActivacion').val('');
	 $('#fechaAnual').text('');
}
function populateForm(data){
	 $('#riesgoId').val(data.id);
	 $('#peligroId').val(data.peligro.id);
	 $('#lugarId').val(data.lugar.id);
	 $('#nivelProbabilidadId').val(data.probabilidad);
	 $('#caducidad').val(data.caducidad);
	 if(!data.mesActivacion)$('#fechaActivacion').val(data.fechaActivacionFormatted);
	 else $('#fechaAnual').text(data.fechaActivacionFormatted);
	
	 $('#mesActivacion').val(data.mesActivacion);
	 $('#texto').val(data.texto);
	 $('#text').val(data.text);
	 $('#formulaDisipacion').val(data.formulaDisipacion);
	 if(data.desactivado){
		 $("#desactivado").prop('checked', true);
	 }else{
		 $("#desactivado").prop('checked', false);
	 }
	 $('#diaActivacion').val(data.diaActivacion);
	 if(data.mesActivacion){
		 $("#radio2").prop('checked', true);
	 }else{
		 $("#radio1").prop('checked', true);
	 }
	 disableRadio();
	 
}
function getAllLugares(){
	var url= "LugarServletJSON?&oper=getAll";
	$.get(url,function (data){
		$.each(data, function(i, item) {
			$('#lugarId').append($('<option>', { 
		        value: item.id,
		        text : item.nombre 
		    }));
		});
		
	},"json");		
	}
function getAllPeligros(){
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

function fectchRiesgosFromPeligro(){
$("#tituloId").text("Riesgo: " + $("#peligroId_formFilter :selected").text());
var url= "RiesgoServletJSON?peligroId="+$("#peligroId_formFilter").val()+"&oper=getAllForPeligro";

$.get(url,function (data){
	$('#tbodyID').children().remove();
	$.each(data, function(i, item) {
	    var row = $('<tr id=\''+item.id+'\' onclick=\'modifyData(this)\' \'></tr>').addClass('fila');
	    row.append('<td>'+ item.id+'</td>');
	    row.append('<td><span data-toggle=\'tooltip\' data-placement=\'top\' title=\''+ item.lugar.nombre +'\'>'+item.lugar.nombre+'</td>');
	    row.append('<td>'+ item.probabilidad+'</td>');
	    if(!item.mesActivacion){
	    	row.append('<td>'+ item.fechaActivacionFormatted+'</td>');
	    }else{
	    	row.append('<td>'+ item.fechaActivacionFormatted+'&nbsp;<i title="Repetición anual " class="material-icons md-18">replay</i></td>');
	    }
	    if(item.activo && item.diasParaCaducar<5 && item.diasParaCaducar>-1){
	    	row.append('<td>'+ item.diasParaCaducar+'['+item.caducidad+']<span data-toggle="tooltip" title="A punto de caducar" class="glyphicon glyphicon-hourglass"></td>');
	    }else{
	    	row.append('<td>'+(item.diasParaCaducar!=-1?item.diasParaCaducar:'')+'['+(item.caducidad!=-1?item.caducidad:'&infin;')+']</td>');
	    };
	    row.append('<td>'+ item.fechapub+'</td>');
	    row.append('<td>'+ item.valor+'</td>');
	  //tendencia
		if(item.tendencia.porcentaje>0){
		   row.append('<td><i title="'+item.tendencia.message+'" class="material-icons md-18">trending_up</i></td');				
		}else if(item.tendencia.porcentaje<0){
		   row.append('<td><i title="'+item.tendencia.message+'" class="material-icons md-18">trending_down</i></td');
		}else{
		   row.append('<td></td>');
		}
		if(item.activo){
			      row.append('<td><span data-toggle="tooltip" title="Activo" class="glyphicon glyphicon-ok"></td');
		}else{
			if(!item.desactivado && item.diasParaCaducar>item.caducidad){
				  row.append('<td><span data-toggle="tooltip" title="Programado" class="glyphicon glyphicon-time md-18"></td>');
			}else if(item.desactivado){
				  row.append('<td><span data-toggle="tooltip" title="Desactivado" class="glyphicon glyphicon-pause md-18"></td>');
			}else{
			      row.append('<td><span data-toggle="tooltip" title="Inactivo" class="glyphicon glyphicon-remove"></td>');
			}
		};
	    $('#tbodyID').append(row);
	});
	

	
},"json");

	
}
function savedata(){
	 $.ajax({
		    type: "POST",
		    url: "RiesgoServletJSON?oper=save",
		    data: $("#form2").serialize(),
		    success: function(data) {
               if(!data.error){
            	   fectchRiesgosFromPeligro();
			    	$('#modal1Id').modal('hide');
               }else{
               	$('#error').show();
               	var msgs="";
               	for(var i in data.messages){
               		msgs+=data.messages[i];
               		msgs+="; ";
               	};
               	$('#error').text(msgs);
				}		    	
		    }
		  });
		  return false;
}
function deletedata(){
	 $.ajax({
		    type: "POST",
		    url: "RiesgoServletJSON?oper=delete",
		    data: $("#form2").serialize(),
		    success: function(data) {
		    	fectchRiesgosFromPeligro();
		    	$('#modal1Id').modal('hide');		    	
		    }
		  });
		  return false;
}
$(function(){
	getAllLugares();
	getAllPeligros();
	$("#peligroId_formFilter").on("change",fectchRiesgosFromPeligro);
	$('#radio1,#radio2').on('click',disableRadio);
});
function openForm(){
	if(!$('#peligroId_formFilter').val()){
		alert("Seleccionar un Peligro");
		return;
	}else{
		$('#peligroId').val($('#peligroId_formFilter').val());
	}	
	cleanForm();
	$('#error').hide();
	$('#modal1Id').modal('show');
	$('#modalFooterId').show();
	
}

function disableRadio(){
	if($("#radio1").is(':checked')){
		$("#fechaActivacion").prop('disabled', false)
		$("#mesActivacion").prop('disabled', true);
		$("#diaActivacion").prop('disabled', true);
	}
    if($("#radio2").is(':checked')){
		$("#fechaActivacion").prop('disabled', true)
		$("#mesActivacion").prop('disabled', false);
		$("#diaActivacion").prop('disabled', false);
	}
	
}
</script>
</head>
<body>

<div class="container">
<div class="container-fluid">
  <div class="row">
    <div class="col-sm-12">


<form  class="form-inline" role="form">
<label class="control-label"  for="peligroId">Peligro</label>
<select class="form-control" class="form-control input-sm" name="peligroId" id="peligroId_formFilter">
		<option value=""></option>		
</select>
<button type="button" class="btn btn-primary btn-sm" onclick="openForm()">
		      <span class="glyphicon glyphicon-plus-sign"></span>
</button>
</form>

 <table class="table table-striped small">
 <thead>
 <tr><th>id</th><th>Lugar</th><th>Probabilidad</th><th>FechaActivación</th><th>caducidad</th><th>FechaCreación</th><th>Valor</th><th>Tendencia</th><th>Activo</th></tr>
 </thead>
 <tbody id="tbodyID">

</tbody>
</table>

</div>
</div>
</div>
</div>
</body> 
</html>

<div id="modal1Id" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"><span id="tituloId"></span></h4>
        <div id="error" class="alert alert-danger">
               
        </div>
      </div>
      <div class="modal-body">
             <form class="form-horizontal" id="form2" action="ProvisionalRiesgoUpdaterForYou" method="post"  role="form">
    			<input type="hidden" name="peligroId" id="peligroId" value="" readonly/>
    			<input type="hidden" name="riesgoId"  id="riesgoId" value="" readonly/>
    			<div class="control-group">
				<label for="">Lugar*</label>
				<select class="form-control" name="lugarId" id="lugarId">
				<option value=""></option>			
				</select>
				</div>
				<div class="control-group">
				<label for="">Probabilidad*</label>
				<select class="form-control" name="nivelProbabilidadId" id="nivelProbabilidadId">
				<%
				for (NivelProbabilidad nivel : NivelProbabilidad.values()) {
					 out.println("<option value=\""+nivel.name()+"\">"+nivel.name()+"</option>");
				}
				%>
				</select> 
				</div>
				<div class="control-group">
                 <label for="">Texto</label><br>
                 <textarea class="form-control" name="texto" id="texto" cols="70" rows="1"></textarea>
                 </div>
                 
                 <div class="control-group">
                 <label for="">Text</label><br>
                 <textarea class="form-control" name="text" id="text" cols="70" rows="1"></textarea>
                 </div>
  
                <div class="control-group">  
                    <label class="control-label" for="">Fecha Activación*</label>                        
					<div class="controls form-inline">
					    <label class="radio-inline"><input type="radio" name="radioFechas" id="radio1" checked="checked">Fecha Inicio 
					        <input class="form-control" type="date" id="fechaActivacion" name="fechaActivacion"/>
					    </label>
                        <label class="radio-inline"><input type="radio" name="radioFechas" id="radio2">Activación(Anual)
							<select class="form-control"  name="mesActivacion" id="mesActivacion" disabled>
							<option value=""></option>
							<%
							for (Mes mes : Mes.values()) {
								 out.println("<option value=\""+mes.name()+"\">"+mes.name()+"</option>");
							}
							%>
							</select>
							<select class="form-control"  name="diaActivacion" id="diaActivacion" disabled>
							<option value=""></option>
							<%
							for (int diaAnual=1;diaAnual<32;diaAnual++) {
								 out.println("<option value=\""+diaAnual+"\">"+diaAnual+"</option>");
							}
							%>
							</select>
							<span id="fechaAnual"></span>
						</label> 
					</div>
				</div>
				
				
				<div class="control-group">
                 <label class="control-label" for="">Caducidad (en días,-1 permanente)*</label>
                 <input class="form-control" type="text" id="caducidad" name="caducidad" />
                </div>
				
				<div class="control-group">
                <label class="control-label" for="">Formula Disipación*
                <a href="media/formulas.pdf"><span class="glyphicon glyphicon-question-sign"></span></a>
                </label>
				<select class="form-control" name="formulaDisipacion" id="formulaDisipacion" >
				<%
				for (FormulaDisipacion fd : FormulaDisipacion.values()) {
					 out.println("<option value=\""+fd.name()+"\">"+fd.name()+"</option>");
				}
				%>
				</select> 
				</div>
				<div class="control-group">
                 <label class="control-label" for="">Desactivación Manual</label>
                 <div class="checkbox">
                     <label><input type="checkbox" name="desactivado" id="desactivado">Desactivado</label>
                 </div>
                </div>
				
				</form>
				
	
      </div>
      <div class="modal-footer" id="modalFooterId">
        <input type="submit" class="btn btn-primary" value="grabar" onclick="savedata()">
        <input type="button" class="btn btn-primary" value="delete" onclick="deletedata()">
      </div>
</div>

</div>
</div>
