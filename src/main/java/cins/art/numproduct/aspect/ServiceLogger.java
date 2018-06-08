package cins.art.numproduct.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceLogger {

	@Pointcut("execution(public * cins.art.numproduct.service.*.*(..))")
	public void service(){}

	@Before("service()")
	public void before(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		String method = signature.getDeclaringTypeName() + '.' + signature.getName();
		log.info("\n");
		log.info("calling : " + method);
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			log.info("arg : " + arg);
		}
		log.info("\n");
	}


	@AfterReturning(pointcut = "service()", returning = "ret")
	public void afterReturn(Object ret) {
		log.info("\n");
		log.info("service return : " + ret);
		log.info("\n");
	}

}
