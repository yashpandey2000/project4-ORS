<%@page import="co.in.model.MarksheetModel"%>
<%@page import="java.util.Iterator"%>
<%@page import="co.in.bean.MarksheetBean"%>
<%@page import="co.in.controller.MarksheetListCtl"%>
<%@page import="java.util.List"%>
<%@page import="co.in.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet List View</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript" src="<%=ORSView.APP_CONTEXT%>/js/checkbox11.js"></script>

</head>
<body>
<%@include file ="Header.jsp" %>
<jsp:useBean id="bean" class="co.in.bean.MarksheetBean" scope="request"></jsp:useBean>

<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="post">

<center>

<h1><u>Marksheet List</u><h1>

<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>

<h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h2>

<%
List l = ServletUtility.getList(request);

if(l==null || l.size()==0){
%>

<input type="submit" name="operation" value="<%=MarksheetListCtl.OP_BACK%>">

<%}else{ %>

<table width="100%">

<td align="center">
<label>Name:</label>
<input type="text" name="name" placeholder="Enter Name" value="<%=ServletUtility.getParameter("name", request)%>">
<label>RollNo:</label>
<input type="text" name="rollno" placeholder="Enter RollNo" value="<%=ServletUtility.getParameter("rollno", request)%>">

<input type="submit" name="operation" value="<%=MarksheetListCtl.OP_SEARCH%>">

<input type="submit" name="operation" value="<%=MarksheetListCtl.OP_RESET%>">


</td>
</table>

<br><br>

<table border="3" width="100%" align="center">

<tr>
<th align="left" width="7%"><input type="checkbox" id="select_all" name="Select">Select All</th>
<th>S.No.</th>
      <th>Roll No</th>
      <th>Name</th>
      <th>Physics</th>
      <th>Chemistry</th>
      <th>Math</th>
      <th>EDIT</th>
      </tr>
      
      
      <%
      int pageNo = ServletUtility.getpageNo(request);
      int pageSize = ServletUtility.getpageSize(request);
    		  
       int index = (pageNo-1)*pageSize+1;
      
      List<MarksheetBean> list = ServletUtility.getList(request);
      
      Iterator it = list.iterator();
      while(it.hasNext()){
    
    	  bean = (MarksheetBean)it.next();  
      %>
      
      <tr>
      <td align="left"><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
       <td align="center"><%=index++ %></td>
        <td align="center"><%=bean.getRollno() %></td>
         <td align="center"><%=bean.getName() %></td>
          <td align="center"><%=bean.getPhysics() %></td>
           <td align="center"><%=bean.getChemistry() %></td>
            <td align="center"><%=bean.getMaths() %></td>
             <td align="center"><a href="MarksheetCtl?id=<%=bean.getId()%>"><i class="fa fa-pencil" aria-hidden="true">Edit</a></i></td>
      </tr>
      
      
  <%} %>    
           
</table>

<% MarksheetModel model = new MarksheetModel(); %>

<table width="100%">

<tr>
<td align="left"><input type="submit" name="operation" value="<%=MarksheetListCtl.OP_PREVIOUS%>"  <%=(pageNo==1)?"disabled":"" %>></td>
<td align="center"><input type="submit" name="operation" value="<%=MarksheetListCtl.OP_NEW%>"></td>
<td align="center"><input type="submit" name="operation" value="<%=MarksheetListCtl.OP_DELETE%>"></td>
<td align="right"><input type="submit" name="operation" value="<%=MarksheetListCtl.OP_NEXT%>" <%=((list.size()<pageSize || model.nextpk()-1 == bean.getId())?"disabled":"") %>></td>
</tr>
</table>


<input type="hidden" name="pageno" value="<%=pageNo%>">
<input type="hidden" name="pagesize" value="<%=pageSize%>">

</form>
</center>
<%} %>

</body>
<%@include file="Footer.jsp"%>
</html>