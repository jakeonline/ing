package com.odsinada.ing.controllers;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Configuration
@Slf4j
public class LoggingAspect {
    @Around("execution (* com.odsinada.ing.controllers.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String id = UUID.randomUUID().toString();
        log.info("Start[{}]: {}({})"
                , id
                , joinPoint.getSignature().getName()
                , Stream.of(joinPoint.getArgs()).collect(Collectors.toList()));
        Object result = joinPoint.proceed();
//        TODO: Log HTTP response status code
        log.info("End[{}]", id);

        return result;
    }

}

