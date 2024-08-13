package com.toan.hello_restapi;

import java.util.Date;

/** response */
public class Response {

    private String message;
    private String time;

    public Response(String message) {
        this.message = message;
        this.time = new Date().toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
