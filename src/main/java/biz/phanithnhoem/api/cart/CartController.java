package biz.phanithnhoem.api.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestParam String userUuid,
                                                 @RequestParam String productUuid) {
        //Remove product from the cart for the given user
        cartService.removeFromCart(userUuid, productUuid);
        return new ResponseEntity<>("Product removed from the cart successfully", HttpStatus.OK);
    }

    @PostMapping("/add")
    ResponseEntity<String> addToCart(@RequestParam String userUuid,
                                     @RequestParam String productUuid,
                                     @RequestParam(defaultValue = "1", required = false) Integer qty){
        // Add product to the cart for the given user
        cartService.addToCart(userUuid, productUuid, qty);
        return new ResponseEntity<>("Product added to the cart successfully", HttpStatus.OK);
    }

}
