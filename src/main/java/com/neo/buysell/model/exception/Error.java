package com.neo.buysell.model.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;

public class Error {
    @JsonIgnore(value = false)
    private Timestamp timestamp;
    private int status;
    private String message;
    private String path;

    public Error() {

    }

    public Error(Timestamp timestamp, int status, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
