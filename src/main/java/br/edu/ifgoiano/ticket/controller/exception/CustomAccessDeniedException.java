package br.edu.ifgoiano.ticket.controller.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.nio.file.AccessDeniedException;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class CustomAccessDeniedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CustomAccessDeniedException(String message) {
        super(message);
    }
}
