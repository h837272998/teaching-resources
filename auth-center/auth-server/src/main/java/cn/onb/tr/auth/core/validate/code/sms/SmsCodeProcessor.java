package cn.onb.tr.auth.core.validate.code.sms;

import cn.onb.tr.auth.core.properties.CoreConstant;
import cn.onb.tr.auth.core.validate.code.AbstractValidateCode;
import cn.onb.tr.auth.core.validate.code.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Description: hbanana--短信验证处理器
 * @Author: 、心
 * @Date: 2019/10/31 17:35
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor {

    /**
     * 短信验证码发送器
     */
    @Autowired
    private SmsCodeSender smsCodeSender;


    @Override
    protected void send(ServletWebRequest request, AbstractValidateCode validateCode) throws Exception {
        String paramName = CoreConstant.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile,validateCode.getCode());
    }
}
