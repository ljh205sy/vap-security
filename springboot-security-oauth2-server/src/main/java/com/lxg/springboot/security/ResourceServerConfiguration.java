package com.lxg.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * 开启资源提供服务的配置  在默认情况下spring security oauth2的http配置 会被WebSecurityConfigurerAdapter的配置覆盖
 * 所以在继承WebSecurityConfigurerAdapter的类中，配置configure的优先级会高一些，也会覆盖resource中的
 * 资源服务器
 * @author wh1107066
 */
@Configuration
@EnableResourceServer
@Order(3)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		super.configure(resources);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.anonymous().disable()
				.httpBasic().and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/login","/logout").permitAll()
//		.antMatchers("/oauth/token").permitAll()
				.anyRequest().authenticated()
				.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.
//		anonymous().disable()
//		.requestMatchers().antMatchers("/user*/**")
//		.and().authorizeRequests().anyRequest().authenticated()
//		.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
//	}
}