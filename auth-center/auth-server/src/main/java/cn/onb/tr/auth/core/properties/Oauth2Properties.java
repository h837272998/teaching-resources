package cn.onb.tr.auth.core.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;

/**
 * @Description: hbanana--oauth 的一些配置
 * @Author: 、心
 * @Date: 2019/10/31 16:57
 */
@Data
public class Oauth2Properties {

    /**
     * jwt signin key
     */
    private String jwtSigningKey = CoreConstant.DEFAULT_JWT_SIGN_IN_KEY;

    /**
     * 客户端配置 使用了jdbc 抛弃xxxx
     */
    @Deprecated
    private OAuth2ClientProperties[] clients = {};
}
