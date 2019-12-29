package cn.onb.tr.autoconfigure.annotation;

import cn.onb.tr.autoconfigure.aop.LogAnnotationAOP;
import cn.onb.tr.autoconfigure.selector.LogImportSelector;
import cn.onb.tr.autoconfigure.service.impl.LogServiceImpl;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description: 启动日志框架支持
 * 需要配置多数据源
 * @Author: HJH
 * @Date: 2019-09-27 23:16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogImportSelector.class)
public @interface EnableLogging {
}
