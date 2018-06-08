package cins.art.numproduct.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class SqlLogger {
    @Pointcut("execution(public * cins.art.numproduct.mapper.*.*(..))")
    public void doMapper(){}
    @Before("doMapper()")
    public void before(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        String method = signature.getDeclaringTypeName()+'.'+signature.getName();
        log.info("\n");
        log.info("calling:"+method);
        Object[] args = joinPoint.getArgs();
        for (Object arg : args){
            log.info("arg:"+arg);
        }
        log.info("\n");
    }

}
