<!DOCTYPE html>
<html>
<head>

  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script>sessionStorage.clear();</script>
<style>
input[type="text"],input[type="password"] {
  display: block;
  margin: 0;
  width: 50%;
  font-family: sans-serif;
  font-size: 18px;
  appearance: none;
  box-shadow: none;
  border-radius: none;
  padding: 1px;
  border: solid 1px #dcdcdc;
  transition: box-shadow 0.3s, border 0.3s;
  margin-bottom: 10px;
}
input[type="text"]:focus {
  outline: none;
  border: solid 1px #707070;
  box-shadow: 0 0 5px 1px #969696;
}

#box {
position: absolute;
top: 50%;
left: 50%;
width: 400px;
height: 200px;
margin-top: -100px;
margin-left: -200px;

}
</style>
</head>

<body>
<div id="box">
<%if(request.getAttribute("resultado")!=null){ %>
<div id="info" class="alert alert-danger">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>
<form id="form1" action="login" method="post" class="form-inline">
	<label for="user">Usuario</label> 
	<input type="text" name="user" id="user"/>
	<label for="user">Password</label> 
	<input type="password" name="pass" id="pass"/>
	<input class="btn btn-primary" type="submit"/>
</form>
</div>

</body>
</html>