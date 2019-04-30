<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改分类的页面</title>
</head>
<body>
    <div style="margin:0px auto; width:500px">
     
    <form action="updateUser" method="post"> 
        name: <input name="username" value="${u.username}"> <br>
        <input name="id" type="hidden" value="${u.id}">
        <button type="submit">提交</button>
    </form>

　　</div>
</body>
</html>