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



<form id="form1" action="ProvisionalPeligroUpdaterForYou" method="get" class="form-inline" role="form">
<div>
<label for="">Peligro</label>
<select class="form-control" name="peligro" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Peligro> peligros =  (List<Peligro>)request.getAttribute("peligros");
          for(Peligro peligro_i:peligros){
        	  out.println("<option value=\""+peligro_i.getId()+"\">"+peligro_i.getNombre()+"</option>");
          }
         
%> 
</select>
</div>
<div>
<label for="">Lugares</label>
<select class="form-control" name="lugar">
<option value=""></option>
<%    
          List<Lugar> lugares =  (List<Lugar>)request.getAttribute("lugares");
          for(Lugar lugar_i:lugares){
        	  out.println("<option value=\""+lugar_i.getId()+"\">"+lugar_i.getNombre()+"</option>");
          }
         
%> 
</select>
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
<div class="col-sm-5">
       XXXXXXXXXXXX
</div>
</div>
</div>
</div>

</body> 
</html>