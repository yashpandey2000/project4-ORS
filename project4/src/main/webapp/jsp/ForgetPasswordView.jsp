<%@page import="co.in.controller.ForgetPasswordCtl"%>
<%@page import="co.in.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
<center>
    <form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">
     <%@include file ="Header.jsp" %>
     <jsp:useBean id="bean" class="co.in.bean.UserBean" scope="request"></jsp:useBean>
     
        <h1><U>Forget password</U></h1>
        
        <h4>Submit your email address and we'll send your password.</h4>
       
<h2>
<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
</h2>

<h2>
<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
</h2>

<input type="hidden" name="id" value="<%=bean.getId()%>">

 <table>
  <tr>
 <th>Emailid</th>           
 <td> 
 <input type="text" name="login" placeholder="Enter Emailid" value="<%=ServletUtility.getParameter("login", request)%>">
 </td>
 &emsp;
 <td><input type="submit" name="operation" value="<%=ForgetPasswordCtl.OP_GO%>"></td>
 <td> <input type="submit" name="operation" value="<%=ForgetPasswordCtl.OP_RESET%>"></td>
 </tr> 
 
 <tr >
 <th></th>
 <td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("login",request) %></font></td>
  </tr> 
            
        </table>
    </form>
    
    <br><br>
    <%@include file="Footer.jsp" %>
    
</center>
</body>
</html>