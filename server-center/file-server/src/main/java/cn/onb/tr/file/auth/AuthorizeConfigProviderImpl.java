package cn.onb.tr.file.auth;

import cn.onb.tr.auth.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @Description: onb
 * @Author: 、心
 * @Data: 2019-10-16 10:08
 */
@Component
public class AuthorizeConfigProviderImpl implements AuthorizeConfigProvider {
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        config.antMatchers(
                "/v2/api-docs",
                "/hos/**"
//                "/test/**"
        ).permitAll();
        return false;
    }

    @Override
    public String[] permitUrl() {
        return new String[0];
    }
}
