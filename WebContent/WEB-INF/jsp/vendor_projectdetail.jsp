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
<script>
	$(document).ready(
			function() {
				$('.img-thumbnail').click(
						function(e) {
							var elementID = $(e.target).attr('id');
							if(!$("#deleteImage").hasClass('invisible')){
								if($(e.target).hasClass("img-thumbnail-delete")){
									$(e.target).removeClass("img-thumbnail-delete");
								}else{
									$(e.target).addClass("img-thumbnail-delete");	
								}
							}else{
								$("#imageSlideControls>.carousel-inner>div.active")
									.removeClass("active");
								$("#CR" + elementID).addClass("active");
								$('#imageSlideModal').modal('show');
							}
						});
				
				$('#removeImage').click(function(e){
					if(!$(e.target).hasClass('active')){
						$('#deleteImage').removeClass('invisible');
					}else{
						$(".img-thumbnail-delete").removeClass("img-thumbnail-delete");
						$('#deleteImage').addClass('invisible');
					}
				});

				$('#uploadImage').click(function(e) {
					var progressBarDiv = $("#imageUploadModal>.modal-dialog>.modal-content>.modal-body>.container-fluid>.row>.col");
					
					var imageInfo = {};
					imageInfo["name"] = $('#title').val();
					imageInfo["description"] = $('#caption').val();
					imageInfo["projectNumber"] = "${proj.projectNumber}";
					imageInfo["accountNumber"] = "${proj.user.accountNumber}";
					var file = $('#imageFile').prop("files")[0];

					var formData = new FormData();
					formData.append("imageFile", file);
					formData.append("imageInfo", JSON.stringify(imageInfo));

					$.ajax({
						xhr: function(){
						    var xhr = new window.XMLHttpRequest();
						    xhr.upload.addEventListener("progress", function(evt){
						      if (evt.lengthComputable) {
						        var percentComplete = evt.loaded / evt.total * 100;
						        $(".progress-bar").width(percentComplete+"%");
						      }
						    }, false);
						    return xhr;
						  },
						type : "POST",
						url : "uploadImage",
						data : formData,
						dataType : 'text',
						async: true,
						processData : false,
						contentType : false,
						timeout : 100000,
						beforeSend : function(){
							progressBarDiv.append($("#fileUploadProgressTemplate").tmpl());
							$("#uploadImage").prop("disabled", true);
					        $("#imageUploadCancel").prop("disabled", true);
					        $("#imageUplodForm :input").attr("disabled", true);
						},
						success : function(data) {
							console.log("SUCCESS: ", data);
							if (data == "Image uploaded") {
								location.reload(true);
							} else {
								imageUploadFailed();
							}
						},
						error : function(e) {
							console.log("ERROR: ", e);
							imageUploadFailed();
						},
						complete : function(e) {
							console.log("DONE");
							$(".progress").remove();
							$("#uploadImage").prop("disabled", false);
					        $("#imageUploadCancel").prop("disabled", false);
					        $("#imageUplodForm :input").attr("disabled", false);
						}
					});
					e.preventDefault();
				});
			});

	function imageUploadFailed() {
		$('#imageUploadModal').modal('hide');
		$('#imageUploadFailedModal').modal('show');
	}
