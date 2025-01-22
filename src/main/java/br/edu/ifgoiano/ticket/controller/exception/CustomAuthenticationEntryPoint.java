package br.edu.ifgoiano.ticket.controller.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private HttpServletRequest request;

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        res.setContentType("application/json; charset=UTF-8");
        res.setStatus(status.value());

        ObjectMapper mapper = new ObjectMapper();
        ErrorDetails errorDetails = new ErrorDetails(new Date(),status.value(),"O Header de autorização está ausente ou é inválido.",getRequestPath());
        res.getWriter().write(mapper.writeValueAsString(errorDetails)
        );
    }

    private String getRequestPath() {
        return request.getRequestURI();
    }
}
