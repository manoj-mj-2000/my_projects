<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TourPUBG</title>
<link href='https://fonts.googleapis.com/css?family=Lato'
	rel='stylesheet'>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<style type="text/css">
* {
	box-sizing: border-box;
}
::-webkit-scrollbar {
  width: 5px;
}

/* Track */
::-webkit-scrollbar-track {
  box-shadow: inset 0 0 5px grey; 
  border-radius: 10px;
}
 
/* Handle */
::-webkit-scrollbar-thumb {
  background: yellow; 
  border-radius: 5px;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #b30000; 
}
body {
	font-family: 'Lato';
	color: white;
	width: 99%;
	height: 1000px;
	background-color: #252525;	
	
}

.top {
	background-color: black;
	height: 60px;
	width: 100%;
	position: sticky;
	top: 0;
}

.head {
	background-image: url(Images/headico.png);
	background-size: contain;
	background-repeat: no-repeat;
	font-size: 30px;
	font-weight: bold;
	width: 30%;
	height: 100%;
	margin-left: 10%;
	padding-left: 75px;
	padding-top: 12px;
	color: #f2ed3e;
	float: left;
}

.span {
	background-color: #f2ed3e;
	color: black;
	border-radius: 5px;
	margin-left: 5px;
	padding: 2px 2px 0px 2px;
}

.loginbut {
    float: right;
    margin: 8px;
    margin-right: 0%;
    width: 33%;
}

#user {
	font-size: 18px;
	background-color: #f2ed3e;
	cursor: pointer;
	color: black;
	padding: 9px;
	border-radius: 5px;
	transition-duration: 0.5s;
}

#user:hover {
	border: 1.5px solid #f2ed3e;
	background-color: black;
	color: #f2ed3e;
}

#out {
	font-size: 20px;
	color: #f2ed3e;
	padding: 5px;
	text-decoration: underline;
	cursor: pointer;
}

.cretour {
	float: left;
	padding: 3% 5% 0% 0%;
	font-size: 20px;
	color: #f2ed3e;
	cursor: pointer;
	margin-right: 2%;
	transition-duration: 0.5s;
}

.cretour:hover {
	text-decoration: underline;
}

#logeduser {
	display: none;
	font-size: 32px;
	color: #f2ed3e;
	padding: 4px 40px;
	cursor: pointer;
}

.body {
    background-color: transparent;
    width: 100%;
    height: 100%;
    margin: auto;
}
.img {
    width: 99%;
    height: 420px;
    background-image: url(Images/coverphoto.jpg);
    background-size: cover;
    background-position: top center;
    position: fixed!important;
    opacity: 0.5;
    box-shadow: inset 0px -15px 20px 5px black;
}
.tourhead{
	width: 80%;
    height: 60%;
    margin: 8% 10% 0% 10%;
    position: absolute;
    background-color: #343434;
}
.logo{
	width: 12%;
    height: 40%;
    margin: -4% 0% 0% 44%;
    border: 1px solid;
    border-radius: 5px;
}
#tourtitle{
	text-align: center;
	color: #f2ed3e;
}
#status{
	text-transform: uppercase;
	text-align: center;
	margin-top: -15px;
}
.green{
	color: #00ff00;
}
.red{
	color: red;
}
.closed{
	color: white;
}
.flexdiv{
	display: flex;
	/*border: 1px solid;*/
	width: 100%;
	height: 25%;
	flex-wrap: wrap;
	flex-direction: row;
}
.flexdiv > div{
	width: 33.33%;
    /*border: 1px solid;*/
}
.ico{
	margin: 2% 44%;
	width: 40px;
	height: 40px;
}
#date, #price, #people{
	color: white;
	text-align: center;
}
input[type="button"]{
    width: 28%;
    height: 45px;
    background: transparent;
    border: 2px solid #f0ed40;
    border-radius: 12px;
    color: #f0ed40;
    font-size: 25px;
    transition-duration: 0.5s;
    margin: 0% 8%;
}
input[type="button"]:hover{
	background-color: #f0ed40;
	color: black;
}

