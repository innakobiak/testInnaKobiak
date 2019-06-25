package com.test.boot.helloJPA;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.ObjectError;

import java.util.List;

public class CustomResponse {
    private Boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public CustomResponse() {
    }

    public CustomResponse(String message, Boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public CustomResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public CustomResponse(String localizedMessage, List<ObjectError> allErrors, boolean b, Object o) {
        this.message = localizedMessage;
        this.success = success;
        this.data = o;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CustomResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data.toString() +
                '}';
    }
}
