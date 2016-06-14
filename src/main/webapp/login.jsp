<!DOCTYPE html>
<html>
<head>

  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <script src="js/localCache.js"></script>
  <script>sessionStorage.clear();</script>
<style>
.form-signin
{
    max-width: 330px;
    padding: 15px;
    margin: 0 auto;
}
.form-signin .form-signin-heading, .form-signin .checkbox
{
    margin-bottom: 10px;
}
.form-signin .checkbox
{
    font-weight: normal;
}
.form-signin .form-control
{
    position: relative;
    font-size: 16px;
    height: auto;
    padding: 10px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}
.form-signin .form-control:focus
{
    z-index: 2;
}
.form-signin input[type="text"]
{
    margin-bottom: -1px;
    border-bottom-left-radius: 0;
    border-bottom-right-radius: 0;
}
.form-signin input[type="password"]
{
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
}
.account-wall
{
    margin-top: 20px;
    padding: 40px 0px 20px 0px;
    background-color: #f7f7f7;
    -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}
.login-title
{
    color: #555;
    font-size: 18px;
    font-weight: 400;
    display: block;
}
.profile-img
{
    width: 96px;
    height: 96px;
    margin: 0 auto 10px;
    display: block;
    -moz-border-radius: 50%;
    -webkit-border-radius: 50%;
    border-radius: 50%;
}
.need-help
{
    margin-top: 10px;
}
.new-account
{
    display: block;
    margin-top: 10px;
}

</style>
</head>
<body>
<div class="container">

    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <%if(request.getAttribute("resultado")!=null){ %>
			<div id="info" class="alert alert-danger">
			<% out.println(request.getAttribute("resultado")); %>
			</div>
			<%}%>
            <div class="account-wall">
                <img class="profile-img" src="https://s-media-cache-ak0.pinimg.com/236x/70/66/b1/7066b1c4998eb0e9343ceaba7b6845ab.jpg"
                    alt="">
                 
                    
                <form id="form1"  class="form-signin" action="login" method="post">
                <input type="text" id="user" class="form-control" name="user" placeholder="user" required autofocus>
                <input type="password"  id="pass" name="pass" class="form-control" placeholder="password" required>
                <button class="btn btn-lg btn-primary btn-block" type="submit">
                    Sign in</button>
               
                <span class="clearfix"></span>
                </form>
            </div>
         
        </div>
    </div>
</div>
</body>