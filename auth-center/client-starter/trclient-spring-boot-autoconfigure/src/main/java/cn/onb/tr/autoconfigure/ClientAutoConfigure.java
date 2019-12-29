package cn.onb.tr.autoconfigure;

import cn.onb.tr.auth.authorize.AuthorizeConfigManager;
import cn.onb.tr.auth.authorize.AuthorizeConfigManagerImpl;
import cn.onb.tr.auth.authorize.OAuth2AccessDeniedHandlerImpl;
import cn.onb.tr.auth.config.AuthenticationEntryPointImpl;
import cn.onb.tr.auth.config.TokenStoreConfig;
import cn.onb.tr.auth.support.TrTokenExtractor;
import cn.onb.tr.support.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: (onb)->设置资源设置。
 * @Author: 、心
 * @Date: 2019/12/2 15:47
 */
//@Component
@Slf4j
@Configuration
@EnableResourceServer
@AutoConfigureAfter(TokenStoreConfig.class)
@Import({TokenStoreConfig.class, AuthenticationEntryPointImpl.class, AuthorizeConfigManagerImpl.class,
        TrTokenExtractor.class})
@EnableFeignClients
public class ClientAutoConfigure extends ResourceServerConfigurerAdapter {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 未登录处理401
     */
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler;

//    @Autowired
//    private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    private TokenExtractor trTokenExtractor;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
        resources.stateless(true);
        resources.authenticationEntryPoint(authenticationEntryPoint);
        resources.expressionHandler(oAuth2WebSecurityExpressionHandler);
        resources.accessDeniedHandler(oAuth2AccessDeniedHandler());
        resources.tokenExtractor(trTokenExtractor);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
//        http.authorizeRequests().antMatchers("/users/**").permitAll().anyRequest().authenticated();
        authorizeConfigManager.config(http.authorizeRequests());

    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    @Bean("oAuth2AccessDeniedHandler")
    public AccessDeniedHandler oAuth2AccessDeniedHandler() {
        return new OAuth2AccessDeniedHandlerImpl();
    }

    /**
     * 处理spring security oauth 处理失败返回消息格式
     * resp_code
     * resp_desc
     */
//    @Bean
//    public OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler() {
//        return new OAuth2AccessDeniedHandler() {
//
//            @Override
//            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
////                Map<String, String> rsp = new HashMap<>();
//                response.setContentType("application/json;charset=UTF-8");
//
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
////                rsp.put("code", HttpStatus.UNAUTHORIZED.value() + "") ;
//                RespBean<Object> build = new RespBean<>().builder().code(HttpStatus.UNAUTHORIZED.value()).msg(authException.getMessage()).build();
//
//                response.setContentType("application/json;charset=UTF-8");
//                response.getWriter().write(objectMapper.writeValueAsString(build));
//                response.getWriter().flush();
//                response.getWriter().close();
//
//            }
//        };
//    }
}
