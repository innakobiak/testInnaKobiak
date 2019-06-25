package com.test.boot.helloJPA;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class CustomExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler (PersonNotFoundExcception.class)
    public void springHandleNotFound(HttpServletResponse resp) throws IOException {
        resp.sendError(404, "No Encontrado");
    }
}
