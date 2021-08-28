<%@page import="co.in.controller.CourseCtl"%>
<%@page import="co.in.controller.CollegeCtl"%>
<%@page import="co.in.util.HTMLUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.util.ServletUtility"%>
<%@page import="co.in.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
<%@include file = "Header.jsp" %>
<jsp:useBean id="bean" class="co.in.bean.CourseBean" scope="request"></jsp:useBean>

<center>
<form action="<%=ORSView.COURSE_CTL %>" method="post">

<%if(bean.getId()>0){ 
	//System.out.println(bean.getId()+"---------------------------");
%>
<h1><u>Update Course</u></h1>
<%}else{ %>
<h1><u>Add Course</u></h1>
<%} %>

<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>


<h2><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h2>


<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdby" value="<%=bean.getCreatedby()%>">
<input type="hidden" name="modifiedby" value="<%=bean.getModifiedby()%>">
<input type="hidden" name="createddatetime" value="<%=DataUtility.getTimestamp(bean.getCreateddatetime())%>"> 
<input type="hidden" name="modifieddatetime" value="<%=DataUtility.getTimestamp(bean.getModifieddatetime())%>">

<table>

<tr>
 <th>Course</th>
 <td><input type="text"  name="cname" placeholder="Enter Course Name" value="<%=DataUtility.getStringData(bean.getCname())%>"></td>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("cname",request) %></font></td>
 </tr>



<tr>
 <th>Duration</th>
 <td>
<%

LinkedHashMap map = new LinkedHashMap();

map.put("1 year","1 year" );
map.put("2 year","2 year" );
map.put("3 year","3 year" );
map.put("4 year","4 year" );
map.put("5 year","5 year" );

String htmlList = HTMLUtility.getList("duration", bean.getDuration(), map);
%>

<%=htmlList%>
</td>
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("duration",request) %></font></td>
</tr>




<tr>
 <th>Description</th>
 <td><input type="text"  name="desc" placeholder="Enter Description" value="<%=DataUtility.getStringData(bean.getDescription())%>"></td>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("desc",request) %></font></td>
 </tr>


<tr>
<th></th>
<td>
<%if (bean.getId()>0 ) {%>

&nbsp;<input type="submit" name="operation" value="<%=CourseCtl.OP_UPDATE%>">
&nbsp;<input type="submit" name="operation" value="<%=CourseCtl.OP_CANCEL%>">

<%}else{ %>

<input type="submit" name="operation" value="<%=CourseCtl.OP_SAVE%>">
&nbsp;<input type="submit" name="operation" value="<%=CourseCtl.OP_RESET%>">

<%} %>

</td>
</tr>

</table>
</form>
</center>
<%@include file = "Footer.jsp"%>
</body>
</html>