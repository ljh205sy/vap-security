package com.vrv.vap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

/**
 * @author wh1107066
 * @date 2021/6/21 16:08
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ResourceServerConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return getOAuth2MethodSecurityExpressionHandler();
    }

    @Bean
    public OAuth2MethodSecurityExpressionHandler getOAuth2MethodSecurityExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
    }

}