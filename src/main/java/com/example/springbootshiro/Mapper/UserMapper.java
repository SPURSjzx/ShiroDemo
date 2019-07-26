package com.example.springbootshiro.Mapper;

import com.example.springbootshiro.domain.User;

/**
 * @ClassName: UserMapper
 * @Description: TODO
 * @Author: Jzxxxxx
 * @Date: Created in 2019/7/25 0025下午 2:56
 */
public interface UserMapper {

    public User findByName(String name);

    public User findById(Integer id);

}
