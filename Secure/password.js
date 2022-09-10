
var seven=[['s','u','n'],['m','o','n'],['t','u','e'],['w','e','d'],['t','h','u'],['f','r','i'],['s','a','t']];

	function clear() 
	{
	  document.getElementById('text').value='';
	  document.getElementById('out').value='';
	}	

	function encode() 
	{
		document.getElementById('out').value='';
		var input = document.getElementById('text').value;
		var picknum = Math.ceil(Math.random()*9);
		var f = seven[Math.floor(Math.random()*6)];
		var m = seven[Math.floor(Math.random()*6)];
		var l = seven[Math.floor(Math.random()*6)];
		var inArr =[];
		if(input!="")
		{
			for (var i = 0; i < input.length; i++) {
				inArr.push(input[i]);
			}
			
			
			if (input.length < 7) 
			{
				var selectCombo = Math.ceil(Math.random()*3);
				if (selectCombo == 1) {
					inArr=flcombo(inArr,f,l);
				} 
				else if(selectCombo == 2){
					inArr=mlcombo(inArr,m,l);
				}
				else if (selectCombo == 3) {
					inArr=fmcombo(inArr,f,m);
				}
			}
			else
			{
				var smlcombo = Math.ceil(Math.random()*3);
				if (smlcombo == 1) {
					for (var i = 0; i < f.length; i++) {
						inArr.unshift(f[i]);
					}
					inArr.push('fc');
				} 
				else if(smlcombo == 2){
					var mid = Math.ceil(inArr.length/2);
					for (var i = 0; i < m.length; i++) {
						inArr.splice(mid,0,m[i]);
					}
					inArr.push('mc');
				}
				else if (smlcombo == 3) {
					for (var i = 0; i < l.length; i++) {
						inArr.push(l[i]);
					}
					inArr.push('lc');
				}
			}
			var str = inArr.join('');
	
			for (var i = 0; i < str.length; i++) {
				let n, res;
	
				n = str.charCodeAt(i);
				n=parseInt(n);
				n+=picknum;
				res=String.fromCharCode(n);
				document.getElementById('out').value += res;
			}
	
			var org=document.getElementById('out').value;
			var rev=[];
			for (var i = 0; i < org.length; i++) {
				rev.push(org[i]);
			}
			document.getElementById('out').value = rev.join('') + numtostr(picknum);
			store();
		}
		else
		{
			alert("Excuse me! Please type something.....");
		}		
	}

	function flcombo(arr,f,l) {
		
		for (var i = 0; i < f.length; i++) {
			arr.unshift(f[i]);
		}

		for (var i = 0; i < l.length; i++) {
			arr.push(l[i]);
		}

		arr.push('f','l');
		return arr;
	}

	function mlcombo(arr,m,l) {
		
		var mid = Math.ceil(arr.length / 2);

		for (var i = 0; i < l.length; i++) {
			arr.push(l[i]);
		}

		
		for (var i = 0; i < m.length; i++) {
			arr.splice(mid,0,m[i]);
		}

		arr.push('m','l');
		return arr;
	}

	function fmcombo(arr,f,m) {
		
		var mid = Math.ceil(arr.length/2);

		for (var i = 0; i < m.length; i++) {
			arr.splice(mid,0,m[i]);
		}

		for (var i = 0; i < f.length; i++) {
			arr.unshift(f[i]);
		}

		arr.push('f','m');
		return arr;
	}
