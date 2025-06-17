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
        log.info("Step into aspect : ");
        log.info("Args: " + Arrays.asList(joinPoint.getArgs()));

        Object obj = joinPoint.proceed();

        log.info("Step out aspect.");
        return obj;
    }

    @Around("execution(public * (@com.lw.jpa.demo.aspect.LogIntercept com.lw.jpa.demo..*).*(..))")
    public Object typeAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        Object obj = joinPoint.proceed();
        log.info("Done the aspect, the result is: " + obj);
        return obj;
    }
}
