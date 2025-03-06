package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.HashMap;
import java.util.Map;

public class PaymentTest {

    @Test
    public void testCreatePaymentDefaultVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("VoucherCode", "ESHOP12345678ABC");

        Payment payment = new Payment("A123", "VOUCHER", paymentData);

        // Assertions
        assertEquals("A123", payment.getId());
        assertEquals(PaymentMethod.VOUCHER, payment.getMethod());
        assertTrue(payment.getPaymentData().containsKey("VoucherCode"));
        assertEquals("ESHOP12345678ABC", payment.getPaymentData().get("VoucherCode"));
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    @Test
    public void testCreatePaymentDefaultCash() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("deliveryFee", "25");
        paymentData.put("address", "MainStreet42");

        Payment payment = new Payment("B456", "COD", paymentData);

        // Assertions
        assertEquals("B456", payment.getId());
        assertEquals(PaymentMethod.COD, payment.getMethod());
        assertTrue(payment.getPaymentData().containsKey("deliveryFee"));
        assertTrue(payment.getPaymentData().containsKey("address"));
        assertEquals("25", payment.getPaymentData().get("deliveryFee"));
        assertEquals("MainStreet42", payment.getPaymentData().get("address"));
        assertEquals(PaymentStatus.CHECKING_PAYMENT, payment.getStatus());
    }

    @Test
    public void testSetStatusSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("PromoCode", "PROMO9876ZYX3333");
        Payment payment = new Payment("A123", "VOUCHER", paymentData);

        payment.setStatus(PaymentStatus.SUCCESS);

        // Assertions
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    @Test
    public void testSetStatusRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("PromoCode", "PROMO9876ZYX3333");
        Payment payment = new Payment("A123", "VOUCHER", paymentData);

        payment.setStatus(PaymentStatus.REJECTED);

        // Assertions
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    public void testCreatePaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Map<String, String> paymentData = new HashMap<>();
            paymentData.put("PromoCode", "PROMO9876ZYX3333");
            // This should throw because "UNKNOWN" is not SUCCESS or REJECTED
            new Payment("C789", "VOUCHER", PaymentStatus.valueOf("unknown"), paymentData);
        });
    }


    @Test
    public void testSetInvalidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("PromoCode", "PROMO9876ZYX3333");
        Payment payment = new Payment("D012", "VOUCHER", paymentData);

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus(PaymentStatus.valueOf("UNSUPPORTED_STATUS"));
        });
    }

    @Test
    public void testPaymentTransitionFromCheckingToRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("PromoCode", "PROMO1111XYZ5555");
        Payment payment = new Payment("E999", "VOUCHER", paymentData);

        payment.setStatus(PaymentStatus.REJECTED);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
        }
}
