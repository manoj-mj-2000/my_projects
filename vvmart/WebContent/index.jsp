<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>mMarket</title>
<link
	href="https://fonts.googleapis.com/css?family=Josefin+Sans&display=swap"
	rel="stylesheet">
<style type="text/css">
* {
	box-sizing: border-box;
	font-family: 'Josefin Sans', sans-serif;
}

body {
	background-image:
		url("https://loopnewslive.blob.core.windows.net/liveimage/sites/default/files/2017-12/dWzbRIGYwj.jpg");
	background-repeat: no-repeat;
}
.main{
    width: 500px;
    height: 600px;
    background-color: #90ee90bd;
    margin: auto;
    margin-top: 7%;
    border-radius: 20px;
    padding: 6%;
}
input[type="text"]{
    font-size: 20px;
    width: 200px;
    height: 40px;
	margin-left: 22px;
}
input[type="password"]{
	font-size: 20px;
    width: 200px;
    height: 40px;
    margin-top: 7%;
    margin-left: 35px;
}
input[type="button"]{
    width: 150px;
    height: 55px;
    border-radius: 20px;
    margin: 10% 0% 0% 28%;
    font-size: 20px;
    background-color: transparent;
    border-color: #ff0000b0;
}
label{
    font-size: 20px;
}
form{
margin-top: 12%; 
}
</style>
</head>
<body>

	<div class="main">
		<h1 align = "center">Login</h1>
		<form action = "login" method ="post" id="log">
			<label>UserName</label> <input type="text" name="name"
				placeholder="AdminName" id="name"><br>
			<br> <label>Password</label> <input type="password" name="pass"
				placeholder="Password" id="pass"><br>
			<br>
			<input type="button" value="Login" onclick="send()">
		</form>
		<s:if test="hasActionErrors()">
      		<s:actionerror/>
  		</s:if>
  		<p id="res"></p>
		
	</div>
<script type="text/javascript">


function send() {
	
	var name = document.getElementById("name").value;
	var pass = document.getElementById("pass").value;
	
	if(name == "" || pass == ""){
		document.getElementById("res").innerText = "Username & Password shouldn't be empty.";
	}
	else{
		document.getElementById("log").submit();
	}
	
}

</script>
</body>
</html>