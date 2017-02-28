<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="sub_header.jsp" />
<c:set var="InEditMode" value="${canEdit}"></c:set>
<c:set var="IsNew" value="${isNew}"></c:set>
<c:choose>
	<c:when test="${IsNew}">
		<c:set var="title" value="Create New Project"></c:set>
		<c:set var="mainButton" value="Create"></c:set>
		<c:set var="formAction" value="submitproject"></c:set>
		<c:set var="cancelPage" value="retrieveAccount?num=${accountNumber}"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Edit Project"></c:set>
		<c:set var="mainButton" value="Save"></c:set>
		<c:set var="formAction" value="updateproject"></c:set>
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
					<h5 class="modal-title" id="exampleModalLabel">${title} -
						Seyon</h5>
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
			<li class="breadcrumb-item"><a href="${cancelPage}">Account#
					${accountNumber}</a></li>
			<li class="breadcrumb-item active">${title}</li>
		</ol>
		<div class="row">
			<div class="col">
				<h3>${title}</h3>
				<hr />
				<form:form action="${formAction}" method="post" id="createForm"
					class="form-horizontal" commandName="project">
					<div class="row">
						<div class="col-sm-6">
							<legend>Project details</legend>
							<c:if test="${not isNew}">
								<div class="form-group row">
									<label for="projectNumber" class="col-sm-3 col-form-label">Project
										#</label>
									<div class="col-sm-6">
										<form:input type="text" class="form-control" readonly="true"
											id="projectNumber" path="projectNumber"
											placeholder="PRXXXXXXXX" />
									</div>
								</div>
							</c:if>
							<div class="form-group row">
								<label for="title" class="col-sm-3 col-form-label">Title</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control" id="title"
										path="title" placeholder="Title" />
								</div>
							</div>
							<div class="form-group row">
								<label for="clientName" class="col-sm-3 col-form-label">Client
									Name</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control" id="clientName"
										path="clientName" placeholder="Client Name" />
								</div>
							</div>
							<div class="form-group row">
								<label for="projectType" class="col-sm-3 col-form-label">Project
									Type</label>
								<div class="col-sm-6">
									<form:select class="form-control" path="projectType"
										id="projectType">
										<form:option value="" label="<none>" />
										<form:options items="${projectTypes}" itemLabel="value" />
									</form:select>
								</div>
							</div>

							<div class="form-group row">
								<label for="totalAreaOfProject" class="col-sm-3 col-form-label">Total
									Area of Project</label>
								<div class="col-sm-6 input-group">
									<form:input type="number" class="form-control"
										id="totalAreaOfProject" path="totalAreaOfProject"
										placeholder="" aria-describedby="sqft-addon1" />
									<span class="input-group-addon" id="sqft-addon1">sqft</span>
								</div>
							</div>
							<div class="form-group row">
								<label for="requestedDate" class="col-sm-3 col-form-label">Requested
									Date</label>
								<div class="col-sm-6">
									<form:input type="date" class="form-control" id="requestedDate"
										path="requestedDate" placeholder="" />
								</div>
							</div>
							<div class="form-group row">
								<label for="estimatedEndDate" class="col-sm-3 col-form-label">Estd.
									End Date</label>
								<div class="col-sm-6">
									<form:input type="date" class="form-control"
										id="estimatedEndDate" path="estimatedEndDate" placeholder="" />
								</div>
							</div>
							<div class="form-group row">
								<label for="estimatedTotalAmount"
									class="col-sm-3 col-form-label">Estd. Total Amount</label>
								<div class="col-sm-6 input-group">
									<span class="input-group-addon" id="ruppee-addon1">&#8377;</span>
									<form:input type="number" class="form-control"
										id="estimatedTotalAmount" path="estimatedTotalAmount"
										placeholder="" aria-describedby="ruppee-addon1" />
								</div>
							</div>
						</div>
						<div class="col-sm-6">
							<legend>Address</legend>
							<div class="form-group row">
								<label for="country" class="col-sm-3 col-form-label">Country</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										path="address.country" id="country"
										placeholder="Country" />
								</div>
							</div>
							<div class="form-group row">
								<label for="state" class="col-sm-3 col-form-label">State</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										path="address.state" id="state" placeholder="State" />
								</div>
							</div>
							<div class="form-group row">
								<label for="line1" class="col-sm-3 col-form-label">Address
									Line1</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										path="address.addressLine1" id="line1"
										placeholder="Address Line1" />
								</div>
							</div>
							<div class="form-group row">
								<label for="line2" class="col-sm-3 col-form-label">Address
									Line2</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										path="address.addressLine2" id="line2"
										placeholder="Address Line2" />
								</div>
							</div>
							<div class="form-group row">
								<label for="line3" class="col-sm-3 col-form-label">Address
									Line3</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										path="address.addressLine3" id="line3"
										placeholder="Address Line3" />
								</div>
							</div>
							<div class="form-group row">
								<label for="city" class="col-sm-3 col-form-label">City</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										path="address.city" id="city" placeholder="City" />
								</div>
							</div>
							<div class="form-group row">
								<label for="pincode" class="col-sm-3 col-form-label">Pincode</label>
								<div class="col-sm-6">
									<form:input type="number" class="form-control"
										path="address.pincode" id="pincode"
										placeholder="XXXXXX" />
								</div>
							</div>
						</div>
					</div>
					<form:hidden path="user.accountNumber" />
				</form:form>
				<button class="btn btn-primary" id="create">${mainButton}</button>

				<button class="btn btn-secondary" id="cancel" data-toggle="modal"
					data-target="#cancelModal">Cancel</button>
			</div>
		</div>
	</div>
</body>
</html>