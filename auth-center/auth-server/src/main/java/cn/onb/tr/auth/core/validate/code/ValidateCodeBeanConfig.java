package cn.onb.tr.auth.core.validate.code;

import cn.onb.tr.auth.core.validate.code.image.ImageValidateCodeGenerator;
import cn.onb.tr.auth.core.validate.code.sms.DefaultSmsCodeSender;
import cn.onb.tr.auth.core.validate.code.sms.SmsCodeSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: hbanana--配置bean ，没有自己实现时使用默认配置。
 * @Author: 、心
 * @Date: 2019/10/31 17:30
 */
@Slf4j
@Configuration
public class ValidateCodeBeanConfig {

    /**
     * 默认的图像验证码生成器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator(){
        log.info("使用默认的图像验证码生成器，实现ValidateCodeGenerator接口并将Bean命名imageValidateCodeGenerator，使用替换默认");
        ImageValidateCodeGenerator codeGenerator = new ImageValidateCodeGenerator();
//        codeGenerator.setSecurityProperties(ssoProperties)；
        return codeGenerator;
    }


    /**
     * 默认的短信验证码发送器。（sout）
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        log.info("使用默认的手机验证码生“发送器”，实现SmsCodeSender接口，替换默认的生成器");
        return new DefaultSmsCodeSender();
    }
}
