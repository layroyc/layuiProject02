package com.hp.service;

import com.hp.bean.User;

import java.util.HashMap;
import java.util.Map;

public class LoginPwd {
    //参数的设置
    private static int maxTime = 5; //连续5次后
    private static double hour = 1; //一小时内不可登录。

    /**
     * 登录错误5次，一小时后不可登录
     * @param loginId 登录id
     * @param password 密码
     * @param isJiaMi 对密码进行加密处理
     * @return
     * @throws DataAccessException
     */
    public Map<String, Object> validateByLoginIdLoginErr(String loginId, String password,
                                                         boolean isJiaMi) throws DataAccessException {
        Map<String,Object> stringMap=new HashMap<>();
        return stringMap;
        Map<String, Object> resultMap = new HashMap(2);

        User user = getByLoginId(loginId);    //一个通过登录id获取用户实体类的方法，文中无源码
        String encodePass = "";
        if (isJiaMi && null != isJiaMi) {
            encodePass = encrypt.encode(password);  //对密码进行加密处理，文中无源码
        }

        if(null != user){    //如果登录id确实存在
            if(user.getPassword().equals(encodePass)){ //并且密码输入正确
                if(this.checkWhenPwdOk(user)){    //校验该用户是否可以登录
                    this.setErrZero(user);    //如果可以确实的登录，则对表中错误登录次数置零
                    resultMap.put("user", user);
                    return resultMap;
                }else {
                    resultMap.put("extMsg", "errMax");    //前台错误信息展现由上层代码实现。
                    return resultMap;
                }
            }else {
                if(this.checkErrNum(user)){  //密码错误时的一系列操作
                    //返回true时，错误次数超过了5次，需要返回特定的错误信息
                    resultMap.put("extMsg", "errMax");
                    return resultMap;
                }else {
                    //错误次数没有超过5次，返回一个空map，交由上层处理
                    return resultMap;
                }
            }
        }
        return resultMap;
    }

    private void setErrZero(User user){
        //将指定id的错误次数置零
        //client是操作数据库的方法，可以认为是SqlMapClientBuilder.buildSqlMapClient(Resources.getResourceAsReader("config/SqlMap.xml"));  这种东西。
        this.client.update("login.err.updateForOk", user);
    }

    /**
     * 即使密码正确，也不能登录
     * @param user
     * @return false 处于锁定中，无法登陆  true 可以正常登录
     */
    private boolean checkWhenPwdOk(User user){
        Map<String, String> daoMap = new HashMap();
        daoMap.put("loginId", user.getLoginId() + "");
        daoMap.put("maxTime", maxTime + "");
        daoMap.put("hour", hour + "");
        String currentNumStr = (String) this.client.queryForObject("login.err.checkWhenPwdOk", daoMap);
        if(currentNumStr == null || currentNumStr.length() == 0){
            return true;    //err表中无数据，可以登录成功
        }
        if(Integer.valueOf(currentNumStr) >= maxTime){    //超过5次
            return false;
        }else {
            return true;
        }
    }

    //密码错误时的方法
    private boolean checkErrNum(User user){
        Map<String, String> daoMap = new HashMap();
        daoMap.put("loginId", user.getLoginId() + "");
        daoMap.put("maxTime", maxTime + "");
        daoMap.put("hour", hour + "");
        String currentNumStr = (String) this.client.queryForObject("login.err.getCurrentNum", daoMap);

        if(currentNumStr == null || currentNumStr.length() == 0){
            //错误信息表与用户表并不是同步的，如果是新建的用户，err表中将没有对应数据，需要插入一条新数据
            this.client.insert("login.err.insertUser", daoMap);
            currentNumStr = "0";
        }

        int currentNum = Integer.valueOf(currentNumStr);
        if(currentNum >= maxTime){
            //若次数超过了，将不会修改登陆时间。那样做会导致一小时的校验错误
            return true;
        }
        //执行+1或=1的操作
        this.client.update("login.err.updateForErr", daoMap);

        return false;
    }
}
