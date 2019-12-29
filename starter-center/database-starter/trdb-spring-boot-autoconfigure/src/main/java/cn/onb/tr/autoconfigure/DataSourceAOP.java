package cn.onb.tr.autoconfigure;


import cn.onb.tr.annotation.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

/**
 * @Describe: 切换数据源
 * @Author: 、心
 * @Date： 2019/10/31
 */
@Slf4j
@Aspect
@Order(-1)//保证AOP在@Transactional之前执行
public class DataSourceAOP {

    @Before("@annotation(db)")
    public void changeDataSource(JoinPoint point, DataSource db) {
        String dbName = db.name();

        try {
            DataSourceKey datasourceKey = DataSourceKey.valueOf(dbName);
            DataSourceHolder.setDataSourceKey(datasourceKey);
        } catch (Exception e) {
            log.error("数据源[{}]不存在，使用默认数据源 > {}", db.name(), point.getSignature());
        }
    }

    @After("@annotation(db)")
    public void restoreDataSource(JoinPoint point, DataSource db) {
        log.error("Revert DtaSource : {transIdo} > {}", db.name(), point.getSignature());
        DataSourceHolder.clearDataSourceKey();
    }

}
