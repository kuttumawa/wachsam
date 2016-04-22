<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
<html> 


<script>
function clearFields(){
	document.getElementById("id0").value="";
    document.getElementById("id").value="";
    document.getElementById("nombre").value="";
    document.getElementById("nombreEn").value="";
    document.getElementById("texto").value="";
    document.getElementById("textoEn").value="";
    document.getElementById("direccion").value="";
    document.getElementById("lugarId").value="";
    document.getElementById("tipo").value="";
   


}
function deleteOper(){

	if(confirm('Seguro?')){
		document.getElementById("oper").value="delete";
	    document.getElementById('form2').submit();
	}
	
	
}
</script>
 
<body>
<jsp:include page="cabecera.jsp"/>
<%
Sitio sitio = (Sitio)request.getAttribute("sitio");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalSitioUpdaterForYou" method="get">
<div>
<label for="">Sitio</label>
<select name="sitioId" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Sitio> sitios =  (List<Sitio>)request.getAttribute("sitios");
          for(Sitio sitio_i:sitios){
        	  out.println("<option value=\""+sitio_i.getId()+"\">"+sitio_i.prettyPrint()+"</option>");
          }
         
%>  
</select>
</div>
</form>


<form id="form2" action="ProvisionalSitioUpdaterForYou" method="post">
<fieldset>


<div>
<label for="">Id</label>
<input type="text"  id="id0" value="<%= sitio.getId()!=null?sitio.getId():""  %>" disabled="disabled" />
<input type="hidden" id="id" name="id" value="<%= sitio.getId()!=null?sitio.getId():"" %>"  />
</div>

<div>
<label for="">Nombre</label>
<input type="text" id="nombre" name="nombre" value="<%=sitio.getNombre()!=null?sitio.getNombre():""%>"/>
</div>

<div>
<label for="">NombreEn</label>
<input type="text" id="nombreEn" name="nombreEn" value="<%= sitio.getNombreEn()!=null?sitio.getNombreEn():""%>"/>
</div>

<div>
<label for="">Dirección</label>
<input type="text" id="direccion" name="direccion" value="<%= sitio.getDireccion()!=null?sitio.getDireccion():"" %>"/>
</div>

<div>
<label for="">Texto</label>
</div>
<div>
<textarea name="texto" id="texto" cols="100" rows="4"><%= sitio.getTexto()!=null?sitio.getTexto():"" %></textarea>
</div>

<div>
<label for="">Text</label>
</div>
<div>
<textarea name="textoEn" id="textoEn" cols="100" rows="4"><%= sitio.getTextoEn()!=null?sitio.getTextoEn():"" %></textarea>
</div>

<div>
<label for="">Tipo</label>
</div><div>
<select name="tipo" id="tipo">
	<option value="0" <%=sitio.getTipo()!=null && sitio.getTipo().equals(0)?" selected":"" %>>Hospital</option>
	<option value="1" <%= sitio.getTipo()!=null && sitio.getTipo().equals(1)?" selected":""%>>Embajada</option>
	<option value="2" <%= sitio.getTipo()!=null && sitio.getTipo().equals(1)?" selected":""%>>CamaraHiperbárica</option>
</select>
</div>




<div>
<label for="">Lugar</label>
</div><div>
<select name="lugarId" id="lugar">
<option value=""></option>
<%    
			List<Lugar> lugares =  (List<Lugar>)request.getAttribute("lugares");
			for(Lugar lugar:lugares){
				  
				  if(sitio.getLugarObj()!=null && sitio.getLugarObj().getId().equals(lugar.getId())){
					  out.println("<option value=\""+lugar.getId()+"\" selected >"+lugar.getNombre()+"</option>"); 
				  }else{
				      out.println("<option value=\""+lugar.getId()+"\">"+lugar.getNombre()+"</option>");
				  }        	         	
			}
        
%> 
</select>
</div>

<div>
<label for="">Valoración</label>
</div><div>
<select name="valoracion" id="valoracion">
	<option value="-1" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(0)?" selected":"" %>>No valorado</option>
	<option value="0" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(0)?" selected":"" %>>0</option>
    <option value="1" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(1)?" selected":"" %>>1</option>
    <option value="2" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(2)?" selected":"" %>>2</option>
    <option value="3" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(3)?" selected":"" %>>3</option>
    <option value="4" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(4)?" selected":"" %>>4</option>
    <option value="5" <%=sitio.getValoracion()!=null && sitio.getValoracion().equals(5)?" selected":"" %>>5</option>
   
</select>
</div>










<input type="hidden" id="oper" name="oper"/>

<input type="submit" value="grabar">
<input type="button" value="delete" onclick="deleteOper()">
<input type="button" value="limpiar" onclick="clearFields()">
</form>
</fieldset>
<fieldset>
<jsp:include page="showData.jsp"/>
<input type="button" value="Nuevo Dato" onclick="nuevoDato('sitioId')"/> 
</fieldset>
</body> 
</html>