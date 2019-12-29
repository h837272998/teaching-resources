package cn.onb.tr.auth.authorize;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: hbanana-- 授权配置管理器实现类
 * @Author: 、心
 * @Date: 2019/10/31 16:27
 */
@Slf4j
@Component
@Data
public class AuthorizeConfigManagerImpl implements AuthorizeConfigManager {

    @Autowired(required = false)
    private List<AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        boolean existAnyRequestConfig = false;
        String existAnyRequestConfigName = null;

        if (authorizeConfigProviders == null) {
            log.error("在spring容器中没有找到任何Provider，默认使用任何请求都需要验证。");
            config.anyRequest().authenticated();
            return;
        }

        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
            boolean currentIsAnyRequestConfig = authorizeConfigProvider.config(config);
            if (existAnyRequestConfig && currentIsAnyRequestConfig) {
                throw new RuntimeException("重复的anyRequest配置:" + existAnyRequestConfigName + ","
                        + authorizeConfigProvider.getClass().getSimpleName());
            } else if (currentIsAnyRequestConfig) {
                existAnyRequestConfig = true;
                existAnyRequestConfigName = authorizeConfigProvider.getClass().getSimpleName();
            }
        }

        if(!existAnyRequestConfig){
            config.anyRequest().authenticated();
        }
    }
}
