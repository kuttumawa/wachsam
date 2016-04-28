<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
<html lang="en">
    <head>
        <title>File Upload</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
    <div class="container">
    <jsp:include page="cabecera.jsp"/>
      <form method="POST" action="upload" enctype="multipart/form-data" >
        <div>
           <label for="objeto">Objeto Principal</label>
           <select name="objeto">
                <option value="alerta">Alerta</option>
                <option value="peligro">Peligro</option>
                <option value="lugar">Lugar</option>
                <option value="factor">Factor</option>
                <option value="sitio">Sitio</option>
                <option value="airport">Airport</option>
           </select>
        </div>
            File:
            <input type="file" name="file" id="file" /> <br/>
            
           
            </br>
            <input type="submit" value="Upload" name="upload" id="upload" />
      </form>
      ------
      <%if(request.getAttribute("resultado")!=null){ %>
         <div id="">
             <%    
          List<String> resultado =  (List<String>)request.getAttribute("resultado");
          for(String s:resultado){
        	  out.println("<div>"+s+"</div>");
          }
         
%>
         </div>
       <%}%>
       </div>
    </body>
</html>