package com.lxg.springboot.config;

/**
 * @author liujinhui
 * date 2021/6/12 10:26
 */

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码工具类
 * @author wh1107066
 */
public class DefaultPasswordConfig {
    /**
     * 装配BCryptPasswordEncoder用户密码的匹配
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder()	{
        return new BCryptPasswordEncoder();
    }
}
