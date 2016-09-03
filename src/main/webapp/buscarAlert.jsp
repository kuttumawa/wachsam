<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="es.io.wachsam.model.*"  %>
    <%@ page import="java.util.*"  %>
<html> 
<jsp:include page="cabecera.jsp"/>
<style>

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
	document.getElementById("f").value="";
	document.getElementById("peligro").value="";
	document.getElementById("fechaPubDesde").value="";
	document.getElementById("fechaPubHasta").value="";
	document.getElementById("tipo").value="";
	document.getElementById("caducidad").value="";
}

function submitForm(page){
	document.getElementById("page").value=page;
	document.forms["form2"].submit();
}
</script>
 
<body>

<div class="container">

<%if(request.getAttribute("resultado")!=null){ %>
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>






<form id="form2" action="BuscarAlert" method="post"  role="form">
<input id="page" name="page" type="hidden"/>

<div  class="form-group">
<label for="">Texto</label><br>
<textarea class="form-control input-sm" name="texto" id="texto" cols="100" rows="1"><%=request.getAttribute("texto")!=null?request.getAttribute("texto"):""%></textarea>
</div>

<div  class="form-group">
<label for="">Peligro</label>
<select  class="form-control input-sm" name="peligro" id="peligro">
<option value=""></option>
<%    
          List<Peligro> peligros =  (List<Peligro>)request.getAttribute("peligros");
          for(Peligro peligro:peligros){
        	      out.println("<option value=\""+peligro.getId()+"\">"+peligro.getNombre()+"</option>");
          }
         
%> 
</select>
</div>

<div  class="form-group">
<label for="">Lugar</label>

<select  class="form-control input-sm" name="lugar" id="lugar">
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
	tipos.add(new Tipo("justInfo","Just Information you may ignore"));
	tipos.add(new Tipo("informativa","Level 1 Watch, Practice Usual Precautions"));
	tipos.add(new Tipo("normal","Level 2 Alert, Practice Enhanced Precautions"));
	tipos.add(new Tipo("severa","Level 3 Warning, Avoid Nonessential Travel"));
	

%>


<div  class="form-group">
<label for="">Tipo</label>

<select  class="form-control input-sm" name="tipo" id="tipo">
<option value="">Todas</option>
<%
for(Tipo tipo_i:tipos){
	      out.println("<option value=\""+tipo_i.id+"\">"+tipo_i.texto+"</option>");
  }
%>
</select> <a href="http://wwwnc.cdc.gov/travel/notices#warning" >ver</a>
</div>

<div class="checkbox">

<label><input type="checkbox" name="caducidad" value="">Todas</label>
<label><input type="checkbox" name="caducidad" value="nocaducadas">No caducadas</label>
<label><input type="checkbox" name="caducidad" value="caducadas">Caducadas</label>

</div>

<div  class="form-group">
<label for="">Fecha (dd/mm/yyyy) Desde</label>
<input class="form-control input-sm" type="date" id="fechaPub" name="fechaPubDesde" value=""/>
</div>






<input type="hidden" id="oper" name="oper"/>
<div class="btn-group center-block">
<input class="btn btn-primary btn-sm" type="submit" value="buscar">
<input class="btn btn-primary btn-sm" type="button" value="limpiar" onclick="clearFields()">
</div>
</form>

<div  style="margin-top: 3em" >
<%
List<Alert> alerts= (List<Alert>)request.getAttribute("alertas");
if(request.getAttribute("alertas")!=null){ %>


<table class="table table-condensed  table-striped small">
<tr><th>id</th><th>nombre</th><th>tipo</th><th>fecha</th><th>texto</th></tr>

<%for(Alert a:alerts){%>
<tr class="<%=a.isCaducado()?"caducado":""%>">
<td><a href="ProvisionalAlertUpdaterForYou?alert=<%=a.getId()%>"><%=a.getId()%></a></td>
<td><%=a.getNombre()%></td>
<td><%=a.getTipo()%></td>
<td><%=a.getFechaPubFormatted()%></td>
<td><%=a.getTexto()%></td>
</tr>
<%} %>
</table>
<%
int numpages = (Integer)request.getAttribute("numpages");
int currentpage = (Integer)request.getAttribute("page");
int totalResults = (Integer)request.getAttribute("totalResults");
%>
<h4>Resultados:&nbsp;<%=alerts.size()%>&nbsp;de&nbsp;<%=totalResults %></h4>
<ul class="pagination pagination-sm">

<%
  for(int i=0;i<numpages;i++){
%>
  <li><a href="javascript:submitForm(<%=i %>)" class="<%=i==currentpage?"active":""%>"><%= i %></a></li>
<%} %>  
 
</ul>

<%}else{%>
<%}%>
</div>
</div>
</body> 
</html>