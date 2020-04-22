package org.example.java.code.Jdbc;

import org.example.java.code.Model.Homework;
import org.example.java.code.Model.Student;
import org.example.java.code.Model.StudentHomework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentHomeworkJdbc {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
    private static DatabasePool databasePool = context.getBean("databasePool",DatabasePool.class);

    private static String driverName = "com.mysql.cj.jdbc.Driver";

    /**
     * 从student_homework表读取指定作业id 的所有记录，即某次作业的所有提交记录
     * @return 返回结果list
     */
    public  List<StudentHomework> selectAll(String hw_id) {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sqlString = "SELECT * FROM student_homework where hw_id="+hw_id;


        List<StudentHomework> list = new ArrayList<>();
        try (Connection connection = databasePool.getHikariDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sqlString)) {
                    //获取执行结果
                    while (resultSet.next()) {
                        StudentHomework studentHomework = context.getBean("studentHomework",StudentHomework.class);
                        studentHomework.setsthwId(resultSet.getLong("st_hw_id"));
                        studentHomework.setstId(resultSet.getLong("st_id"));
                        studentHomework.sethwId(resultSet.getLong("hw_id"));
                        studentHomework.sethwTitle(resultSet.getString("hw_title"));
                        studentHomework.sethwContent(resultSet.getString("hw_content"));
                        studentHomework.setsubmitContent(resultSet.getString("submit_content"));
                        studentHomework.setsthwCreateTime(resultSet.getTimestamp("st_hw_create_time"));
                        list.add(studentHomework);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 向homework表添加作业记录
     * @param homework  将要添加的homework
     * @return true=>成功否则失败
     */
    public boolean addHomework(Homework homework){

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sqlString = "insert into homework (hw_id,hw_title,hw_content,hw_create_time) values(?,?,?,?)";

        int resultSet = 0;
		
		PreparedStatement ps = null;
        Connection connection = null;
        try {
            connection = databasePool.getHikariDataSource().getConnection();
            connection.setAutoCommit(false);  //相当于 set autocommit=0;
			ps = connection.prepareStatement(sqlString);
            //获取执行结果
            ps.setLong(1,homework.gethwId());
            ps.setString(2,homework.gethwTitle());
            ps.setString(3,homework.gethwContent());
            ps.setTimestamp(4,new Timestamp(homework.gethwCreateTime().getTime()));
            resultSet = ps.executeUpdate();
			connection.commit();  //executeUpdate()语句若不出错，提交事务。
        } catch (Exception e) {
            try {
                //如果在创建连接对象过程中爆发异常，connection就会为null。若不加if语句，就会出现空指针异常。
                if(connection!=null) {
                    connection.rollback(); //executeUpdate()语句若出错，回滚，SQL语句执行不成功。
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                if (ps!=null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection!=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultSet > 0;
    }

    /**
     * 向student表添加学生
     * @param student 将要添加的student
     * @return true成功否则失败
     */
    public boolean addStudent(Student student){

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sqlString = "insert into student (st_id,st_name,st_create_time) values(?,?,?)";

        int resultSet = 0;
		
		PreparedStatement ps = null;
        Connection connection = null;
        try {
            connection = databasePool.getHikariDataSource().getConnection();
            connection.setAutoCommit(false);  //相当于 set autocommit=0;
			ps = connection.prepareStatement(sqlString);
            //获取执行结果
            ps.setLong(1,student.getstId());
            ps.setString(2,student.getstName());
            ps.setTimestamp(3,new Timestamp(student.getstCreateTime().getTime()));
            resultSet = ps.executeUpdate();
			connection.commit();  //executeUpdate()语句若不出错，提交事务。
        } catch (Exception e) {
            try {
                //如果在创建连接对象过程中爆发异常，connection就会为null。若不加if语句，就会出现空指针异常。
                if(connection!=null) {
                    connection.rollback(); //executeUpdate()语句若出错，回滚，SQL语句执行不成功。
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                if (ps!=null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection!=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultSet > 0;
    }

    /**
     *从homework表读取所有作业记录
     * @return  结果list
     */
    public List<Homework> showHomework(){

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sqlString = "SELECT * FROM homework";

        List<Homework> list = new ArrayList<>();
        try (Connection connection = databasePool.getHikariDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sqlString)) {
                    //获取执行结果
                    while (resultSet.next()) {
                        Homework homework = context.getBean("homework",Homework.class);
                        homework.sethwId(resultSet.getLong("hw_id"));
                        homework.sethwTitle(resultSet.getString("hw_title"));
                        homework.sethwContent(resultSet.getString("hw_content"));
                        list.add(homework);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    /**
     *从homework表读取所有作业记录
     * @return  结果list
     */
    public List<Homework> manageHomework(){

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sqlString = "SELECT * FROM homework";

        List<Homework> list = new ArrayList<>();
        try (Connection connection = databasePool.getHikariDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sqlString)) {
                    //获取执行结果
                    while (resultSet.next()) {
                        Homework homework = context.getBean("homework",Homework.class);
                        homework.sethwId(resultSet.getLong("hw_id"));
                        homework.sethwTitle(resultSet.getString("hw_title"));
                        homework.sethwCreateTime(resultSet.getDate("hw_create_time"));
                        list.add(homework);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    /**
     *从student表读取所有作业记录
     * @return  结果list
     */
    public List<Student> showStudent(){

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sqlString = "SELECT * FROM student";

        List<Student> list = new ArrayList<>();
        try (Connection connection = databasePool.getHikariDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sqlString)) {
                    //获取执行结果
                    while (resultSet.next()) {
                        Student student = context.getBean("student",Student.class);
                        student.setstId(resultSet.getLong("st_id"));
                        student.setstName(resultSet.getString("st_name"));
                        student.setstCreateTime(resultSet.getDate("st_create_time"));
                        list.add(student);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    /**
     * 从student表读取指定id删除该学生
     * @param st_id 学生id
     * @return true成功否则失败
     */
    public boolean deleteStudent(String st_id){

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sqlString = "DELETE FROM student WHERE st_id="+st_id;

        int resultSet = 0;
        try (Connection connection = databasePool.getHikariDataSource().getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement(sqlString)) {
                    resultSet = ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet > 0;
    }

    /**
     * 从homework表读取指定id删除该学生
     * @param hw_id 作业id
     * @return true成功否则失败
     */
    public boolean deleteHomework(String hw_id){

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sqlString = "DELETE FROM homework WHERE hw_id="+hw_id;

        int resultSet = 0;
        try (Connection connection = databasePool.getHikariDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sqlString)) {
                resultSet = ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet > 0;
    }

    /**
     * 从homework表读取指定id的作业详细内容
     * @param hw_id 作业id
     * @return 作业对象homework
     */
    public Homework showHomeworkDetails(String hw_id){

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sqlString = "SELECT * FROM homework WHERE hw_id=" + hw_id;

        Homework homework = context.getBean("homework",Homework.class);
        try (Connection connection = databasePool.getHikariDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sqlString)) {
                    //获取执行结果
                    while (resultSet.next()) {
                        homework.sethwId(resultSet.getLong("hw_id"));
                        homework.sethwTitle(resultSet.getString("hw_title"));
                        homework.sethwContent(resultSet.getString("hw_content"));
                        homework.sethwCreateTime(resultSet.getTimestamp("hw_create_time"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return homework;
    }

    /**
     * 向student_homework表提交作业
     * @param studentHomework 作业
     * @return true成功否则失败
     */
    public boolean addStudentHomework(StudentHomework studentHomework){

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sqlString = "insert into student_homework (st_id,hw_id," +
                "hw_title,hw_content,submit_content,st_hw_create_time) values(?,?,?,?,?,?)";

        int resultSet = 0;
		
		PreparedStatement ps = null;
        Connection connection = null;
        try {
            connection = databasePool.getHikariDataSource().getConnection();
            connection.setAutoCommit(false);  //相当于 set autocommit=0;
			ps = connection.prepareStatement(sqlString);
            //获取执行结果
            ps.setLong(1,studentHomework.getstId());
            ps.setLong(2,studentHomework.gethwId());
            ps.setString(3,studentHomework.gethwTitle());
            ps.setString(4,studentHomework.gethwContent());
            ps.setString(5,studentHomework.getsubmitContent());
            ps.setTimestamp(6,new Timestamp(studentHomework.getsthwCreateTime().getTime()));
            resultSet = ps.executeUpdate();
			connection.commit();  //executeUpdate()语句若不出错，提交事务。
        } catch (Exception e) {
            try {
                //如果在创建连接对象过程中爆发异常，connection就会为null。若不加if语句，就会出现空指针异常。
                if(connection!=null) {
                    connection.rollback(); //executeUpdate()语句若出错，回滚，SQL语句执行不成功。
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                if (ps!=null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection!=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultSet > 0;
    }

}
