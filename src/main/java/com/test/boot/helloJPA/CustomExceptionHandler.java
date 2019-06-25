package com.test.boot.helloJPA;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class CustomExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler ({PersonNotFoundExcception.class,PhoneNotFoundExcception.class})
    public @ResponseBody
    CustomResponse springHandleNotFound(HttpServletResponse resp, PersonNotFoundExcception e) throws IOException {
        resp.setStatus(404);
        return new CustomResponse( e.getLocalizedMessage(),false);
        //resp.sendError(404, "No Encontrado");
    }
}
