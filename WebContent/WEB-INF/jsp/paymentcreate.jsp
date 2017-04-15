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
		<c:set var="title" value="Create New Payment"></c:set>
		<c:set var="mainButton" value="Create"></c:set>
		<c:set var="formAction" value="submitpayment"></c:set>
		<c:set var="cancelPage" value="retrieveBill?num=${billNumber}"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Edit Payment"></c:set>
		<c:set var="mainButton" value="Save"></c:set>
		<c:set var="formAction" value="updatepayment"></c:set>
		<c:set var="cancelPage"
			value="retrievePayment?num=${payment.paymentNumber}"></c:set>
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
		
		$('#status').on('change', function(){
			if($('#status').val() == 'PAID'){
				$('.conditional').removeClass('hidden');
			}else{
				$('.conditional').addClass('hidden');
			}
		});

		$('#status').trigger('change');
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
					class="form-horizontal" commandName="payment"> <!-- enctype="multipart/form-data"  -->
					<div class="row">
						<div class="col-sm-6">
							<legend>Payment details</legend>
							<c:if test="${not IsNew}">
								<div class="form-group row">
									<label for="paymentNumber" class="col-sm-3 col-form-label">Payment
										#</label>
									<div class="col-sm-6">
										<form:input type="text" class="form-control" readonly="true"
											id="paymentNumber" path="paymentNumber"
											placeholder="XXXXXXXXXX" />
									</div>
								</div>
							</c:if>
							<div class="form-group required row">
								<label for="status" class="col-sm-3 col-form-label">Status</label>
								<div class="col-sm-6">
									<form:select class="form-control" path="status" id="status">
										<form:option value="" label="<none>" />
										<form:options items="${paymentStatuses}" itemLabel="value" />
									</form:select>
								</div>
							</div>
							<div class="form-group required row">
								<label for="dueDate" class="col-sm-3 col-form-label">Due
									Date</label>
								<div class="col-sm-6">
									<form:input type="date" class="form-control" id="dueDate"
										path="dueDate" placeholder="" />
								</div>
							</div>
							<div class="form-group required row">
								<label for="amountPayable" class="col-sm-3 col-form-label">Amount
									Payable</label>
								<div class="col-sm-6 input-group">
									<span class="input-group-addon" id="ruppee-addon1">&#8377;</span>
									<form:input type="number" class="form-control"
										id="amountPayable" path="amountPayable" placeholder=""
										aria-describedby="ruppee-addon1" />
								</div>
							</div>
							<div class="form-group required conditional row">
								<label for="paymentDate" class="col-sm-3 col-form-label">Paid
									Date</label>
								<div class="col-sm-6">
									<form:input type="date" class="form-control" id="paymentDate"
										path="paymentDate" placeholder="" />
								</div>
							</div>
							<div class="form-group required conditional row">
								<label for="modeOfPayment" class="col-sm-3 col-form-label">Mode
									of Payment</label>
								<div class="col-sm-6">
									<form:select class="form-control" path="modeOfPayment"
										id="modeOfPayment">
										<form:option value="" label="<none>" />
										<form:options items="${modeOfPayments}" itemLabel="value" />
									</form:select>
								</div>
							</div>
							<div class="form-group required conditional row">
								<label for="referenceNumber" class="col-sm-3 col-form-label">Reference
									#</label>
								<div class="col-sm-6">
									<form:input type="text" class="form-control"
										error-regex="^[a-zA-Z0-9 ]{2,}$" path="referenceNumber"
										id="referenceNumber" placeholder="XX" />
								</div>
							</div>
						</div>
						<div class="col-sm-6">
						
						</div>
					</div>
					<form:hidden path="document.fileLocation" />
					<form:hidden path="document.documentNumber" />
					<input type="hidden" name="bNumber" value="${billNumber}" />
				</form:form>
				<button class="btn btn-primary" id="create">${mainButton}</button>

				<button class="btn btn-secondary" id="cancel" data-toggle="modal"
					data-target="#cancelModal">Cancel</button>
			</div>
		</div>
	</div>
</body>
</html>