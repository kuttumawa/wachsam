<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="es.io.wachsam.services.*"  %>
<%@ page import="es.io.wachsam.model.*"  %>
<%@ page import="org.springframework.web.context.WebApplicationContext"  %> 
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"  %> 
<%@ page import="es.io.wachsam.util.*"  %>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="js/localCache.js"></script>
<script>clearLocalStorage()</script>
<style>
.btn-group{padding-top: 10px;}

.navbar-brand { 
        padding: 1px 15px;
    }

.profile-img
{
    width: 46px;
    height: 46px;    
    -moz-border-radius: 50%;
    -webkit-border-radius: 50%;
    border-radius: 50%;
}

.fuente {
font-style: italic;
}
.pointer{
cursor:pointer;
}

/* Rules for icon sizes: */
.material-icons.md-18 { font-size: 18px; }
.material-icons.md-24 { font-size: 24px; } /* Default */
.material-icons.md-36 { font-size: 36px; }
.material-icons.md-48 { font-size: 48px; }


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

<%
WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
SecurityService sec=(SecurityService)context.getBean("securityService");
Usuario usuario = (Usuario)request.getSession().getAttribute("user");
if(usuario==null){
	   String nextJSP = "/login.jsp";
	   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
	   dispatcher.forward(request,response);
	   return;
}
%>

<nav class="navbar navbar-inverse ">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span> 
      </button>
      <a class="navbar-brand" href="main.jsp">
       
       <img class="profile-img" src="https://s-media-cache-ak0.pinimg.com/236x/70/66/b1/7066b1c4998eb0e9343ceaba7b6845ab.jpg"
                    alt="Heimdallr"> 
                        
       </a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
    <ul class="nav navbar-nav">
     <!--  <li class="active"><a href="#">Home</a></li> -->
      <%if(sec.hasAuth(usuario,Alert.class,Acciones.READ,null)){ %>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Eventos
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="ProvisionalAlertUpdaterForYou">Eventos</a></li>           
        </ul>
      </li>
      <%}%>
       <%if(sec.hasAuth(usuario,Lugar.class,Acciones.READ,null)){ %>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Lugares
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="ProvisionalLugarUpdaterForYou">Lugares</a></li> 
        </ul>
      </li>
      <%}%>
       <%if(sec.hasAuth(usuario,Peligro.class,Acciones.READ,null)){ %>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Peligros
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="ProvisionalPeligroUpdaterForYou">Peligros</a></li>
         
         </ul>
      </li>
       <%}%>
       <%if(sec.hasAuth(usuario,Riesgo.class,Acciones.READ,null)){ %>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Riesgos
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="riesgoLugar.jsp">Riesgos por Lugar</a></li> 
          <li><a href="riesgoPeligro.jsp">Riesgos por Peligro</a></li> 
          <li><a href="ProvisionalFactorUpdaterForYou">Factores</a></li>
          <li><a href="ProvisionalMitigacionUpdaterForYou">Mitigaciones</a></li>  
        </ul>
      </li>
      <%}%>
       <%if(sec.hasAuth(usuario,Data.class,Acciones.READ,null)){ %>
       <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Datas
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="ProvisionalDataUpdaterForYou">Data</a></li>
          <li><a href="ProvisionalTagUpdaterForYou">Tags</a></li>        
        </ul>
      </li>
      <%}%>
      <%if(sec.hasAuth(usuario,Sitio.class,Acciones.READ,null)){ %>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Sitios
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="ProvisionalSitioUpdaterForYou">Sitio </a></li>
          <li><a href="ProvisionalAirportUpdaterForYou">Airport</a></li>        
        </ul>
      </li>
      <%}%>
      <%if(sec.hasAuth(usuario,PermisosAvanzados.class,Acciones.READ,null)){ %>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">in/out
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="upload">File Upload</a></li>        
        </ul>     
      </li>
      <%}%>
      <%if(sec.hasAuth(usuario,PermisosAvanzados.class,Acciones.READ,null)){ %>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Otros
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="ProvisionalFuenteUpdaterForYou">Fuente</a></li>
          <li><a href="ProvisionalTipoSitioUpdaterForYou">Tipo Sitio</a></li>
          <li><a href="recurso">Recurso</a></li>
          <li><a href="main.jsp">Dashboard 1</a></li>
          <%if(Tools.getSystemProperty("HEIMDALLR_KIBANA_URL")!=null){ %>
              <li><a href="<%=Tools.getSystemProperty("HEIMDALLR_KIBANA_URL")%>">Kibana</a></li>
          <%}%>
        </ul>
      </li>
      <%}%>
      <%if(sec.hasAuth(usuario,PermisosGod.class,Acciones.READ,null)){ %>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Seguridad
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="ProvisionalUsuarioUpdaterForYou">Usuarios</a></li>
          <li><a href="ProvisionalPermisoUpdaterForYou">Permisos</a></li>  
          <li><a href="#">Tokens_OFF</a></li>
          <li><a href="logs.jsp">Log</a></li>
          <li><a href="whowhen.jsp">WhoWhen</a></li>   
        </ul>
      </li>
       <%}%>     
      <li><a href="version.jsp">Versi�n</a></li> 
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="#"><span class="glyphicon glyphicon-user"></span> <%=usuario.getLogin() %></a></li>
      <li><a href="login"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
    </ul>
  </div>
  </div>
  
</nav>

