<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Purchases</title>
<link
	href="https://fonts.googleapis.com/css?family=Josefin+Sans&display=swap"
	rel="stylesheet">
<style type="text/css">
body {
	width: 100%;
	margin: -2% 0%;
}

* {
	box-sizing: border-box;
	font-family: 'Josefin Sans', sans-serif;
}

.top {
	width: 100%;
	height: 100px;
	background-color: #98ce4a;
	color: white;
}

.adName {
	padding: 35px;
	font-weight: bold;
	font-size: 25px;
	text-align: center;
}

.left {
	width: 25%;
	height: 690px;
	background-color: black;
	border: 1px solid;
	float: left;
}

.right {
	width: 75%;
	height: 690px;
	background-color: #e8e9ed;
	border: 1px solid;
	float: right;
	overflow: scroll;
}

li {
	height: 85px;
	color: white;
	border-bottom: 1px solid white;
	padding: 35px;
	cursor: pointer;
	box-shadow: 0px 5px 15px #888888;
}

#cusForm1 {
	background-color: lightgreen;
	margin: 5% auto;
	width: 45%;
	height: 60%;
	border-radius: 20px;
	display: none;
}

#cusForm2 {
	background-color: lightgreen;
	margin: 5% auto;
	width: 55%;
	height: 30%;
	border-radius: 20px;
	display: none;
}

tr {
	height: 35px;
}

td {
	padding: 35px 5px 5px 50px;
}

input[type="text"] {
	height: 30px;
	width: 200px;
	font-size: 20px;
}

input[type="button"] {
	height: 50px;
	width: 150px;
	text-align: center;
	border-radius: 25px;
	font-size: 20px;
	background-color: #5cd65c;
	margin: 0% 35%;
}

span {
	color: black;
}

#customers {
	width: 80%;
	height: 90%;
	background-color: #88888830;
	margin: auto;
	overflow: scroll;
}

#cus {
	width: 100%;
	text-align: center;
	border-collapse: collapse;
}

th {
	border: 1px solid black;
	text-align: center;
	background-color: #888888ad;
	color: white;
}

.tr {
	height: 50px;
	border: 1px solid;
}

