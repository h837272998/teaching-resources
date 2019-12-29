package cn.onb.tr.api.config;

import cn.onb.tr.api.properties.DynamicRouteProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: HJH
 * @Date: 2019-09-27 1:32
 */
@Configuration
@EnableConfigurationProperties(DynamicRouteProperties.class)
public class BeanConfig {

}
