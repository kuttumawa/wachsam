<!DOCTYPE html>
<%@ page import="java.net.*" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.Map" %>
<jsp:include page="cabecera.jsp"/>
<%
boolean flag=false;
String strUrl =null;
if(System.getProperty("DASHBOARD_URL")!=null){
	strUrl=System.getProperty("DASHBOARD_URL");
	flag=true;
try {
    URL url = new URL(strUrl);
    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
    urlConn.connect();
  
} catch (IOException e) {
	flag=false;
}
}
if(flag){ %>
 <iframe style="border:0" src="<%=strUrl%>" height="800" width="1500"></iframe>
<%}else{%>
	 <jsp:include page="barcodeUserGraph.jsp"/>
<%}%>





