package com.example.springbootshiro.service;

import com.example.springbootshiro.domain.User;

/**
 * @ClassName: UserService
 * @Description: TODO
 * @Author: Jzxxxxx
 * @Date: Created in 2019/7/25 0025下午 3:12
 */
public interface UserService {

   public User findByName(String name);

   public User findById(Integer id);
}
