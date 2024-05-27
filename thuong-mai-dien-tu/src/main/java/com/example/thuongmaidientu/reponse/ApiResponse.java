package com.example.thuongmaidientu.reponse;

public class ApiResponse {
    private String message;
    private Boolean Status;

    public ApiResponse() {
    }

    public ApiResponse(String message, Boolean status) {
        this.message = message;
        Status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
