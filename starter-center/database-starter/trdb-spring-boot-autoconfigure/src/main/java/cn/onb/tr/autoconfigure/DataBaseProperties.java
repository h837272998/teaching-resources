package cn.onb.tr.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/11/29 19:39
 */
@Data
@ConfigurationProperties(prefix = "tr.db")
public class DataBaseProperties {

    /**
     * 动态分库开关。log和core
     */
    boolean dynamic = false;
}
