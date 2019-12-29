package cn.onb.tr.autoconfigure;

import cn.onb.tr.autoconfigure.interceptor.LogInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/12/2 16:56
 */
@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
public class LogAutoConfigure  implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        log.error("-----------------------------------LogAutoConfig--------------------------------------");

        /**
         * 自定义拦截器，添加拦截路径和排除拦截路径
         * addPathPatterns():添加需要拦截的路径
         * excludePathPatterns():添加不需要拦截的路径
         * 在括号中还可以使用集合的形式，如注释部分代码所示
         */
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**") ;


    }
}