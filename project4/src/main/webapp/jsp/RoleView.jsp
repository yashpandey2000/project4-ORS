<%@page import="co.in.controller.RoleCtl"%>
<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.util.ServletUtility"%>
<%@page import="co.in.bean.RoleBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Role View</title>
</head>


<body>

<center>
<form action="<%=ORSView.ROLE_CTL%>" method="post">
<%@include file = "Header.jsp" %>
    <jsp:useBean id="bean" class ="co.in.bean.RoleBean" scope="request"></jsp:useBean>
 
 
 <h1 align = "center">
 
 <% if(bean!=null && bean.getId()>0){ %>
	 
	<u>Update Role</u>
	
	
	 <%}else{%>
	 
   <u>Add Role</u>
	
	<%}%> 
	 
 </h1>
 
 <h2>
 <font color = "green"><%=ServletUtility.getSuccessMessage(request)%></font>
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

<th>Name</th>

<td>
<input type="text" name="name" size=25 placeholder="Enter Role Name" value="<%=DataUtility.getStringData(bean.getName())%>">
</td>

<td style="position: fixed">
<font color="red"><%=ServletUtility.getErrorMessage("name",request) %></font>
</td>

</tr>



<tr>

<th>Description</th>

<td>
<input type="text" name="desc" size=25 placeholder="Enter Short Description" value="<%=DataUtility.getStringData(bean.getDescription())%>" >
</td>

<td style="position: fixed">
<font color="red"><%=ServletUtility.getErrorMessage("desc",request) %></font>
</td>

</tr>



<tr>
<th></th>
<%
if(bean.getId() >0 && bean!=null){
%>
    <td align="center"><br><input type="submit" name="operation" value="<%=RoleCtl.OP_UPDATE%>">
    <input type="submit" name="operation" value="<%=RoleCtl.OP_CANCEL%>">
     </td>
    <%}else{ %>
    
    <td ><br><input type="submit" name="operation" value="<%=RoleCtl.OP_SAVE%>">
    <input type="submit" name="operation" value="<%=RoleCtl.OP_RESET%>"> </td>
    <%} %>
    
   
</tr>

</table>
</form>
</center>
<%@include file = "Footer.jsp" %>


</body>
</html>