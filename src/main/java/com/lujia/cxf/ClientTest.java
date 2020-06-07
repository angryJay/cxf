package com.lujia.cxf;

import com.lujia.cxf.Interceptor.AuthAddInterceptor;
import com.lujia.cxf.service.HelloService;
import com.lujia.cxf.service.HelloServiceItf;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.awt.*;

public class ClientTest {
    //RMI方式调用，客户端直接服务器端提供的服务接口(interface),CXF通过运行时代理生成远程服务的代理对象
    //但是客户端也必须依赖服务器端的接口，这种调用方式限制是很大的，
    //要求服务器端的webservice必须是java实现
    //这样也就失去了使用webservice的意义，不推荐
    /*public static void main(String[] args) {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setAddress("http://localhost:8080/cxf/ws/demo");
        jaxWsProxyFactoryBean.setServiceClass(HelloServiceItf.class);
        HelloServiceItf demo = (HelloServiceItf) jaxWsProxyFactoryBean.create();
        System.out.println(demo.sayHello("hgx", 24));
    }*/

    //只要指定服务器端wsdl文件的位置，然后指定要调用的方法和方法的参数即可，
    //不关心服务端的实现方式,推荐使用
    public static void main(String[] args) {
        long a0 = System.currentTimeMillis();
        //创建JaxWsDynamicClientFactory作为调用客户端；
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        //下面的是接口wsdl的地址
        String wsUrl = "http://localhost:8080/cxf/ws/demo?wsdl";
        //这是需要调用的方法名字
        String method = "sayHello";
        long a1 = System.currentTimeMillis();
        System.err.println(a1-a0+"毫秒哟");
        try {
            //客户端调用wsdl地址 创建耗时，可放到缓存里或使用单例？？
            Client client = dcf.createClient(wsUrl);
            long a2 = System.currentTimeMillis();
            System.err.println(a2-a1+"毫秒");
            //设置超时时间，单位为毫秒
            HTTPConduit conduit = (HTTPConduit)client.getConduit();
            HTTPClientPolicy policy = new HTTPClientPolicy();
            policy.setConnectionTimeout(15000);
            policy.setAllowChunking(false);
            policy.setReceiveTimeout(30000);
            conduit.setClient(policy);
            //通添加拦截器
            client.getInInterceptors().add(new LoggingInInterceptor());
            client.getOutInterceptors().add(new AuthAddInterceptor("admin","admin"));
            //把webservice接口的方法放入 后面为请求参数 xml格式的 后面的参数看传参是上面类型的，
            //我的是string类型，经过测试，后面其实写什么都行，只要类型一样就行
            Object[] objects = client.invoke(method, "1ujia",271);
            System.out.println(objects[0].toString());
            long a3 = System.currentTimeMillis();
            System.err.println(a3-a2+"毫秒呀");
            Object[] objects2 = client.invoke(method, "1ujia",272);
            System.out.println(objects2[0].toString());
            Object[] objects3 = client.invoke(method, "1ujia",273);
            System.out.println(objects3[0].toString());
            new Thread(){
                @Override
                public void run(){
                    try {
                        Object[] objects4 = client.invoke(method, "1ujia",274);
                        System.out.println(objects4[0].toString());
                        Object[] objects5 = client.invoke(method, "1ujia",275);
                        System.out.println(objects5[0].toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            new Thread(){
                @Override
                public void run(){
                    try {
                        Object[] objects8 = client.invoke(method, "1ujia",278);
                        System.out.println(objects8[0].toString());
                        Object[] objects9 = client.invoke(method, "1ujia",279);
                        System.out.println(objects9[0].toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            Object[] objects6 = client.invoke(method, "1ujia",276);
            System.out.println(objects6[0].toString());
            Object[] objects7 = client.invoke(method, "1ujia",277);
            System.out.println(objects7[0].toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
