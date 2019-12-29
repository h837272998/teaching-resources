package cn.onb.tr.autoconfigure.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Description: log-spring-boot-starter 自动装配
 * @Author: HJH
 * @Date: 2019-09-27 23:23
 */
public class LogImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                "cn.onb.tr.autoconfigure.aop.LogAnnotationAOP",
                "cn.onb.tr.autoconfigure.service.impl.LogServiceImpl",
                "cn.onb.tr.autoconfigure.LogAutoConfigure"
        };
    }

}
