<%@ page import="java.util.List" %>
<%@ page import="org.example.java.code.Model.Student" %>
<%--
  Created by IntelliJ IDEA.
  User: hp
  Date: 2020/4/4
  Time: 23:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>作业管理系统-管理学生</title>
    <script>
        function show(st_id) {
            let student = document.getElementById('studentId')
            student.setAttribute("value",st_id)
            let sub = document.getElementById('sub')
            sub.submit()
        }
    </script>
</head>
<body>
<h2 style="text-align: center; color: #2418DA; font-family: '仿宋';">管理学生</h2>
<form id="sub" method="post" action="/deleteStudent">
    <input id="studentId" name="st_id" type="hidden">
    <table align="center" width="960" border="1" style="line-height: 40px; font-family: '华文楷体';">
        <tr>
            <th>学号</th>
            <th>姓名</th>
            <th>创建时间</th>
            <th></th>
        </tr>
        <%
            List<Student> list = (List<Student>)request.getAttribute("list");
            if (list == null || list.size() <= 0){
                out.print("None data");
            }else {
                for (Student student : list) {
        %>
        <tr>
            <td><%=student.getstId()%></td>
            <td><%=student.getstName()%></td>
            <td><%=student.getstCreateTime()%></td>
            <td><input type="button" value="删除学生" onclick="show(<%=student.getstId()%>)"></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</form>
<h2 style="font-family: '华文宋体'; text-align: center; color: #112CEB;"><a href="jsp/Teacher.jsp">返回老师操作界面</a></h2>
</body>
</html>
