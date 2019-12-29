package cn.onb.tr.user;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/12/2 14:30
 */
@SpringBootApplication
@EnableDiscoveryClient
//@EnableLogging
@EnableFeignClients("cn.onb.tr.auth")
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableApiIdempotent
@MapperScan("cn.onb.tr.user.mapper")
public class UserServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(UserServerApplication.class).run(args);
    }

    // 注解支持的配置Bean TODO 重构每个微服务
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
