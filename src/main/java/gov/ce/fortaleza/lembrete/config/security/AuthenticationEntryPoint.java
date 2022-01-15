package gov.ce.fortaleza.lembrete.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by berkson
 * Date: 14/01/2022
 * Time: 09:39
 */
@Slf4j
@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void afterPropertiesSet() {
        setRealmName("lembrete");
        super.afterPropertiesSet();
    }

    public AuthenticationEntryPoint(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}
