package cn.onb.tr.auth.core.social.qq.config;

import cn.onb.tr.auth.core.properties.CoreProperties;
import cn.onb.tr.auth.core.properties.QQProperties;
import cn.onb.tr.auth.core.social.qq.connet.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/11/3 16:44
 */
@Configuration
@ConditionalOnProperty(prefix = "teaching-resources.auth.social.qq",name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {

    @Autowired
    private CoreProperties coreProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
//        super.addConnectionFactories(connectionFactoryConfigurer, environment);
        connectionFactoryConfigurer.addConnectionFactory(createConnectionFactory());
    }

    private ConnectionFactory<?> createConnectionFactory() {
        QQProperties qq = coreProperties.getSocial().getQq();
        return new QQConnectionFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret());
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//        return super.getUsersConnectionRepository(connectionFactoryLocator);
        return null;
    }
}
