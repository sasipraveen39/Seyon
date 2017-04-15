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
		<c:set var="title" value="Create New Drawing"></c:set>
		<c:set var="mainButton" value="Create"></c:set>
		<c:set var="formAction" value="submitdrawing"></c:set>
		<c:set var="cancelPage" value="retrieveProject?num=${projectNumber}"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Edit Drawing"></c:set>
		<c:set var="mainButton" value="Save"></c:set>
		<c:set var="formAction" value="updatedrawing"></c:set>
		<c:set var="cancelPage"
			value="retrieveDrawing?num=${drawing.drawingNumber}"></c:set>
	</c:otherwise>
</c:choose>
<title>${title} - Seyon</title>
<script>
	$(document).ready(function() {
		$(document).ready(function() {
			$('#create').click(function(e) {
				if (validateFields($('#createForm'))) {
					$('#createForm').submit();
				}
				e.preventDefault();
			});
		});
		$('#createForm').ajaxForm({
			beforeSend : function() {
				var formDiv = $("#createForm");
				formDiv.after($("#fileUploadProgressMainTemplate").tmpl());
				$("#create").prop("disabled", true);
				$("#cancel").prop("disabled", true);
				$("#createForm :input").attr("disabled", true);
			},
			uploadProgress : function(event, position, total, percentComplete) {
				var percentVal = percentComplete + '%';
				$(".progress-bar").width(percentVal);
			},
			success : function() {
				window.location.href = "${cancelPage}";
			},
			error : function() {
			},
			complete : function() {
			}
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
				<form:form action="${formAction}" method="post" id="createForm"
					enctype="multipart/form-data" class="form-horizontal"
					commandName="drawing">
					<div class="row">
						<div class="col-sm-6">
							<legend>Drawing details</legend>
							<div class="form-group required row">
								<label for="drawingNumber" class="col-sm-3 col-form-label">Drawing
									#</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										readonly="${not IsNew}" id="drawingNumber"
										error-regex="^[a-zA-Z0-9 ]{3,}$" path="drawingNumber"
										placeholder="XXX" />
								</div>
							</div>
							<div class="form-group required row">
								<label for="type" class="col-sm-3 col-form-label">Type</label>
								<div class="col-sm-6">
									<form:select class="form-control" path="typeOfDrawing"
										id="type">
										<form:option value="" label="<none>" />
										<form:options items="${drawingTypes}" itemLabel="value" />
									</form:select>
								</div>
							</div>
							<div class="form-group required row">
								<label for="estimatedDateOfIssue"
									class="col-sm-3 col-form-label">Estd. Date of Issue</label>
								<div class="col-sm-6">
									<form:input type="date" class="form-control"
										id="estimatedDateOfIssue" path="estimatedDateOfIssue"
										placeholder="" />
								</div>
							</div>
							<div class="form-group row">
								<label for="dateOfIssue" class="col-sm-3 col-form-label">Date
									of Issue</label>
								<div class="col-sm-6">
									<form:input type="date" class="form-control" id="dateOfIssue"
										path="dateOfIssue" placeholder="" />
								</div>
							</div>
							<div class="form-group required row">
								<label for="status" class="col-sm-3 col-form-label">Status</label>
								<div class="col-sm-6">
									<form:select class="form-control" path="status" id="status">
										<form:option value="" label="<none>" />
										<form:options items="${statuses}" itemLabel="value" />
									</form:select>
								</div>
							</div>
						</div>
						<div class="col-sm-6">
							<legend>Document</legend>
							<div class="form-group required row">
								<label for="name" class="col-sm-3 col-form-label">Name</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										error-regex="^[a-zA-Z0-9 ]{2,}$" path="document.name"
										id="name" placeholder="Document Name" />
								</div>
							</div>
							<div class="form-group row">
								<label for="description" class="col-sm-3 col-form-label">Description</label>
								<div class="col-sm-6">
									<form:textarea class="form-control" rows="3" id="description"
										error-regex="^[\w\., ]{2,}$" path="document.description"></form:textarea>
								</div>
							</div>
							<div class="form-group ${isNew?'required':'' } row">
								<label for="drawingFile" class="col-sm-3 col-form-label">Drawing
									File</label>
								<div class="col-sm-6">
									<input type="file" class="form-control-file" id="drawingFile"
										name="drawingFile" accept="application/pdf,image/x-png,image/gif,image/jpeg"
										aria-describedby="fileHelp"> <small id="fileHelp"
										class="form-text text-muted">Upload only pdf file, jpeg, png
												or gif file.<c:if
											test="${not isNew}">
											<b><a target="_blank"
												href="${drawing.document.fileLocation}">Open Document</a></b>
										</c:if></small>
								</div>
							</div>
						</div>
					</div>
					<form:hidden path="document.fileLocation" />
					<form:hidden path="document.documentNumber" />
					<input type="hidden" name="projNumber" value="${projectNumber}" />
				</form:form>
				<button class="btn btn-primary" id="create">${mainButton}</button>

				<button class="btn btn-secondary" id="cancel" data-toggle="modal"
					data-target="#cancelModal">Cancel</button>
			</div>
		</div>
	</div>
</body>
</html>