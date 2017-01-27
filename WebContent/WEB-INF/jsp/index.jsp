<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="header.jsp" />
<title>Homepage - Seyon</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row full-height">
			<div class="col homepage-logo">
				<div class="vertical-center">
					<img class="img-fluid" src="resources/pics/seyon-full.png"
						alt="Seyon logo">
				</div>
			</div>
			<div class="col">
				<div class="row half-height">
					<div class="col homepage-content-image-box">
						<div class="homepage-content1 homepage-content-image">
							<div class="homepage-content">
								<div class="flex-row">
									<div class="flex-item homepage-content-logo">
										<span class="fa-stack fa-3x"> <i
											class="fa fa-circle fa-stack-2x fa-inverse"></i> <i
											class="fa fa-shopping-cart fa-stack-1x"></i>
										</span>
									</div>
									<div class="flex-item homepage-content-desc">
										Shop for construction materials at <br />production rate
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col homepage-content-image-box">
						<div class="homepage-content2 homepage-content-image">
							<div class="homepage-content">
								<div class="flex-row">
									<div class="flex-item homepage-content-logo">
										<span class="fa-stack fa-3x"> <i
											class="fa fa-circle fa-stack-2x fa-inverse"></i> <i
											class="fa fa-envelope-open fa-stack-1x"></i>
										</span>
									</div>
									<div class="flex-item homepage-content-desc">
										Drop us a note and we will <br />contact you
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row half-height">
					<div class="col homepage-content-image-box">
						<div class="homepage-content3 homepage-content-image">
							<div class="homepage-content">
								<div class="flex-row">
									<div class="flex-item homepage-content-logo">
										<span class="fa-stack fa-3x"> <i
											class="fa fa-circle fa-stack-2x fa-inverse"></i> <i
											class="fa fa-camera fa-stack-1x"></i>
										</span>
									</div>
									<div class="flex-item homepage-content-desc">
										Unique world class designs at<br />economic price
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col homepage-content-image-box">
						<div class="login-container">
							<div class="homepage-content4 homepage-content-image">
								<div class="homepage-content">
									<div class="flex-row">
										<div class="flex-item homepage-content-logo">
											<span class="fa-stack fa-3x"> <i
												class="fa fa-circle fa-stack-2x fa-inverse"></i> <i
												class="fa fa-sign-in fa-stack-1x"></i>
											</span>
										</div>
										<div class="flex-item homepage-content-desc">
											Login to check the status of<br />your dream project
										</div>
									</div>
								</div>
							</div>
							<div class="card">
								<div class="card-block">
									<h3 class="card-title">Login</h3>
									<div class="card-text">
										<form>
											<div class="form-group row">
												<label for="username" class="col-sm-3 col-form-label">Username</label>
												<div class="col-sm-9">
													<input type="email" class="form-control" id="username"
														placeholder="Username">
												</div>
											</div>
											<div class="form-group row">
												<label for="password" class="col-sm-3 col-form-label">Password</label>
												<div class="col-sm-9">
													<input type="password" class="form-control"
														id="password" placeholder="Password">
												</div>
											</div>
											<div class="form-group row">
												<div class="offset-sm-3 col-sm-9">
													<button type="submit" class="btn btn-primary">Sign
														in</button>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="navbar-xs">
				<nav class="navbar fixed-bottom navbar-inverse bg-inverse bg-faded">
					<!-- Brand and toggle get grouped for better mobile display -->
					<div class="navbar-header">
						<div class="navbar-brand">
							<ul class="footer-links">
								<li><footer>
										<small>Copyright © 2017, </small><img
											src="resources/pics/seyon.png" width="20" height="27"
											class="d-inline-block align-top" alt="Seyon logo">
										Seyon. <small>All Rights Reserved.</small>
									</footer></li>
								<li class="footer-link"><a href="#">About us</a></li>
								<li><a href="#">Contact us</a></li>
							</ul>
						</div>
					</div>
				</nav>
			</div>
		</div>
	</div>
</body>
</html>