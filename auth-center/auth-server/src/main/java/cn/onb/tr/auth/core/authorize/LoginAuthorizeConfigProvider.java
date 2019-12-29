package cn.onb.tr.auth.core.authorize;

import cn.onb.tr.auth.authorize.AuthorizeConfigProvider;
import cn.onb.tr.auth.core.properties.CoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/10/31 17:10
 */
@Component
@Order(Integer.MIN_VALUE)
public class LoginAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private CoreProperties coreProperties;

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(permitUrl()).permitAll();
        return false;
    }

    @Override
    public String[] permitUrl() {
        return new String[]{coreProperties.getBrowser().getLoginPage(), coreProperties.getBrowser().getSignInProcessingUrl(),
                "/oauth/**", "/code/**", "/v2/api-docs", "/logout", "/auth/**", "/users/register"};
    }
}

