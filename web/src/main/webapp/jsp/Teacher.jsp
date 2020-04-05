<%--
  Created by IntelliJ IDEA.
  User: hp
  Date: 2020/3/9
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%--教师页--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>作业管理系统-老师</title>
</head>
<body>
<h2 style="text-align: center; color: #2418DA; font-family: '仿宋';">欢迎你，老师！</h2>
<h2 style="text-align: center; color: #2418DA; font-family: '仿宋';">你可以干这些事情</h2>
<div style="margin-left: 50px; border: 7px solid #13C6EB;">
    <ul style="line-height: 40px; font-family: '华文宋体'; text-align: center;">
        <li><a href="addHomework.jsp">添加作业</a></li>
        <li><a href="addStudent.jsp">添加学生</a></li>
        <li><a href="/showHomework">查询作业提交情况</a></li>
        <li><a href="/showStudent">管理学生</a></li>
        <li><a href="/manageHomework">管理作业</a></li>
    </ul>
</div>

<h2 style="font-family: '华文宋体'; text-align: center; color: #112CEB;"><a href="http://localhost:8080/">返回首页</a></h2>
</body>
</html>
