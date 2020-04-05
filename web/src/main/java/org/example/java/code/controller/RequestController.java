package org.example.java.code.controller;

import org.example.java.code.Jdbc.StudentHomeworkJdbc;
import org.example.java.code.Model.Homework;
import org.example.java.code.Model.Student;
import org.example.java.code.Model.StudentHomework;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class RequestController {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
    private static StudentHomeworkJdbc studentHomeworkJdbc = context.getBean("hwSystemJDBC",StudentHomeworkJdbc.class);

    @RequestMapping("/showStudent")
    @GetMapping
    public void showSt(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从数据库读取所有作业记录
        List<Student> list = studentHomeworkJdbc.showStudent();

        req.setAttribute("list",list);

        req.getRequestDispatcher("jsp/manageStudent.jsp").forward(req,resp);
    }
    @RequestMapping("/showHomework")
    @GetMapping
    public void showHw(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从数据库读取所有作业记录
        List<Homework> list = studentHomeworkJdbc.showHomework();

        req.setAttribute("list",list);

        req.getRequestDispatcher("jsp/queryStudentHomework.jsp").forward(req,resp);
    }
    @RequestMapping("/manageHomework")
    @GetMapping
    public void manageHw(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从数据库读取所有作业记录
        List<Homework> list = studentHomeworkJdbc.manageHomework();

        req.setAttribute("list",list);

        req.getRequestDispatcher("jsp/manageHomework.jsp").forward(req,resp);
    }
    @RequestMapping("/deleteStudent")
    @PostMapping
    public void showStPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String st_id = req.getParameter("st_id");
        boolean result = studentHomeworkJdbc.deleteStudent(st_id);//访问数据库

        req.setAttribute("isOK", result);   //用于判断是否提交成功
        req.setAttribute("type","deleteStudent");
        req.getRequestDispatcher("result.jsp").forward(req,resp);
    }
    @RequestMapping("/deleteHomework")
    @PostMapping
    public void showHwPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hw_id = req.getParameter("hw_id");
        boolean result = studentHomeworkJdbc.deleteHomework(hw_id);//访问数据库

        req.setAttribute("isOK", result);   //用于判断是否提交成功
        req.setAttribute("type","deleteHomework");
        req.getRequestDispatcher("result.jsp").forward(req,resp);
    }
    @RequestMapping("/queryStudentHomework")
    @GetMapping
    public void queryStHw(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hw_id = req.getParameter("hw_id");
        //从数据库读取指定作业id的所有记录
        List<StudentHomework> list = studentHomeworkJdbc.selectAll(hw_id);//访问数据库
        req.setAttribute("list",list);
        req.getRequestDispatcher("jsp/homeworkDetail.jsp").forward(req,resp); //展示数据
    }
    @RequestMapping("/addStudent")
    @PostMapping
    public void addSt(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");//设置编码，以防表单提交的内容乱码

        Student student = new Student();
        student.setstId(Long.parseLong(req.getParameter("st_id")));
        student.setstName(req.getParameter("st_name"));
        Date date = new Date();
        student.setstCreateTime(date);

        boolean result = studentHomeworkJdbc.addStudent(student);

        req.setAttribute("isOK", result);  //用来判断是否添加作业成功
        req.setAttribute("type","addStudent");
        req.getRequestDispatcher("result.jsp").forward(req,resp);
    }
    @RequestMapping("/addHomework")
    @PostMapping
    public void addHw(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");//设置编码，以防表单提交的内容乱码

        Homework homework = new Homework();

        homework.sethwId(Long.parseLong(req.getParameter("hw_id")));
        homework.sethwTitle(req.getParameter("hw_title"));
        homework.sethwContent(req.getParameter("hw_content"));
        Date date = new Date();
        homework.sethwCreateTime(date);

        boolean result = studentHomeworkJdbc.addHomework(homework);

        req.setAttribute("isOK", result);    //用来判断是否添加作业成功
        req.setAttribute("type","addHomework");
        req.getRequestDispatcher("result.jsp").forward(req,resp);
    }
    @RequestMapping("/submitGet")
    @GetMapping
    public void submitHwGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hw_id = req.getParameter("hw_id");
        //读取指定id的作业内容详细信息
        Homework homework = studentHomeworkJdbc.showHomeworkDetails(hw_id);//访问数据库
        req.setAttribute("homework",homework);
        req.getRequestDispatcher("jsp/submitHomework.jsp").forward(req,resp); //展示内容
    }
    @RequestMapping("/submitPost")
    @PostMapping
    public void submitHwPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");//设置编码，以防表单提交的内容乱码

        StudentHomework studentHomework = new StudentHomework();

        studentHomework.setstId(Long.parseLong(req.getParameter("st_id")));
        studentHomework.sethwId(Long.parseLong(req.getParameter("hw_id")));
        studentHomework.sethwTitle(req.getParameter("hw_title"));
        studentHomework.sethwContent(req.getParameter("hw_content"));
        studentHomework.setsubmitContent(req.getParameter("submit_content"));
        Date date = new Date();
        studentHomework.setsthwCreateTime(date);

        boolean result = studentHomeworkJdbc.addStudentHomework(studentHomework);

        req.setAttribute("isOK", result);   //用于判断是否提交成功
        req.setAttribute("type","addStudentHomework");
        req.getRequestDispatcher("result.jsp").forward(req,resp);
    }

}
