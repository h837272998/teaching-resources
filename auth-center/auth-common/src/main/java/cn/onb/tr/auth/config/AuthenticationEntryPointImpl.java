package cn.onb.tr.auth.config;

import cn.onb.tr.support.CodeEnum;
import cn.onb.tr.support.RespBean;
import cn.onb.tr.support.RespUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: hbanana-- 未登录，处理401
 * @Author: 、心
 * @Date: 2019/10/31 16:29
 */
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//        Map<String ,String > rsp =new HashMap<>();

        response.setStatus(HttpStatus.UNAUTHORIZED.value() );
        String msg = e.getMessage();
//        if (e instanceof InsufficientAuthenticationException) {
//            log.error(e.getMessage());
//            msg = "登录信息过期！";
//        }else {
//            log.error("类名： {}，信息：{}",e.getClass().getName(),e.getMessage());
//            msg = String.format("auth-server未知的Token错误, 原错误信息：%s。", e.getMessage());
//        }
        e.printStackTrace();
        log.info(e.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(RespBean.builder().code(CodeEnum.UNAUTHORIZED.getCode()).msg(msg).build()));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
