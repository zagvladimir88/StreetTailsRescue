package com.zagvladimir.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
@Aspect
public class LoggingServiceAspect {

    @Pointcut("execution(* com.zagvladimir.service.*.*.*(..))")
    public void aroundServicePointcut() {
    }
    @Pointcut("execution(* com.zagvladimir.dao.*.*(..))")
    public void aroundDaoPointcut() {
    }

    @Around("aroundServicePointcut() || aroundDaoPointcut()")
    public Object logAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Method {} in {} start" ,joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringType());
        Object proceed = joinPoint.proceed();
        String arguments = Arrays.stream(joinPoint.getArgs())
                .map(arg -> arg.toString())
                .collect(Collectors.joining(", "));
        log.info("Method: {}, Arguments: {}", joinPoint.getSignature().toShortString(), arguments);
        log.info("Method {} in {} finished", joinPoint.getSignature().getName(),joinPoint.getSignature().getDeclaringType());
        return proceed;
    }
}