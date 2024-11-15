package com.beauty911;

public class Consent {
    private String userEmail;
    private String status;

    public Consent(String userEmail, String status) {
        this.userEmail = userEmail;
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
