package cn.onb.tr.auth.core.properties;

import lombok.Data;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/11/3 16:47
 */
@Data
public class SocialProperties {

    private QQProperties qq = new QQProperties();

    private String filterProcessesUrl = "/auth";
}
