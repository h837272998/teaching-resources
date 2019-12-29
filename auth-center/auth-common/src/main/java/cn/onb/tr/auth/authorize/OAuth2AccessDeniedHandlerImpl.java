package cn.onb.tr.auth.authorize;

import cn.onb.tr.support.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: (onb)->处理spring security oauth 处理失败返回消息格式
 * @Author: 、心
 * @Date: 19/12/22 13:20
 */
@Slf4j
public class OAuth2AccessDeniedHandlerImpl extends OAuth2AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        //                Map<String, String> rsp = new HashMap<>();
        response.setContentType("application/json;charset=UTF-8");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                rsp.put("code", HttpStatus.UNAUTHORIZED.value() + "") ;
        log.info(authException.getMessage());
        RespBean<Object> build = new RespBean<>().builder().code(HttpStatus.UNAUTHORIZED.value()).msg(authException.getMessage()).build();

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(build));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
