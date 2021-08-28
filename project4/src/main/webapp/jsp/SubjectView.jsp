<%@page import="co.in.controller.StudentCtl"%>
<%@page import="co.in.controller.UserCtl"%>
<%@page import="co.in.controller.SubjectCtl"%>
<%@page import="co.in.util.HTMLUtility"%>
<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="co.in.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Subject View</title>

</head>
<body>
<%@include file = "Header.jsp" %>
<center>


<jsp:useBean id="bean" class="co.in.bean.SubjectBean" scope="request"></jsp:useBean>

<%

List l = (List)request.getAttribute("courselist");

%>

<%if(bean.getId()>0){ %>
<h1><u>Update Subject</u></h1>
<%}else{%>
<h1><u>Add Subject</u></h1>
<%} %>


<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>

<h2><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h2>

<form action="<%=ORSView.SUBJECT_CTL%>" method="post">

<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdby" value="<%=bean.getCreatedby()%>">
<input type="hidden" name="modifiedby" value="<%=bean.getModifiedby()%>">
<input type="hidden" name="createddatetime" value="<%=DataUtility.getTimestamp(bean.getCreateddatetime())%>"> 
<input type="hidden" name="modifieddatetime" value="<%=DataUtility.getTimestamp(bean.getModifieddatetime())%>">

<table>


<tr>
 <th>Course Name</th>
 <td><%=HTMLUtility.getList("courseid", String.valueOf(bean.getCourseid()),l) %></td>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("courseid",request) %></font></td>
 </tr>


<tr>
 <th>Subject Name</th>
 <td><input type="text"  name="subjectname" placeholder="Enter Subject Name" value="<%=DataUtility.getStringData(bean.getSubjectname())%>"></td>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("subjectname",request) %></font></td>
 </tr>



<tr>
 <th>Description</th>
 <td><input type="text"  name="desc" placeholder="Enter Description" value="<%=DataUtility.getStringData(bean.getDescription())%>"></td>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("desc",request) %></font></td>
 </tr>



<tr>
<th></th>
<td>

<br>

<%
if(bean.getId()>0){
%>
&nbsp;<input type="submit" name="operation" value="<%=StudentCtl.OP_UPDATE%>">
&nbsp;<input type="submit" name="operation" value="<%=StudentCtl.OP_CANCEL%>">

<%}else{ %>

<input type="submit" name="operation" value="<%=StudentCtl.OP_SAVE%>">
&nbsp;<input type="submit" name="operation" value="<%=UserCtl.OP_RESET%>">

<%} %>
</td>
</tr>

</table>
</form>
</center>
<%@include file = "Footer.jsp"%>
</body>
</html>