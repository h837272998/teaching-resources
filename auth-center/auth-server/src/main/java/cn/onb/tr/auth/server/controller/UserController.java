package cn.onb.tr.auth.server.controller;

import cn.onb.tr.auth.server.clients.UserFeignClient;
import cn.onb.tr.entity.SysUser;
import cn.onb.tr.support.JSONViewInterface;
import cn.onb.tr.support.RespBean;
import cn.onb.tr.support.RespUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/12 15:58
 */
@RestController
@RequestMapping("users")
@Api("操作需要授权认证的User服务，例如需要验证码")
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    @PostMapping("/register")
    @ApiImplicitParam()
    public RespBean register(@Valid @RequestBody SysUser user) {
        return userFeignClient.register(user);
    }

    /**
     *
     * @param authentication
     * @return
     */
    @GetMapping("/info")
    @JsonView(JSONViewInterface.User.Simple.class)
    public RespBean getInfo(Authentication authentication) {
        if (authentication != null) {
            return RespUtil.succeed(authentication.getPrincipal());
        }
        return RespUtil.failed("没有登录信息！");
    }

}
