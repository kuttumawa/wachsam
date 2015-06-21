<!DOCTYPE html>
<html>
<head>
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
#info{
    margin-top: 10px;
    margin-bottom: 5px;
    margin-right: 20px;
    margin-left: 20px;
    color: red;
    font-style: italic;
    padding: 10px;
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
<div id="info">
<% out.println(request.getAttribute("resultado")); %>
</div>
<%}%>
<form id="form1" action="login" method="post">
	Usuario<input type="text" name="user"/>
	Password<input type="password" name="pass"/>
	<input type="submit"/>
</form>
</div>

</body>
</html>