<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
<html> 
<style>
table, th, td {
    border: 1px solid black;
}
.caducado{
color: red;
}
</style>
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
	document.getElementById("fechaPubDesde").value="";
	document.getElementById("fechaPubHasta").value="";
	document.getElementById("tipo").value="";
	document.getElementById("caducidad").value="";
}

</script>
 
<body>
<jsp:include page="cabecera.jsp"/>


<%if(request.getAttribute("resultado")!=null){ %>
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>






<form id="form2" action="BuscarAlert" method="post">

<fieldset>
<div>
<label for="">Texto</label><br>
<textarea name="texto" id="texto" cols="100" rows="1"></textarea>
</div>

<div>
<label for="">Peligro</label>
</div><div>

<select name="peligro" id="peligro">
<option value=""></option>
<%    
          List<Peligro> peligros =  (List<Peligro>)request.getAttribute("peligros");
          for(Peligro peligro:peligros){
        	      out.println("<option value=\""+peligro.getId()+"\">"+peligro.getNombre()+"</option>");
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
				      out.println("<option value=\""+lugar.getId()+"\">"+lugar.getNombre()+"</option>");
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
	      out.println("<option value=\""+tipo_i.id+"\">"+tipo_i.texto+"</option>");
  }
%>
</select> <a href="http://wwwnc.cdc.gov/travel/notices#warning" >ver</a>
</div>

<div>
<label for="">Caducidad</label>
<input type="checkbox" name="caducidad" value="">Todas</input>
<input type="checkbox" name="caducidad" value="no">No caducadas</input>
<input type="checkbox" name="caducidad" value="si">Caducadas</input>

</div>

<div>
<label for="">Fecha (dd/mm/yyyy) Desde</label>
<input type="text" id="fechaPub" name="fechaPubDesde" value=""/>
</div>






<input type="hidden" id="oper" name="oper"/>

<input type="submit" value="buscar">
<input type="button" value="limpiar" onclick="clearFields()">
</form>
</fieldset>
<%
List<Alert> alerts= (List<Alert>)request.getAttribute("alertas");
if(request.getAttribute("alertas")!=null){ %>
<fieldset>
<h3>Resultados:&nbsp; <%=alerts.size() %></h3>
<table>
<tr><th>id</th><th>nombre</th><th>fecha</th><th>texto</th></tr>

<%for(Alert a:alerts){%>
<tr class="<%=a.isCaducado()?"caducado":""%>">
<td><a href="ProvisionalAlertUpdaterForYou?alert=<%=a.getId()%>"><%=a.getId()%></a></td>
<td><%=a.getNombre()%></td>
<td><%=a.getFechaPubFormatted()%></td>
<td><%=a.getTexto()%></td>
</tr>
<%} %>
</table>
</fieldset>
<%}else{%>
<div>---- Sin resultado ----</div>
<%}%>
</body> 
</html>