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
	document.getElementById("city").value="";
	document.getElementById("country").value="";
	document.getElementById("IATA_FAA").value="";
	document.getElementById("ICAO").value="";
	document.getElementById("IATA_FAA").value="";
	document.getElementById("latitud").value="";
	document.getElementById("logitud").value="";
	document.getElementById("altitud").value="";
	document.getElementById("timezone").value="";
	document.getElementById("DST").value="";
	document.getElementById("TZ").value="";
	
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
Airport airport = (Airport)request.getAttribute("airport");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info" class="alert alert-danger">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalAirportUpdaterForYou" method="get" class="form-inline" role="form">
<div class="form-group">
<label for="">Airport</label>
<select class="form-control" name="airport" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Airport> airports =  (List<Airport>)request.getAttribute("airports");
          for(Airport airport_i:airports){
        	  out.println("<option value=\""+airport_i.getId()+"\">"+airport_i.prettyPrint()+"</option>");
          }
         
%> 
</select>
</div>
</form>

<form id="form2" action="ProvisionalAirportUpdaterForYou" method="post" role="form">
<fieldset>




<div class="form-group">
<label for="">Id</label>
<input class="form-control" type="text"  id="id0" value="<%= airport.getId()!=null?airport.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= airport.getId()!=null?airport.getId():"" %>"  />
</div>

<div class="form-group">
<label for="">Nombre</label>
<input class="form-control" type="text" id="nombre" name="nombre" value="<%= airport.getName()!=null?airport.getName():"" %>"/>
</div>

<div class="form-group">
<label for="">City</label>
<input class="form-control" type="text" id="city" name="city" value="<%= airport.getCity()!=null?airport.getCity():""%>"/>
</div>

<div class="form-group">
<label for="">Country</label>
<input class="form-control" type="text" id="country" name="country" value="<%= airport.getCountry()!=null?airport.getCountry():"" %>"/>
</div>

<div class="form-group">
<label for="">IATA_FAA</label>
<input class="form-control" type="text" id="IATA_FAA" name="IATA_FAA" value="<%= airport.getIATA_FAA()!=null?airport.getIATA_FAA():"" %>"/>
</div>

<div class="form-group">
<label for="">ICAO</label>
<input class="form-control" type="text" id="ICAO" name="ICAO" value="<%= airport.getICAO()!=null?airport.getICAO():""%>"/>
</div>

<div class="form-group">
<label for="">latitud</label>
<input class="form-control" type="text" id="latitud" name="latitud" value="<%= airport.getLatitud()!=null?airport.getLatitud():""%>"/>
</div>

<div class="form-group">
<label for="">logitud</label>
<input class="form-control" type="text" id="logitud" name="logitud" value="<%= airport.getLogitud()!=null?airport.getLogitud():""%>"/>
</div>

<div class="form-group">
<label for="">altitud</label>
<input class="form-control" type="text" id="altitud" name="altitud" value="<%= airport.getAltitud()!=null?airport.getAltitud():""%>"/>
</div>

<div class="form-group">
<label for="">timezone</label>
<input class="form-control" type="text" id="timezone" name="timezone" value="<%= airport.getTimezone()!=null?airport.getTimezone():""%>"/>
</div>

<div class="form-group">
<label for="">DST</label>
<input class="form-control" type="text" id="DST" name="DST" value="<%= airport.getDST()!=null?airport.getDST():""%>"/>
</div>

<div class="form-group">
<label for="">TZ</label>
<input class="form-control" type="text" id="TZ" name="TZ" value="<%= airport.getTZ()!=null?airport.getTZ():""%>"/>
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