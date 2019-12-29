package cn.onb.tr.user.controller;

import cn.onb.tr.entity.SysUser;
import cn.onb.tr.entity.UserDetailsImpl;
import cn.onb.tr.support.JSONViewInterface;
import cn.onb.tr.support.RespBean;
import cn.onb.tr.support.RespUtil;
import cn.onb.tr.user.mapper.ISysUserMapper;
import cn.onb.tr.user.service.ISysUserService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;
import java.util.Date;

import static cn.onb.tr.support.RespUtil.succeed;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/6 21:35
 */
@Slf4j
@RestController
@Api("用户管理")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysUserMapper iSysUserMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/{type}")
    @ApiOperation(value = "根据账号或手机号查找用户", notes = "只提供给认证中心，服务间的调用。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path",dataType = "String",name = "type",
                    value = "查找类型",required = true,example = "mobile"),
            @ApiImplicitParam(paramType = "request",dataType = "String",name = "value",value =
                    "value of type",required = true,example = "15815817806")
    })
    @SentinelResource( value = "getUserByUserNameResource",blockHandler = "bolckHandler",
            fallback = "fallback")
    @JsonView(JSONViewInterface.User.All.class)
    public RespBean getUserDetails(@PathVariable String type,
                                   @RequestParam String value) throws InterruptedException, JsonProcessingException {
//        Thread.sleep(12000);
        if ("mobile".equals(type)) {
            UserDetailsImpl user = sysUserService.getUserByPhone(value);
            return RespUtil.<UserDetailsImpl>succeed(user);
        }else {
            UserDetailsImpl user = sysUserService.getUserByUserName(value);
            return RespUtil.<UserDetailsImpl>succeed(user);
        }

    }

    /**
     *     限流与阻塞处理
     */
    public RespBean bolckHandler(String type,String value, BlockException ex) {
        log.error( "blockHandler：" + type, ex);
//        throw new UsernameNotFoundException("用户微服务出错！");
        return RespUtil.failed("用户微服务异常");
    }

    public RespBean fallback(String type,String value) {
        log.error( "fallback：" + type);
//        throw new UsernameNotFoundException("用户微服务出错！");
        return RespUtil.failed("用户微服务异常");
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("check/{type}")
    @ApiOperation(value = "检查手机或者用户是否存在",notes = "存在返回flase，不存在返回true")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "String", name = "type", value =
                    "检查类型", required = true, example = "mobile"),
            @ApiImplicitParam(paramType = "request", dataType = "String", name = "value", value =
                    "value of type", required = true, example = "15815817806")
    })
    public RespBean check(@PathVariable String type, @RequestParam String value) {
        log.info(passwordEncoder.encode("secret"));
        boolean check = iSysUserMapper.check(type, value);
        if (check) {
            return RespUtil.<Boolean>succeed(false);
        }
        return RespUtil.<Boolean>succeed(true);
    }


    @ApiOperation(value = "添加用户。用于前台注册，自动生成学生用户")
    @ApiImplicitParam(name = "user",paramType = "SysUser",value = "用户实例",required = true)
    @PostMapping("add")
    public RespBean addSysUser(@Valid @RequestBody SysUser user) {
        try {
            sysUserService.addUser(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return RespUtil.failed("注册失败！");
        }
        return RespUtil.succeed("注册成功！");
    }

}
