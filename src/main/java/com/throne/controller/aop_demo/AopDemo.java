package com.throne.controller.aop_demo;

import org.shogakuframework.core.annotations.Controller;

@Controller
public class AopDemo {
    public void nonsense(){
        System.out.println("一个Controller执行了");
    }
}