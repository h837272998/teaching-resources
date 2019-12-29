package cn.onb.tr.auth.support;

import cn.onb.tr.support.NoPasswordEncoder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description: 将数据包表oauth_client_datails表缓存到redis。
 * @Author: HJH
 * @Data: 2019-10-11 10:41
 */
@Slf4j
@Component
@Primary
public class RedisClientDetailsService extends JdbcClientDetailsService {
    // 扩展 默认的 ClientDetailsService, 增加逻辑删除判断( status = 1)
    private static final String SELECT_CLIENT_DETAILS_SQL = "select client_id, client_secret, resource_ids, scope, authorized_grant_types, " +
            "web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove, original_client_secret " +
            "from oauth_client_details where client_id = ? and `status` = 1 ";


    private static final String SELECT_FIND_STATEMENT = "select client_id, client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove, original_client_secret from oauth_client_details where `status` = 1 order by client_id " ;

    private static final String DEFAULT_CACHE_CLIENT_KEY = "oauth_client_details";


    private RedisTemplate<String,Object> redisTemplate ;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    private ObjectMapper objectMapper;

    private final JdbcTemplate jdbcTemplate;

    public RedisClientDetailsService(DataSource dataSource, RedisTemplate redisTemplate) {
        super(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.redisTemplate = redisTemplate;
//        this.setPasswordEncoder(NoPasswordEncoder.getInstance());
        setSelectClientDetailsSql(SELECT_CLIENT_DETAILS_SQL);
        setFindClientDetailsSql(SELECT_FIND_STATEMENT);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails details = null;
        //1.从redis获取
        String value = (String) redisTemplate.boundHashOps(DEFAULT_CACHE_CLIENT_KEY).get(clientId);
        //2、如果不存在则从数据库获取并加入缓存，否则装换成ClientDetails对象
        if (StringUtils.isBlank(value)) {
            details = cacheAndGetClient(clientId);
        } else {
            details = JSONObject.parseObject(value, TrClientDetails.class);
        }

        return details;
    }

    /**
     * 从数据库加载并加入redis缓存
     * @param clientId
     * @return
     */
    private ClientDetails cacheAndGetClient(String clientId) {
        // 从数据库读取
        ClientDetails clientDetails = null ;

        try {
            try {
                clientDetails = jdbcTemplate.queryForObject(SELECT_CLIENT_DETAILS_SQL, new ClientDetailsRowMapper(), clientId);
            }
            catch (EmptyResultDataAccessException e) {
                throw new NoSuchClientException("No client with requested id: " + clientId);
            }

            if (clientDetails != null) {
                // 写入redis缓存
                redisTemplate.boundHashOps(DEFAULT_CACHE_CLIENT_KEY).put(clientId, JSONObject.toJSONString(clientDetails));
                log.info("缓存clientId:{},{}", clientId, clientDetails);
            }
        }catch (NoSuchClientException e){
            log.error("clientId:{},{}", clientId, clientId );
            throw new AuthenticationException("应用不存在"){};
        }catch (InvalidClientException e) {
            throw new AuthenticationException ("应用状态不合法"){};
        }

        return clientDetails;
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        super.updateClientDetails(clientDetails);
        cacheAndGetClient(clientDetails.getClientId());
    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        super.updateClientSecret(clientId, secret);
        cacheAndGetClient(clientId);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        super.removeClientDetails(clientId);
        removeRedisCache(clientId);
    }

    /**
     * 删除redis缓存
     *
     * @param clientId
     */
    private void removeRedisCache(String clientId) {
        redisTemplate.boundHashOps(DEFAULT_CACHE_CLIENT_KEY).delete(clientId);
    }

    /**
     * 将oauth_client_details全表刷入redis
     */
    public void loadAllClientToCache() {
        if (redisTemplate.hasKey(DEFAULT_CACHE_CLIENT_KEY)) {
            return;
        }
        log.info("将oauth_client_details全表刷入redis");

        List<ClientDetails> list = this.listClientDetails();
        if (CollectionUtils.isEmpty(list)) {
            log.error("oauth_client_details表数据为空，请检查");
            return;
        }

        list.parallelStream().forEach(client -> {
            redisTemplate.boundHashOps(DEFAULT_CACHE_CLIENT_KEY).put(client.getClientId(), JSONObject.toJSONString(client));
        });
    }


    /**
     * 追加if_limit  limit_count , original_client_secret
     *      * DefaultClientDetails
     *
     */
    @Override
    public List<ClientDetails> listClientDetails() {

        return  jdbcTemplate.query(SELECT_FIND_STATEMENT,  new ClientDetailsRowMapper());
    }

    private static class ClientDetailsRowMapper implements RowMapper<ClientDetails> {
        private JsonMapper mapper = createJsonMapper();

        @Override
        public ClientDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
            TrClientDetails details = new TrClientDetails(rs.getString(1), rs.getString(3),
                    rs.getString(4),
                    rs.getString(5), rs.getString(7), rs.getString(6));
            details.setClientSecret(rs.getString(2));
            if (rs.getObject(8) != null) {
                details.setAccessTokenValiditySeconds(rs.getInt(8));
            }
            if (rs.getObject(9) != null) {
                details.setRefreshTokenValiditySeconds(rs.getInt(9));
            }
            String json = rs.getString(10);
            if (json != null) {
                try {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> additionalInformation = mapper.read(json, Map.class);
                    details.setAdditionalInformation(additionalInformation);
                }
                catch (Exception e) {
                    log.warn("Could not decode JSON for additional information: " + details, e);
                }
            }
            String scopes = rs.getString(11);
            String originalSecret = rs.getString(12);
            details.setOriginalClientSecret(originalSecret);

//            long ifLimit = rs.getLong(12) ;
//            details.setIf_limit(ifLimit);
//
//            long limitCount = rs.getLong(13) ;
//            details.setLimit_count(limitCount);
            if (scopes != null) {
                details.setAutoApproveScopes(org.springframework.util.StringUtils.commaDelimitedListToSet(scopes));
            }
            return details;
        }
    }

    /**
     * json process
     * @return
     */
    private static JsonMapper createJsonMapper() {
        if (ClassUtils.isPresent("org.codehaus.jackson.map.ObjectMapper", null)) {
            return new JacksonMapper();
        }
        else if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", null)) {
            return new Jackson2Mapper();
        }
        return new NotSupportedJsonMapper();
    }
}
