package com.example.demo.rpc.client;

import com.example.demo.rpc.client.ServiceDiscovery;
import com.example.demo.rpc.common.Invocation;
import com.example.demo.rpc.server.LoadBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/***
 @author  *
 @date 2023/7/17$ 22:48$*
 @description: */
@Component
public class ClientProxy {
    @Autowired
    LoadBalance loadBalance;
    @Autowired
    ServiceDiscovery serviceDiscovery;
    public Object process(Invocation invocation) throws Exception {
        List<String> serverNodes = serviceDiscovery.getServerNodes(invocation.getInterfaceName());
        //负载均衡选一//
        String address = loadBalance.getAddress(serverNodes);
        String result = new HttpClient().send(address, 8080, invocation);
        //String result = new NIOClient().send(address, 8080, invocation);
        return result;
    }

    /*public Object invokeMethod(String address, String className, String methodName) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        File file = new File(address);
        URL[] urls = {file.toURI().toURL()};
        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        Class<?> a = urlClassLoader.loadClass(className);
        Object o = a.newInstance();
        Method hello = o.getClass().getMethod(methodName);
        hello.setAccessible(true);
        Object invoke = hello.invoke(o);
        return invoke;
    }*/

    /*public String downloadNet(String address, String className, String methodName) throws MalformedURLException {
            // 下载网络文件
        int bytesum = 0;
        int byteread = 0;
            //文件网络地址
        URL url = new URL(address);
        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream("d:/a/a.class"); //存储本地位置

            byte[] buffer = new byte[2048];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "123";
    }*/
}
