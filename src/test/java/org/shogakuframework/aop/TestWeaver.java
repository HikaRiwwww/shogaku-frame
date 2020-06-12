package org.shogakuframework.aop;

import com.throne.controller.aop_demo.AopDemo;
import org.junit.Before;
import org.junit.Test;
import org.shogakuframework.core.BeanContainer;
import org.shogakuframework.inject.DependencyInjector;

public class TestWeaver {
    private BeanContainer beanContainer;


    @Test
    public void testWeaver() throws Exception {
        beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.throne", true);
        new Weaver().doAop();
        new DependencyInjector().doIoc(true);
        AopDemo bean = (AopDemo) beanContainer.getBean(AopDemo.class);
        bean.nonsense();

    }
}