package com.example.springbootshiro.Shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: ShiroConfig
 * @Description: TODO
 * @Author: Jzxxxxx
 * @Date: Created in 2019/7/25 0025上午 8:58
 */
@Configuration
public class ShiroConfig {
    /**
     * 创建ShrioFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //首先设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //添加shiro的内置过滤器 实现对URL的拦截过滤
        /**
         * shiro内置过滤器 可以实现权限相关的拦截器
         *    常用的有
         *    anon：无需登录（认证）可以访问
         *    authc：必须认证登录才可以访问
         *    user：如果使用了rememberMe的功能可以直接访问
         *    perms：该资源必须得到资源权限才可以访问
         *    role：该资源必须得到角色授权才可以访问
         */
        //建立一个map集合用来存放字符串 存放需要拦截的url等
        Map<String,String> filterMap=new LinkedHashMap<String,String>();
        /**
         * filterMap.put("/add","authc");
         * filterMap.put("/update","authc");
         * 类似于上面的两行代码 如果有几个同一目录下的url使用同一种拦截器进行拦截 可以进行通配
         */
        filterMap.put("login","anon");
        filterMap.put("/add","authc");
        filterMap.put("/update","authc");
        //filterMap.put("/user/*","authc");

        //授权过滤器
        //当授权拦截后 shiro会自动跳转到未授权页面  401
        filterMap.put("/add","perms[user:add]");
        filterMap.put("/update","perms[user:update]");

        //我们可以修改跳转的页面 shiro默认是跳转到login.jsp登录页面的
        shiroFilterFactoryBean.setLoginUrl("/tologin");
        //设置未授权页提示页面  默认是到401页面   需要设置请求跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }
    /**
     * 创建DefaultWebSecurity
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
       //关联realm
        securityManager.setRealm(userRealm);
        return  securityManager;
    }
    /**
     * 创建Realm
     */
    @Bean(name="userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }
//    在ShrioConfig中配置ShiroDoalect，用于thymeleaf和shiro标签配合使用
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
