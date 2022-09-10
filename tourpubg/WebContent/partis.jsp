<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TourPUBG</title>
<link href='https://fonts.googleapis.com/css?family=Lato' rel='stylesheet'>
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
*{
	box-sizing: border-box;
}
body{
    font-family: 'Lato';
    color: white;
    width: 99%;
    height: 770px;
    background-color: #252525;
}
.top
{
    background-color: black;
    height: 60px;
    width: 100%;
    position: sticky;
    top: 0;
    /*margin-left: -8px;
    margin-top: -8px;*/
}
.head{
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
.span{
	background-color: #f2ed3e;
    color: black;
    border-radius: 5px;
    margin-left: 5px;
    padding: 2px 2px 0px 2px;
}
.loginbut{
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
.loginbut:hover{
	border: 1.5px solid #f2ed3e;
	background-color: black;
	color: #f2ed3e;
}
.body{
	width: 80%;
    height: 75%;
    margin: auto;
    overflow-y: scroll;
    margin-top: 5%;
}
table{
    border: 1px solid;
    border-collapse: collapse;
    text-align: center;
    width: 100%;
    height: 70%;
    margin-top: 0px;
}
th{
    color: #f2ed3e;
    border: 1px solid;
    height: 60px;
    font-size: 20px;
}
tr{
	height: 50px;
}
td{
    border: 1px solid;
    font-size: 18px;
    text-transform: capitalize;

}
</style>
</head>
<body onload = "getPartis()">

<div class="top" onclick = "window.location.href='/tourpubg';">
	<div class="head">Tour<span class="span">PUBG</span></div>
</div>

<div  class="body">
	<table align="center" id = "tab">
		<tr>
			<th>Name</th>
			<th>Character Id</th>
			<th>No. Of Kills</th>
		</tr>

	</table>
</div>
<script type="text/javascript">

	var gData = "";
	
	fetch('http://localhost:8082/tourpubg/partis.jsp')
	  .then((response) => {
	    for (var pair of response.headers.entries()) {
	      console.log(pair[0]+ ': '+ pair[1]);
	      if(pair[0] == "request-counter" && pair[1] != "No"){
	    	  alert("Limit Exceeded");
	      }
	    }
	  });
	
	function getPartis() {
		var dict = {
				"fun": "partis"
		};
		var xhr = new XMLHttpRequest();
		
		var myHeader = xhr.getResponseHeader("Request-Counter");
		console.log(myHeader);
		xhr.timeout = 2000;
		xhr.responseType = "json";
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				console.log(this.response);
				gData = this.response;
				create();
			}
			if(this.status == 409){
				alert("Request Conflict occured");
			}
		};

		let jsonstring = JSON.stringify(dict);
		let uriencode = encodeURIComponent(jsonstring);

		let sendstr = "data=" + uriencode;
		xhr.open("DELETE", "tournaments/tour?" + sendstr, true);

		xhr.send(sendstr);
	}

	function create() {
		
		var tab = document.getElementById("tab");

		for (i in gData) {
			var tr = document.createElement("tr");

			var n = Object.keys(gData[i]).length;
			var tdarr = [];
			for (var j = 0; j < n; j++) {
				var td = document.createElement("td");
				tr.appendChild(td);
				tdarr.push(td);
			}

			tdarr[0].innerText = gData[i].name;
			tdarr[1].innerText = gData[i].charid;
			tdarr[2].innerText = gData[i].kill;

			tab.appendChild(tr);
		}
	}

</script>
</body>
</html>