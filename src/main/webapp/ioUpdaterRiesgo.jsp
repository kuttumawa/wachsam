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
<script>
function clearFields(){
	document.getElementById("id0").value="";
    document.getElementById("id").value="";
	
}
function deleteOper(){
	if(confirm('Seguro?')){
		document.getElementById("oper").value="delete";
	    document.getElementById('form2').submit();
	}
	
}
function newData(){
	$('#myModal').modal('show');
	$('#dataId').val('');
		
}
function openForm(){
	$('#modalGraph').modal('show');
	refresh();		
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
<form id="form1" action="ProvisionalRiesgoUpdaterForYou" method="get" class="form-inline" role="form">
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


 <table>
 <thead>
 <tr><th></th><th>id</th><th>lugar</th><th>value</th></tr>
 </thead>
 <tbody>
 <%
 List<Riesgo> riesgos= (List<Riesgo>)request.getAttribute("riesgos");
 %>
 <%
 for(Riesgo r: riesgos){ %>
  <tr>
  <td><span style="cursor:pointer" class="glyphicon glyphicon-remove-sign" onclick="delete(<%=r.getId()%>)"></td>
  <td><%=r.getId()%></td>
  <td><%=r.getLugar().getNombre()%></td>
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

<div id="modalGraph" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Riesgo</h4>
      </div>
      <div class="modal-body">
             <form id="form2" action="ProvisionalRiesgoUpdaterForYou" method="get" class="form-inline" role="form">
    			<div class="form-group">
				<label for="">Lugares</label>
				<select class="form-control" name="lugarId">
				<option value=""></option>
				<%    
				          List<Lugar> lugares =  (List<Lugar>)request.getAttribute("lugares");
				          for(Lugar lugar_i:lugares){
				        	  out.println("<option value=\""+lugar_i.getId()+"\">"+lugar_i.getNombre()+"</option>");
				          }
				         
				%> 
				</select>
				</div>
				<div class="form-group">
				<label for="">Probabilidad</label>
				<select class="form-control" name="nivelProbabilidadId" id="nivelProbabilidadId">
				<%
				for (NivelProbabilidad nivel : NivelProbabilidad.values()) {
					 out.println("<option value=\""+nivel.ordinal()+"\">"+nivel.name()+"</option>");
				}
				%>
				</select> 
				</div>
				<button type="button" class="btn btn-primary btn-sm" onclick="newData()">
				      <span class="glyphicon glyphicon-plus-sign"></span>
				</button>
				</form>
      </div>
      <div class="modal-footer">
       
      </div>
</div>

</div>
</div>