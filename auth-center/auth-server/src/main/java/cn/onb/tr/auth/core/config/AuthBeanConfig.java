package cn.onb.tr.auth.core.config;

import cn.onb.tr.auth.authorize.OAuth2AccessDeniedHandlerImpl;
import cn.onb.tr.auth.authorize.WebResponseExceptionTranslatorImpl;
import cn.onb.tr.auth.config.AuthenticationEntryPointImpl;
import cn.onb.tr.auth.core.authorize.handler.OauthLogoutHandler;
import cn.onb.tr.auth.core.service.impl.DefaultUserDetailsServiceImpl;
import cn.onb.tr.auth.core.service.impl.TrTokenServices;
import cn.onb.tr.support.NoPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.Arrays;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/10/31 17:17
 */
@Order(Integer.MIN_VALUE)
@Configuration
public class AuthBeanConfig {

    /**
     * 提供默认的UserDetailsService。这是一个不可用的。只是提供一个错误。所以需要server服务实现UserDetailsService
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsServiceImpl();
    }

    @Bean
    public OauthLogoutHandler oauthLogoutHandler() {
        return new OauthLogoutHandler();
    }


    @Bean("passwordEncoder")
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(value = "oAuth2AccessDeniedHandler")
    public OAuth2AccessDeniedHandlerImpl oAuth2AccessDeniedHandler() {
        return new OAuth2AccessDeniedHandlerImpl();
    }

    @Bean(value = "webResponseExceptionTranslator")
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new WebResponseExceptionTranslatorImpl();
    }

    @Bean(value = "oAuth2AccessDeniedHandler")
    public AccessDeniedHandler accessDeniedHandler() {
        return new OAuth2AccessDeniedHandlerImpl();
    }

    @Bean(value = "authenticationEntryPoint")
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    @Bean(value = "oAuth2WebSecurityExpressionHandler")
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    @Bean(value = "authorizationServerTokenServices")
    public AuthorizationServerTokenServices authorizationServerTokenServices(TokenStore tokenStore,
                                                                             ClientDetailsService clientDetailsService,
                                                                             TokenEnhancer accessTokenEnhancer,
                                                                             AuthenticationManager authenticationManager,
                                                                             UserDetailsService userDetailsService) {
        TrTokenServices trTokenServices = new TrTokenServices();
        trTokenServices.setTokenEnhancer(accessTokenEnhancer);
        trTokenServices.setTokenStore(tokenStore);
        trTokenServices.setClientDetailsService(clientDetailsService);
        trTokenServices.setAuthenticationManager(authenticationManager);
        //刷新token时删除原token和原refresh_token
        trTokenServices.setReuseRefreshToken(false);
        trTokenServices.setSupportRefreshToken(true);
        if (userDetailsService != null) {
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(
                    userDetailsService));
            trTokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));
        }
        return trTokenServices;
    }

}
