<%@page import="co.in.controller.LoginCtl"%>
<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.util.ServletUtility"%>
<%@page import="co.in.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
<center>
<form action="<%=ORSView.LOGIN_CTL%>" method="post">
<%@include file = "Header.jsp" %>
    <h1><u>Login</u></h1>
    <jsp:useBean id="bean" class ="co.in.bean.UserBean" scope="request"></jsp:useBean>
 
 
  <%
  String uri= (String)session.getAttribute("URI"); 
%> 

 
 <%String str = (String)request.getAttribute("error11");
 if(str!=null){
 %>
 
<h2><font color="red"><%=request.getAttribute("error11") %></font></h2>
 <%} %>
 
 
 <h2>
 <font color = "green"><%=ServletUtility.getSuccessMessage(request) %></font>
 </h2>
       
       
 <h2>
 <font color = "red"><%=ServletUtility.getErrorMessage(request)%></font>
 </h2>
       
       
      <input type ="hidden" name = "id" value = "<%=bean.getId()%>" >
      <input type = "hidden" name= "createdby" value="<%= bean.getCreatedby()%>">
      <input type = "hidden" name="modifiedby" value="<%=bean.getModifiedby()%>">
      <input type="hidden"  name="createddatetime" value="<%=DataUtility.getTimestamp(bean.getCreateddatetime()) %>"> 
      <input type="hidden"  name="modifieddatetime" value="<%=DataUtility.getTimestamp(bean.getModifieddatetime())%>">
       
   
   
<table>
<tr>

<th>Loginid</th>

<td>
<div class="input-icons">
<i class="fa fa-user" aria-hidden="true"></i>
<input type="text" name="loginid" size=25 placeholder="Enter Emailid" style="border-left-width:25px;" value="<%=DataUtility.getStringData(bean.getLoginid())%>">
</td>

<td style="position: fixed">
<font color="red"><%=ServletUtility.getErrorMessage("loginid",request) %></font>
</td>

</tr>



<tr>

<th>Password</th>

<td>
 <div class="input-icons">
  <i class="fa fa-lock" aria-hidden="true"></i>
<input type="password" name="password" size=25 placeholder="Enter password" style="border-left-width:25px;" value="<%=DataUtility.getStringData(bean.getPassword())%>" >
<!-- <span class="eye">
<i id="hide1" class="fa fa-eye"></i>
<i id="hide2" class="fa fa-eye-slash"></i></span> -->
</td>

<td style="position: fixed">
<font color="red"><%=ServletUtility.getErrorMessage("password",request) %></font>
</td>

</tr>

<tr>
    <td colspan="2" align="center"><br><input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_IN%>"></td>
</tr>

<tr>
 
    <td colspan="2" align="center"><a href="<%=ORSView.USER_REGISTRATION_CTL%>"><b>SignUp</b></a> | <a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget Password?</b></a>
   &nbsp;</td>
</tr>


</table>

 <input type="hidden" name="str" value="<%=uri%>"> 

</form>

</center>
<%@include file = "Footer.jsp" %>
</body>
</html>