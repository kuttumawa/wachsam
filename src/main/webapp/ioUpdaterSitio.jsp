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
    document.getElementById("texto").value="";
    document.getElementById("textoEn").value="";
    document.getElementById("direccion").value="";
    document.getElementById("lugarId").value="";
    document.getElementById("tipo").value="";
   


}
function deleteOper(){

	if(confirm('Seguro?')){
		document.getElementById("oper").value="delete";
	    document.getElementById('form2').submit();
	}
	
	
}
</script>
 
<body>

<div class="container">

<jsp:include page="cabecera.jsp"/>

<div class="container-fluid">
  <div class="row">
    <div class="col-sm-6">
     <%
Sitio sitio = (Sitio)request.getAttribute("sitio");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalSitioUpdaterForYou" method="get">
<div>
<label for="">Sitio</label>
<select name="sitioId" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Sitio> sitios =  (List<Sitio>)request.getAttribute("sitios");
          for(Sitio sitio_i:sitios){
        	  out.println("<option value=\""+sitio_i.getId()+"\">"+sitio_i.prettyPrint()+"</option>");
          }
         
%>  
</select>
</div>
</form>


<form id="form2" action="ProvisionalSitioUpdaterForYou" method="post">



<div>
<label for="">Id</label>
<input type="text"  id="id0" value="<%= sitio.getId()!=null?sitio.getId():""  %>" disabled="disabled" />
<input type="hidden" id="id" name="id" value="<%= sitio.getId()!=null?sitio.getId():"" %>"  />
</div>

<div>
<label for="">Nombre</label>
<input type="text" id="nombre" name="nombre" value="<%=sitio.getNombre()!=null?sitio.getNombre():""%>"/>
</div>

<div>
<label for="">NombreEn</label>
<input type="text" id="nombreEn" name="nombreEn" value="<%= sitio.getNombreEn()!=null?sitio.getNombreEn():""%>"/>
</div>

<div>
<label for="">Dirección</label>
<input type="text" id="direccion" name="direccion" value="<%= sitio.getDireccion()!=null?sitio.getDireccion():"" %>"/>
</div>

<div>
<label for="">Texto</label>
</div>
<div>
<textarea name="texto" id="texto" cols="70" rows="4"><%= sitio.getTexto()!=null?sitio.getTexto():"" %></textarea>
</div>

<div>
<label for="">Text</label>
</div>
<div>
<textarea name="textoEn" id="textoEn" cols="70" rows="4"><%= sitio.getTextoEn()!=null?sitio.getTextoEn():"" %></textarea>
</div>

<div>
<label for="">Tipo</label>
</div><div>
<select name="tipo" id="tipo">
	<option value="0" <%=sitio.getTipo()!=null && sitio.getTipo().equals(0)?" selected":"" %>>Hospital</option>
	<option value="1" <%= sitio.getTipo()!=null && sitio.getTipo().equals(1)?" selected":""%>>Embajada</option>
	<option value="2" <%= sitio.getTipo()!=null && sitio.getTipo().equals(2)?" selected":""%>>CamaraHiperbárica</option>
	<option value="3" <%= sitio.getTipo()!=null && sitio.getTipo().equals(3)?" selected":""%>>Playa</option>
	<option value="4" <%= sitio.getTipo()!=null && sitio.getTipo().equals(4)?" selected":""%>>Cueva</option>
	<option value="5" <%= sitio.getTipo()!=null && sitio.getTipo().equals(5)?" selected":""%>>Pico</option>
	<option value="6" <%= sitio.getTipo()!=null && sitio.getTipo().equals(6)?" selected":""%>>Mercado</option>
</select>
</div>




<div>
<label for="">Lugar</label>
</div><div>
<select name="lugarId" id="lugar">
<option value=""></option>
<%    
			List<Lugar> lugares =  (List<Lugar>)request.getAttribute("lugares");
			for(Lugar lugar:lugares){
				  
				  if(sitio.getLugarObj()!=null && sitio.getLugarObj().getId().equals(lugar.getId())){
					  out.println("<option value=\""+lugar.getId()+"\" selected >"+lugar.getNombre()+"</option>"); 
				  }else{
				      out.println("<option value=\""+lugar.getId()+"\">"+lugar.getNombre()+"</option>");
				  }        	         	
			}
        
%> 
</select>
</div>

<div>
<label for="">Valoración</label>
</div><div>
<select name="valoracion" id="valoracion">
	<option value="-1" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(0)?" selected":"" %>>No valorado</option>
	<option value="0" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(0)?" selected":"" %>>0</option>
    <option value="1" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(1)?" selected":"" %>>1</option>
    <option value="2" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(2)?" selected":"" %>>2</option>
    <option value="3" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(3)?" selected":"" %>>3</option>
    <option value="4" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(4)?" selected":"" %>>4</option>
    <option value="5" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(5)?" selected":"" %>>5</option>
   
</select>
</div>
<input type="hidden" id="oper" name="oper"/>

<input type="submit" class="btn btn-primary" value="grabar">
<input type="button" class="btn btn-primary" value="delete" onclick="deleteOper()">
<input type="button" class="btn btn-primary" value="limpiar" onclick="clearFields()">
</form>
</div>
<div class="col-sm-6">
        <div class="col">
	    <div class="">
	       <input id="pac-input" class="controls" type="text" placeholder="Search Box">
           <div id="googleMap" style="width:600px;height:350px;"></div>
	    </div>
	    <div class="">
	      <jsp:include page="showData.jsp"/>
		   <input type="button" class="btn btn-primary" value="Nuevo Dato" onclick="nuevoDato('sitioId')"/> 
	    </div>
       </div>
</div>
</div>
</div>



<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
  
<script>
var markers = [];
var map;
function initialize() {
  var myCenter=new google.maps.LatLng(<%= sitio.getLugarObj()!=null && sitio.getLugarObj().getLatitud()!=null && sitio.getLugarObj().getLatitud().length()>0 ?sitio.getLugarObj().getLatitud():"0"%>,<%= sitio.getLugarObj()!=null && sitio.getLugarObj().getLongitud()!=null && sitio.getLugarObj().getLatitud().length()>0?sitio.getLugarObj().getLongitud():"0"%>);
  var mapProp = {
    center:myCenter,
    zoom:<%= sitio.getLugarObj()!=null && sitio.getLugarObj().getNivel()!=null?sitio.getLugarObj().getNivel():"1"%>,
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



</body> 
</html>