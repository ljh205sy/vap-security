package com.vrv.vap.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

/**
 * @author wh1107066
 * @date 2021/6/21 11:13
 */
@Configuration
@EnableResourceServer
@Order(3)
public class MyResourceConfigurerAdapter extends ResourceServerConfigurerAdapter {
    private static final String[] AUTH_WHITELIST= {"/v2/api-docs", "/swagger/**", "/doc.html"};

    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.expressionHandler(expressionHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        for (String au : AUTH_WHITELIST) {
            http.authorizeRequests().antMatchers(au).permitAll();
        }
        http.authorizeRequests().anyRequest().authenticated();
        registry.anyRequest().access("@rbacService.hasPermission(request,authentication)").and();

        http.httpBasic().and().csrf().disable()
            .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
