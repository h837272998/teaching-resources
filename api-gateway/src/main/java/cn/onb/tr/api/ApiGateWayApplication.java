package cn.onb.tr.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/6 13:56
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGateWayApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ApiGateWayApplication.class).run(args);
    }
}
