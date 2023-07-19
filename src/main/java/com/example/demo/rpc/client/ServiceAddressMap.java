package com.example.demo.rpc.client;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 @author  *
 @date 2023/7/17$ 16:20$*
 @description: */


public class ServiceAddressMap {
    private static Map<String, List<String>> serviceNameToServiceList = new HashMap<>();
    public static void put(String serviceName, List<String> serviceList) {
        deleteService(serviceName);
        serviceNameToServiceList.put(serviceName, serviceList);
    }
    public static boolean containsService(String serviceName) {
        return serviceNameToServiceList.containsKey(serviceName);
    }
    public static void deleteService(String serviceName) {
        if (containsService(serviceName)) {
            serviceNameToServiceList.remove(serviceName);
        }
    }

    public static List<String> get(String serviceName) {
        if (containsService(serviceName)) {
            return serviceNameToServiceList.get(serviceName);
        }
        return null;
    }
}
