package com.pilaf.mybatis.aspect;

import com.pilaf.common.DatabaseContextHolder;
import com.pilaf.common.DatabaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @description: 由于代码不涉及到在一个service方法中同时修改db1、db2库，所以可以在service方法中就确定访问哪个数据源
 * pointcut表达式参考：https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#aop-pointcuts
 *
 * @author: dufeng3
 * @create: 2019-12-23 12:03
 */
@Slf4j
@Component
@Aspect
@Order(value = AspectOrderConstants.DataSourceSwitchAspectOrder)
public class DatabaseSwitchAspect {

    /**
     * db1的Service类中所有带Transactional注解注释的方法
     * 因为@Transactional注解会导致在Service层就需要确定访问的数据源
     */
    @Pointcut("execution(* com.pilaf.mybatis.service.db1.*.*(..)) && @annotation(org.springframework.transaction.annotation.Transactional)")
    private void db1TransactionalServiceMethod() {

    }

    /**
     * db1的pointcut由db1的mapper接口中所有方法和db1的带有@Transactional注解的方法组成
     */
    @Pointcut("execution(* com.pilaf.mybatis.db1.mapper..*.*(..)) || db1TransactionalServiceMethod()")
    private void db1Pointcut() {

    }

    @Around("db1Pointcut()")
    public Object db1AroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        log.info("db1AroundAdvice start");
        try {
            DatabaseContextHolder.setDatabase(DatabaseEnum.db1);
            Object result = pjp.proceed();
            DatabaseContextHolder.clearDatabase();
            return result;
        } finally {
            log.info("db1AroundAdvice end");
            DatabaseContextHolder.clearDatabase();
        }

    }


    /**
     * db2的Service类中所有带Transactional注解注释的方法
     * 因为@Transactional注解会导致在Service层就需要确定访问的数据源
     */
    @Pointcut("execution(* com.pilaf.mybatis.service.db2.*.*(..)) && @annotation(org.springframework.transaction.annotation.Transactional)")
    private void db2TransactionalServiceMethod() {

    }

    /**
     * db2的pointcut由db1的mapper接口中所有方法和db1的带有@Transactional注解的方法组成
     */
    @Pointcut("execution(* com.pilaf.mybatis.db2.mapper..*.*(..)) || db2TransactionalServiceMethod()")
    private void db2Pointcut() {

    }

    @Around("db2Pointcut()")
    public Object db2AroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        log.info("db2AroundAdvice start");
        try {
            DatabaseContextHolder.setDatabase(DatabaseEnum.db2);
            Object result = pjp.proceed();
            DatabaseContextHolder.clearDatabase();
            return result;
        } finally {
            log.info("db2AroundAdvice end");
            DatabaseContextHolder.clearDatabase();
        }

    }
}
