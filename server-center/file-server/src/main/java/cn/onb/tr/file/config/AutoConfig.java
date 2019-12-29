package cn.onb.tr.file.config;

import cn.onb.tr.support.NacosUtils;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.*;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/11 20:39
 */
@Slf4j
@Configuration
public class AutoConfig {

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String serverAddr;

    @Bean(name = "fileSystem")
    public FileSystem fileSystem() throws URISyntaxException, IOException, NacosException {
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        FileSystem fileSystem = null;
        String properties1 = NacosUtils.getProperties(serverAddr, "core-site.xml", "DEFAULT_GROUP");
        String properties2 = NacosUtils.getProperties(serverAddr, "hdfs-site.xml", "DEFAULT_GROUP");

        conf.addResource(properties1);
        conf.addResource(properties2);
        fileSystem = FileSystem.get(new URI("hdfs://master:9000"), conf);
        return fileSystem;
    }

    @Bean
    public Connection getConnection() throws IOException {
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "master");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set(HConstants.HBASE_RPC_TIMEOUT_KEY, "3600000");
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("hbase-pool-%d").build();
        ExecutorService executor = new ThreadPoolExecutor(20, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        return ConnectionFactory.createConnection(configuration, executor);
    }

    @Bean(name = "zkUrls")
    public String getZkUrls() {
        return "hjh";
    }
}
