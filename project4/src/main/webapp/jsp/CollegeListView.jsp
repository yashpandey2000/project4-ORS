<%@page import="co.in.model.CollegeModel"%>
<%@page import="co.in.bean.CollegeBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.controller.CollegeListCtl"%>
<%@page import="java.util.List"%>
<%@page import="co.in.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College list View</title>


<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  
  
  
<script type="text/javascript" src="<%=ORSView.APP_CONTEXT%>/js/checkbox11.js"></script>



      
</head>
<body>
<jsp:useBean id="bean" class= "co.in.bean.CollegeBean" scope="request"></jsp:useBean>
<%@include file = "Header.jsp" %>
<center>
<h1><u>College List</u></h1>

<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="post">

<h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h2>

<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>

<%
List l = ServletUtility.getList(request);
if(l==null || l.size()==0){
%>

<input type="submit" name="operation" value="<%=CollegeListCtl.OP_BACK%>">

<%}else{%>

<table width="100%">


<tr>
<td align="center">


<label>Name:</label>
<input type="text" name="cname" placeholder="Enter Last Name" value="<%=DataUtility.getStringData(bean.getName())%>">  
 
<label>City:</label>
<input type="text" name="ccity" placeholder="Enter City Name" value="<%=DataUtility.getStringData(bean.getCity())%>">  


<input type="submit" name ="operation" value="<%=CollegeListCtl.OP_SEARCH%>">
<input type="submit" name ="operation" value="<%=CollegeListCtl.OP_RESET%>">

</td>
</tr>

</table>

<br>
<br>

<table width="100%" border="3">

<tr>
<th width="7%" align="left"><input type="checkbox" id="select_all" name="Select">Select All</th>
<th>S.NO</th>
<th> NAME</th>
<th>ADDRESS</th>
<th>CITY</th>
<th>STATE</th>
<th>PHONE NO</th>
<th>EDIT</th>
</tr>


<%
int pageNo = ServletUtility.getpageNo(request);
int pageSize = ServletUtility.getpageSize(request);
int index = ((pageNo-1)*pageSize)+1;

List list = ServletUtility.getList(request);

Iterator<CollegeBean> it = list.iterator();

while(it.hasNext()){
	bean= it.next();


%>

<tr>
<td><input type= "checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
<td align="center"><%=index++ %></td>
<td align="center"><%=bean.getName() %></td>
<td align="center"><%=bean.getAddress() %></td>
<td align="center"><%=bean.getState() %></td>
<td align="center"><%=bean.getCity()%></td>
<td align="center"><%=bean.getPhoneno() %></td>
<td align="center"><a href="CollegeCtl?id=<%=bean.getId()%>"><i class="fa fa-pencil" aria-hidden="true">Edit</a></i></td>

</tr>

<%} %>
</table>

<%
CollegeModel model = new CollegeModel();

%>

<table width="100%" >

<tr>
<tr>
<td align="left"><input type="submit" name="operation" value="<%=CollegeListCtl.OP_PREVIOUS%>"<%=(pageNo>1)?"":"disabled" %>></td>
<td align="center"><input type="submit" name="operation" value="<%=CollegeListCtl.OP_NEW%>"></td>
<td align="center" ><input type="submit"  name="operation"  value="<%=CollegeListCtl.OP_DELETE%>"></td>
<td align="right"><input type="submit" name="operation" value="<%=CollegeListCtl.OP_NEXT%>"<%=(list.size()<pageSize || model.nextPK()-1==bean.getId())?"disabled":""%>></td>
</tr>

</table>

<input type="hidden" name = "pageno" value="<%=pageNo%>">
  <input type="hidden" name = "pagesize" value="<%=pageSize%>">
  
</form>

<%} %>


</center>
<%@include file = "Footer.jsp"%>

</body>
</html>