<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %> 
    

<script>

function nuevoDato(param){
	var id=document.getElementById('id').value;
	var url='ProvisionalDataUpdaterForYou?'+param+'=' + id;
	if(id)location.href=url;
}
</script>    
<%
List<Data> datas= new ArrayList<Data>();
if(request.getAttribute("datas")!=null){
datas= (List<Data>)request.getAttribute("datas");
}
%>  
<h3>Data</h3>     
<h4>Resultados:&nbsp; <%=datas.size() %></h4>
<table class="table table-striped small">
<tr><th>id</th><th></th><th></th><th></th><th>valor</th></tr>

<%for(Data data:datas){%>
<tr>
<td><a href="ProvisionalDataUpdaterForYou?dataId=<%=data.getId()%>"><%=data.getId()%></a></td>
<td><span data-toggle="tooltip" data-placement="top" title='<%= data.getTag()!=null?data.getTag().getDescripcion():""%>'><%= data.getTag()!=null?data.getTag().getNombre():""%></span></td>
<td><span data-toggle="tooltip" data-placement="top" title='<%= data.getObjetoConnected()!=null?data.getObjetoConnected():""%>'><%= data.getObjetoConnected()!=null?data.getObjetoConnected():""%></span></td>
<td><span data-toggle="tooltip" data-placement="top" title='<%= data.getConnectToId()!=null?data.getConnectToId():""%>'><%= data.getConnectToId()!=null?data.getConnectToId():""%></span></td>

<td><%= data.getValue()%></td>

</tr>
<%} %>
</table>


  