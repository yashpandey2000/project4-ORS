<%@page import="co.in.controller.CollegeListCtl"%>
<%@page import="co.in.model.CourseModel"%>
<%@page import="co.in.bean.CourseBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.controller.CourseListCtl"%>
<%@page import="java.util.List"%>
<%@page import="co.in.util.ServletUtility"%>
<%@page import="co.in.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course List View</title>


<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript" src="<%=ORSView.APP_CONTEXT%>/js/checkbox11.js"></script>
    
</head>
<body>
<center>
<form action ="<%=ORSView.COURSE_LIST_CTL%>" method = "post">
<%@include file = "Header.jsp" %>
<jsp:useBean id="bean" class="co.in.bean.CourseBean" scope="request"></jsp:useBean>

<h1><u>Course List</u></h1>


<h2><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h2>


<h2><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h2>


<%

List l  = ServletUtility.getList(request);

if(l==null || l.size()==0){
%>

<input type="submit" name="operation" value="<%=CourseListCtl.OP_BACK%>">

<%}else{ %>


<table  align="center">
<tr align="center">
<td>
<label>Course:</label>
<input type="text" name="cname" placeholder="Enter Course Name" value="<%=DataUtility.getStringData(bean.getCname())%>">  
 


&nbsp;<input type="submit" name ="operation" value="<%=CollegeListCtl.OP_SEARCH%>">
&nbsp;<input type="submit" name ="operation" value="<%=CollegeListCtl.OP_RESET%>">

</tr>
</td>
</table>

<br>
<br>

<table width="100%" border="3">
<tr><th width="7%" align="left"><input type="checkbox" id="select_all" name="Select">Select All</th>
<th >SNO</th>
<th>COURSENAME</th>
<th>DURATION</th>
<th>DESCRIPTION</th>
<th>EDIT</th>
</tr>


<tr>
<%
int pageNo = ServletUtility.getpageNo(request);
int pageSize = ServletUtility.getpageSize(request);
int index = ((pageNo-1)*pageSize)+1;

List list = ServletUtility.getList(request);

Iterator it = list.iterator();

while(it.hasNext()){
	
bean = (CourseBean)it.next();

%>

<td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
<td align="center"><%=index++%></td>
<td align="center"><%=bean.getCname()%></td>
<td align="center"><%=bean.getDuration()%></td>
<td align="center"><%=bean.getDescription()%></td>
<td align="center"><a href="CourseCtl?id=<%=bean.getId()%>"><i class="fa fa-pencil" aria-hidden="true">Edit</a></i></td>

</tr>

<% } %>
</table>

<%

CourseModel model = new CourseModel();


%>

<table width="100%">
<tr>
<td align = "left"><input type="submit" name="operation" value="<%=CourseListCtl.OP_PREVIOUS%>"<%=(pageNo>1)?"":"disabled" %>></td>
<td align="center"><input type="submit" name="operation" value="<%=CourseListCtl.OP_NEW%>"></td>
<td align="center"><input type="submit" name="operation" value="<%=CourseListCtl.OP_DELETE%>"></td>
<td align="right"><input type="submit" name="operation" value="<%=CourseListCtl.OP_NEXT%>" <%=((list.size()<pageSize) || model.nextpk()-1==bean.getId())?"disabled":""%>></td>
</tr>

</table>


<input type="hidden" name="pageno" value="<%=pageNo%>">
<input type="hidden" name="pagesize" value="<%=pageSize%>">

</form>
<% } %>
</center>
<%@include file = "Footer.jsp"%>
</body>
</html>