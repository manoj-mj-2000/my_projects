var alphabet=['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];

	function output() {
	  var copyText = document.getElementById("out");
	  copyText.select();
	  document.execCommand("copy");
	  alert("Copied the text: " + copyText.value);
	}
	function clear()
	{
		  document.getElementById('text').value='';
		  document.getElementById('out').value='';
	}

	function encode() {
		document.getElementById('out').value="";
		var pinarr=[];
		var colapseStr;
		var pickNum=Math.ceil(Math.random()*9);
		var pin=document.getElementById('text');
		var pinval=pin.value;
		var pinLen=pinval.length;
		var out=[];
		if(pin.value!="")
		{
			for (var i = 0; i < pinval.length; i++) {
				pinarr.push(pinval[i]);
			}
			for (var i = 1; i < pinarr.length; i+=2) {
				let alphaPick=Math.floor(Math.random()*25);
				pinarr.splice(i,0,alphabet[alphaPick]);
			}
			
			for (var i = 0; i <= pinarr.length; i+=2) {
				pinarr[i]=numtostr(pinarr[i]);
			}
			colapseStr = pinarr.join('');

			for (var i = 0; i < colapseStr.length; i++) {
				let n,res;
				n=colapseStr.charCodeAt(i);
				n=parseInt(n);
				n+=pickNum;
				res=String.fromCharCode(n);
				document.getElementById('out').value += res;
			}

			var rev=document.getElementById('out').value;
			for (var i = rev.length; i>=0 ; i--) {
				out.push(rev[i]);
			}
			document.getElementById('out').value= out.join('') + numtostr(pickNum);
			store();
		}
		else
		{
			alert("Excuse me! Please type something.....");
		}
	}


function numtostr(num)
{	
	var alpha;
	switch(parseInt(num))
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
		case 0:
			alpha="j";
			break;
	}
	return alpha;
}

function decode() {
	document.getElementById('out').value="";
	var input=document.getElementById('text').value;
	var pickedNum=num(input.slice(-1));
	var stri = [];
	var revArr=[];
	var encArr=[];
	var encrypt="";
	if(input!="")
	{	
		for (var i = 0; i < input.length-1; i++) {
			stri.push(input[i]);
		}
		for (var i = stri.length -1; i>=0; i--) {
			revArr.push(stri[i]);
		}
		for (var i = 0; i < revArr.length; i+=2) {
			encArr.push(revArr[i]);
		}
		encrypt=encArr.join('')

		for (var i = 0; i < encrypt.length; i++) {
			let n,res;
			n = encrypt.charCodeAt(i);
			n-=pickedNum;
			res=String.fromCharCode(n);
			document.getElementById('out').value +=num(res);
		}
	}
	else
	{
		alert("Excuse me! Please type something.....");
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
    num=0;
    break;
  }
  return num;
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
	  // Store
	  localStorage.setItem(org, decrypt);
	} else {
	  document.getElementById("result").innerHTML = "Sorry, your browser does not support Web Storage...";
	}	
}

function retrieve() {
	var org=prompt("Enter your original text");
	
	alert("Your encrypted text: "+localStorage.getItem(org));
}