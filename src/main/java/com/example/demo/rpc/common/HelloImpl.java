package com.example.demo.rpc.common;

/***
 @author  *
 @date 2023/7/18$ 21:03$*
 @description: */
public class HelloImpl implements Hello{
    @Override
    public String hello(String name) {
        return "hi,"+name;
    }
}
