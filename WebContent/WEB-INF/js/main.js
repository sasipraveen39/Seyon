$(function () {
  $('[data-toggle="tooltip"]').tooltip();
});

$(document).ready(function(){
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
});