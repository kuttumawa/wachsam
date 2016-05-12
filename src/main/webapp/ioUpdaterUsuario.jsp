<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
<html> 

<script>
function clearFields(){
	document.getElementById("id0").value="";
    document.getElementById("id").value="";
	document.getElementById("login").value="";
	document.getElementById("password").value="";
	document.getElementById("email").value="";
	document.getElementById("oper").value="";
	
}
function deleteOper(){
	if(confirm('Seguro?')){
		document.getElementById("oper").value="delete";
	    document.getElementById('form2').submit();
	}
	
}
function deletePermisoOper(idPermiso){
	if(confirm('Seguro?')){
		document.getElementById("oper1").value="deletePermiso";
		document.getElementById("permisoId").value=idPermiso;
	    document.getElementById('form3').submit();
	}
	
}
function addPermisoOper(){
		document.getElementById("oper1").value="addPermiso";
	    document.getElementById('form3').submit();
}
</script>
 

<body>
<jsp:include page="cabecera.jsp"/>
<div class="container">
<div class="container-fluid">
  <div class="row">
    <div class="col-sm-7">

<%
Usuario usuario = (Usuario)request.getAttribute("usuario");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info" class="alert alert-danger">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalUsuarioUpdaterForYou" method="get" class="form-inline" role="form">
<div>
<label for="">Usuario</label>
<select class="form-control" name="usuario" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Usuario> usuarios =  (List<Usuario>)request.getAttribute("usuarios");
          for(Usuario usuario_i:usuarios){
        	  out.println("<option value=\""+usuario_i.getId()+"\">"+usuario_i.getLogin()+"</option>");
          }
         
%> 
</select>
</div>
</form>

<form id="form2" action="ProvisionalUsuarioUpdaterForYou" method="post" role="form">





<div class="form-group">
<label for="">Id</label>
<input type="text" class="form-control" id="id0" value="<%= usuario.getId()!=null?usuario.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= usuario.getId()!=null?usuario.getId():"" %>"  />
</div>

<div class="form-group">
<label for="">Login</label>
<input type="text" class="form-control" id="login" name="login" value="<%= usuario.getLogin()!=null?usuario.getLogin():"" %>"/> 
</div>
<div class="form-group">
<label for="">Password</label>
<input type="text" class="form-control" id="password" name="password" value="<%= usuario.getPassword()!=null?usuario.getPassword():"" %>"/>
</div>

<div class="form-group">
<label for="">Email</label>
<input type="text" class="form-control" id="email" name="email" value="<%= usuario.getEmail()!=null?usuario.getEmail():""%>"/>
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


<form id="form3" action="ProvisionalUsuarioUpdaterForYou" method="post" class="form-inline" role="form">
<div>
<label for="">Permiso</label>
<select class="form-control" id="permisoId" name="permisoId" >
<option value=""></option>
<%    
          List<Permiso> permisos =  (List<Permiso>)request.getAttribute("permisos");
          for(Permiso permiso_i:permisos){
        	  out.println("<option value=\""+permiso_i.getId()+"\">"+permiso_i.prettyPrint()+"</option>");
          }        
%>  
</select>
<input type="button" class="btn btn-primary" value="+" onclick="addPermisoOper()">
</div>
<table  class="table table-striped small">
<tr><th>Permiso</th></tr>
<%
Iterator<Permiso> it=usuario.getPermisos().iterator();
while(it.hasNext()){ 
Permiso p=it.next();
%>
<tr><td><span style="cursor:pointer" class="glyphicon glyphicon-remove-sign" onclick="deletePermisoOper(<%=p.getId()%>)">&nbsp;&nbsp;</span><%=p.prettyPrint()%></td></tr>
<%}%>
</table>

 
<input type="hidden" id="oper1" name="oper"/>
<input type="hidden" name="id" id="id" value="<%= usuario.getId()!=null?usuario.getId():"" %>"  />


</form>

</div>
</div>
</div>
</div>

</body> 
</html>