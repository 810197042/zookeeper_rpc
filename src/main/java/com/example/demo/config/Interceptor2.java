package com.example.demo.config;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.rpc.common.Invocation;
import com.example.demo.rpc.server.LocalRegister;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/***
 @author  *
 @date 2023/6/18$ 21:27$*
 @description: */
@Component
@Slf4j
public class Interceptor2 implements HandlerInterceptor {

    @Autowired
    LocalRegister localRegister;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            //1.通过ServerSocketChannel 的open()方法创建一个ServerSocketChannel对象，open方法的作用：打开套接字通道
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //2.通过ServerSocketChannel绑定ip地址和port(端口号)
            ssc.socket().bind(new InetSocketAddress("127.0.0.1", 8080));
            //通过ServerSocketChannelImpl的accept()方法创建一个SocketChannel对象用户从客户端读/写数据
            SocketChannel socketChannel = ssc.accept();
            //创建读数据的缓存区对象
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            //读取缓存区数据
            socketChannel.read(readBuffer);
            StringBuilder stringBuffer = new StringBuilder();
            //3.将Buffer从写模式变为可读模式
            readBuffer.flip();
            while (readBuffer.hasRemaining()) {
                stringBuffer.append((char) readBuffer.get());
            }
            String jsonString = stringBuffer.toString();
            System.out.println("从客户端接收到的数据："+jsonString);
            JSONObject json = (JSONObject) JSONObject.parse(jsonString);
            String interfaceName = (String) json.get("intefaceName");
            Class classImpl = localRegister.get(interfaceName);
            Method method = classImpl.getMethod((String) json.get("methodName"), (Class<?>) json.get("paramtersType"));
            String result = (String) method.invoke(classImpl.newInstance(), json.get("parameTers"));
            //4.创建写数据的缓存区对象
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            writeBuffer.put(result.getBytes());
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
            socketChannel.close();
            ssc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
