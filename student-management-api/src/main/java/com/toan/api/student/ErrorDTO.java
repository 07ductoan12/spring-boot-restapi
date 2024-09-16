package com.toan.api.student;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * ErrorDTO
 */
public class ErrorDTO {
    private Date timestamp;
    private int status;
    private String path;
    private List<String> error = new ArrayList<>();

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public void addError(String message){
        this.error.add(message);
    }

}
