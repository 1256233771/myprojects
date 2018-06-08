package cins.art.numproduct.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ControllerLogger {



	@Pointcut(value = "execution(public * cins.art.numproduct.controller.*.*(..))")
	public void controllerLog(){}

	@Before("controllerLog()")
	public void before(JoinPoint point) {
		log.info("controller aspect beginning");
		Object[] args = point.getArgs();
		for (Object arg : args) {
			log.info("arg : " + arg);
		}
		String method = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();
		log.info("aspect finishing");
		log.info("calling " + method);
	}

	@AfterReturning(pointcut = "controllerLog()", returning = "ret")
	public void afterReturning(Object ret) {
		log.info("controller return " + ret);
	}

	@AfterThrowing(pointcut = "controllerLog()", throwing = "throwable")
	public void afterThrowing(Throwable throwable) {
		log.info("controller throw " + throwable.getMessage());
	}

}
