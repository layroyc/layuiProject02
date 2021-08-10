package com.hp.controller;

import com.alibaba.fastjson.JSONObject;
import com.hp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LoginServlet",urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收登陆过来的 3个参数
        //1.修正编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");

        //2.接收前端传过来的 3个参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String code = req.getParameter("code");

        //3.登录的时候，首先要验证 验证码是否正确
        //3.1 获取 后台的验证码
        HttpSession session = req.getSession();
        String codeFromSession = (String) session.getAttribute("code");
        System.out.println("codeFromSession = " + codeFromSession);
        if(!codeFromSession.equals(code)){
            //验证错误 注意 前面有个 ！
            //向前端 输入一段 json，告知前端 验证码错误
            PrintWriter writer = resp.getWriter();
            Map map = new HashMap();
            map.put("code",400);
            map.put("msg","验证码不正确");
            //把map 变换为 json
            String jsonString = JSONObject.toJSONString(map);
            writer.print(jsonString);
            writer.close();
        }else{
            //验证码 正确，判断账户和密码
            System.out.println("验证码正确");
            //就需要 service/dao 层判断
            UserService userService = new UserService();
            Map map = userService.login(username, password, req);
            String jsonString = JSONObject.toJSONString(map);
            PrintWriter writer = resp.getWriter();
            writer.print(jsonString);
            writer.close();
        }

    }
}
