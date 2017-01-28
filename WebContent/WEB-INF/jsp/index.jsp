<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="header.jsp" />
<title>HomePage - Seyon</title>
<script>

</script>
</head>
<body>
	<c:if test="${not empty errorMessage}">
		<div class="top-alert">
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>Oops!</strong> ${errorMessage}.
			</div>
		</div>
	</c:if>
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
										<form:form id="login-form" class="form-horizontal"
											commandName="login" action="login" method="post">
											<div class="form-group row">
												<label for="username" class="col-sm-3 col-form-label">Username</label>
												<div class="col-sm-9">
													<form:input path="username" id="username" name="username"
														class="form-control" type="text"
														placeholder="Username" />
												</div>
											</div>
											<div class="form-group row">
												<label for="password" class="col-sm-3 col-form-label">Password</label>
												<div class="col-sm-9">
													<form:input path="password" id="password" name="password" type="password"
														placeholder="Password" class="form-control" />
												</div>
											</div>
											<div class="form-group row">
												<div class="offset-sm-3 col-sm-9">
													<button type="submit" class="btn btn-primary">Sign
														in</button>
												</div>
											</div>
										</form:form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>