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
	document.getElementById("nombreEn").value="";
	document.getElementById("damage").value="";
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
Peligro peligro = (Peligro)request.getAttribute("peligro");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalPeligroUpdaterForYou" method="get">
<div>
<label for="">Peligro</label>
<select name="peligro" onchange="document.getElementById('form1').submit();">
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

<form id="form2" action="ProvisionalPeligroUpdaterForYou" method="post">
<fieldset>




<div>
<label for="">Id</label>
<input type="text"  id="id0" value="<%= peligro.getId()!=null?peligro.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= peligro.getId()!=null?peligro.getId():"" %>"  />
</div>

<div>
<label for="">Nombre</label>
<input type="text" id="nombre" name="nombre" value="<%= peligro.getNombre()!=null?peligro.getNombre():"" %>"/>
</div>

<div>
<label for="">Name</label>
<input type="text" id="nombreEn" name="nombreEn" value="<%= peligro.getNombreEn()!=null?peligro.getNombreEn():""%>"/>
</div>

<div>
<label for="">Categor�a</label>
</div><div>
<select name="categoria" >
<option value="Enfermedad" <%= (peligro.getCategoria()!=null && peligro.getCategoria().name().equalsIgnoreCase("Enfermedad")?" selected ":"") %>>Enfermedad</option>
<option value="Otros" <%= (peligro.getCategoria()!=null && peligro.getCategoria().name().equalsIgnoreCase("Otros")?" selected ":"") %>>Otros</option>
</select>
</div>




<div>
<label for="">Da�o</label>
</div><div>
<select id="damage" name="damage"" >
<option value="6" <%= (peligro.getDamage()!=null && peligro.getDamage().equals(6)?" selected ":"") %>>Mortal</option>
<option value="4" <%= (peligro.getDamage()!=null && peligro.getDamage().equals(4)?" selected ":"") %>>Muy Grave</option>
<option value="3" <%= (peligro.getDamage()!=null && peligro.getDamage().equals(3)?" selected ":"") %>>Grave</option>
<option value="2" <%= (peligro.getDamage()!=null && peligro.getDamage().equals(2)?" selected ":"") %>>Menor</option>
<option value="1" <%= (peligro.getDamage()!=null && peligro.getDamage().equals(1)?" selected ":"") %>>Molestias</option>


</select>
</div>


<input type="hidden" id="oper" name="oper"/>

<input type="submit" value="grabar">
<input type="button" value="delete" onclick="deleteOper()">
<input type="button" value="limpiar" onclick="clearFields()">
</form>
</fieldset>
</body> 
</html>