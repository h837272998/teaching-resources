package cn.onb.tr.support;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/21 21:02
 */
public class NoPasswordEncoder implements PasswordEncoder {

    private static class NoPasswordEncoderInstance {
        private static final NoPasswordEncoder instance = new NoPasswordEncoder();
    }

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return charSequence.equals(s);
    }

    public static NoPasswordEncoder getInstance() {

        return NoPasswordEncoderInstance.instance;
    }
}
