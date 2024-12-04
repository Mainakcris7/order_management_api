package com.practice.order_management.AOP;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLoggingAspect {

    @Pointcut("execution(* com.practice.order_management.controller.*.*(..))")
    public void allControllerMethods() {
    }

    @Around("allControllerMethods()")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        // Getting the method execution time and result
        String methodSignature = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        // Writing to the log file
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(
                        "./src/main/java/com/practice/order_management/logs/logs.txt", true));) {

            writer.write("[" + methodSignature + "]" + " - execution time : " + (endTime - startTime) + " ms "
                    + "TIMESTAMP " + LocalDateTime.now() + "\n");
        } catch (IOException e) {
            System.out.println("File not found");
        }

        // Returning the result
        return result;
    }

}
