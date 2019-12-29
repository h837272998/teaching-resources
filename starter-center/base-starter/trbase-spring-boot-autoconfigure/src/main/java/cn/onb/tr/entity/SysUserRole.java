package cn.onb.tr.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/12/1 23:58
 */
@Data
@Builder
public class SysUserRole {
    private String userId;
    private String roleId;
}
