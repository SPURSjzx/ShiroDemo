package com.example.springbootshiro.service;

import com.example.springbootshiro.Mapper.UserMapper;
import com.example.springbootshiro.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @Author: Jzxxxxx
 * @Date: Created in 2019/7/25 0025下午 3:14
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    @Override
    public User findById(Integer id) {
        return  userMapper.findById(id);
    }
}
