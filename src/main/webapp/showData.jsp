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
<td><span data-toggle="tooltip" data-placement="top" title='<%= data.getTag1()!=null?data.getTag1().getDescripcion():""%>'><%= data.getTag1()!=null?data.getTag1().getNombre():""%></span></td>
<td><span data-toggle="tooltip" data-placement="top" title='<%= data.getTag2()!=null?data.getTag2().getDescripcion():""%>'><%= data.getTag2()!=null?data.getTag2().getNombre():"" %></td>
<td><span data-toggle="tooltip" data-placement="top" title='<%= data.getTag3()!=null?data.getTag3().getDescripcion():""%>'><%= data.getTag3()!=null?data.getTag3().getNombre():"" %></td>
<td><%= data.getValue()%></td>

</tr>
<%} %>
</table>


  