
package com.tunnel.e2e.asp;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
public class ResourceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ResourceLogAspect.class);

    // 定义切点：controller 包下的所有类的方法
    @Pointcut("execution(* com.tunnel.e2e.resource..*.*(..))")
    public void requestLog() {
        // 方法体为空，用于定义切点
    }

    @Around("requestLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求上下文
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        long startTime = System.currentTimeMillis();

        try {
            // 执行原方法
            Object result = joinPoint.proceed();

            // 记录日志
            logger.info("\nURL: {} \nHTTP Method: {} \nIP: {} \nMethod: {}.{}() \nArgs: {} \nResponse: {} \nTime: {} ms",
                    request.getRequestURL().toString(),
                    request.getMethod(),
                    request.getRemoteAddr(),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs()),
                    result,
                    System.currentTimeMillis() - startTime);
            return result;
        } catch (Exception e) {
            logger.error("Controller 请求处理异常", e);
            throw e;
        }
    }
}