package cn.onb.tr.auth.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Description: hbanana--验证码存取器接口
 * @Author: 、心
 * @Date: 2019/10/31 17:32
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request, AbstractValidateCode code, ValidateCodeType validateCodeType);
    /**
     * 获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    AbstractValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);
    /**
     * 移除验证码
     * @param request
     * @param codeType
     */
    void remove(ServletWebRequest request, ValidateCodeType codeType);
}

