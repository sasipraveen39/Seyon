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
		<c:set var="title" value="Create New Legal Document"></c:set>
		<c:set var="mainButton" value="Create"></c:set>
		<c:set var="formAction" value="submitlegaldocument"></c:set>
		<c:set var="cancelPage" value="retrieveProject?num=${projectNumber}"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Edit Drawing"></c:set>
		<c:set var="mainButton" value="Save"></c:set>
		<c:set var="formAction" value="updatelegaldocument"></c:set>
		<c:set var="cancelPage" value="retrieveProject?num=${projectNumber}"></c:set>
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
			<c:choose>
				<c:when test="${not empty prevURL}">
					<li class="breadcrumb-item"><a href="account">Account</a></li>
					<li class="breadcrumb-item"><a href="${prevURL}">Account#
							${fn:substringAfter(prevURL, "num=")}</a></li>
				</c:when>
				<c:otherwise>
					<li class="breadcrumb-item"><a href="project">Project</a></li>
				</c:otherwise>
			</c:choose>
			<li class="breadcrumb-item"><a href="${cancelPage}">Project#
					${projectNumber}</a></li>
			<li class="breadcrumb-item active">${title}</li>
		</ol>
		<div class="row">
			<div class="col">
				<h3>${title}</h3>
				<hr />
				<form:form action="${formAction}" method="post" id="createForm" enctype="multipart/form-data"
					class="form-horizontal" commandName="legalDocument">
					<div class="row">
						<div class="col-sm-6">
							<legend>Document details</legend>
							<div class="form-group row">
								<label for="name" class="col-sm-3 col-form-label">Name</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										path="name" id="name" placeholder="Document Name" />
								</div>
							</div>
							<div class="form-group row">
								<label for="description" class="col-sm-3 col-form-label">Description</label>
								<div class="col-sm-6">
									<form:textarea class="form-control" rows="3" id="description"
										path="description"></form:textarea>
								</div>
							</div>
							<div class="form-group row">
								<label for="documentFile" class="col-sm-3 col-form-label">Document File</label>
								<div class="col-sm-6">
									<input type="file" class="form-control-file" id="documentFile" name="documentFile"
										accept="application/pdf"
										aria-describedby="fileHelp"> <small id="fileHelp"
										class="form-text text-muted">Upload only pdf file.</small>
								</div>
							</div>
						</div>
					</div>
					<input type="hidden" name="projNumber" value="${projectNumber}"/>
				</form:form>
				<a href="#" class="btn btn-primary" id="create" role="button">${mainButton}</a>

				<a href="${cancelPage}" class="btn btn-secondary" id="cancel"
					data-toggle="modal" data-target="#cancelModal" role="button">Cancel</a>
			</div>
		</div>
	</div>
</body>
</html>