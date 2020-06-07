package com.lujia.cxf.service;

import javax.jws.WebService;

@WebService
public interface HelloServiceItf {
    String sayHello(String name, int age);
}
