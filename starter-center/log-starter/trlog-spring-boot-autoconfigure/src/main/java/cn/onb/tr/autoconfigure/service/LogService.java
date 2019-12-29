package cn.onb.tr.autoconfigure.service;


import cn.onb.tr.entity.SysLog;

/**
 * @Description:
 * @Author: HJH
 * @Date: 2019-09-27 23:25
 */
public interface LogService {
    void save(SysLog log);
}
