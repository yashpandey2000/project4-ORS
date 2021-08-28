<%@page import="co.in.controller.MarksheetCtl"%>
<%@page import="co.in.util.HTMLUtility"%>
<%@page import="com.mysql.fabric.xmlrpc.base.Data"%>
<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="org.w3c.dom.ls.LSInput"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet View</title>

</head>
<body>
<%@include file="Header.jsp" %>
<center>
<jsp:useBean id="bean" class="co.in.bean.MarksheetBean" scope="request"></jsp:useBean>

<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">

<%

List l = (List)request.getAttribute("studentlist");


if(bean!=null && bean.getId()>0){
%>
<h1><U>Update Marksheet</U></h1>
<%}else{ %>
<h1><U>Add Marksheet</U></h1>
<%} %>


<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>

<h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h2>

<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" id="createdby" value="<%=bean.getCreatedby()%>">
<input type="hidden" id="modifiedby" value="<%=bean.getModifiedby()%>">
<input type="hidden" id="createddatetime" value="<%=DataUtility.getTimestamp(bean.getCreateddatetime())%>"> 
<input type="hidden" id="modifieddatetime" value="<%=DataUtility.getTimestamp(bean.getModifieddatetime())%>">


<table align="center">

<tr>
<th align="left">RollNo</th>
<td><input type="text" name="rollno"  placeholder="Enter Roll No" value="<%=DataUtility.getStringData(bean.getRollno())%>"></td>
<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("rollno",request) %></font></td>
</tr>

 
 <tr>
<th align="left">Name</th>
<td><font color="red"><%=HTMLUtility.getList("studentid", String.valueOf(bean.getStudentid()),l) %></font></td>
<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("studentid",request) %></font></td>
</tr>
 



<tr>
<th align="left">Physics</th>
<td><input type="text" name="physics" placeholder=" Enter Physics Marks" value="<%=DataUtility.getStringData(bean.getPhysics())%>"></td>
<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("physics",request) %></font></td>
</tr>



<tr>
<th align="left">Chemistry</th>
<td><input type="text" name="chemistry" placeholder="Enter Chemistry Marks" value="<%=DataUtility.getStringData(bean.getChemistry())%>"></td>
<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("chemistry",request) %></font></td>
</tr>




<tr>
<th align="left">Math</th>
<td><input type="text" name="math" placeholder="Enter Math Marks" value="<%=DataUtility.getStringData(bean.getMaths())%>"></td>
<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("math",request) %></font></td>
</tr>


<tr>
<th></th>
<td>

<br>

<%if(bean.getId()>0 && bean!=null){ %>


<input type="submit" name="operation" value="<%=MarksheetCtl.OP_UPDATE%>">
&nbsp;<input type="submit" name="operation" value="<%=MarksheetCtl.OP_CANCEL%>">

<%}else{ %>

<input type="submit" name="operation" value="<%=MarksheetCtl.OP_SAVE%>">
&nbsp;<input type="submit" name="operation" value="<%=MarksheetCtl.OP_RESET%>">

<%} %>
</td>
</tr>


</table>
</center>
</form>
<%@include file="Footer.jsp" %>
</body>
</html>