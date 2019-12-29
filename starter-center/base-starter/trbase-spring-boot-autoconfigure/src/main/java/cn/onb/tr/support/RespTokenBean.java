package cn.onb.tr.support;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/7 22:12
 */
@Data
public class RespTokenBean<T> extends AbstractRespBean<T> {
    private static final long serialVersionUID = 1869538063909473964L;

    private OAuth2AccessToken token;

    public RespTokenBean(OAuth2AccessToken token) {
        this.token = token;
    }

    @Builder
    public RespTokenBean(Integer code, String msg, T data, OAuth2AccessToken token) {
        super(code, msg, data);
        this.token = token;
    }
}
