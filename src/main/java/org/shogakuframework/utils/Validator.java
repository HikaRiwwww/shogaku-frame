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

    public static boolean isEmptySet(Set<?> set){
        return set == null || set.size() == 0;
    }


    public static boolean isEmptyMap(Map<?,?> map){
        return map == null || map.isEmpty();
    }

    public static boolean isEmptyList(Object[] list){
        return list==null || list.length == 0;
    }
}
