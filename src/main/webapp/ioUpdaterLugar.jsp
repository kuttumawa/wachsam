<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
    <%@ page import="es.io.wachsam.services.*"  %>
    <%@ page import="org.springframework.web.context.WebApplicationContext"  %> 
    <%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"  %> 
<html> 
<%
WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
SecurityService sec=(SecurityService)context.getBean("securityService");
Usuario usuario = (Usuario)request.getSession().getAttribute("user");
%>

<script>
function clearFields(){
	document.getElementById("id0").value="";
    document.getElementById("id").value="";
	document.getElementById("nombre").value="";
	document.getElementById("nombreEn").value="";
	document.getElementById("latitud").value="";
	document.getElementById("longitud").value="";
	document.getElementById("padre1").value="";
	document.getElementById("padre2").value="";
	document.getElementById("padre3").value="";
	document.getElementById("nivel").value="";
	document.getElementById("oper").value="";
	$('tr').remove()
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
<div class="container">


<div class="container-fluid">
  <div class="row">
    <div class="col-sm-6">
    <%
		Lugar lugar = (Lugar)request.getAttribute("lugar");
		%>
		
		<%if(request.getAttribute("resultado")!=null){ %>
		<div id="info" class="alert alert-danger">
		<% out.println(request.getAttribute("resultado")); %>
		</div>
		<%}%>
		
		
		
		<form id="form1" action="ProvisionalLugarUpdaterForYou" method="get" class="form-inline" role="form">
		<div class="form-group">
		<label for="">Lugar</label>
		<select class="form-control" name="lugar" onchange="document.getElementById('form1').submit();">
		<option value=""></option>
		<%    
		          List<Lugar> lugares =  (List<Lugar>)request.getAttribute("lugares");
		          for(Lugar lugar_i:lugares){
		        	  out.println("<option value=\""+lugar_i.getId()+"\">"+lugar_i.prettyPrint()+"</option>");
		          }
		          
		%> 
		</select>
		</div>
		</form>
      <form id="form2" action="ProvisionalLugarUpdaterForYou" method="post">

<div class="form-group">
<label for="">Id</label>
<input class="form-control" type="text"  id="id0" value="<%= lugar.getId()!=null?lugar.getId():""  %>" disabled="disabled" />
<input type="hidden" name="id" id="id" value="<%= lugar.getId()!=null?lugar.getId():"" %>"  />
</div>

<div class="form-group">
<label for="">Nombre</label>
<input class="form-control" type="text" id="nombre" name="nombre" value="<%= lugar.getNombre()!=null?lugar.getNombre():"" %>"/>
</div>

<div class="form-group">
<label for="">Name</label>
<input class="form-control" type="text" id="nombreEn" name="nombreEn" value="<%= lugar.getNombreEn()!=null?lugar.getNombreEn():""%>"/>
</div>

<div class="form-group">
<label for="">Padre 1</label>
<select class="form-control" id="padre1" name="padre1">
<option value=""></option>
<%    
           for(Lugar lugar_i:lugares){
        	  if(lugar.getPadre1()!=null && lugar.getPadre1().getId().equals(lugar_i.getId())){
        		  out.println("<option value=\""+lugar_i.getId()+"\" selected >"+lugar_i.prettyPrint()+"</option>"); 
        	  }else{
        	      out.println("<option value=\""+lugar_i.getId()+"\">"+lugar_i.prettyPrint()+"</option>");
        	  }
            } 
         
%> 
</select>
</div>

<div class="form-group">
<label for="">Padre 2</label>
<select class="form-control" id="padre2" name="padre2">
<option value=""></option>
<%    
           for(Lugar lugar_i:lugares){
        	  if(lugar.getPadre2()!=null && lugar.getPadre2().getId().equals(lugar_i.getId())){
        		  out.println("<option value=\""+lugar_i.getId()+"\" selected >"+lugar_i.prettyPrint()+"</option>"); 
        	  }else{
        	      out.println("<option value=\""+lugar_i.getId()+"\">"+lugar_i.prettyPrint()+"</option>");
        	  }
            } 
         
%> 
</select>
</div>

<div class="form-group">
<label for="">Padre 3</label>
<select class="form-control" id="padre3" name="padre3">
<option value=""></option>
<%    
           for(Lugar lugar_i:lugares){
        	  if(lugar.getPadre3()!=null && lugar.getPadre3().getId().equals(lugar_i.getId())){
        		  out.println("<option value=\""+lugar_i.getId()+"\" selected >"+lugar_i.prettyPrint()+"</option>"); 
        	  }else{
        	      out.println("<option value=\""+lugar_i.getId()+"\">"+lugar_i.prettyPrint()+"</option>");
        	  }
            } 
         
%> 
</select>
</div>


<div class="form-group">
<label for="">Latitud</label>
<input class="form-control" type="text" id="latitud" name="latitud" value="<%= lugar.getLatitud()!=null?lugar.getLatitud():""%>"/>
</div>

<div class="form-group">
<label for="">Longitud</label>
<input class="form-control" type="text" id="longitud" name="longitud" value="<%= lugar.getLongitud()!=null?lugar.getLongitud():""%>"/>
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


<div class="form-group">
<label for="">Nivel</label>

<select class="form-control" name="nivel" id="nivel">
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
 <div class="btn-group center-block">
<input type="submit" class="btn btn-primary" value="grabar">
<input type="button" class="btn btn-primary" value="delete" onclick="deleteOper()">
<input type="button" class="btn btn-primary" value="limpiar" onclick="clearFields()">
</div>
</form>
</div>
<div class="col-sm-6">
        <div class="col">
	    <div class="">
	       <input id="pac-input" class="controls" type="text" placeholder="Search Box">
           <div id="googleMap" style="width:600px;height:350px;"></div>
	    </div>
	    <div class="">
	     <jsp:include page="showData.jsp" >
		    <jsp:param name="objetoId" value="<%=lugar.getId()!=null?lugar.getId():null%>" />
		    <jsp:param name="objetoTipo" value="2"/>
		 </jsp:include>
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



</body> 
</html>