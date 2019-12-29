package cn.onb.tr.user.mapper;

import cn.onb.tr.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/6 21:10
 */
public interface ISysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 获得用户的角色
     * @param userId
     * @return
     */
    List<SysRole> getByUser(@Param("userId") Long userId);
}
