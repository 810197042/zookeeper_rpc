package com.example.demo.rpc.server;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/***
 @author  *
 @date 2023/7/18$ 16:24$*
 @description: */
@Component
public class LoadBalance {
    private Random random;
    public LoadBalance() {
        this.random = new Random();
    }
    public String getAddress(List<String> addresses) {
        int i = random.nextInt(addresses.size());
        return addresses.get(i);
    }
}
