package org.shogakuframework.utils;

import org.junit.Assert;
import org.junit.Test;
import org.shogakuframework.utils.ClassUtil;

import java.util.Set;

/**
 * @author throne
 * @packageName org.shogakuframework
 * @className ClassUtilsTest
 * @date 2020/5/14 9:52
 * @description
 */
public class ClassUtilsTest {

    @Test
    public void extractPackageClassTest() throws Exception {
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("com.throne.entity");
        assert classSet.size() == 4;
    }
}
