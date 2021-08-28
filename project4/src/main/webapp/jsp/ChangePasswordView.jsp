<%@page import="co.in.controller.ChangePasswordCtl"%>
<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Password</title>

</head>
<body>
<center>

<form action ="<%=ORSView.CHANGE_PASSWORD_CTL %>" method = "post">
<%@include file = "Header.jsp" %>
<jsp:useBean id="bean" class="co.in.bean.UserBean" scope="request"></jsp:useBean>
<h1><u>Change password</u></h1>


<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>


<h2><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h2>


<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdby" value="<%=bean.getCreatedby()%>">
<input type="hidden" name="modifiedby" value="<%=bean.getModifiedby()%>">
<input type="hidden" name="createddatetime" value="<%=DataUtility.getTimestamp(bean.getCreateddatetime())%>">
<input type="hidden" name="modifieddatetime" value="<%=DataUtility.getTimestamp(bean.getModifieddatetime())%>">

<table>

<tr>
<th class="label".>Old Password</th>
<td><input type="password" name="oldpassword" size="30" placeholder="Enter Old Password" value="<%=DataUtility.getStringData(request.getParameter("oldpassword"))%>"></td>
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("oldpassword",request) %></font></td>
</tr>



<tr>
<th class="label">New Password</th>
<td><input type="password" name="newpassword" size="30" placeholder="Enter New Password" value="<%=DataUtility.getStringData(request.getParameter("newpassword"))%>"></td>
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("newpassword",request) %></font></td>
</tr>


<tr>
<th class="label">Confirm Password</th>
<td><input type="password" name="confirmpassword" size="30" placeholder="Enter Confirm Password" value="<%=DataUtility.getStringData(request.getParameter("confirmpassword"))%>"></td>
<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("confirmpassword",request)%></font></td>
</tr>


 <tr>  
 <th></th>                
<td colspan="3"><br><input type="submit" name="operation" value="<%=ChangePasswordCtl.OP_SAVE %>" >                        
&nbsp;<input type="submit" name="operation" value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE %>" > 
</td>
</tr>


</table>
</form>
<%@include file = "Footer.jsp"%>
</center>

</body>
</html>