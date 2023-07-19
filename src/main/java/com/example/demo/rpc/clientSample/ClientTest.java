package com.example.demo.rpc.clientSample;

import com.example.demo.rpc.client.ClientProxy;
import com.example.demo.rpc.client.HttpClient;
import com.example.demo.rpc.common.Invocation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/***
 @author  *
 @date 2023/7/17$ 23:03$*
 @description: */
@RestController
public class ClientTest {
    @Autowired
    ClientProxy clientProxy;

    @RequestMapping("/clientInvoke")
    public String clientInvoke() throws Exception {
        HttpClient httpClient = new HttpClient();
        Invocation invocation = new Invocation("Hello", "hello", new Class[]{String.class}, new Object[]{"cwx"});
        String result = (String) clientProxy.process(invocation);
        return result;
    }
}
