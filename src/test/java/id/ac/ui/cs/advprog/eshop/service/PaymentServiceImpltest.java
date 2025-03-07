package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImpltest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    private List<Payment> payments;
    private List<Order> orders;

    @BeforeEach
    void setUp() {
        payments = new ArrayList<>();
        orders = new ArrayList<>();

        // Create sample products
        List<Product> productList = new ArrayList<>();
        Product item = new Product();
        item.setId("abc111-def222-ghi333");
        item.setName("Organic Soap");
        item.setQuantity(3);
        productList.add(item);

        Order firstOrder = new Order("ORD-100", productList, 1708561000L, "Alice");
        Order secondOrder = new Order("ORD-200", productList, 1708562000L, "Bob");
        orders.add(firstOrder);
        orders.add(secondOrder);

        Map<String, String> codData = new HashMap<>();
        codData.put("address", "River Road");
        codData.put("deliveryFee", "15");
        Payment codPayment = new Payment(firstOrder.getId(), PaymentMethod.COD.name(), codData);
        payments.add(codPayment);

        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("VoucherCode", "ESHOP1234WXYZ5678");
        Payment voucherPayment = new Payment(secondOrder.getId(), PaymentMethod.VOUCHER.name(), voucherData);
        payments.add(voucherPayment);
    }


    @Test
    void testAddPaymentSuccess() {
        Payment payment = payments.get(1); // The voucher payment
        Order order = orders.get(1);

        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        Payment result = paymentService.addPayment(order, payment.getMethod().name(), payment.getPaymentData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getPaymentData(), result.getPaymentData());
    }


    @Test
    void testGetPaymentIdFound() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).getPayment(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }


    @Test
    void testGetPaymentIdNotFound() {
        doReturn(null).when(paymentRepository).getPayment("PAY-999");
        assertNull(paymentService.getPayment("PAY-999"));
    }


    @Test
    void testSetStatusSuccess() {
        Payment payment = payments.get(0); // The COD payment
        Order order = orders.get(0);

        doReturn(order).when(orderRepository).findById(order.getId());
        doReturn(order).when(orderRepository).save(any(Order.class));
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.name());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));

        assertEquals(PaymentStatus.SUCCESS, result.getStatus());
        assertEquals("SUCCESS", order.getStatus());
    }


    @Test
    void testSetStatusRejected() {
        Payment payment = payments.get(1); // The VOUCHER payment
        Order order = orders.get(1);

        doReturn(order).when(orderRepository).findById(order.getId());
        doReturn(order).when(orderRepository).save(any(Order.class));
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.name());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));

        assertEquals(PaymentStatus.REJECTED, result.getStatus());
        assertEquals("FAILED", order.getStatus());
    }


    @Test
    void testSetStatusToInvalidStatus() {
        Payment payment = payments.get(0);
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.setStatus(payment, "NOT_REAL_STATUS"));
    }
    @Test
    void testAddPaymentVoucherNullValue() {
        Order order = orders.get(0);
        Map<String, String> nullVoucher = new HashMap<>();
        nullVoucher.put("VoucherCode", null);
        Payment result = paymentService.addPayment(order, PaymentMethod.VOUCHER.name(), nullVoucher);
        assertEquals(PaymentStatus.REJECTED, result.getStatus());
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).getAllPayments();

        List<Payment> all = paymentService.getAllPayments();
        verify(paymentRepository, times(1)).getAllPayments();
        assertEquals(payments.size(), all.size());
    }

}

