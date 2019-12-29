package cn.onb.tr.auth.core.authorize.handler;

import cn.onb.tr.support.RespBean;
import cn.onb.tr.support.RespUtil;
import cn.onb.tr.support.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/10/31 17:16
 */
@Slf4j
public class OauthLogoutHandler implements LogoutHandler {
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Assert.notNull(tokenStore, "tokenStore must be set");
        String token = TokenUtils.getToken();
        if (StringUtils.isBlank(token)) {
            throw new BadCredentialsException("未检测到token，请先登录！");
        }
        OAuth2AccessToken existingAccessToken = tokenStore.readAccessToken(token);
        OAuth2RefreshToken refreshToken;
        if (existingAccessToken == null) {
            throw new BadCredentialsException("请先登录");
        }
        if (existingAccessToken.getRefreshToken() != null) {
            log.info("remove refreshToken!", existingAccessToken.getRefreshToken());
            refreshToken = existingAccessToken.getRefreshToken();
            tokenStore.removeRefreshToken(refreshToken);
        }
        log.info("remove existingAccessToken!", existingAccessToken);
        tokenStore.removeAccessToken(existingAccessToken);
        response.setContentType("application/json;charset=utf-8");
        try {
            RespBean succeed = RespUtil.succeed("注销成功！");
            response.getWriter().write(objectMapper.writeValueAsString(succeed));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    protected String extractToken(HttpServletRequest request) {
//        // first check the header...
//        String token = extractHeaderToken(request);
//
//        // bearer type allows a request parameter as well
//        if (token == null) {
//            log.debug("Token not found in headers. Trying request parameters.");
//            token = request.getParameter(OAuth2AccessToken.ACCESS_TOKEN);
//            if (token == null) {
//                log.debug("Token not found in request parameters.  Not an OAuth2 request.");
//            } else {
//                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, OAuth2AccessToken.BEARER_TYPE);
//            }
//        }
//
//        return token;
//    }

//    protected String extractHeaderToken(HttpServletRequest request) {
//        Enumeration<String> headers = request.getHeaders("Authorization");
//        while (headers.hasMoreElements()) { // typically there is only one (most
//            // servers enforce that)
//            String value = headers.nextElement();
//            if ((value.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase()))) {
//                String authHeaderValue = value.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
//                // Add this here for the auth details later. Would be better to
//                // change the signature of this method.
//                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE,
//                        value.substring(0, OAuth2AccessToken.BEARER_TYPE.length()).trim());
//                int commaIndex = authHeaderValue.indexOf(',');
//                if (commaIndex > 0) {
//                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
//                }
//                return authHeaderValue;
//            }
//        }
//
//        return null;
//    }
}
