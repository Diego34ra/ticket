package br.edu.ifgoiano.ticket.controller.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private HttpServletRequest request;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        HttpStatus status = HttpStatus.FORBIDDEN;
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(status.value());

        ObjectMapper mapper = new ObjectMapper();
        ErrorDetails errorDetails = new ErrorDetails(new Date(),status.value(),"Acesso negado.Você não tem permissão para acessar este recurso.",getRequestPath());
        response.getWriter().write(mapper.writeValueAsString(errorDetails)
        );
    }

    private String getRequestPath() {
        return request.getRequestURI();
    }

}
