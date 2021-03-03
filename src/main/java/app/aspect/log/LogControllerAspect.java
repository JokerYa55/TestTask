package app.aspect.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

/**
 * Логгирование контроллеров
 */
@Slf4j
@Aspect
@Configuration
public class LogControllerAspect {

    @Autowired
    HttpServletRequest request;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
        //Метод необходимый для инициализации pointcut'а
    }

    @Before("controller()")
    public void beforeController(JoinPoint joinPoint) {

        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Контроллер {} принял HTTP запрос:  {} {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                request.getMethod(),
                request.getRequestURL());
        log.info("Параметры, обрабатывемые контроллером :  {}", Arrays.toString(joinPoint.getArgs()));
    }

    @Around("controller()")
    public Object secure(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch clock = new StopWatch(joinPoint.toString());
        try {
            clock.start(joinPoint.toShortString());
            return joinPoint.proceed();
        } finally {
            clock.stop();
            log.info(clock.prettyPrint());
        }
    }

    @AfterReturning(
            pointcut = "controller()",
            returning = "result")
    public void afterController(JoinPoint joinPoint, Object result) {

        log.info("Контроллер {} вернул  {} ",
                joinPoint.getSignature().getDeclaringTypeName(),
                result);
    }

    /**
     * Обработка ошибок
     *
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(pointcut = "within(@org.springframework.web.bind.annotation.RestController *)", throwing = "exception")
    public void logAfterThrowingAllMethods(JoinPoint joinPoint, Throwable exception) {
        log.error("Ошибка при выполнении HTTP запроса : {} {} ошибка : {}", request.getMethod(), request.getRequestURL(), exception.getMessage());
    }
}
