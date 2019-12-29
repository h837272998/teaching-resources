package cn.onb.tr.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/12 22:37
 */
@Order(Integer.MIN_VALUE)
@FeignClient("auth-server")
public interface AuthFeignClient {

    @GetMapping("/oauth/check_token")
    public Map<String, ?> checkToken(@RequestParam("token") String value);

    @PostMapping("/oauth/token")
    OAuth2AccessToken refreshToken(@RequestParam("grant_type") String type, @RequestParam(
            "refresh_token") String token, @RequestHeader("Authorization") String auth);
}
