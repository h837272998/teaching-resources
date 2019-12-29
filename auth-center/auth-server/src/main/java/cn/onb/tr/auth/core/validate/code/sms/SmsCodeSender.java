package cn.onb.tr.auth.core.validate.code.sms;

/**
 * @Description: hbanana--发送验证码，高级抽象
 * @Author: 、心
 * @Date: 2019/10/31 17:36
 */
public interface SmsCodeSender {
    void send(String model, String code);
}

