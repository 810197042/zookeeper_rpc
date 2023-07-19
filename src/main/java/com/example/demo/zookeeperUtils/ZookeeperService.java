package com.example.demo.zookeeperUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class ZookeeperService {

    @Resource(name = "curatorClient")
    private CuratorFramework client;



    /**
     * 创建永久Zookeeper节点
     *
     * @param nodePath  节点路径（如果父节点不存在则会自动创建父节点），如：/curator
     * @param nodeValue 节点数据
     * @return 返回创建成功的节点路径
     */
    public String createPersistentNode(String nodePath, String nodeValue) throws Exception {
        try {
            String s = client.create().creatingParentsIfNeeded().forPath(nodePath, nodeValue.getBytes());
            return s;
        } catch (Exception e) {
            log.error("创建永久Zookeeper节点失败,nodePath:{},nodeValue:{}", nodePath, nodeValue, e);
            throw e;
        }
    }

    /**
     * 创建永久有序Zookeeper节点
     *
     * @param nodePath  节点路径（如果父节点不存在则会自动创建父节点），如：/curator
     * @param nodeValue 节点数据
     * @return 返回创建成功的节点路径
     */
    public String createSequentialPersistentNode(String nodePath, String nodeValue) throws Exception {
        try {
            String s = client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(nodePath, nodeValue.getBytes());
            return s;
        } catch (Exception e) {
            throw e;
            //log.error("创建永久有序Zookeeper节点失败,nodePath:{},nodeValue:{}", nodePath, nodeValue, e);
        }
    }

    /**
     * 创建临时Zookeeper节点
     *
     * @param nodePath  节点路径（如果父节点不存在则会自动创建父节点），如：/curator
     * @param nodeValue 节点数据
     * @return 返回创建成功的节点路径
     */
    public String createEphemeralNode(String nodePath, String nodeValue) throws Exception {
        try {
            String s = client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(nodePath, nodeValue.getBytes());
            return s;
        } catch (Exception e) {
            log.error("创建临时Zookeeper节点失败,nodePath:{},nodeValue:{}", nodePath, nodeValue, e);
            throw e;
        }
    }

    /**
     * 创建临时有序Zookeeper节点
     *
     * @param nodePath  节点路径（如果父节点不存在则会自动创建父节点），如：/curator
     * @param nodeValue 节点数据
     * @return 返回创建成功的节点路径
     * @since 1.0.0
     */
    public String createSequentialEphemeralNode(String nodePath, String nodeValue) throws Exception {
        try {
            String s = client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(nodePath, nodeValue.getBytes());
            return s;
        } catch (Exception e) {
            log.error("创建临时有序Zookeeper节点失败,nodePath:{},nodeValue:{}", nodePath, nodeValue, e);
            throw e;
        }

    }

    /**
     * 检查Zookeeper节点是否存在
     *
     * @param nodePath 节点路径
     * @return boolean 如果存在则返回true
     */
    public boolean checkExists(String nodePath) throws Exception {
        try {
            Stat stat = client.checkExists().forPath(nodePath);
            return stat != null;
        } catch (Exception e) {
            log.error("检查Zookeeper节点是否存在出现异常,nodePath:{}", nodePath, e);
            throw e;
        }
    }

    /**
     * 获取某个Zookeeper节点的所有子节点
     *
     * @param nodePath 节点路径
     * @return 返回所有子节点的节点名
     */
    public List<String> getChildren(String nodePath) throws Exception {
        try {
            List<String> strings = client.getChildren().forPath(nodePath);
            return strings;
        } catch (Exception e) {
            log.error("获取某个Zookeeper节点的所有子节点出现异常,nodePath:{}", nodePath, e);
            throw e;
        }
    }

    /**
     * 获取某个Zookeeper节点的数据
     *
     * @param nodePath 节点路径
     * @return 节点存储的数据
     */
    public String getData(String nodePath) throws Exception {
        try {
            String s = new String(client.getData().forPath(nodePath));
            return s;
        } catch (Exception e) {
            log.error("获取某个Zookeeper节点的数据出现异常,nodePath:{}", nodePath, e);
            throw e;
        }
    }

    /**
     * 设置某个Zookeeper节点的数据
     *
     * @param nodePath 节点路径
     */
    public void setData(String nodePath, String newNodeValue) throws Exception {
        try {
            Stat stat = client.setData().forPath(nodePath, newNodeValue.getBytes());
        } catch (Exception e) {
            log.error("设置某个Zookeeper节点的数据出现异常,nodePath:{}", nodePath, e);
            throw e;
        }
    }

    /**
     * 删除某个Zookeeper节点
     *
     * @param nodePath 节点路径
     */
    public void delete(String nodePath) throws Exception {
        try {
            client.delete().guaranteed().forPath(nodePath);
        } catch (Exception e) {
            log.error("删除某个Zookeeper节点出现异常,nodePath:{}", nodePath, e);
            throw e;
        }
    }

    /**
     * 级联删除某个Zookeeper节点及其子节点
     *
     * @param nodePath 节点路径
     */
    public void deleteChildrenIfNeeded(String nodePath) throws Exception {
        try {
            client.delete().guaranteed().deletingChildrenIfNeeded().forPath(nodePath);
        } catch (Exception e) {
            log.error("级联删除某个Zookeeper节点及其子节点出现异常,nodePath:{}", nodePath, e);
            throw e;
        }
    }


}