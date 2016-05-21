<%@page import="es.io.wachsam.dao.OperationLogDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

    
    <%@ page import="es.io.wachsam.model.*"%>
    <%@ page import="java.util.*"  %> 
    <%@ page import="org.springframework.web.context.WebApplicationContext"  %> 
    <%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"  %> 
 

   
<%

WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
OperationLogDao operationLogDao=(OperationLogDao)context.getBean("operationLogDao");
List<Object[]> res=operationLogDao.accesosAgrupadosPorUsuarioDia();
%> 

<%for(Object o : res){%>
  <%=o %>
<%}%>

