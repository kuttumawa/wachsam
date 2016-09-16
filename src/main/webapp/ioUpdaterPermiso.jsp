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
	document.getElementById("objeto").value="";
	document.getElementById("accion").value="";
	document.getElementById("filtro").value="";
	document.getElementById("filtroFlag").value="";
	
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
Permiso permiso = (Permiso)request.getAttribute("permiso");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info" class="alert alert-danger">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalPermisoUpdaterForYou" method="get" role="form">
<div>
<label for="">Permiso</label>
<select class="form-control" name="permiso" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Permiso> permisos =  (List<Permiso>)request.getAttribute("permisos");
          for(Permiso permiso_i:permisos){
        	  out.println("<option value=\""+permiso_i.getId()+"\">"+permiso_i.prettyPrint()+"</option>");
          }
         
%> 
</select>
</div>
</form>

<form id="form2" action="ProvisionalPermisoUpdaterForYou" method="post" role="form">





<div class="form-group">
<label for="">Id</label>
<input type="text" class="form-control"  id="id0" value="<%= permiso.getId()!=null?permiso.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= permiso.getId()!=null?permiso.getId():"" %>"  />
</div>

<div class="form-group">
<label for="">Nombre</label>
<input type="text" class="form-control" name="nombre" id="nombre" value="<%= permiso.getNombre()!=null?permiso.getNombre():""%>"/>
</div>

<div>
<label for="">Objeto</label>
<select class="form-control" name="objeto" id="objeto">
		<option value="Alert" <%=permiso.getObjeto()!=null && permiso.getObjeto().equals("Alert")?" selected":""%>>Alerta</option>
	    <option value="Lugar" <%=permiso.getObjeto()!=null && permiso.getObjeto().equals("Lugar")?" selected":""%>>Lugar</option>
	    <option value="Peligro" <%=permiso.getObjeto()!=null && permiso.getObjeto().equals("Peligro")?" selected":""%>>Peligro</option>
	    <option value="Sitio" <%=permiso.getObjeto()!=null && permiso.getObjeto().equals("Sitio")?" selected":""%>>Sitio</option>
	    <option value="Data" <%=permiso.getObjeto()!=null && permiso.getObjeto().equals("Data")?" selected":""%>>Data</option>
	    <option value="Riesgo" <%=permiso.getObjeto()!=null && permiso.getObjeto().equals("Riesgo")?" selected":""%>>Riesgo</option>
	    <option value="Mitigacion" <%=permiso.getObjeto()!=null && permiso.getObjeto().equals("Mitigacion")?" selected":""%>>Mitigacion</option>	   
	    <option value="Recurso" <%=permiso.getObjeto()!=null && permiso.getObjeto().equals("Recurso")?" selected":""%>>Recurso</option>	   
	   	<option value="PermisosAvanzados" <%=permiso.getObjeto()!=null && permiso.getObjeto().equals("PermisosAvanzados")?" selected":""%>>PermisosAvanzados</option>
	    <option value="PermisosGod" <%=permiso.getObjeto()!=null && permiso.getObjeto().equals("PermisosGod")?" selected":""%>>PermisosGod</option>
	
	</select>
</div>

<div>
<label for="">Accion</label>

	<select class="form-control" name="accion" id="accion">
		<option value="0" <%=permiso.getAccion()!=null && permiso.getAccion().equals(Acciones.CREATE)?" selected":""%>>CREATE</option>
		<option value="1" <%=permiso.getAccion()!=null && permiso.getAccion().equals(Acciones.UPDATE)?" selected":""%>>UPDATE</option>		
		<option value="2" <%=permiso.getAccion()!=null && permiso.getAccion().equals(Acciones.READ)?" selected":""%>>READ</option>
		<option value="3" <%=permiso.getAccion()!=null && permiso.getAccion().equals(Acciones.DELETE)?" selected":""%>>DELETE</option>
		<option value="4" <%=permiso.getAccion()!=null && permiso.getAccion().equals(Acciones.ALL)?" selected":""%>>ALL</option>
	
	</select>
</div>



<div>
<label for="">Filtro &nbsp;</label>
<input type="checkbox"  name="filtroFlag" id="filtroFlag" <%=permiso.getFiltroFlag()!=null && permiso.getFiltroFlag()?" checked":""  %>  />
<input class="form-control" type="text"  id="filtro" name="filtro" value="<%= permiso.getFiltro()!=null?permiso.getFiltro().replaceAll("\"", "&quot;"):""%>"  />
</div>

<input type="hidden" id="oper" name="oper"/>
 <div class="btn-group">
	<input type="submit" class="btn btn-primary" value="grabar">
	<input type="button" class="btn btn-primary" value="delete" onclick="deleteOper()">
	<input type="button" class="btn btn-primary" value="limpiar" onclick="clearFields()">
</div>
</form>

</div>
</body> 
</html>