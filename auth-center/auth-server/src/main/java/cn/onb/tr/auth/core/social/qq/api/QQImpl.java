package cn.onb.tr.auth.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/11/3 14:32
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
    public static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    public static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    private String appId;
    private String openId;

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        log.info(result);
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        log.info(result);
        QQUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("获用户信息失败");
        }
    }
}
