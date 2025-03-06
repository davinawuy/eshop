package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentList = new ArrayList<>();

    public Payment save(Payment payment) {
        if (payment.getMethod() == PaymentMethod.VOUCHER) {
            String code = payment.getPaymentData().get("VoucherCode");
            if (code != null && code.startsWith("ESHOP") && code.length() == 16) {
                payment.setStatus(PaymentStatus.SUCCESS);
            } else {
                payment.setStatus(PaymentStatus.REJECTED);
            }
        } else if (payment.getMethod() == PaymentMethod.COD) {
            Map<String, String> data = payment.getPaymentData();
            if (data.containsValue("") || data.containsValue(null)) {
                payment.setStatus(PaymentStatus.REJECTED);
            } else {
                payment.setStatus(PaymentStatus.SUCCESS);
            }
        }

        for (int idx = 0; idx < paymentList.size(); idx++) {
            if (paymentList.get(idx).getId().equals(payment.getId())) {
                paymentList.set(idx, payment);
                return payment;
            }
        }
        paymentList.add(payment);
        return payment;
    }

    public Payment getPayment(String id) {
        for (Payment savedPayment : paymentList) {
            if (savedPayment.getId().equals(id)) {
                return savedPayment;
            }
        }
        return null;
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(paymentList);
    }
}