</script>
</head>
<body>
	<!-- Image Upload Modal -->
	<div class="modal fade" id="imageUploadModal" tabindex="-1"
		role="dialog" aria-labelledby="imageUploadModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="imageUploadModalLabel">Image
						Upload - Seyon</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<div class="col">
								<form id="imageUplodForm">
									<div class="form-group row">
										<label for="title" class="col-sm-3 col-form-label">Title</label>
										<div class="col-sm-9">
											<input type="text" class="form-control" id="title"
												placeholder="" />
										</div>
									</div>
									<div class="form-group row">
										<label for="caption" class="col-sm-3 col-form-label">Caption</label>
										<div class="col-sm-9">
											<textarea class="form-control" rows="3" id="caption"></textarea>
										</div>
									</div>
									<div class="form-group row">
										<label for="imageFile" class="col-sm-3 col-form-label">Image</label>
										<div class="col-sm-9">
											<input type="file" class="form-control-file" id="imageFile"
												accept="image/x-png,image/gif,image/jpeg"
												aria-describedby="fileHelp"> <small id="fileHelp"
												class="form-text text-muted">Upload only jpeg, png
												or gif file.</small>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						id="imageUploadCancel" data-dismiss="modal">Cancel</button>
					<button href="#" id="uploadImage" class="btn btn-primary">Upload</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Image Upload Failed Modal -->
	<div class="modal fade" id="imageUploadFailedModal" tabindex="-1"
		role="dialog" aria-labelledby="imageUploadFailedModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="imageUploadFailedModalLabel">Image
						Upload Failed - Seyon</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="alert alert-danger" role="alert">
						<strong>Image upload Failed!!</strong> Please try again later.
					</div>
				</div>
				<div class="modal-footer">
					<a href="#" data-dismiss="modal" class="btn btn-primary"
						role="button">OK</a>
				</div>
			</div>
		</div>
	</div>

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

	<!-- Image Slide Modal -->
	<div class="modal fade" id="imageSlideModal" tabindex="-1"
		role="dialog" aria-labelledby="imageSlideModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="imageSlideModalLabel">Image
						Slideshow - Seyon</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body"
					style="background-color: var(--header-color);">
					<div class="container-fluid">
						<div class="row">
							<div class="col">
								<div id="imageSlideControls" class="carousel slide"
									data-ride="carousel">
									<div class="carousel-inner" role="listbox">
										<c:forEach items="${pageScope.projImagesMap}" var="entry">
											<c:forEach items="${entry.value}" var="image">
												<div class="carousel-item" id="CR${image.iddocument}">
													<img src="${image.fileLocation}" alt="${image.name}"
														class="d-block img-fluid" />
													<div class="carousel-caption d-none d-md-block">
															<h3>${image.name}</h3>
															<p class="font-italic">${image.description}</p>
													</div>
												</div>
											</c:forEach>
										</c:forEach>
									</div>
									<a class="carousel-control-prev carousel-control-bg"
										href="#imageSlideControls" role="button" data-slide="prev">
										<span class="carousel-control-prev-icon" aria-hidden="true"></span>
										<span class="sr-only">Previous</span>
									</a> <a class="carousel-control-next carousel-control-bg"
										href="#imageSlideControls" role="button" data-slide="next">
										<span class="carousel-control-next-icon" aria-hidden="true"></span>
										<span class="sr-only">Next</span>
									</a>
								</div>
							</div>
						</div>
					</div>
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
									<div class="btn-toolbar justify-content-between" role="toolbar"
										aria-label="Toolbar with button groups">
										<div class="btn-group" role="group">
											<button type="button" id="addDocument" onclick="window.location.href='newLegalDocument?num=${proj.projectNumber}'"
												class="btn btn-primary btn-sm">Add Document</button>
											<button type="button" id="deleteDocument" data-toggle="button" aria-pressed="false" autocomplete="off"
												class="btn btn-secondary btn-sm remove-button">Remove Document</button>
										</div>
										<div class="btn-group" role="group">
											<button type="button" data-toggle="modal"
												data-target="#itemDeleteModal" name="Document"
												class="btn btn-danger btn-sm delete-button invisible">Delete Selected Documents</button>
										</div>
									</div>
									<br />
									<table class="table table-striped delete-table">
										<thead>
											<tr>
												<th>Name</th>
												<th>Description</th>
												<th>Document Link</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${proj.documents}" var="document">
												<c:if test="${document.documentType == 'CONTRACT'}">
													<tr id="${document.iddocument}">
														<th scope="row">${document.name}</th>
														<td>${document.description}</td>
														<td><a target="_blank"
															href="${document.fileLocation}"
															class="btn btn-primary btn-sm" role="button">Open
																Document</a></td>
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
									<div class="btn-toolbar justify-content-between" role="toolbar"
										aria-label="Toolbar with button groups">
										<div class="btn-group" role="group">
											<button type="button" id="addDrawing" onclick="window.location.href='newDrawing?num=${proj.projectNumber}'"
												class="btn btn-primary btn-sm">Add Drawing</button>
											<button type="button" id="deleteDrawing" data-toggle="button" aria-pressed="false" autocomplete="off"
												class="btn btn-secondary btn-sm remove-button">Remove Drawing</button>
										</div>
										<div class="btn-group" role="group">
											<button type="button" data-toggle="modal"
												data-target="#itemDeleteModal" name="Drawing"
												class="btn btn-danger btn-sm delete-button invisible">Delete Selected Drawings</button>
										</div>
									</div>
									<br />
									<table class="table table-striped delete-table">
										<thead>
											<tr>
												<th>Drawing #</th>
												<th>Type</th>
												<th>Status</th>
												<th>Est. Date of Issue</th>
												<th>Date of Issue</th>
												<th>Document Link</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${proj.drawings}" var="drawing">
												<tr id="${drawing.iddrawing}">
													<th scope="row">${drawing.drawingNumber}</th>
													<td>${drawing.typeOfDrawing.value}</td>
													<td>${drawing.status.value}</td>
													<td><fmt:formatDate type="date" dateStyle="long"
															value="${drawing.estimatedDateOfIssue}" /></td>
													<td><fmt:formatDate type="date" dateStyle="long"
															value="${drawing.dateOfIssue}" /></td>
													<td><a target="_blank" href="${drawing.document.fileLocation}" class="btn btn-primary btn-sm"
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
									<div class="btn-toolbar justify-content-between" role="toolbar"
										aria-label="Toolbar with button groups">
										<div class="btn-group" role="group">
											<button type="button" id="addBill" onclick="window.location.href='newBill?num=${proj.projectNumber}'"
												class="btn btn-primary btn-sm">Add Bill</button>
											<button type="button" id="deleteBill"
												class="btn btn-secondary btn-sm remove-button">Remove
												Bill</button>
										</div>
										<div class="btn-group" role="group">
											<button type="button" data-toggle="modal"
												data-target="#itemDeleteModal" name="Bill"
												class="btn btn-danger btn-sm delete-button invisible">Delete Selected Bills</button>
										</div>
									</div>
									<br />
									<table class="table table-striped delete-table">
										<thead>
											<tr>
												<th>Bill #</th>
												<th>Date</th>
												<th>Type</th>
												<th>Status</th>
												<th>Total Amount</th>
												<th>Document Link</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${proj.bills}" var="bill">
												<tr id="${bill.idbill}">
													<th scope="row">${bill.billNumber}</th>
													<td><fmt:formatDate type="date" dateStyle="long"
															value="${bill.billDate}" /></td>
													<td>${bill.billType.value}</td>
													<td>${bill.billStatus.value}</td>
													<td>&#x20b9; ${bill.totalBillAmount}</td>
													<td><a target="_blank" href="${bill.document.fileLocation}" class="btn btn-primary btn-sm"
														role="button">Open Drawing</a></td>
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
									<div class="btn-toolbar justify-content-between" role="toolbar"
										aria-label="Toolbar with button groups">
										<div class="btn-group" role="group">
											<button type="button" id="addImage" data-toggle="modal"
												data-target="#imageUploadModal"
												class="btn btn-primary btn-sm">Upload Images</button>
											<button type="button" id="removeImage" data-toggle="button" aria-pressed="false" autocomplete="off"
												class="btn btn-secondary btn-sm">Remove Images</button>
										</div>
										<div class="btn-group" role="group">
											<button type="button" id="deleteImage" data-toggle="modal"
												data-target="#itemDeleteModal" name="Image"
												class="btn btn-danger delete-button btn-sm invisible">Delete Selected Images</button>
										</div>
									</div>
									<br />


									<c:forEach items="${pageScope.projImagesMap}" var="entry">
										<fmt:parseDate value="${entry.key}" var="imageDate"
											pattern="yyyyMMdd" />
										<h4>
											<fmt:formatDate type="date" dateStyle="long"
												value="${imageDate}" />
										</h4>
										<hr />
										<c:forEach items="${entry.value}" var="image">
											<img src="${image.fileLocation}" alt="${image.name}"
												id="${image.iddocument}" class="img-thumbnail">
										</c:forEach>
										<br />
										<br />
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