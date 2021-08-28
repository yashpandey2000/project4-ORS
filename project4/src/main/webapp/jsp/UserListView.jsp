<%@page import="co.in.model.UserModel"%>
<%@page import="co.in.util.DataUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="co.in.controller.UserListCtl"%>
<%@page import="java.util.List"%>
<%@page import="co.in.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User list view</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript" src="<%=ORSView.APP_CONTEXT%>/js/checkbox11.js"></script>


</head>
<body>
<jsp:useBean id="bean" class= "co.in.bean.UserBean" scope="request"></jsp:useBean>
<%@include file = "Header.jsp" %>
<center>
<h1><u>User List</u></h1>

<h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h2>

<h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>

<form action="<%=ORSView.USER_LIST_CTL%>" method="post">

<%List l = ServletUtility.getList(request);
if(l==null || l.size()==0){%>

<input type="submit" name="operation" value="<%=UserListCtl.OP_BACK%>">

<%}else{ %>

<table width = "100%">

<tr>
<td align = "center">
<label>First Name:</label>
<input type="text" placeholder="Enter First Name" name ="firstname" value="<%=ServletUtility.getParameter("firstname", request)%>">
&nbsp;<label>Last Name:</label>
<input type="text" name ="lastname"  placeholder="Enter Last Name" value="<%=ServletUtility.getParameter("lastname", request)%>">
&nbsp;<label>EmailId:</label>
<input type = "text" name = "loginid" placeholder="Enter EmailId" value="<%=ServletUtility.getParameter("loginid", request)%>">

<input type="submit" name = "operation" value="<%=UserListCtl.OP_SEARCH %>">
<input type="submit" name = "operation" value="<%=UserListCtl.OP_RESET%>">

</td>
</tr>
</table>

<br><br>

<table border="3" width="100%">
 <tr>
 <th align="left" width="7%"><input type="checkbox" name="Select" id = "select_all">Select All</th>
     <th>S.NO</th>
     <th>FIRST NAME</th>
     <th>LAST NAME</th>
    
     <th>Mobile No</th>
     <th>GENDER</th>
     
     <th>DOB</th>
     <th>EMAIL</th>
     <th>EDIT</th>
</tr>

<tr>
   <%
     int pageNo= ServletUtility.getpageNo(request);
     int pageSize= ServletUtility.getpageSize(request);
     int index= ((pageNo-1)*pageSize)+1;
     
     int nextList = DataUtility.getInt(request.getAttribute("nextlist").toString()); 
    // System.out.println("next list "+nextList);
     
     List list = ServletUtility.getList(request);
     
     Iterator it = list.iterator();
     
     //UserBean bean;
     while(it.hasNext()){
    	
    	 bean = (UserBean)it.next();
    	// System.out.println("roll is"+bean.getRoleid());
   %>
   <tr>
   <td align="left"><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"
   <%if(bean.getRoleid()==1) {%><%="disabled"%><%} %>>
   </td>
   
   <td align="center"><%=index++%></td>
   <td align="center"><%=bean.getFirstname()%></td>
   <td align="center"><%=bean.getLastname()%></td>
   
   <td align="center"><%=bean.getMobileno()%></td>
   <td align="center"><%=bean.getGender()%></td>
   
   <td align="center"><%=bean.getDob()%></td>
   <td align="center"><%=bean.getLoginid()%></td>
   
   <%if(bean.getRoleid()!=RoleBean.admin){ %>
   <td align="center"><a href="UserCtl?id=<%=bean.getId()%>"><i class="fa fa-pencil" aria-hidden="true">Edit</i></a></td>
   <%}else{%>
   <td align="center">(admin)</td>
  <%} %>
   </tr>  
    	 
    	 
   <% }%> 
  </table>
<%
UserModel model = new UserModel();
%>
<table width="100%">

<tr>
    <td ><input type="submit" name="operation" value="<%=UserListCtl.OP_PREVIOUS %>"
    <%=(pageNo>1)?"":"disabled"%>>
    </td>
     
    <td align="center"><input type="submit" name="operation" value="<%=UserListCtl.OP_NEW %>">
    </td>
    
    <td align="center"><input type="submit" name="operation" value="<%=UserListCtl.OP_DELETE %>">
    </td>
         
    <td align="right"><input type="submit" name="operation" value="<%=UserListCtl.OP_NEXT %>"
    <%=((list.size()<pageSize || model.nextPK()-1 == bean.getId())?"disabled":"") %>></td>
    
    
   
    
</tr>

</table>

<input type="hidden" name= "pageno" value="<%=pageNo%>">
 <input type="hidden" name= "pagesize" value="<%=pageSize%>">
 
</form>
<%} %>

</center>

<%@include file = "Footer.jsp"%>
</body>
</html>