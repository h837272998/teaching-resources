package cn.onb.tr.auth.server.clients;

import cn.onb.tr.entity.SysUser;
import cn.onb.tr.entity.UserDetailsImpl;
import cn.onb.tr.support.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/7 14:35git
 */
@FeignClient(value = "user-server")
public interface UserFeignClient {

    @GetMapping(value = "/users/username", params = "value")
    RespBean<UserDetailsImpl> getUserByUsername(@RequestParam("value") String username);

    @GetMapping(value = "/users/mobile", params = "value")
    RespBean<UserDetailsImpl> getUserByPhone(@RequestParam("value") String phone);

    @PostMapping(value = "/users/add")
    RespBean register(SysUser user);
}
