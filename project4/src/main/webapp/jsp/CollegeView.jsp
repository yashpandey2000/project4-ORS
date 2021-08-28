<%@page import="co.in.controller.CollegeCtl"%>
<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.util.ServletUtility"%>
<%@page import="co.in.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College View</title>

</head>
<body>
<%@include file = "Header.jsp" %>
<jsp:useBean id="bean" class = "co.in.bean.CollegeBean" scope="request"></jsp:useBean>
<center>
<%
if( bean.getId()>0){
%>
<h1><u>Update College</u></h1>
<%}else{ %>
<h1><u>Add College</u></h1>
<%} %>

<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>


<h2><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h2>



<form action ="<%=ORSView.COLLEGE_CTL %>" method="post">

<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdby" value="<%=bean.getCreatedby()%>">
<input type="hidden" name="modifiedby" value="<%=bean.getModifiedby()%>">
<input type="hidden" name="createddatetime" value="<%=DataUtility.getTimestamp(bean.getCreateddatetime())%>"> 
<input type="hidden" name="modifieddatetime" value="<%=DataUtility.getTimestamp(bean.getModifieddatetime())%>">

<table>

<tr>
 <th>Name</th>
 <td><input type="text"  name="cname" placeholder="Enter College Name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("cname",request) %></font></td>
 </tr>


<tr>
 <th>Address</th>
 <td><input type="text"  name="caddress" placeholder="Enter College Address" value="<%=DataUtility.getStringData(bean.getAddress())%>"></td>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("caddress",request) %></font></td>
 </tr>


<tr>
 <th>State</th>
 <td><input type="text"  name="cstate" placeholder="Enter College State" value="<%=DataUtility.getStringData(bean.getState())%>"></td>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("cstate",request) %></font></td>
 </tr>

<tr>
 <th>City</th>
 <td><input type="text"  name="ccity" placeholder="Enter College City" value="<%=DataUtility.getStringData(bean.getCity())%>"></td>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("ccity",request) %></font></td>
 </tr>


<tr>
 <th>PhoneNo</th>
 <td><input type="text"  name="cphone" placeholder="Enter College PhoneNo" value="<%=DataUtility.getStringData(bean.getPhoneno())%>"></td>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("cphone",request) %></font></td>
 </tr>


<tr>
<th></th>
<td>

<br>

<%
if(bean.getId()>0){
%>
&nbsp;<input type="submit" name="operation" value="<%=CollegeCtl.OP_UPDATE%>">
&nbsp;<input type="submit" name="operation" value="<%=CollegeCtl.OP_CANCEL%>">

<%}else{ %>

<input type="submit" name="operation" value="<%=CollegeCtl.OP_SAVE%>">
&nbsp;<input type="submit" name="operation" value="<%=CollegeCtl.OP_RESET%>">

<%} %>
</td>
</tr>
</table>
</form>
</center>
<%@include file = "Footer.jsp"%>
</body>
</html>