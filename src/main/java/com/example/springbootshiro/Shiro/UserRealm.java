package com.example.springbootshiro.Shiro;

import com.example.springbootshiro.domain.User;
import com.example.springbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: UserRealm
 * @Description: TODO
 * @Author: Jzxxxxx
 * @Date: Created in 2019/7/25 0025上午 9:09
 */
public class UserRealm extends AuthorizingRealm{
    /**
     * 执行授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权");
        //给资源进行授权
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //添加资源的授权字符串     在shrioconfig设置的授权字符串
         /*info.addStringPermission("user:add");*/
        /*到数据库查询当前登录用户的授权字符串*/
        //要根据用户id查询授权字符串 首先要获取当前登陆用户的id才可以
        Subject subject= SecurityUtils.getSubject();
        //通过perincipal拿到user 即在登录过程中设置的参数1
        Map<String,String> userMap=new HashMap<>();
        System.out.println("-----------------------");
        User user=(User)subject.getPrincipal();
        System.out.println("-----------------------");
        //通过拿到的user的id去查询
        User dbuser=userService.findById(user.getId());
        //向info中加入从数据库中查到的授权字符串
        info.addStringPermission(dbuser.getPerms());
        //取出登陆用户的用户名
        String uname=dbuser.getName();
        System.out.println(uname);
        return info;
    }
    @Autowired
    UserService userService;

    /**
     * 执行认证逻辑
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行逻辑认证");
        //假定用户名和密码
        /*String name="aaaa";
        String password="123";*/
        //编写shrio判断逻辑，判断用户名和密码是否正确
//        1、判断用户名  将方法中的token转化为页面中传过来的参数
        UsernamePasswordToken token1=(UsernamePasswordToken)token;
        User user=userService.findByName(token1.getUsername());
        //使用传递过来的用户名与数据库中的name进行对比
//        String n=user.getName();
//        System.out.println(n);
        if(user==null){
            //用户名不存在  我们定义返回一个null shiro底层会抛出一个UnknownAccountException
            // 即与UserController中login方法抛出的异常一致
            return null;
        }
        //判断密码                        参数1: principal 需要返回给login的一些数据 可以留空
        //                                 参数2: 数据库中的密码
        //                                   参数3: shiro的名字 可以留空
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
