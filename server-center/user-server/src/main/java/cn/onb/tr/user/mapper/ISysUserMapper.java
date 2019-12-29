package cn.onb.tr.user.mapper;

import cn.onb.tr.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/6 19:47
 */
public interface ISysUserMapper extends BaseMapper<SysUser>{

    /**
     * 检查用户是否存在
     * @param type
     * @param value
     * @return
     */
    boolean check(@Param("type") String type, @Param("value") String value);

}
