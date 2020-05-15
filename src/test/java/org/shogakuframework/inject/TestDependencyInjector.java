package org.shogakuframework.inject;

import com.throne.controller.admin.AdminPageController;
import com.throne.controller.common.MainPageControler;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.shogakuframework.core.BeanContainer;

/**
 * @author throne
 * @packageName org.shogakuframework.inject
 * @className TestDependencyInjector
 * @date 2020/5/15 11:16
 * @description
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDependencyInjector {
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
    public void Test2LoadController() throws Exception {
        AdminPageController adminPageController =(AdminPageController) beanContainer.getBean(AdminPageController.class);
        assert adminPageController != null;
        assert adminPageController.getHeadLineService() == null;
        assert adminPageController.getShopCategorySerivce() == null;
        new DependencyInjector().doIoc(true);
        assert adminPageController.getHeadLineService() != null;
        assert adminPageController.getShopCategorySerivce() != null;
    }

}
