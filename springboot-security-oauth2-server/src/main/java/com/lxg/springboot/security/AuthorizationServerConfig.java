package com.lxg.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * https://github.com/ljh205sy/spring-security-oauth2-example
 * https://www.cnblogs.com/hetutu-5238/p/10022963.html
 * @author wh1107066
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory connectionFactory;
    @Resource
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    /**
     * 声明TokenStore实现 (redis存储)
     *
     * @return
     */
    @Bean
    public RedisTokenStore redisTokenStore() {
        return new RedisTokenStore(connectionFactory);
    }

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * *用来配置令牌端点(Token Endpoint)的安全约束.
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        /**
         * 配置oauth2服务跨域
         */
        CorsConfigurationSource source = new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedHeader("*");
                corsConfiguration.addAllowedOrigin(request.getHeader(HttpHeaders.ORIGIN));
                corsConfiguration.addAllowedMethod("*");
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setMaxAge(3600L);
                return corsConfiguration;
            }
        };

        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
                .addTokenEndpointAuthenticationFilter(new CorsFilter(source));
    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService），
     * 客户端详情信息在这里进行初始化，  数据库在进行client_id 与 client_secret验证时   会使用这个service进行验证
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

//        clients.inMemory()
//                .withClient("client")
//                .secret("secret")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
//                .scopes("app")
//            .and()
//                .withClient("webapp")
//                .scopes("xx")
//                .authorizedGrantTypes("implicit","password");
        clients.withClientDetails(clientDetails());
    }

    /**
     *  用来配置授权（authorizatio）以及令牌（token）的访问端点和令牌服务   核心配置  在启动时就会进行配置
     * 1. authenticationManager 的配置类会在后面的WebSecurityAdaptConfig配置中给出
     * 2.TokenStore 一共有五种配置方式  本项目采用jdbc也就是数据库的形式
     *
     * 实现方式有5中，
     * - 1.JdbcTokenStore
     * - 2.RedisTokenStore
     * - 3.JwtTokenStore
     * - 4.InMemmoryTokenStore
     * - 5.JwkTokenStore
     *
     * 3.WebResponseExceptionTranslator为自定义的验证错误异常返回类，代码如下，其中responsedata为自定义的数据返回类 包含code，message，data三个属性
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //实现token生成 并持久化到数据库中
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(redisTokenStore());

        // 配置TokenServices参数(控制token 的失效时间等信息)
        // 配置TokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(false);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        // token有效期自定义设置，默认12小时
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 12);
//        tokenServices.setAccessTokenValiditySeconds( (int) TimeUnit.DAYS.toSeconds(30)); // 30天
        endpoints.tokenServices(tokenServices);
//        endpoints.tokenServices(defaultTokenServices());
//        认证异常翻译
        endpoints.exceptionTranslator(webResponseExceptionTranslator);
    }




    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     *
     * @return
     */
//    @Primary
//    @Bean
//    public DefaultTokenServices defaultTokenServices() {
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(redisTokenStore());
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setClientDetailsService(clientDetails());
//        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 12); // token有效期自定义设置，默认12小时
//        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);//默认30天，这里修改
//        return tokenServices;
//    }


}
