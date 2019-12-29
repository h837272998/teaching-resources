package cn.onb.tr.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description:
 * @Author: HJH
 * @Date: 2019-09-27 0:02
 */
@Data
@ConfigurationProperties(prefix = "tr.nacos.route")
public class DynamicRouteProperties {

    /**
     * nacos配置的dataId
     */
    private String dataId = ApiConstants.DEFAULT_DATA_ID;

    /**
     * 配置的group
     */
    private String group = ApiConstants.DEFAULT_GROUP;

}
