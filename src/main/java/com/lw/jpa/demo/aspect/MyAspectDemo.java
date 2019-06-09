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
        System.out.println("进入aspect 切面：");
        System.out.println(Arrays.asList(joinPoint.getArgs()));
        System.out.println(joinPoint.getKind());
        System.out.println(joinPoint.getSourceLocation());
        System.out.println(joinPoint.getSignature().getDeclaringType());
        System.out.println(joinPoint.getSignature().getDeclaringTypeName());
        System.out.println(joinPoint.getSignature().getModifiers());
        System.out.println(joinPoint.getSignature().getName());

        Object obj = joinPoint.proceed();

        System.out.println("切面处理结束！");
        return obj;
    }

    @Around("execution(public * (@com.lw.jpa.demo.aspect.LogIntercept com.lw.jpa.demo..*).*(..))")
    public Object typeAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        Object obj = joinPoint.proceed();
        log.info("切面执行结束，执行结果：" + obj);
        return obj;
    }
}
