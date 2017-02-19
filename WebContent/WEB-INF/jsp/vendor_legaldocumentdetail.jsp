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
<c:set var="doc" value="${document}"></c:set>
<c:set var="InEditMode" value="${canEdit}"></c:set>
<title>Document# ${doc.name} - Seyon</title>
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
					Document# ${doc.name}
					<c:if test="${InEditMode}">
						<a href="editLegalDocument?num=${doc.name}" data-toggle="tooltip"
							data-placement="top" title="Edit Document"><i
							class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
					</c:if>
				</h3>
				<hr />
				<dl class="row">
					<dt class="col-sm-2">Name</dt>
					<dd class="col-sm-10">${doc.name}</dd>

					<dt class="col-sm-2">Created On</dt>
					<dd class="col-sm-10">
						<fmt:formatDate type="both" dateStyle="long" timeStyle="short"
							value="${doc.createTime}" />
					</dd>

					<dt class="col-sm-2">Type</dt>
					<dd class="col-sm-10">${doc.documentType.value}</dd>

					<dt class="col-sm-2">Description</dt>
					<dd class="col-sm-10">${doc.description}</dd>

					<dt class="col-sm-2">File Link</dt>
					<dd class="col-sm-10"><a target="_blank" href="${doc.fileLocation}">Open Document</a></dd>
				</dl>
			</div>
		</div>
	</div>
</body>
</html>