<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="co.seyon.enums.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="sub_header.jsp" />
<title>Project - Seyon</title>
<script>
	$(document)
			.ready(
					function() {
						$('#reset').click(function(e) {
							$("form#projectSearchForm :input").each(function() {
								var input = $(this);
								if(input.prop("tagName") == "SELECT"){
									input.val("none");									
								}else{
									input.val(null);	
								}
							});
							e.preventDefault();
						});

						$('#search')
								.click(
										function(e) {
											var projectSearch = {};
											projectSearch["projectNumber"] = $(
													'#projectNumber').val();
											projectSearch["title"] = $('#title')
													.val();
											projectSearch["type"] = $('#type')
													.val();
											projectSearch["pincode"] = $(
													'#pincode').val();
											$
													.ajax({
														type : "POST",
														contentType : "application/json",
														url : "seacrhProject",
														data : JSON
																.stringify(projectSearch),
														dataType : 'json',
														timeout : 100000,
														success : function(data) {
															console
																	.log(
																			"SUCCESS: ",
																			data);
															if (data.code == "200") {
																var content = "";
																jQuery
																		.each(
																				data.result,
																				function(
																						index,
																						value) {
																					content += '<tr>';
																					content += '<th scope=\"row\"><a href=\"retrieveProject?num='
																							+ value.projectNumber
																							+ '\">'
																							+ value.projectNumber
																							+ '</a></th>';
																					content += '<td>'
																							+ value.title
																							+ '</td>';
																					content += '<td>'
																							+ value.type
																							+ '</td>';
																					content += '<td>'
																							+ value.address
																							+ '</td>';
																					content += '</tr>';
																				});
																$(
																		'#searchTable tbody')
																		.html(
																				content);
																$(
																		'#searchTable')
																		.removeClass(
																				'hidden');
																$('#zeroResult')
																		.addClass(
																				'hidden');
																$(
																		'#resultCount')
																		.html(
																				data.result.length);
															} else if (data.code == "204") {
																$(
																		'#resultCount')
																		.html(
																				'0');
																$(
																		'#searchTable')
																		.addClass(
																				'hidden');
																$('#zeroResult')
																		.removeClass(
																				'hidden');
															}
															$('#searchResult')
																	.removeClass(
																			'hidden');
														},
														error : function(e) {
															console.log(
																	"ERROR: ",
																	e);
														},
														done : function(e) {
															console.log("DONE");
														}
													});
											e.preventDefault();
										});
					});
</script>
</head>
<body>
	<div class="container-fluid page-height">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="/Seyon">Home</a></li>
			<li class="breadcrumb-item active">Project</li>
		</ol>
		<div class="row">
			<div class="col">
				<ul class="nav nav-tabs" role="tablist">
					<li class="nav-item"><a class="nav-link active"
						data-toggle="tab" href="#basicPanel" role="tab">Basic</a></li>
					<li class="nav-item"><a class="nav-link" data-toggle="tab"
						href="#searchPanel" role="tab">Project Search</a></li>
				</ul>

				<!-- Tab panes -->
				<div class="tab-content">
					<div class="tab-pane active" id="basicPanel" role="tabpanel">
						<div class="card">
							<h3 class="card-header">Recent Projects</h3>
							<div class="card-block">
								<h4 class="card-title">Recently accessed projects</h4>
								<div class="card-text">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Project #</th>
												<th>Title</th>
												<th>Type</th>
												<th>Address</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${recentProjects}" var="recentProject">
												<tr>
													<th scope="row"><a
														href="retrieveProject?num=${recentProject.projectNumber}">${recentProject.projectNumber}</a></th>
													<td>${recentProject.title}</td>
													<td>${recentProject.type}</td>
													<td>${recentProject.address}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="searchPanel" role="tabpanel">
						<div class="card">
							<h3 class="card-header">Search Projects</h3>
							<div class="card-block">
								<div class="card-text">
									<div class="container-fluid">
										<form id="projectSearchForm">
											<div class="row">
												<div class="col">
													<div class="form-group row">
														<label for="projectNumber" class="col-sm-3 col-form-label">Project
															#</label>
														<div class="col-sm-6">
															<input type="text" class="form-control"
																id="projectNumber" placeholder="PRXXXXXXXX" />
														</div>
													</div>
													<div class="form-group row">
														<label for="title" class="col-sm-3 col-form-label">Title</label>
														<div class="col-sm-6">
															<input type="text" class="form-control" id="title" />
														</div>
													</div>
												</div>
												<div class="col">
													<div class="form-group row">
														<label for="type" class="col-sm-3 col-form-label">Type</label>
														<div class="col-sm-6">
															<select class="form-control" id="type">
																<option value="none">&lt;none&gt;</option>
																<c:forEach items="<%=ProjectType.values()%>" var="entry">
																	<option value="${entry}">${entry.value}</option>
																</c:forEach>
															</select>
														</div>
													</div>

													<div class="form-group row">
														<label for="pincode" class="col-sm-3 col-form-label">Pincode</label>
														<div class="col-sm-6">
															<input type="number" class="form-control" id="pincode"
																placeholder="XXXXXX" />
														</div>
													</div>
												</div>
											</div>
										</form>
									</div>
								</div>
								<a href="#" class="btn btn-primary" id="search" role="button">Search</a>
								<a href="#" class="btn btn-secondary" id="reset" role="button">Reset</a>
							</div>
						</div>

						<div class="card hidden" id="searchResult">
							<h3 class="card-header">
								Search Result (<span id="resultCount"></span>)
							</h3>
							<div class="card-block">
								<h4 class="card-title hidden" id="zeroResult">Search
									returned zero results.</h4>
								<div class="card-text">
									<table class="table table-striped" id="searchTable">
										<thead>
											<tr>
												<th>Project #</th>
												<th>Title</th>
												<th>Type</th>
												<th>Address</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
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