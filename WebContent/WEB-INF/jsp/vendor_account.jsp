<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="sub_header.jsp" />
<title>Account - Seyon</title>
<script>
	$(document).ready(function() {
		$('#reset').click(function(e) {
			$("form#accountSearchForm :input").each(function() {
				var input = $(this);
				input.val(null);
			});
			e.preventDefault();
		});
		
		$('#search').click(function(e){
			var accountSearch = {};
			accountSearch["accountNumber"] = $('#accountNumber').val();
			accountSearch["accountName"] = $('#accountName').val();
			accountSearch["mobileNumber"] = $('#mobileNumber').val();
			accountSearch["email"] = $('#email').val();
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "seacrhAccount",
				data : JSON
						.stringify(accountSearch),
				dataType : 'json',
				timeout : 100000,
				success : function(data) {
					console.log("SUCCESS: ",data);
					if (data.code == "200") {
						var content ="";
						jQuery.each(data.result,function(index,value) {
											content += '<tr>';
											content += '<th scope=\"row\"><a href=\"retrieveAccount?num='+value.accountNumber+'\">'
													+ value.accountNumber+ '</a></th>';
											content += '<td>'
													+ value.accountName
													+ '</td>';
											content += '<td>'
													+ value.mobileNumber
													+ '</td>';
											content += '<td>'
													+ value.email
													+ '</td>';
											content += '</tr>';
										});
						$('#searchTable tbody').html(content);
						$('#searchTable').removeClass('hidden');
						$('#zeroResult').addClass('hidden');
						$('#resultCount').html(data.result.length);
					}else if(data.code == "204"){
						$('#resultCount').html('0');
						$('#searchTable').addClass('hidden');
						$('#zeroResult').removeClass('hidden');
					}
					$('#searchResult').removeClass('hidden');
				},
				error : function(e) {
					console.log("ERROR: ",e);
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
			<li class="breadcrumb-item active">Account</li>
		</ol>
		<div class="row">
			<div class="col">
				<ul class="nav nav-tabs" role="tablist">
					<li class="nav-item"><a class="nav-link active"
						data-toggle="tab" href="#basicPanel" role="tab">Basic</a></li>
					<li class="nav-item"><a class="nav-link" data-toggle="tab"
						href="#searchPanel" role="tab">Account Search</a></li>
				</ul>

				<!-- Tab panes -->
				<div class="tab-content">
					<div class="tab-pane active" id="basicPanel" role="tabpanel">
						<div class="card">
							<h3 class="card-header">Recent Accounts</h3>
							<div class="card-block">
								<h4 class="card-title">Recently accessed accounts</h4>
								<div class="card-text">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Account #</th>
												<th>Account Name</th>
												<th>Mobile Number</th>
												<th>Email</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<th scope="row">1</th>
												<td>Mark</td>
												<td>Otto</td>
												<td>@mdo</td>
											</tr>
											<tr>
												<th scope="row">2</th>
												<td>Jacob</td>
												<td>Thornton</td>
												<td>@fat</td>
											</tr>
											<tr>
												<th scope="row">3</th>
												<td>Larry</td>
												<td>the Bird</td>
												<td>@twitter</td>
											</tr>
										</tbody>
									</table>
								</div>

								<a href="#" class="btn btn-primary" role="button">Create New
									Account</a>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="searchPanel" role="tabpanel">
						<div class="card">
							<h3 class="card-header">Search Accounts</h3>
							<div class="card-block">
								<div class="card-text">
									<div class="container-fluid">
										<form id="accountSearchForm">
											<div class="row">
												<div class="col">
													<div class="form-group row">
														<label for="accountNumber" class="col-sm-3 col-form-label">Account
															#</label>
														<div class="col-sm-6">
															<input type="text" class="form-control"
																id="accountNumber" placeholder="ACXXXXXXXX" />
														</div>
													</div>
													<div class="form-group row">
														<label for="accountName" class="col-sm-3 col-form-label">Account
															Name</label>
														<div class="col-sm-6">
															<input type="text" class="form-control" id="accountName" />
														</div>
													</div>
												</div>
												<div class="col">
													<div class="form-group row">
														<label for="mobileNumber" class="col-sm-3 col-form-label">Mobile
															Number</label>
														<div class="col-sm-6">
															<input type="text" class="form-control" id="mobileNumber"
																placeholder="XXXXXXXXX" />
														</div>
													</div>

													<div class="form-group row">
														<label for="email" class="col-sm-3 col-form-label">Email</label>
														<div class="col-sm-6">
															<input type="email" class="form-control" id="email"
																placeholder="xxxxxxx@xxx.xxx" />
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
							<h3 class="card-header">Search Result (<span id="resultCount"></span>)</h3>
							<div class="card-block">
							<h4 class="card-title hidden" id="zeroResult">Search returned zero results.</h4>
								<div class="card-text">
									<table class="table table-striped" id="searchTable">
										<thead>
											<tr>
												<th>Account #</th>
												<th>Account Name</th>
												<th>Mobile Number</th>
												<th>Email</th>
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