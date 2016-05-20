<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %> 
    

--------------------------------------------

<%= request.getParameter("objetoId")%>
<%= request.getParameter("objetoTipo")%>

--------------------------------------------




<script>
var objetoId = <%= request.getParameter("objetoId")%>
var objetoTipo = <%= request.getParameter("objetoTipo")%>
function fectchData(){
//var url= "DataServletJSON?objetoId="+objetoId+"&objetoTipo="+objetoTipo+"&oper=getAllForObject";
var url="http://localhost:8080/wachsam/DataServletJSON?objetoId=1376424326&objetoTipo=0&oper=getAllForObject";
$.get(url,function (data){
	console.log(data);
	$.each(data, function(i, item) {
	    alert(item);
	    var row = $('<tr></tr>').addClass('bar');
	    row.append('<td><span data-toggle=\'tooltip\' data-placement=\'top\' title=\''+ item.tag.descripcion +'\'>'+item.tag.nombre+'</td>');
	    row.append('<td>'+ item.objetoId +'</td>');
	    row.append('<td>'+ item.objetoTipo +'</td>');
	    $('tbody').append(row);
	});
	
},"json");

	
}



fectchData();
</script>

<h3>Data</h3>     

<table class="table table-striped small">
<tr><th>id</th><th>tag</th><th>valor</th></tr>
<tbody>
</tbody>
</table>