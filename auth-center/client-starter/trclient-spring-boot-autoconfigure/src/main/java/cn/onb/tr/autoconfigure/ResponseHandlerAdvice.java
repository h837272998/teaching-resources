package cn.onb.tr.autoconfigure;

import cn.onb.tr.auth.AuthFeignClient;
import cn.onb.tr.auth.support.RedisClientDetailsService;
import cn.onb.tr.auth.support.TrClientDetails;
import cn.onb.tr.support.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.security.Principal;
import java.util.Map;

/**
 * @Description: (onb)-构想是希望在响应时添加一个判断如果token快过期了。从新刷新一次token。最后发现将这个功能交给
 * 前端才是最理想的。
 * refresh_token时需要clientId和Secret这是服务端所难以知道的。但也可以解决的。通过checkToken获取clientId然后再获取Sercet再加密重新刷新token。
 * 这个功能应该交给前端实现会好一点。但是我想用后端实现也是一个学习过程吧。
 * TODO 需要重构
 * @Author: 、心
 * @Date: 19/12/12 18:00
 */
@Slf4j
@RestControllerAdvice
@Import(RedisClientDetailsService.class)
public class ResponseHandlerAdvice implements ResponseBodyAdvice<AbstractRespBean> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        if (methodParameter.getMethod() == null) {
            return false;
        }
        return RespBean.class.isAssignableFrom(methodParameter.getMethod().getReturnType());
    }

    @Autowired()
    private AuthFeignClient authFeignClient;

    @Autowired
    private RedisClientDetailsService redisClientDetailsService;

    /**
     * 自动刷新token机制
     * 1、判断是否是授权URI。获取Token，根据Token。向认证中心查询token的详细信息（expired，和client_id）。
     * 2、根据client_id去查询Client的相关信息（修改BaseClient 添加字段originalClientSecret）。
     * 3、根据信息判断是否重签。需要重签则向认证中心重签。重签完后添加tokne。
     * @param respBean
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @SneakyThrows
    @Override
    public AbstractRespBean beforeBodyWrite(AbstractRespBean respBean, MethodParameter methodParameter,
                                            MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        for (CodeEnum value : CodeEnum.values()) {
            if (value.getCode().equals(respBean.getCode())) {
                serverHttpResponse.setStatusCode(value.getStatus());
            }
        }

        Principal principal = serverHttpRequest.getPrincipal();
        //没有认证的资源直接放回
        if (principal == null) {
            return respBean;
        }
        String token = TokenUtils.getToken();
        Map<String, ?> map = authFeignClient.checkToken(token);
        Integer exp = (Integer) map.get("exp");

        Long expired = new Long(exp.toString());
        long l = expired - System.currentTimeMillis()/1000;
        String clientId = (String) map.get("client_id");
        ClientDetails clientDetails = redisClientDetailsService.loadClientByClientId(clientId);

        if (l <= 60000 || l < ((ClientDetails) clientDetails).getAccessTokenValiditySeconds()/13) {
            //刷新tokenzcv
            String oldRefreshToken = TokenUtils.getRefreshToken();
            String authHead = TokenUtils.encodeAuthenticationHeader(clientDetails.getClientId(),
                    ((TrClientDetails)clientDetails).getOriginalClientSecret());
            if (oldRefreshToken == null) {
                respBean.setMsg(respBean.getMsg() + "（异常的请求！）");
                return respBean;
            }
            OAuth2AccessToken newRefreshToken = authFeignClient.refreshToken("refresh_token",
                    oldRefreshToken,authHead);
            return RespTokenBean.builder().code(respBean.getCode()).msg(respBean.getMsg()).data(respBean.getData()).token(newRefreshToken).build();
        }

        return respBean;
    }
}