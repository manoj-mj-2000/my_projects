//Function that clears all text box on loading the page.
function clear() {
  document.getElementById('text').value='';
  document.getElementById('demo').value='';
}
//Function to choose the type of algorithm is going to encrypt.
function encode()
{
var select=document.getElementById('ciphers');
	if(document.getElementById('text').value!="")
  {
    if(select.value=="simple")
    {
      encode_simple();
    }
    if(select.value=="complex")
    {
      encode_complex();
    }
  }
  else
  {
    alert("Excuse me! Please type something.....");
  }
}
//Function to choose the type of algorithm is going to decrypt.
function decode()
{
var select=document.getElementById('ciphers');
let input= document.getElementById('text').value;
	if(document.getElementById('text').value!="")
  {
    if(input.slice(-2,-1)=="s")
  	{
  		decode_simple();
  	}
  	if(input.slice(-2,-1)=="c")
  	{
  		decode_complex();
  	}
  }
  else
  {
    alert("Excuse me! Please type something.....");
  }
}
//Function use complex algorithm.
function encode_complex() {
  document.getElementById("demo").value="";
  var str =document.getElementById("text").value+"nakamura";
  var n;
  var res;
  var pick = Math.floor((Math.random() * 10) + 1);
  for(i=0;i<str.length;i++)
  {
  n = str.charCodeAt(i);
  n=parseInt(n);
  n+=pick;
  res = String.fromCharCode(n);
  document.getElementById("demo").value +=res;
  
  }
  var rev = document.getElementById('demo').value;
  var out=[];
  for(let i=rev.length-1; i>=0;i--){
      out.push(rev[i]);
  }
  document.getElementById("demo").value =out.join('') +"c"+ numtostr(pick);

}
//Function use simple algorithm.
function encode_simple() {
  document.getElementById("demo").value="";
  var str =document.getElementById("text").value;
  var n;
  var res;
  var pick = Math.floor((Math.random() * 10) + 1);
  for(i=0;i<str.length;i++)
  {
  n = str.charCodeAt(i);
  n=parseInt(n);
  n+=pick;
  res = String.fromCharCode(n);
  document.getElementById("demo").value +=res;
  
  }
  var rev = document.getElementById('demo').value;
  var out=[];
  for(let i=rev.length-1; i>=0;i--){
      out.push(rev[i]);
  }
  
  document.getElementById("demo").value = out.join('') +"s"+ numtostr(pick);

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
//Function use simple decryption algorithm.
function decode_simple() {
  document.getElementById("demo").value="";
  var str =document.getElementById("text").value;
  var last=str.slice(-1);
  var n,res;
  num(last);
  for (var i = 0; i < str.length; i++) {
	n = str.charCodeAt(i);
	n=parseInt(n);
	n-=num(last);
	res = String.fromCharCode(n);
  document.getElementById("demo").value +=res;
  }
  var str=document.getElementById("demo").value;
  str=str.slice(0,-1);
  var rev = str;
  var out=[];
  for(let i=rev.length-2; i>=0;i--){
      out.push(rev[i]);
  }
  document.getElementById("demo").value=out.join('');
}

//Function use complex algorithm to decrypt.
function decode_complex() {
  document.getElementById("demo").value="";
  var str =document.getElementById("text").value;
  var last=str.slice(-1);
  var n,res;
  num(last);
  var rev = str.slice(0,-1);
  var out=[];
  for(let i=rev.length-2; i>=0;i--){
      out.push(rev[i]);
  }
  rev=out.join('');
  for (var i = 0; i < rev.length; i++) {
	n = rev.charCodeAt(i);
	n=parseInt(n);
	n-=num(last);
	res = String.fromCharCode(n);
  document.getElementById("demo").value +=res;
  }
  str = document.getElementById("demo").value;
  document.getElementById("demo").value=str.slice(0,-8);
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
  var copyText = document.getElementById("demo");
  copyText.select();
  document.execCommand("copy");
  alert("Copied the text: " + copyText.value);
}
//Event listener to avoid Showing inspect Element.
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
  if(e.ctrlKey && e.keyCode == 'U'.charCodeAt(0)) {
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

