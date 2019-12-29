package cn.onb.tr.auth.config;

import cn.onb.tr.auth.support.RedisTokenStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @Description: hbanana--Oauth2 tokenStore 的配置
 * 默认使用oauth jwtTokenStore 。使用配置hjh.auth.oauth2.tokenStore=redis 替换成redis
 * @Author: 、心
 * @Date: 2019/10/31 16:34
 */
@Slf4j
@Order(-1)
@Configuration
public class TokenStoreConfig {
    @Value("${tr.oauth2.jwt-signing-key}")
    private String nacosJwtSigningKey;
    /**
     * 采用jwt作为token
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(nacosJwtSigningKey);
        return converter;
    }

    /**
     * 使用redis存储token的配置，hjh.oauth2.tokenStore配置为redis时生效
     * @author zhailiang
     *
     */
    @Configuration
    @ConditionalOnProperty(prefix = "tr.oauth2", name = "tokenStore", havingValue = "redis")
    public static class RedisConfig {

        @Autowired
        private RedisConnectionFactory redisConnectionFactory;

        /**
         * @return
         */
        @Bean
        public TokenStore redisTokenStore() {
            return new RedisTokenStore(redisConnectionFactory);
        }

    }

    /**
     * JwtTokenStore，默认生效 "matchIfMissing = true没有 hjh.oauth2.tokenStore=jwt这个配置也会生效”
     * @author zhailiang
     *
     */
    @Configuration
    @ConditionalOnProperty(prefix = "tr.oauth2", name = "tokenStore", havingValue = "jwt",
            matchIfMissing = true)
    public static class JwtConfig {

        @Autowired
        private JwtAccessTokenConverter jwtAccessTokenConverter;

        /**
         * @return
         */
        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter);
        }

    }
}
