<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="sub_header.jsp" />
<c:set var="InEditMode" value="${canEdit}"></c:set>
<c:set var="IsNew" value="${isNewAccount}"></c:set>
<c:choose>
	<c:when test="${IsNew}">
		<c:set var="title" value="Create New Account"></c:set>
		<c:set var="mainButton" value="Create"></c:set>
		<c:set var="formAction" value="submitaccount"></c:set>
		<c:set var="cancelPage" value="account"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Edit Account"></c:set>
		<c:set var="mainButton" value="Save"></c:set>
		<c:set var="formAction" value="updateaccount"></c:set>
		<c:set var="cancelPage" value="retrieveAccount?num=${accountNumber}"></c:set>
	</c:otherwise>
</c:choose>
<title>${title} - Seyon</title>
<script>
	$(document).ready(function() {
		$('#create').click(function(e) {
			$('#createForm').submit();
			e.preventDefault();
		});
	});
</script>
</head>
<body>

	<!-- Modal -->
	<div class="modal fade" id="cancelModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">${title} - Seyon</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">Are you sure you want to cancel?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<a href="${cancelPage}" class="btn btn-primary" role="button">Yes</a>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid page-height">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="/Seyon">Home</a></li>
			<li class="breadcrumb-item"><a href="account">Account</a></li>
			<li class="breadcrumb-item active">${title}</li>
		</ol>
		<div class="row">
			<div class="col">
				<h3>${title}</h3>
				<hr />
				<form:form action="${formAction}" method="post" id="createForm"
					class="form-horizontal" commandName="accountLogin">
					<div class="row">
						<div class="col-sm-6">
							<legend>Account details</legend>
							<c:if test="${not IsNew}">
								<div class="form-group row">
									<label for="accountNumber" class="col-sm-3 col-form-label">Account
										#</label>
									<div class="col-sm-6">
										<form:input type="text" class="form-control" readonly="true"
											id="accountNumber" path="user.accountNumber"
											placeholder="ACXXXXXXXX" />
									</div>
								</div>
							</c:if>
							<div class="form-group row">
								<label for="name" class="col-sm-3 col-form-label">Name</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control" path="user.name"
										id="name" placeholder="Name" />
								</div>
							</div>
							<div class="form-group row">
								<label for="email" class="col-sm-3 col-form-label">Email</label>
								<div class="col-sm-6">
									<form:input type="email" class="form-control" path="user.email"
										id="email" placeholder="xxxxxxx@xxx.xxx" />
								</div>
							</div>
							<div class="form-group row">
								<label for="mobile" class="col-sm-3 col-form-label">Mobile
									Number</label>
								<div class="col-sm-6">
									<form:input type="number" class="form-control"
										path="user.mobileNumber" id="mobile" placeholder="XXXXXXXXXX" />
								</div>
							</div>
							<div class="form-group row">
								<label for="landline" class="col-sm-3 col-form-label">Landline
									Number</label>
								<div class="col-sm-6">
									<form:input type="number" class="form-control"
										path="user.landlineNumber" id="landline"
										placeholder="XXXXXXXXXX" />
								</div>
							</div>
							<div class="form-group row">
								<label for="username" class="col-sm-3 col-form-label">Username</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control" path="username" readonly="${not IsNew}"
										id="username" placeholder="Username" />
								</div>
							</div>
						</div>
						<div class="col-sm-6">
							<legend>Address</legend>
							<div class="form-group row">
								<label for="country" class="col-sm-3 col-form-label">Country</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										path="user.address.country" id="country" placeholder="Country" />
								</div>
							</div>
							<div class="form-group row">
								<label for="state" class="col-sm-3 col-form-label">State</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										path="user.address.state" id="state" placeholder="State" />
								</div>
							</div>
							<div class="form-group row">
								<label for="line1" class="col-sm-3 col-form-label">Address
									Line1</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										path="user.address.addressLine1" id="line1"
										placeholder="Address Line1" />
								</div>
							</div>
							<div class="form-group row">
								<label for="line2" class="col-sm-3 col-form-label">Address
									Line2</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										path="user.address.addressLine2" id="line2"
										placeholder="Address Line2" />
								</div>
							</div>
							<div class="form-group row">
								<label for="line3" class="col-sm-3 col-form-label">Address
									Line3</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										path="user.address.addressLine3" id="line3"
										placeholder="Address Line3" />
								</div>
							</div>
							<div class="form-group row">
								<label for="city" class="col-sm-3 col-form-label">City</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										path="user.address.city" id="city" placeholder="City" />
								</div>
							</div>
							<div class="form-group row">
								<label for="pincode" class="col-sm-3 col-form-label">Pincode</label>
								<div class="col-sm-6">
									<form:input type="number" class="form-control"
										path="user.address.pincode" id="pincode" placeholder="XXXXXX" />
								</div>
							</div>
						</div>
					</div>
				</form:form>
				<a href="#" class="btn btn-primary" id="create" role="button">${mainButton}</a>

				<a href="${cancelPage}" class="btn btn-secondary" id="cancel"
					data-toggle="modal" data-target="#cancelModal" role="button">Cancel</a>
			</div>
		</div>
	</div>
</body>
</html>