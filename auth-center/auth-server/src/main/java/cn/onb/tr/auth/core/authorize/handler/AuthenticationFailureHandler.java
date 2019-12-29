package cn.onb.tr.auth.core.authorize.handler;

import cn.onb.tr.support.RespBean;
import cn.onb.tr.support.RespUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: hbanana--密码错误处理器
 * @Author: 、心
 * @Date: 2019/10/31 17:12
 */
@Slf4j
@Primary
@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        response.setContentType("application/json;charset=utf-8");
        RespBean respBean;
        if (exception instanceof UsernameNotFoundException) {
            respBean = RespUtil.failed("用户不存在!");
        } else if (exception instanceof BadCredentialsException) {
            respBean = RespUtil.failed("账户名或者密码输入错误!");
        } else if (exception instanceof LockedException) {
            respBean = RespUtil.failed("账户被锁定，请联系管理员!");
        } else if (exception instanceof CredentialsExpiredException) {
            respBean = RespUtil.failed("密码过期，请联系管理员!");
        } else if (exception instanceof AccountExpiredException) {
            respBean = RespUtil.failed("账户过期，请联系管理员!");
        } else if (exception instanceof DisabledException) {
            respBean = RespUtil.failed("账户被禁用，请联系管理员!");
        } else if (exception instanceof InternalAuthenticationServiceException) {
            respBean = RespUtil.failed("服务器繁忙！");
            log.info(exception.getMessage());
        } else {
//            respBean = RespBean.failed("未知错误，登录失败!");
            respBean = RespUtil.failed(exception.getMessage());
        }
        response.getWriter().write(objectMapper.writeValueAsString(respBean));

    }
}
