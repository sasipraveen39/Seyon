

$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	$('.remove-button').click(function(e){
					var parent = $(e.target).closest('.card-text');
					var deleteButton = $(parent).find('.delete-button');
					var tableHeader = $(parent).find('.delete-table>thead>tr');
					var tableRows = $(parent).find('.delete-table>tbody>tr');
					if($(deleteButton).hasClass('invisible')){
						$(deleteButton).removeClass('invisible');
						$(tableHeader).each(function(){
							$(this).prepend('<th><input type="checkbox" class="big-checkbox"></th>');
						});
						$(tableRows).each(function(){
							$(this).prepend('<td><input  type="checkbox" class="big-checkbox"></td>');
						});	
					}else {
						$(deleteButton).addClass('invisible');
						$(tableHeader).each(function(){
							$(this).children().first().remove();
						});
						$(tableRows).each(function(){
							$(this).children().first().remove();
						});
					}
					e.preventDefault();
				});
	
	$('#confirmDelete').click(function(e){
		var itemsToDelete = [];
		var eleName = $(".itemDeleteName").first().text();
		
		switch(eleName){
		case 'Image':
			$('#imagePanel').find('.img-thumbnail-delete').each(function (){
				itemsToDelete.push($(this).attr('id'));
			});
			break;
		case 'Document':
			$('#documentPanel').find('.delete-table>tbody>tr>td>input:checked').each(function (){
				var parentTR = $(this).closest('tr').attr('id');
				itemsToDelete.push(parentTR);
			});
			break;
		case 'Bill':
			$('#billPanel').find('.delete-table>tbody>tr>td>input:checked').each(function (){
				var parentTR = $(this).closest('tr').attr('id');
				itemsToDelete.push(parentTR);
			});
			break;
		case 'Drawing':
			$('#drawingPanel').find('.delete-table>tbody>tr>td>input:checked').each(function (){
				var parentTR = $(this).closest('tr').attr('id');
				itemsToDelete.push(parentTR);
			});
			break;
		case 'Project':
			$('#projectPanel').find('.delete-table>tbody>tr>td>input:checked').each(function (){
				var parentTR = $(this).closest('tr').attr('id');
				itemsToDelete.push(parentTR);
			});
			break;
		}
		
		

		if(itemsToDelete.length == 0){
			var content = '<div class="zero-selection"><br/><div class="alert alert-danger" role="alert">'
				+'<strong>Zero '+eleName+'s '
				+'selected!!</strong> Please select some '+eleName+'s and try again.</div></div>';
			$("#itemDeleteModal>.modal-dialog>.modal-content>.modal-body").append(content);
		}else{
			var item = {};
			item['type'] = eleName;
			item['ids'] = itemsToDelete;
			$.ajax({
				type : "POST",
				url : "deleteItems",
				data : JSON.stringify(item),
				dataType : 'text',
				async: false,
				contentType : 'application/json',
				timeout : 100000,
				success : function(data) {
					console.log("SUCCESS: ", data);
					if (data == "Delete successful") {
						location.reload(true);
					} else {
						itemDeleteFailed();
					}
				},
				error : function(e) {
					console.log("ERROR: ", e);
					itemDeleteFailed();
				},
				done : function(e) {
					console.log("DONE");
				}
			});
		}
		e.preventDefault();
	});	
	
});

$(document).on("change", ".delete-table>thead>tr>th>input[type='checkbox']",
		function(e) {
			var parent = $(e.target).closest(".delete-table");
			var tbodyCheckboxes = $(parent).find("tbody>tr>td>input[type='checkbox']");
			if (this.checked) {
				$(tbodyCheckboxes).each(function(){
					$(this).prop('checked', true);
				});
			} else {
				$(tbodyCheckboxes).each(function(){
					$(this).prop('checked', false);
				});
			}
		});


$(document).on("click", ".delete-button", function(e) {
	var eleName = $(this).attr('name');
	$(".itemDeleteName").html(eleName);
	$('.zero-selection').remove();
});

function itemDeleteFailed() {
	$('#itemDeleteModal').modal('hide');
	$('#itemDeleteFailedModal').modal('show');
}

function removeAllErrors(){
	$(".form-control-feedback").remove();
	$(".has-danger").removeClass("has-danger");
	$(".form-control-danger").removeClass("form-control-danger");
}

function validateFields(element) {
	removeAllErrors();
	var flag = true;
	$(element).find('input, select, textarea').each(
			function() {
				var value = $(this).val().trim();
				var parent = $(this).closest(".form-group");
				var labelText = $(parent).find(".col-form-label").html();
				var errorText = null;
				if ($(parent).hasClass("required") && (!value)) {
					errorText = labelText + " is required";
				} else {
					if (value) {
						var errorRegex = $(this).attr("error-regex");
						if(errorRegex){
							var regEx = new RegExp(errorRegex);
							if(!regEx.test(value)){
								var erTxt = $(this).attr("error-text");
								if(erTxt){
									errorText = erTxt;
								}else{
									errorText = labelText + " is invalid";
								}	
							}
						}
					}
				}
				if (errorText) {
					$(this).addClass("form-control-danger");
					var content = '<div class="form-control-feedback">'
							+ errorText + '</div>';
					parent.append(content);
					parent.addClass("has-danger");
					if (flag) {
						flag = false;
					}
				}

			});
	return flag;
}