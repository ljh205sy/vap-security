package com.lxg.springboot.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lxg.springboot.mapper.UserRepository;
import com.lxg.springboot.mapper.UserRoleRepository;

/**
 * Created by lxg
 * on 2017/2/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    public void test() throws Exception {
        //User user = userRepository.findByUsername("123");
        //List<UserRole> userRole = userRoleRepository.findByuid(1);


        List<String> list = userRoleRepository.findRoleName(1);
        System.out.println(list.get(0));
    }
}
