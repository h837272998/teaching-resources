package cn.onb.tr.auth.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: hbanana--核心配置
 * @Author: 、心
 * @Date: 2019/10/31 16:56
 */
@Data
@ConfigurationProperties(prefix = "tr.auth")
public class CoreProperties {
    private Oauth2Properties oauth2 = new Oauth2Properties();
    private BrowserProperties browser = new BrowserProperties();
    private ValidateCodeProperties code = new ValidateCodeProperties();
    private SocialProperties social = new SocialProperties();
}
