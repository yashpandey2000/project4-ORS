<%@page import="co.in.controller.UserCtl"%>
<%@page import="co.in.util.HTMLUtility"%>
<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.util.ServletUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="co.in.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

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

<% List l = (List)request.getAttribute("roleList");%>

<jsp:useBean id="bean" class="co.in.bean.UserBean" scope="request"></jsp:useBean>

<center>

<form action="<%=ORSView.USER_CTL%>" method="post">

<%
if(bean.getId()>0){
%>
<h1><U>Update User</U></h1>
<%}else{ %>
<h1><u>Add User</u></h1>
<% } %>




<h2>
<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
</h2>

<h2>
<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
</h2>


<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdby" value="<%=bean.getCreatedby()%>">
<input type="hidden" name="modifiedby" value="<%=bean.getModifiedby()%>">
<input type="hidden" name="createddatetime" value="<%=DataUtility.getTimestamp(bean.getCreateddatetime())%>">
<input type="hidden"  name="modifieddatetime" value="<%=DataUtility.getTimestamp(bean.getModifieddatetime())%>">


<table>


<tr>
    <th>First Name</th>
    <td>
        <input type="text" name="firstname" value="<%=DataUtility.getStringData(bean.getFirstname()) %>" placeholder="Enter First Name">
    </td>
      <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("firstname",request) %></font></td>
</tr>


<tr>
    <th>Last Name</th>
    <td>
        <input type="text" name="lastname" value="<%=DataUtility.getStringData(bean.getLastname())%>" placeholder="Enter Last Name">
    </td>
      <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("lastname",request) %></font></td>
</tr>




<tr>
    <th>LoginId</th>
    <td>
        <input type="text" name="loginid" value="<%=DataUtility.getStringData(bean.getLoginid()) %>" placeholder="Enter Email ID">
    </td>
    
      <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("loginid",request) %></font></td>
</tr>


<tr>
    <th>DOB</th>
    <td><input type="text" name="dob" placeholder="dd/mm/yyyy" id="datepicker"  value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
    <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("dob",request) %></font></td>
</tr>

 
<tr>
    <th>Password</th>
<td><input type="password" name="password" placeholder="Enter Password" value="<%=DataUtility.getStringData(bean.getPassword())%>">  
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("password",request) %></font></td>
</tr>

 <%if(bean.getId()==0){ %>
<tr>
    <th>Confirm Password</th>
<td><input type="password" name="confirmpassword" placeholder="Enter Confirm Password" value="<%=DataUtility.getStringData(bean.getConfirmpassword())%>">  
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("confirmpassword",request) %></font></td>
</tr>

    <%} %>
    
    

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
 
    <th>Role</th>
    
 <td><%=HTMLUtility.getList("roleid", String.valueOf(bean.getRoleid()), l ) %></td> 
 
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("roleid",request) %></font></td>

</tr> 



<tr>
<th>Mobile No</th>
<td><input type="text" name="mobile" placeholder="Enter Mobile No" value="<%=DataUtility.getStringData(bean.getMobileno())%>"></td>
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("mobile" , request) %></font></td>
</tr>




<tr>
<th></th>
<td>
<br>
<%
if(bean.getId()>0){
%>

<input type="submit" name="operation" value="<%=UserCtl.OP_UPDATE%>">
&nbsp;<input type="submit" name="operation" value="<%=UserCtl.OP_CANCEL%>">

<%}else{%>

<input type="submit" name="operation" value="<%=UserCtl.OP_SAVE%>">
&nbsp;<input type="submit" name="operation" value="<%=UserCtl.OP_RESET%>">

<% } %>


</td>
</tr>

</table>

</center>

</form>

<%@include file = "Footer.jsp" %>

</body>
</html>