package cn.onb.tr.user.controller;

import cn.onb.tr.support.RespBean;
import cn.onb.tr.support.RespUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/6 16:56
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping
    public RespBean test() {
        return RespUtil.succeed("Test接口");
    }

}