.pricedata{
    width: 100%;
    height: 55%;
    margin: 10% 0px 0px 0px;
    background-color: #343434;
    margin-left: 0px; 
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
}
.pricedata > div{
	/*border: 1px solid;*/
	width: 33.25%;
	height: 80%;
}
.trophy{
	width: 60px;
    height: 60px;
    margin: -2% 47.5% 0% 47.5%;
    background-color: #343434;
    border-radius: 5px;
}
.pricehead{
	text-align: center;
	color: #f2ed3e;
	padding-top: 5%;
}
#type{
	font-size: 28px;
    background-color: grey;
    padding: 10px;
    color: white;
    border-radius: 12px;
}
#firstprice, #perkill, #perfees{
	text-align: center;
    font-size: 40px;
    font-weight: bold;
    color: red;
    margin-top: -1%;
}
</style>
</head>
<body onload="check()">

	<div class="top">
		<div class="head" onclick="window.location.href = '/tourpubg'">
			Tour<span class="span">PUBG</span>
		</div>
		<div class="loginbut">
			<i class='fas fa-user' id='logeduser' onclick = "window.location.href = 'user.jsp'">
			<a href="" id="out"	onclick="checkout()">LogOut</a>
			</i>
			 <input type="button" id="user" value="SignIn/SignUp"
				onclick="window.location.href = 'log.jsp'">
		</div>
	</div>

	<div class="body">
		<div class="img"></div>
		<div class="tourhead">
			<img src="Images/pubgm.jpg" class="logo">

			<h1 id="tourtitle"></h1>
			<h2 id="status"></h2>

			<div class="flexdiv">
				<div>
					<img src="Images/calendar.svg" class="ico">
					<p id="date"></p>
				</div>
				<div>
					<img src="Images/tournaments-grey.svg" class="ico">
					<p id="price"></p>
				</div>
				<div>
					<img src="Images/team-grey.svg" class="ico">
					<p id="people"></p>
				</div>
			</div>
			<center>
				<input type="button" value="Register" id="regisbut" onclick="createtour()">
				<span id="type"></span>
				<input type="button" value="Participants" onclick="window.location.href='partis.jsp'">
			</center>

			<div class="pricedata">
				<img src="Images/trophy.svg" class="trophy">
				<div>
					<h1 class="pricehead">Chicken Dinner Price</h1>
					<p id="firstprice">
						
					</p>
				</div>
				<div>
					<h1 class="pricehead">Amount Per Kill</h1>
					<p id="perkill">
						
					</p>
				</div>
				<div>
					<h1 class="pricehead">Entry Fee</h1>
					<p id="perfees">
						
					</p>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
	fetch('http://localhost:8082/tourpubg/tourreg.jsp')
	  .then((response) => {
	    for (var pair of response.headers.entries()) {
	      console.log(pair[0]+ ': '+ pair[1]);
	      if(pair[0] == "request-counter" && pair[1] != "No"){
	    	  alert("Limit Exceeded");
	      }
	    }
	  });
		
	function check() {
		var dict = {
			"fun" : "check"
		};
		var xhr = new XMLHttpRequest();
		
		var myHeader = xhr.getResponseHeader("Request-Counter");
		console.log(myHeader);
		xhr.timeout = 2000;
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				if (this.responseText == "logged") {
					document.getElementById("user").style.display = "none";
					document.getElementById("logeduser").style.display = "block";
					getTour();
				} else {
					document.getElementById("user").style.display = "block";
					document.getElementById("logeduser").style.display = "none";
					getTour();
				}
			}
			if(this.status == 409){
				alert("Request Conflict occured");
			}
		};

		let jsonstring = JSON.stringify(dict);
		let uriencode = encodeURIComponent(jsonstring);

		let sendstr = "data=" + uriencode;
		xhr.open("GET", "check?" + sendstr, true);

		xhr.send(sendstr);
		
	}

	function checkout() {
		var dict = {
			"fun" : "checkout"
		};
		var xhr = new XMLHttpRequest();
		xhr.timeout = 2000;
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				if (this.responseText == "checkout") {
					windows.reload();
				} else {
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
		xhr.open("DELETE", "check?" + sendstr, true);

		xhr.send(sendstr);
	}
	
	var gData = "";
	function getTour() {
		var dict = {
				"fun" : "tournament"
			};
			var xhr = new XMLHttpRequest();
			xhr.timeout = 2000;
			xhr.responseType = "json";
			xhr.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					gData = this.response;
					console.log(gData);
					setDetails();
				}
				if(this.status == 409){
					alert("Request Conflict occured");
				}
			};

			let jsonstring = JSON.stringify(dict);
			let uriencode = encodeURIComponent(jsonstring);

			let sendstr = "data=" + uriencode;
			xhr.open("GET", "tournaments/tour?" + sendstr, true);

			xhr.send(sendstr);
	}
	
	function setDetails() {
		
		document.getElementById("tourtitle").innerText = gData[0].title;
		document.getElementById("status").innerText = gData[0].status;
		document.getElementById("date").innerText = gData[0].datet;
		document.getElementById("price").innerText = gData[0].amount + " Rs";
		document.getElementById("people").innerText = gData[0].people + " / 100";
		document.getElementById("firstprice").innerText = gData[0].dinner + " Rs";
		document.getElementById("perkill").innerText = gData[0].perkill + " Rs";
		document.getElementById("perfees").innerText = gData[0].perfees + " Rs";
		document.getElementById("type").innerText = gData[0].type;
		
		if(gData[0].status == "open"){
			document.getElementById("status").setAttribute("class","green");
		}
		if(gData[0].status == "live"){
			document.getElementById("status").setAttribute("class","red");
		}
		if(gData[0].status == "closed"){
			document.getElementById("status").setAttribute("class","closed");
		}
	}
	
	function createtour() {
		var dict = {
			"fun" : "check"
		};
		var xhr = new XMLHttpRequest();
		xhr.timeout = 2000;
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				if (this.responseText == "logged") {
					regis();
				}
				if (this.responseText == "nolog") {
					window.location.href = "log.jsp";
				}
			}
			if(this.status == 409){
				alert("Request Conflict occured");
			}
		};

		let jsonstring = JSON.stringify(dict);
		let uriencode = encodeURIComponent(jsonstring);

		let sendstr = "data=" + uriencode;
		xhr.open("GET", "check?" + sendstr, true);

		xhr.send(sendstr);
		
	}
	
	function regis(){
		var dict = {
				"fun" : "register"
			};
			var xhr = new XMLHttpRequest();
			xhr.timeout = 2000;
			xhr.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					if(this.responseText == "success"){
						alert("Registration Successfully")
						window.location.reload();
					}
					else if(this.responseText == "Already Joined"){
						alert(this.responseText);
					}
					else if(this.responseText == "Tournament was Filled"){
						alert(this.responseText);
					}
					else if(this.responseText == "login"){
						window.location.href = "log.jsp";
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
			xhr.open("PUT", "tournaments/tour?" + sendstr, true);

			xhr.send(sendstr);
	}

	</script>
</body>
</html>