package com.example.demo.rpc.server;

import com.example.demo.rpc.common.HelloImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/***
 @author  *
 @date 2023/7/18$ 21:40$*
 @description: */

@Component
public class LocalRegister {
    private Map<String, Class> localClassMap = new HashMap<>();

    @Autowired
    public LocalRegister() {
        localClassMap.put("Hello", HelloImpl.class);
    }
    public Class get(String interfaceName) {
        if (!localClassMap.containsKey(interfaceName)) {
            return null;
        }
        return localClassMap.get(interfaceName);
    }
}
