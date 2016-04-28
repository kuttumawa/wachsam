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
	document.getElementById("text").value="";
	document.getElementById("texto").value="";
	document.getElementById("link1").value="";
	document.getElementById("link2").value="";
	document.getElementById("link3").value="";
	document.getElementById("oper").value="";
	document.getElementById("lugar").value="";
	document.getElementById("peligro").value="";
	document.getElementById("fechaPub").value="";
	document.getElementById("tipo").value="";
	document.getElementById("caducidad").value="";
	document.getElementById("fuente").value="";
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
<div class="container">
<%
Alert alert = (Alert)request.getAttribute("alert");
%>

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info" class="alert alert-danger">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>



<form id="form1" action="ProvisionalAlertUpdaterForYou" method="get">
<div>
<label for="">Alerta</label>
<select name="alert" onchange="document.getElementById('form1').submit();">
<option value=""></option>
<%    
          List<Alert> alertas =  (List<Alert>)request.getAttribute("alertas");
          for(Alert alert_i:alertas){
        	  out.println("<option value=\""+alert_i.getId()+"\">"+alert_i.prettyPrint()+"</option>");
          }
         
%> 
</select>
</div>
</form>


<form id="form2" action="ProvisionalAlertUpdaterForYou" method="post">
<fieldset>


<div>
<label for="">Id</label>
<input type="text"  id="id0" value="<%= alert.getId()!=null?alert.getId():""  %>" disabled="disabled" />
<input type="hidden" id="id" name="id" value="<%= alert.getId()!=null?alert.getId():"" %>"  />
</div>

<div>
<label for="">Nombre</label>
<input type="text" id="nombre" name="nombre" value="<%= alert.getNombre()!=null?alert.getNombre():"" %>"/>
</div>

<div>
<label for="">Peligro</label>
</div><div>

<select name="peligro" id="peligro">
<option value=""></option>
<%    
          List<Peligro> peligros =  (List<Peligro>)request.getAttribute("peligros");
          for(Peligro peligro:peligros){
        	  
        	  if(alert.getPeligro()!=null && alert.getPeligro().getId()!=null && alert.getPeligro().getId().equals(peligro.getId())){
        		  out.println("<option value=\""+peligro.getId()+"\" selected >"+peligro.getNombre()+"</option>"); 
        	  }else{
        	      out.println("<option value=\""+peligro.getId()+"\">"+peligro.getNombre()+"</option>");
        	  }        	         	
          }
         
%> 
</select>
</div>

<div>
<label for="">Lugar</label>
</div><div>
<select name="lugar" id="lugar">
<option value=""></option>
<%    
			List<Lugar> lugares =  (List<Lugar>)request.getAttribute("lugares");
			for(Lugar lugar:lugares){
				  
				  if(alert.getLugarObj()!=null && alert.getLugarObj().getId()!=null && alert.getLugarObj().getId().equals(lugar.getId())){
					  out.println("<option value=\""+lugar.getId()+"\" selected >"+lugar.getNombre()+"</option>"); 
				  }else{
				      out.println("<option value=\""+lugar.getId()+"\">"+lugar.getNombre()+"</option>");
				  }        	         	
			}
        
%> 
</select>
</div>



<%
class Tipo{
	public String id;
	public String texto;
	public Tipo(String id,String texto){
		this.id=id;
		this.texto=texto;
	}
}	
	List<Tipo> tipos = new ArrayList<Tipo>();
	tipos.add(new Tipo("informativa","Level 1 Watch, Practice Usual Precautions"));
	tipos.add(new Tipo("normal","Level 2 Alert, Practice Enhanced Precautions"));
	tipos.add(new Tipo("severa","Level 3 Warning, Avoid Nonessential Travel"));
	

%>


<div>
<label for="">Tipo</label>
</div><div>
<select name="tipo" id="tipo">
<%
for(Tipo tipo_i:tipos){
	  if(alert.getTipo()!=null && alert.getTipo().equals(tipo_i.id)){
		  out.println("<option value=\""+tipo_i.id+"\" selected >"+tipo_i.texto+"</option>"); 
	  }else{
	      out.println("<option value=\""+tipo_i.id+"\">"+tipo_i.texto+"</option>");
	  }
  }
%>
</select> <a href="http://wwwnc.cdc.gov/travel/notices#warning" >ver</a>
</div>

<div>

<label for="">Caducidad (en días,-1 permanente)</label><%=alert!=null && alert.isCaducado()?"<span class=\"label label-danger\">&nbsp;CADUCADA</span>":"<span class=\"label label-success\">&nbsp;ACTIVA</span>"%>
<input type="text" id="caducidad" name="caducidad" value="<%=alert.getCaducidad()!=null?alert.getCaducidad():-1%>"/>
</div>

<div>
<label for="">Fecha (dd/mm/yyyy)</label>
<input type="text" id="fechaPub" name="fechaPub" value="<%=alert.getFechaPubFormatted()!=null?alert.getFechaPubFormatted():""  %>"/>
</div>



<div>
<label for="">Texto</label><br>
<textarea name="texto" id="texto" cols="100" rows="4"><%= alert.getTexto()!=null?alert.getTexto():"" %></textarea>
</div>

<div>
<label for="">Text</label><br>
<textarea name="text" id="text" cols="100" rows="4"><%= alert.getText()!=null?alert.getText():"" %></textarea>
</div>

<div>
<label for="">link1</label>
<input type="text" id="link1" name="link1" value="<%= alert.getLink1()!=null?alert.getLink1():"" %>"/>
</div>

<div>
<label for="">link2</label>
<input type="text" id="link2" name="link2" value="<%= alert.getLink2()!=null?alert.getLink2():"" %>"/>
</div>

<div>
<label for="">link3</label>
<input type="text" id="link3" name="link3" value="<%= alert.getLink3()!=null?alert.getLink3():"" %>"/>
</div>

<div>
<label for="">Fuente</label>
<select name="fuente">
<option value=""></option>
<%    
          List<Fuente> fuentes =  (List<Fuente>)request.getAttribute("fuentes");
          for(Fuente fuente_i:fuentes){
        	  if(alert.getFuente()!=null && alert.getFuente().getId().equals(fuente_i.getId())){
        		  out.println("<option value=\""+fuente_i.getId()+"\" selected >"+fuente_i.getNombre()+"</option>"); 
        	  }else{
        	      out.println("<option value=\""+fuente_i.getId()+"\">"+fuente_i.getNombre()+"</option>");
        	  }
          }
         
%> 
</select>
</div>

<input type="hidden" id="oper" name="oper"/>

<input type="submit" class="btn btn-primary" value="grabar">
<input type="button" class="btn btn-primary" value="delete" onclick="deleteOper()">
<input type="button" class="btn btn-primary" value="limpiar" onclick="clearFields()">
</form>
</fieldset>
<fieldset>
<jsp:include page="showData.jsp"/>
<input type="button" class="btn btn-primary" value="Nuevo Dato" onclick="nuevoDato('eventoId')"/> 
</fieldset>
</div>
</body> 
</html>