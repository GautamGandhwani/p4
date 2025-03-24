<%@page import="com.rays.pro4.controller.PatientCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Patient Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '2000:2030',
		});
	});
</script>
</head>
<body>
<jsp:useBean id="bean" class="com.rays.pro4.Bean.PatientBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.PATIENT_CTL%>" method="post">

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Patient </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Patient </font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>
				<tr>
					<th align="left">Name <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="name" placeholder="Enter Name"
						size="26" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Mobile <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="mobile"
						placeholder="Enter Mobile" size="26"
						value="<%=DataUtility.getStringData(bean.getMobile())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("mobile", request)%></font></td>
				</tr>
				<tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">Decease <span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Heart", "Heart");
							map.put("Brain", "Brain");

							String hlist = HTMLUtility.getList("decease", String.valueOf(bean.getDecease()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("decease", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">DOV <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="dov" placeholder="Enter DOV"
						size="26" readonly="readonly" id="udatee"
						value="<%=DataUtility.getDateString(bean.getDov())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dov", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=PatientCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=PatientCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=PatientCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=PatientCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>
			</table>
		</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>