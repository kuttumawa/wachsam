<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
<html> 
<head>
<style>
input[type="text"] {
  display: block;
  margin: 0;
  width: 50%;
  font-family: sans-serif;
  font-size: 18px;
  appearance: none;
  box-shadow: none;
  border-radius: none;
  
  padding: 10px;
  border: solid 1px #dcdcdc;
  transition: box-shadow 0.3s, border 0.3s;
}
input[type="text"]:focus {
  outline: none;
  border: solid 1px #707070;
  box-shadow: 0 0 5px 1px #969696;
}
#info{
    border: 1px solid red;
    margin-top: 10px;
    margin-bottom: 20px;
    margin-right: 20px;
    margin-left: 20px;
    color: red;
    font-style: italic;
    padding: 10px;
}
</style>
<script>
function clearFields(){
	document.getElementById("id0").value="";
    //document.getElementById("id").value="";
	document.getElementById("nombre").value="";
	document.getElementById("nombreEn").value="";
	document.getElementById("damage").value="";
	
}
function deleteOper(){
	document.getElementById("oper").value="delete";
	
}
</script>
</head>  
<body>
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
<input type="hidden" name="id" value="<%= peligro.getId()!=null?peligro.getId():"" %>"  />
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
<label for="">Categoría</label>
</div><div>
<select name="categoria" >
<option value="Enfermedad" <%= (peligro.getCategoria()!=null && peligro.getCategoria().name().equalsIgnoreCase("Enfermedad")?" selected ":"") %>>Enfermedad</option>
<option value="Otros" <%= (peligro.getCategoria()!=null && peligro.getCategoria().name().equalsIgnoreCase("Otros")?" selected ":"") %>>Otros</option>
</select>
</div>

<div>
<label for="">Daño</label>
<input type="text" id="damage" name="damage" value="<%= peligro.getDamage()!=null?peligro.getDamage():""%>"/>
</div>

<input type="hidden" id="oper" name="oper"/>

<input type="submit" value="grabar">
<input type="submit" value="delete" onclick="deleteOper()">
<input type="button" value="limpiar" onclick="clearFields()">
</form>
</fieldset>
</body> 
</html>