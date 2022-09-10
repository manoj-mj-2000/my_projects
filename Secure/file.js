function clear() {

  document.getElementById('file').value="";
  document.getElementById('show').value='';
  document.getElementById('out').value='';
  document.getElementById('a').style.display='none';

}
	document.getElementById('file').addEventListener("change",function(){
		var fr = new FileReader();

		fr.onload = 	function(){
			document.getElementById('show').value = this.result;
		}
		fr.readAsText(this.files[0]);
		document.getElementById('but').value=this.files[0].name;
	})

	function encode() {
		document.getElementById('out').value ="";
		var input=document.getElementById('show').value;
		var pickNum= Math.ceil(Math.random()*9);
		if(input!="")
		{
			for (var i = 0; i < input.length; i++) {
				let n,res;
				n=input.charCodeAt(i);
				n+=pickNum;
				res=String.fromCharCode(n);
				document.getElementById('out').value += res;
			}
	
			var org=document.getElementById('out').value;
			var rev=[];
			for (var i = 0; i < org.length; i++) {
				rev.unshift(org[i]);
			}
	
			document.getElementById('out').value = rev.join('') + numtostr(pickNum);
			download(document.getElementById('out').value, 'Encrypt.txt', 'text/plain');
			document.getElementById('a').style.display='block';
		}
		else
		{
			alert("Excuse me! Please Choose a file......");
		}
	}

	function download(text, name, type) {

		var a = document.getElementById("a");
		var file = new Blob([text], {type: type});
		a.href = URL.createObjectURL(file);
		a.download = name;

	}

	function decode(){

		document.getElementById('out').value ="";
		var rev = document.getElementById('show').value;
		var orgstr = "";
		if(rev!="")
		{
			rev=rev.split("");
			rev=rev.reverse();
			var pickedNum = num(rev[0]);
			rev = rev.slice(1);
			org=rev.join('');
			
			for (var i = 0; i < org.length; i++) {
				let n,res;
	
				n=org.charCodeAt(i);
				n-=pickedNum;
				res=String.fromCharCode(n);
				orgstr += res;
			}
			download(orgstr, 'Org.txt', 'text/plain');
			document.getElementById('a').style.display='block';
		}
		else
		{
			alert("Excuse me! Please Choose a file......");
		}
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
