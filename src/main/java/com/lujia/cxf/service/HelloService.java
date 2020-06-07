package com.lujia.cxf.service;

public class HelloService implements HelloServiceItf {

    @Override
    public String sayHello(String name, int age) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello!!!" + name + "(" + age + " years old)";
    }
}
