package com.example.springbootshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @Author: Jzxxxxx
 * @Date: Created in 2019/7/24 0024下午 3:44
 */
@Controller
public class UserController {

    @RequestMapping("/hello")

    public String hello(){
//        System.out.println("hello");
        return "ok";
    }
    /**
     * 测试thymeleaf
     */
    @RequestMapping("/test")
    public String testThyeleaf(Model model){
        //数据存入model
        model.addAttribute("name","哈哈哈");
        //返回test.html
        return "test";
    }
    @RequestMapping("/add")
    public String add(){
//      System.out.println("hello");
        return "user/add.html";
    }
    @RequestMapping("/update")
    public String update(){
//      System.out.println("hello");
        return "user/update.html";
    }
    @RequestMapping("/tologin")
    public String tologin(){
//      System.out.println("hello");
        return "login.html";
    }
    @RequestMapping("/noAuth")
    public String noAuth(){
//        System.out.println("hello");
        return "noAuth.html";
    }
    /**
     * 登录逻辑处理
     */
    @RequestMapping("/login")
    public String login(String name,String password,Model model){
        /**
         * 使用shiro编写认证操作
         */
//        1、获取Subject
         Subject subject= SecurityUtils.getSubject();
//        2、封装用户数据
         UsernamePasswordToken token=new UsernamePasswordToken(name,password);
        //3、执行登录方法  通过token封装的用户名和密码传递给shriO 通过try/catch
        // 只要没有异常系统判断登录成功，否则失败
        try {
            //登录时 login会调用UserRealm中的doGetAuthenticationInfo方法进行验证
            subject.login(token);
            //登录成功 跳转到test.html
            return "test";
        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败 UnknownAccountException代表用户名不存在 可以利用login方法中添加的参数model在页面中返回提示 返回的提示在页面中需要捕获之后才能显示
            model.addAttribute("msg","用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e) {
            //e.printStackTrace();
            //登录失败 IncorrectCredentialsException代表密码错误 可以利用login方法中添加的参数model在页面中返回提示 返回的提示在页面中需要捕获之后才能显示
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }
}
