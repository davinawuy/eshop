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
        this.id = id;
        this.setMethod(method);
        this.status = PaymentStatus.CHECKING_PAYMENT;  // default
        this.paymentData = paymentData;

        // If method is COD, ensure address/deliveryFee are present
        if (this.method == PaymentMethod.COD) {
            if (!paymentData.containsKey("address") || !paymentData.containsKey("deliveryFee")) {
                throw new IllegalArgumentException("Missing address or deliveryFee for COD method");
            }
        }
        // If method is VOUCHER, check for valid voucher code
        else if (this.method == PaymentMethod.VOUCHER) {
            String voucherCode = paymentData.get("VoucherCode");
            if (voucherCode == null
                    || voucherCode.length() != 16
                    || !voucherCode.startsWith("ESHOP")
                    || voucherCode.replaceAll("[^0-9]", "").length() != 8) {
                this.status = PaymentStatus.REJECTED;
            } else {
                this.status = PaymentStatus.SUCCESS;
            }
        }
    }


    public Payment(String id, String method, PaymentStatus status, Map<String, String> paymentData) {
        this(id, method, paymentData);
        this.setStatus(status);
    }


    public void setMethod(String method) {
        if (PaymentMethod.COD.name().equals(method)) {
            this.method = PaymentMethod.COD;
        } else if (PaymentMethod.VOUCHER.name().equals(method)) {
            this.method = PaymentMethod.VOUCHER;
        } else {
            throw new IllegalArgumentException("Invalid method: " + method);
        }
    }


    public void setStatus(PaymentStatus status) {
        if (status == PaymentStatus.SUCCESS || status == PaymentStatus.REJECTED) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}
