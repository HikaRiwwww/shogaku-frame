package org.shogakuframework.core;

import com.throne.entity.bo.HeadLine;
import com.throne.service.solo.HeadLineService;
import com.throne.service.solo.impl.HeadLineServiceImpl;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.shogakuframework.core.annotations.Component;
import org.shogakuframework.core.annotations.Service;

import java.util.Set;

/**
 * @author throne
 * @packageName org.shogakuframework.core
 * @className BeanContainerTest
 * @date 2020/5/14 16:09
 * @description
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BeanContainerTest {
    private static BeanContainer beanContainer;

    @Before
    public void init() throws Exception {
        beanContainer = BeanContainer.getInstance();
    }

    @Test
    public void Test1loadBeans() throws Exception {
        assert !beanContainer.isLoaded();
        beanContainer.loadBeans("com.throne", true);
        assert beanContainer.getBeanMapSize() > 0;
        assert beanContainer.isLoaded();
    }

    @Test
    public void Test2getBean() {
        HeadLineService service = (HeadLineService) beanContainer.getBean(HeadLineServiceImpl.class);
        System.out.println(service);

        assert beanContainer.getBean(HeadLine.class) == null;
    }

    @Test
    public void Test3getClasses() {
        Set<Class<?>> classSet = beanContainer.getClasses();
        assert classSet.size() == 2;
    }

    @Test
    public void Test4getBeans() {
        Set<Object> beans = beanContainer.getBeans();
        assert beans.size() == 2;
    }

    @Test
    public void Test5getClassByAnnotation() {
        Set<Class<?>> classSet = beanContainer.getClassByAnnotation(Service.class);
        assert classSet.size() == 2;

        Set<Class<?>> classSet1 = beanContainer.getClassByAnnotation(Component.class);
        assert classSet1 == null;
    }

    @Test
    public void Test5getClassBySuper() {
        Set<Class<?>> classSet = beanContainer.getClassBySuper(HeadLineService.class, false);
        assert classSet.size() == 1;
    }
}
