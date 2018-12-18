package myProg.config.security.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

@Aspect
//@Component
@Slf4j
public class AccessDeniedLoggingAspect {

    @Pointcut("execution(public void org.springframework.security.web.access.AccessDeniedHandlerImpl.handle(javax.servlet.http.HttpServletRequest,..)) && args(request,..))")
    public void callAtHandle(HttpServletRequest request) {
    }

    @Before(value = "callAtHandle(request)", argNames = "request")
    public void beforeCallAtHandle(HttpServletRequest request) {
//        Object[] args = thisJoinPoint.getArgs();
//
//        HttpServletRequest request = (HttpServletRequest) args[0];
//        HttpServletResponse response= (HttpServletResponse)args[1];
//        AccessDeniedException ex= (AccessDeniedException)args[2];

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            log.warn("From aspect: {} was trying to access protected resource: {}", auth.getName(), request.getRequestURI());
        }

    }
}
