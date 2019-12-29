package cn.onb.tr.auth.core.social;

import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/11/3 17:06
 */
public class HSpringSocialConfigurer extends SpringSocialConfigurer {
    private String filterProcessesUrl;

    public HSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    /**
     * Performs post processing of an object. The default is to delegate to the
     * {@link ObjectPostProcessor}.
     *
     * @param object the Object to post process
     * @return the possibly modified Object to use
     */
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) filter;
    }
}
