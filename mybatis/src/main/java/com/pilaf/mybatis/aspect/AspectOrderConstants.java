package com.pilaf.mybatis.aspect;

/**
 * @description: 切面的order越小优先级越高，beforeAdvice越先执行
 * 切面和被增强的方法就像热狗，切面是外面的面包，方法是里面的香肠。切面的order越大，越贴近香肠。order越小，越在外面。
 * 但是方法被切面增强后再执行，就像一支箭从上往下刺穿热狗。order小的切面的beforeAdvice反倒先执行，
 * order大的切面的beforeAdvice反倒后执行，切面的增强都执行完之后才会执行真正的方法。
 * 然后不同order切面的afterAdvice执行顺序和beforeAdvice反过来，order大的切面的
 * afterAdvice先执行，order小的切面的afterAdvice后执行。
 *        |
 *        | 方法调用
 *        |
 * --------------- order=1 的切面的 beforeAdvice
 *        |
 * --------------- order=2 的切面的 beforeAdvice
 *    |真正的方法|
 * --------------- order=2 的切面的 afterAdvice
 *        |
 * --------------- order=1 的切面的 afterAdvice
 *       \|/
 *
 * 注意：谈论切面order的前提是要针对同一个被增强的方法，如果两个切面增强的是不同的方法，谈论order毫无意义。
 * 比如一个切面的pointcut圈定的是Service层的方法，另一个切面的pointcut圈定的是dao层的方法，由于Service方法调用dao方法，
 * 所以不管这两个切面order如何，肯定是Service层的切面包着service层方法，service层方法调用被dao层切面包裹着的dao方法。
 *
 * @see DatabaseSwitchAspect
 * @see org.springframework.transaction.annotation.EnableTransactionManagement
 * @author: dufeng3
 * @create: 2020-01-06 15:55
 */
public interface AspectOrderConstants {
    /**
     * 切换数据源切面（要在事务开始前就切换数据源，否则事务方法拿不到对的数据源）
     */
    int DataSourceSwitchAspectOrder = 20;
    /**
     * 事务切面
     */
    int EnableTransactionManagementOrder = 100;
}
