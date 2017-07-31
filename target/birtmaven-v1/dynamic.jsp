<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>dynamic table</title>

</head>
<body>

	<form id="form1" method="get" action="http://localhost:8080/birtmaven/run">
			<select name="dyna1" multiple size="8" >
				<option value="COUNTRY">COUNTRY</option>
				<option value="CITY">CITY</option>
				<option value="STATE">STATE</option>
				<option value="OFFICECODE">OFFICECODE</option>	
				<option value="ADDRESSLINE1">ADDRESSLINE1</option>	
				<option value="ADDRESSLINE2">ADDRESSLINE2</option>
				<option value="POSTALCODE">POSTALCODE</option>	
				<option value="TERRITORY">TERRITORY</option>			
			</select>

			<INPUT TYPE="SUBMIT" VALUE="Run Dynamic Report">
			<INPUT TYPE="HIDDEN" NAME="ReportName" Value="DynamicTableExample.rpttemplate">
		</form>
	
</body>
</html>