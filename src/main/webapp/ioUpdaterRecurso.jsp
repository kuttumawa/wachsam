<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
    <%@ page import="com.google.common.net.MediaType"%>
<html lang="en">
    <head>
        <title>Recurso</title>
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
      <script>
function clearFields(){
	document.getElementById("id0").value="";
    document.getElementById("id").value="";
	document.getElementById("nombre").value="";
	document.getElementById("nombreEn").value="";
	document.getElementById("texto").value="";
	document.getElementById("textoEn").value="";
	document.getElementById("oper").value="";
	$('tr').remove()
}
function deleteOper(){
	if(confirm('Seguro?')){
		document.getElementById("oper").value="delete";
	    document.getElementById('recursoForm1').submit();
	}
	
}
function save(){
		document.getElementById("oper").value="";
		$('#wait').show();
		document.getElementById('recursoForm1').submit();	
}
function download(){
	document.getElementById("oper").value="download";
	document.getElementById('recursoForm1').submit();	
}
</script>
    </head>
    <body>
    <div class="container">
    <jsp:include page="cabecera.jsp"/>
     <script src="js/bootstrap.file-input.js"></script>
     <% Recurso recurso = (Recurso)request.getAttribute("recurso");%>
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
     
     
      <form method="POST" action="recurso" enctype="multipart/form-data"  role="form" id="recursoForm1">
      
      <div class="form-group">
		<label for="">Id</label>
		<input class="form-control" type="text"  id="id0" value="<%= recurso.getId()!=null?recurso.getId():""  %>" disabled="disabled" />
		<input type="hidden" name="recursoId" id="recursoId" value="<%= recurso.getId()!=null?recurso.getId():"" %>"  />
		<input type="hidden" name="oper" id="oper"/> 
      </div>
      
      <div class="form-group">
          <label for="">Nombre</label>
           <input class="form-control" type="text" id="nombre" name="nombre" value="<%= recurso.getNombre()!=null?recurso.getNombre():"" %>"/>
      </div>

	  <div class="form-group">
		<label for="">Descripción</label><br>
		<textarea class="form-control" name="descripcion" id="descripcion" cols="70" rows="4"><%= recurso.getDescripcion()!=null?recurso.getDescripcion():"" %></textarea>
	  </div>
      
        <div class="form-group">
           <label for="objeto">Formato</label>
           <select class="form-control" name="formato">
              <option value="<%=MediaType.ANY_TYPE%>" <%=recurso.getFormato()!=null && recurso.getFormato().equals(MediaType.ANY_TYPE)?"checked":""%>></option>
              <option value="<%=MediaType.ANY_TEXT_TYPE%>" <%=recurso.getFormato()!=null && recurso.getFormato().equals(MediaType.ANY_TEXT_TYPE.toString())?"selected":""%>>Texto</option>
              <option value="<%=MediaType.ANY_IMAGE_TYPE%>" <%=recurso.getFormato()!=null && recurso.getFormato().equals(MediaType.ANY_IMAGE_TYPE.toString())?"selected":""%>>Imagen</option>
              <option value="<%=MediaType.ANY_VIDEO_TYPE%>" <%=recurso.getFormato()!=null && recurso.getFormato().equals(MediaType.ANY_VIDEO_TYPE.toString())?"selected":""%>>Video</option>
              <option value="<%=MediaType.ANY_AUDIO_TYPE%>" <%=recurso.getFormato()!=null && recurso.getFormato().equals(MediaType.ANY_AUDIO_TYPE.toString())?"selected":""%>>Audio</option>
              <option value="<%=MediaType.MICROSOFT_EXCEL%>" <%=recurso.getFormato()!=null && recurso.getFormato().equals(MediaType.MICROSOFT_EXCEL.toString())?"selected":""%>>Excel</option>
              <option value="<%=MediaType.MICROSOFT_POWERPOINT%>" <%=recurso.getFormato()!=null && recurso.getFormato().equals(MediaType.MICROSOFT_POWERPOINT.toString())?"selected":""%>>PowerPoint</option>
              <option value="<%=MediaType.MICROSOFT_WORD%>" <%=recurso.getFormato()!=null && recurso.getFormato().equals(MediaType.MICROSOFT_WORD.toString())?"selected":""%>>Word</option>
              <option value="<%=MediaType.PDF%>"  <%=recurso.getFormato()!=null && recurso.getFormato().equals(MediaType.PDF.toString())?"selected":""%>>Pdf</option>
           </select>
        </div>
        <div class="form-group">
            <label for="file">File</label>  <em></em>
            <%if(recurso.getNombre()!=null){ %>
             <button type="button" class="btn btn-default btn-xs" onclick="download()"><%=recurso.getNombre()%></button>       
            <%}%>
            <input  class="file-input-wrapper btn btn-info btn-xs" type="file" name="file" id="file" data-filename-placement="inside"/> 
            <span id="wait" class="glyphicon glyphicon-refresh spin" style="display:none"></span>
        </div>  
        <div class="form-group">
            <input type="checkbox" name="s3Publico" value="true" <%=recurso.getS3Publico()!=null && recurso.getS3Publico()?"checked":""%>>Recurso Público</input>
            <% if(recurso.getUri()!=null){%>
			    	   <a style="font-size: 0.8em" href="<%=recurso.getUri()%>"><%=recurso.getUri()%></a>			    	  
            <%}%>       
        </div>              

        <div class="btn-group center-block">
			<input type="button" class="btn btn-primary" value="save" onclick="save()">
			<input type="button" class="btn btn-primary" value="delete" onclick="deleteOper()">
			<input type="button" class="btn btn-primary" value="limpiar" onclick="clearFields()">
        </div>
      </form>
     
      </div>
       <script>$('input[type=file]').bootstrapFileInput();</script>
    </body>
</html>
