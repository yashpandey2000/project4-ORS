<%@page import="co.in.util.DataUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="co.in.controller.RoleListCtl"%>
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


</head>
<body>
<jsp:useBean id="bean" class="co.in.bean.RoleBean" scope="request"></jsp:useBean>
<%@include file = "Header.jsp" %>

<center>
<h1><u>Role List</u></h1>

<h2>
 <font color = "green"><%=ServletUtility.getSuccessMessage(request)%></font>
 </h2>
       
       
 <h2>
 <font color = "red"><%=ServletUtility.getErrorMessage(request)%></font>
 </h2>


<form action="<%=ORSView.ROLE_LIST_CTL%>" method="post">

<%
List l = ServletUtility.getList(request);
if(l==null || l.size()==0){
%>

<input type="submit" name="operation" value="<%=RoleListCtl.OP_BACK%>">
<%}else{ %>

<table width = "100%">
<tr align = "center">
<td>Name:
<input type="text" name = "name" placeholder="Enter Role Name" value="<%=DataUtility.getStringData(bean.getName())%>">

&nbsp;<input type="submit" name="operation" value="<%=RoleListCtl.OP_SEARCH%>">
&nbsp;<input type="submit" name="operation" value="<%=RoleListCtl.OP_RESET%>">
</td>
</tr>
</table>

<br>
<br>

<table width="100%" border="3">

<tr>
					<th width="6%" align="left"><input type="checkbox" id="select_all" name="Select" />Select All</th>
					<th width="6%">S.No</th>
					<th width="25%">Role</th>
					<th width="40%">Description</th>
					<th width="7%">EDIT</th>
				</tr>

<%
int pageNo = ServletUtility.getpageNo(request);
int pageSize = ServletUtility.getpageSize(request);
int index = ((pageNo-1)*pageSize)+1;

List list = ServletUtility.getList(request);

Iterator<RoleBean> it = list.iterator();

while(it.hasNext()){
	
	RoleBean bean1;
    bean1 = it.next();
%>

<tr>

<td><input type="checkbox" class="checkbox" name="ids" value="<%=bean1.getId()%>"      

<%if(bean1.getId()==1) {%> <%="disabled"%><%}%>>

</td>
   
<td align = "center"><%=index++ %></td>
<td align = "center"><%=bean1.getName()%></td>
<td align = "center"><%=bean1.getDescription() %></td>
<td align = "center">
<%if(bean1.getId()!=1){ %>

<a href="RoleCtl?id=<%=bean1.getId()%>"><i class="fa fa-pencil" aria-hidden="true">Edit</a></i>

</td>
<%}%>

</tr>

<% } %>


</table>

<table width="100%">

<tr>
<td align="left"><input type="submit" name = "operation" value="<%=RoleListCtl.OP_PREVIOUS %>"<%=(pageNo==1)?"disabled":"" %>></td>
   <td align="center"><input type="submit" name = "operation" value="<%=RoleListCtl.OP_NEW %>"></td>
   <td align="center"><input type="submit" name = "operation" value="<%=RoleListCtl.OP_DELETE%>"></td>
   <td align="right"><input type="submit" name = "operation" value="<%=RoleListCtl.OP_NEXT %>"  <%=(list.size()<pageSize)?"disabled":"" %>>
</td>


</tr>



</table>
</form>
</center>
<%} %>
<%@include file = "Footer.jsp" %>
</body>
</html>