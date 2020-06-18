package org.shogakuframework.aop.aspect;

import org.shogakuframework.aop.PointcutLocator;

/**
 * 存储了每次切面织入的具体实例（DefaultAspect）和织入的优先级
 * org.shogakuframework.aop.aspect
 * Created by throne on 2020/6/9
 */
public class AspectInfo {
    private DefaultAspect defaultAspect;
    private int orderIndex;
    private PointcutLocator pointcutLocator;

    public AspectInfo(DefaultAspect defaultAspect, int orderIndex, PointcutLocator pointcutLocator) {
        this.defaultAspect = defaultAspect;
        this.orderIndex = orderIndex;
        this.pointcutLocator = pointcutLocator;
    }

    public PointcutLocator getPointcutLocator() {
        return pointcutLocator;
    }

    public DefaultAspect getDefaultAspect() {
        return defaultAspect;
    }

    public int getOrderIndex() {
        return orderIndex;
    }
}
