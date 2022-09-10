<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>mMarket</title>
	<link href="https://fonts.googleapis.com/css?family=Josefin+Sans&display=swap" rel="stylesheet">
<style type="text/css">
	*{
		box-sizing: border-box;
		font-family: 'Josefin Sans', sans-serif;
	}
	.top{
		width: 100%;
		height: 100px;
		background-color: #98ce4a;
		color: white;
	}
	.adName{
		padding: 35px;
		font-weight: bold;
		font-size: 25px;
		text-align: right;
		margin: 0;
	}
	body{
		background-color: #e8e9ed;
	}
	.main{
		width: 95%;
		height: 70%;
		border-radius: 20px;
		background-color: white;
		margin-left: 2%;
		display: flex;
		flex-wrap: wrap;
		box-shadow: 8px 8px 6px #888888;
	}
	.main > div{
    margin: 5% 5%;
    height: 250px;
    width: 300px;
    border-radius: 15px;
    flex-direction: row;
    background-color: #e8e9ed; 
    box-shadow: 8px 8px 6px #888888;
	}
	img{
	width: 100px;
    height: 100px;
    margin: -15% 10%;
    border-radius: 15px;
    background-color: white
	}
	a{
		text-decoration: none;
		color: black;
	}
	a:hover{
		text-decoration: underline;
		color: blue;
		transition-duration: 0.5s;
	}
	h1{
		padding: 75px;
	}
	h1:hover{
		transition-duration: 0.5s;
		transform: scale(1, 2); 
	}
</style>
</head>
<body>

	<div class = "top">
		<p class="adName">
		Hi, Admin
		</p>
	</div>

	<p style="color: #514845;padding-left: 30px;font-size: 25px">Home</p>

	<div class="main">
		<div>
			<img src="https://pluspng.com/img-png/png-customer-benefit-for-your-customers-550.png">
			
			<a href="customers/Home"><h1 align="center">Customers</h1></a>
		</div>
		<div>
			<img src="https://cdn4.iconfinder.com/data/icons/hr-recruitment-management-part-2/400/hr-07-2-512.png">

			<a href="staffs/Home"><h1 align="center">Staffs</h1></a>
		</div>
		<div>
			<img src="https://www.aer.gov.au/sites/www.aer.gov.au/files/Customer%20calling%20their%20retailer.png
			">

			<a href="vendors/Home"><h1 align="center">Vendors</h1></a>
		</div>
		<div>
			<img src="https://pngimage.net/wp-content/uploads/2018/06/product-png-8.png">

			<a href="products/Home"><h1 align="center">Products</h1></a>
		</div>
		<div>
			<img src="https://cdn.pixabay.com/photo/2015/10/31/12/41/sale-1015710_960_720.jpg">

			<a href="sales/Home"><h1 align="center">Sales</h1></a>
		</div>
		<div>
			<img src="https://www.eleczo.com/media/wysiwyg/Easy-Purchase.png">

			<a href="purchases/Home"><h1 align="center">Purchases</h1></a>
		</div>
		<div>
			<img src="https://cdn11.bigcommerce.com/s-xad432y4/product_images/uploaded_images/invoice-clipart.png">

			<a href="invoices/Home"><h1 align="center">Invoices</h1></a>
		</div>
	</div>
</body>
</html>