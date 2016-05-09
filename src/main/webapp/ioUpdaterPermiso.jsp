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
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalPermisoUpdaterForYou" method="get">
<div>
<label for="">Permiso</label>
<select name="permiso" onchange="document.getElementById('form1').submit();">
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

<form id="form2" action="ProvisionalPermisoUpdaterForYou" method="post">





<div>
<label for="">Id</label>
<input type="text"  id="id0" value="<%= permiso.getId()!=null?permiso.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= permiso.getId()!=null?permiso.getId():"" %>"  />
</div>



<div>
<label for="">Objeto</label>
</div><div>
	<select name="objeto" id="objeto">
		<option value="Alert" <%=permiso.getObjeto()!=null && permiso.getObjeto().equals("Alert")?" selected":""%>>Alerta</option>
	    <option value="Lugar" <%=permiso.getObjeto()!=null && permiso.getObjeto().equals("Lugar")?" selected":""%>>Lugar</option>
	
	</select>
</div>

<div>
<label for="">Accion</label>
</div><div>
	<select name="accion" id="accion">
		<option value="0" <%=permiso.getAccion()!=null && permiso.getAccion().equals(AccionesSobreObjetosTipos.CREATE)?" selected":""%>>CREATE</option>
		<option value="1" <%=permiso.getAccion()!=null && permiso.getAccion().equals(AccionesSobreObjetosTipos.READ)?" selected":""%>>READ</option>
		<option value="2" <%=permiso.getAccion()!=null && permiso.getAccion().equals(AccionesSobreObjetosTipos.DELETE)?" selected":""%>>DELETE</option>
		<option value="3" <%=permiso.getAccion()!=null && permiso.getAccion().equals(AccionesSobreObjetosTipos.ALL)?" selected":""%>>ALL</option>
	
	</select>
</div>



<div>
<label for="">Filtro &nbsp;<input type="checkbox"  name="filtroFlag" id="filtroFlag" <%=permiso.getFiltroFlag()!=null && permiso.getFiltroFlag()?" checked":""  %>  />
</label>
</div>
<div>

<input type="text"  id="filtro" name="filtro" value="<%= permiso.getFiltro()!=null?permiso.getFiltro().replaceAll("\"", "&quot;"):""%>"  />
</div>

<input type="hidden" id="oper" name="oper"/>

<input type="submit" class="btn btn-primary" value="grabar">
<input type="button" class="btn btn-primary" value="delete" onclick="deleteOper()">
<input type="button" class="btn btn-primary" value="limpiar" onclick="clearFields()">
</form>

</div>
</body> 
</html>