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

/***
 @author  *
 @date 2023/6/18$ 21:27$*
 @description: */
@Component
@Slf4j
public class Interceptor implements HandlerInterceptor {

    @Autowired
    LocalRegister localRegister;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            Invocation invocation = (Invocation) new ObjectInputStream(request.getInputStream()).readObject();
            String interfaceName = invocation.getInterfaceName();
            Class classImpl = localRegister.get(interfaceName);
            Method method = classImpl.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
            String result = (String) method.invoke(classImpl.newInstance(), invocation.getParameters());

            //String result = "anxzczc";
            //Object result = new ObjectInputStream(request.getInputStream()).readObject();
           // IOUtils.write(result, response.getOutputStream());
            response.getOutputStream().write(result.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
          catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return true;
    }
}
