package com.lxg.springboot.service.impl;

import com.lxg.springboot.entity.User;
import com.lxg.springboot.mapper.UserRepository;
import com.lxg.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author wh1107066
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        Optional<User> byId = userRepository.findById(id);
        User user = byId.get();
        return user;
    }

}
