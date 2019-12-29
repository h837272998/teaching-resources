package cn.onb.tr.support;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/11 20:41
 */
public class NacosUtils {
    public static String  getProperties(String serverAddr, String dataId, String group) throws NacosException {
        ConfigService configService = NacosFactory.createConfigService(serverAddr);
        String content = configService.getConfig(dataId, group, 5000);
        return content;
    }
}
