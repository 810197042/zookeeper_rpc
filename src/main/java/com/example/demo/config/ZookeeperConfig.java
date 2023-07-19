package com.example.demo.config;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname ZookeeperConfig
 * @Description TODO
 * @Date 2021/7/13 11:08
 * @Created by zlx
 */
@Configuration
public class ZookeeperConfig {
    private String zkAddress = "192.168.116.131:2182,192.168.116.131:2183,192.168.116.131:2184"; //地址和端口号192 .168 .23 .129:2181, 192.168 .23 .128:2181
    private int sessionTimeout = 60000;//会话超时时间 单位ms
    private int connectionTimeout = 30000;//连接超时时间 单位ms
    //private String nameSpace = "zjzyzlx";//默认为操作的根节点，为了实现不同的Zookeeper业务之间的隔离，所有操作都是基于该目录进行的
    //private String lockNode = "/123";//默认为操作的根节点，为了实现不同的Zookeeper业务之间的隔离，所有操作都是基于该目录进行的
    private int baseSleepTimeMs = 3000;//重试间隔时间
    private int maxRetries = 10;//重试次数

    @Bean("curatorClient")
    public CuratorFramework curatorFramework() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(zkAddress)
                .sessionTimeoutMs(sessionTimeout)
                .connectionTimeoutMs(connectionTimeout)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        //client.blockUntilConnected();
        ZookeeperWatches watches = new ZookeeperWatches(client);
        watches.znodeWatcher();
        watches.znodeChildrenWatcher();
        return client;
    }
}