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
<c:set var="bill" value="${billDetail}"></c:set>
<c:set var="InEditMode" value="${canEdit}"></c:set>
<c:set var="title" value="Bill# ${bill.billNumber}"></c:set>
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
				href="retrieveProject?num=${bill.project.projectNumber}">Project#
					${bill.project.projectNumber}</a></li>
			<li class="breadcrumb-item active">${title}</li>
		</ol>
		<div class="row">
			<div class="col">
				<h3>
					Bill# ${bill.billNumber}
					<c:if test="${canEdit}">
						<a href="editBill?num=${bill.billNumber}" data-toggle="tooltip"
							data-placement="top" title="Edit Bill"><i
							class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
					</c:if>
				</h3>
				<hr />
				<dl class="row">
					<dt class="col-sm-2">Project #</dt>
					<dd class="col-sm-10">
						<b><a href="retrieveProject?num=${bill.project.projectNumber}">${bill.project.projectNumber}</a></b>
					</dd>

					<dt class="col-sm-2">Type</dt>
					<dd class="col-sm-10">${bill.billType.value}</dd>

					<dt class="col-sm-2">Status</dt>
					<dd class="col-sm-10">${bill.billStatus.value}</dd>

					<dt class="col-sm-2">Bill Date</dt>
					<dd class="col-sm-10">
						<fmt:formatDate type="both" dateStyle="long" timeStyle="short"
							value="${draw.billDate}" />
					</dd>

					<dt class="col-sm-2">Total Amount</dt>
					<dd class="col-sm-10">&#x20b9; ${bill.totalBillAmount}</dd>

					<dt class="col-sm-2">Document Name</dt>
					<dd class="col-sm-10">${bill.document.name}</dd>

					<dt class="col-sm-2">Description</dt>
					<dd class="col-sm-10">${bill.document.description}</dd>

					<dt class="col-sm-2">File Link</dt>
					<dd class="col-sm-10">
						<b><a target="_blank" href="${bill.document.fileLocation}">Open
								Document</a></b>
					</dd>

					<dt class="col-sm-2">Created On</dt>
					<dd class="col-sm-10">
						<fmt:formatDate type="both" dateStyle="long" timeStyle="short"
							value="${bill.createTime}" />
					</dd>
				</dl>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<div class="card">
					<div class="card-header">
						<h5>Payment details</h5>
					</div>
					<div class="card-block">
						<div class="btn-toolbar" role="toolbar"
							aria-label="Toolbar with button groups">
							<div class="btn-group" role="group">
								<button type="button" id="addProject"
									class="btn btn-primary btn-sm">Add Payment</button>
								<button type="button" id="deleteProject"
									class="btn btn-secondary btn-sm">Remove Payment</button>
							</div>
						</div>
						<br />
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Payment #</th>
									<th>Due Date</th>
									<th>Amount Payable</th>
									<th>Status</th>
									<th>Mode of Payment</th>
									<th>Link</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${bill.payments}" var="payment">
									<tr>
										<th scope="row"><a href="retrievePayment?num=${'001'}">PY00000001</a></th>
										<td><fmt:formatDate type="date" dateStyle="long"
												value="${payment.dueDate}" /></td>
										<td>&#x20b9; ${payment.amountPayable}</td>
										<td>&#x20b9; ${payment.status}</td>
										<td>&#x20b9; ${payment.modeOfPayment.value}</td>
										<td><a target="_blank"
											href="${payment.document.fileLocation}"
											class="btn btn-primary btn-sm" role="button">Open Receipt</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>