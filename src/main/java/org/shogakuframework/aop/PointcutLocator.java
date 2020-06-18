package org.shogakuframework.aop;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;

public class PointcutLocator {
    private PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(
            PointcutParser.getAllSupportedPointcutPrimitives()
    );

    private PointcutExpression pointcutExpression;

    public PointcutLocator(String expression) {
        this.pointcutExpression = this.pointcutParser.parsePointcutExpression(expression);
    }

    /**
     * 初步筛选
     * @param targetClass
     * @return
     */
    public boolean roughMatches(Class<?> targetClass) {
        return pointcutExpression.couldMatchJoinPointsInType(targetClass);
    }


    public boolean accurateMatches(Method method) {
        ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);
        return shadowMatch.alwaysMatches();
    }
}