package br.com.welson.clinic.errors;

import javax.ws.rs.core.Response;

public class ErrorDetails {

    private String message;
    private String status;
    private int statusCode;

    public ErrorDetails(String message, Response.Status status) {
        this.message = message;
        this.status = status.getReasonPhrase();
        this.statusCode = status.getStatusCode();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
