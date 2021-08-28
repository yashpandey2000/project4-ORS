<%@page import="co.in.controller.TimetableCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="co.in.util.HTMLUtility"%>
<%@page import="co.in.util.DataUtility"%>
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
<center>
<jsp:useBean id="bean" class = "co.in.bean.TimeTableBean" scope="request"></jsp:useBean>

<%if(bean.getId()>0) {%>
<h1><u>Update Timetable</u></h1>
<%}else{ %>
<h1><u>Add Timetable</u></h1>
<%} %>

<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>

<h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h2>

<form action="<%=ORSView.TIMETABLE_CTL%>" method="post">

<%

List cl = (List)request.getAttribute("courselist");
List sl = (List)request.getAttribute("subjectlist");

%>

<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdby" value="<%=bean.getCreatedby()%>">
<input type="hidden" name="modifiedby" value="<%=bean.getModifiedby()%>">
<input type="hidden"  name="createddatetime" value="<%=DataUtility.getTimestamp(bean.getCreateddatetime()) %>"> 
<input type="hidden"  name="modifieddatetime" value="<%=DataUtility.getTimestamp(bean.getModifieddatetime()) %>">
     
       
<table>
 <tr>
<th>Course Name</th>
<td><%=HTMLUtility.getList("courseid",String.valueOf(bean.getCourseid()),cl) %></td>
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("coursename",request)%></font></td>
</tr> 

 <tr>
<th>Subject Name</th>
<td><%=HTMLUtility.getList("subjectid",String.valueOf(bean.getSubjectid()), sl)%></td>
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("subjectname",request)%></font></td>
</tr>

 
<tr>
<th>Semester</th>
<td>
<%
LinkedHashMap map = new LinkedHashMap();
map.put("1st", "1st");
map.put("2nd", "2nd");
map.put("3rd", "3rd");
map.put("4th", "4th");
map.put("5th", "5th");
map.put("6th", "6th");
map.put("7th", "7th");
map.put("8th", "8th");

String htmllist = HTMLUtility.getList("sem", bean.getSemester(), map);

%>
<%=htmllist %>
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("sem", request) %></font></td>
</tr>

<tr>
<th>Exam Time</th>
<td>
<%
HashMap map1 = new HashMap();
map1.put("8 to 10 AM", "8 to 10 AM");
map1.put("12 to 2 PM", "12 to 2 PM");
map1.put("3 to 5 PM", "3 to 5 PM");

String htmllist1 = HTMLUtility.getList("examtime", bean.getExamtime(), map1);
%>
<%=htmllist1%>
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("examtime", request) %></font></td>
</tr>


<tr>
<th>Exam Date</th>
<td><input type="text" placeholder="Enter Exam Date" name="examdate" id="datepicker" value="<%=DataUtility.getDateString(bean.getExamdate())%>"></td>
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("examdate", request)%></font></td>
</tr>


<tr>
<th></th>
<td>

<br>

<%if(bean.getId()>0){ %>
<input type="submit" name="operation" value="<%=TimetableCtl.OP_UPDATE %>">
<input type="submit" name="operation" value="<%=TimetableCtl.OP_CANCEL %>">

<%}else{ %>

<input type="submit" name="operation" value="<%=TimetableCtl.OP_SAVE %>">
<input type="submit" name="operation" value="<%=TimetableCtl.OP_RESET %>">

<%} %>
</td>
</tr>


</table>
</form>
</center>
<%@include file = "Footer.jsp" %>
</body>
</html>