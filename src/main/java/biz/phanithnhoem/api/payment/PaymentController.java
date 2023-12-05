package biz.phanithnhoem.api.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> processPayment(@RequestParam String orderUuid,
                                                 @RequestParam(defaultValue = "CASH") String paymentMethod,
                                                 @RequestParam Double amount){
        paymentService.processPayment(orderUuid, paymentMethod, amount);
        return new ResponseEntity<>("Payment successfully completed.", HttpStatus.CREATED);
    }
}
