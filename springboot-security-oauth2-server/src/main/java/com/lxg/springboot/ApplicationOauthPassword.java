package com.lxg.springboot;

import com.lxg.springboot.config.MyUserDetailsService;
import com.lxg.springboot.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

/**
 * @author wh1107066
 */
@SpringBootApplication
public class ApplicationOauthPassword {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationOauthPassword.class, args);
        Map<String, UserService> beansOfType = context.getBeansOfType(UserService.class);
        for (String s : beansOfType.keySet()) {
            System.out.println(s + "" + beansOfType.get(s));
        }

        Map<String, UserDetailsService> beansOfType1 = context.getBeansOfType(UserDetailsService.class);
        for (String s : beansOfType1.keySet()) {
            System.out.println(s + " ------------" + beansOfType.get(s));
        }
    }
}
