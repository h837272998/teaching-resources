package cn.onb.tr.auth.core.validate.code.sms;

import cn.onb.tr.auth.core.properties.CoreProperties;
import cn.onb.tr.auth.core.validate.code.AbstractValidateCode;
import cn.onb.tr.auth.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Description: hbanana--验证码生成器。实现接口ValidateCodeGenerator
 * @Author: 、心
 * @Date: 2019/10/31 17:36
 */
@Component("smsValidateCodeGenerator")
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private CoreProperties coreProperties;


    /**
     * 验证码生成
     * @param request
     * @return
     */
    @Override
    public AbstractValidateCode generator(ServletWebRequest request) {
        String code =  RandomStringUtils.randomNumeric(coreProperties.getCode().getSms().getLength());
        //只是返回最基础的Sms
        return new SmsValidateCode(code, coreProperties.getCode().getSms().getExpireIn());
    }

//    public SecurityProperties getSecurityProperties() {
//        return securityProperties;
//    }
//
//    public void setSecurityProperties(SecurityProperties securityProperties) {
//        this.securityProperties = securityProperties;
//    }
}
