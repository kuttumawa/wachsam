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
Tag tag = (Tag)request.getAttribute("tag");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalTagUpdaterForYou" method="get">
<div>
<label for="">Tag</label>
<select name="tag" onchange="document.getElementById('form1').submit();">
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

<form id="form2" action="ProvisionalTagUpdaterForYou" method="post">
<fieldset>




<div>
<label for="">Id</label>
<input type="text"  id="id0" value="<%= tag.getId()!=null?tag.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= tag.getId()!=null?tag.getId():"" %>"  />
</div>

<div>
<label for="">Nombre</label>
<input type="text" id="nombre" name="nombre" value="<%= tag.getNombre()!=null?tag.getNombre():"" %>"/>
</div>

<div>
<label for="">Name</label>
<input type="text" id="nombreEn" name="nombreEn" value="<%= tag.getNombreEn()!=null?tag.getNombreEn():""%>"/>
</div>

<div>
<label for="">Descripción</label><br>
<textarea name="descripcion" id="descripcion" cols="100" rows="4"><%= tag.getDescripcion()!=null?tag.getDescripcion():"" %></textarea>
</div>


<input type="hidden" id="oper" name="oper"/>

<input type="submit" value="grabar">
<input type="button" value="delete" onclick="deleteOper()">
<input type="button" value="limpiar" onclick="clearFields()">
</form>
</fieldset>
</body> 
</html>