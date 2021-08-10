package com.hp.dao;

import com.hp.bean.User;
import com.hp.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//dao 层 应该是接口，可以使用aop  目前不用aop，就可以直接使用写成类
//dao层 如何 和 数据库 做对接 知识点叫做jdbc
//很多框架 都是 基于这个 jdbc 来的
//要连接数据库，就需要用到刚刚的DBHelper.getConnection()
//这个对象可以负责 和 mysql连接
public class UserDao {
    //登录  select * from t_user where username=? and password =?;
    //登录
    public User login(String username,String password){
        User user =null;
        //创建链接
        Connection conn=DBHelper.getConnection();
        //创建sql 语句
        String sql="select * from t_user where username=? and password=?";
        //获取预编译对象
        PreparedStatement ps=null;
        //执行预编译对象
        ResultSet rs=null;
        try {
            //获取预编译对象
            ps=conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            //执行预编译对象
            rs=ps.executeQuery();
            if(rs.next()){
                user=new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    //增删改查
    //查询 select * from t_user
    public List<User> selectAll(){
        ArrayList<User> users = new ArrayList<>();
        //1.创建出 连接对象
        Connection connection = DBHelper.getConnection();
        //2.创建出SQL语句
        String sql = "select * from t_user";
        //3.使用连接对象 获取 预编译对象
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
           //4.执行预编译，得到结果集
            rs = ps.executeQuery();
            //5.遍历结果集
            while (rs.next()){
                System.out.println("username = " + rs.getString("username"));
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    //新增
    public int addUser(User user){
        //1.创建出 连接对象
        Connection conn = DBHelper.getConnection();
        //2.创建出SQL语句
        String sql = "insert into t_user values (null,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        int i = 0;
        try {
            //3.使用连接对象 获取 预编译对象
            ps = conn.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getReal_name());
            ps.setString(4,user.getImg());
            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());
            ps.setString(7,user.getCreate_time());
            ps.setString(8,user.getModify_time());
            //4.执行预编译对象
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    //删除
    public int del(int id){
        //1.创建出 连接对象
        Connection conn = DBHelper.getConnection();
        //2.创建出SQL语句
        String sql = "delete from t_user where id=?";
        //3.创建preparedStatement,执行sql
        PreparedStatement ps = null;
        int count = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    //修改
    public int update(User user){
        //1.创建出 连接对象
        Connection conn = DBHelper.getConnection();
        //2.创建出SQL语句
        String sql = "update t_user set username=?,password=?,real_name=?,img=?,type=?,is_del=?,create_time=?,modify_time=? where id=?";
        //3.创建preparedStatement,执行sql
        PreparedStatement ps = null;
        int i = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getReal_name());
            ps.setString(4,user.getImg());
            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());
            ps.setString(7,user.getCreate_time());
            ps.setString(8,user.getModify_time());
            ps.setInt(9,user.getId());
            i= ps.executeUpdate();
            System.out.println("成功修改"+i+"条数据");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    public static void main(String[] args) {
        //全查
        UserDao userDao = new UserDao();
        /*List<User> users = userDao.selectAll();
        for (User user:users) {
            System.out.println("user = " + user);
        }*/

        //新增
        /*User user = new User();
        user.setUsername("caicai");
        user.setPassword("123456");
        user.setReal_name("蔡蔡");
        user.setImg("xxx");
        user.setType(1);
        user.setIs_del(1);
        user.setCreate_time("1998-08-02");
        user.setModify_time("1998-08-02");
        int i = userDao.addUser(user);
        System.out.println("i = " + i);*/

        //删除
        /*Scanner sc = new Scanner(System.in);
        System.out.println("请输入要删除的序号：");
        int id = sc.nextInt();
        int i = userDao.del(id);
        if(i>0){
            System.out.println("恭喜你删除成功！");
        }else{
            System.out.println("删除失败！");
        }*/

       /* //修改
        Scanner sc= new Scanner(System.in);
        System.out.println("你选择了要修改的id：");
        int id = sc.nextInt();

        System.out.println("请输入要修改的用户名：");
        String username = sc.next();
        System.out.println("请输入要修改的用户密码：");
        String password = sc.next();
        System.out.println("请输入要修改的真是姓名：");
        String real_name = sc.next();
        System.out.println("请输入要修改的用户图像：");
        String img = sc.next();
        System.out.println("请输入要修改的用户类型:  1 管理员   2 业务员：");
        int type = sc.nextInt();
        System.out.println("请输入要修改的 其是否有效: 1 有效   2 无效：");
        int is_del = sc.nextInt();
        System.out.println("请输入要修改其创建的时间：");
        String create_time = sc.next();
        System.out.println("请输入要修改的修改时间：");
        String modify_time = sc.next();
        User u = new User(id,username,password,real_name,img,type,is_del,create_time,modify_time);
        System.out.println(u.toString());
        int i = userDao.update(u);
        if(i>0){
            System.out.println("恭喜你修改成功！");
        }else{
            System.out.println("修改失败！");
        }*/

       //登陆的测试
        User abc = userDao.login("abc", "123456");
        System.out.println("abc = " + abc);
    }
}
