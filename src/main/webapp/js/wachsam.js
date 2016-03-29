var wachsam=(function() {

// Localize jQuery variable
var jQuery;
//var context="http://viajarseguro.elasticbeanstalk.com/";
var context="http://localhost:8080/wachsam/";
var img_red_ball="<img src=\"" + context + "css/redball.ico\"/>";
var img_orange_ball="<img src=\"" + context + "css/orangeball.ico\"/>";
var img_green_ball="<img src=\"" + context + "css/greenball.ico\"/>";

var jsonp_url = context + "Magno?callback=?";
var code='<div id="wachsam-container" style="width:#WIDTH;"><div class="caption_wachsam">#CAPTION</div><div id="mainContent" class="mainContent_wachsam" style="height:#HEIGHT"></div></div>';
var timer= 11;  //in minutes
var english = false;
var lugaresA=[];
var map;
/******** Load jQuery if not present *********/


/******** Called once jQuery has loaded ******/
function scriptLoadHandler() {
    // Restore $ and window.jQuery to their previous values and store the
    // new jQuery in our local jQuery variable
    jQuery = window.jQuery.noConflict(true);
    // Call our main function
    main(); 
}

	  

function init(o){
  map=o.map;	
  if(o.texto) jsonp_url += "&texto="+o.texto;
  if(o.lugar) jsonp_url += "&lugar="+o.lugar; 
  if(o.fecha) jsonp_url += "&fecha="+o.fecha; 
  if(o.tipo)  jsonp_url += "&tipo="+o.fecha;
  if(o.order)  jsonp_url += "&order="+o.order;
  if(o.english)  jsonp_url += "&english=on";
  if(o.height) code=code.replace("#HEIGHT",o.height);
  if(o.width) code=code.replace("#WIDTH",o.width);
  if(o.caption){
	  code=code.replace("#CAPTION",o.caption);
  }
  else{
	  if(o.english)
	      code=code.replace("#CAPTION","Alerts");
	  else
		  code=code.replace("#CAPTION","Alertas");
  }
  if(o.timer) timer=o.timer;
  if(o.english) english=true;
  if(o.ultimosdias) jsonp_url += "&ultimosdias="+o.ultimosdias; 
  if(o.caducadas) jsonp_url += "&caducadas="+o.caducadas; 
 

 if (window.jQuery === undefined || window.jQuery.fn.jquery !== '1.4.2') {
    var script_tag = document.createElement('script');
    script_tag.setAttribute("type","text/javascript");
    script_tag.setAttribute("src",
        "http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js");
    if (script_tag.readyState) {
      script_tag.onreadystatechange = function () { // For old versions of IE
          if (this.readyState == 'complete' || this.readyState == 'loaded') {
              scriptLoadHandler();
          }
      };
    } else {
      script_tag.onload = scriptLoadHandler;
    }
    // Try to find the head, otherwise default to the documentElement
    (document.getElementsByTagName("head")[0] || document.documentElement).appendChild(script_tag);
} else {
    // The jQuery version on the window is the one we want to use
    jQuery = window.jQuery;
    main(); 
}

}

/******** Our main function ********/


function main() { 
    jQuery(document).ready(function($) { 
    	
    	
        /******* Load CSS *******/
        var css_link = $("<link>", { 
            rel: "stylesheet", 
            type: "text/css", 
            href: context+ "css/alert.css" 
        });
        css_link.appendTo('head');   
        $('#wachsam-widget-container').html(code);
        fetchContent($);
        (function poll(){
        	   setTimeout(function(){
        		   fetchContent();
        		   poll();
        	  }, timer*6000);
        	})();
    });
}

function fetchContent($){
	  //$.ajaxSetup({ scriptCharset: "UTF-8" , contentType: "application/json; charset=UTF-8"});
	  $.getJSON(encodeURI(jsonp_url), function(data) {
		  var codeList ='';
          if(data.length==0){
        	  codeList='<div class="noResult_wachsam">No hay resultados</div>';
          }else{
        	  codeList +='<ul class="lista_wachsam">';
        	  $.each(data, function(i, obj) {
	        	  var _=createDivContent(obj);
        		  codeList += "<li class=base_wachsam>"+_+ "</li>";
	        	  addMarker(obj,_);
	          });
	          codeList +="</ul>";
          }
          $('#mainContent').html(codeList);
        });
}
function disperse(c,level){
	var grados_nivel=[4,4,3,2,2,0.3,0];
	return parseFloat(c) + (Math.random() < 0.5 ? -1 : 1) * Math.random() * (grados_nivel[level]?grados_nivel[level]:0) ;
}
function addMarker(obj,contenido){
	if(!map) return;
	if(!obj.lugarObj || obj.lugarObj.id==1)return;
	try{
	var dispersion=false;
	if(obj.lugarObj.nombre in lugaresA) {
		dispersion=true;		
	}else{
		lugaresA[obj.lugarObj.nombre]=1;
	}
	
	 if(dispersion)
		coor = new google.maps.LatLng(disperse(obj.lugarObj.latitud,obj.lugarObj.nivel),disperse(obj.lugarObj.longitud,obj.lugarObj.nivel));
	 else
	    coor = new google.maps.LatLng(obj.lugarObj.latitud,obj.lugarObj.longitud);
	var infowindow = new google.maps.InfoWindow({
		      content: contenido
		  });
	var marker;
	if(obj.tipo=='severa'){
	marker=new google.maps.Marker({
	    position: coor,
	    map: map,
	    title: obj.nombre + ' (' + obj.lugar + ' ' + obj.fechaPubFormatted + ')',
	    icon: context+'css/redball.ico'
	  });
	}else if(obj.tipo=='normal'){
		marker=new google.maps.Marker({
		    position: coor,
		    map: map,
		    title: obj.nombre + ' (' + obj.lugar + ' ' + obj.fechaPubFormatted + ')',
		    icon: context+'css/orangeball.ico'
		  });
		
	}else{
		marker=new google.maps.Marker({
		    position: coor,
		    map: map,
		    title: obj.nombre + ' (' + obj.lugar + ' ' + obj.fechaPubFormatted + ')',
		    icon: context+'css/greenball.ico'
		  });
	}
	google.maps.event.addListener(marker, 'click', function() {
	    infowindow.open(map,marker);
	  });
	}catch(err){
		console.log(obj.nombre);
	}
	
}

function createDivContent(o){
var fuente_texto="Fuente";
if(english) fuente_texto="Source";
var a='<div class="header_wachsam"><span class="nombre_wachsam"><a href="#LINK3">#NOMBRE</a></span><span>#TIPO #LUGAR </span><div class="fecha_wachsam">#FECHA</div></div>';
a +='<div class="content_wachsam">#TEXTO</div><div class="footer_wachsam"><a href="#LINK1">'+fuente_texto+'</a>#LINK2</div>';
var r=a.replace("#FECHA",o.fechaPubFormatted).replace("#LINK1",o.link1);
if(o.link2){
  r=r.replace("#LINK2",'<a style="margin-left:1em" href="'+o.link2+'">'+fuente_texto+'</a>');
}else{
  r=r.replace("#LINK2",'');
}
if(o.link3){
	r=r.replace("#LINK3",o.link3);
}
if(english){ 
	if(o.text!=null && o.text.length > 3) r=r.replace("#TEXTO",o.text);
	else  r=r.replace("#TEXTO",o.texto);
	if(o.nombreEn!=null && o.nombreEn.length > 1) r=r.replace("#NOMBRE",o.nombreEn);
	else if(o.peligro.nombreEn!=null && o.peligro.nombreEn.length > 1) r=r.replace("#NOMBRE",o.peligro.nombreEn);
	else  r=r.replace("#NOMBRE",o.nombre);
	if(o.lugarObj && o.lugarObj.nombreEn && o.lugarObj.nombreEn.length > 1) r=r.replace("#LUGAR",o.lugarObj.nombreEn);
	else if(o.lugarObj)  r=r.replace("#LUGAR",o.lugarObj.nombre);

}
else{
	r=r.replace("#TEXTO",o.texto);
	r=r.replace("#NOMBRE",o.nombre);
	if(o.lugarObj && o.lugarObj.nombre && o.lugarObj.nombre.length > 1)
	     r=r.replace("#LUGAR",o.lugarObj.nombre);
	else  r=r.replace("#LUGAR",o.lugar);
}

if(o.tipo=='severa'){
	    r=r.replace("#TIPO",img_red_ball);
}else if(o.tipo=='normal'){
		r=r.replace("#TIPO",img_orange_ball);
}else{
		r=r.replace("#TIPO",img_green_ball);
}

return r;
} 


return {init : init};
})(); // We call our anonymous function immediately