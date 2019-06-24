package com.test.boot.helloJPA;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CustomResponse {
    private String message;
    private Boolean success;
    private Object data;

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
