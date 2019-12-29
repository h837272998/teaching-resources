package cn.onb.tr.auth.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/10/31 17:31
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
