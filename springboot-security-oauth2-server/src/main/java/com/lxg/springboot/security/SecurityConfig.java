package com.lxg.springboot.security;

import com.lxg.springboot.config.DefaultPasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * WebSecurityAdaptConfig的配置类会覆盖ResourceServerConfiguration的配置.
 *
 * WebSecurityConfigurerAdapter = @Order(100)
 *ResourceServerConfiguration = @Order(3)， 没有设置就是最大值
 * 如果优先级的顺序颠倒了，会出现不准许访问。
 *
 *
 * 注意4：此类中的configure(HttpSecurity http)会覆盖ResourceServerConfig中的配置
 * Time:16:42
 * ProjectName:Mirco-Service-Skeleton
 * @author wh1107066
 */
@Configuration   //开启三种可以在方法上面加权限控制的注解
@EnableWebSecurity
@Order(10)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Import(DefaultPasswordConfig.class)
// 能使用springsecurity的注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserDetailsService userDetailsService;
    /**
     * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     * @return 认证管理对象
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 全局用户信息
     */
//    @Autowired
//    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off      formLogin().and().      httpBasic().and()
        http.httpBasic().and()
                .authorizeRequests()
                .antMatchers("/login","/oauth/*","/oauth/token", "/oauth/authorize", "/oauth/check_token").permitAll()
//                .antMatchers("/oauth/*").permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/api-docs/**").permitAll()
                // antMatchers("/test").permitAll().
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
        // @formatter:on
    }

    /**
     * 权限管理器  AuthorizationServerConfigurerAdapter认证中心需要的AuthenticationManager需要
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("======================" + passwordEncoder.encode("123456"));
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }




}

