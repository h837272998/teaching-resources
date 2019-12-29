package cn.onb.tr.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/11 15:34
 */
@Data
@ConfigurationProperties(prefix = "tr.redis")
public class RedisPorperties {

    /**
     * 缓存过期时间
     */
    private int expired = 300;

}
