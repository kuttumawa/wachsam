<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
<html> 

<script>
function clearFields(){
	document.getElementById("id0").value="";
    document.getElementById("id").value="";
	document.getElementById("nombre").value="";
	document.getElementById("descripcion").value="";
	document.getElementById("fiabilidad").value="";
	
	document.getElementById("oper").value="";
	
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


<%
Fuente fuente = (Fuente)request.getAttribute("fuente");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalFuenteUpdaterForYou" method="get">
<div>
<label for="">Fuente</label>
<select name="fuente" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Fuente> fuentes =  (List<Fuente>)request.getAttribute("fuentes");
          for(Fuente fuente_i:fuentes){
        	  out.println("<option value=\""+fuente_i.getId()+"\">"+fuente_i.getNombre()+"</option>");
          }
         
%> 
</select>
</div>
</form>

<form id="form2" action="ProvisionalFuenteUpdaterForYou" method="post">
<fieldset>




<div>
<label for="">Id</label>
<input type="text"  id="id0" value="<%= fuente.getId()!=null?fuente.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= fuente.getId()!=null?fuente.getId():"" %>"  />
</div>

<div>
<label for="">Nombre</label>
<input type="text" id="nombre" name="nombre" value="<%= fuente.getNombre()!=null?fuente.getNombre():"" %>"/>
</div>

<div>
<label for="">Descripcion</label><br>
<textarea name="descripcion" id="descripcion" cols="100" rows="4"><%= fuente.getDescripcion()!=null?fuente.getDescripcion():"" %></textarea>
</div>


<div>
<label for="">Fiabilidad</label>
<input type="text" id="fiabilidad" name="fiabilidad" value="<%= fuente.getFiabilidad()!=null?fuente.getFiabilidad():"" %>"/>
</div>



<input type="hidden" id="oper" name="oper"/>

<input type="submit" value="grabar">
<input type="button" value="delete" onclick="deleteOper()">
<input type="button" value="limpiar" onclick="clearFields()">
</form>
</fieldset>
</body> 
</html>