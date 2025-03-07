package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        // TODO: Create and save a new Payment using order, method, and paymentData
        return null;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        // TODO: Convert status string to PaymentStatus and update Payment
        // TODO: Update the linked Order's status using checkStatus
        return null;
    }

    private String checkStatus(String paymentStatus) {
        // TODO: Map PaymentStatus (SUCCESS, REJECTED, CHECKING_PAYMENT) to corresponding OrderStatus value
        return null;
    }

    @Override
    public Payment getPayment(String paymentId) {
        // TODO: Retrieve and return a Payment by its ID
        return null;
    }

    @Override
    public List<Payment> getAllPayments() {
        // TODO: Retrieve and return all Payment objects
        return null;
    }
}
