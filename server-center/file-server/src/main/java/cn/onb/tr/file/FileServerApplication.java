package cn.onb.tr.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/11 20:28
 */
@SpringBootApplication
@EnableDiscoveryClient
@Configuration
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FileServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FileServerApplication.class).run(args);
    }
}
