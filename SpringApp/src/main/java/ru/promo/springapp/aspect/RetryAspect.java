package ru.promo.springapp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.promo.springapp.annotation.Retry;

@Slf4j
@Aspect
@Component
public class RetryAspect {
    @Around("@annotation(ru.promo.springapp.annotation.Retry)")
    public Object retry(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Retry annotation = signature.getMethod().getAnnotation(Retry.class);
        int count = annotation.count();
        long delay = annotation.delay();

        for (int i = 0; i < count; i++) {
            try {
                return joinPoint.proceed();
            } catch (Throwable th) {
                log.info("{} threw exception", methodName);
                Thread.sleep(delay);
                log.info("retry calling {}", methodName);
                if (i == count - 1) {
                    throw th;
                }
            }
        }

        return null;
    }
}
