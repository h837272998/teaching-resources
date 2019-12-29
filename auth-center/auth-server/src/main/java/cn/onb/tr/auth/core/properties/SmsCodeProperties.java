package cn.onb.tr.auth.core.properties;

import lombok.Data;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/10/31 16:57
 */
@Data
public class SmsCodeProperties {
    /**
     * 验证码长度
     */
    private int length = 6;
    /**
     * 过期时间
     */
    private int expireIn = 60;
    /**
     * 要拦截的url，多个url用逗号隔开，ant pattern
     */
    private String url;
}
