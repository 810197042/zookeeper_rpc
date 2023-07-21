package com.example.demo.rpc.client;

import com.example.demo.rpc.common.Invocation;


import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/***
 @author  *
 @date 2023/7/20$ 16:00$*
 @description: */
public class NIOClient {
    public String send(String hostname, Integer port, Invocation invocation) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        //2.连接到远程服务器（连接此通道的socket）
        socketChannel.connect(new InetSocketAddress(hostname+"/", 8080));
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        JSONObject json = new JSONObject();
        json.put("interfaceName", invocation.getInterfaceName());
        json.put("methodName", invocation.getMethodName());
        json.put("parameters", invocation.getParameters());
        json.put("parameterTypes", invocation.getParameterTypes());
        json.put("class", invocation.getClass());
        writeBuffer.put(json.toJSONString().getBytes());
        writeBuffer.flip();
        socketChannel.write(writeBuffer);
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(readBuffer);
        //String 字符串常量，不可变；StringBuffer 字符串变量（线程安全），可变；StringBuilder 字符串变量（非线程安全），可变
        StringBuilder stringBuffer=new StringBuilder();
        //4.将Buffer从写模式变为可读模式
        readBuffer.flip();
        while (readBuffer.hasRemaining()) {
            stringBuffer.append((char) readBuffer.get());
        }
        System.out.println("从服务端接收到的数据："+stringBuffer.toString());
        socketChannel.close();
        return stringBuffer.toString();
    }
}
