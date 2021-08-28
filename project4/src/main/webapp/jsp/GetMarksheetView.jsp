<%@page import="co.in.util.DataUtility"%>
<%@page import="co.in.controller.GetMarksheetCtl"%>
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
	<%@include file="Header.jsp"%>
	
	<jsp:useBean id="bean" class="co.in.bean.MarksheetBean" scope="request"></jsp:useBean>
	<center>
	
	<h1><u>Get Marksheet</u></h1>
	
	<form action="<%=ORSView.GET_MARKSHEET_CTL%> " method="post">
	
	<input type="hidden" name="id" value="<%=bean.getId()%>">
	
	<h2><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h2>


    <h2><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h2>
	
	
	<table>
	
	<tr>
	
	<th align="right">Roll No:</th>
	
	<td>
	<input type="text" name="rollno" placeholder="Enter RollNo" value="<%=ServletUtility.getParameter("rollno", request) %>" >&nbsp;
	<input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_GO%>">
	&nbsp;<input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_RESET%>">
	</td>
	
	</tr>
	
	<tr>
	<th></th>
    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("rollno",request) %></font></td>
	</tr>
	
	
	</table>
	
		<% if(bean.getRollno()!=null && bean.getRollno().trim().length()>0 ){	%>
										
		<br><br>							
	
	<table border="1">
	  <table border="1" width="40%">
	  
	  <tr>
	  <td align="center"><h2>Rays Technologies</h2></td>
	  </tr>
	  </table>
	
	
	<table border="1" width="40%">
	
	<tr>
	<td align="center">Name</td>
	<th><%=DataUtility.getStringData(bean.getName()) %></th>
	
	<td align="center">Roll No</td>
	<th><%=DataUtility.getStringData(bean.getRollno()) %></th>
	</tr>
	
	<tr>
	<td align="center">Status</td>
	<th>Regular</th>
	
	<td align="center">Course</td>
	<th>BE</th>
	
	</tr>
	</table>
	
<%

int phy = DataUtility.getInt(DataUtility.getStringData(bean.getPhysics()));

int chem = DataUtility.getInt(DataUtility.getStringData(bean.getChemistry()));

int math = DataUtility.getInt(DataUtility.getStringData(bean.getMaths()));

int tot = (phy+chem+math);

float per = tot/3;


%>	
	
<table border="1" width="40%">
<tr>
<th align="center" style="width: 25%">Subject</th>
<th align="center" style="width: 25%">Marks Obtained</th>
<th align="center" style="width: 25%">Maximum Marks</th>
<th align="center" style="width: 25%">Minimum Marks</th>
<th align="center" style="width: 35%">Grade</th>
</tr>

<tr>
<td align="center">Physics</td>
<td align="center"><%=phy%>
<%
if(phy<33){%>
<span style="color: red">*</span>

<%} %>
</td>

<td align="center">100</td>
<td align="center">33</td>
<td align = "center">

<%if(phy>90 && phy<=100){%>A+

<%}else if(phy>80 && phy<=90){%>A

<%}else if(phy>70 && phy<=80){%>B+

<%}else if(phy>70 && phy<=80){%>B

<%}else if(phy>60 && phy<=70){%>C+

<%}else if(phy>50 && phy<=60){%>C

<%}else if(phy>=33 && phy<=55){%>D

<%}else if(phy>=0 && phy<33){%><span color="red">Fail</span><%}%>
 
 </td>
</tr>


<tr>
<td align="center">Chemistry</td>
<td align="center"><%=chem%>
<%
if(chem<33){%>
<span style="color: red">*</span>

<%} %>
</td>

<td align="center">100</td>
<td align="center">33</td>
<td align = "center">

<%if(chem>90 && chem<=100){%>A+

<%}else if(chem>80 && chem<=90){%>A

<%}else if(chem>70 && chem<=80){%>B+

<%}else if(chem>70 && chem<=80){%>B

<%}else if(chem>60 && chem<=70){%>C+

<%}else if(chem>50 && chem<=60){%>C

<%}else if(chem>=33 && chem<=55){%>D

<%}else if(chem>=0 && chem<33){%><span color="red">Fail</span><%}%>
 
 </td>
</tr>



<tr>
<td align="center">Math</td>
<td align="center"><%=math%>
<%
if(math<33){%>
<span style="color: red">*</span>

<%} %>
</td>

<td align="center">100</td>
<td align="center">33</td>
<td align = "center">

<%if(math>90 && chem<=100){%>A+

<%}else if(math>80 && math<=90){%>A

<%}else if(math>70 && math<=80){%>B+

<%}else if(math>70 && math<=80){%>B

<%}else if(math>60 && math<=70){%>C+

<%}else if(math>50 && math<=60){%>C

<%}else if(math>=33 && math<=55){%>D

<%}else if(math>=0 && math<33){%><span color="red">Fail</span><%}%>
 
 </td>
</tr>

</table>	
	
	
<table border="1" width="40%">

<tr>
 <th>Total</th>
		      <th>Percentage(%)</th>
		      <th>Division</th>
		      <th>Result</th>
</tr>

<tr>
<th><%=tot%>

<%
if(tot<99 ||phy<33||chem<33||math<33){
%><span style="color: red">*</span><%} %>
</th>

<th><%=per%>%</th>
<th>

<%if(per>=60 && per<100){%>1<sup>st</sup>
<%}else if(per>=40 && per<60){%>2<sup>nd</sup>
<%}else if(per>0 && per<40){%>3<sup>rd</sup>
<%} %>

</th>

<th>
<%if(phy>=33 && chem>=33 && math>33){%><span style="color: green">Pass</span>
<%}else{ %>
 <span style="color: red">Fail</span>
 <%} %>
</th>
</tr>

</table>	
	
 <%} %>	
	
	
</table>
	
	
</form>	

</center>
<br><br>	
<%@include file="Footer.jsp"%>
</body>
</html>