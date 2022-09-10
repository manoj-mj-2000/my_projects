<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Tournament</title>
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
    width: 50%;
    height: 100%;
    background-repeat: no-repeat;
    margin: auto;
    background-size: 100%;
    margin-top: 5%;
    position: relative;
}
.logo{
    position: absolute;
    background-image: url(Images/main_logo.jpg);
    width: 150px;
    height: 150px;
    background-size: contain;
    border-radius: 50%;
    margin: -8% 0% 0% 40%;
}
input[type="text"], input[type="password"],input[type="number"],select{
    width: 80%;
    height: 50px;
    background: transparent;
    border: 2px solid #f0ed40;
    border-radius: 12px;
    color: white;
    font-size: 18px;
    margin: 5%;
    margin-left: 5%;
    padding-left: 10px;
}
select{
    width: 80%;
    height: 50px;
    background: transparent;
    border: 2px solid #f0ed40;
    border-radius: 12px;
    color: yellow;
    font-size: 18px;
    margin: 5%;
    margin-left: 5%;
    padding-left: 10px;
}
option{
	color: black; 
}
input[type="button"]{
    width: 28%;
    height: 45px;
    background: transparent;
    border: 2px solid #f0ed40;
    border-radius: 12px;
    margin-left: 35%;
    margin-top: 6%;
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
<body>
<div class="main">
<div class="logo"></div>
	<form method="post" id = "form1">
	<h1 id="logsign">Create Your Own Tournament</h1>
		<table align="center">
			<tr>
				<td><input type="text" id="title" placeholder="Tournament title"></td>
				<td>
					<select id="time">
						<option>Select</option>
						<option value="9am">9 AM</option>
						<option value="12pm">12 PM</option>
						<option value="3pm">3 PM</option>
						<option value="6pm">6 PM</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><input type="number" id="date" 
					placeholder="Date: 2-28"></td>
				<td>
					<select id="month">
							<option>Select</option>
							<option value="JAN">January</option>
							<option value="FEB">February</option>
							<option value="MAR">March</option>
							<option value="APR">April</option>
							<option value="MAY">May</option>
							<option value="JUN">June</option>
							<option value="JUL">July</option>
							<option value="AUG">August</option>
							<option value="SEP">September</option>
							<option value="OCT">October</option>
							<option value="NOV">November</option>
							<option value="DEC">December</option>

					</select>
				</td>
			</tr>
			<tr>
				<td><input type="number" id="amount" 
					placeholder="Minimum: 2000 Rs"></td>
				<td>
					<select id="type">
						<option>Select</option>
						<option value="SOLO">Solo</option>
						<option value="DUO">Duo</option>
						<option value="SQUAD">Squad</option>
					</select>
				</td>
			</tr>
						
		</table>
		<input type="button" value="Create" onclick="create()">
	</form>
	
</div>
<script type="text/javascript">
	
fetch('http://localhost:8082/tourpubg/tournament.jsp')
.then((response) => {
  for (var pair of response.headers.entries()) {
    console.log(pair[0]+ ': '+ pair[1]);
    if(pair[0] == "request-counter" && pair[1] != "No"){
  	  alert("Limit Exceeded");
    }
  }
});
	
function create() {
	
	var title = document.getElementById('title').value;
	var time = document.getElementById('time').value;
	var date = document.getElementById('date').value;
	date = parseInt(date);
	var month = document.getElementById('month').value;
	var amount = document.getElementById('amount').value;
	amount = parseInt(amount);
	var type = document.getElementById('type').value;

	if(title != "" && time != "" && time != "Select" &&
		amount != "" && type != "" && type != "Select" &&
		month != "" && month != "Select" &&
		date > 1 && date < 29 && amount > 1999){
		
		var dict = {
				"title":title,
				"time":time,
				"date":date.toString(),
				"month":month,
				"amount":amount.toString(),
				"type":type
			};
			var xhr = new XMLHttpRequest();
			xhr.timeout = 2000;
			xhr.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					if(this.responseText == "success"){
						window.location.href = "/tourpubg";
					}
					else{
						alert("Something went wrong");
					}
				}
				if(this.status == 409){
					alert("Request Conflict occured");
				}
			};

			let jsonstring = JSON.stringify(dict);
			let uriencode = encodeURIComponent(jsonstring);

			let sendstr = "data=" + uriencode;
			xhr.open("POST", "tournaments/tour?" + sendstr, true);

			xhr.send(sendstr);
	}
	else{
		alert("Put all valid Details");
	}
}

</script>
</body>
</html>