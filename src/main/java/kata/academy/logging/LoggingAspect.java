package kata.academy.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* kata.academy.service.UserService.*(..))")
    public void logBefore(JoinPoint joinPoint) {

        logger.info("Вызов метода : {}({})",
                joinPoint.getSignature().getName(),
                Arrays.stream(joinPoint.getArgs())
                        .map(Object::toString)
                        .collect(Collectors.joining(","))
        );
    }

    @AfterThrowing(pointcut = "execution(* kata.academy.service.UserService.*(..))", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Exception e) {
        logger.error("Ошибка при вызове метода : {}({})",
                joinPoint.getSignature().getName(),
                Arrays.stream(joinPoint.getArgs())
                        .map(Object::toString)
                        .collect(Collectors.joining(",")));
        logger.error("StackTrace ошибки: {}",
                Arrays.stream(e.getStackTrace())
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"))
        );
    }
}
