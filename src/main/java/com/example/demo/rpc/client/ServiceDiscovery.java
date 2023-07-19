package com.example.demo.rpc.client;

import com.example.demo.zookeeperUtils.ZookeeperService;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/***
 @author  *
 @date 2023/7/17$ 16:02$*
 @description: */
@Component
public class ServiceDiscovery {
    @Autowired
    ZookeeperService zookeeperService;


    private final String pre = "/rpc/";
    public List<String> getServerNodes(String serviceName) throws Exception {
        if (ServiceAddressMap.containsService(serviceName)) {
            return ServiceAddressMap.get(serviceName);
        }
        List<String> children = zookeeperService.getChildren(pre + serviceName);
        return children;
    }

}
