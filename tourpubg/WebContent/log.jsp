<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SignIn / SignUp</title>
<link href='https://fonts.googleapis.com/css?family=Lato' rel='stylesheet'>
<style type="text/css">
	
body{
	/*border: 1px solid;*/
	background-color: #252525;
    font-family: 'Lato';
	width: 99%;
	height: 650px;
	color: #f0ed40;
}
.main{
    background-image: url(Images/login_bg.png);
    width: 35%;
    height: 90%;
    background-repeat: no-repeat;
    margin: auto;
    background-size: cover;
    margin-top: 8%;
    position: relative;
}
.logo{
    position: absolute;
    background-image: url(Images/main_logo.jpg);
    width: 150px;
    height: 150px;
    background-size: contain;
    border-radius: 50%;
    margin: -18% 0% 0% 35%;
}
input[type="text"], input[type="password"],input[type="number"]{
    width: 75%;
    height: 50px;
    background: transparent;
    border: 2px solid #f0ed40;
    border-radius: 12px;
    color: white;
    font-size: 25px;
    margin: 5%;
    margin-left: 22%;
    padding-left: 10px;
}
input[type="button"]{
    width: 50%;
    height: 45px;
    background: transparent;
    border: 2px solid #f0ed40;
    border-radius: 12px;
    margin-left: 35%;
    margin-top: 0%;
    color: #f0ed40;
    font-size: 25px;
    transition-duration: 0.5s;
}
input[type="button"]:hover{
	background-color: #f0ed40;
	color: black;
}
#form1{
    padding: 12%;
    padding-top: 0%;
    /*display: none;*/
}
#form2{
    padding: 12%;
    padding-top: 0%;
    display: none;
}
#logsign{
    text-align: center;
    padding-top: 20%;
}
#name, #id, #pass, #mobile{
	width: 85%;
    height: 45px;
    margin-top: 2%;
    margin-left: 15%;
}
</style>
</head>
<body >
<div class="main">
<div class="logo"></div>
	<form method="post" id = "form1">
	<h1 id="logsign">LogIn</h1>
		<table>
			<tr>
				<td><input type="text" id="username" placeholder="Username"></td>
			</tr>
			<tr>
				<td><input type="password" id="password" placeholder="Password"></td>
			</tr>
			<tr>
				<td><input type="button" value="LogIn" onclick="log()"></td>
			</tr>
			<tr>
				<td style="text-align: center;text-decoration: underline; padding-top: 30px; cursor: pointer;padding-left: 70px;
				" onclick="change(2)">Create New Account</td>
			</tr>
		</table>
	</form>
	<form method="post" id="form2">
		<h1 id="logsign">New User</h1>
		<table>
			<tr>
				<td><input type="text" id="name" placeholder="Name"></td>
			</tr>
			<tr>
				<td><input type="number" id="id" placeholder="Character Id"></td>
			</tr>
			<tr>
				<td><input type="password" id="pass" placeholder="Password"></td>
			</tr>
			<tr>
				<td><input type="number" id="mobile" placeholder="Mobile"></td>
			</tr>
			<tr>
				<td><input type="button" value="Create" onclick="signin()"></td>
			</tr><tr>
				<td style="text-align: center;text-decoration: underline; padding-top: 30px; cursor: pointer;padding-left: 70px;
				" onclick="change(1)">Already have account</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	
fetch('http://localhost:8082/tourpubg/log.jsp')
.then((response) => {
  for (var pair of response.headers.entries()) {
    console.log(pair[0]+ ': '+ pair[1]);
    if(pair[0] == "request-counter" && pair[1] != "No"){
  	  alert("Limit Exceeded");
    }
  }
});

	function change(n) {
		var f1 = document.getElementById('form1');
		var f2 = document.getElementById('form2');

		if(n==1){
			f1.style.display = "block";
			f2.style.display = "none";
		}
		if(n==2){
			f1.style.display = "none";
			f2.style.display = "block";			
		}
		
	}
	
	function log() {
		
		var user = document.getElementById("username").value;
		var pass = document.getElementById("password").value;
		
		if(user != "" && pass != null) {
			var dict = {
				"user":user,
				"pass":pass
			};
			
			var xhr = new XMLHttpRequest();
			xhr.timeout = 2000;
			xhr.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					if(this.responseText == "success"){
						window.location.href = "/tourpubg";
					}
					else{
						alert("Incorrect username or password");
					}
					
				}
				if(this.status == 409){
					alert("Request Conflict occured");
				}
			};
	
			let jsonstring = JSON.stringify(dict);
			let uriencode = encodeURIComponent(jsonstring);
	
			let sendstr = "data=" + uriencode;
			xhr.open("GET", "users/user?" + sendstr, true);
	
			xhr.send(sendstr);
		}
		else{
			alert("Give valid Username and Password");
		}
	}
	
	function signin() {
		
		var name =document.getElementById("name").value;
		var id =document.getElementById("id").value;
		var pass=document.getElementById("pass").value;
		var mob=document.getElementById("mobile").value;
		
		if(name != "" && id != "" && pass != "" && mob != "") {
			var dict = {
				"name":name,
				"cid":id,
				"pass":pass,
				"mobl":mob
			};
			var xhr = new XMLHttpRequest();
			xhr.timeout = 2000;
			xhr.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					if(this.responseText == "success"){
						window.location.href = "/tourpubg";
					}
					else if(this.responseText == "failed"){
						alert("Character Id already exists");
					}
					else{
						alert("Try with other Username and Password");
					}
					
				}
				if(this.status == 409){
					alert("Request Conflict occured");
				}
			};
	
			let jsonstring = JSON.stringify(dict);
			let uriencode = encodeURIComponent(jsonstring);
	
			let sendstr = "data=" + uriencode;
			xhr.open("POST", "users/user?" + sendstr, true);
	
			xhr.send(sendstr);
		}
		else{
			alert("Provide all valid details to Register");
		}
	}
</script>
</body>
</html>