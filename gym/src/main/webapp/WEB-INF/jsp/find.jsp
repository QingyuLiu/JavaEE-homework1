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
<div style="width:500px;margin:20px auto;text-align: center">
<form action="find" method="post">

  	输入查询用户的ID:<input type="text" name="id">
		<br><br>

          <button type="submit">提交</button>
</form>
<br>
	
    <table align='center' border='1' cellspacing='0'>
        <tr>
            <td>id</td>
            <td>name</td>
        </tr>
        <c:forEach items="${page.content}" var="u" varStatus="st">
            <tr>
                <td>${u.id}</td>
                <td>${u.username}</td>
             
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
    </div>
</body>
</html>