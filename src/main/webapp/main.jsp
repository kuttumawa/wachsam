<!DOCTYPE html>
<%@ page import="java.net.*" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.Map" %>
<jsp:include page="cabecera.jsp"/>
<%
boolean flag=false;
Map<String,String> env=System.getenv();
String strUrl =null;
if(env.get("DASHBOARD_URL")!=null){
	strUrl=env.get("DASHBOARD_URL");
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
 <iframe src="<%=strUrl%>" height="600" width="1500"></iframe>
<%}else{%>
	 <jsp:include page="barcodeUserGraph.jsp"/>
<%}%>





