package com.project1.project.model;


public class PdfPasswordRequest {
    private long applicationTransactionId;
    private String password;

    // Constructors (if needed)
    public PdfPasswordRequest() {
    }

    public PdfPasswordRequest(long applicationTransactionId, String password) {
        this.applicationTransactionId = applicationTransactionId;
        this.password = password;
    }

    // Getters and setters
    public long getApplicationTransactionId() {
        return applicationTransactionId;
    }

    public void setApplicationTransactionId(long applicationTransactionId) {
        this.applicationTransactionId = applicationTransactionId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
