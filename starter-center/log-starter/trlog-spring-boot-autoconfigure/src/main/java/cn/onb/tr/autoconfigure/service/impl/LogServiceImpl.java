package cn.onb.tr.autoconfigure.service.impl;

import cn.onb.tr.annotation.DataSource;
import cn.onb.tr.autoconfigure.dao.LogDao;
import cn.onb.tr.autoconfigure.service.LogService;
import cn.onb.tr.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;

/**
 * @Description: 切换数据源，存储log-center
 * @Author: HJH
 * @Date: 2019-09-27 23:25
 */
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Async
    @Override
    @DataSource(name="log")
    public void save(SysLog log) {
        if (log.getCreateTime() == null) {
            log.setCreateTime(new Date());
        }
        if (log.getFlag() == null) {
            log.setFlag(Boolean.TRUE);
        }

        logDao.save(log);
    }


}
