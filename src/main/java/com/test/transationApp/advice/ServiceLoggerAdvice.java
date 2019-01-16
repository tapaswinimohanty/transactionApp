
package com.test.transationApp.advice;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class ServiceLoggerAdvice {

	private static Logger logger = LoggerFactory.getLogger("Service Advice");

	@Around("CentralizedPointCutAspect.serviceClassesToLog()")
	public Object logInvocations(ProceedingJoinPoint pjoinPoint) throws Throwable {

		Object returnType = null;

		try {

			logger.info("Entering method {} of class {}", pjoinPoint.getSignature().getName(),
					pjoinPoint.getTarget().getClass().getName());
			returnType = pjoinPoint.proceed();

			logger.info("Completed method {}", pjoinPoint.getSignature().getName());

		} catch (Exception e) {

			logger.error(String.format("failed to execute method %s with error message %s",
					pjoinPoint.getSignature().getName(), e.getMessage()), e);

			throw e;

		}

		return returnType;
	}

}
