package cn.onb.tr.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/11/29 19:15
 */
@Slf4j
@Configuration
@AutoConfigureBefore(RedisTemplate.class)
@EnableConfigurationProperties(RedisPorperties.class)
public class RedisAutoConfiguration {

    @Autowired(required=false)
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * 适配redis cluster单节点
     * @return
     */
    @Primary
    @Bean("redisTemplate")
    // 没有此属性就不会装配bean 如果是单个redis 将此注解注释掉
    @ConditionalOnProperty(name = "spring.redis.cluster.nodes", matchIfMissing = false)
    public RedisTemplate<String, Object> getRedisTemplate() {
        log.info("---------------------------------");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);

        RedisSerializer stringSerializer = new StringRedisSerializer();
        // RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
        RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
        // key的序列化类型
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        // value的序列化类型
        redisTemplate.setValueSerializer(redisObjectSerializer);
        // value的序列化类型
        redisTemplate.setHashValueSerializer(redisObjectSerializer);
        redisTemplate.afterPropertiesSet();

        redisTemplate.opsForValue().set("hello", "wolrd");
        return redisTemplate;
    }
    /**
     * 适配redis单节点
     * @return
     */
    @Primary
    @Bean("redisTemplate")
    @ConditionalOnProperty(name = "spring.redis.host", matchIfMissing = true)
    public RedisTemplate<String, Object> getSingleRedisTemplate( ) {
        log.info("=========================================");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        // key的序列化类型
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // value的序列化类型
        redisTemplate.setValueSerializer(redisObjectSerializer);
        redisTemplate.setHashValueSerializer(redisObjectSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Autowired
    private RedisPorperties redisPorperties;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(redisPorperties.getExpired())).disableCachingNullValues();
        return RedisCacheManager.builder(RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory)).cacheDefaults(cacheConfiguration).build();
    }


    @Bean
    public HashOperations<String, String, String> hashOperations(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForHash();
    }

}
