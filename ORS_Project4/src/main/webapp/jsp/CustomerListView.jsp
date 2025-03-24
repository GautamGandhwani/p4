<%@page import="com.rays.pro4.controller.CustomerListCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Bean.CustomerBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>
<title>Insert title here</title>
</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.CustomerBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.CUSTOMER_LIST_CTL%>" method="post">

		<center>

			<div align="center">
				<h1>Customer List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<%
				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<CustomerBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<tr>
					<th></th>
					<td align="center"><label>Client Name</font> :
					</label> <input type="text" name="clientName"
						placeholder="Enter Client Name"
						value="<%=ServletUtility.getParameter("clientname", request)%>">
						&nbsp; <input type="submit" name="operation"
						value="<%=CustomerListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation"
						value="<%=CustomerListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr style="background: skyblue">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Client Name</th>
					<th>Location</th>
					<th>Contact No.</th>
					<th>Importance</th>
					<th>Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
							bean = (CustomerBean) it.next();
				%>


				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>
					<td><%=index++%></td>
					<td><%=bean.getClientName()%></td>
					<td><%=bean.getLocation()%></td>
					<td><%=bean.getContactNo()%></td>
					<td><%=bean.getImportance()%></td>
					<td><a href="CustomerCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>

			<table width="100%">
				<tr>
					<th></th>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=CustomerListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=CustomerListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value="<%=CustomerListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=CustomerListCtl.OP_NEW%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=CustomerListCtl.OP_NEXT%>"
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



				</tr>
			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=CustomerListCtl.OP_BACK%>"></td>
			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
	</form>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>

	</center>

	<%@include file="Footer.jsp"%>
</body>
</html>