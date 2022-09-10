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
	height: 1200px;
	background-color: #252525;
}

.top {
	background-color: black;
	height: 60px;
	width: 100%;
	position: sticky;
	top: 0;
	/*margin-left: -8px;
    margin-top: -8px;*/
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
	margin-right: 8%;
	width: 25%;
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
	padding: 3% 6% 0% 0%;
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
	width: 80%;
	height: 100%;
	margin: auto;
	background-repeat: no-repeat;
	background-position: center;
}

.nav {
	border: 1px solid grey;
	border-radius: 10px;
	width: 74%;
	height: 6%;
	margin: auto;
	margin-top: 3%;
	font-size: 18px;
	position: sticky;
}

.li {
	float: left;
	padding: 8px 12%;
	border-right: 1px solid grey;
	cursor: pointer;
	transition-duration: 0.5s;
}

.li:hover {
	transform: scale(1.2, 1.2);
}

#tour {
	display: flex;
	flex-wrap: wrap;
	flex-direction: row;
	width: 100%;
	height: 50%;
	overflow-y: scroll;
	margin-top: 2%;
}

#tour>div {
	width: 330px;
	height: 170px;
	border: 1px solid grey;
	margin: 2% 2%;
	border-radius: 10px;
	cursor: pointer;
	transition-duration: 0.5s;
}

#tour>div:hover {
	transform: translate(0px, -10px);
}

.bottom {
	width: 100%;
	height: 80px;
	border: 1px solid transparent;
	background-color: black;
	margin-top: 5%;
	color: #f2ed3e;
}

.left {
	float: left;
	/*border: 1px solid;*/
	width: 30%;
	height: 100%;
	background-size: 100% 100%;
}

.right {
	float: right;
	/*border: 1px solid;*/
	width: 70%;
	height: 100%;
}

.ico {
	color: #8e8e8e;
	padding: 10px;
	font-size: 0.82rem;
}

.green {
	width: 10px;
	height: 10px;
	background-color: green;
	border-radius: 50%;
	margin: -10% 90%;
}

.red {
	width: 10px;
	height: 10px;
	background-color: red;
	border-radius: 50%;
	margin: -10% 90%;
}

.closed {
	width: 10px;
	height: 10px;
	background-color: white;
	border-radius: 50%;
	margin: -10% 90%;
}

#title {
	font-weight: bold;
	padding-left: 10px;
	/*padding-bottom: 10px;*/
}

.header {
	position: absolute;
	font-size: 35px;
	color: #f2ed3e;
	margin: 14% 0% 0% 14%;
}

