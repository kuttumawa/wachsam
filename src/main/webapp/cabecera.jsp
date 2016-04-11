<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<head>
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
<h1>Viajar Seguro</h1>
<nav style="margin-bottom: 10px;">
  <a href="BuscarAlert">BuscarAlertas</a> |
  <a href="ProvisionalAlertUpdaterForYou">Alertas</a> |
  <a href="ProvisionalLugarUpdaterForYou">Lugares</a> |
  <a href="ProvisionalPeligroUpdaterForYou">Peligros</a> |
  <a href="ProvisionalFactorUpdaterForYou">Factores</a> |
  <a href="ProvisionalDataUpdaterForYou">Data</a> |
  <a href="ProvisionalTagUpdaterForYou">Tags</a> |
  <a href="ProvisionalSitioUpdaterForYou">Sitio</a> |
  <a href="ProvisionalAirportUpdaterForYou">Airport</a>|
  <a href="ProvisionalFuenteUpdaterForYou">Fuente</a>|
  <a href="upload">File Upload</a>
</nav>

