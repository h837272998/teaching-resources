package cn.onb.tr.auth.support;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/12/2 16:35
 */
public interface JsonMapper {
    String write(Object input) throws Exception;

    <T> T read(String input, Class<T> type) throws Exception;
}
