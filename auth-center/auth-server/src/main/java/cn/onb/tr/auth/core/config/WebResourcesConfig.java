package cn.onb.tr.auth.core.config;

import cn.onb.tr.auth.authorize.AuthorizeConfigManager;
import cn.onb.tr.auth.authorize.AuthorizeConfigManagerImpl;
import cn.onb.tr.auth.config.AuthenticationEntryPointImpl;
import cn.onb.tr.auth.config.TokenStoreConfig;
import cn.onb.tr.auth.core.authorize.FormAuthenticationConfig;
import cn.onb.tr.auth.core.authorize.handler.OauthLogoutHandler;
import cn.onb.tr.auth.core.mobile.SmsCodeAuthenticationSecurityConfig;
import cn.onb.tr.auth.core.properties.CoreProperties;
import cn.onb.tr.auth.core.social.SocialConfig;
import cn.onb.tr.auth.core.validate.code.ValidateCodeSecurityConfig;
import cn.onb.tr.auth.support.TrTokenExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @Description: hbanana--抽象的验证码对象
 * @Author: 、心
 * @Date: 2019/10/31 17:21
 */
@Slf4j
@Configuration
@EnableResourceServer
@AutoConfigureAfter(TokenStoreConfig.class)
@Import({TokenStoreConfig.class, AuthenticationEntryPointImpl.class, AuthorizeConfigManagerImpl.class,
        TrTokenExtractor.class})
public class WebResourcesConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    private CoreProperties coreProperties;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 验证码配置
     */
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private OauthLogoutHandler oauthLogoutHandler;

    //todo
//    @Autowired
//    private SpringSocialConfigurer hSpringSocialConfigurer;

    /**
     * 手机登录配置
     */
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        formAuthenticationConfig.configure(http);
        http.apply(validateCodeSecurityConfig).and()
                .apply(smsCodeAuthenticationSecurityConfig);
//                .apply(hSpringSocialConfigurer);

        http.logout().logoutUrl("/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .addLogoutHandler(oauthLogoutHandler).clearAuthentication(true);
        //修改密码校对方式。只要改变验证RefreshToken时的验证方式。
//        http.authenticationProvider(new TrDaoAuthenticationProvider(userDetailsService));
        http.csrf().disable();
        authorizeConfigManager.config(http.authorizeRequests());
    }



    @Autowired
    private TokenExtractor trTokenExtractor;

    @Autowired
    private OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler;

    @Autowired
    private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;
    /**
     * 未登录处理401
     */
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.tokenStore(tokenStore)
//                .stateless(true)
                .authenticationEntryPoint(authenticationEntryPoint)
                .expressionHandler(oAuth2WebSecurityExpressionHandler)
                .accessDeniedHandler(oAuth2AccessDeniedHandler)
                .tokenExtractor(trTokenExtractor);
//        resources.tokenExtractor(trTokenExtractor);
    }


}
