package cn.onb.tr.auth;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/12/1 20:32
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AuthServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AuthServerApplication.class).run(args);
    }
}
