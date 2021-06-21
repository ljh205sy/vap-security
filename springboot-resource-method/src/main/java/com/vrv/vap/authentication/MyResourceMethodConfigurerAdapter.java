package com.vrv.vap.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * @author wh1107066
 * @date 2021/6/21 11:13
 * 基于方法的访问进行权限判定
 */
@Configuration
@EnableResourceServer
public class MyResourceMethodConfigurerAdapter extends ResourceServerConfigurerAdapter {
    private static final String[] AUTH_WHITELIST= {"/v2/api-docs", "/swagger/**", "/doc.html"};


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        for (String au : AUTH_WHITELIST) {
            http.authorizeRequests().antMatchers(au).permitAll();
        }
        http.authorizeRequests().anyRequest().authenticated();

//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
//        registry.anyRequest().access("@rbacService.hasPermission(request,authentication)").and();

        http.httpBasic().and().csrf().disable()
            .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
