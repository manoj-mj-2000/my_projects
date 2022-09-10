<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Invoices</title>
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
	height: 50%;
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
	width: 90%;
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
		<p class="adName">Invoices</p>
	</div>

	<div class="left">

		<li onclick="displayDiv('update')">Update</li>
		<li onclick="displayDiv2('delete')">Delete</li>

	</div>
	<div class="right">
		<h1 id="res" align="center"></h1>
		<form id="cusForm1">
			<table>
				<tr>
					<td>Bill Number</td>
					<td><input type="text" id="bill"></td>
				</tr>
				<tr>
					<td>Tax in Percentage</td>
					<td><input type="text" id="tax"></td>
				</tr>
				<tr>
					<td>Discount in Percentage</td>
					<td><input type="text" id="disc"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="button" value="submit" onclick="sendData()"></td>
				</tr>
			</table>
		</form>

		<form id="cusForm2">
			<table>
				<tr>
					<td>Bill No</td>
					<td><input type="text" id="billno2"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="button" value="submit" onclick="sendData2()"></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
	var gaction = "";
	var bill, tp, dp;
	var jsonArr ="";
	
	function sendData() {
		bill = document.getElementById("bill").value;
		tp = document.getElementById("tax").value;
		dp = document.getElementById("disc").value;

		if(bill != "" && tp != "" && dp != ""){
		var dict = {
			"bill" : bill,
			"tax" : tp,
			"disc" : dp
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
		xhr.open("PUT", "http://localhost:8081/vvMart/invoices/invoice?"
				+ sendstr, true);
		xhr.timeout = 3000;
		xhr.send(sendstr);
		}		
		else{
			alert("Give valid details to all fields");
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
		var bill = document.getElementById("billno2").value;
		
		if(bill != ""){
		var dict = {"bill": bill};
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
		xhr.open("DELETE", "http://localhost:8081/vvMart/invoices/invoice?"
				+ sendstr, true);
		xhr.timeout = 3000;
		xhr.send(sendstr);
		}
		else{
			alert("Give a valid Bill Number");
		}
	}
	
	function getData(){
		var xhr = new XMLHttpRequest();
		xhr.responseType = "json"
		
		xhr.open("GET", "http://localhost:8081/vvMart/invoices/invoice", true);
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
			
			var n = Object.keys(gData[i]).length;
			var tdarr = [];
			for(var j=0; j< n; j++ ){
				var td = document.createElement("td");
				td.setAttribute("class", "td");
				tr.appendChild(td);
				tdarr.push(td);
			}
			
			tdarr[0].innerText = gData[i].billno;
			tdarr[1].innerText = gData[i].cid;
			tdarr[2].innerText = gData[i].staid;
			tdarr[3].innerText = gData[i].amount;
			tdarr[4].innerText = gData[i].pid;
			tdarr[5].innerText = gData[i].salid;
			tdarr[6].innerText = gData[i].quan;
			tdarr[7].innerText = gData[i].netamt;
			tdarr[8].innerText = gData[i].tax;
			tdarr[9].innerText = gData[i].disc;
			tdarr[10].innerText = gData[i].tot;
			/* td1.innerText = gData[i].id;
			tr.appendChild(td1);
			td2.innerText = gData[i].pid;
			tr.appendChild(td2);
			td3.innerText = gData[i].quan;
			tr.appendChild(td3);
			td4.innerText = gData[i].vid;
			tr.appendChild(td4); */
			
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
		var th5 = document.createElement("th");
		var th6 = document.createElement("th");
		var th7 = document.createElement("th");
		var th8 = document.createElement("th");
		var th9 = document.createElement("th");
		var th10 = document.createElement("th");
		var th11 = document.createElement("th");
		
		th1.innerText = "Bill Number";
		tr.appendChild(th1);
		th2.innerText = "Customer Id";
		tr.appendChild(th2);
		th3.innerText = "Staff Id";
		tr.appendChild(th3);
		th4.innerText = "Amount";
		tr.appendChild(th4);
		th5.innerText = "Product Id";
		tr.appendChild(th5);
		th6.innerText = "Sales Id";
		tr.appendChild(th6);
		th7.innerText = "Quantity";
		tr.appendChild(th7);
		th8.innerText = "Net Amount";
		tr.appendChild(th8);
		th9.innerText = "Tax Amount";
		tr.appendChild(th9);
		th10.innerText = "Discount";
		tr.appendChild(th10);
		th11.innerText = "Total Amount";
		tr.appendChild(th11);
		
	}
	
	</script>
</body>
</html>