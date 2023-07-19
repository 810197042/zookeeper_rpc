package com.example.demo.rpc.server;

import com.example.demo.zookeeperUtils.ZookeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 @author  *
 @date 2023/7/17$ 15:33$*
 @description: */
@RestController
public class ServiceRegister {

    @Autowired
    ZookeeperService zookeeperService;

    private final String pre = "/rpc";

    @RequestMapping("/registerPersistNode")
    public String registerPersistNode(String path, String value) {
        try {
            zookeeperService.createPersistentNode(pre + path, value);
        }
        catch (Exception e) {
            return "error";
        }
        return "succeed";
    }

    @RequestMapping("/")
    public void index() {

    }

    @RequestMapping("/registerEphemeralNode")
    public String registerEphemeralNode(String path, String value) throws Exception {
        try {
            zookeeperService.createEphemeralNode(pre+path, value);
        }
        catch (Exception e) {
            return "error";
        }
        return "succeed";
    }
}
