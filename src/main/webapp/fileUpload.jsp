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
     <script src="js/bootstrap.file-input.js"></script>
     
      <%if(request.getAttribute("resultado")!=null){ %>
         <div id="info" class="alert alert-danger">
        <%    
          List<String> resultado =  (List<String>)request.getAttribute("resultado");
          for(String s:resultado){
        	  out.println("<div>"+s+"</div>");
          }
         
        %> 
         </div>
       <%}%>
     
     
      <form method="POST" action="upload" enctype="multipart/form-data"  role="form">
        <div class="form-group">
           <label for="objeto">Objeto Principal</label>
           <select class="form-control" name="objeto">
                <option value="alerta">Alerta</option>
                <option value="peligro">Peligro</option>
                <option value="lugar">Lugar</option>
                <option value="factor">Factor</option>
                <option value="sitio">Sitio</option>
                <option value="airport">Airport</option>
                <option value="data">Data</option>
           </select>
        </div>
        <div class="form-group">
            <label for="file">File</label>
            <input  class="file-input-wrapper btn btn-info btn-xs" type="file" name="file" id="file" data-filename-placement="inside"/> 
        </div>    
           
        <div class="btn-group center-block">
            <input class="btn btn-primary" type="submit" value="Upload" name="upload" id="upload" />
         </div>
      </form>
     
     
       </div>
       <script>$('input[type=file]').bootstrapFileInput();</script>
    </body>
</html>