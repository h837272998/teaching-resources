package cn.onb.tr.autoconfigure.annotation;

import java.lang.annotation.*;

/**
 * @Description: 日志的注解.需要在控制器，方法中
 * @Author: HJH
 * @Date: 2019-09-27 23:16
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    /**
     * 模块
     * @return
     */
    String module();

    /**
     * 记录执行参数
     * @return
     */
    boolean recordRequestParam() default true;
}

