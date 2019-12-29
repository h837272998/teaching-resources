package cn.onb.tr.user.mapper;

import cn.onb.tr.entity.SysPermission;
import cn.onb.tr.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/6 21:11
 */
public interface ISysPremissionMapper extends BaseMapper<SysPermission> {

    Set<SysPermission> getByRoles(@Param("roleIds") Set<Long> roleIds);
}
