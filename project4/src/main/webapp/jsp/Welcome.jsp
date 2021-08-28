<%@page import="co.in.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
 
        
        <%@include file ="Header.jsp" %>
        
        <form action="<%=ORSView.WELCOME_CTL%>" method="post">
            
            
            <br>
            <br>
            
            
            <h1 align="center">
            <font size="10px" color="red">Welcome To ORS</font>
            </h1>
        <%-- <%
        UserBean userbean1 = (UserBean)session.getAttribute("user");
        if(userbean1!=null){
        	
        	if(userbean1.getRoleid()==RoleBean.student){
          %>
        
         <h2 align = "center">
        <a href="<%=ORSView.GET_MARKSHEET_CTL %>">click here to see your marksheet</a>
        </h2>
        
        <%
        }  
    } 
    %> --%>
        
        </form>
        <%@include file="Footer.jsp" %>
        
</body>
</html>