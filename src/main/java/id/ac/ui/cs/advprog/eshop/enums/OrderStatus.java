package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter

public enum OrderStatus {
    WAITING_PAYMENT("WAITING_PAYMENT"),
    FAILED("FAILED"),
    SUCCESS("SUCCESS"),
    CANCELLED("CANCELLED");

    private final String value;
    // Changing contains Implementation to Set
    private static final Set<String> NAMES = new HashSet<>();

    static {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            NAMES.add(orderStatus.name());
        }
    }

    private OrderStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        return NAMES.contains(param);
    }
}