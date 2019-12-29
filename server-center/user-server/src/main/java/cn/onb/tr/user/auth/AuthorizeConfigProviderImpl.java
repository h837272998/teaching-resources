package cn.onb.tr.user.auth;

import cn.onb.tr.auth.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @Description: 授权的一个提供者。在这里配置不验证的地址
 * @Author: HJH
 * @Date: 2019-10-02 12:09
 */
@Component
public class AuthorizeConfigProviderImpl implements AuthorizeConfigProvider {
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        config.antMatchers(
                permitUrl()
        ).permitAll();

        return false;
    }

    @Override
    public String[] permitUrl() {
        return new String[]{"/users/login",
                "/users/loginByPhone",
                "/v2/api-docs",
                "/test/**",
                "/users/**"};
    }
}
