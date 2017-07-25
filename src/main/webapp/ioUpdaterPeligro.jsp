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
	document.getElementById("nombre").value="";
	document.getElementById("nombreEn").value="";
	document.getElementById("damage").value="";
	document.getElementById("oper").value="";
	document.getElementById("texto").value="";
	document.getElementById("text").value="";
	$('tr').remove()
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
    <div class="col-sm-7">
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
</form>

<form id="form2" action="ProvisionalPeligroUpdaterForYou" method="post" role="form">





<div class="form-group">
<label for="">Id</label>
<input class="form-control"type="text"  id="id0" value="<%= peligro.getId()!=null?peligro.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= peligro.getId()!=null?peligro.getId():"" %>"  />
</div>

<div class="form-group">
<label for="">Nombre</label>
<input class="form-control" type="text" id="nombre" name="nombre" value="<%= peligro.getNombre()!=null?peligro.getNombre():"" %>"/>
</div>

<div class="form-group">
<label for="">Name</label>
<input class="form-control" type="text" id="nombreEn" name="nombreEn" value="<%= peligro.getNombreEn()!=null?peligro.getNombreEn():""%>"/>
</div>
<div class="form-group">
    <label for="">Texto</label><br>
    <textarea class="form-control" name="texto" id="texto" cols="70" rows="2">
    <%= peligro.getTexto()!=null?peligro.getTexto():"" %>
    </textarea>
</div>
<div class="form-group">
    <label for="">Text</label><br>
    <textarea class="form-control" name="text" id="text" cols="70" rows="2">
    <%= peligro.getText()!=null?peligro.getText():"" %>
    </textarea>
</div>
<div class="form-group">
<label for="">Categoría</label>

<select class="form-control" name="categoria" >
<option value="enfermedad" <%= (peligro.getCategoria()!=null && peligro.getCategoria().name().equalsIgnoreCase("Enfermedad")?" selected ":"") %>>Enfermedad</option>
<option value="violencia" <%= (peligro.getCategoria()!=null && peligro.getCategoria().name().equalsIgnoreCase("violencia")?" selected ":"") %>>Violencia,Inseguridad</option>
<option value="naturaleza" <%= (peligro.getCategoria()!=null && peligro.getCategoria().name().equalsIgnoreCase("naturaleza")?" selected ":"") %>>Clima,Catástrofes Naturales</option>
<option value="accidentes" <%= (peligro.getCategoria()!=null && peligro.getCategoria().name().equalsIgnoreCase("accidentes")?" selected ":"") %>>Accidentes</option>
<option value="conflicto" <%= (peligro.getCategoria()!=null && peligro.getCategoria().name().equalsIgnoreCase("conflicto")?" selected ":"") %>>Conflicto Bélico,Inestabilidad social</option>
<option value="otros" <%= (peligro.getCategoria()!=null && peligro.getCategoria().name().equalsIgnoreCase("Otros")?" selected ":"") %>>Otros</option>

</select>
</div>




<div class="form-group">
<label for="">Daño</label>
<select class="form-control" id="damage" name="damage"" >
<option value="6" <%= (peligro.getDamage()!=null && peligro.getDamage().equals(6)?" selected ":"") %>>Mortal</option>
<option value="4" <%= (peligro.getDamage()!=null && peligro.getDamage().equals(4)?" selected ":"") %>>Muy Grave</option>
<option value="3" <%= (peligro.getDamage()!=null && peligro.getDamage().equals(3)?" selected ":"") %>>Grave</option>
<option value="2" <%= (peligro.getDamage()!=null && peligro.getDamage().equals(2)?" selected ":"") %>>Menor</option>
<option value="1" <%= (peligro.getDamage()!=null && peligro.getDamage().equals(1)?" selected ":"") %>>Molestias</option>
<option value="0" <%= (peligro.getDamage()!=null && peligro.getDamage().equals(1)?" selected ":"") %>>Sin daño</option>
</select>
</div>


<input type="hidden" id="oper" name="oper"/>
<div class="btn-group center-block">
<input type="submit" class="btn btn-primary" value="grabar">
<input type="button" class="btn btn-primary" value="delete" onclick="deleteOper()">
<input type="button" class="btn btn-primary" value="limpiar" onclick="clearFields()">
</div>
</form>
</div>
<div class="col-sm-5">
         <jsp:include page="showData.jsp" >
		    <jsp:param name="objetoId" value="<%=peligro.getId()!=null?peligro.getId():null%>" />
		    <jsp:param name="objetoTipo" value="1"/>
		 </jsp:include>
 </div>
</div>
</div>
</div>

</body> 
</html>