package cn.onb.tr.auth.core.config;

import cn.onb.tr.auth.core.service.impl.TrTokenServices;
import cn.onb.tr.auth.support.RedisClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.List;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/10/31 17:20
 */
@Configuration
@EnableAuthorizationServer
@AutoConfigureAfter(AuthorizationServerEndpointsConfiguration.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * inject authenticationManager，use password grant type
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private RedisClientDetailsService redisClientDetailsService;

    @Autowired
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
     * defines the authorization and token endpoints and the token services
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // url:/oauth/token_key,exposes
        security.tokenKeyAccess("permitAll()")
                /// public key for token
                /// verification if using
                /// JWT tokens
                // url:/oauth/check_token
//                .checkTokenAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")
                // allow check token
                .allowFormAuthenticationForClients();

    }


    /**
     * 配置客户端详情服务。客户端的信息在这里初始化。可以采用内存，jdbc等
     * a configurer that defines the client details service. Client details can be initialized, or you can just refer
     * to an existing store.
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(redisClientDetailsService);
        //加载所有client到redis缓存。
        redisClientDetailsService.loadAllClientToCache();
    }

    /**
     * 配置令牌端点(Token Endpoint) 的安全约束
     * defines the oauth constraints on the token endpoint.
     * 配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        if (tokenStore instanceof JwtTokenStore) {
            //token存储
            endpoints.tokenStore(tokenStore);
        } else {
            endpoints.tokenStore(tokenStore).accessTokenConverter(jwtAccessTokenConverter);
        }

        endpoints.authenticationManager(authenticationManager)
                //UserDetailsService
                .userDetailsService(userDetailsService)
                //自定义异常返回
                .exceptionTranslator(webResponseExceptionTranslator)
                .tokenServices(authorizationServerTokenServices);
        //todo
    }



}
