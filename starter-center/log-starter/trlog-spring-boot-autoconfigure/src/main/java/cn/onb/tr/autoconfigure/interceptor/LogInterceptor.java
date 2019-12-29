package cn.onb.tr.autoconfigure.interceptor;

import cn.onb.tr.autoconfigure.util.LogUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 首先创建拦截器，加入拦截列表中，在请求到达时生成traceId。
 * @Author: HJH
 * @Date: 2019-09-27 23:23
 */
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // "traceId"

        String traceId = request.getHeader(LogUtil.HTTP_HEADER_TRACE_ID);
        traceId = "a test";
        if (StringUtils.isNotEmpty(traceId)) {
            MDC.put(LogUtil.LOG_TRACE_ID, traceId);
        }

        return true;
    }
}
