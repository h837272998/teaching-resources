package cn.onb.tr.api.entity;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description:过滤器实体类
 * @Author: HJH
 * @Date: 2019-09-22 18:04
 */
@Data
public class FilterEntity {

    //过滤器对应的Name
    private String name;

    //路由规则
    private Map<String, String> args = new LinkedHashMap<>();
}
