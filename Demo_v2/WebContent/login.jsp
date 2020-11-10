<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8" isErrorPage="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String userName="";
		String password="";
		Cookie[] cArray=request.getCookies();
		if(cArray!=null){
			for(int i=0;i<cArray.length;i++){
				Cookie myC=cArray[i];
				if(myC.getName().equals("userName")){
					userName=myC.getValue();
				}
				if(myC.getName().equals("password")){
					password=myC.getValue();
				}
			}
		}
	%>
	<form action="login.do?flag=login" method="post">
		用户名：<input type="text" name="userName" value="<%=userName%>"/><br>
		密码：<input type="text" name="password" value="<%=password%>"/><br>
		<input type="submit" value="登陆"/><br>
		记住密码：<input type="checkbox" name="cookie">
	</form>
	
	<!-- 如何接受值 -->
	<a href="reg.jsp">注册</a>
	${msg }
	<c:if test="${param.f=='1' }">
		对不起，请登录
	</c:if>
	<c:if test="${param.f=='2' }">
		注销成功，请登陆
	</c:if>
</body>
</html>