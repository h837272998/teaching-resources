package cn.onb.tr.autoconfigure;

/**
 * @Describe: 数据源持有者，用于切换数据库
 * @Author: 、心
 * @Date： 2019/10/31
 */
public class DataSourceHolder {
    //注意使用ThreadLocal，微服务下,建议使用信号量
    private static final ThreadLocal<DataSourceKey> DATA_SOURCE_KEY_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获得当期连接的数据库
     * @return
     */
    public static DataSourceKey getDataSourceKey() {
        return DATA_SOURCE_KEY_THREAD_LOCAL.get();
    }

    /**
     * 设置数据库连接
     * @return
     */
    public static void setDataSourceKey(DataSourceKey dataSourceKey) {
        DATA_SOURCE_KEY_THREAD_LOCAL.set(dataSourceKey);
    }


    public static void clearDataSourceKey() {
        DATA_SOURCE_KEY_THREAD_LOCAL.remove();
    }
}
