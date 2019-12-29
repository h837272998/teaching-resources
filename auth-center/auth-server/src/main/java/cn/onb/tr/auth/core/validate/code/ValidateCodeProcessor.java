package cn.onb.tr.auth.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Description: hbanana--高级接口，校检码处理器。
 * @Author: 、心
 * @Date: 2019/10/31 17:32
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建校检码
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception, ValidateCodeException;

    /**
     * 校检码验证
     * @param servletWebRequest
     */
    void validate(ServletWebRequest servletWebRequest);
}
