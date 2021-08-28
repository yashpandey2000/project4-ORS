
<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.model.Studentmodel"%>
<%@page import="co.in.bean.StudentBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="co.in.controller.StudentListCtl"%>
<%@page import="java.util.List"%>
<%@page import="co.in.util.ServletUtility"%>
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
<%@include file = "Header.jsp" %>
<jsp:useBean id="bean" class="co.in.bean.StudentBean" scope="request"></jsp:useBean>
<center>
<h1><u>Student List</u></h1>

<form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">

<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>


<h2><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h2>


<%
List l = (List)ServletUtility.getList(request);

if(l==null || l.size()==0){

%>

<input type="submit" name="operation" value="<%=StudentListCtl.OP_BACK%>">

<%}else{%>

<table width="100%">

<tr align = "center">
<td>
 <label>First Name:</label>
<input type="text"  name="firstname" placeholder="Enter First Name" value="<%=DataUtility.getStringData(bean.getFirstname())%>">
 

<label>Last Name:</label>
<input type="text" name="lastname" placeholder="Enter Last Name" value="<%=DataUtility.getStringData(bean.getLastname())%>">  
 
 
<label>EmailId:</label>
<input type="text" name="email" placeholder="Enter Email ID" value="<%=DataUtility.getStringData(bean.getEmail())%>">  


<input type="submit" name ="operation" value="<%=StudentListCtl.OP_SEARCH%>">
<input type="submit" name ="operation" value="<%=StudentListCtl.OP_RESET%>">
<br>
<br>
<br>

</td>
</tr>
</table>


<table border="3" width="100%">

<tr>
<th width="7%" align="left"><input type="checkbox" id="select_all" name="Select">Select All</th>
<th>S.NO</th>
<th>COLLEGE</th>
<th>FIRST NAME</th>
<th>LAST NAME</th>
<th>DOB</th>
<th>MOBILE NO</th>
<th>EMAIL</th>
<th>EDIT</th>
</tr>


<%
int pageNo = ServletUtility.getpageNo(request);
int pageSize = ServletUtility.getpageSize(request);
int index = ((pageNo-1)*pageSize)+1;

List list = ServletUtility.getList(request);

Iterator<StudentBean> it = list.iterator();

while(it.hasNext()){


bean = it.next();


%>


<tr>
<td><input type= "checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
<td align="center"><%=index++ %></td>
<td align="center"><%=bean.getCollegename() %></td>
<td align="center"><%=bean.getFirstname() %></td>
<td align="center"><%=bean.getLastname() %></td>
<td align="center"><%=bean.getDob() %></td>
<td align="center"><%=bean.getMobileno() %></td>
<td align="center"><%=bean.getEmail() %></td>
<td align="center"><a href="StudentCtl?id=<%=bean.getId()%>"><i class="fa fa-pencil" aria-hidden="true">Edit</a></i></td>

<%}%>
</tr>

</table>
<%
Studentmodel model = new Studentmodel();
%>

<table width="100%">

<tr>
<td align="left"><input type="submit" name="operation" value="<%=StudentListCtl.OP_PREVIOUS%>"<%=(pageNo>1)?"":"disabled" %>></td>
<td align="center"><input type="submit" name="operation" value="<%=StudentListCtl.OP_NEW%>"></td>
<td align="center" ><input type="submit"  name="operation"  value="<%=StudentListCtl.OP_DELETE%>"></td>
<td align="right"><input type="submit" name="operation" value="<%=StudentListCtl.OP_NEXT%>"<%=(list.size()<pageSize || model.nextpk()-1==bean.getId())?"disabled":""%>></td>
</tr>
</table>


<input type="hidden" name="pageno" value="<%=pageNo%>">

<input type="hidden" name="pagesize" value="<%=pageSize%>">

</form>
</center>

<%}%>
</body>
<%@include file = "Footer.jsp"%>

</html>