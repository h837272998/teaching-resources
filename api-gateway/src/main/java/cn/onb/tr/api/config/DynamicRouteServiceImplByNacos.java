package cn.onb.tr.api.config;

import cn.onb.tr.api.properties.DynamicRouteProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.Executor;


/**
 * @Description: (onb)->nacos动态加载
 * @Author: 、心
 * @Date: 19/12/6 21:35
 */
@Slf4j
@Configuration
public class DynamicRouteServiceImplByNacos {
    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    @Autowired
    private DynamicRouteProperties dynamicRouteProperties;

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String serverAddr;

    /**
     * 监听Nacos Server下发的动态路由配置
     */
    @Bean
    public ConfigService dynamicRouteByNacosListener (){
        ConfigService configService = null;
        try {
            configService= NacosFactory.createConfigService(serverAddr);
            String content = configService.getConfig(dynamicRouteProperties.getDataId(), dynamicRouteProperties.getGroup(), 5000);
            log.info("nacos gateway config {}",content);
            configService.addListener(dynamicRouteProperties.getDataId(), dynamicRouteProperties.getGroup(), new Listener()  {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    List<RouteDefinition> routeDefinitions = JSON.parseArray(configInfo, RouteDefinition.class);
                    for (RouteDefinition routeDefinition : routeDefinitions) {
                        dynamicRouteService.update(routeDefinition);
                    }
                }
                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            //todo 提醒:异常自行处理此处省略
            log.error(e.getErrMsg());
        }
        return configService;
    }
}
