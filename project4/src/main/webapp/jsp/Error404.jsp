<%@page import="co.in.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page isErrorPage="true" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/forall.css" ></link>
</head>
<body>
<div align="center">
<h1><font color="red">....oops! Something went wrong</font></h1>
<font style="size: 30px ;">
<h3>Requested Resource not found<br>
Please check the URL entered by you</h3>
</font>
</div>
<div align="center">
 <h2><a href="<%=ORSView.WELCOME_CTL%>">click here to go back</a></h2>
</div>
</body>
</html>