package app.aspect.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * Логгирование репозиториев
 */
@Slf4j
@Aspect
@Configuration
public class LogRepositoryAspect {

    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    public void repository() {
        //Метод необходимый для инициализации pointcut'а
    }

    @Before("repository()")
    public void beforeRepostiory(JoinPoint joinPoint) {
        log.info("Репозиторий {}  начинает работу с БД ",
                joinPoint.getSignature().getDeclaringTypeName());
    }

    @AfterReturning(
            pointcut = "repository()",
            returning = "result")
    public void afterRepository(JoinPoint joinPoint, Object result) {

        log.info("Метод {} репозитория {} вернул {}",
                joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringTypeName(),
                result);
    }

    @Pointcut("execution(public * org.springframework.data.repository.Repository+.*(..))")
    public void crudRepository() {
        //Метод необходимый для инициализации pointcut'а
    }

    @Before("crudRepository()")
    public void beforeCrudRepository(JoinPoint joinPoint) {
        if (joinPoint.getSignature().getName().equals("save")) {
            log.info("Репозиторий начинает сохранение следующей записи в БД: {}", joinPoint.getArgs());
        } else {
            log.info("Репозиторий начинает выполнение метода {} ", joinPoint.getSignature().getName());
        }
    }

    @AfterReturning(
            pointcut = "crudRepository()",
            returning = "result")
    public void afterCrudRepository(JoinPoint joinPoint, Object result) {
        if (joinPoint.getSignature().getName().equals("save")) {
            log.info("Репозиторий успешно сохранил запись в БД.");
        } else {
            log.info("Репозиторий завершил выполнение метода {} ", joinPoint.getSignature().getName());
        }
    }
}
