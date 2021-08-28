<%@page import="co.in.model.SubjectModel"%>
<%@page import="co.in.controller.StudentListCtl"%>
<%@page import="co.in.bean.SubjectBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="co.in.util.HTMLUtility"%>
<%@page import="co.in.controller.SubjectListCtl"%>
<%@page import="java.util.List"%>
<%@page import="co.in.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Subject List Page</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


<script type="text/javascript" src="<%=ORSView.APP_CONTEXT%>/js/checkbox11.js"></script>


</head>
<body>
<jsp:useBean id="bean" class= "co.in.bean.SubjectBean" scope="request"></jsp:useBean>

<%@include file = "Header.jsp" %>
<center>
<h1><u>Subject List</u></h1>
<form action="<%=ORSView.SUBJECT_LIST_CTL%>" method="post">

<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>
       
<h2><font color = "red"><%=ServletUtility.getErrorMessage(request) %></font></h2>
       

<%
List lc = (List)request.getAttribute("courselist");

List ls = (List)request.getAttribute("subjectlist");

%>

<%

List li  = ServletUtility.getList(request);
if(li==null || li.size()==0){

%>

<input type="submit" name="operation" value="<%=SubjectListCtl.OP_BACK%>">

<%}else{%>
<table width="100%">

<tr align="center">
<td><label>Course Name:</label>
<%=HTMLUtility.getList("courseid", String.valueOf(bean.getCourseid()), lc) %>

&nbsp;<label>Subject Name:</label>
<%=HTMLUtility.getList("subjectid", String.valueOf(bean.getId()), ls) %>
&nbsp;<input type="submit" name="operation" value="<%=SubjectListCtl.OP_SEARCH%>">
&nbsp;<input type="submit" name="operation" value="<%=SubjectListCtl.OP_RESET %>">
 </td>
</tr>
</table>

<br><br>

<table width="100%" border="3">

<tr><th align="left" width="7%"><input type="checkbox" id="select_all" name="Select">Select All</th>
<th>SNO</th>
<th>COURSENAME</th>
<th>SUBJECTNAME</th>
<th>DESCRIPTION</th>
<th align="center">EDIT</th>

</tr>



<%
int pageNo = ServletUtility.getpageNo(request);
int pageSize = ServletUtility.getpageSize(request);
int index = ((pageNo-1)*pageSize)+1;

List list = ServletUtility.getList(request);

Iterator it = list.iterator();

while(it.hasNext()){
	
bean = (SubjectBean)it.next();

%>
<tr>
<td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
<td align="center"><%=index++ %></td>
<td align="center"><%=bean.getCoursename() %></td>
<td align="center"><%=bean.getSubjectname() %></td>
<td align="center"><%=bean.getDescription() %></td>
<td align="center"><a href="SubjectCtl?id=<%=bean.getId()%>"><i class="fa fa-pencil" aria-hidden="true">Edit</a></i></td>

<%} %>

</tr>

</table>

<%
SubjectModel model = new SubjectModel();
%>


<table width="100%">
<tr>
<td align="left"><input type="submit" name="operation" value="<%=SubjectListCtl.OP_PREVIOUS%>" <%=(pageNo==1)?"disabled":""%>>

<%-- <%if(bean.getId()==RoleBean.admin){ %> --%>

<td align="center"><input type="submit" name="operation" value="<%=SubjectListCtl.OP_NEW%>">

<td align="center"><input type="submit" name="operation" value="<%=SubjectListCtl.OP_DELETE%>">

<%-- <%} %> --%>

<td align="right"><input type="submit" name="operation" value="<%=StudentListCtl.OP_NEXT%>"  <%=(list.size()<pageSize || model.nextpk()-1 == bean.getId())?"disabled":""%>> 

</td>
</tr>
</table>

<input type="hidden" name="pageno" value="<%=pageNo%>">
<input type="hidden" name="pagesize" value="<%=pageSize%>">

</form>
</center>

<%} %>

<%@include file = "Footer.jsp"%>


</body>
</html>