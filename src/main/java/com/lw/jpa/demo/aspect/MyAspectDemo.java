package com.lw.jpa.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author wei.liuw
 * @date 2019/5/13
 */
@Aspect
@Component
@Slf4j
public class MyAspectDemo {

    @Around("@annotation(logIntercept)")
    public Object methodAspect(ProceedingJoinPoint joinPoint, LogIntercept logIntercept) throws Throwable {
        log.info("进入aspect 切面：");
        log.info("参数列表：" + Arrays.asList(joinPoint.getArgs()));

        Object obj = joinPoint.proceed();

        log.info("切面处理结束！");
        return obj;
    }

    @Around("execution(public * (@com.lw.jpa.demo.aspect.LogIntercept com.lw.jpa.demo..*).*(..))")
    public Object typeAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        Object obj = joinPoint.proceed();
        log.info("切面执行结束，执行结果：" + obj);
        return obj;
    }
}
