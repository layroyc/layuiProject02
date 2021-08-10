package com.hp.service;

import com.hp.bean.User;
import com.hp.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    //登录
    public Map login(String username, String password, HttpServletRequest request){
        Map map = new HashMap();
        //service 层要调用dao层
        UserDao userDao = new UserDao();
        User userFromDB = userDao.login(username, password);
        if(null == userFromDB){
            //没查出来，即：账户名或密码不正确
            map.put("code",4001);
            map.put("msg","账户或密码不正确");
            return map;
        }else{
           //当登陆成功后，把信息放入到session作用域中
            HttpSession session = request.getSession();
            session.setAttribute("user",userFromDB);
            map.put("code",0);
            map.put("msg","登陆成功");
            return map;
        }
    }
}
