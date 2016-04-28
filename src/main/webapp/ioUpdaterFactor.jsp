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
	document.getElementById("texto").value="";
	document.getElementById("textoEn").value="";
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
<div class="container">

<%
Factor factor = (Factor)request.getAttribute("factor");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalFactorUpdaterForYou" method="get">
<div>
<label for="">Factor</label>
<select name="factor" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Factor> factors =  (List<Factor>)request.getAttribute("factors");
          for(Factor factor_i:factors){
        	  out.println("<option value=\""+factor_i.getId()+"\">"+factor_i.getNombre()+"</option>");
          }
         
%> 
</select>
</div>
</form>

<form id="form2" action="ProvisionalFactorUpdaterForYou" method="post">
<fieldset>




<div>
<label for="">Id</label>
<input type="text"  id="id0" value="<%= factor.getId()!=null?factor.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= factor.getId()!=null?factor.getId():"" %>"  />
</div>

<div>
<label for="">Nombre</label>
<input type="text" id="nombre" name="nombre" value="<%= factor.getNombre()!=null?factor.getNombre():"" %>"/>
</div>

<div>
<label for="">Name</label>
<input type="text" id="nombreEn" name="nombreEn" value="<%= factor.getNombreEn()!=null?factor.getNombreEn():""%>"/>
</div>


<div>
<label for="">Texto</label><br>
<textarea name="texto" id="texto" cols="100" rows="4"><%= factor.getTexto()!=null?factor.getTexto():"" %></textarea>
</div>

<div>
<label for="">Text</label><br>
<textarea name="textoEn" id="textoEn" cols="100" rows="4"><%= factor.getTextoEn()!=null?factor.getTextoEn():"" %></textarea>
</div>

<input type="hidden" id="oper" name="oper"/>

<input type="submit" class="btn btn-primary" value="grabar">
<input type="button" class="btn btn-primary" value="delete" onclick="deleteOper()">
<input type="button" class="btn btn-primary" value="limpiar" onclick="clearFields()">
</form>
</fieldset>
</div>
</body> 
</html>