package cn.onb.tr.annotation;

import java.lang.annotation.*;

/**
 * @Describe: 使用数据库。
 * @Author: 、心
 * @Date： 2019/10/31
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    //数据库名称
    String name();
}
