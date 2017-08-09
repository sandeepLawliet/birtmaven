 
function show() {
			$.ajax({
				url : 'GetReportServlet',
				data : {
					ReportName : $('#ReportName').val(),
					Cust : $('#Cust').val()
				},
				success : function(responseText) {
					$('#ajaxGetUserServletResponse').html(responseText);
				}
			});
}