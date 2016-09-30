<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
    <%@ page import="es.io.wachsam.services.*"  %>
    <%@ page import="org.springframework.web.context.WebApplicationContext"  %> 
    <%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"  %> 
<html> 
<%
WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
SecurityService sec=(SecurityService)context.getBean("securityService");
Usuario usuario = (Usuario)request.getSession().getAttribute("user");
%>
<style>
.fila {cursor: pointer}
#modal1Id .modal-dialog  {width:75%;}
</style>

<script>
function modifyData(e){
	$('#modal1Id').modal('show');
	$('#error').hide();
	var mitigacionId = $(e).children().first().text();
	var url="MitigacionServletJSON?oper=getMitigacion&mitigacionId="+mitigacionId;
	$.getJSON(url,function(data){
        console.log(data);
        populateForm(data);
		});	
	
}
function newData(){	
	$('#modal1Id').modal('show');
	$('#error').hide();
	$('#dataId').val('');
		
}

function cleanForm(){
	 
	 $('#mitigacionId').val('');
	 $('#peligroId').val('');
	 $('#factorId').val('');
	 $('#valorMitigacionId').val('');
}
function populateForm(data){
	 $('#mitigacionId').val(data.id);
	 $('#peligroId').val(data.peligro.id);
	 $('#factorId').val(data.factor.id);
	 $('#valorMitigacionId').val(data.value);
	
	 	
}
function savedata(){
	 $.ajax({
		    type: "POST",
		    url: "MitigacionServletJSON?oper=save",
		    data: $("#form2").serialize(),
		    success: function(data) {
                if(!data.error){
			    	fectchData();
			    	$('#modal1Id').modal('hide');
                }else{
                	$('#error').show();
                	$('#error').text(data.resultado);
				}		    	
		    }
		  });
		  return false;
}
function deletedata(){
	 $.ajax({
		    type: "POST",
		    url: "MitigacionServletJSON?oper=delete",
		    data: $("#form2").serialize(),
		    success: function(data) {
		    	fectchData();
		    	$('#modal1Id').modal('hide');		    	
		    }
		  });
		  return false;
}
function openForm(){
	$('#error').hide();
	$('#modal1Id').modal('show');
	
}
function fectchData(){
	var peligroId=$('#peligroId').val();
	if(!peligroId) return;
	var url= "MitigacionServletJSON?peligroId="+peligroId+"&oper=getAllForPeligro";

	$.get(url,function (data){
		$('#tbodyID').children().remove();
		$.each(data, function(i, item) {
		    var row = $('<tr id=\''+item.id+'\' onclick=\'modifyData(this)\' \'></tr>').addClass('fila');
		    row.append('<td>'+ item.id+'</td>');
		    row.append('<td><span data-toggle=\'tooltip\' data-placement=\'top\' title=\''+ item.factor.nombre +'\'>'+item.factor.nombre+'</td>');
		    row.append('<td>'+ item.value+'</td>');
		    $('#tbodyID').append(row);
		});
		
		
	},"json");

		
	}
</script>
 

<body>
<jsp:include page="cabecera.jsp"/>
<div class="container">
<div class="container-fluid">
  <div class="row">
    <div class="col-sm-12">

<%
Peligro peligro = (Peligro)request.getAttribute("peligro");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info" class="alert alert-danger">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>
<form id="form1" action="ProvisionalMitigacionUpdaterForYou" method="get" class="form-inline" role="form">
	<div>
		<label for="">Peligro</label>
		<select class="form-control" name="peligroId" onchange="document.getElementById('form1').submit();">
		<option value=""></option>
		<%    
		          List<Peligro> peligros =  (List<Peligro>)request.getAttribute("peligros");
		          for(Peligro peligro_i:peligros){
		        	  out.println("<option value=\""+peligro_i.getId()+"\">"+peligro_i.getNombre()+"</option>");
		          }
		         
		%> 
		</select>
		<button type="button" class="btn btn-primary btn-sm" onclick="openForm()">
		      <span class="glyphicon glyphicon-plus-sign"></span>
		</button>
	</div>
</form>

<h2><%=peligro!=null?peligro.getNombre():"" %></h2>
 <table class="table table-striped small">
 <thead>
 <tr><th>id</th><th>factor</th><th>value</th></tr>
 </thead>
 <tbody id="tbodyID">
 <%
 List<Mitigacion> mitigaciones= (List<Mitigacion>)request.getAttribute("mitigaciones");
 %>
 <%
 for(Mitigacion r: mitigaciones){ %>
  <tr onclick="modifyData(this)" class="fila">
  
  <td><%=r.getId()%></td>
  <td><%=r.getFactor().getNombre()%></td>
  <td><%=r.getValue() %></td></tr>
 <%}%>
 
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
        <h4 class="modal-title">Mitigaci�n</h4>
        <div id="error" class="alert alert-danger">
               
        </div>
      </div>
      <div class="modal-body">
             <form id="form2" action="ProvisionalMitigacionUpdaterForYou" method="post" class="form-inline" role="form">
    			<input type="hidden" name="peligroId" id="peligroId" value="<%= request.getParameter("peligroId")%>" readonly/>
    			<input type="hidden" name="mitigacionId"  id="mitigacionId" value="" readonly/>
    			<div class="form-group">
				<label for="">Factores</label>
				<select class="form-control" name="factorId" id="factorId">
				<option value=""></option>
				<%    
				          List<Factor> factores =  (List<Factor>)request.getAttribute("factores");
				          for(Factor factor_i:factores){
				        	  out.println("<option value=\""+factor_i.getId()+"\">"+factor_i.getNombre()+"</option>");
				          }
				         
				%> 
				</select>
				</div>
				<div class="form-group">
				<label for="">Probabilidad</label>
				<select class="form-control" name="valorMitigacionId" id="valorMitigacionId">
				<%
				for (ValorMitigacion valor : ValorMitigacion.values()) {
					 out.println("<option value=\""+valor.name()+"\">"+valor.name()+"</option>");
				}
				%>
				</select> 
				</div>
				
				</form>
				
      </div>
      <div class="modal-footer">
        <input type="submit" class="btn btn-primary" value="grabar" onclick="savedata()">
        <input type="button" class="btn btn-primary" value="delete" onclick="deletedata()">
      </div>
</div>

</div>
</div>