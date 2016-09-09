<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
    <%@ page import="es.io.wachsam.services.*"  %>
    <%@ page import="org.springframework.web.context.WebApplicationContext"  %> 
    <%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"  %> 
<html> 
<jsp:include page="cabecera.jsp"/>
<%
WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
SecurityService sec=(SecurityService)context.getBean("securityService");
Usuario usuario = (Usuario)request.getSession().getAttribute("user");
%>
<style>
#modalSearch .modal-dialog  {width:95%;}
</style>

<script>
function clearFields(){
	document.getElementById("id0").value="";
    document.getElementById("id").value="";
    document.getElementById("nombre").value="";
    document.getElementById("nombreEn").value="";
    document.getElementById("texto").value="";
    document.getElementById("textoEn").value="";
    document.getElementById("direccion").value="";
    document.getElementById("lugar").value="";
    document.getElementById("tipo").value="";
    $('tr').remove();


}
function refreshmap(data){
    var isLat=false;
    var isLon=false
	$.each(data, function(i, item) {
	       try{
		    if(item.tag.nombre==="latitud"){
			     latitud=item.value;
			     isLat=true;
		    }     
		    else if(item.tag.nombre==="longitud"){
			     longitud=item.value;
			     isLon=true;
		    }     
		}catch(err){
		}
	});
	if(isLat && isLon) initialize(latitud,longitud,2);
}
function loadFields(data){
	if(data.id)document.getElementById("id0").value=data.id;
    if(data.id)document.getElementById("id").value=data.id;
    if(data.nombre)document.getElementById("nombre").value=data.nombre;
    if(data.nombreEn)document.getElementById("nombreEn").value=data.nombreEn;
    if(data.texto)document.getElementById("texto").value=data.texto;
    if(data.textoEn)document.getElementById("textoEn").value=data.textoEn;
    if(data.direccion)document.getElementById("direccion").value=data.direccion;
    if(data.lugarObj.id)document.getElementById("lugar").value=data.lugarObj.id;
    if(data.tipo.id)document.getElementById("tipo").value=data.tipo.id;
    objetoId=data.id;
    closeSearch();
    initialize(data.lugarObj.latitud,data.lugarObj.longitud,data.lugarObj.nivel);
    callMeAfterFunction=refreshmap;
    fectchData();
   
    
   
}
function deleteOper(){

	if(confirm('Seguro?')){
		document.getElementById("oper").value="delete";
	    document.getElementById('form2').submit();
	}
	
	
}
function openSearch(){
	$('#modalSearch').modal('show');
		
}
function closeSearch(){
	$('#modalSearch').modal('hide');
		
}

$(function(){
	initialize(0,0,1);
});
</script>
 
<body>

<div class="container">



<div class="container-fluid">
  <div class="row">
    <div class="col-sm-6">
     <%
Sitio sitio = (Sitio)request.getAttribute("sitio");
Long tipoSitioFiltro=(Long)request.getAttribute("tipoSitioFiltro");
List<Data> datas=(List<Data>)request.getAttribute("datas");
String iconoClass="";
if(datas!=null){
for(Data data:datas){
	if(data.getTag()!=null && data.getTag().getNombre().equalsIgnoreCase("icono")) iconoClass=data.getValue();
}
}
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info" class="alert alert-danger">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<%    
        List<Sitio> sitios =  (List<Sitio>)request.getAttribute("sitios");
		List<TipoSitio> tipoSitios =  (List<TipoSitio>)request.getAttribute("tipoSitios");         
%>  


<h3>Sitio&nbsp;
<button type="button" class="btn btn-primary btn-sm" onclick="openSearch()">
      <span class="glyphicon glyphicon-search"></span>
</button>
</h3>


<form id="form2" action="ProvisionalSitioUpdaterForYou" method="post" role="form">



<div class="form-group">
<label for="">Id</label>
<input class="form-control" type="text"  id="id0" value="<%= sitio.getId()!=null?sitio.getId():""  %>" disabled="disabled" />
<input type="hidden" id="id" name="id" value="<%= sitio.getId()!=null?sitio.getId():"" %>"  />
<span class="<%=iconoClass%>"></span>
</div>

<div class="form-group">
<label for="">Nombre</label>
<input class="form-control" type="text" id="nombre" name="nombre" value="<%=sitio.getNombre()!=null?sitio.getNombre():""%>"/>
</div>

<div class="form-group">
<label for="">NombreEn</label>
<input class="form-control" type="text" id="nombreEn" name="nombreEn" value="<%= sitio.getNombreEn()!=null?sitio.getNombreEn():""%>"/>
</div>

<div class="form-group">
<label for="">Dirección</label>
<input class="form-control" type="text" id="direccion" name="direccion" value="<%= sitio.getDireccion()!=null?sitio.getDireccion():"" %>"/>
</div>

<div class="form-group">
<label for="">Texto</label>
</div>
<div>
<textarea class="form-control" name="texto" id="texto" cols="70" rows="4"><%= sitio.getTexto()!=null?sitio.getTexto():"" %></textarea>
</div>

<div class="form-group">
<label for="">Text</label>
<textarea class="form-control" name="textoEn" id="textoEn" cols="70" rows="4"><%= sitio.getTextoEn()!=null?sitio.getTextoEn():"" %></textarea>
</div>


<div class="form-group">
<label for="">Tipo</label>
<select class="form-control" name="tipoSitio" id="tipo">
<option value=""></option>
<%    
			for(TipoSitio tipoSitio:tipoSitios){
				  
				  if(sitio.getTipo()!=null && sitio.getTipo().getId().equals(tipoSitio.getId())){
					  out.println("<option value=\""+tipoSitio.getId()+"\" selected >"+tipoSitio.getNombre()+"</option>"); 
				  }else{
				      out.println("<option value=\""+tipoSitio.getId()+"\">"+tipoSitio.getNombre()+"</option>");
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
				  
				  if(sitio.getLugarObj()!=null && sitio.getLugarObj().getId().equals(lugar.getId())){
					  out.println("<option value=\""+lugar.getId()+"\" selected >"+lugar.getNombre()+"</option>"); 
				  }else{
				      out.println("<option value=\""+lugar.getId()+"\">"+lugar.getNombre()+"</option>");
				  }        	         	
			}
        
%> 
</select>
</div>

<div class="form-group">
<label for="">Valoración</label>
</div><div>
<select  class="form-control" name="valoracion" id="valoracion">
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
	        <div id="googleMap" style="width:500px;height:250px;"></div>
	    </div>
	    <div class="">
	      <jsp:include page="showData.jsp" >
		    <jsp:param name="objetoId" value="<%=sitio.getId()!=null?sitio.getId():null%>" />
		    <jsp:param name="objetoTipo" value="4"/>
		 </jsp:include>
	    </div>
       </div>
</div>
</div>
</div>

<!-- Modal -->
<div id="modalSearch" class="modal fade" role="dialog" >
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Buscar</h4>
      </div>
      <div class="modal-body">
             <jsp:include page="buscarSitio.jsp"/>
      </div>
      <div class="modal-footer">
       
      </div>
</div>
</div>
</div>

<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
  
<script>

function initialize(lat,lon,nivel) {
  var myCenter=new google.maps.LatLng(lat,lon);
  if(!nivel) zoomNivel = 1;
  var mapProp = {
    center:myCenter,
    zoom:nivel ,
    mapTypeId:google.maps.MapTypeId.ROADMAP
  };
 var markers = [];
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


</script>
</div>





</body> 
</html>