<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<head>
<script src="http://maps.googleapis.com/maps/api/js" type="text/javascript"></script>
<!-- 
<script src="http://localhost:8080/wachsam/js/wachsam.js" type="text/javascript"></script>
<script src="http://viajarseguro.elasticbeanstalk.com/js/wachsam.js" type="text/javascript"></script>
 -->
 
<script src="http://localhost:8080/wachsam/js/wachsam.js" type="text/javascript"></script></head>  

 <script>

 function mapini(){
	var mapProp = {center:{lat:20,lng:3},
                   zoom:2,
    	           mapTypeId:google.maps.MapTypeId.ROADMAP
    	  };
	var map=new google.maps.Map(document.getElementById("wachsam-map"),mapProp);
	wachsam.init({map:map,caducadas:"true",order:"fecha",height:"500px",width:"700px",caption:"<img src=\"css/viajarseguro.jpg\"/>Alertas"});
}

google.maps.event.addDomListener(window, 'load', mapini); 

</script>




<div id="wachsam-map" style="width:1000px;height:700px;"></div>
<div id="wachsam-widget-container"/> 