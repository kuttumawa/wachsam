var wachsam=(function() {

// Localize jQuery variable
var jQuery;
var context="http://viajarseguro.elasticbeanstalk.com/";
//var context="http://localhost:8080/wachsam/";
var jsonp_url = context + "Magno?callback=?";
var code='<div id="wachsam-container" style="width:#WIDTH;"><div class="caption_wachsam">#CAPTION</div><div id="mainContent" class="mainContent_wachsam" style="height:#HEIGHT"></div></div>';
var timer= 11;  //in minutes
var english = false;
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
  if(o.texto) jsonp_url += "&texto="+o.texto;
  if(o.lugar) jsonp_url += "&lugar="+o.lugar; 
  if(o.fecha) jsonp_url += "&fecha="+o.fecha; 
  if(o.tipo)  jsonp_url += "&tipo="+o.fecha;
  if(o.order)  jsonp_url += "&order="+o.order;
  if(o.height) code=code.replace("#HEIGHT",o.height);
  if(o.width) code=code.replace("#WIDTH",o.width);
  if(o.caption){
	  code=code.replace("#CAPTION",o.caption);
  }
  else code=code.replace("#CAPTION","Alertas");
  if(o.timer) timer=o.timer;
  if(o.english) english=true;
  if(o.ultimosdias) jsonp_url += "&ultimosdias="+o.ultimosdias; 
 

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
	  $.ajaxSetup({ scriptCharset: "UTF-8" , contentType: "application/json; charset=UTF-8"});
	  $.getJSON(encodeURI(jsonp_url), function(data) {
		  var codeList ='';
          if(data.length==0){
        	  codeList='<div class="noResult_wachsam">No hay resultados</div>';
          }else{
        	  codeList +='<ul class="lista_wachsam">';
        	  $.each(data, function(i, obj) {
	        	  codeList += "<li class=base_wachsam>"+createDivContent(obj) + "</li>";
	          });
	          codeList +="</ul>";
          }
          $('#mainContent').html(codeList);
        });
}
function createDivContent(o){
var a='<div class="header_wachsam"><span class="nombre_wachsam"><a href="#LINK3">#NOMBRE</a></span><span>#LUGAR</span><div class="fecha_wachsam">#FECHA</div></div>';
a +='<div class="content_wachsam">#TEXTO</div><div class="footer_wachsam"><a href="#LINK1">FUENTE</a></div>';
var r=a.replace("#NOMBRE",o.nombre).replace("#LUGAR",o.lugar).replace("#FECHA",o.fechaPubFormatted).replace("#LINK1",o.link1);
if(o.link3){
	r=r.replace("#LINK3",o.link3);
}
if(english){ 
	if(o.text!=null && o.text.length > 3) r=r.replace("#TEXTO",o.text);
	else  r=r.replace("#TEXTO",o.texto);
}
else r=r.replace("#TEXTO",o.texto);
return r;
} 


return {init : init};
})(); // We call our anonymous function immediately