package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {

    private PaymentRepository PaymentRepository;
    private List<Payment> testPayments;

    @BeforeEach
    void init() {
        PaymentRepository = new PaymentRepository();
        testPayments = new ArrayList<>();

        Map<String, String> discountData = new HashMap<>();
        discountData.put("VoucherCode", "ESHOP1111TEST2222");
        Payment discountPayment = new Payment("PAY001", PaymentMethod.VOUCHER.name(), discountData);
        testPayments.add(discountPayment);

        Map<String, String> directData = new HashMap<>();
        directData.put("deliveryFee", "25");
        directData.put("address", "Maple Street");
        Payment directPayment = new Payment("PAY002", PaymentMethod.COD.name(), directData);
        testPayments.add(directPayment);
    }

    @Test
    void testFindByIdNoMatch() {
        for (Payment payment : testPayments) {
            PaymentRepository.save(payment);
        }
        Payment lookupResult = PaymentRepository.getPayment("noSuchId");
        assertNull(lookupResult);
    }

    @Test
    void testGetAllWhenEmpty() {
        List<Payment> allPayments = PaymentRepository.getAllPayments();
        assertTrue(allPayments.isEmpty());
    }

    @Test
    void testGetAll() {
        for (Payment payment : testPayments) {
            PaymentRepository.save(payment);
        }
        List<Payment> storedList = PaymentRepository.getAllPayments();
        assertEquals(2, storedList.size());
    }

    @Test
    void testInsertNewPayment() {
        Payment original = testPayments.get(1);
        Payment saved = PaymentRepository.save(original);

        Payment retrieved = PaymentRepository.getPayment(testPayments.get(1).getId());
        assertEquals(original.getId(), saved.getId());
        assertEquals(original.getId(), retrieved.getId());
        assertEquals(original.getMethod(), retrieved.getMethod());
        assertEquals(PaymentStatus.SUCCESS, retrieved.getStatus());
    }

    @Test
    void testVoucherWithInvalidPattern() {
        Map<String, String> badVoucher = new HashMap<>();
        badVoucher.put("VoucherCode", "BADCODE");
        Payment invalidPayment = new Payment("PAY003", PaymentMethod.VOUCHER.name(), badVoucher);

        PaymentRepository.save(invalidPayment);
        Payment result = PaymentRepository.getPayment(invalidPayment.getId());

        assertEquals(invalidPayment.getId(), result.getId());
        assertEquals(invalidPayment.getMethod(), result.getMethod());
        assertEquals(invalidPayment.getPaymentData().get("VoucherCode"), result.getPaymentData().get("VoucherCode"));
        assertEquals(PaymentStatus.REJECTED, result.getStatus());
    }

    @Test
    void testVoucherNotLongEnough() {
        Map<String, String> shortVoucher = new HashMap<>();
        shortVoucher.put("VoucherCode", "EHOP1234TEST567");
        Payment voucherPayment = new Payment("PAY006", PaymentMethod.VOUCHER.name(), shortVoucher);

        Payment outcome = PaymentRepository.save(voucherPayment);
        assertEquals(PaymentStatus.REJECTED, outcome.getStatus());
    }

    @Test
    void testCODWithMissingAddress() {
        Map<String, String> noAddress = new HashMap<>();
        noAddress.put("deliveryFee", "12");
        noAddress.put("address", "");
        Payment payment = new Payment("PAY004", PaymentMethod.COD.name(), noAddress);

        PaymentRepository.save(payment);
        Payment result = PaymentRepository.getPayment(payment.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertTrue(result.getPaymentData().get("address").isEmpty());
        assertEquals(PaymentStatus.REJECTED, result.getStatus());
    }

    @Test
    void testCODWithMissingFee() {
        Map<String, String> noFee = new HashMap<>();
        noFee.put("deliveryFee", "");
        noFee.put("address", "Maple Street");
        Payment payment = new Payment("PAY005", PaymentMethod.COD.name(), noFee);

        PaymentRepository.save(payment);
        Payment result = PaymentRepository.getPayment(payment.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertTrue(result.getPaymentData().get("deliveryFee").isEmpty());
        assertEquals(PaymentStatus.REJECTED, result.getStatus());
    }

    @Test
    void testFindByIdMatch() {
        for (Payment payment : testPayments) {
            PaymentRepository.save(payment);
        }
        Payment found = PaymentRepository.getPayment(testPayments.get(1).getId());
        assertEquals(testPayments.get(1).getId(), found.getId());
        assertEquals(testPayments.get(1).getMethod(), found.getMethod());
        assertEquals(testPayments.get(1).getPaymentData(), found.getPaymentData());
        assertEquals(testPayments.get(1).getStatus(), found.getStatus());
    }

    @Test
    void testUpdateExistingPayment() {
        Payment original = testPayments.get(0);
        Payment firstSave = PaymentRepository.save(original);

        Map<String, String> updatedData = new HashMap<>(original.getPaymentData());
        updatedData.put("VoucherCode", "ESHOP11112222333"); // 16 chars: ESHOP(5)+1111(4)+2222(4)+333(3)=16; digits: 1111+2222 = 8 digits
        Payment updatedPayment = new Payment(original.getId(), original.getMethod().name(), updatedData);

        Payment updatedSave = PaymentRepository.save(updatedPayment);

        Payment retrieved = PaymentRepository.getPayment(original.getId());
        assertEquals("ESHOP11112222333", retrieved.getPaymentData().get("VoucherCode"));
        assertEquals(PaymentStatus.SUCCESS, retrieved.getStatus());
    }


    @Test
    void testDuplicateSaveDoesNotIncreaseListSize() {
        Payment payment = testPayments.get(0);
        Payment firstSave = PaymentRepository.save(payment);
        int sizeAfterFirstSave = PaymentRepository.getAllPayments().size();

        Payment duplicateSave = PaymentRepository.save(payment);
        int sizeAfterDuplicateSave = PaymentRepository.getAllPayments().size();

        assertEquals(sizeAfterFirstSave, sizeAfterDuplicateSave);
    }

    @Test
    void testCODWithNullValue() {
        Map<String, String> codData = new HashMap<>();
        codData.put("address", null);
        codData.put("deliveryFee", "10");
        Payment payment = new Payment("PAY010", PaymentMethod.COD.name(), codData);
        Payment saved = PaymentRepository.save(payment);
        assertEquals(PaymentStatus.REJECTED, saved.getStatus());
    }

    @Test
    void testVoucherValidCode() {
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("VoucherCode", "ESHOP1234ABC5678"); // 16 chars, valid pattern
        Payment payment = new Payment("PAY011", PaymentMethod.VOUCHER.name(), voucherData);
        Payment saved = PaymentRepository.save(payment);
        assertEquals(PaymentStatus.SUCCESS, saved.getStatus());
    }
}
