package cn.onb.tr.auth.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Description: hbanana--校检码生成接口
 * @Author: 、心
 * @Date: 2019/10/31 17:31
 */
public interface ValidateCodeGenerator {
    /**
     * 抽象生成验证码
     * @param request
     * @return
     */
    AbstractValidateCode generator(ServletWebRequest request);
}
