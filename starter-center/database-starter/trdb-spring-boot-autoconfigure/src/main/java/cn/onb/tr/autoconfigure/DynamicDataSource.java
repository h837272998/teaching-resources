package cn.onb.tr.autoconfigure;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Describe: spring动态数据源（需要继承AbstractRoutingDataSource）
 * @Author: 、心
 * @Date： 2019/10/31
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> datasources;

    public DynamicDataSource() {
        datasources = new HashMap<>();
        super.setTargetDataSources(datasources);
    }

    /**
     * 添加数据源
     *
     * @param dataSourceKey
     * @param data
     * @param <T>
     */
    public <T extends DataSource> void addDataSource(DataSourceKey dataSourceKey, T data) {
        datasources.put(dataSourceKey, data);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getDataSourceKey();
    }

}
