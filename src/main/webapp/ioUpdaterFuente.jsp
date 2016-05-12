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
	document.getElementById("fiabilidad").value="";
	
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
Fuente fuente = (Fuente)request.getAttribute("fuente");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="alert alert-danger">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalFuenteUpdaterForYou" method="get"  class="form-inline" role="form">
<div class="form-group">
<label for="">Fuente</label>
<select class="form-control" name="fuente" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Fuente> fuentes =  (List<Fuente>)request.getAttribute("fuentes");
          for(Fuente fuente_i:fuentes){
        	  out.println("<option value=\""+fuente_i.getId()+"\">"+fuente_i.getNombre()+"</option>");
          }
         
%> 
</select>
</div>
</form>

<form id="form2" action="ProvisionalFuenteUpdaterForYou" method="post" role="form">





<div class="form-group">
<label for="">Id</label>
<input class="form-control" type="text"  id="id0" value="<%= fuente.getId()!=null?fuente.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= fuente.getId()!=null?fuente.getId():"" %>"  />
</div>

<div class="form-group">
<label for="">Nombre</label>
<input  class="form-control" type="text" id="nombre" name="nombre" value="<%= fuente.getNombre()!=null?fuente.getNombre():"" %>"/>
</div>

<div class="form-group">
<label for="">Descripción</label><br>
<textarea class="form-control" name="descripcion" id="descripcion" cols="100" rows="4"><%= fuente.getDescripcion()!=null?fuente.getDescripcion():"" %></textarea>
</div>




<%
class Fiabilidad{
	public Integer id;
	public String texto;
	public Fiabilidad(Integer id,String texto){
		this.id=id;
		this.texto=texto;
	}
}	
	List<Fiabilidad> niveles = new ArrayList<Fiabilidad>();
	niveles.add(new Fiabilidad(6,"Reliable:No doubt about the source"));
	niveles.add(new Fiabilidad(5,"Usually reliable:Minor doubts.History of mostly valid information"));
	niveles.add(new Fiabilidad(4,"Fairly reliable:Doubts.Provided valid information in the past"));
	niveles.add(new Fiabilidad(3,"Not usually reliable:	Significant doubts. Provided valid information in the past"));
	niveles.add(new Fiabilidad(2,"Unreliable:Lacks authenticity: History of invalid information"));
	niveles.add(new Fiabilidad(1,"Cannot be judged:Insufficient information to evaluate reliability.May or may not be reliable"));
	



%>


<div class="form-group">
<label for="">Fiabilidad</label>

<select class="form-control" name="fiabilidad" id="fiabilidad">
<%
for(Fiabilidad f_i:niveles){
	  if(fuente.getFiabilidad()!=null && fuente.getFiabilidad().equals(f_i.id)){
		  out.println("<option value=\""+f_i.id+"\" selected >"+f_i.texto+"</option>"); 
	  }else{
	      out.println("<option value=\""+f_i.id+"\">"+f_i.texto+"</option>");
	  }
  }
%>
</select>
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