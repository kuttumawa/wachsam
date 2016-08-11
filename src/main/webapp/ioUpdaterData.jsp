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
    document.getElementById("tag").value="";
    
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
<label for="">Descripción del Dato</label>
<input class="form-control" type="text" id="descripcion" name="descripcion" value="<%= data.getDescripcion()!=null?data.getDescripcion():""%>"/>
</div>


<div class="form-group">
<label for="">TipoValor</label>

<select class="form-control" name="tipoValor" id="tipo">
	<option value="NUMERICO" <%=data.getTipoValor()!=null && data.getTipoValor().toString().equalsIgnoreCase("NUMERICO")?" selected":"" %>>NUMÉRICO</option>
	<option value="TEXTO" <%= data.getTipoValor()!=null && data.getTipoValor().toString().equalsIgnoreCase("TEXTO")?" selected":"" %>>TEXTO</option>
	<option value="VACIO" <%=data.getTipoValor()!=null && data.getTipoValor().toString().equalsIgnoreCase("VACIO")?" selected":"" %>>VACÍO</option>
</select>
</div>


<div class="form-group">
<label for="">Objeto</label>

<select class="form-control" name="objetoTipo" id="objetoTipo">
<option value=""></option>
<%    
          for(ObjetoSistema o:ObjetoSistema.values()){
        	  
        	  if(data.getObjetoTipo()!=null  && data.getObjetoTipo().equals(o)){
        		  out.println("<option value=\""+o.ordinal()+"\" selected >"+o.name()+"</option>"); 
        	  }else{
        	      out.println("<option value=\""+o.ordinal()+"\">"+o.name()+"</option>");
        	  }        	         	
          }
         
%> 
</select>
</div>

<div class="form-group">
<label for="">ObjetoConneted</label>

<select class="form-control" name="objetoConnected" id="objetoConnected">
<option value=""></option>
<%    
          for(ObjetoSistema o:ObjetoSistema.values()){
        	  
        	  if(data.getObjetoTipo()!=null  && data.getObjetoTipo().equals(o)){
        		  out.println("<option value=\""+o.ordinal()+"\" selected >"+o.name()+"</option>"); 
        	  }else{
        	      out.println("<option value=\""+o.ordinal()+"\">"+o.name()+"</option>");
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