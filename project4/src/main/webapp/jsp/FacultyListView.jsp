<%@page import="co.in.model.FacultyModel"%>
<%@page import="co.in.bean.FacultyBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="co.in.controller.FacultyListCtl"%>
<%@page import="java.util.List"%>
<%@page import="co.in.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty List View</title>


<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 

<script type="text/javascript" src="<%=ORSView.APP_CONTEXT%>/js/checkbox11.js"></script>


      

</head>
<body>
<%@include file="Header.jsp"%>
<center>
<h1><u>Faculty List</u></h1>
<jsp:useBean id="bean" class = "co.in.bean.FacultyBean" scope="request"></jsp:useBean>

<h2><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h2>

<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>

<form action="<%=ORSView.FACULTY_LIST_CTL%>" method="post">

<% List  l =ServletUtility.getList(request);

if(l==null || l.size()==0){%>
	
<input type="submit" name="operation" value="<%=FacultyListCtl.OP_BACK%>">

<%}else{%>

<table width="100%">

<tr align="center">
<td>
<label>FirstName:</label>
<input type="text" name="fname" placeholder="Enter First Name"  value="<%=ServletUtility.getParameter("fname", request)%>">
&nbsp;<label>LastName:</label>
<input type="text" name="lname" placeholder="Enter Last Name" value="<%=ServletUtility.getParameter("lname", request)%>">
&nbsp;<label>LoginId:</label>
<input type="text" name="loginid" placeholder="Enter Emailid" value="<%=ServletUtility.getParameter("loginid", request)%>">
&nbsp;
<input type="submit" name="operation" value="<%=FacultyListCtl.OP_SEARCH%>">
&nbsp;
<input type="submit" name="operation" value="<%=FacultyListCtl.OP_RESET%>">
</td>
</tr>
</table>

<br><br>

<table width="100%" border="3">
<tr>
<th align="left"><input type="checkbox" id="select_all" name="Select">Select All</th>
<th>SNO</th>
<th>FIRST_NAME</th>
<th>LAST_NAME</th>
<th>LOGIN_ID</th>
<th>DATE_OF_JOINING</th>
<th>QUALIFICATION</th>
<th>MOBILE NO</th>
<th>COLLEGE NAME</th>
<th>COURSE NAME</th>
<th>SUBJECT NAME</th>
<th>EDIT</th>
</tr>

<tr>
<%
int pageNo  = ServletUtility.getpageNo(request);
int pageSize = ServletUtility.getpageSize(request);
int index = ((pageNo-1)*pageSize)+1;

List list = ServletUtility.getList(request);

Iterator it = list.iterator();

while(it.hasNext()){
bean = (FacultyBean)it.next();
%>

<td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
<td align="center"><%=index++%></td>
<td align="center"><%=bean.getFirstname()%></td>
<td align="center"><%=bean.getLastname()%></td>
<td align="center"><%=bean.getLoginid()%></td>
<td align="center"><%=bean.getDateofjoining()%></td>
<td align="center"><%=bean.getQualification()%></td>
<td align="center"><%=bean.getMobileno()%></td>
<td align="center"><%=bean.getCollegename()%></td>
<td align="center"><%=bean.getCoursename()%></td>
<td align="center"><%=bean.getSubjectname()%></td>
<td align="center"><a href="FacultyCtl?id=<%=bean.getId()%>"><i class="fa fa-pencil" aria-hidden="true">Edit</a></i></td>
</tr>

<%}%>
</table>
<%FacultyModel model = new FacultyModel(); %>

<table width="100%">
<tr>
<td align="left"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>></td>
<td align="center"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEW%>"></td>
<td align="center"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_DELETE%>"></td>
<td align="right"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEXT%>" <%=((list.size()<pageSize || model.nextpk()-1==bean.getId())?"disabled":"")%>></td>
</tr>
</table>

<input type="hidden" name = "pageNo" value="<%=pageNo%>">
  <input type="hidden" name = "pageSize" value="<%=pageSize%>">

</form>
</center>
<%} %>
<%@include file="Footer.jsp"%>
</body>
</html>