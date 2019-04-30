<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分类展示列表</title>
</head>
<body>



<div style="width:1000px;margin:20px auto;text-align: center">
	<a href="find">查找教练</a>
    <table align='center' border='1' cellspacing='0'>
        <tr>
            <td>id</td>
            <td>name</td>
         
            <td>编辑</td>
            <td>删除</td>
        </tr>
        <c:forEach items="${page.content}" var="u" varStatus="st">
            <tr>
                <td>${u.id}</td>
                <td>${u.username}</td>
               
                <td><a href="editUser?id=${u.id}">编辑</a></td>
                <td><a href="deleteUser?id=${u.id}">删除</a></td>
            </tr>
        </c:forEach>
         
    </table>
    <br>
    <div>
                <a href="?pageNo=0">[首  页]</a>  <!-- 这是相对路径的写法。 前面没有斜线就是相对当前路径加上这个地址。-->
            <c:if test="${page.number-1>-1}">
                <a href="?pageNo=${page.number-1}">[上一页]</a>
            </c:if>
            <c:if test="${page.number+1<page.totalPages}">
                <a href="?pageNo=${page.number+1}">[下一页]</a>
            </c:if>
                <a href="?pageNo=${page.totalPages-1}">[末  页]</a>
    </div>
    <br>
    <form action="addUser" method="post">
        添加分类:
        name: <input name="name"> <br>
       <button type="submit">提交</button>
     
    </form>
</div>
</body>
</html>