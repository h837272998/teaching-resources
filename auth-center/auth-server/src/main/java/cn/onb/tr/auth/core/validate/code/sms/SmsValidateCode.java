package cn.onb.tr.auth.core.validate.code.sms;

import cn.onb.tr.auth.core.validate.code.AbstractValidateCode;

import java.time.LocalDateTime;

/**
 * @Description: hbanana--继承抽象。只重构构造方法。没有添加属性。对于手机验证码只有code expire
 * @Author: 、心
 * @Date: 2019/10/31 17:36
 */
public class SmsValidateCode extends AbstractValidateCode {
    public SmsValidateCode(String code, LocalDateTime expireTime) {
        super(code, expireTime);
    }

    public SmsValidateCode(String code, int expireIn) {
        super(code, expireIn);
    }
}
