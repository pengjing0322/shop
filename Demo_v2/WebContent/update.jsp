<%@ page language="java" import="com.beans.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/top.jsp" %>
	<form action='updateUserById.action?flag=updateUserById' method='post'>
		<input type='hidden' name='id' value='${user.id }'/>
		<input type='hidden' name='pageIndex' value='${pageIndex}'/>
		<input type='text' name='userName' value='${user.userName }'/><br>
		<input type='text' name='password' value='${user.password }'/><br>
		<input type='submit' value='确定修改'/>
	</form>
	<label style='color:red'>${msg }</label>
</body>
</html>