package cn.onb.tr.autoconfigure.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description: 日志埋点工具类
 * @Author: HJH
 * @Date: 2019-09-27 23:27
 */
@Slf4j
public class LogUtil {
    /**
     * 日志跟踪id名。
     */
    public static final String LOG_TRACE_ID = "traceid";

    /**
     * 请求头跟踪id名。
     */
    public static final String HTTP_HEADER_TRACE_ID = "app_trace_id";

    /**
     * 生成日志随机数
     * @return String
     */
    public static String getTraceId() {
        int i = 0;
        StringBuilder st = new StringBuilder();
        while (i < 5) {
            i++;
            st.append(ThreadLocalRandom.current().nextInt(10));
        }
        return st.toString() + System.currentTimeMillis();
    }

}

