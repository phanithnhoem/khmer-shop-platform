package biz.phanithnhoem.api.order;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> checkout(@RequestParam String userUuid) {
        // Checkout the cart
        Order order = orderService.checkout(userUuid);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
