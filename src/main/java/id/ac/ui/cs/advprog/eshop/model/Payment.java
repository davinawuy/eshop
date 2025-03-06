package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private PaymentMethod method;
    private PaymentStatus status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        // TODO: Initialize id, paymentData, and default status.
        // TODO: Validate and set the method using setMethod(method).
        // TODO: Apply business logic for COD and VOUCHER based on paymentData.
    }


    public Payment(String id, String method, PaymentStatus status, Map<String, String> paymentData) {
        // TODO: Call the primary constructor.
        // TODO: Validate and set the status using setStatus(status).
    }


    public void setMethod(String method) {
        // TODO: Add validation logic to set this.method to PaymentMethod.COD or PaymentMethod.VOUCHER.
    }


    public void setStatus(PaymentStatus status) {
        // TODO: Add validation logic to set this.status, throwing an exception for invalid statuses.
    }
}
