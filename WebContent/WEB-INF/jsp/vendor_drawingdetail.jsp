<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="sub_header.jsp" />
<c:set var="draw" value="${drawing}"></c:set>
<c:set var="InEditMode" value="${canEdit}"></c:set>
<c:set var="title" value="Drawing# ${draw.drawingNumber}"></c:set>
<title>${title} - Seyon</title>
</head>
<body>
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
			<li class="breadcrumb-item"><a
				href="retrieveProject?num=${draw.project.projectNumber}">Project#
					${draw.project.projectNumber}</a></li>
			<li class="breadcrumb-item active">${title}</li>
		</ol>
		<div class="row">
			<div class="col">
				<h3>
					${title}
					<c:if test="${InEditMode}">
						<a href="editDrawing?num=${draw.drawingNumber}"
							data-toggle="tooltip" data-placement="top" title="Edit Drawing"><i
							class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
					</c:if>
				</h3>
				<hr />
				<dl class="row">
					<dt class="col-sm-2">Project #</dt>
					<dd class="col-sm-10">
						<b><a href="retrieveProject?num=${draw.project.projectNumber}">${draw.project.projectNumber}</a></b>
					</dd>

					<dt class="col-sm-2">Type</dt>
					<dd class="col-sm-10">${draw.typeOfDrawing.value}</dd>

					<dt class="col-sm-2">Status</dt>
					<dd class="col-sm-10">${draw.status.value}</dd>

					<dt class="col-sm-2">Estd. Date of Issue</dt>
					<dd class="col-sm-10">
						<fmt:formatDate type="both" dateStyle="long" timeStyle="short"
							value="${draw.estimatedDateOfIssue}" />
					</dd>

					<dt class="col-sm-2">Date of Issue</dt>
					<dd class="col-sm-10">
						<fmt:formatDate type="both" dateStyle="long" timeStyle="short"
							value="${draw.dateOfIssue}" />
					</dd>

					<dt class="col-sm-2">Document Name</dt>
					<dd class="col-sm-10">${draw.document.name}</dd>

					<dt class="col-sm-2">Description</dt>
					<dd class="col-sm-10">${draw.document.description}</dd>

					<dt class="col-sm-2">File Link</dt>
					<dd class="col-sm-10">
						<b><a target="_blank" href="${draw.document.fileLocation}">Open
							Document</a></b>
					</dd>

					<dt class="col-sm-2">Created On</dt>
					<dd class="col-sm-10">
						<fmt:formatDate type="both" dateStyle="long" timeStyle="short"
							value="${draw.createTime}" />
					</dd>
				</dl>
			</div>
		</div>
	</div>
</body>
</html>