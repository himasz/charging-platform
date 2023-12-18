package com.dcs.common.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;

@Aspect
@Component
@Slf4j
public class DCSLogger {

    @Pointcut("execution(* com.dcs.*.*(..))")
    public void controllersPoint() {
    }

    @Pointcut("execution(* com.dcs.*.*.*(..))")
    public void ComponentsPoint() {
    }


    private static final String POINTCUT = "controllersPoint() || ComponentsPoint()";

    @Before(POINTCUT)
    public void logBefore(JoinPoint joinPoint) {
        log.debug("> {} {}{}", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), argumentsToString(joinPoint));
    }

    @AfterReturning(pointcut = POINTCUT, returning = "result")
    public void logAfterResult(JoinPoint joinPoint, Object result) {
        String entityString = "<empty>";
        if (result != null) {
            entityString = result.toString();
        }
        log.debug("< {} {}{} = {}", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), argumentsToString(joinPoint), entityString);
    }

    @AfterThrowing(pointcut = POINTCUT, throwing = "e")
    public void logAfterException(JoinPoint joinPoint, Exception e) {
        log.error("< {} {}{} = {}", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), argumentsToString(joinPoint), e.toString());
    }

    private String argumentsToString(JoinPoint joinPoint) {
        StringBuilder argsString = new StringBuilder("[");

        if (joinPoint.getSignature() instanceof MethodSignature) {
            Object[] args = joinPoint.getArgs();
            Annotation[][] annotations = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterAnnotations();

            if (null != args) {
                for (int i = 0; i < args.length; i++) {
                    argsString.append(argumentToString(annotations[i], args[i]));

                    if (i != args.length - 1) {
                        argsString.append(", ");
                    }
                }
            }
        }
        argsString.append("]");

        return argsString.toString();
    }

    private String argumentToString(Annotation[] annotations, Object argValue) {
        StringBuilder argString = new StringBuilder();
        String argName = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof PathVariable) {
                argName = ((PathVariable) annotation).value();

            } else if (annotation instanceof RequestParam) {
                argName = ((RequestParam) annotation).value();

            } else if (annotation instanceof RequestHeader) {
                argName = ((RequestHeader) annotation).name();
            }
        }
        if (null != argName) {
            argString.append(argName).append("@");
        }
        argString.append(argValue);

        return argString.toString();
    }
}