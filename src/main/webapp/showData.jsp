<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>   
    
<%
List<Data> datas= new ArrayList<Data>();
if(request.getAttribute("datas")!=null){
datas= (List<Data>)request.getAttribute("datas");
}
%>  
<h3>Data</h3>     
<h4>Resultados:&nbsp; <%=datas.size() %></h4>
<table>
<tr><th>id</th><th></th><th></th><th></th><th>valor</th></tr>

<%for(Data data:datas){%>
<tr>
<td><a href="ProvisionalDataUpdaterForYou?dataId=<%=data.getId()%>"><%=data.getId()%></a></td>
<td><%= data.getTag1()!=null?data.getTag1().getNombre():"" %></td>
<td><%= data.getTag2()!=null?data.getTag2().getNombre():"" %></td>
<td><%= data.getTag3()!=null?data.getTag3().getNombre():"" %></td>
<td><%= data.getValue()%></td>

</tr>
<%} %>
</table>   