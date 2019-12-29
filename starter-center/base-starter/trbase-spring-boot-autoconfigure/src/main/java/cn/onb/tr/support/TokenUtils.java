package cn.onb.tr.support;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/6 15:02
 */
public class TokenUtils {
    public static final String TOKEN_HEADER = "accessToken" ;
    public static final String REFRESH_HEADER = "refreshToken" ;
    public static final String TOKEN_COOKIE = "access_token" ;
    public static final String REFRESH_COOKIE = "refresh_token" ;

    /**
     * 获得Token。可以获得多种发送的请求token
     *
     * @return
     */
    public static String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String header = request.getHeader("Authorization");
        String token = StringUtils.isBlank(StringUtils.substringAfter(header,
                OAuth2AccessToken.BEARER_TYPE + " ")) ? request.getParameter(OAuth2AccessToken.ACCESS_TOKEN) :
                StringUtils.substringAfter(header, OAuth2AccessToken.BEARER_TYPE + " ");

        token = StringUtils.isBlank(request.getHeader(TOKEN_HEADER)) ? token : request.getHeader(TOKEN_HEADER);

//        if (token == null) {
//            Cookie[] cookies = request.getCookies();
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(TOKEN_COOKIE)) {
//                    token = cookie.getValue();
//                }
//            }
//        }

        return token;
    }

    public static String getRefreshToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String header = request.getHeader("RF");
        String token = StringUtils.isBlank(header) ?
                request.getParameter(OAuth2AccessToken.REFRESH_TOKEN) :
                header;

        token = StringUtils.isBlank(request.getHeader(REFRESH_HEADER)) ? token : request.getHeader(REFRESH_HEADER);
//        Cookie[] cookies = null;
////        if (token == null && (cookies = request.getCookies())!=null) {
////            for (Cookie cookie : cookies) {
////                if (cookie.getName().equals(REFRESH_COOKIE)) {
////                    token = cookie.getValue();
////                }
////            }
////        }
        return token;
    }

    /**
     * 拆解client id 和secret
     * @param header
     * @return
     * @throws IOException
     */
    public static String[] extractAndDecodeHeader(String header) throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }

    public static String encodeAuthenticationHeader(String clientId, String secret) throws UnsupportedEncodingException {
        String s = clientId + ":" +secret;
        byte[] base64Token = s.getBytes("UTF-8");
        byte[] encoded;
        try {
            encoded = Base64.encode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to encode basic authentication token");
        }
        String token = new String(encoded, "UTF-8");
        return "Basic " + token;
    }
}
