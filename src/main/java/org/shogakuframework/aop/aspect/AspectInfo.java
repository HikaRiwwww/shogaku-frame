package org.shogakuframework.aop.aspect;

/**
 * 存储了每次切面织入的具体实例（DefaultAspect）和织入的优先级
 * org.shogakuframework.aop.aspect
 * Created by throne on 2020/6/9
 */
public class AspectInfo {
    private DefaultAspect defaultAspect;
    private int orderIndex;

    public DefaultAspect getDefaultAspect() {
        return defaultAspect;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public AspectInfo(DefaultAspect defaultAspect, int orderIndex) {
        this.defaultAspect = defaultAspect;
        this.orderIndex = orderIndex;
    }
}
