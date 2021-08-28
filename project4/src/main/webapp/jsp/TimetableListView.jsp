<%@page import="co.in.model.TimeTableModel"%>
<%@page import="co.in.bean.TimeTableBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.util.HTMLUtility"%>
<%@page import="co.in.controller.TimetableListCtl"%>
<%@page import="java.util.List"%>
<%@page import="co.in.util.ServletUtility"%>
<%@page import="co.in.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>



<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
   <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> 
  
<script type="text/javascript" src="<%=ORSView.APP_CONTEXT%>/js/checkbox11.js"></script>

 <script>
  $( function() {
    $( "#datepicker" ).datepicker({
    	 changeMonth : true,
    		changeYear : true,
    		yearRange:'1980:2021',
    		dateFormat: 'dd/mm/yy'
        });
   
  } );
  </script> 

</head>
<body>
<%@include file = "Header.jsp" %>
<jsp:useBean id="bean" class= "co.in.bean.TimeTableBean" scope="request"></jsp:useBean>
<form action="<%=ORSView.TIMETABLE_LIST_CTL %>" method="post">

<center>
<h1><u>Timetable List</u></h1>

<h2><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h2>
<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>

<%

List cl = (List)request.getAttribute("courselist");

List sl = (List)request.getAttribute("subjectlist");

%>


<%

List list = ServletUtility.getList(request);
if(list==null || list.size()==0){
%>

<input type="submit" name="operation" value="<%=TimetableListCtl.OP_BACK%>">

<%}else{%>

<table width="100%">
<tr align="center">
<td><label>Course Name:</label>
<%=HTMLUtility.getList("courseid",String.valueOf(bean.getCourseid()),cl) %>
&nbsp;<label>Subject Name:</label>
<%=HTMLUtility.getList("subjectid", String.valueOf(bean.getSubjectid()), sl) %>
&nbsp;<label>Exam Date:</label>
<input type="text" name="examdate" placeholder="dd/mm/yyyy" id="datepicker" value="<%=DataUtility.getDateString(bean.getExamdate())%>">
&nbsp;<label>Exam Time:</label>
<%
HashMap map1 = new HashMap();
map1.put("8 to 10 AM", "8 to 10 AM");
map1.put("12 to 2 PM", "12 to 2 PM");
map1.put("3 to 5 PM", "3 to 5 PM");

String htmllist1 = HTMLUtility.getList("examtime", bean.getExamtime(), map1);
%>
<%=htmllist1%>
&nbsp;<input type="submit" name="operation" value="<%=TimetableListCtl.OP_SEARCH%>">
&nbsp;<input type="submit" name="operation" value="<%=TimetableListCtl.OP_RESET%>">
</td>
</tr>
</table>

<br>
<br>

<table width="100%" border="3">

<tr>
<th align="left" width="7%"><input type="checkbox" id="select_all" name="Select">Select All</th>
    <th>SNO</th>
    <th>COURSE NAME</th>
    <th>SUBJECT NAME</th>
    <th>SEMESTER</th>
    <th>EXAM DATE</th>
    <th>EXAM TIME</th>
    <th>EDIT</th>

</tr>

<tr>
<%
int pageNo = ServletUtility.getpageNo(request);
int pageSize = ServletUtility.getpageSize(request);
int index = ((pageNo-1)*pageSize)+1;

Iterator it = list.iterator();
while(it.hasNext()){
bean = (TimeTableBean)it.next();


%>

<td align="left"><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
<td align="center"><%=index++ %></td>
<td align="center"><%=bean.getCoursename() %></td>
<td align="center"><%=bean.getSubjectname() %></td>
<td align="center"><%=bean.getSemester() %></td>
<td align="center"><%=bean.getExamdate() %></td>
<td align="center"><%=bean.getExamtime() %></td>
<td align="center"><a href="TimetableCtl?id=<%=bean.getId()%>"><i class="fa fa-pencil" aria-hidden="true">Edit</a></i></td>
</tr>
<%} %>

</table>

<%TimeTableModel model = new TimeTableModel(); %>

<table width="100%">
<tr>
<td align="left"><input type="submit" name="operation" value="<%=TimetableListCtl.OP_PREVIOUS%>" <%=(pageNo==1)?"disabled":"" %>></td>
<td align="center"><input type="submit" name="operation" value="<%=TimetableListCtl.OP_NEW%>"></td>
<td align="center"><input type="submit" name="operation" value="<%=TimetableListCtl.OP_DELETE%>"></td>
<td align="right"><input type="submit" name="operation" value="<%=TimetableListCtl.OP_NEXT%>"<%=(list.size()<pageSize || model.nextpk()-1 == bean.getId())?"disabled":"" %>></td>
</tr>
</table>
</center>
<input type ="hidden" name="pageNo" value="<%=pageNo%>">
<input type ="hidden" name="pageSize" value="<%=pageSize%>">
<%}%>
</form>
<%@include file = "Footer.jsp"%>
</body>
</html>