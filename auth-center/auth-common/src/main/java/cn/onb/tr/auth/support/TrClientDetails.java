package cn.onb.tr.auth.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * @Description: 主要添加字段originalClientSecret(没有加密的密码)。
 * 原因：  希望系统获取Client 时能够获得没有加密的密码，然后便于系统重新刷新Token。
 * @Author: 、心
 * @Date: 2019/10/31 17:17
 */
@ToString
@EqualsAndHashCode
public class TrClientDetails extends BaseClientDetails {

    @JsonProperty("original_client_secret")
    @com.fasterxml.jackson.annotation.JsonProperty("original_client_secret")
    private String originalClientSecret;

    public TrClientDetails(String clientId, String resourceIds, String scopes, String grantTypes, String authorities, String redirectUris, String originalClientSecret) {
        super(clientId, resourceIds, scopes, grantTypes, authorities, redirectUris);
        this.originalClientSecret = originalClientSecret;
    }

    public TrClientDetails(ClientDetails prototype, String originalClientSecret) {
        super(prototype);
        this.originalClientSecret = originalClientSecret;
    }



    public TrClientDetails(String clientId, String resourceIds, String scopes, String grantTypes, String authorities, String redirectUris) {
        super(clientId, resourceIds, scopes, grantTypes, authorities, redirectUris);
    }

    public TrClientDetails() {
        super();
    }

    public TrClientDetails(String originalClientSecret) {
        this.originalClientSecret = originalClientSecret;
    }

    @JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public String getOriginalClientSecret() {
        return originalClientSecret;
    }

    public void setOriginalClientSecret(String originalClientSecret) {
        this.originalClientSecret = originalClientSecret;
    }
}
