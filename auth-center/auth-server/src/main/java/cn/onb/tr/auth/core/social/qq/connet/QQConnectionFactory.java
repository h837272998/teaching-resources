package cn.onb.tr.auth.core.social.qq.connet;

import cn.onb.tr.auth.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @Description: hbanana--QQ连接工厂
 * @Author: 、心
 * @Date: 2019/11/3 15:06
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     *
     * @param providerId
     * @param appId
     * @param appSecret
     */
    public QQConnectionFactory(String providerId,String appId,  String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}
