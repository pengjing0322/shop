<%@ page language="java" import="java.util.*,com.beans.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	table,td,th{
		border:1px solid blue;
		border-collapse:collapse
	}
</style>
<script type="text/javascript">
		function test1(){
			var pageIndex=document.getElementById("pageIndex").value;
			if(parseInt(pageIndex)<=0 || parseInt(pageIndex)>${pageInfo.pageCount}){
				alert("输入的不合法");
				document.getElementById("pageIndex").value="";
				return;
			}
			window.location.href="${pageContext.request.contextPath }/queryAllUser.action?flag=queryAllUser&pageIndex="+pageIndex;
		}
		function test2(){
			var sel=document.getElementById("sel");
			var pageIndex=sel.options[sel.selectedIndex].text;
			window.location.href="${pageContext.request.contextPath }/queryAllUser.action?flag=queryAllUser&pageIndex="+pageIndex;
		}
</script>
</head>
<body>
	<%@ include file="/top.jsp" %>
	<table width='80%'>
		<tr>
			<th>ID</th><th>用户名</th><th>密码</th><th>操作</th>
		</tr>
		<c:forEach var="user" items="${userList }">
			<tr>
				<td>${user.id }</td><td>${user.userName }</td><td>${user.password }</td>
				<td><a href='deleteUserById.action?flag=deleteUserById&id=${user.id }&pageIndex=${pageInfo.pageIndex}' onclick="return confirm('确定删除?')">删除</a> || <a href='queryUserById.action?flag=queryUserById&id=${user.id }&pageIndex=${pageInfo.pageIndex}'>修改</a></td>
			</tr>
		</c:forEach>
	</table>
	共有${pageInfo.rowCount } 条记录  当前第${pageInfo.pageIndex }页 共有${pageInfo.pageCount }页
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<c:choose>
		<c:when test="${pageInfo.hasPre }">
			<a href="${pageContext.request.contextPath }/queryAllUser.action?flag=queryAllUser&pageIndex=1">首页</a><a href="${pageContext.request.contextPath }/queryAllUser.action?flag=queryAllUser&pageIndex=${pageInfo.pageIndex-1 }">上一页</a>
		</c:when>
		<c:otherwise>
			首页  上一页
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${pageInfo.hasNext}">
			<a href="${pageContext.request.contextPath }/queryAllUser.action?flag=queryAllUser&pageIndex=${pageInfo.pageIndex+1 }">下一页</a><a href="${pageContext.request.contextPath }/queryAllUser.action?flag=queryAllUser&pageIndex=${pageInfo.pageCount }">尾页</a>
		</c:when>
		<c:otherwise>
			下一页  尾页
		</c:otherwise>
	</c:choose>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="text" size="2" id="pageIndex" value="${pageInfo.pageIndex }"/>
	<input type="button" onclick="test1();" value="转到"/>
	<label style='color:red'>${msg }</label>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<select id="sel" onchange="test2();">
		<c:forEach var="i" begin="1" end="${pageInfo.pageCount }">
			<c:if test="${i==pageInfo.pageIndex }">
				<option selected="selected">${i }</option>
			</c:if>
			<c:if test="${i!=pageInfo.pageIndex }">
				<option>${i }</option>
			</c:if>
			
		</c:forEach>
		
	</select>
</body>
</html>