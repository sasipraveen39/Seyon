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
<c:set var="payment" value="${paymentDetail}"></c:set>
<c:set var="InEditMode" value="${canEdit}"></c:set>
<c:set var="title" value="Payment# ${payment.paymentNumber}"></c:set>
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
				href="retrieveProject?num=${payment.bill.project.projectNumber}">Project#
					${payment.bill.project.projectNumber}</a></li>
			<li class="breadcrumb-item active">${title}</li>
		</ol>
		<div class="row">
			<div class="col">
				<h3>
					Payment# ${payment.paymentNumber}
					<c:if test="${canEdit}">
						<a href="editPayment?num=${payment.paymentNumber}"
							data-toggle="tooltip" data-placement="top" title="Edit Payment"><i
							class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
					</c:if>
				</h3>
				<hr />
				<div class="row">
					<div class="col-sm-6">
						<dl class="row">
							<dt class="col-sm-4">Project #</dt>
							<dd class="col-sm-8">
								<b><a
									href="retrieveProject?num=${payment.bill.project.projectNumber}">${payment.bill.project.projectNumber}</a></b>
							</dd>

							<dt class="col-sm-4">Bill #</dt>
							<dd class="col-sm-8">
								<b><a href="retrieveBill?num=${payment.bill.billNumber}">${payment.bill.billNumber}</a></b>
							</dd>

							<dt class="col-sm-4">Status</dt>
							<dd class="col-sm-8">${payment.status.value}</dd>

							<dt class="col-sm-4">Due Date</dt>
							<dd class="col-sm-8">
								<fmt:formatDate type="both" dateStyle="long" timeStyle="short"
									value="${payment.dueDate}" />
							</dd>

							<dt class="col-sm-4">Amount Payable</dt>
							<dd class="col-sm-8">&#x20b9; ${payment.amountPayable}</dd>

							<c:if test="${not empty payment.paymentDate}">
								<dt class="col-sm-4">Paid Date</dt>
								<dd class="col-sm-8">
									<fmt:formatDate type="both" dateStyle="long" timeStyle="short"
										value="${payment.paymentDate}" />
								</dd>

								<dt class="col-sm-4">Mode of Payment</dt>
								<dd class="col-sm-8">${payment.modeOfPayment.value}</dd>

								<dt class="col-sm-4">Reference #</dt>
								<dd class="col-sm-8">${payment.referenceNumber}</dd>

								<dt class="col-sm-4">Created On</dt>
								<dd class="col-sm-8">
									<fmt:formatDate type="both" dateStyle="long" timeStyle="short"
										value="${payment.createTime}" />
								</dd>
							</c:if>
						</dl>
						<c:if test="${empty payment.receiptNumber}">
							<button type="button" onclick="window.location.href='generateReceipt?num=${payment.paymentNumber}'"class="btn btn-warning btn-sm" ${payment.status == 'PAID'?'':'disabled'}>Generate receipt</button>
							<p><em>Mark the Payment as paid to generate receipt.</em></p>
						</c:if>
					</div>
					<div class="col-sm-6">
						<dl class="row">
							<c:if test="${not empty payment.receiptNumber}">
								<dt class="col-sm-4">Receipt #</dt>
								<dd class="col-sm-8">${payment.receiptNumber}</dd>

								<dt class="col-sm-4">Receipt Date</dt>
								<dd class="col-sm-8">
									<fmt:formatDate type="both" dateStyle="long" timeStyle="short"
										value="${payment.receiptDate}" />
								</dd>

								<dt class="col-sm-4">Receipt Name</dt>
								<dd class="col-sm-8">${payment.document.name}</dd>

								<dt class="col-sm-4">Description</dt>
								<dd class="col-sm-8">${payment.document.description}</dd>

								<dt class="col-sm-4">File Link</dt>
								<dd class="col-sm-8">
									<c:if test="${not empty payment.document.fileLocation}">
										<b><a target="_blank"
											href="${payment.document.fileLocation}">Open Document</a></b>
									</c:if>
								</dd>
							</c:if>
						</dl>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>