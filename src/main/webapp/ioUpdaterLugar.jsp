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


 
<body>
<style>
 #pac-input {
        background-color: #fff;
        font-family: Roboto;
        font-size: 15px;
        font-weight: 300;
        margin-left: 12px;
        padding: 0 11px 0 13px;
        text-overflow: ellipsis;
        width: 400px;
      }
</style>
<jsp:include page="cabecera.jsp"/>
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

<div id="Izq" style="width:40%;float:left;">
<form id="form2" action="ProvisionalLugarUpdaterForYou" method="post">
<fieldset>




<div>
<label for="">Id</label>
<input type="text"  id="id0" value="<%= lugar.getId()!=null?lugar.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= lugar.getId()!=null?lugar.getId():"" %>"  />
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
<input type="text" id="nivel"  value="<%= lugar.getNivel()!=null?lugar.getNivel():""%>"/>
</div>



<%
class Nivel{
	public Integer id;
	public String texto;
	public Nivel(Integer id,String texto){
		this.id=id;
		this.texto=texto;
	}
}	
	List<Nivel> niveles = new ArrayList<Nivel>();
	niveles.add(new Nivel(1,"Supra Continental"));
	niveles.add(new Nivel(2,"Continental"));
	niveles.add(new Nivel(3,"Supra Nacional"));
	niveles.add(new Nivel(4,"Nacional"));
	niveles.add(new Nivel(5,"Regional"));
	niveles.add(new Nivel(6,"Infra Regional"));
	



%>


<div>
<label for="">Nivel</label>
</div><div>
<select name="nivel" id="nivel">
<%
for(Nivel nivel_i:niveles){
	  if(lugar.getNivel()!=null && lugar.getNivel().equals(nivel_i.id)){
		  out.println("<option value=\""+nivel_i.id+"\" selected >"+nivel_i.texto+"</option>"); 
	  }else{
	      out.println("<option value=\""+nivel_i.id+"\">"+nivel_i.texto+"</option>");
	  }
  }
%>
</select>
</div>

<input type="hidden" id="oper" name="oper"/>

<input type="submit" value="grabar">
<input type="button" value="delete" onclick="deleteOper()">
<input type="button" value="limpiar" onclick="clearFields()">
</form>
</fieldset>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
  
<script>
var markers = [];
var map;
function initialize() {
  var myCenter=new google.maps.LatLng(<%= lugar.getLatitud()!=null && lugar.getLatitud().length()>0 ?lugar.getLatitud():"0"%>,<%= lugar.getLongitud()!=null && lugar.getLatitud().length()>0?lugar.getLongitud():"0"%>);
  var mapProp = {
    center:myCenter,
    zoom:<%= lugar.getNivel()!=null?lugar.getNivel():"1"%>,
    mapTypeId:google.maps.MapTypeId.ROADMAP
  };
  map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
  // Create the search box and link it to the UI element.
  var input = /** @type {HTMLInputElement} */(
      document.getElementById('pac-input'));
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

  var searchBox = new google.maps.places.SearchBox(
    /** @type {HTMLInputElement} */(input));
  
  google.maps.event.addListener(map, 'click', function(event) {
	  placeMarker(event.latLng);
	  });
  google.maps.event.addListener(searchBox, 'places_changed', function() {
	    var places = searchBox.getPlaces();

	    if (places.length == 0) {
	      return;
	    }
	    for (var i = 0, marker; marker = markers[i]; i++) {
	      marker.setMap(null);
	    }

	    // For each place, get the icon, place name, and location.
	    markers = [];
	    var bounds = new google.maps.LatLngBounds();
	    for (var i = 0, place; place = places[i]; i++) {
	      var image = {
	        url: place.icon,
	        size: new google.maps.Size(71, 71),
	        origin: new google.maps.Point(0, 0),
	        anchor: new google.maps.Point(17, 34),
	        scaledSize: new google.maps.Size(25, 25)
	      };

	      // Create a marker for each place.
	      var marker = new google.maps.Marker({
	        map: map,
	        icon: image,
	        title: place.name,
	        position: place.geometry.location
	      });

	      markers.push(marker);

	      bounds.extend(place.geometry.location);
	      map.setCenter(place.geometry.location);
	    }
        //console.log(bounds);
	    //map.fitBounds(bounds);
	  });
  google.maps.event.addListener(map, 'bounds_changed', function() {
	    var bounds = map.getBounds();
	    searchBox.setBounds(bounds);
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
<div id="Der" style="float:left">
 <input id="pac-input" class="controls" type="text" placeholder="Search Box">
<div id="googleMap" style="width:700px;height:450px;"></div>
</div>
<fieldset>
<jsp:include page="showData.jsp"/>
</fieldset>
</body> 
</html>