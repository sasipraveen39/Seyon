<%@page import="co.seyon.enums.DocumentType"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@page import="co.seyon.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="sub_header.jsp" />
<c:set var="proj" value="${project}"></c:set>
<c:set var="canEdit" value="${canEditProject}"></c:set>
<title>Project# ${proj.projectNumber} - Seyon</title>
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
			<li class="breadcrumb-item active">Project#
				${proj.projectNumber}</li>
		</ol>
		<div class="row">
			<div class="col">
				<h3>
					Project# ${proj.projectNumber}
					<c:if test="${canEdit}">
						<a href="editProject?num=${proj.projectNumber}"
							data-toggle="tooltip" data-placement="top" title="Edit Project"><i
							class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
					</c:if>
				</h3>
				<hr />
				<ul class="nav nav-tabs" role="tablist">
					<li class="nav-item"><a class="nav-link active"
						data-toggle="tab" href="#infoPanel" role="tab">Info</a></li>
					<li class="nav-item"><a class="nav-link" data-toggle="tab"
						href="#documentPanel" role="tab">Legal Documents</a></li>
					<li class="nav-item"><a class="nav-link" data-toggle="tab"
						href="#drawingPanel" role="tab">Drawings</a></li>
					<li class="nav-item"><a class="nav-link" data-toggle="tab"
						href="#billPanel" role="tab">Bills</a></li>
					<li class="nav-item"><a class="nav-link" data-toggle="tab"
						href="#imagePanel" role="tab">Images</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="infoPanel" role="tabpanel">
						<div class="card">
							<div class="card-block">
								<div class="card-text">
									<div class="row">
										<div class="col">
											<dl class="row">
												<dt class="col-sm-3">Account #</dt>
												<dd class="col-sm-9">
													<b><a
														href="retrieveAccount?num=${proj.user.accountNumber}">${proj.user.accountNumber}</a></b>
												</dd>

												<dt class="col-sm-3">Title</dt>
												<dd class="col-sm-9">${proj.title}</dd>

												<dt class="col-sm-3">Client Name</dt>
												<dd class="col-sm-9">${proj.clientName}</dd>

												<dt class="col-sm-3">Address</dt>
												<dd class="col-sm-9">${proj.address.addressLine1},</dd>
												<c:if test="${not empty proj.address.addressLine2}">
													<dd class="col-sm-9 offset-sm-3">${proj.address.addressLine2},</dd>
												</c:if>
												<c:if test="${not empty proj.address.addressLine3}">
													<dd class="col-sm-9 offset-sm-3">${proj.address.addressLine3},</dd>
												</c:if>
												<dd class="col-sm-9 offset-sm-3">${proj.address.city} -
													${proj.address.pincode}</dd>
												<dd class="col-sm-9 offset-sm-3">${proj.address.state},
													${proj.address.country}</dd>
											</dl>
										</div>
										<div class="col">
											<dl class="row">
												<dt class="col-sm-4">Type</dt>
												<dd class="col-sm-8">${proj.projectType.value}</dd>

												<dt class="col-sm-4">Total Area</dt>
												<dd class="col-sm-8">${proj.totalAreaOfProject} sqft</dd>

												<dt class="col-sm-4">Requested Date</dt>
												<dd class="col-sm-8">
													<fmt:formatDate type="date" dateStyle="long"
														value="${proj.requestedDate}" />
												</dd>

												<dt class="col-sm-4">Start Date</dt>
												<dd class="col-sm-8">
													<fmt:formatDate type="date" dateStyle="long"
														value="${proj.startDate}" />
												</dd>

												<dt class="col-sm-4">Estimated End Date</dt>
												<dd class="col-sm-8">
													<fmt:formatDate type="date" dateStyle="long"
														value="${proj.estimatedEndDate}" />
												</dd>

												<c:if test="${not empty proj.actualEndDate}">
													<dt class="col-sm-4">Actual End Date</dt>
													<dd class="col-sm-8">
														<fmt:formatDate type="date" dateStyle="long"
															value="${proj.actualEndDate}" />
													</dd>
												</c:if>

												<dt class="col-sm-4">Estimated Total Amount</dt>
												<dd class="col-sm-8">&#x20b9;
													${proj.estimatedTotalAmount}</dd>
											</dl>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="documentPanel" role="tabpanel">
						<div class="card">
							<div class="card-header">
								<h5>Legal Documents</h5>
							</div>
							<div class="card-block">
								<div class="card-text">
									<div class="btn-toolbar" role="toolbar"
										aria-label="Toolbar with button groups">
										<div class="btn-group" role="group">
											<button type="button" id="addProject"
												class="btn btn-primary btn-sm">Add Document</button>
											<button type="button" id="deleteProject"
												class="btn btn-secondary btn-sm">Remove Document</button>
										</div>
									</div>
									<br />
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Document #</th>
												<th>Name</th>
												<th>Link</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${proj.documents}" var="document">
												<c:if test="${document.documentType == 'CONTRACT'}">
													<tr>
														<th scope="row">DC00000001</th>
														<td>${document.name}</td>
														<td><a href="#" class="btn btn-primary btn-sm"
															role="button">Open Document</a></td>
													</tr>
												</c:if>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="drawingPanel" role="tabpanel">
						<div class="card">
							<div class="card-header">
								<h5>Drawings</h5>
							</div>
							<div class="card-block">
								<div class="card-text">
									<div class="btn-toolbar" role="toolbar"
										aria-label="Toolbar with button groups">
										<div class="btn-group" role="group">
											<button type="button" id="addProject"
												class="btn btn-primary btn-sm">Add Drawing</button>
											<button type="button" id="deleteProject"
												class="btn btn-secondary btn-sm">Remove Drawing</button>
										</div>
									</div>
									<br />
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Drawing #</th>
												<th>Type</th>
												<th>Status</th>
												<th>Est. Date of Issue</th>
												<th>Date of Issue</th>
												<th>Link</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${proj.drawings}" var="drawing">
												<tr>
													<th scope="row">${drawing.drawingNumber}</th>
													<td>${drawing.typeOfDrawing.value}</td>
													<td>${drawing.status.value}</td>
													<td><fmt:formatDate type="date" dateStyle="long"
															value="${drawing.estimatedDateOfIssue}" /></td>
													<td><fmt:formatDate type="date" dateStyle="long"
															value="${drawing.dateOfIssue}" /></td>
													<td><a href="#" class="btn btn-primary btn-sm"
														role="button">Open Drawing</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="billPanel" role="tabpanel">
						<div class="card">
							<div class="card-header">
								<h5>Bills</h5>
							</div>
							<div class="card-block">
								<div class="card-text">
									<div class="btn-toolbar" role="toolbar"
										aria-label="Toolbar with button groups">
										<div class="btn-group" role="group">
											<button type="button" id="addProject"
												class="btn btn-primary btn-sm">Add Bill</button>
											<button type="button" id="deleteProject"
												class="btn btn-secondary btn-sm">Remove Bill</button>
										</div>
									</div>
									<br />
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Bill #</th>
												<th>Date</th>
												<th>Type</th>
												<th>Status</th>
												<th>Amount</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${proj.bills}" var="bill">
												<tr>
													<th scope="row">${bill.billNumber}</th>
													<td><fmt:formatDate type="date" dateStyle="long"
															value="${bill.billDate}" /></td>
													<td>${bill.billType.value}</td>
													<td>${bill.billStatus}</td>
													<td>&#x20b9; ${bill.totalBillAmount}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="imagePanel" role="tabpanel">
						<div class="card">
							<div class="card-header">
								<h5>Images and Photos</h5>
							</div>
							<div class="card-block">
								<div class="card-text">
									<div class="btn-toolbar" role="toolbar"
										aria-label="Toolbar with button groups">
										<div class="btn-group" role="group">
											<button type="button" id="addProject"
												class="btn btn-primary btn-sm">Upload Images</button>
											<button type="button" id="deleteProject"
												class="btn btn-secondary btn-sm">Remove Images</button>
										</div>
									</div>
									<br />

									<%	Map<String, List<Document>> imageMap = new LinkedHashMap<String, List<Document>>();
										Project projt = (Project)pageContext.getAttribute("proj");
										List<Document> docs = projt.getDocuments();
										Collections.sort(docs, new Comparator<Document>(){
											@Override
											public int compare(Document o1, Document o2){
												if(o1.getCreateTime().after(o2.getCreateTime())){
													return -1;
												} else if (o1.getCreateTime().before(o2.getCreateTime())){
													return 1;
												}
												return 0;
											}
										});
										for(Document doc : docs){
											if(doc.getDocumentType() == DocumentType.IMAGE){
												String date = co.seyon.util.DateUtil.getDayString(doc.getCreateTime());
												List<Document> images = imageMap.get(date);
												if(images == null){
													imageMap.put(date, new ArrayList<Document>());
													images = imageMap.get(date);
												}
												images.add(doc);	
											}
										}
										pageContext.setAttribute("projImagesMap", imageMap);
									%>
									<c:forEach items="${pageScope.projImagesMap}" var="entry">
									<fmt:parseDate value="${entry.key}" var="imageDate" pattern="yyyyMMdd" />
										<h4><fmt:formatDate type="date" dateStyle="long"
															value="${imageDate}" /></h4><hr/>
										<c:forEach items="${entry.value}" var="image">
											<img src="${image.fileLocation}" alt="${image.name}"
												class="img-thumbnail">
										</c:forEach>
										<br /> <br />
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>