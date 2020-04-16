<%@ page import="java.util.List" %>
<%@ page import="org.example.java.code.Model.Homework" %>
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
    <title>作业管理系统-管理作业</title>
    <script>
        function show(hw_id) {
            let homework = document.getElementById('hwId')
            homework.setAttribute("value",hw_id)
            let sub = document.getElementById('sub')
            sub.submit()
        }
    </script>
</head>
<body>
<h2 style="text-align: center; color: #2418DA; font-family: '仿宋';">管理作业</h2>
<form id="sub" method="post" action="/deleteHomework">
    <input id="hwId" name="hw_id" type="hidden">
    <table align="center" width="960" border="1" style="line-height: 40px; font-family: '华文楷体';">
        <tr>
            <th>作业号</th>
            <th>作业标题</th>
            <th>创建时间</th>
            <th></th>
        </tr>
        <%
            List<Homework> list = (List<Homework>)request.getAttribute("list");
            if (list == null || list.size() <= 0){
                out.print("None data");
            }else {
                for (Homework homework : list) {
        %>
        <tr>
            <td><%=homework.gethwId()%></td>
            <td><%=homework.gethwTitle()%></td>
            <td><%=homework.gethwCreateTime()%></td>
            <td><input type="button" value="删除作业" onclick="show(<%=homework.gethwId()%>)"></td>
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
