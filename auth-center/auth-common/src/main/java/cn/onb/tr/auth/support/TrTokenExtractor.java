package cn.onb.tr.auth.support;

import cn.onb.tr.auth.authorize.AuthorizeConfigManager;
import cn.onb.tr.auth.authorize.AuthorizeConfigProvider;
import cn.onb.tr.support.AuthConstants;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * @Description: (onb)->修改默认的BearerTokenExtractor 添加可以从Cookie中提取取access_token
 * 需要在ResourceServerConfigurerAdapter。配置。
 * @Author: 、心
 * @Date: 19/12/21 21:43
 */
//@Order(Integer.MIN_VALUE)
//@Component
public class TrTokenExtractor implements TokenExtractor {
    private static final Log logger = LogFactory.getLog(BearerTokenExtractor.class);

    public TrTokenExtractor() {
    }

    public Authentication extract(HttpServletRequest request) {
        String tokenValue = this.extractToken(request);
        if (tokenValue != null) {
            PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(tokenValue, "");
            return authentication;
        } else {
            return null;
        }
    }

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviders;

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    protected String extractToken(HttpServletRequest request) {
        String token = this.extractHeaderToken(request);
        if (token == null) {
            logger.debug("Token not found in headers. Trying request parameters.");
            token = request.getParameter(AuthConstants.ACCESS_TOKEN);
        }

//        if (token == null) {
//            logger.debug("Token not found in parameters. Trying cookie parameters.");
//            Cookie[] cookies = request.getCookies();
//            if(ArrayUtils.isNotEmpty(cookies)) {
//                String requestURI = request.getRequestURI();
//                //放行的链接不需要获取其Cookie。TODO 还有一些跳转问题
//                for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
//                    String[] strings = authorizeConfigProvider.permitUrl();
//                    for (String string : strings) {
//                        if (pathMatcher.match(string, requestURI)) {
//                            logger.debug("Permit URI.");
//                            return null;
//                        }
//                    }
//                }
//                for (Cookie cookie : cookies) {
//                    if (cookie.getName().equals(AuthConstants.ACCESS_TOKEN)) {
//
//                        token = cookie.getValue();
//                        break;
//                    }
//                }
//            }
//        }

        if (token == null) {
            logger.debug("Token not found in request parameters.  Not an OAuth2 request.");
        } else {
            request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, "Bearer");
        }

        return token;
    }

    protected String extractHeaderToken(HttpServletRequest request) {
        Enumeration headers = request.getHeaders("Authorization");

        String value;
        do {
            if (!headers.hasMoreElements()) {
                return null;
            }

            value = (String)headers.nextElement();
        } while(!value.toLowerCase().startsWith("Bearer".toLowerCase()));

        String authHeaderValue = value.substring("Bearer".length()).trim();
        request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, value.substring(0, "Bearer".length()).trim());
        int commaIndex = authHeaderValue.indexOf(44);
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }

        return authHeaderValue;
    }
}
