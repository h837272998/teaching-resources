package cn.onb.tr.api.entity;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description:断言实体类
 * @Author: HJH
 * @Date: 2019-09-22 18:04
 */
@Data
public class PredicateEntity {
    //断言对应的Name
    private String name;

    //断言规则
    private Map<String, String> args = new LinkedHashMap<>();
}
