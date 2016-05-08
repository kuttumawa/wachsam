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
function deletePermisoOper(){
	if(confirm('Seguro?')){
		document.getElementById("oper").value="deletePermiso";
	    document.getElementById('form2').submit();
	}
	
}
function addPermisoOper(){
	
		document.getElementById("oper").value="addPermiso";
	    document.getElementById('form2').submit();
	
	
}
</script>
 

<body>
<jsp:include page="cabecera.jsp"/>
<div class="container">

<%
Usuario usuario = (Usuario)request.getAttribute("usuario");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalUsuarioUpdaterForYou" method="get">
<div>
<label for="">Usuario</label>
<select name="usuario" onchange="document.getElementById('form1').submit();">
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

<form id="form2" action="ProvisionalUsuarioUpdaterForYou" method="post">





<div>
<label for="">Id</label>
<input type="text"  id="id0" value="<%= usuario.getId()!=null?usuario.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= usuario.getId()!=null?usuario.getId():"" %>"  />
</div>

<div>
<label for="">Login</label>
<input type="text" id="login" name="login" value="<%= usuario.getLogin()!=null?usuario.getLogin():"" %>"/> 
</div>
<div>
<label for="">Password</label>
<input type="text" id="password" name="password" value="<%= usuario.getPassword()!=null?usuario.getPassword():"" %>"/>
</div>

<div>
<label for="">Email</label>
<input type="text" id="email" name="email" value="<%= usuario.getEmail()!=null?usuario.getEmail():""%>"/>
</div>




<input type="hidden" id="oper" name="oper"/>

<input type="submit" class="btn btn-primary" value="grabar">
<input type="button" class="btn btn-primary" value="delete" onclick="deleteOper()">
<input type="button" class="btn btn-primary" value="limpiar" onclick="clearFields()">
</form>


-----------
<form id="form3" action="ProvisionalUsuarioUpdaterForYou" method="post">
<div>
<label for="">Permisos</label>
<select name="permisoId" >
<option value=""></option>
<%    
          List<Permiso> permisos =  (List<Permiso>)request.getAttribute("permisos");
          for(Permiso permiso_i:permisos){
        	  out.println("<option value=\""+permiso_i.getId()+"\">"+permiso_i.prettyPrint()+"</option>");
          }
         
%>  
</select>
</div>
<table>
<tr><th></th></tr>
<%for(Permiso p:usuario.getPermisos()){ %>
<tr><td><%=p.prettyPrint() %></td></tr>
<%} %>
</table>
<input type="button" class="btn btn-primary" value="addPermiso" onclick="addPermisoOper()">
<input type="button" class="btn btn-primary" value="deletePermiso" onclick="deletePermisoOper()">
</form>
-----------
</div>
</body> 
</html>