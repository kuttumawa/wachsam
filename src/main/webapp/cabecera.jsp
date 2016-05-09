<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="es.io.wachsam.model.*"  %>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<style>
input[type="text"] {
  display: block;
  margin: 0;
  width: 65%;
  font-family: sans-serif;
  font-size: 18px;
  appearance: none;
  box-shadow: none;
  border-radius: none;
   margin-bottom: 5px;
  padding: 2px;
  border: solid 1px #dcdcdc;
  transition: box-shadow 0.3s, border 0.3s;
}
input[type="text"]:focus {
  outline: none;
  border: solid 1px #707070;
  box-shadow: 0 0 5px 1px #969696;
}
select {
 margin-bottom: 5px;
}
#info{
    border: 1px solid red;
    margin-top: 10px;
    margin-bottom: 20px;
    margin-right: 20px;
    margin-left: 20px;
    color: red;
    font-style: italic;
    padding: 10px;
}
</style>
</head>

<%
Usuario usuario = (Usuario)request.getSession().getAttribute("user");
%>

<nav class="navbar navbar-inverse ">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span> 
      </button>
      <a class="navbar-brand" href="#">Viajar Seguro</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
    <ul class="nav navbar-nav">
     <!--  <li class="active"><a href="#">Home</a></li> -->
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Alertas
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="BuscarAlert">Buscar </a></li>
          <li><a href="ProvisionalAlertUpdaterForYou">Alertas</a></li>
           
        </ul>
      </li>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Lugares
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">Buscar_OFF</a></li>
          <li><a href="ProvisionalLugarUpdaterForYou">Lugares</a></li> 
        </ul>
      </li>
      
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Peligros
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">Buscar_OFF</a></li>
          <li><a href="ProvisionalPeligroUpdaterForYou">Peligros</a></li>
          <li><a href="ProvisionalFactorUpdaterForYou">Factores</a></li>
        </ul>
      </li>
      
      
       <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Datas
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="ProvisionalDataUpdaterForYou">Data</a></li>
          <li><a href="ProvisionalTagUpdaterForYou">Tags</a></li>        
        </ul>
      </li>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Sitios
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="ProvisionalSitioUpdaterForYou">Sitio </a></li>
          <li><a href="ProvisionalAirportUpdaterForYou">Airport</a></li>        
        </ul>
      </li>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">in/out
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="upload">File Upload</a></li>        
        </ul>
      </li>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Otros
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
           
          <li><a href="ProvisionalFuenteUpdaterForYou">Fuente</a></li>
        </ul>
      </li>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Seguridad
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="ProvisionalUsuarioUpdaterForYou">Usuarios</a></li>
          <li><a href="ProvisionalPermisoUpdaterForYou">Permisos</a></li>  
          <li><a href="#">Tokens_OFF</a></li>
        </ul>
      </li>
            
      <li><a href="version.jsp">Versión</a></li> 
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="#"><span class="glyphicon glyphicon-user"></span> <%=usuario.getLogin() %></a></li>
      <li><a href="login"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
    </ul>
  </div>
  </div>
  
</nav>

