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

* {
	box-sizing: border-box;
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
	font-size: 18px;
	background-color: #f2ed3e;
	color: black;
	padding: 9px;
	border-radius: 5px;
	transition-duration: 0.5s;
}

.loginbut:hover {
	border: 1.5px solid #f2ed3e;
	background-color: black;
	color: #f2ed3e;
}

.body {
	width: 80%;
	height: 93%;
	margin: auto;
	padding-top: 5%;
}

.user {
	border-radius: 10px;
	width: 80%;
	height: 40%;
	margin: auto;
	background-color: black;
}

.imguser {
	width: 15%;
	border-radius: 50%;
}

#form {
	margin: -8% 0% 0% 0%;
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
}

.bottom {
	width: 100%;
	height: 300px;
	border: 1px solid transparent;
	background-color: black;
	margin-top: 5%;
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
	margin: 10% 0% 0% 14%;
}

input[type="text"] {
	width: 30%;
	height: 60px;
	background: transparent;
	border: 2px solid #f0ed40;
	border-radius: 12px;
	color: white;
	font-size: 25px;
	margin: 6% 2% 0% 8%;
	padding-left: 10px;
	pointer-events: none;
	text-transform: capitalize;
}

.img {
	width: 100%;
	height: 100%;
	border-radius: 10px;
}
</style>
</head>
<body onload="createtour()">

	<div class="top">
		<div class="head" onclick="window.location.href = '/tourpubg'">
			Tour<span class="span">PUBG</span>
		</div>
	</div>

	<div class="body">
		<div class="user">
			<img src="Images/headico.png" class="imguser">
			<form id="form">
				<center>

					<input type="text" id="name"> <input type="text"
						id="charid"><br> <input type="text" id="mobile">
					<input type="text" id="winamt">
				</center>
			</form>
		</div>

		<div id="tour"></div>
	</div>
	<script type="text/javascript">
	
	fetch('http://localhost:8082/tourpubg/user.jsp')
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
			
			var myHeader = xhr.getResponseHeader("Request-Counter");
			console.log(myHeader);
			xhr.timeout = 2000;
			xhr.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					if (this.responseText == "logged") {
						getuser();
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

		function getuser() {
			var dict = {
				"fun" : "user"
			};
			var xhr = new XMLHttpRequest();
			xhr.timeout = 2000;
			xhr.responseType = "json";
			xhr.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					var data = this.response;
					document.getElementById("name").value = data[0].name;
					document.getElementById("charid").value = data[0].charid;
					document.getElementById("mobile").value = data[0].mobile;
					document.getElementById("winamt").value = "Rs. "
							+ data[0].winamt + " Won";
					getusertour();
				}
				if(this.status == 409){
					alert("Request Conflict occured");
				}
			};

			let jsonstring = JSON.stringify(dict);
			let uriencode = encodeURIComponent(jsonstring);

			let sendstr = "data=" + uriencode;
			xhr.open("PUT", "users/user?" + sendstr, true);

			xhr.send(sendstr);
		}

		var gData = "";
		function getusertour() {
			var dict = {
				"fun" : "usertour"
			};
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
			xhr.open("DELETE", "users/user?" + sendstr, true);

			xhr.send(sendstr);
		}

		function start() {
			var n = gData.length;

			for (var i = 0; i < n; i++) {
				create(i);
			}
		}

		function create(i) {
			var flex = document.getElementById('tour');
			var div = document.createElement("div");
			div.setAttribute("id", gData[i].tid);
			div.setAttribute("onclick", "gotour(this.id)");
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
			date.innerText = " " + gData[i].date;
			peo.setAttribute("class", "fas fa-users ico");
			peo.innerText = " " + gData[i].people + " / 100";
			p1.appendChild(date);
			p1.appendChild(peo);

			price.setAttribute("class", "fas fa-trophy ico");
			price.innerText = " " + gData[i].amount;
			info.setAttribute("class", "fas fa-info-circle ico");
			info.innerText = " " + gData[i].type;
			p2.appendChild(price);
			p2.appendChild(info);

			if (gData[i].status == "open") {
				status.setAttribute("class", "green");
			} else if (gData[i].status == "live") {
				status.setAttribute("class", "red");
			} else if (gData[i].status == "closed") {
				status.setAttribute("class", "closed");
				div.removeAttribute("onclick");
			}
			r.appendChild(status);
		}

		function gotour(id) {
			var dict = {
				"tid" : id
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
	</script>
</body>
</html>