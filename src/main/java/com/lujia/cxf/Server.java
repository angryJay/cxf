package com.lujia.cxf;

import com.lujia.cxf.service.HelloService;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class Server {
    //依赖jetty容器的发布方式
    public static void main(String[] args) {
        /*JaxWsServerFactoryBean jaxWsServerFactoryBean = new JaxWsServerFactoryBean();
        jaxWsServerFactoryBean.setAddress("http://localhost:8080/demo");
        jaxWsServerFactoryBean.setServiceClass(HelloService.class);
        org.apache.cxf.endpoint.Server server = jaxWsServerFactoryBean.create();
        server.start();
        System.out.println("demo服务启动 。。。。");*/
    }
}
