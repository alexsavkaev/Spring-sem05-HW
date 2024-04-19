package ru.gb.sem05.HOMEWORK.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

@Component
@Aspect
public class TrackingAspect {

    @Before("@annotation(ru.gb.sem05.HOMEWORK.annotations.TrackUserAction)")
    public void trackUserAction(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        System.out.println("Был вызван метод " + methodName + " с параметрами " + Arrays.toString(args));
    }
}
