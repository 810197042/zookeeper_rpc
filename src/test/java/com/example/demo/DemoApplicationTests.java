package com.example.demo;

import com.example.demo.config.ZookeeperConfig;
import com.example.demo.rpc.client.ClientProxy;
import com.example.demo.rpc.client.ServiceDiscovery;
import com.example.demo.rpc.common.Hello;
import com.example.demo.rpc.common.HelloImpl;
import com.example.demo.rpc.common.Invocation;
import com.example.demo.rpc.server.ServiceRegister;
import com.example.demo.zookeeperUtils.ZookeeperService;
import org.apache.commons.io.IOUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	//@Autowired
	//ZookeeperService zookeeperService;
	/*@Resource(name = "curatorClient")
	CuratorFramework client;
	@Autowired
	ServiceRegister serviceRegister;
	@Autowired
	ServiceDiscovery serviceDiscovery;
	@Autowired
	ClientProxy clientProxy;*/
	//@Autowired
	//ZookeeperService zookeeperService;
	/*@Test
	void contextLoads() throws Exception {
		List<String> a = serviceDiscovery.getServerNodes("a");
		for (String s : a) {
			System.out.println("迷哦查出是"+s);
		}
		System.out.println(clientProxy.process("a", "hello"));
		//ServiceRegister serviceRegister = new ServiceRegister();
		//serviceRegister.registerPersistNode("/rpc", "services");
		//String rpc = zookeeperService.createPersistentNode("/rpc", "rpc");
		//System.out.println(rpc);
		*//*CuratorFramework client = new ZookeeperConfig().curatorFramework();
		List<String> strings = client.getChildren().forPath("/");
		for (String string : strings) {
				System.out.println(string);
		}*//*
		//zookeeperService.createPersistentNode("/lok","as");
		//serviceRegister.registerPersistNode("/a", "a.class");
		//serviceRegister.registerPersistNode("/a/D:\\1\\b","D:\\1\\b");
	}*/
	@Autowired
	ZookeeperService zookeeperService;
	@Autowired
	ServiceRegister serviceRegister;
	@Test
	public void a() throws Exception {
		String xzc = serviceRegister.registerPersistNode("/czcmimc", "xzc");
		System.out.println(xzc);
		//zookeeperService.createPersistentNode("/rpc/Hello/127.0.0.1", "address1");
	}
	@Test
	public void watch() throws Exception {
		//client.create().creatingParentsIfNeeded().forPath("ckoamc");
		//client.start();
		//while (true) {}
		File file = new File("D:\\1");
		URL[] urls = {file.toURI().toURL()};
		URLClassLoader urlClassLoader = new URLClassLoader(urls);
		Class<?> a = urlClassLoader.loadClass("a");
		System.out.println(a);

	}
	@Test
	public void proxy() throws InstantiationException, IllegalAccessException {
		Object proxyInstance = Proxy.newProxyInstance(Hello.class.getClassLoader(), new Class[]{Hello.class}, new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				method.invoke(proxy,args);
				return "123";
			}
		});
		//Object o = proxyInstance.getClass().newInstance();
		System.out.println(proxyInstance);
	}
	@Test
	public void kk() throws IOException {
		Invocation invocation = new Invocation("Hello", "hello", new Class[]{String.class}, new Object[]{"cwx"});
		URL url = new URL("http", "127.0.0.1", 8080, "/");
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setDoOutput(true);

		// 配置
		OutputStream outputStream = httpURLConnection.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(outputStream);

		oos.writeObject(invocation);
		oos.flush();
		oos.close();

		InputStream inputStream = httpURLConnection.getInputStream();
		int length = inputStream.available();
		String result = IOUtils.toString(inputStream);
		System.out.println(result+length);
		/*int length = inputStream.available();
		while(length != httpURLConnection.getContentLength()){
			inputStream = httpURLConnection.getInputStream();
			length = inputStream.available();
			String result = IOUtils.toString(inputStream);
			System.out.println(result);
		}*/
	}

}
