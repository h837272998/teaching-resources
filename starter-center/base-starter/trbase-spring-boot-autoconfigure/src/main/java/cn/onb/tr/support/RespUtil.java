package cn.onb.tr.support;

import cn.onb.tr.entity.UserDetailsImpl;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/12/1 19:48
 */
public class RespUtil {

    public static RespBean succeed() {
        return RespBean.builder().code(CodeEnum.SUCCESS.getCode()).build();
    }

    public static  RespBean succeed(String msg) {
        return RespBean.builder().code(CodeEnum.SUCCESS.getCode()).msg(msg).build();
    }

    public static  RespBean succeed(Object data) {
        return RespBean.builder().code(CodeEnum.SUCCESS.getCode()).data(data).build();
    }

    public static  RespTokenBean succeed(OAuth2AccessToken token) {
        return RespTokenBean.builder().token(token).code(CodeEnum.SUCCESS.getCode()).build();
    }

    public static  RespBean succeed(String msg,Object data) {
        return RespBean.builder().code(CodeEnum.SUCCESS.getCode()).msg(msg).data(data).build();
    }

    public static  RespBean failed() {
        return new RespBean(CodeEnum.ERROR.getCode(), null, null);
    }

    public static  RespBean failed(String msg) {
        return new RespBean(CodeEnum.ERROR.getCode(), msg, null);
    }


}
