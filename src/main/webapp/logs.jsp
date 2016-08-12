<%@page import="es.io.wachsam.dao.OperationLogDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %> 
    <%@ page import="org.springframework.web.context.WebApplicationContext"  %> 
    <%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"  %> 
 

   
<%

WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
OperationLogDao opedao=(OperationLogDao)context.getBean("operationLogDao");
List<OperationLog> logs= new ArrayList<OperationLog>();
logs=opedao.getAll();
%>  
<jsp:include page="cabecera.jsp"/>
<h3>Logs</h3>     

<table class="table table-striped small">
<tr><th>id</th><th>Objeto</th><th>Objeto Id</th><th>Operación</th><th>Usuario</th><th>TimeStamp</th><th>Stamp</th></tr>

<%for(OperationLog log:logs){%>


<tr>
<td><%=log.getId() %></td>
<td><%=log.getObjeto() %></td>
<td><%=log.getObjetoId()!=null?log.getObjetoId():"" %></td>
<td><%=log.getOperation() %></td>
<td><%=log.getUsuarioId() %></td>
<td><%=log.getTimestamp() %></td>
<td><%=log.getStamp()!=null?log.getStamp():"" %></td>
</tr>
<%} %>
</table>


  