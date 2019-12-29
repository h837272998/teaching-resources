package cn.onb.tr.auth.core.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: hbanana--发送验证码
 * @Author: 、心
 * @Date: 2019/10/31 17:35
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender{

    @Override
    public void send(String model, String code) {
        log.info("向"+model+"发送短信验证码："+code);
    }
}
