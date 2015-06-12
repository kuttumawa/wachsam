<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
<html> 
<head>
<style>
input[type="text"] {
  display: block;
  margin: 0;
  width: 100%;
  font-family: sans-serif;
  font-size: 18px;
  appearance: none;
  box-shadow: none;
  border-radius: none;
  
  padding: 10px;
  border: solid 1px #dcdcdc;
  transition: box-shadow 0.3s, border 0.3s;
}
input[type="text"]:focus {
  outline: none;
  border: solid 1px #707070;
  box-shadow: 0 0 5px 1px #969696;
}
#info{
    border: 1px solid red;
    margin-top: 10px;
    margin-bottom: 20px;
    margin-right: 20px;
    margin-left: 20px;
    color: red;
    font-style: italic;
    padding: 10px;
}
</style>
<script>
function clearFields(){
	document.getElementById("id0").value="";
    //document.getElementById("id").value="";
	document.getElementById("nombre").value="";
	document.getElementById("nombreEn").value="";
	document.getElementById("latitud").value="";
	document.getElementById("longitud").value="";
	document.getElementById("nivel").value="";
	
}
function deleteOper(){
	document.getElementById("oper").value="delete";
	
}
</script>
</head>  
<body>

<%
Alert alert = (Alert)request.getAttribute("alert");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalLugarUpdaterForYou" method="get">
<div>
<label for="">Alerta</label>
<select name="alert" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Alert> alertas =  (List<Alert>)request.getAttribute("alertas");
          for(Alert alert_i:alertas){
        	  out.println("<option value=\""+alert_i.getId()+"\">"+alert_i.getNombre()+"</option>");
          }
         
%> 
</select>
</div>
</form>


<form action="ProvisionalAlertUpdaterForYou" method="post">
<fieldset>


<div>
<label for="">Id</label>
<input type="text"  id="id0" value="<%= alert.getId()!=null?alert.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" value="<%= alert.getId()!=null?alert.getId():"" %>"  />
</div>

<div>
<label for="">Nombre</label>
<input type="text" id="nombre" name="nombre" value="<%= alert.getNombre()!=null?alert.getNombre():"" %>"/>
</div>

<div>
<label for="">Peligro</label>
</div><div>
<select name="peligro">
<option value=""></option>
<%    
          List<Peligro> peligros =  (List<Peligro>)request.getAttribute("peligros");
          for(Peligro peligro:peligros){
        	  
        	  if(alert.getPeligro()!=null && alert.getPeligro().getId()!=null && alert.getPeligro().getId().equals(peligro.getId())){
        		  out.println("<option value=\""+peligro.getId()+"\" selected >"+peligro.getNombre()+"</option>"); 
        	  }else{
        	      out.println("<option value=\""+peligro.getId()+"\">"+peligro.getNombre()+"</option>");
        	  }        	         	
          }
         
%> 
</select>
</div>

<div>
<label for="">Lugar</label>
</div><div>
<select name="lugar">
<option value=""></option>
<%    
			List<Lugar> lugares =  (List<Lugar>)request.getAttribute("lugares");
			for(Lugar lugar:lugares){
				  
				  if(alert.getLugarObj()!=null && alert.getLugarObj().getId()!=null && alert.getLugarObj().getId().equals(lugar.getId())){
					  out.println("<option value=\""+lugar.getId()+"\" selected >"+lugar.getNombre()+"</option>"); 
				  }else{
				      out.println("<option value=\""+lugar.getId()+"\">"+lugar.getNombre()+"</option>");
				  }        	         	
			}
        
%> 
</select>
</div>

<div>
<label for="">Tipo</label>
</div><div>
<select name="tipo">
<option value="normal">normal</option>
<option value="severa">severa</option>
</select>
</div>

<div>
<label for="">Fecha (dd/mm/yyyy)</label>
<input type="text" name="fechaPub" value="<%=alert.getFechaPubFormatted()!=null?alert.getFechaPubFormatted():""%>/>
</div>



<div>
<label for="">Texto</label><br>
<textarea name="texto" cols="100"><%= alert.getTexto()!=null?alert.getTexto():"" %></textarea>
</div>

<div>
<label for="">Text</label><br>
<textarea name="text" cols="100"><%= alert.getText()!=null?alert.getText():"" %></textarea>
</div>

<div>
<label for="">link1</label>
<input type="text" name="link1" value="<%= alert.getLink1()!=null?alert.getLink1():"" %>"/>
</div>

<div>
<label for="">link2</label>
<input type="text" name="link2" value="<%= alert.getLink2()!=null?alert.getLink2():"" %>"/>
</div>

<div>
<label for="">link3</label>
<input type="text" name="link3" value="<%= alert.getLink3()!=null?alert.getLink3():"" %>"/>
</div>

<input type="hidden" id="oper" name="oper"/>

<input type="submit" value="grabar">
<input type="submit" value="delete" onclick="deleteOper()">
<input type="button" value="limpiar" onclick="clearFields()">
</form>
</fieldset>
</body> 
</html>