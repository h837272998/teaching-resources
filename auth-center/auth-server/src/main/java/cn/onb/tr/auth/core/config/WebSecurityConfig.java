package cn.onb.tr.auth.core.config;

//import cn.onb.tr.auth.core.authorize.TrDaoAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/10/31 17:22
 */
@Component
@Configuration
@EnableWebSecurity
//保证比ResourceServerConfig快
//@Order(Integer.MIN_VALUE-1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(new TrDaoAuthenticationProvider(userDetailsService));
        super.configure(auth);
    }

    //
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private FormAuthenticationConfig formAuthenticationConfig;
//
//    @Autowired
//    private AuthorizeConfigManager authorizeConfigManager;
//
//    @Autowired
//    private CoreProperties coreProperties;
//
//    @Autowired
//    private AuthenticationSuccessHandler authenticationSuccessHandler;
//
//    @Autowired
//    private AuthenticationFailureHandler authenticationFailureHandler;
//
//    /**
//     * 验证码配置
//     */
//    @Autowired
//    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
//
//    @Autowired
//    private OauthLogoutHandler oauthLogoutHandler;
//
//    /**
//     * 手机登录配置
//     */
//    @Autowired
//    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        formAuthenticationConfig.configure(http);
//        http.apply(validateCodeSecurityConfig).and()
//                .apply(smsCodeAuthenticationSecurityConfig);
//
//        http.logout().logoutUrl("/logout")
//                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
//                .addLogoutHandler(oauthLogoutHandler).clearAuthentication(true);
//
//        http.csrf().disable();
////        http.antMatcher("/oauth/**");
//        authorizeConfigManager.config(http.authorizeRequests());
//    }

    /**
     * override bean authticationManager
     * support password grant type oauth2
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {

        return super.authenticationManager();
    }
}
