<%@page import="co.in.controller.StudentCtl"%>
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
<title>Student View</title>


  

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
 <center>
 
 <%@include file = "Header.jsp" %>
<form action="<%=ORSView.STUDENT_CTL%>"  method="post">

    <jsp:useBean id="bean" class ="co.in.bean.StudentBean" scope="request"></jsp:useBean>
   <%
   List l  = (List)request.getAttribute("collegelist");
   %>

<%
if(bean.getId()>0){
%>

<h1><u>Update Student</u></h1>

<%}else{%>

<h1><u>Add Student</u></h1>


<%} %>


       <h2>
       <font color = "green"><%=ServletUtility.getSuccessMessage(request) %></font>
       </h2>
       
       
       <h2>
       <font color = "red"><%=ServletUtility.getErrorMessage(request) %></font>
       </h2>

 <input type ="hidden" name = "id" value = "<%=bean.getId()%>" >
      <input type = "hidden" name= "createdby" value="<%= bean.getCreatedby()%>">
      <input type = "hidden" name="modifiedby" value="<%=bean.getModifiedby()%>">
      <input type="hidden"  name="createddatetime" value="<%=DataUtility.getTimestamp(bean.getCreateddatetime()) %>"> 
      <input type="hidden"  name="modifieddatetime" value="<%=DataUtility.getTimestamp(bean.getModifieddatetime()) %>">

<table>





<tr>
 <th>First Name</th>
 <td><input type="text"  name="firstname" placeholder="Enter First Name" value="<%=DataUtility.getStringData(bean.getFirstname())%>"></td>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("firstname",request) %></font></td>
 </tr>


<tr>
 <th>Last Name</th>
 <td><input type="text" name="lastname" placeholder="Enter Last Name" value="<%=DataUtility.getStringData(bean.getLastname())%>">  
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("lastname", request)%></font>
</tr>



<tr>
 <th>College</th>
 <td><%=HTMLUtility.getList("college", String.valueOf(bean.getCollegeid()),l) %></td>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("college",request) %></font></td>
 </tr>



<tr>
    <th>DOB</th>
    <td><input type="text" name="dob" placeholder="dd/mm/yyyy" id="datepicker" value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
    <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("dob",request) %></font></td>
</tr>



<tr>
<th>MobileNo</th>
<td><input type="text" name="mobile" placeholder="Enter Mobile No" value="<%=DataUtility.getStringData(bean.getMobileno())%>"></td>
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("mobile" , request) %></font></td>
</tr>

<tr>
    <th>Emailid</th>
<td><input type="text" name="email" placeholder="Enter Email ID" value="<%=DataUtility.getStringData(bean.getEmail())%>">  
<td><font color="red"><%=ServletUtility.getErrorMessage("email",request) %></font></td>
</tr>


<tr>
<th></th>
<td>


<%if(bean.getId()>0 && bean!=null){%>
<input type="submit" name="operation" value="<%=StudentCtl.OP_UPDATE%>">
&nbsp;<input type = "submit" name="operation" value="<%=StudentCtl.OP_CANCEL%>">

<%}else{ %>
<input type="submit" name="operation" value="<%=StudentCtl.OP_SAVE%>">
&nbsp;<input type = "submit" name="operation" value="<%=StudentCtl.OP_RESET%>">

<%} %>
</td>
</tr>




</table>
</form>
</center>
<%@include file = "Footer.jsp" %>
</body>
</html>