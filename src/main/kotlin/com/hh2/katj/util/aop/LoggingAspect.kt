package com.hh2.katj.util.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)


    @Pointcut("execution(* com.hh2.katj.*.controller..*.*(..)) || " +
            "execution(* com.hh2.katj.*.service..*.*(..))")
    fun allControllerAndServiceMethods() {}

    @Around("allControllerAndServiceMethods()")
    fun logMethodExecution(joinPoint: ProceedingJoinPoint): Any? {
        val methodName = joinPoint.signature.name
        val className = joinPoint.signature.declaringTypeName.replace("com.hh2.katj.","")
        val start = System.currentTimeMillis()

        val args = joinPoint.args
        logger.info("Method $className.$methodName called with arguments: ${args.joinToString()}")

        try {
            val result = joinPoint.proceed()
            val totalTime = System.currentTimeMillis() - start
            logger.info("Method $className.$methodName executed in $totalTime ms")
            return result
        } catch (e: Exception) {
            val totalTime = System.currentTimeMillis() - start
            logger.error("Method $className.$methodName threw an exception: ${e.message} in $totalTime ms")
            throw e
        }
    }

}