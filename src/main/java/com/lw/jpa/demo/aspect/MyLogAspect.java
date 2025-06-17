package com.lw.jpa.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * The turn aspect will act：
 *      Around begin： -> Before-> before -> around -> after -> afterReturning
 *  The rule for mapping
 *      * mapping to a single path，
 *      .. mapping to path with hierarchy
 *      + append to the class
 *   fuzzy mapping:
 *      execution(* *..get*(Long,..)) => means all method which start by get and has first param with type of Long,
 *   within(class path)：
 *      target class or package（should be a package with hierarchy，please see >> within(com.xhx.springboot..*)）
 * @author wei.liuw
 * @date 2019/7/8
 */
@Aspect
@Component
@Slf4j
public class MyLogAspect {

    /**
     * aspect： * means one level， .. means lot of levels
     * 1. access type，public，is not required, but is not type, do not use *
     * 2. first * means all return type
     * 3. second * means all class in this package
     * 4. the third * mean all method in class
     * 5.  .. means all parameters
     *  if add ..* after package, then means the hierarchy package will also include
     * execution(public * com.lw.jpa.demo.controller..*.*(..))
     */
    private static final String POINT_CUT = "execution(public * com.lw.jpa.demo.controller.*.*(..))";

    @Pointcut(POINT_CUT)
    public void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        log.info("@Before notice begin：");
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(arg -> log.info("arg = {}", arg));

        // aop proxy
        Object aThis = joinPoint.getThis();
        log.info("this = {}", aThis);

        // object who will by proxied
        Object target = joinPoint.getTarget();
        log.info("target = {}", target);

        // get method sign：
        Signature signature = joinPoint.getSignature();
        log.info("signature longType = {}", signature.toLongString());
        log.info("signature shortType = {}", signature.toShortString());
        log.info("signature = {}", signature.toString());

        // method name
        log.info("method name = {}", signature.getName());
        log.info("declare type name = {}", signature.getDeclaringTypeName());
        log.info("declare type = {} ", signature.getDeclaringType());

        // type
        String kind = joinPoint.getKind();
        log.info("kind = {}", kind);

        SourceLocation sourceLocation = joinPoint.getSourceLocation();
        log.info("sourceLocation = {}", sourceLocation.toString());

        JoinPoint.StaticPart staticPart = joinPoint.getStaticPart();
        log.info("staticPart = {}", staticPart);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null != attributes) {
            HttpServletRequest request = attributes.getRequest();
            log.info("request = {}", request.getRequestURL().toString());
            log.info("address = {}", request.getRemoteAddr());
            log.info("request method = {}", request.getMethod());
        }
        log.info("@Before  notice end!");
    }

    /**
     * after return:
     *      1. if first param is JoinPoint , then the second was the result
     *      2. if first param is not JoinPoint, then the first should be the result
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = POINT_CUT, returning = "result")
    public void doAfterReturning1(JoinPoint joinPoint, Object result) {
        log.info("@AfterReturning JoinPoint = {}, result = {} ", joinPoint.toShortString(), result);
    }

    @AfterReturning(value = POINT_CUT, returning = "result", argNames = "result")
    public void doAfterReturning2(Object result) {
        log.info("@AfterReturning result = {}", result);
    }

    /**
     * after notification
     * @param joinPoint     point cut
     */
    @After(value = POINT_CUT)
    public void doAfter(JoinPoint joinPoint) {
        log.info("@After notice  = {}", joinPoint.toLongString());
    }

    /**
     *  around can catch by step in step out, will control the whole process of the target method.
     *  proceedingJoinPoint.proceed(Object[] args) -> modify method's parameter
     *  do not use with @AfterThrowing at sametime
     * @param proceedingJoinPoint
     * @return
     */
    @Around(value = POINT_CUT)
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("@Around = {}", proceedingJoinPoint.getSignature().toString());
        Object object = null;

        try {
            object = proceedingJoinPoint.proceed();
            log.info("result = {}", object);
        } catch (Throwable throwable) {
            log.error("around throw :", throwable);
        }
        return object;
    }


}
