package com.hp.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CodeServlet", urlPatterns = "/CodeServlet")
public class CodeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //使用hutool 工具 创建验证s码
       //定义图形验证码的长、宽、验证码字符数、干扰元素个数
       //圆圈干扰验证码
        // CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);

       //createShearCaptcha 扭曲干扰验证码
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);

        //拿到验证码
        String code = captcha.getCode();
        //获取session
        HttpSession session = req.getSession();
        //把验证码 放到session作用域中
        session.setAttribute("code",code);
        //5.把验证码 发送到 浏览器
        ServletOutputStream outputStream = resp.getOutputStream();
        captcha.write(outputStream);
        outputStream.close();


        //定义图形验证码的长和宽
        //线性干扰验证码
       /* LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        //拿到验证码
        String code = lineCaptcha.getCode();
        //获取session
        HttpSession session = req.getSession();
        //把验证码 放到session作用域中
        session.setAttribute("code",code);
        //5.把验证码 发送到 浏览器
        ServletOutputStream outputStream = resp.getOutputStream();
        lineCaptcha.write(outputStream);
        outputStream.close();*/
    }

}
