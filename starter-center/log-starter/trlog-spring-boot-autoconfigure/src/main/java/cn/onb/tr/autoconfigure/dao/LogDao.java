package cn.onb.tr.autoconfigure.dao;

import cn.onb.tr.entity.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.sql.DataSource;

/**
 * @Description: 保存日志
 * eureka-server配置不需要datasource,不会装配bean
 * @Author: HJH
 * @Date: 2019-09-27 23:22
 */
@Mapper
@ConditionalOnBean(DataSource.class)
public interface LogDao {

    @Insert("insert into sys_log(username, module, params, remark, flag, createTime) values(#{username}, #{module}, #{params}, #{remark}, #{flag}, #{createTime})")
    int save(SysLog log);

}
