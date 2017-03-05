<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="sub_header.jsp" />
<c:set var="acct" value="${account}"></c:set>
<c:set var="canEdit" value="${canEditAccount}"></c:set>
<title>Account# ${acct.accountNumber} - Seyon</title>
</head>
<body>
	<div class="container-fluid page-height">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="/Seyon">Home</a></li>
			<li class="breadcrumb-item"><a href="account">Account</a></li>
			<li class="breadcrumb-item active">Account#
				${acct.accountNumber}</li>
		</ol>
		<div class="row">
			<div class="col">
				<h3>
					Account# ${acct.accountNumber}
					<c:if test="${canEdit}">
						<a href="editAccount?num=${acct.accountNumber}"
							data-toggle="tooltip" data-placement="top" title="Edit Account"><i
							class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
					</c:if>
				</h3>
				<hr />
				<dl class="row">
					<dt class="col-sm-2">Name</dt>
					<dd class="col-sm-10">${acct.name}</dd>

					<dt class="col-sm-2">Email</dt>
					<dd class="col-sm-10">${acct.email}</dd>

					<dt class="col-sm-2">Mobile Number</dt>
					<dd class="col-sm-10">${acct.mobileNumber}</dd>

					<dt class="col-sm-2">Landline Number</dt>
					<dd class="col-sm-10">${acct.landlineNumber}</dd>

					<dt class="col-sm-2">Address</dt>
					<dd class="col-sm-10">${acct.address.addressLine1},</dd>
					<c:if test="${not empty acct.address.addressLine2}">
						<dd class="col-sm-10 offset-sm-2">${acct.address.addressLine2},</dd>
					</c:if>
					<c:if test="${not empty acct.address.addressLine3}">
						<dd class="col-sm-10 offset-sm-2">${acct.address.addressLine3},</dd>
					</c:if>
					<dd class="col-sm-10 offset-sm-2">${acct.address.city} -
						${acct.address.pincode}</dd>
					<dd class="col-sm-10 offset-sm-2">${acct.address.state},
						${acct.address.country}</dd>

					<dt class="col-sm-2">Created On</dt>
					<dd class="col-sm-10">
						<fmt:formatDate type="both" dateStyle="long" timeStyle="short"
							value="${acct.createTime}" />
					</dd>
				</dl>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<div id="projectPanel">
					<div class="card">
						<div class="card-header">
							<h5>Project details</h5>
						</div>
						<div class="card-block">
							<div class="card-text">
								<div class="btn-toolbar justify-content-between" role="toolbar"
									aria-label="Toolbar with button groups">
									<div class="btn-group" role="group">
										<button type="button" id="addProject"
											onclick="window.location.href='newProject?num=${acct.accountNumber}'"
											class="btn btn-primary btn-sm">Add Project</button>
										<button type="button" id="deleteProject" data-toggle="button"
											aria-pressed="false" autocomplete="off"
											class="btn btn-secondary btn-sm remove-button">Remove
											Project</button>
									</div>
									<div class="btn-group" role="group">
										<button type="button" data-toggle="modal"
											data-target="#itemDeleteModal" name="Project"
											class="btn btn-danger btn-sm delete-button invisible">Delete
											Selected Projects</button>
									</div>
								</div>
								<br />
								<table class="table table-striped delete-table">
									<thead>
										<tr>
											<th>Project #</th>
											<th>Title</th>
											<th>Type</th>
											<th>Address</th>
											<th>Estimated Amount</th>
											<th>Estimated End Date</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${acct.projects}" var="project">
											<tr id="${project.idproject}">
												<th scope="row"><a
													href="retrieveProject?num=${project.projectNumber}">${project.projectNumber}</a></th>
												<td>${project.title}</td>
												<td>${project.projectType.value}</td>
												<td>${project.address.city} -
													${project.address.pincode}</td>
												<td>&#x20b9; ${project.estimatedTotalAmount}</td>
												<td><fmt:formatDate type="date" dateStyle="long"
														value="${project.estimatedEndDate}" /></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>