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
 * 切面执行的顺序：
 *      Around 开始： -> Before开始 -> before结束 -> around结束 -> after开始 -> afterReturning开始
 *  切面的标识及意思
 *      * 只能匹配一级路径，
 *      .. 可以匹配多级路径
 *      +只能放在类后面，表示当前类及子类
 *   使用模糊匹配：
 *      execution(* *..get*(Long,..)) => 表示所有含有get的，且第一个参数是long类型的方法
 *   within(类路径)：
 *      限定类，指定包及（可以是一个包或者该包下的所有包，看配置 >> within(com.xhx.springboot..*)）
 * @author wei.liuw
 * @date 2019/7/8
 */
@Aspect
@Component
@Slf4j
public class MyLogAspect {

    /**
     * 切点： * 代表一个， .. 代表多个
     * 1. 访问符，public，可以不写，但是不能用 *
     * 2. 第一个 * 表示所有返回类型
     * 3. 第二个 * 表示包下所有类
     * 4. 第三个 * 表示所有方法
     * 5.  .. 表示所有参数类型
     *  如果在包后加..*，如下，就是对该包下所有的子包也生效
     * execution(public * com.lw.jpa.demo.controller..*.*(..))
     */
    private static final String POINT_CUT = "execution(public * com.lw.jpa.demo.controller.*.*(..))";

    @Pointcut(POINT_CUT)
    public void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        log.info("@Before 通知开始：");
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(arg -> log.info("arg = {}", arg));

        // aop代理对象
        Object aThis = joinPoint.getThis();
        log.info("this = {}", aThis);

        // 被代理对象
        Object target = joinPoint.getTarget();
        log.info("target = {}", target);

        // 获取方法签名：
        Signature signature = joinPoint.getSignature();
        log.info("signature longType = {}", signature.toLongString());
        log.info("signature shortType = {}", signature.toShortString());
        log.info("signature = {}", signature.toString());

        // 方法名称
        log.info("method name = {}", signature.getName());
        // 声明类型名
        log.info("declare type name = {}", signature.getDeclaringTypeName());
        // 声明类型 -> 方法所在的class对象(signature.getDeclaringType().getName() == signature.getDeclaringTypeName())
        log.info("declare type = {} ", signature.getDeclaringType());

        // 类型
        String kind = joinPoint.getKind();
        log.info("kind = {}", kind);

        // 返回连接点所在方法在类文件中的未知
        SourceLocation sourceLocation = joinPoint.getSourceLocation();
        log.info("sourceLocation = {}", sourceLocation.toString());

        // 返回连接点的静态部分
        JoinPoint.StaticPart staticPart = joinPoint.getStaticPart();
        log.info("staticPart = {}", staticPart);

        // 请求信息等
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null != attributes) {
            HttpServletRequest request = attributes.getRequest();
            log.info("request = {}", request.getRequestURL().toString());
            log.info("address = {}", request.getRemoteAddr());
            log.info("request method = {}", request.getMethod());
        }
        log.info("@Before  通知结束!");
    }

    /**
     * 后置返回:
     *      1. 如果第一个参数为 JoinPoint ,则第二个对象为返回信息
     *      2. 如果第一个参数不为 JoinPoint,则第一个参数为 returning 中对应的参数
     * @param joinPoint     切点对象
     * @param result        返回结果
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
     * 后置通知
     * @param joinPoint     切点
     */
    @After(value = POINT_CUT)
    public void doAfter(JoinPoint joinPoint) {
        log.info("@After notice  = {}", joinPoint.toLongString());
    }

    /**
     *  环绕很强大，可以决定目标方法是否执行，什么时候执行，是否需要替换参数，执行完是否需要替换返回值等
     *  proceedingJoinPoint.proceed(Object[] args) -> 修改方法的传入参数
     *  不能和 @AfterThrowing 同时使用
     * @param proceedingJoinPoint       环绕对象
     * @return                          返回结果
     */
    @Around(value = POINT_CUT)
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("@Around = {}", proceedingJoinPoint.getSignature().toString());
        Object object = null;

        try {
            // 可以加参数
            object = proceedingJoinPoint.proceed();
            log.info("result = {}", object);
        } catch (Throwable throwable) {
            log.error("around throw :", throwable);
        }
        return object;
    }

    /*
     * 后置异常通知
     *      throwing：匹配类型的异常被抛出才会触发该通知，throwable为所有类型
     * @param joinPoint     切点
     * @param exception      异常类型
     */
    /*@AfterThrowing(value = POINT_CUT, throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.info("signature name = {}", joinPoint.getSignature().getName());
        log.info("exception = {}", exception.toString());
    }*/
}
