<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="shortcut icon" href="resources/pics/seyon.ico" />

<script src="resources/js/jquery-3.1.1.min.js"></script>
<script src="resources/js/jquery.validate.min.js"></script>
<script src="resources/js/tether.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/main.js"></script>

<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/bootstrap-grid.min.css">
<link rel="stylesheet" href="resources/css/bootstrap-reboot.min.css">
<link rel="stylesheet" href="resources/css/main.css">
<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<script>
	$.ajaxSetup({
		cache : false
	});

	$.ajax({
		url : "isLoggedIn",
		success : function(data) {
			if (!data) {
				window.location.href = "/Seyon";
			}
		},
		async : false
	});
	
	$(document).ready(function(){
		$('.nav-item-border').click(function(e) {
		    $('.navbar li.active').removeClass('active');
		    var $this = $(this);
		    if (!$this.hasClass('dropdown') && !$this.hasClass('active')) {
		        $this.addClass('active');
		    }
		});
	});
</script>

</head>
<body>
	<!-- Header -->

	<nav
		class="navbar navbar-toggleable-md navbar-inverse bg-inverse bg-faded navbar-content">
		<div class="container">
			<button class="navbar-toggler navbar-toggler-right" type="button"
				data-toggle="collapse" data-target="#navbarNavDropdown"
				aria-controls="navbarNavDropdown" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<a class="navbar-brand navbar-brand-header" href="/Seyon"> <img
				src="resources/pics/seyon.png" width="40" height="53"
				class="d-inline-block align-top" alt="Seyon logo"><span
				class="navbar-brand-header-text"> Seyon</span>
			</a>
			<div class="collapse navbar-collapse justify-content-end"
				id="navbarNavDropdown">
				<ul class="navbar-nav">
					<li class="nav-item nav-item-border"><a
						class="nav-link" href="#">Home </a></li>
					<li class="nav-item nav-item-border"><a class="nav-link"
						href="#">Features</a></li>
					<li class="nav-item nav-item-border"><a class="nav-link"
						href="#">Pricing</a></li>
					<li class="nav-item nav-item-border dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDropdownMenuLink" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> Settings </a>
						<div class="dropdown-menu"
							aria-labelledby="navbarDropdownMenuLink">
							<a class="dropdown-item" href="#">Change Password</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="logout">Logout</a>
						</div></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Footer -->

	<nav
		class="navbar fixed-bottom navbar-toggleable-md navbar-inverse bg-inverse bg-faded navbar-content">
		<div class="container">
			<button class="navbar-toggler navbar-toggler-right" type="button"
				data-toggle="collapse" data-target="#navbarNavDropdownFooter"
				aria-controls="navbarNavDropdownFooter" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<a class="navbar-brand navbar-brand-footer" href="/Seyon"> <span class="small-font">Copyright © 2017, </span><img
				src="resources/pics/seyon.png" width="22" height="29"
				class="d-inline-block align-top" alt="Seyon logo"><span
				class="navbar-brand-footer-text"> Seyon.</span><span class="small-font"> All Rights Reserved.</span>
			</a>
			<div class="collapse navbar-collapse justify-content-end"
				id="navbarNavDropdownFooter">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link  footer-link" href="#">About us</a></li>
					<li class="nav-item"><a class="nav-link  footer-link" href="#">Contact us</a></li>
				</ul>
			</div>
		</div>
	</nav>
</body>
</html>