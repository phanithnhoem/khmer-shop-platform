package biz.phanithnhoem.api.payment;

import biz.phanithnhoem.api.order.Order;
import biz.phanithnhoem.api.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public void processPayment(String orderUuid, String paymentMethod, Double amount) {
        // Perform payment processing logic (e.g., connect to a payment gateway)
        // For simplicity, we are assuming the payment is successful
        Order order = orderRepository.findByUuid(orderUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.OK,
                        "The requested order was not found in the database."));

        if(order != null && Objects.equals(order.getTotalAmount(), amount)) {
            Payment payment = new Payment();
            payment.setPaymentDate(Instant.now());
            payment.setPaymentMethod(paymentMethod);
            payment.setAmount(amount);
            payment.setOrder(order);
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);
        }
        throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, "Please check payment amount!");
    }
}
