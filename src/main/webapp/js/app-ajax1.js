 
function show() {
			$.ajax({
				url : 'GetReportServlet',
				data : {
					ReportName : $('#ReportName').val(),
					credits : $('#credits').val()
				},
				success : function(responseText) {
					$('#ajaxGetUserServletResponse').html(responseText);
				}
			});
}