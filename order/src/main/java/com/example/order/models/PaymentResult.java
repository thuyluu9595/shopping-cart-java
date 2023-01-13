package com.example.order.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Embeddable
public class PaymentResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private String updateTime;
    private String emailAddress;

    public PaymentResult() {
    }

    public PaymentResult(String status, String updateTime, String emailAddress) {
        this.status = status;
        this.updateTime = updateTime;
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "PaymentResult{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