.td {
	padding: 0px;
	border: 1px solid;
}
</style>
</head>
<body onload="getData()">

	<div class="top">
		<p class="adName">Purchase Page</p>
	</div>

	<div class="left">

		<li onclick="displayDiv('add')">Add</li>
		<li onclick="displayDiv('update')">Update</li>
		<li onclick="displayDiv2('delete')">Delete</li>

	</div>
	<div class="right">
		<h1 id="res" align="center"></h1>
		<form id="cusForm1">
			<table>
				<tr>
					<td>Purchase Id</td>
					<td><input type="text" id="id"></td>
				</tr>
				<tr>
					<td>Product Id</td>
					<td><input type="text" id="pid"></td>
				</tr>
				<tr>
					<td>Quantity</td>
					<td><input type="text" id="quan"></td>
				</tr>
				<tr>
					<td>Vendor Id</td>
					<td><input type="text" id="vid"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="button" value="submit" onclick="sendData()"></td>
				</tr>
			</table>
		</form>

		<form id="cusForm2">
			<table>
				<tr>
					<td>Purchase Id</td>
					<td><input type="text" id="pid2"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="button" value="submit" onclick="sendData2()"></td>
				</tr>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
	var gaction = "";
	var id, pid, quan, vid;
	var jsonArr ="";
	
	function sendData() {
		id = document.getElementById("id").value;
		pid = document.getElementById("pid").value;
		quan = document.getElementById("quan").value;
		vid = document.getElementById("vid").value;
		var method = "";
		if(gaction == "add"){
			method = "POST";
		}
		if(gaction == "update"){
			method = "PUT";
		}
		if(id != "" && pid != "" && quan != "" && vid != ""){
		var dict = {
			"id" : id,
			"pid" : pid,
			"quan" : quan,
			"vid" : vid
		};
		var xhr = new XMLHttpRequest();
		
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				document.getElementById("res").innerText = this.responseText;
				document.getElementById("cusForm1").reset();
				window.stop();
			}
		};
		
		let jsonstring = JSON.stringify(dict);
		let uriencode = encodeURIComponent(jsonstring);

		let sendstr = "data=" + uriencode;
		xhr.open(method, "http://localhost:8081/vvMart/purchases/purchase?"
				+ sendstr, true);
		xhr.timeout = 3000;
		xhr.send(sendstr);
		}
		else{
			alert("Give valid fields to all fields");
		}

	}

	function displayDiv(action) {
		gaction = action
		var x = document.getElementById("cusForm1");
		var x2 = document.getElementById("cusForm2");
		if (x.style.display == "none") {
			x.reset();
			x.style.display = "block";
			x2.reset();
			x2.style.display = "none";
		} else {
			x2.reset();
			x.style.display = "none";
		}
	}
	function displayDiv2(action) {
		gaction = action;
		var x = document.getElementById("cusForm2");
		var x2 = document.getElementById("cusForm1");
		if (x.style.display == "none") {
			x.reset();
			x.style.display = "block";
			x2.reset();
			x2.style.display = "none";
		} else {
			x2.reset();
			x.style.display = "none";
		}
	}
	function sendData2() {
		var pid = document.getElementById("pid2").value;
		
		if(pid != ""){
		var dict = {"id": pid};
		var xhr = new XMLHttpRequest();
		
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				document.getElementById("res").innerText = this.responseText;
				document.getElementById("cusForm2").reset();
				window.stop();
			}
		};
		
		let jsonstring = JSON.stringify(dict);
		let uriencode = encodeURIComponent(jsonstring);

		let sendstr = "data=" + uriencode;
		xhr.open("DELETE", "http://localhost:8081/vvMart/purchases/purchase?"
				+ sendstr, true);
		xhr.timeout = 3000;
		xhr.send(sendstr);
		}
		else{
			alert("Give a valid Purchase Id");
		}
	}
	
	function getData(){
		var xhr = new XMLHttpRequest();
		xhr.responseType = "json"
		
		xhr.open("GET", "http://localhost:8081/vvMart/purchases/purchase", true);
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				gData = this.response;
				setData();
				window.stop();
			}
		};
		xhr.send();
	}
	
	function setData() {
		console.log(gData);
		createtab();
		var tab = document.getElementById("cus");
		for(i in gData){
			var tr = document.createElement("tr");
			tr.setAttribute("class","tr");
			
			var td1 = document.createElement("td");
			td1.setAttribute("class","td");
			var td2 = document.createElement("td");
			td2.setAttribute("class","td");
			var td3 = document.createElement("td");
			td3.setAttribute("class","td");
			var td4 = document.createElement("td");
			td4.setAttribute("class","td");
			
			td1.innerText = gData[i].id;
			tr.appendChild(td1);
			td2.innerText = gData[i].pid;
			tr.appendChild(td2);
			td3.innerText = gData[i].quan;
			tr.appendChild(td3);
			td4.innerText = gData[i].vid;
			tr.appendChild(td4);
			
			tab.appendChild(tr);
		}
	}
	
	function createtab() {
		
		var rdiv = document.getElementsByClassName("right")[0];
		//div creation 
		var d = document.createElement("div");
		d.setAttribute("id" , "customers");
		rdiv.appendChild(d);
		
		//table creation
		var tab = document.createElement("table");
		tab.setAttribute("id", "cus");
		d.appendChild(tab);
		
		var tr = document.createElement("tr");
		tr.setAttribute("class", "tr");
		tab.appendChild(tr);
		
		var th1 = document.createElement("th");
		var th2 = document.createElement("th");
		var th3 = document.createElement("th");
		var th4 = document.createElement("th");
		
		th1.innerText = "Purchase Id";
		tr.appendChild(th1);
		th2.innerText = "Product Id";
		tr.appendChild(th2);
		th3.innerText = "Quantity";
		tr.appendChild(th3);
		th4.innerText = "Vendor Id";
		tr.appendChild(th4);
		
	}
	
	</script>
</body>
</html>