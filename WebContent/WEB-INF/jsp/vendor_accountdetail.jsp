<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
				<h3>Account# ${acct.accountNumber} 
				<c:if test="${canEdit}"><a href="editAccount?num=${acct.accountNumber}" data-toggle="tooltip" data-placement="top" title="Edit Account"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a></c:if></h3><hr/>
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
					<c:if test="${not empty acct.address.addressLine2}"><dd class="col-sm-9 offset-sm-2">${acct.address.addressLine2},</dd></c:if>  
					<c:if test="${not empty acct.address.addressLine3}"><dd class="col-sm-9 offset-sm-2">${acct.address.addressLine3},</dd></c:if>
					<dd class="col-sm-9 offset-sm-2">${acct.address.city} - ${acct.address.pincode}</dd>
					<dd class="col-sm-9 offset-sm-2">${acct.address.state}, ${acct.address.country}</dd>
				</dl>
			</div>
		</div>
	</div>
</body>
</html>