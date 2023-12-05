package biz.phanithnhoem.api.payment;

public interface PaymentService {
    void processPayment(String order, String paymentMethod, Double amount);
}
