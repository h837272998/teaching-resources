package cn.onb.tr.auth.core.social;

import cn.onb.tr.auth.core.properties.CoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/11/3 16:57
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository jdbc = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
        jdbc.setTablePrefix("t_");
        return jdbc;
    }

    @Autowired
    private CoreProperties coreProperties;

    @Bean
    public SpringSocialConfigurer hSpringSocialConfigurer() {
        String filterProcessesUrl = coreProperties.getSocial().getFilterProcessesUrl();
        HSpringSocialConfigurer configurer = new HSpringSocialConfigurer(filterProcessesUrl);
        configurer.signupUrl(coreProperties.getBrowser().getSignInProcessingUrl());
        return configurer;
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public ProviderSignInUtils provider(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator));
    }
}
