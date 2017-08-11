
function show() {
			$.ajax({
				url : 'GetReportServlet',
				data : {
					ReportName : $('#ReportName').val(),
					startDate : $('#startDate').val(),
					endDate : $('#endDate').val()
				},
				success : function(responseText) {
					$('#ajaxGetUserServletResponse').html(responseText);
				}
			});
}