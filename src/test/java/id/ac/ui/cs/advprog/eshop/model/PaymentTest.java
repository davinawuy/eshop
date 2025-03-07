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

    @Test
    void testConstructorWithValidStatusSuccess() {
        Map<String, String> data = new HashMap<>();
        data.put("VoucherCode", "ESHOP1234ABC5678"); // Valid: 16 chars, starts with ESHOP, 8 digits present
        Payment payment = new Payment("P100", PaymentMethod.VOUCHER.name(), PaymentStatus.SUCCESS, data);
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    @Test
    void testConstructorWithValidStatusRejected() {
        Map<String, String> data = new HashMap<>();
        data.put("VoucherCode", "INVALIDVOUCHERCODE"); // Voucher code doesn't meet criteria
        Payment payment = new Payment("P101", PaymentMethod.VOUCHER.name(), PaymentStatus.REJECTED, data);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testMissingAddressForCOD() {
        Map<String, String> data = new HashMap<>();
        data.put("deliveryFee", "10");
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> new Payment("P102", PaymentMethod.COD.name(), data));
        assertEquals("Missing address or deliveryFee for COD method", ex.getMessage());
    }

    @Test
    void testMissingDeliveryFeeForCOD() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Main Street");
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> new Payment("P103", PaymentMethod.COD.name(), data));
        assertEquals("Missing address or deliveryFee for COD method", ex.getMessage());
    }

    @Test
    void testInvalidMethodThrowsException() {
        Map<String, String> data = new HashMap<>();
        data.put("VoucherCode", "ESHOP1234ABC5678");
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> new Payment("P104", "CREDIT", data));
        assertTrue(ex.getMessage().contains("Invalid method"));
    }

    @Test
    void testSetInvalidStatusThrowsException() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Central Park");
        data.put("deliveryFee", "20");
        Payment payment = new Payment("P105", PaymentMethod.COD.name(), data);
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> payment.setStatus(PaymentStatus.CHECKING_PAYMENT));
        assertTrue(ex.getMessage().contains("Invalid status"));
    }

    @Test
    void testVoucherCodeNull() {
        Map<String, String> data = new HashMap<>();
        data.put("VoucherCode", null);
        Payment payment = new Payment("V001", PaymentMethod.VOUCHER.name(), data);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testVoucherCodeInvalidLength() {
        Map<String, String> data = new HashMap<>();
        data.put("VoucherCode", "ESHOP1234ABCD56"); // 15 characters instead of 16
        Payment payment = new Payment("V002", PaymentMethod.VOUCHER.name(), data);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testVoucherCodeDoesNotStartWithESHOP() {
        Map<String, String> data = new HashMap<>();
        data.put("VoucherCode", "TEST1234ABCD5678"); // 16 characters but wrong prefix
        Payment payment = new Payment("V003", PaymentMethod.VOUCHER.name(), data);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testVoucherCodeIncorrectDigitCount() {
        Map<String, String> data = new HashMap<>();
        data.put("VoucherCode", "ESHOP12ABCD34567");
        Payment payment = new Payment("V004", PaymentMethod.VOUCHER.name(), data);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testVoucherValid() {
        Map<String, String> data = new HashMap<>();
        data.put("VoucherCode", "ESHOP1234ABC5678"); // 16 characters, starts with ESHOP, exactly 8 digits in total
        Payment payment = new Payment("V005", PaymentMethod.VOUCHER.name(), data);
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

}

