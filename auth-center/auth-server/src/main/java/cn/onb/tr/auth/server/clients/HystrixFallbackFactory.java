package cn.onb.tr.auth.server.clients;

import cn.onb.tr.entity.SysUser;
import cn.onb.tr.entity.UserDetailsImpl;
import cn.onb.tr.exception.FeignException;
import cn.onb.tr.support.CodeEnum;
import cn.onb.tr.support.RespBean;
import cn.onb.tr.support.RespUtil;
import org.springframework.stereotype.Component;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/13 18:19
 */
//@Component
public class HystrixFallbackFactory implements UserFeignClient{

    @Override
    public RespBean<UserDetailsImpl> getUserByUsername(String username) throws FeignException {
        return RespUtil.failed("user 微服务异常");
//        throw new FeignException("user 微服务异常");
    }

    @Override
    public RespBean<UserDetailsImpl> getUserByPhone(String phone) {
        return RespUtil.failed("user 微服务异常");

    }

    @Override
    public RespBean register(SysUser user) {
        return RespUtil.failed("user 微服务异常");
    }
}
