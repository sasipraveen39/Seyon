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
</script>

</head>
<body>
	<!-- Header -->
	<div class="navbar-container">
		<div class="container">
			<nav
				class="navbar navbar-toggleable-md navbar-inverse bg-inverse bg-faded navbar-content">
				<button class="navbar-toggler navbar-toggler-right" type="button"
					data-toggle="collapse" data-target="#navbarNavDropdown"
					aria-controls="navbarNavDropdown" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<a class="navbar-brand navbar-brand-header" href="/Seyon"> <img
					src="resources/pics/seyon.png" width="40" height="53"
					class="d-inline-block align-top" alt="Seyon logo"> Seyon
				</a>
				<div class="collapse navbar-collapse justify-content-end"
					id="navbarNavDropdown">
					<ul class="navbar-nav ">
						<li class="nav-item active"><a class="nav-link" href="#">Home
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#">Features</a>
						</li>
						<li class="nav-item"><a class="nav-link" href="#">Pricing</a></li>
						<li class="nav-item dropdown"><a
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
			</nav>
		</div>
	</div>
	<!-- Footer -->
	<div class="navbar-xs">
		<nav class="navbar fixed-bottom navbar-inverse bg-inverse bg-faded">
			<div class="navbar-header">
				<div class="navbar-brand">
					<ul class="footer-links">
						<li><footer>
								<small>Copyright © 2017, </small><img
									src="resources/pics/seyon.png" width="20" height="27"
									class="d-inline-block align-top" alt="Seyon logo"> Seyon.
								<small>All Rights Reserved.</small>
							</footer></li>
						<li class="footer-link"><a href="#">About us</a></li>
						<li><a href="#">Contact us</a></li>
					</ul>
				</div>
			</div>
		</nav>
	</div>
</body>
</html>