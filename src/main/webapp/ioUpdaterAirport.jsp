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


<%
Airport airport = (Airport)request.getAttribute("airport");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalAirportUpdaterForYou" method="get">
<div>
<label for="">Airport</label>
<select name="airport" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Airport> airports =  (List<Airport>)request.getAttribute("airports");
          for(Airport airport_i:airports){
        	  out.println("<option value=\""+airport_i.getId()+"\">"+airport_i.getName()+"</option>");
          }
         
%> 
</select>
</div>
</form>

<form id="form2" action="ProvisionalAirportUpdaterForYou" method="post">
<fieldset>




<div>
<label for="">Id</label>
<input type="text"  id="id0" value="<%= airport.getId()!=null?airport.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= airport.getId()!=null?airport.getId():"" %>"  />
</div>

<div>
<label for="">Nombre</label>
<input type="text" id="nombre" name="nombre" value="<%= airport.getName()!=null?airport.getName():"" %>"/>
</div>

<div>
<label for="">City</label>
<input type="text" id="city" name="city" value="<%= airport.getCity()!=null?airport.getCity():""%>"/>
</div>

<div>
<label for="">Country</label>
<input type="text" id="country" name="country" value="<%= airport.getCountry()!=null?airport.getCountry():"" %>"/>
</div>

<div>
<label for="">IATA_FAA</label>
<input type="text" id="IATA_FAA" name="IATA_FAA" value="<%= airport.getIATA_FAA()!=null?airport.getIATA_FAA():"" %>"/>
</div>

<div>
<label for="">ICAO</label>
<input type="text" id="ICAO" name="ICAO" value="<%= airport.getICAO()!=null?airport.getICAO():""%>"/>
</div>

<div>
<label for="">latitud</label>
<input type="text" id="latitud" name="latitud" value="<%= airport.getLatitud()!=null?airport.getLatitud():""%>"/>
</div>

<div>
<label for="">logitud</label>
<input type="text" id="logitud" name="logitud" value="<%= airport.getLogitud()!=null?airport.getLogitud():""%>"/>
</div>

<div>
<label for="">altitud</label>
<input type="text" id="altitud" name="altitud" value="<%= airport.getAltitud()!=null?airport.getAltitud():""%>"/>
</div>

<div>
<label for="">timezone</label>
<input type="text" id="timezone" name="timezone" value="<%= airport.getTimezone()!=null?airport.getTimezone():""%>"/>
</div>

<div>
<label for="">DST</label>
<input type="text" id="DST" name="DST" value="<%= airport.getDST()!=null?airport.getDST():""%>"/>
</div>

<div>
<label for="">TZ</label>
<input type="text" id="TZ" name="TZ" value="<%= airport.getTZ()!=null?airport.getTZ():""%>"/>
</div>

<input type="hidden" id="oper" name="oper"/>

<input type="submit" value="grabar">
<input type="button" value="delete" onclick="deleteOper()">
<input type="button" value="limpiar" onclick="clearFields()">
</form>
</fieldset>
</body> 
</html>