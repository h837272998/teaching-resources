package cn.onb.tr.auth.core.properties;

import lombok.Data;

/**
 * @Description: hbanana--验证码配置
 * @Author: 、心
 * @Date: 2019/10/31 16:57
 */
@Data
public class ValidateCodeProperties {

    /**
     * 手机验证码配置
     */
    private SmsCodeProperties sms = new SmsCodeProperties();
    /**
     * 图像验证码配置
     */
    private ImageCodeProperties image = new ImageCodeProperties();
}
