package com.hanpyeon.academyapi.aspect.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Aspect
@Component
public class LoggingAspect {

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(proceedingJoinPoint.getTarget().getClass());
        List<String> arguments = Arrays.stream(proceedingJoinPoint.getArgs())
                .map(Objects::toString)
                .toList();
        logger.info("[ REQUEST ] -> " + proceedingJoinPoint.getSignature().getName() + ", [ ARGUMENT ] -> " + arguments);
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
            logger.info("[ REQUEST SUCCEED ]");
            return result;
        } catch (Throwable throwable) {
            logger.warn("[ REQUEST FAILED ] -> " + throwable.getMessage());
            throw throwable;
        }
    }
    @AfterThrowing(value = "within(@WarnLoggable *)", throwing = "exception")
    public void warningLog(JoinPoint joinPoint, Exception exception) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.warn("[ EXCEPTION ] -> " + exception.getClass().getSimpleName() + ", -> " + exception.getMessage());
    }

    @AfterThrowing(value = "within(@ErrorLoggable *)", throwing = "exception")
    public void errorLog(JoinPoint joinPoint, Exception exception) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.warn("[ EXCEPTION ] -> " + exception.getClass().getSimpleName() + " -> " + exception.getMessage());
    }

    @Pointcut("execution(* com.hanpyeon.academyapi..*Repository.*(..))")
    public void cutRepositoryAccess() {
    }

    @Before("cutRepositoryAccess()")
    public void beforeAccess(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        List<String> arguments = Arrays.stream(joinPoint.getArgs())
                .map(Objects::toString)
                .toList();
        logger.info("[ REPOSITORY ACCESS ] -> " + joinPoint.getSignature().getName() + ", [ ARGUMENT ] -> " + arguments);
    }

    @AfterThrowing(value = "cutRepositoryAccess()", throwing = "exception")
    public void afterRepositoryThrows(JoinPoint joinPoint, Exception exception) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        logger.error("[ EXCEPTION ] -> " + exception.getClass().getSimpleName() + " -> " + exception.getMessage());
    }
}
