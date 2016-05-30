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
	document.getElementById("text").value="";
	document.getElementById("texto").value="";
	document.getElementById("link1").value="";
	document.getElementById("link2").value="";
	document.getElementById("link3").value="";
	document.getElementById("oper").value="";
	document.getElementById("lugar").value="";
	document.getElementById("peligro").value="";
	document.getElementById("fechaPub").value="";
	document.getElementById("tipo").value="";
	document.getElementById("caducidad").value="";
	document.getElementById("fuente").value="";
	$('tr').remove()
}
function deleteOper(){

	if(confirm('Seguro?')){
		document.getElementById("oper").value="delete";
	    document.getElementById('form2').submit();
	}
	
	
}

function clonar(){
	var tx='id   <CLONADO de la alerta' + $('#id').val()+'>';
	$('#idLabel').text(tx).css('color', 'red')
	$('#id,#id0').val('');
}
</script>
 
<body>
<jsp:include page="cabecera.jsp"/>
<div class="container">


<div class="container-fluid">
  <div class="row">
    <div class="col-sm-7">
      

<%
Alert alert = (Alert)request.getAttribute("alert");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info" class="alert alert-danger">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalAlertUpdaterForYou" method="get" role="form">
<div>
<label for="">Alerta</label>
<select class="form-control" name="alert" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Alert> alertas =  (List<Alert>)request.getAttribute("alertas");
          for(Alert alert_i:alertas){
        	  out.println("<option value=\""+alert_i.getId()+"\">"+alert_i.prettyPrint()+"</option>");
          }
         
%> 
</select>
</div>
</form>


<form id="form2" action="ProvisionalAlertUpdaterForYou" method="post" role="form">



<div class="form-group">
<label for=""><span id="idLabel">Id</span></label>
<input class="form-control" type="text"  id="id0" value="<%= alert.getId()!=null?alert.getId():""  %>" disabled="disabled" />
<input type="hidden" id="id" name="id" value="<%= alert.getId()!=null?alert.getId():"" %>"  />
</div>

<div class="form-group">
<label for="">Nombre</label>
<input class="form-control" type="text" id="nombre" name="nombre" value="<%= alert.getNombre()!=null?alert.getNombre():"" %>"/>
</div>

<div class="form-group">
<label for="">Peligro</label>


<select class="form-control" name="peligro" id="peligro">
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

<div class="form-group">
<label for="">Lugar</label>

<select class="form-control" name="lugar" id="lugar">
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



<%
class Tipo{
	public String id;
	public String texto;
	public Tipo(String id,String texto){
		this.id=id;
		this.texto=texto;
	}
}	
	List<Tipo> tipos = new ArrayList<Tipo>();
	tipos.add(new Tipo("justInfo","Just Information you may ignore"));
	tipos.add(new Tipo("informativa","Level 1 Watch, Practice Usual Precautions"));
	tipos.add(new Tipo("normal","Level 2 Alert, Practice Enhanced Precautions"));
	tipos.add(new Tipo("severa","Level 3 Warning, Avoid Nonessential Travel"));
	

%>


<div class="form-group">
<label for="">Tipo<a href="http://wwwnc.cdc.gov/travel/notices#warning" ><span class="glyphicon glyphicon-question-sign"></span></a></label>

<select class="form-control" name="tipo" id="tipo">
<%
for(Tipo tipo_i:tipos){
	  if(alert.getTipo()!=null && alert.getTipo().equals(tipo_i.id)){
		  out.println("<option value=\""+tipo_i.id+"\" selected >"+tipo_i.texto+"</option>"); 
	  }else{
	      out.println("<option value=\""+tipo_i.id+"\">"+tipo_i.texto+"</option>");
	  }
  }
%>
</select> 
</div>

<div class="form-group">

<label for="">Caducidad (en días,-1 permanente)</label><%=alert!=null && alert.isCaducado()?"<span class=\"label label-danger\">&nbsp;CADUCADA</span>":"<span class=\"label label-success\">&nbsp;ACTIVA</span>"%>
<input class="form-control" type="text" id="caducidad" name="caducidad" value="<%=alert.getCaducidad()!=null?alert.getCaducidad():-1%>"/>
</div>

<div class="form-group">
<label for="">Fecha</label>
<input class="form-control" type="date" id="fechaPub" name="fechaPub" value="<%=alert.getFechaPubFormattedForDateHtmlInput()!=null?alert.getFechaPubFormattedForDateHtmlInput():""  %>"/>
</div>



<div class="form-group">
<label for="">Texto</label><br>
<textarea class="form-control" name="texto" id="texto" cols="70" rows="4"><%= alert.getTexto()!=null?alert.getTexto():"" %></textarea>
</div>

<div class="form-group">
<label for="">Text</label><br>
<textarea class="form-control" name="text" id="text" cols="70" rows="4"><%= alert.getText()!=null?alert.getText():"" %></textarea>
</div>

<div class="form-group">
<label for="">link1</label>
<input class="form-control" type="text" id="link1" name="link1" value="<%= alert.getLink1()!=null?alert.getLink1():"" %>"/>
</div>

<div class="form-group">
<label for="">link2</label>
<input class="form-control" type="text" id="link2" name="link2" value="<%= alert.getLink2()!=null?alert.getLink2():"" %>"/>
</div>

<div class="form-group">
<label for="">link3</label>
<input class="form-control" type="text" id="link3" name="link3" value="<%= alert.getLink3()!=null?alert.getLink3():"" %>"/>
</div>

<div class="form-group">
<label for="">Fuente</label>
<select class="form-control" name="fuente">
<option value=""></option>
<%    
          List<Fuente> fuentes =  (List<Fuente>)request.getAttribute("fuentes");
          for(Fuente fuente_i:fuentes){
        	  if(alert.getFuente()!=null && alert.getFuente().getId().equals(fuente_i.getId())){
        		  out.println("<option value=\""+fuente_i.getId()+"\" selected >"+fuente_i.getNombre()+"</option>"); 
        	  }else{
        	      out.println("<option value=\""+fuente_i.getId()+"\">"+fuente_i.getNombre()+"</option>");
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
<input type="button" class="btn btn-primary"  onclick="clonar()" value="clonar">

</div>
</form>
</div>
     <div class="col-sm-5">
        <div class="col">
	    <div class="">
	       <input id="pac-input" class="controls" type="text" placeholder="Search Box">
           <div id="googleMap" style="width:550px;height:300px;"></div>
	    </div>
	    <div class="">
	     <jsp:include page="showData.jsp" >
		    <jsp:param name="objetoId" value="<%=alert.getId()!=null?alert.getId():null%>" />
		    <jsp:param name="objetoTipo" value="0"/>
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
  var myCenter=new google.maps.LatLng(<%= alert.getLugarObj()!=null && alert.getLugarObj().getLatitud()!=null && alert.getLugarObj().getLatitud().length()>0 ?alert.getLugarObj().getLatitud():"0"%>,<%= alert.getLugarObj()!=null && alert.getLugarObj().getLongitud()!=null && alert.getLugarObj().getLatitud().length()>0?alert.getLugarObj().getLongitud():"0"%>);
  var mapProp = {
    center:myCenter,
    zoom:<%= alert.getLugarObj()!=null && alert.getLugarObj().getNivel()!=null?alert.getLugarObj().getNivel():"1"%>,
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