function numtostr(num)
{	
	var alpha;
	switch(num)
	{
		case 1:
			alpha="a";
			break;
		case 2:
			alpha="b";
			break;
		case 3:
		 	alpha="c";
		 	break;
		case 4:
		  	alpha="d";
		  	break;
		case 5:
			alpha="e";
			break;
		case 6:
			alpha="f";
			break;
		case 7:
			alpha="g";
			break;
		case 8:
			alpha="h";
			break;
		case 9:
			alpha="i";
			break;
		case 10:
			alpha="j";
			break;
	}
	return alpha;
}
	function decode() 
	{	
		var orgstr='';
		var output=document.getElementById('text').value;
		var pickedNum= num(output.slice(-1));
		output = output.slice(0,-1);
		var org=[];
		if(output!="")
		{
			for (var i = 0; i < output.length; i++) {
				org.push(output[i])
			}
			org = org.join('');
	
			for (var i = 0; i < org.length; i++) {
				
				let n,res;
				n = org.charCodeAt(i);
				n-= pickedNum;
				res = String.fromCharCode(n);
				orgstr += res;
			}
	
			var combo = orgstr.slice(-2);
			orgstr = orgstr.slice(0,-2);
			org = orgstr.split("");
			let mid;
			
			switch(combo)
			{
				case "fl":
					for (var i = 0; i < 3; i++) {
						org.shift(i);
					}
					for (var i = 0; i < 3; i++) {
						org.pop(i);
					}
	
					break;
				case "ml":
					
					for (var i = 0; i < 3; i++) {
						org.pop(i);
					}
					mid = Math.ceil((org.length-3) /2);
					for (var i = 0; i < 3; i++) {
						org.splice(mid,1);
					}
	
					break;
				case "fm":
					
					for (var i = 0; i < 3; i++) {
						org.shift(i);
					}
					mid = Math.ceil((org.length-3) /2);
					for (var i = 0; i < 3; i++) {
						org.splice(mid,1);
					}
	
					break;
				case "fc":
					for (var i = 0; i < 3; i++) {
						org.shift(i);
					}
					break;
				case "mc":
					mid = Math.ceil((org.length-3) /2);
					for (var i = 0; i < 3; i++) {
						org.splice(mid,1);
					}
					break;
				case "lc":
					for (var i = 0; i < 3; i++) {
						org.pop(i);
					}
					break;
			}
		document.getElementById('out').value=org.join('');
	}
	else
	{
		alert("Excuse me! Please type something....");
	}
}

function num(alpha)
{
  var num;
  switch(alpha)
  {
    case "a":
    num=1;
    break;
    case "b":
    num=2;
    break;
    case "c":
    num=3;
    break;
    case "d":
    num=4;
    break;
    case "e":
    num=5;
    break;
    case "f":
    num=6;
    break;
    case "g":
    num=7;
    break;
    case "h":
    num=8;
    break;
    case "i":
    num=9;
    break;
    case "j":
    num=10;
    break;
  }
  return num;
}

function output() {
  var copyText = document.getElementById("out");
  copyText.select();
  document.execCommand("copy");
  alert("Copied the text: " + copyText.value);
}


document.onkeydown = function(e) {
  if(e.keyCode == 123) {
   return false;
  }
  if(e.ctrlKey && e.shiftKey && e.keyCode == 'I'.charCodeAt(0)){
   return false;
  }
  if(e.ctrlKey && e.shiftKey && e.keyCode == 'J'.charCodeAt(0)){
   return false;
  }
  if(e.ctrlKey && e.keyCode == 'U'.charCodeAt(0)){
   return false;
  }
  if(e.ctrlKey && e.shiftKey && e.keyCode == 'C'.charCodeAt(0)){
   return false;
  }   
  if(e.ctrlKey && e.shiftKey && e.keyCode == 'K'.charCodeAt(0)){
    return false;
  }  
  if(e.shiftKey && e.keyCode == 118){
    return false;
  }
  if(e.shiftKey && e.keyCode == 116){
    return false;
  }
  if(e.shiftKey && e.keyCode == 120){
    return false;
  }
  if(e.shiftKey && e.keyCode == 123){
    return false;
  }
  if(e.shiftKey && e.keyCode == 119){
    return false;
  }
  if(e.ctrlKey && e.shiftKey && e.keyCode == 'E'.charCodeAt(0)){
    return false;
  }    
}

function store() {
	var org=document.getElementById("text").value;
	var decrypt=document.getElementById("out").value;

	// Check browser support
	if (typeof(Storage) !== "undefined") {
	  // store
	  localStorage.setItem(org, decrypt);
	} else {
	  document.getElementById("result").innerHTML = "Sorry, your browser does not support Web Storage...";
	}	
}

function retrieve() {
	var org=prompt("Enter your original text");
	
	alert("Your encrypted text: "+localStorage.getItem(org));
}
