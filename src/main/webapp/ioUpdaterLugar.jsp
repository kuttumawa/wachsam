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
  width: 50%;
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
	document.getElementById("oper").value="";
	
}
function deleteOper(){
	if(confirm('Seguro?')){
		document.getElementById("oper").value="delete";
	    document.getElementById('form2').submit();
	}

	
}
</script>


</head>  
<body>
<%
Lugar lugar = (Lugar)request.getAttribute("lugar");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalLugarUpdaterForYou" method="get">
<div>
<label for="">Lugar</label>
<select name="lugar" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Lugar> lugares =  (List<Lugar>)request.getAttribute("lugares");
          for(Lugar lugar_i:lugares){
        	  out.println("<option value=\""+lugar_i.getId()+"\">"+lugar_i.getNombre()+"</option>");
          }
         
%> 
</select>
</div>
</form>
<div id="Der" style="float:right">
<div id="googleMap" style="width:700px;height:450px;"></div>
</div>
<div id="Izq" style="width:40%">
<form id="form2" action="ProvisionalLugarUpdaterForYou" method="post">
<fieldset>




<div>
<label for="">Id</label>
<input type="text"  id="id0" value="<%= lugar.getId()!=null?lugar.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" value="<%= lugar.getId()!=null?lugar.getId():"" %>"  />
</div>

<div>
<label for="">Nombre</label>
<input type="text" id="nombre" name="nombre" value="<%= lugar.getNombre()!=null?lugar.getNombre():"" %>"/>
</div>

<div>
<label for="">Name</label>
<input type="text" id="nombreEn" name="nombreEn" value="<%= lugar.getNombreEn()!=null?lugar.getNombreEn():""%>"/>
</div>

<div>
<label for="">Padre 1</label>
<select name="padre1">
<option value=""></option>
<%    
           for(Lugar lugar_i:lugares){
        	  if(lugar.getPadre1()!=null && lugar.getPadre1().getId().equals(lugar_i.getId())){
        		  out.println("<option value=\""+lugar_i.getId()+"\" selected >"+lugar_i.getNombre()+"</option>"); 
        	  }else{
        	      out.println("<option value=\""+lugar_i.getId()+"\">"+lugar_i.getNombre()+"</option>");
        	  }
            } 
         
%> 
</select>
</div>

<div>
<label for="">Padre 2</label>
<select name="padre2">
<option value=""></option>
<%    
           for(Lugar lugar_i:lugares){
        	  if(lugar.getPadre2()!=null && lugar.getPadre2().getId().equals(lugar_i.getId())){
        		  out.println("<option value=\""+lugar_i.getId()+"\" selected >"+lugar_i.getNombre()+"</option>"); 
        	  }else{
        	      out.println("<option value=\""+lugar_i.getId()+"\">"+lugar_i.getNombre()+"</option>");
        	  }
            } 
         
%> 
</select>
</div>

<div>
<label for="">Padre 3</label>
<select name="padre3">
<option value=""></option>
<%    
           for(Lugar lugar_i:lugares){
        	  if(lugar.getPadre3()!=null && lugar.getPadre3().getId().equals(lugar_i.getId())){
        		  out.println("<option value=\""+lugar_i.getId()+"\" selected >"+lugar_i.getNombre()+"</option>"); 
        	  }else{
        	      out.println("<option value=\""+lugar_i.getId()+"\">"+lugar_i.getNombre()+"</option>");
        	  }
            } 
         
%> 
</select>
</div>


<div>
<label for="">Latitud</label>
<input type="text" id="latitud" name="latitud" value="<%= lugar.getLatitud()!=null?lugar.getLatitud():""%>"/>
</div>

<div>
<label for="">Longitud</label>
<input type="text" id="longitud" name="longitud" value="<%= lugar.getLongitud()!=null?lugar.getLongitud():""%>"/>
</div>

<div>
<label for="">Nivel</label>
<input type="text" id="nivel" name="nivel" value="<%= lugar.getNivel()!=null?lugar.getNivel():""%>"/>
</div>

<input type="hidden" id="oper" name="oper"/>

<input type="submit" value="grabar">
<input type="button" value="delete" onclick="deleteOper()">
<input type="button" value="limpiar" onclick="clearFields()">
</form>
</fieldset>
<script src="http://maps.googleapis.com/maps/api/js"></script>
<script>
function initialize() {
  var myCenter=new google.maps.LatLng(<%= lugar.getLatitud()!=null && lugar.getLatitud().length()>0 ?lugar.getLatitud():"0"%>,<%= lugar.getLongitud()!=null && lugar.getLatitud().length()>0?lugar.getLongitud():"0"%>);
  var mapProp = {
    center:myCenter,
    zoom:<%= lugar.getNivel()!=null?lugar.getNivel():"1"%>,
    mapTypeId:google.maps.MapTypeId.ROADMAP
  };
  var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
  google.maps.event.addListener(map, 'click', function(event) {
	  placeMarker(event.latLng);
	  });
    
	function placeMarker(location) {
	  var currentMark = new google.maps.Marker({
	    position: location,
	    map: map,
	  });
	  var infowindow = new google.maps.InfoWindow({
	    content: 'Latitude: ' + location.lat() +
	    '<br>Longitude: ' + location.lng() + '<br>' + ''
	    + '<input style="float:right" type="button" onclick="document.getElementById(\'latitud\').value='+location.lat()+';document.getElementById(\'longitud\').value='+location.lng()+';"/>'});
	  infowindow.open(map,currentMark);
      
	  google.maps.event.addListener(infowindow,'closeclick',function(){
		   currentMark.setMap(null);
		});
	}
  var marker=new google.maps.Marker({
  position:myCenter,
  });

   marker.setMap(map);
}
google.maps.event.addDomListener(window, 'load', initialize);

</script>
</div>

</body> 
</html>