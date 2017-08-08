<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
<html lang="en">
    <head>
        <title>File Upload</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <style>
      .spin {
			  -webkit-animation: spin .5s infinite linear;
			  -moz-animation: spin .5s infinite linear;
			  -o-animation: spin .5s infinite linear;
			  animation: spin .5s infinite linear;
			     -webkit-transform-origin: 50% 58%;
			         transform-origin:50% 58%;
			         -ms-transform-origin:50% 58%; /* IE 9 */
			}

			@-moz-keyframes spin {
			  from {
			    -moz-transform: rotate(0deg);
			  }
			  to {
			    -moz-transform: rotate(360deg);
			  }
			}

			@-webkit-keyframes spin {
			  from {
			    -webkit-transform: rotate(0deg);
			  }
			  to {
			    -webkit-transform: rotate(360deg);
			  }
			}

			@keyframes spin {
			  from {
			    transform: rotate(0deg);
			  }
			  to {
			    transform: rotate(360deg);
			  }
			}
      
      </style> 
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
               <%for(ObjetoSistema objetoSistema:ObjetoSistema.values()){ %>
                   <option value="<%=objetoSistema.toString()%>"><%=objetoSistema.toString() %></option>
                <%} %>
           </select>
        </div>
       <div class="form-group">
            <label for="file">File</label>
            <input  class="file-input-wrapper btn btn-info btn-xs" type="file" name="file" id="file" data-filename-placement="inside"/> 
            <span id="wait" class="glyphicon glyphicon-refresh spin" style="display:none"></span>
        </div>  
         <div class="controls form-inline">  
              <label class="control-label" for="">Separador</label>                        
			  <select class="form-control" name="separador">
                  <option value=",">,</option>
                  <option value=";">;</option>
                  <option value="#">#</option>
           </select>
		</div> 
        <div class="form-group">
            <input type="checkbox" name="actualizaObjeto" value="ok">Actualiza Objeto principal</input>       
        </div>              
        <div class="btn-group center-block">
            <input class="btn btn-primary" type="submit" value="Upload" name="upload" id="upload" onclick="$('#wait').show()"/>
        </div>
      </form>
     
     
       </div>
       <script>$('input[type=file]').bootstrapFileInput();</script>
    </body>
</html>