package com.lxg.springboot.service;

import com.lxg.springboot.entity.User;

import java.util.List;

/**
 * @author wh1107066
 */
public interface UserService {

    User findByUsername(String name);

    List<User> findAll();
    
    User findById(Integer id);

}
