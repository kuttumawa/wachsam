<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
<html> 


<script>
function clearFields(){
	document.getElementById("id0").value="";
    document.getElementById("id").value="";
    document.getElementById("value").value="";
    document.getElementById("descripcion").value="";
    document.getElementById("tipoValor").value="";
    document.getElementById("peligro").value="";
    document.getElementById("lugar").value="";
    document.getElementById("tag1").value="";
    document.getElementById("tag2").value="";
    document.getElementById("tag3").value="";
    document.getElementById("evento").value="";
    document.getElementById("sitio").value="";

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
Data data = (Data)request.getAttribute("data");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info" class="alert alert-danger">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalDataUpdaterForYou" method="get" class="form-inline" role="form">
<div>
<label for="">Data</label>
<select class="form-control" name="dataId" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Data> datas =  (List<Data>)request.getAttribute("datas");
          for(Data data_i:datas){
        	  out.println("<option value=\""+data_i.getId()+"\">"+data_i.prettyPrint()+"</option>");
          }
         
%> 
</select>
</div>
</form>


<form id="form2" action="ProvisionalDataUpdaterForYou" method="post" role="form">



<div class="form-group">
<label for="">Id</label>
<input class="form-control" type="text"  id="id0" value="<%= data.getId()!=null?data.getId():""  %>" disabled="disabled" />
<input type="hidden" id="id" name="id" value="<%= data.getId()!=null?data.getId():"" %>"  />
</div>

<div class="form-group">
<label for="">Valor</label>
<input class="form-control" type="text" id="value" name="value" value="<%= data.getValue()!=null?data.getValue():""%>"/>
</div>

<div class="form-group">
<label for="">Descripci�n del Dato</label>
<input class="form-control" type="text" id="descripcion" name="descripcion" value="<%= data.getDescripcion()!=null?data.getDescripcion():""%>"/>
</div>


<div class="form-group">
<label for="">TipoValor</label>

<select class="form-control" name="tipoValor" id="tipo">
	<option value="NUMERICO" <%=data.getTipoValor()!=null && data.getTipoValor().toString().equalsIgnoreCase("NUMERICO")?" selected":"" %>>NUM�RICO</option>
	<option value="TEXTO" <%= data.getTipoValor()!=null && data.getTipoValor().toString().equalsIgnoreCase("TEXTO")?" selected":"" %>>TEXTO</option>
	<option value="VACIO" <%=data.getTipoValor()!=null && data.getTipoValor().toString().equalsIgnoreCase("VACIO")?" selected":"" %>>VAC�O</option>
</select>
</div>


<div class="form-group">
<label for="">Peligro</label>

<select class="form-control" name="subjectId" id="peligro">
<option value=""></option>
<%    
          List<Peligro> peligros =  (List<Peligro>)request.getAttribute("peligros");
          for(Peligro peligro:peligros){
        	  
        	  if(data.getSubjectId()!=null  && data.getSubjectId().equals(peligro.getId())){
        		  out.println("<option value=\""+peligro.getId()+"\" selected >"+peligro.getNombre()+"</option>"); 
        	  }else{
        	      out.println("<option value=\""+peligro.getId()+"\">"+peligro.getNombre()+"</option>");
        	  }        	         	
          }
         
%> 
</select>
</div>

<div class="form-group">
<label for="">Lugar</label>

<select class="form-control" name="lugarId" id="lugar">
<option value=""></option>
<%    
			List<Lugar> lugares =  (List<Lugar>)request.getAttribute("lugares");
			for(Lugar lugar:lugares){
				  
				  if(data.getLugarId()!=null && data.getLugarId().equals(lugar.getId())){
					  out.println("<option value=\""+lugar.getId()+"\" selected >"+lugar.getNombre()+"</option>"); 
				  }else{
				      out.println("<option value=\""+lugar.getId()+"\">"+lugar.getNombre()+"</option>");
				  }        	         	
			}
        
%> 
</select>
</div>

<div class="form-group">
<label for="">Alerta</label>

<select class="form-control" name="eventoId" id="evento">
<option value=""></option>
<%    
          List<Alert> alertas =  (List<Alert>)request.getAttribute("alertas");
          for(Alert alert_i:alertas){
        	  if(data.getEventoId()!=null && data.getEventoId().equals(alert_i.getId())){
				  out.println("<option value=\""+alert_i.getId()+"\" selected >"+alert_i.prettyPrint()+"</option>"); 
			  }else{
			      out.println("<option value=\""+alert_i.getId()+"\">"+alert_i.prettyPrint()+"</option>");
			  } 
          }
         
%> 
</select>
</div>
<div class="form-group">
<label for="">Sitio</label>

<select class="form-control" name="sitioId" id="sitio">
<option value=""></option>
<%    
          List<Sitio> sitios =  (List<Sitio>)request.getAttribute("sitios");
          for(Sitio sitio_i:sitios){
        	  if(data.getSitioId()!=null && data.getSitioId().equals(sitio_i.getId())){
				  out.println("<option value=\""+sitio_i.getId()+"\" selected >"+sitio_i.prettyPrint()+"</option>"); 
			  }else{
			      out.println("<option value=\""+sitio_i.getId()+"\">"+sitio_i.prettyPrint()+"</option>");
			  } 
          }
         
%> 
</select>
</div>
<div class="form-group">
<label for="">Tag1</label>

<select class="form-control" name="tag1" id="tag1">
<option value=""></option>
<%    
			List<Tag> tags =  (List<Tag>)request.getAttribute("tags");
			for(Tag tag:tags){
				  
				  if(data.getTag1()!=null && data.getTag1().getId()!=null && data.getTag1().getId().equals(tag.getId())){
					  out.println("<option value=\""+tag.getId()+"\" selected >"+tag.getNombre()+"</option>"); 
				  }else{
				      out.println("<option value=\""+tag.getId()+"\">"+tag.getNombre()+"</option>");
				  }        	         	
			}
        
%> 
</select>
</div>

<div class="form-group">
<label for="">Tag2</label>

<select class="form-control" name="tag2" id="tag2">
<option value=""></option>
<%    
			//List<Tag> tags =  (List<Tag>)request.getAttribute("tags");
			for(Tag tag:tags){
				  
				  if(data.getTag2()!=null && data.getTag2().getId()!=null && data.getTag2().getId().equals(tag.getId())){
					  out.println("<option value=\""+tag.getId()+"\" selected >"+tag.getNombre()+"</option>"); 
				  }else{
				      out.println("<option value=\""+tag.getId()+"\">"+tag.getNombre()+"</option>");
				  }        	         	
			}
        
%> 
</select>
</div>

<div class="form-group">
<label for="">Tag3</label>

<select class="form-control" name="tag3" id="tag3">
<option value=""></option>
<%    
			//List<Tag> tags =  (List<Tag>)request.getAttribute("tags");
			for(Tag tag:tags){
				  
				  if(data.getTag3()!=null && data.getTag3().getId()!=null && data.getTag3().getId().equals(tag.getId())){
					  out.println("<option value=\""+tag.getId()+"\" selected >"+tag.getNombre()+"</option>"); 
				  }else{
				      out.println("<option value=\""+tag.getId()+"\">"+tag.getNombre()+"</option>");
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