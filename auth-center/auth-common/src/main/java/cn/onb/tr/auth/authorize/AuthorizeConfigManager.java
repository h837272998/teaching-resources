package cn.onb.tr.auth.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @Description: hbanana--授权信息管理器。用于收集服务的AuthorizeConfigProvider。并加载其配置。
 * @Author: 、心
 * @Date: 2019/10/31 16:25
 */
public interface AuthorizeConfigManager {

    /**
     * @param config
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
