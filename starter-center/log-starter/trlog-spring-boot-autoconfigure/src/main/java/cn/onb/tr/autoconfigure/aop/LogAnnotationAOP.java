package cn.onb.tr.autoconfigure.aop;


import cn.onb.tr.autoconfigure.annotation.LogAnnotation;
import cn.onb.tr.autoconfigure.service.LogService;
import cn.onb.tr.autoconfigure.util.LogUtil;
import cn.onb.tr.entity.SysLog;
import com.alibaba.druid.support.json.JSONUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description:
 * @Author: HJH
 * @Data: 2019-10-06 13:52
 */
@Aspect
@Order(-1) // 保证该AOP在@Transactional之前执行
public class LogAnnotationAOP {

    private static final Logger logger = LoggerFactory.getLogger(LogAnnotationAOP.class);

    @Autowired(required=false)
    private LogService logService ;

    @Around("@annotation(ds)")
    public Object logSave(ProceedingJoinPoint joinPoint, LogAnnotation ds) throws Throwable {

        // 请求流水号
        String transid = MDC.get(LogUtil.LOG_TRACE_ID);
        // 记录开始时间
        long start = System.currentTimeMillis();
        // 获取方法参数
        String url = null;
        String httpMethod = null;
        Object result = null;
        List<Object> httpReqArgs = new ArrayList<Object>();
        SysLog log = new SysLog();
        log.setCreateTime(new Date());

        //TODO
//        LoginAppUser loginAppUser = SysUserUtil.getLoginAppUserMe();
//
//
//        if (loginAppUser != null) {
//            log.setUsername(loginAppUser.getUsername());
//        }
        log.setUsername("Test");

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
        log.setModule(logAnnotation.module() + ":" + methodSignature.getDeclaringTypeName() + "/"
                + methodSignature.getName());
        // 参数值
        Object[] args = joinPoint.getArgs();
        url =  methodSignature.getDeclaringTypeName() + "/"+ methodSignature.getName();
        for (Object object : args) {
            if (object instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) object;
                url = request.getRequestURI();
                httpMethod = request.getMethod();
            } else if (object instanceof HttpServletResponse) {
            } else {

                httpReqArgs.add(object);
            }
        }

        try {
//            String params = JSONObject.toJSONString(httpReqArgs);
            String params = JSONUtils.toJSONString(httpReqArgs);
            log.setParams(params);
            // 打印请求参数参数
            logger.info("开始请求，transid={},  url={} , httpMethod={}, reqData={} ", transid, url, httpMethod, params);
        } catch (Exception e) {
            logger.error("记录参数失败：{}", e.getMessage());
        }

        try {
            // 调用原来的方法
            result = joinPoint.proceed();
            log.setFlag(Boolean.TRUE);
        } catch (Exception e) {
            log.setFlag(Boolean.FALSE);
            log.setRemark(e.getMessage());

            throw e;
        } finally {

            CompletableFuture.runAsync(() -> {
                try {
                    if (logAnnotation.recordRequestParam()) {
                        if(logService!=null){
                            logService.save(log);
                        }

                    }
                } catch (Exception e) {
                    logger.error("记录参数失败：{}", e.getMessage());
                }

            });
            // 获取回执报文及耗时
            logger.info("请求完成, transid={}, 耗时={}, resp={}:", transid, (System.currentTimeMillis() - start),
//                    result == null ? null : JSON.toJSONString(result));
                    result == null ? null : JSONUtils.toJSONString(result));

        }
        return result;
    }

    private int traceLen = 5;

    /**
     * 生成日志随机数
     *
     * @return String
     */
    public String getRandom() {
        int i = 0;
        StringBuilder st = new StringBuilder();
        while (i < traceLen) {
            i++;
            st.append(ThreadLocalRandom.current().nextInt(10));
        }
        return st.toString() + System.currentTimeMillis();
    }

}
