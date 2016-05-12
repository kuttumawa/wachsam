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
	document.getElementById("alias").value="";
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
Tag tag = (Tag)request.getAttribute("tag");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info" class="alert alert-danger">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalTagUpdaterForYou" method="get" class="form-inline" role="form">
<div>
<label for="">Tag</label>
<select class="form-control" name="tag" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Tag> tags =  (List<Tag>)request.getAttribute("tags");
          for(Tag tag_i:tags){
        	  out.println("<option value=\""+tag_i.getId()+"\">"+tag_i.getNombre()+"</option>");
          }
         
%> 
</select>
</div>
</form>

<form id="form2" action="ProvisionalTagUpdaterForYou" method="post" role="form">





<div class="form-group">
<label for="">Id</label>
<input class="form-control" type="text"  id="id0" value="<%= tag.getId()!=null?tag.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= tag.getId()!=null?tag.getId():"" %>"  />
</div>

<div class="form-group">
<label for="">Alias</label>
<input class="form-control" type="text" id="alias" name="alias" value="<%= tag.getAlias()!=null?tag.getAlias():"" %>"/> 
</div>
<div class="form-group">
<label for="">Nombre</label>
<input class="form-control" type="text" id="nombre" name="nombre" value="<%= tag.getNombre()!=null?tag.getNombre():"" %>"/>
</div>

<div class="form-group">
<label for="">Name</label>
<input class="form-control" class="form-control" type="text" id="nombreEn" name="nombreEn" value="<%= tag.getNombreEn()!=null?tag.getNombreEn():""%>"/>
</div>

<div class="form-group">
<label for="">Descripción</label><br>
<textarea  class="form-control" name="descripcion" id="descripcion" cols="100" rows="4"><%= tag.getDescripcion()!=null?tag.getDescripcion():"" %></textarea>
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