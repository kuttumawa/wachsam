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
TipoSitio tipoSitio = (TipoSitio)request.getAttribute("tipoSitio");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info" class="alert alert-danger">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalTipoSitioUpdaterForYou" method="get" class="form-inline" role="form">
<div>
<label for="">TipoSitio</label>
<select class="form-control" name="tipoSitio" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<TipoSitio> tipoSitios =  (List<TipoSitio>)request.getAttribute("tipoSitios");
          for(TipoSitio tipoSitio_i:tipoSitios){
        	  out.println("<option value=\""+tipoSitio_i.getId()+"\">"+tipoSitio_i.getNombre()+"</option>");
          }
         
%> 
</select>
</div>
</form>

<form id="form2" action="ProvisionalTipoSitioUpdaterForYou" method="post" role="form">





<div class="form-group">
<label for="">Id</label>
<input class="form-control" type="text"  id="id0" value="<%= tipoSitio.getId()!=null?tipoSitio.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= tipoSitio.getId()!=null?tipoSitio.getId():"" %>"  />
</div>

<div class="form-group">
<label for="">Nombre</label>
<input class="form-control" type="text" id="nombre" name="nombre" value="<%= tipoSitio.getNombre()!=null?tipoSitio.getNombre():"" %>"/>
</div>

<div class="form-group">
<label for="">Descripción</label><br>
<textarea class="form-control" name="descripcion" id="descripcion" cols="70" rows="4"><%= tipoSitio.getDescripcion()!=null?tipoSitio.getDescripcion():"" %></textarea>
</div>


<input type="hidden" id="oper" name="oper"/>
<div class="btn-group center-block">
<input type="submit" class="btn btn-primary" value="grabar">
<input type="button" class="btn btn-primary" value="delete" onclick="deleteOper()">
<input type="button" class="btn btn-primary" value="limpiar" onclick="clearFields()">
</div>
</form>

</div>
</body> 
</html>