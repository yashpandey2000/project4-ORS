+<%@page import="co.in.controller.FacultyCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="co.in.util.HTMLUtility"%>
<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.bean.SubjectBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="co.in.util.ServletUtility"%>
<%@page import="co.in.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty View</title>

  

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
<%@include file="Header.jsp"%>
<center>
<jsp:useBean id="bean" class="co.in.bean.FacultyBean" scope="request"></jsp:useBean>


<%if(bean.getId()>0){%>
<h1><u>Update Faculty</u></h1>
<%}else{ %>
<h1><u>Add Faculty</u></h1>
<%} %>


<h2><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h2>

<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>

<%
List l1 =(List) request.getAttribute("collegelist");
List l2 = (List)request.getAttribute("courselist");
List l3 = (List)request.getAttribute("subjectlist");
%>

<form action="<%=ORSView.FACULTY_CTL%>" method="post">

<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdby" value="<%=bean.getCreatedby()%>">
<input type="hidden" name="modifiedby" value="<%=bean.getModifiedby()%>">
<input type="hidden" name="createddatetime" value="<%=DataUtility.getTimestamp(bean.getCreateddatetime())%>"> 
<input type="hidden" name="modifieddatetime" value="<%=DataUtility.getTimestamp(bean.getModifieddatetime())%>">

<table>

<tr>
<th>College Name</th>
<td><%=HTMLUtility.getList("collegeid",String.valueOf(bean.getCollegeid()),l1) %></td>
<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("collegename",request)%></font></td>
</tr> 

 <tr>
<th>Course Name</th>
<td><%=HTMLUtility.getList("courseid",String.valueOf(bean.getCourseid()),l2) %></td>
<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("coursename",request)%></font></td>
</tr> 




 <tr>
<th>Subject Name</th>
<td><%=HTMLUtility.getList("subjectid",String.valueOf(bean.getSubjectid()),l3) %></td>
<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("subjectname",request)%></font></td>
</tr>

<tr>
 <th>First Name</th>
 <td><input type="text"  name="fname" placeholder="Enter First Name" value="<%=DataUtility.getStringData(bean.getFirstname())%>"></td>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("fname",request) %></font></td>
 </tr>

<tr>
 <th>Last Name</th>
 <td><input type="text" name="lname" placeholder="Enter Last Name" value="<%=DataUtility.getStringData(bean.getLastname())%>">  
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("lname", request)%></font>
</tr>

<tr>
<th>Gender</th>
<td>
<%
HashMap<String,String> map = new HashMap<String , String>();
map.put("Male","Male");
map.put("Female", "Female");

String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);

%>
<%=htmlList%> 
</td> 
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font></td>
</tr>


<tr>
    <th>Emailid</th>
<td><input type="text" name="loginid" placeholder="Enter Email ID" value="<%=DataUtility.getStringData(bean.getLoginid())%>">  
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("loginid",request) %></font></td>
</tr>


<tr>
    <th>Date_Of_Joining</th>
<td><input type="text" name="doj" id="datepicker" placeholder="Enter Date_Of_Joining"  value="<%=DataUtility.getDateString(bean.getDateofjoining())%>">  
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("doj",request) %></font></td>
</tr>


<tr>
    <th>Qualification</th>
<td><input type="text" name="qual" placeholder="Enter Qualification" value="<%=DataUtility.getStringData(bean.getQualification())%>">  
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("qual",request) %></font></td>
</tr>


<tr>
<th>MobileNo</th>
<td><input type="text" name="mobile" placeholder="Enter Mobile No" value="<%=DataUtility.getStringData(bean.getMobileno())%>"></td>
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("mobile" , request) %></font></td>
</tr>


<tr>
<th></th>
<td>

<br>

<%if (bean.getId()>0 && bean!=null) {%>
<input type="submit" name="operation" value="<%=FacultyCtl.OP_UPDATE%>">
<input type="submit" name="operation" value="<%=FacultyCtl.OP_CANCEL%>">

<%}else{%>

<input type="submit" name="operation" value="<%=FacultyCtl.OP_SAVE%>">
<input type="submit" name="operation" value="<%=FacultyCtl.OP_RESET%>">

<%} %>
</td>
</tr>

</table>
</form>
</center>
</body>
<%@include file="Footer.jsp" %>
</html>