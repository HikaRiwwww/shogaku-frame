package org.shogakuframework.core;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author throne
 * @packageName org.shogakuframework.core
 * @className BeanContainerTest
 * @date 2020/5/14 16:09
 * @description
 */
public class BeanContainerTest {
    private static BeanContainer beanContainer;

    @Before
    public void init() throws Exception {
        beanContainer = BeanContainer.getInstance();
    }

    @Test
    public void loadBeansTest() throws Exception {
        assert !beanContainer.isLoaded();
        beanContainer.loadBeans("com.throne", true);
        assert beanContainer.getBeanMapSize() > 0;
        assert beanContainer.isLoaded();
    }


}