.img {
	width: 100%;
	height: 100%;
	border-radius: 10px;
}
</style>
</head>
<body onload="check()" >

	<div class="top">
		<div class="head">
			Tour<span class="span">PUBG</span>
		</div>
		<div class="loginbut">
			<div class="cretour" onclick="createtour()">Create Tournament</div>
			<i class='fas fa-user' id='logeduser'
				onclick="window.location.href = 'user.jsp'"> <a href="" id="out"
				onclick="checkout()">LogOut</a>
			</i> <input type="button" id="user" value="SignIn/SignUp"
				onclick="window.location.href = 'log.jsp'">
		</div>
	</div>

	<div class="header">
		<div style="font-size: 65px; font-weight: bold;">
			Tour<span
				style="background-color: #f2ed3e; color: black; border-radius: 5px; margin-left: 5px; padding: 5px;">PUBG</span>
			<br>
			<spanMystic
				style="font-size: 25px; font-weight: normal; padding: 5%;">Play
			more, Earn more.</span>
		</div>

	</div>
	<div class="body">
		<img src="Images/poster.jpg" width="100%">
		<div class="nav">
			<ul type="none">
				<li class="li" style="color: #08fd08;" onclick="filter('open')">Upcoming</li>
				<li class="li" style="color: red;" onclick="filter('live')">Live</li>
				<li class="li" style="border-right: 0px" onclick="filter('closed')">Closed</li>
			</ul>
		</div>

		<div id="tour"></div>
	</div>

	<div class="bottom">
		<h1 align="center">Play More, Earn More</h1>
	</div>
	<script type="text/javascript">

		var gData = "";		
		
		fetch('http://localhost:8082/tourpubg/')
		  .then((response) => {
		    for (var pair of response.headers.entries()) {
		      console.log(pair[0]+ ': '+ pair[1]);
		      if(pair[0] == "request-counter" && pair[1] != "No"){
		    	  alert("Limit Exceeded");
		      }
		    }
		  });
		
		function createtour() {
			
			var dict = {
				"fun" : "create"
			};
			var xhr = new XMLHttpRequest();
			xhr.timeout = 2000;
			xhr.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					if (this.responseText == "logged") {
						window.location.href = "tournament.jsp";
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
	
		var gFilter = "open";
		function filter(filtr) {
			gFilter = filtr;
			getTour();
		}
		function getTour() {
			
			var dict = {
				"filter" : gFilter
			};
			document.getElementById("tour").innerHTML = "";
			var xhr = new XMLHttpRequest();
			xhr.timeout = 2000;
			xhr.responseType = "json";
			xhr.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					console.log(this.response);
					gData = this.response;
					start();
				}
				if(this.status == 409){
					alert("Request Conflict occured");
				}
			};

			let jsonstring = JSON.stringify(dict);
			let uriencode = encodeURIComponent(jsonstring);

			let sendstr = "data=" + uriencode;
			xhr.open("POST", "check?" + sendstr, true);

			xhr.send(sendstr);
		}

		function check() {
			var dict = {
				"fun" : "check"
			};
			var xhr = new XMLHttpRequest();
						
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
		
		function gotour(id) {
			
			var dict ={
					"tid":id
			};
			var xhr = new XMLHttpRequest();
			xhr.timeout = 2000;
			xhr.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					if (this.responseText == "success") {
						window.location.href = "tourreg.jsp";
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
			xhr.open("PUT", "check?" + sendstr, true);

			xhr.send(sendstr);
		}
		
		function start() {
			var n = gData.length;
						
			for(var i = 0; i < n; i++){
				create(i);
			}
		}
		
		function create(i) {
			var flex = document.getElementById('tour');
			var div = document.createElement("div");
			div.setAttribute("id", gData[i].tid);
			div.setAttribute("onclick","gotour(this.id)");
			flex.appendChild(div);
			var l = document.createElement("div");
			l.setAttribute("class", "left");
			var r = document.createElement("div");
			r.setAttribute("class", "right");
			div.appendChild(l);
			div.appendChild(r);
			var img = document.createElement("img");
			img.setAttribute("src", "Images/pubgm.jpg");
			img.setAttribute("class", "img");
			l.appendChild(img);

			var title = document.createElement("p");
			var hr = document.createElement("hr");
			var p1 = document.createElement("p");
			var p2 = document.createElement("p");
			var date = document.createElement("i");
			var price = document.createElement("i");
			var peo = document.createElement("i");
			var info = document.createElement("i");
			var status = document.createElement("div");

			title.setAttribute("id", "title");
			title.innerText = gData[i].title;
			r.appendChild(title);
			r.appendChild(hr);
			r.appendChild(p1);
			r.appendChild(p2);

			date.setAttribute("class", "far fa-calendar ico");
			date.innerText = " "+gData[i].date;
			peo.setAttribute("class", "fas fa-users ico");
			peo.innerText = " "+gData[i].people+" / 100";
			p1.appendChild(date);
			p1.appendChild(peo);

			price.setAttribute("class", "fas fa-trophy ico");
			price.innerText = " "+gData[i].amount;
			info.setAttribute("class", "fas fa-info-circle ico");
			info.innerText = " "+gData[i].type;
			p2.appendChild(price);
			p2.appendChild(info);
			
			if(gData[i].status == "open"){
				status.setAttribute("class", "green");
			}
			else if(gData[i].status == "live") {
				status.setAttribute("class", "red");
			}
			else if(gData[i].status == "closed"){
				status.setAttribute("class","closed");
				div.removeAttribute("onclick");
			}
			r.appendChild(status);
		}
		
	</script>
</body>
</html>