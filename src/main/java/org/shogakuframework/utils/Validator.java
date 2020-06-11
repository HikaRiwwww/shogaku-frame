package org.shogakuframework.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author throne
 * @packageName org.shogakuframework.utils
 * @className Validator
 * @date 2020/5/15 9:43
 * @description 验证判断相关的工具类
 */
public class Validator {

    public static boolean isEmpty(Set<?> set) {
        return set == null || set.size() == 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }
}
