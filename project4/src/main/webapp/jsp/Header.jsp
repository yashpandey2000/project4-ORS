<%@page import="co.in.controller.LoginCtl"%>
<%@page import="co.in.bean.RoleBean"%>
<%@page import="co.in.controller.ORSView"%>
<%@page import="co.in.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Header page</title>

<link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/forall.css" ></link>


</head>
<body>

<%
UserBean userbean = (UserBean)session.getAttribute("user");

boolean userlogged = userbean!=null;
String welcomeMsg = "Hi ,";

if(userlogged){
	String role = (String)session.getAttribute("role");
	
welcomeMsg = welcomeMsg+" "+userbean.getFirstname()+" "+"("+role+")";

}
else{
	welcomeMsg = welcomeMsg+" "+"Guest";
}
%>

<table width = "100%">
<tr>
<td><a href = "<%=ORSView.WELCOME_CTL %>"><b>Welcome</b></a> |
<%if(userlogged){ %>
	<a href = "<%=ORSView.LOGIN_CTL %>?operation=<%=LoginCtl.OP_LOG_OUT%>"><b>Logout</b></a>


 <%  } else{ %>
	 
	 <a href = "<%=ORSView.LOGIN_CTL %>"><b>Login</a>
	
	<% }%>
</td>
 

  <td rowspan ="2">

   <img align="right" src="<%=ORSView.APP_CONTEXT%>/img/JOBPORTAL-1583211843.png" height="80">
  
  </td>
 
</tr>

<tr>
<td>
<h3>
<%=welcomeMsg%>
</h3>
</td>
</tr>

<%
if(userlogged){
%>
<tr>
<td colspan="2">

<%
if(userbean.getRoleid() == RoleBean.admin){

%>

<a href = "<%=ORSView.MY_PROFILE_CTL%>">MyProfile</b></a> |
<a href = "<%=ORSView.CHANGE_PASSWORD_CTL %>">Change Password</b></a> |
<a href = "<%=ORSView.GET_MARKSHEET_CTL %>">Get Marksheet</b></a> |
<a href = "<%=ORSView.MARKSHEET_MERIT_LIST_CTL %>">Marksheet MeriList</b></a> |
<a href = "<%=ORSView.MARKSHEET_CTL %>">Add Marksheet</b></a> |
<a href = "<%=ORSView.MARKSHEET_LIST_CTL %>">Marksheet List</b></a> |
<a href= "<%=ORSView.USER_CTL %>">Add User</b></a> |
<a href="<%=ORSView.USER_LIST_CTL %>">User List</b></a> |
<a href = "<%=ORSView.COLLEGE_CTL %>">Add College</b></a> |
<a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
<a href= "<%=ORSView.STUDENT_CTL %>">Add Student</b></a> |
<a href = "<%=ORSView.STUDENT_LIST_CTL %>">Student List</b></a> |
<a href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> |
<a href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> |
<a href="<%=ORSView.COURSE_CTL%>">Add Course</b></a> |
<a href = "<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |
<a href="<%=ORSView.SUBJECT_CTL %>">Add Subject</b></a> |
<a href="<%=ORSView.SUBJECT_LIST_CTL %>">Subject List</b></a> |
<a href = "<%=ORSView.FACULTY_CTL %>">Add Faculty</b></a> |
<a href= "<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
<a href = "<%=ORSView.TIMETABLE_CTL %>">Add Timetable</b></a> |
<a href="<%=ORSView.TIMETABLE_LIST_CTL %>">Timetable List</b></a> |
<a href ="<%=ORSView.JAVA_DOC_VIEW %>" target="blank">Java Doc</b></a>


<%
}
%>

<%
if(userbean.getRoleid() == RoleBean.student){
	 System.out.println("roll name  "+userbean.getRoleid());
%>

<a href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</b></a> | 
<a href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</b></a> |
<a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b></a> |
<a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet MeritList</b></a> |
<a href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</b></a> 
      
<%
}
%>

<%

if(userbean.getRoleid() == RoleBean.faculty) {
	
%>

<a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b></a> |
<a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet MeritList</b></a> |
<a href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</b></a> | 
<a href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</b></a> |
<a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> | 
<a href="<%=ORSView.USER_CTL%>">Add User</b></a> | 
<a href="<%=ORSView.USER_LIST_CTL%>">User List</b></a> | 
<a href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a> | 
 <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> | 
 <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> | 
 <a  href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> | 
 <a  href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> | 
 <a  href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> |
 <a  href="<%=ORSView.COURSE_CTL%>">Add Course</b></a> | |
 <a  href="<%=ORSView.COURSE_LIST_CTL%>">Course List</b></a> |
 <a   href="<%=ORSView.SUBJECT_CTL%>">Add Subject</b></a> |
 <a   href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</b></a> |
 <a  href="<%=ORSView.FACULTY_CTL%>">Add Faculty</b></a> |
 <a   href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List</b></a>|
 <a   href="<%=ORSView.TIMETABLE_CTL%>">Add Timetable</b></a> |
 <a  href="<%=ORSView.TIMETABLE_LIST_CTL%>">Timetable List</b></a> |
        
<%} %>



<%
if(userbean.getRoleid() == RoleBean.college)
{ %>

<a href = "<%=ORSView.GET_MARKSHEET_CTL %>">Get Marksheet</b></a> |
<a href = "<%=ORSView.MARKSHEET_MERIT_LIST_CTL %>">Marksheet MeritList</b></a> |
<a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |
<a href = "<%=ORSView.MARKSHEET_LIST_CTL %>">Marksheet List</b></a> |
<a href = "<%=ORSView.STUDENT_CTL %>">Add Student</b></a> |
<a href = "<%=ORSView.STUDENT_LIST_CTL %>">Student List</b></a> |
<a href="<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
<a href ="<%=ORSView.TIMETABLE_LIST_CTL %>">Timetable List</b></a> |
<a href ="<%=ORSView.SUBJECT_LIST_CTL %>">Subject List</b></a>

<%} %>

</td>
</tr>

<%} %>



</table>
<hr>
</body>
</html>