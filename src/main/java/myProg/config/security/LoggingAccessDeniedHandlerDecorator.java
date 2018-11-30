package myProg.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoggingAccessDeniedHandlerDecorator implements AccessDeniedHandler {
    private AccessDeniedHandler delegate;

    LoggingAccessDeniedHandlerDecorator(@NonNull AccessDeniedHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            log.warn("{} was trying to access protected resource: {}", auth.getName(), request.getRequestURI());
        }

        delegate.handle(request, response, ex);
    }
}
