package cn.onb.tr.auth.server.service.impl;

import cn.onb.tr.auth.server.clients.UserFeignClient;
import cn.onb.tr.entity.UserDetailsImpl;
import cn.onb.tr.exception.FeignException;
import cn.onb.tr.support.CodeEnum;
import cn.onb.tr.support.RespBean;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/12/1 20:38
 */
@Slf4j
@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 重写加载用户接口
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetailsImpl user = null;
        if (httpServletRequest != null && StringUtils.containsIgnoreCase(httpServletRequest.getRequestURI(),"mobile")) {
            RespBean<UserDetailsImpl> result = userFeignClient.getUserByPhone(s);
            checkRespBean(result);
            return result.getData();
        } else {
            RespBean<UserDetailsImpl> result = userFeignClient.getUserByUsername(s);
            checkRespBean(result);
            return result.getData();
        }
    }

    /**
     * 检查feign结果
     * @param bean
     */
    private void checkRespBean(RespBean bean) {
        boolean isNull = bean.getData() == null;
        boolean isWrong = CodeEnum.ERROR.getCode().equals(bean.getCode());

        if (isWrong) {
            throw new FeignException("服务器繁忙！");
        }

        if (isNull) {
            throw new UsernameNotFoundException("用户不存在！");
        }


    }
}
