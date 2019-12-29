package cn.onb.tr.auth.core.authorize;

import cn.onb.tr.auth.core.authorize.handler.AuthenticationSuccessHandler;
import cn.onb.tr.auth.core.properties.CoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @Description: hbanana--表单认证配置，将安全模块分开。主要用于配置 SuccessHandler。FailureHandler
 * @Author: 、心
 * @Date: 2019/10/31 17:09
 */
@Component
public class FormAuthenticationConfig {

    @Autowired
    protected AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CoreProperties coreProperties;

    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(coreProperties.getBrowser().getLoginPage())
                .loginProcessingUrl(coreProperties.getBrowser().getSignInProcessingUrl())
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
    }
}
