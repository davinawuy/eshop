package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.HashMap;
import java.util.Map;

public class PaymentTest {

    @Test
    public void testCreatePaymentDefaultVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("PromoCode", "PROMO9876ZYX3333");

        Payment payment = new Payment("A123", "VOUCHER", paymentData);

        // Assertions
        assertEquals("A123", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertTrue(payment.getPaymentData().containsKey("PromoCode"));
        assertEquals("PROMO9876ZYX3333", payment.getPaymentData().get("PromoCode"));
        assertEquals("CHECKING_PAYMENT", payment.getStatus());
    }

    @Test
    public void testCreatePaymentDefaultCash() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("deliveryFee", "25");
        paymentData.put("address", "MainStreet42");

        Payment payment = new Payment("B456", "CASH", paymentData);

        // Assertions
        assertEquals("B456", payment.getId());
        assertEquals("CASH", payment.getMethod());
        assertTrue(payment.getPaymentData().containsKey("deliveryFee"));
        assertTrue(payment.getPaymentData().containsKey("address"));
        assertEquals("25", payment.getPaymentData().get("deliveryFee"));
        assertEquals("MainStreet42", payment.getPaymentData().get("address"));
        assertEquals("CHECKING_PAYMENT", payment.getStatus());
    }

    @Test
    public void testSetStatusSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("PromoCode", "PROMO9876ZYX3333");
        Payment payment = new Payment("A123", "VOUCHER", paymentData);

        payment.setStatus("SUCCESS");

        // Assertions
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    public void testSetStatusRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("PromoCode", "PROMO9876ZYX3333");
        Payment payment = new Payment("A123", "VOUCHER", paymentData);

        payment.setStatus("REJECTED");

        // Assertions
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    public void testCreatePaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Map<String, String> paymentData = new HashMap<>();
            paymentData.put("PromoCode", "PROMO9876ZYX3333");
            // This should throw because "UNKNOWN" is not SUCCESS or REJECTED
            new Payment("C789", "VOUCHER", "UNKNOWN", paymentData);
        });
    }


    @Test
    public void testSetInvalidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("PromoCode", "PROMO9876ZYX3333");
        Payment payment = new Payment("D012", "VOUCHER", paymentData);

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("UNSUPPORTED_STATUS");
        });
    }

    @Test
    public void testPaymentTransitionFromCheckingToRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("PromoCode", "PROMO1111XYZ5555");
        Payment payment = new Payment("E999", "VOUCHER", paymentData);

        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
        }
}
