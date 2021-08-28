<%@page import="co.in.controller.MarksheetMeritListCtl"%>
<%@page import="co.in.bean.MarksheetBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="co.in.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet Merit List View</title>

</head>
<%@include file="Header.jsp"%>
<body>
	<center>
	
	<h1><u>MarksheetMeritList</u></h1>

	<h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h2>


	<form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="post">

			<table border="3" width="100%">

				<tr>
					<th>SNO</th>
					<th>Roll No</th>
					<th>Name</th>
					<th>Physics</th>
					<th>Chemistry</th>
					<th>Maths</th>
					<th>Total</th>
					<th>Percentage(%)</th>
				</tr>

<%

int pageNo = (int) ServletUtility.getpageNo(request);

int pageSize = (int) ServletUtility.getpageSize(request);

int index = ((pageNo-1)*pageSize)+1;

List list = ServletUtility.getList(request);
Iterator it = list.iterator();

while(it.hasNext()){
	
	MarksheetBean bean = new MarksheetBean();
	bean=(MarksheetBean) it.next();
	
	
	long id = bean.getId();
	String roll = bean.getRollno();
	String name = bean.getName();
	int phy = bean.getPhysics();
	int chem = bean.getChemistry();
	int math = bean.getMaths();

%>

<tr>
<td align="center"><%=index++%></td>
<td align="center"><%=roll%></td>
<td align="center"><%=name%></td>
<td align="center"><%=phy%></td>
<td align="center"><%=chem%></td>
<td align="center"><%=math %></td>
<td align="center"><%=(phy+chem+math) %></td>
<td align="center"><%=(phy+chem+math)/3 %>%</td>
</tr>

<%
} 
%>

</table>

<table>
<tr>
<td><input type="submit" name="operation" value="<%=MarksheetMeritListCtl.OP_BACK%>"></td>
</tr>
</table>

<input type ="hidden" name ="pageno" value="<%=pageNo%>">
<input type ="hidden" name ="pagesize" value="<%=pageSize%>"> 


</form>
</center>
<%@include file ="Footer.jsp" %>
</body>
</html>