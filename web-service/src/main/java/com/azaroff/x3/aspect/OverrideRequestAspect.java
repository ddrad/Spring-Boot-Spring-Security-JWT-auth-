package com.azaroff.x3.aspect;

import com.azaroff.x3.user.dao.entity.User;
import com.azaroff.x3.web.auth.dao.entity.AuthenticationData;
import java.util.stream.Stream;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OverrideRequestAspect {

//    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.azaroff.x3.annotation.FormatAuthRequest)")
    public void needModifyAuthRequest() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Before("needModifyAuthRequest()")
    public void logAfterThrowing(JoinPoint joinPoint) {
        Object args = Stream.of(joinPoint.getArgs()).findFirst().orElse(null);
        if (args instanceof AuthenticationData) {
            ((AuthenticationData) args).setUsername(((AuthenticationData) args).getEmail().toLowerCase());
        }
        if (args instanceof User) {
            ((User) args).setEmail(((User) args).getEmail().toLowerCase());
        }
    }
}
