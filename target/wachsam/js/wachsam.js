var wachsam=(function() {

// Localize jQuery variable
var jQuery;
var jsonp_url = "http://localhost:8080/wachsam/Magno?callback=?";

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
            href: "css/alert.css" 
        });
        css_link.appendTo('head');          

        /******* Load HTML *******/
          $.getJSON(jsonp_url, function(data) {
          var code='<div id="wachsam-container" style="width:600px;"><div class="caption">Alertas</div><div class="mainContent">';
          code+='<ul>';
          $.each(data, function(i, obj) {
                code += "<li id=base>"+createDivContent(obj) + "</li>";
          });
          code +="</ul></div></div>";
          $('#example-widget-container').html(code);
        });
    });
}

function createDivContent(o){
var a='<div class="header"><span class="nombre">#NOMBRE</span><span>#LUGAR</span><div class="fecha">#FECHA</div></div>';
a +='<div class="content">#TEXTO</div><div class="footer"><a href="#LINK1">FUENTE</a></div>';
return a.replace("#NOMBRE",o.nombre).replace("#LUGAR",o.lugar).replace("#FECHA",o.fechaPubFormatted).replace("#TEXTO",o.texto).replace("#LINK1",o.link1);
} 


return {init : init};
})(); // We call our anonymous function immediately