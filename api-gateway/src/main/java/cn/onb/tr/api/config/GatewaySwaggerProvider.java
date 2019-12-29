package cn.onb.tr.api.config;

import cn.onb.tr.api.properties.DynamicRouteProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 加载所有路由资源
 * @Author: HJH
 * @Date: 2019-09-26 12:06
 */
@Component
@Primary
@AllArgsConstructor
public class GatewaySwaggerProvider implements SwaggerResourcesProvider {
    public static final String API_URI = "/v2/api-docs";
    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;

    @Autowired
    private DynamicRouteProperties dynamicRouteProperties;

    @Autowired
    private ConfigService configService;


    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        //取出gateway的route
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));


        String content = null;
        try {
            content = configService.getConfig(dynamicRouteProperties.getDataId(), dynamicRouteProperties.getGroup(), 5000);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        if (StringUtils.isBlank(content)) {
            throw new RuntimeException(String.format("data_id:%d group:%d；没有从nacos获得相关的配置。",
                    dynamicRouteProperties.getDataId(), dynamicRouteProperties.getGroup()));
        }
        List<RouteDefinition> routeDefinitions = JSON.parseArray(content, RouteDefinition.class);

        //结合配置的route-路径(Path)，和route过滤，只获取有效的route节点..因为route的配置是写在nacos中的，所以从nacos加载
//        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
        routeDefinitions.stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
                                predicateDefinition.getArgs().get("pattern")
                                        .replace("/**", API_URI)))));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
