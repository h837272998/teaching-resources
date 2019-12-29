package cn.onb.tr.auth.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Description: hbanana--
 * @Author: 、心
 * @Date: 2019/10/31 17:19
 */
@SuppressWarnings({"ALL", "AlibabaServiceOrDaoClassShouldEndWithImpl"})
@Slf4j
public class DefaultUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error("系统采用默认的UserDetailsService，请配置实现UserDetailsService的实例，并注入！");
        throw new UsernameNotFoundException("没找到UserDetailsService");
    }
}
